package mybatis.framework.zsl.com.sqlsession;

import mybatis.framework.zsl.com.config.Configuration;
import mybatis.framework.zsl.com.executor.Executor;
import mybatis.framework.zsl.com.executor.SimpleExecutor;

import java.util.List;

public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    public <T> T selectOne(String statementId, Object param) {
        List<Object> list = selectList(statementId, param);
        if (list != null && list.size() == 1) {
            return (T) list.get(0);
        }
        return null;
    }

    @Override
    public <T> List<T> selectList(String statementId, Object param) {
        Executor executor = new SimpleExecutor();
        return executor.query(configuration, statementId, param);
    }

}
