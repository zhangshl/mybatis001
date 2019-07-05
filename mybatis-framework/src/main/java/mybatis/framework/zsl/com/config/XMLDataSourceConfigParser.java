package mybatis.framework.zsl.com.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Element;

import java.util.List;
import java.util.Properties;

/**
 * DataSource解析类，具体配置详见SqlMap-Config.xml
 */
public class XMLDataSourceConfigParser {
    private Configuration configuration;

    public XMLDataSourceConfigParser(Configuration configuration){
        this.configuration = configuration;
    }

    public void parseConfiguration(Element element){
        Element envElement = element.element("environments");
        parseEnvironments(envElement);
    }

    private void parseEnvironments(Element element) {
        // <environments default="dev">
        String defaultId = element.attributeValue("default");
        List<Element> elements = element.elements("environment");

        for (Element envElement : elements) {
            String envId = envElement.attributeValue("id");
            // 如果和默认的环境ID匹配，才进行解析
            if (envId != null && envId.equals(defaultId)) {
                parseDataSource(envElement.element("dataSource"));
            }
        }
    }

    private void parseDataSource(Element element) {
        String type = element.attributeValue("type");
        if (type == null || type.equals("")) {
            type = "DBCP";
        }
        List<Element> elements = element.elements("property");

        Properties properties = new Properties();
        for (Element propertyEle : elements) {
            String name = propertyEle.attributeValue("name");
            String value = propertyEle.attributeValue("value");

            properties.setProperty(name, value);
        }

        BasicDataSource dataSource = null;
        if (type.equals("DBCP")) {
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName(properties.getProperty("driver"));
            dataSource.setUrl(properties.getProperty("url"));
            dataSource.setUsername(properties.getProperty("username"));
            dataSource.setPassword(properties.getProperty("password"));
        }
        configuration.setDataSource(dataSource);
    }

}
