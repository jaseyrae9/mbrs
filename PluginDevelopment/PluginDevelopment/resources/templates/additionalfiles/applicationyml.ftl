micronaut:
  application:
    name: ${name}
  server:
    port: ${port?string["0"]}

eureka:
  client:
    registration:
      enabled: true
    defaultZone: ${r"${EUREKA_HOST:localhost}:${EUREKA_PORT:8761}"}

<#if dbUrl??>
datasources:
  default:
    url: ${dbUrl}
    driverClassName: org.postgresql.Driver
    username: ${dbUsername}
    password: ${dbPassword}

jpa:
  default:
    properties:
      hibernate:
        hbm2ddl:
          auto: update
        show_sql: true
 </#if>