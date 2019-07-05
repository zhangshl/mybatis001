package mybatis.framework.zsl.com.mapping;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class BoundSql {
    private String sql;
    private List<ParameterNameMapping> paramterName;
}
