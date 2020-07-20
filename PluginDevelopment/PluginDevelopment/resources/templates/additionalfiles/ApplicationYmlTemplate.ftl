micronaut:
  application:
    name: ${name}
  server:
    port: 8081

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