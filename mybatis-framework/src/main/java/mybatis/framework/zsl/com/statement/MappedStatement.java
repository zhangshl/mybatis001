package mybatis.framework.zsl.com.statement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import mybatis.framework.zsl.com.mapping.SqlSource;

@AllArgsConstructor
@Getter
@Setter
public class MappedStatement {
    private String id;
    private String statementType;
    private Class<?> paramterTypeClass;
    private Class<?> resultTypeClass;
    private SqlSource sqlSource;
}
