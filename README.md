# CLM (Cable Label Management System)

## Deployment

### Creating and Initialize Database

Use a MySQL client to connect to the local MySQL server and execute the following script to create an empty database (assuming the name is `clm`):

```sql
CREATE SCHEMA `clm` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
```

Go to the `src` directory and package the Java project:

```shell
mvn package
```

Then, run a command-line tool to initialize the database:

```shell
java -jar ./clm-service-cli/target/clm-service-cli-0.0.1-SNAPSHOT.jar ddl -d "./scripts" -c "jdbc:mysql://127.0.0.1:3306/clm?enabledTLSProtocols=TLSv1.2&characterEncoding=utf8&serverTimezone=GMT%2b0&useLegacyDatetimeCode=false" -u root -p 123456
```

### Running the Service


In the `src` directory, run the following command to start the restful service:

```shell
mvn -pl clm-service-rest -am spring-boot:run
```

### Test Application

```shell
curl -X POST "http://localhost:1023/api/Cabinets" -H "accept: application/json" -H "Content-Type: application/json" -d '{"cabinetId":"CabinetId_1","active":true,"commandId":"11234324","description":"Test cabinet"}'
```

