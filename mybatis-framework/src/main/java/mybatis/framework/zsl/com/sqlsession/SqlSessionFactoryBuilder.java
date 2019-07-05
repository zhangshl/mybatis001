package mybatis.framework.zsl.com.sqlsession;

import mybatis.framework.zsl.com.config.Configuration;
import mybatis.framework.zsl.com.config.XMLConfigParser;
import mybatis.framework.zsl.com.parser.DocumentReader;
import org.dom4j.Document;

import java.io.InputStream;

public class SqlSessionFactoryBuilder {
    private Configuration configuration;

    public SqlSessionFactoryBuilder() {
        configuration = new Configuration();
    }

    public SqlSessionFactory build(InputStream inputStream) {
        Document document = DocumentReader.createDocument(inputStream);
        XMLConfigParser xmlConfigParser = new XMLConfigParser(configuration);
        configuration = xmlConfigParser.parseConfig(document.getRootElement());
        return new DefaultSqlSessionFactory(configuration);
    }
}
