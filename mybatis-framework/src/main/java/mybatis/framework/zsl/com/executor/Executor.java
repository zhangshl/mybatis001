package mybatis.framework.zsl.com.executor;

import mybatis.framework.zsl.com.config.Configuration;

import java.util.List;

public interface Executor {
    <T> List<T> query(Configuration configuration, String statementId, Object param);
}
