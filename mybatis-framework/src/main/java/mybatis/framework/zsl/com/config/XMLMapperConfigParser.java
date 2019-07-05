package mybatis.framework.zsl.com.config;

import mybatis.framework.zsl.com.mapping.SqlSource;
import mybatis.framework.zsl.com.parser.DocumentReader;
import mybatis.framework.zsl.com.statement.MappedStatement;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.InputStream;
import java.util.List;

public class XMLMapperConfigParser {
    private Configuration configuration;
    private String namespace;

    public XMLMapperConfigParser(Configuration configuration){
        this.configuration = configuration;
    }

    public void parseConfiguration(Element rootElement){
        namespace = rootElement.attributeValue("namespace");
        parseStatements(rootElement.elements("select"));
    }

    private void parseStatements(List<Element> elements) {
        /**
         * <select id="findUserById" parameterType="com.kkb.mybatis.po.User" resultType="com.kkb.mybatis.po.User" statementType="prepared">
         *   SELECT * FROM user WHERE id = #{id}
         * </select>
         */

        for (Element selectEle : elements) {
            parseStatement(selectEle);
        }
    }


    private void parseStatement(Element element){
        String id = element.attributeValue("id");
        String parameterType = element.attributeValue("parameterType");
        Class parameterTypeClass = getClassType(parameterType);
        String resultType = element.attributeValue("resultType");
        Class resultTypeClass = getClassType(resultType);
        String statementType = element.attributeValue("statementType");

        String sqlText = element.getTextTrim();
        SqlSource sqlSource = new SqlSource(sqlText);

        MappedStatement mappedStatement = new MappedStatement(id, statementType, parameterTypeClass, resultTypeClass, sqlSource);
        configuration.addMappedStatementMap(namespace+"."+id, mappedStatement);
    }

    //解析参数类型和返回值类型
    private Class<?> getClassType(String parameterType) {
        if (parameterType == null || "".equals(parameterType)){
            return null;
        }

        try {
            return Class.forName(parameterType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
