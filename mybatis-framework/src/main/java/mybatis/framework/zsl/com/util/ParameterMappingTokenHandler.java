package mybatis.framework.zsl.com.util;


import mybatis.framework.zsl.com.mapping.ParameterNameMapping;

import java.util.ArrayList;
import java.util.List;

public class ParameterMappingTokenHandler implements TokenHandler {
	private List<ParameterNameMapping> parameterNameMappings = new ArrayList<>();

	// context是参数名称
	@Override
	public String handleToken(String content) {
		parameterNameMappings.add(buildParameterMapping(content));
		return "?";
	}

	private ParameterNameMapping buildParameterMapping(String content) {
		ParameterNameMapping parameterNameMapping = new ParameterNameMapping(content);
		return parameterNameMapping;
	}

	public List<ParameterNameMapping> getParameterNameMappings() {
		return parameterNameMappings;
	}

	public void setParameterNameMappings(List<ParameterNameMapping> parameterNameMappings) {
		this.parameterNameMappings = parameterNameMappings;
	}

}
