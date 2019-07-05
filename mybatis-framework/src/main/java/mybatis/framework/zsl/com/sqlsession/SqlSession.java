package mybatis.framework.zsl.com.sqlsession;

import java.util.List;

public interface SqlSession {
    <T> T selectOne(String statementId, Object param);
    <T> List<T> selectList(String statementId, Object param);
}
