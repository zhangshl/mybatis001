package mybatis.framework.zsl.com.config;

import lombok.AllArgsConstructor;
import mybatis.framework.zsl.com.parser.DocumentReader;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.InputStream;
import java.util.List;

@AllArgsConstructor
public class XMLConfigParser {
    private Configuration configuration;

    public Configuration parseConfig(Element rootElement){
        //解析DataSource
        XMLDataSourceConfigParser xmlDataSourceConfigParser = new XMLDataSourceConfigParser(configuration);
        xmlDataSourceConfigParser.parseConfiguration(rootElement);

        //解析mapper文件
        List<Element> elements = rootElement.element("mappers").elements("mapper");
        for (Element element : elements) {
            String resource = element.attributeValue("resource");
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);
            Document document = DocumentReader.createDocument(inputStream);
            XMLMapperConfigParser xmlMapperConfigParser = new XMLMapperConfigParser(configuration);
            xmlMapperConfigParser.parseConfiguration(document.getRootElement());
        }
        return configuration;
    }

}
