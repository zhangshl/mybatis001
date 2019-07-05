package mybatis.framework.zsl.com.mapping;

import lombok.AllArgsConstructor;
import mybatis.framework.zsl.com.util.GenericTokenParser;
import mybatis.framework.zsl.com.util.ParameterMappingTokenHandler;

@AllArgsConstructor
public class SqlSource {
    private String sqlText;

    public BoundSql getBoundSql(){
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", tokenHandler);
        String sql = genericTokenParser.parse(sqlText);
        return new BoundSql(sql, tokenHandler.getParameterNameMappings());
    }
}
