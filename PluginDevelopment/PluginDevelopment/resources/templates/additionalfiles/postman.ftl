{
	"info": {
		"_postman_id": "${id}",
		"name": "${name} Test",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
	},
	"item": [
		<#list classes as class>
		{
			"name": "${class.name}",
			"item": [
				<#if class.generateReadAll>
				{
					"name": "Get All",
					"request": {
						"method": "GET",
						"header": [],
						"url": {
							"raw": "http://localhost:${port?string["0"]}/${class.name?lower_case}",
							"protocol": "http",
							"host": [
								"localhost"
							],
							"port": "${port?string["0"]}",
							"path": [
								"${class.name?lower_case}"
							]
						}
					},
					"response": []
				}<#if class.generateReadOne || class.generateCreate || class.generateUpdate || class.generateDelete>,</#if>
				</#if>
				<#if class.generateReadOne>
				{
					"name": "Get One",
					"request": {
						"method": "GET",
						"header": [],
						"url": {
							"raw": "http://localhost:${port?string["0"]}/${class.name?lower_case}/${class.keyDefaultValue}",
							"protocol": "http",
							"host": [
								"localhost"
							],
							"port": "${port?string["0"]}",
							"path": [
								"${class.name?lower_case}",
								"${class.keyDefaultValue}"
							]
						}
					},
					"response": []
				}<#if class.generateCreate || class.generateUpdate || class.generateDelete>,</#if>
				</#if>
				<#if class.generateCreate>
				{
					"name": "Create",
					"request": {
						"method": "POST",
						"header": [],
						"body": {
							"mode": "raw",
							"raw": "{<#if class.properties?size !=0><#list class.editableProperties as property>\n\t\"${property.name}\": <#if property.feign>${property.type.keyDefaultValue}<#else>${property.type.defaultValue}</#if><#sep>,</#sep></#list>\n</#if>}",
							"options": {
								"raw": {
									"language": "json"
								}
							}
						},
						"url": {
							"raw": "http://localhost:${port?string["0"]}/${class.name?lower_case}",
							"protocol": "http",
							"host": [
								"localhost"
							],
							"port": "${port?string["0"]}",
							"path": [
								"${class.name?lower_case}"
							]
						}
					},
					"response": []
				}<#if class.generateUpdate || class.generateDelete>,</#if>
				</#if>
				<#if class.generateUpdate>
				{
					"name": "Edit",
					"request": {
						"method": "PUT",
						"header": [],
						"body": {
							"mode": "raw",
							"raw": "{<#if class.properties?size !=0><#list class.editableProperties as property>\n\t\"${property.name}\": <#if property.feign>${property.type.keyDefaultValue}<#else>${property.type.defaultValue}</#if><#sep>,</#sep></#list>\n</#if>}",
							"options": {
								"raw": {
									"language": "json"
								}
							}
						},
						"url": {
							"raw": "http://localhost:${port?string["0"]}/${class.name?lower_case}/${class.keyDefaultValue}",
							"protocol": "http",
							"host": [
								"localhost"
							],
							"port": "${port?string["0"]}",
							"path": [
								"${class.name?lower_case}",
								"${class.keyDefaultValue}"
							]
						}
					},
					"response": []
				}<#if class.generateDelete>,</#if>
				</#if>
				<#if class.generateDelete>
				{
					"name": "Delete",
					"request": {
						"method": "DELETE",
						"header": [],
						"body": {
							"mode": "raw",
							"raw": "",
							"options": {
								"raw": {
									"language": "json"
								}
							}
						},
						"url": {
							"raw": "http://localhost:${port?string["0"]}/${class.name?lower_case}/${class.keyDefaultValue}",
							"protocol": "http",
							"host": [
								"localhost"
							],
							"port": "${port?string["0"]}",
							"path": [
								"${class.name?lower_case}",
								"${class.keyDefaultValue}"
							]
						}
					},
					"response": []
				}
				</#if>
			],
			"protocolProfileBehavior": {}			
		}<#sep>,
		</#sep>
		</#list>
	],
	"protocolProfileBehavior": {}
}