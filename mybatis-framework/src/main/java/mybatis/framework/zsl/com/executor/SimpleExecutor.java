package mybatis.framework.zsl.com.executor;

import mybatis.framework.zsl.com.config.Configuration;
import mybatis.framework.zsl.com.mapping.ParameterNameMapping;
import mybatis.framework.zsl.com.statement.MappedStatement;
import mybatis.framework.zsl.com.statement.StatementType;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor {
    @Override
    public <T> List<T> query(Configuration configuration, String statementId, Object paramObject) {
        Connection connection = null;
        List<Object> results = new ArrayList<>();

        try {
            connection = configuration.getDataSource().getConnection();
            MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
            String sql = mappedStatement.getSqlSource().getBoundSql().getSql();
            if (mappedStatement.getStatementType().equalsIgnoreCase(StatementType.PREPARED.toString())){
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                List<ParameterNameMapping> parameterNameMappingList = mappedStatement.getSqlSource().getBoundSql().getParamterName();
                // 获取入参类型
                Class<?> parameterTypeClass = mappedStatement.getParamterTypeClass();

                // 八种基本类型都如此处理
                if (parameterTypeClass == Integer.class) {
                    // 根本不关系#{}中的名称到底是什么
                    preparedStatement.setObject(1, paramObject);
                }else if (parameterTypeClass == String.class) {
                    // 根本不关系#{}中的名称到底是什么
                    preparedStatement.setObject(1, paramObject);
                }else {
                    //对象参数用此方法处理字段
                    for (int i = 0; i < parameterNameMappingList.size(); i++) {
                        ParameterNameMapping parameterNameMapping = parameterNameMappingList.get(i);
                        // 得到属性名称
                        String name = parameterNameMapping.getName();
                        // 通过反射获取入参对象中执行名称的属性值
                        Field field = parameterTypeClass.getDeclaredField(name);
                        //Field为私有时也可以访问
                        field.setAccessible(true);
                        //获取对象的field的值
                        Object value = field.get(paramObject);
                        preparedStatement.setObject(i+1, value);
                    }
                }

                ResultSet resultSet = preparedStatement.executeQuery();
                // 获取入参类型
                Class<?> resultTypeClass = mappedStatement.getResultTypeClass();
                while (resultSet.next()){
                    Object returnObject = resultTypeClass.newInstance();
                    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                    int count = resultSetMetaData.getColumnCount();
                    for (int i = 1; i <= count; i++) {
                        String columnName = resultSetMetaData.getColumnName(i);
                        Field field = resultTypeClass.getDeclaredField(columnName);
                        field.setAccessible(true);
                        field.set(returnObject, resultSet.getObject(columnName));
                    }
                    results.add(returnObject);
                }

            }else {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return (List<T>) results;
    }
}
