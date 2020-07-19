package generated.model${package};

public enum ${name} {
	<#list literals as literal>${literal}<#sep>,
	</#sep></#list><#if literals?has_content>;</#if>		
	
	public String get() {
		switch (this) {
		<#list literals as literal>
		case ${literal}:
			return "${literal}";
		</#list>
		default:
			return "";
		}
	}
}