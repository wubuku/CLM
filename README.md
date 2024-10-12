# CLM (Cable Label Management System)


## Prerequisites

Currently, the dddappp low-code tool is published as a Docker image for developers to experience.

The services generated by the tool are written in Java and use the MySQL database by default.
But you can switch to other database like PostgreSQL because we use Hibernate/JPA as the ORM framework.

Before getting started, you need to:

* Install [Docker](https://docs.docker.com/engine/install/).
* Install MySQL database server.
* Install JDK and Maven.

If you have already installed Docker, you can use Docker to run a MySQL database service. For example:

```shell
sudo docker run -p 3306:3306 --name mysql \
-v ~/docker/mysql/conf:/etc/mysql \
-v ~/docker/mysql/logs:/var/log/mysql \
-v ~/docker/mysql/data:/var/lib/mysql \
-e MYSQL_ROOT_PASSWORD=123456 \
-d mysql:5.7
```

> **Tip**
>
> About DDDML, here is an introductory article: ["Introducing DDDML: The Key to Low-Code Development for Decentralized Applications"](https://github.com/wubuku/Dapp-LCDP-Demo/blob/main/IntroducingDDDML.md). 



## Programming

### Write DDDML Model File

In the `dddml` directory in the root of the repository, we have a `CLM.yaml` file. This is the DDDML model file.


### Run dddappp Project Creation Tool


> **Hint**
>
> Sometimes you may need to remove old containers and images, ensure you are using the latest image:
>
> ```shell
> docker rm $(docker ps -aq --filter "ancestor=wubuku/dddappp-java:master")
> docker rmi wubuku/dddappp-java:master
> ```


In repository root directory, run:

```shell
docker run \
-v .:/myapp \
wubuku/dddappp-java:master \
--dddmlDirectoryPath /myapp/dddml \
--boundedContextName Dddml.Clm \
--boundedContextJavaPackageName org.dddml.clm \
--javaProjectsDirectoryPath /myapp/src \
--javaProjectNamePrefix clm \
--pomGroupId dddml.clm
```

The command parameters above are straightforward:

* This line `-v .:/myapp \` indicates mounting the local current directory into the `/myapp` directory inside the container.
* `dddmlDirectoryPath` is the directory where the DDDML model files are located. It should be a directory path that can be read in the container.
* Understand the value of the `boundedContextName` parameter as the name of the application you want to develop. When the name has multiple parts, separate them with dots and use the PascalCase naming convention for each part. Bounded-context is a term in Domain-driven design (DDD) that refers to a specific problem domain scope that contains specific business boundaries, constraints, and language. If you cannot understand this concept for the time being, it is not a big deal.
* `boundedContextJavaPackageName` is the Java package name. According to Java naming conventions, it should be all lowercase and the parts should be separated by dots.
* `javaProjectsDirectoryPath` is the directory path where the code is placed. The service consists of multiple modules (projects). It should be a readable and writable directory path in the container.
* `javaProjectNamePrefix` is the name prefix of each module of the service. It is recommended to use an all-lowercase name.
* `pomGroupId` is the GroupId of the service. We use Maven as the project management tool for service. It should be all lowercase and the parts should be separated by dots.


Now you can try to compile the service. Go to the directory `src` and run:

```shell
mvn compile
```

If there is no unexpected failure, the compilation should be successful.



## Test the Application

### Creating and Initialize Database

注意，下面我们假设使用的数据库名称是 `clm`。
但是在代码刚生成的时候，数据库名称可能并不是 `clm`，而可能是 `test2` 之类。
请搜索代码/配置文件中的数据库连接字符串，并修改为你想要的名称。

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

注意，这时候你可能会碰到执行错误。因为我们在模型中是这样定义 `Tag` 实体（聚合根）的：

```yaml
  Tag:
    metadata:
      CreationWithoutIdEnabled: true
```

这意味着，这个实体的 ID 不是由外部提供的，而是在服务端生成的。因为我们没有在数据访问层指定这个 ID 的生成策略，那么就意味着我们需要在应用层实现这个逻辑。


#### 实现并注入需要的 Id Generator bean

生成 Tag 实体的 ID 的实现请参考 `src/clm-service/src/main/java/org/dddml/clm/tag/TagIdGenerator.java` 文件。

然后参考 `src/clm-service/src/main/java/org/dddml/clm/config/IdGeneratorConfig.java` 文件，注入这个 bean，让应用的其他部分（即需要生成 Tag ID 的地方）能够使用它。

重新运行服务，应该就不会有错误了：

```shell
mvn -pl clm-service-rest -am spring-boot:run
```

#### 文档

在服务运行起来之后，可以在这个地方找到 Swagger 在线文档（假设服务的端口是 1023）：

```text
http://localhost:1023/api/swagger-ui/index.html
```


### First Test


You can use `curl` to test the service. For example:

```shell
curl -X POST "http://localhost:1023/api/Cabinets" -H "accept: application/json" -H "Content-Type: application/json" -d '{"cabinetId":"CabinetId_1","active":true,"commandId":"11234324","description":"Test cabinet"}'
```


## 扩展阅读

### DDDML 介绍

关于 DDDML 的入门介绍：["Introducing DDDML: The Key to Low-Code Development for Decentralized Applications"](https://github.com/wubuku/Dapp-LCDP-Demo/blob/main/IntroducingDDDML.md).

### 面向 MUD 开发者的 DDDML 介绍

如果你想要基于 EVM，特别是 [MUD](https://mud.dev) 框架，开发去中心化应用，可能从这个入门介绍开始了解 DDDML 会更合适：https://github.com/wubuku/Dapp-LCDP-Demo/blob/main/docs/IntroToDDDMLforMudDevelopers_CN.md

### DDDML x AI

AI 辅助开发基于 EVM/MUD 的去中心化应用：https://github.com/wubuku/hello-mud?tab=readme-ov-file#leveraging-ai

AI 辅助开发 AO 去中心化应用：https://github.com/dddappp/AI-Assisted-AO-Dapp-Example/blob/main/README_CN.md

### 将 dddappp 作为全链游戏引擎使用

#### 使用 dddappp 开发 Sui 全链游戏

这个一个生产级的实际案例：https://github.com/wubuku/infinite-sea

#### 用于开发 Aptos 全链游戏的示例

原版的 [constantinople](https://github.com/0xobelisk/constantinople) 是一个基于全链游戏引擎 [obelisk](https://obelisk.build) 开发的运行在 Sui 上的游戏。（注：obelisk 不是我们的项目。）

我们这里尝试了使用 dddappp 低代码开发方式，实现这个游戏的 Aptos Move 版本：https://github.com/wubuku/aptos-constantinople/blob/main/README_CN.md

开发者可以按照 README 的介绍，复现整个游戏的合约和 indexer 的开发和测试过程。模型文件写一下，生成代码，在三个文件里面填了下业务逻辑，开发就完成了。

有个地方可能值得一提。Aptos 对发布的 Move 合约包的大小有限制（不能超过60k）。这个问题在 Aptos 上开发稍微大点的应用都会碰到。我们现在可以在模型文件里面声明一些模块信息，然后就可以自动拆分（生成）多个 Move 合约项目。（注：这里说的模块是指领域模型意义上的模块，不是 Move 语言的那个模块。）


### Sui 博客示例

代码库：https://github.com/dddappp/sui-blog-example

只需要写 30 行左右的代码（全部是领域模型的描述）——除此以外不需要开发者写一行其他代码——就可以一键生成一个博客；
类似 [RoR 入门指南](https://guides.rubyonrails.org/getting_started.html) 的开发体验，

特别是，一行代码都不用写，100% 自动生成的链下查询服务（有时候我们称之为 indexer）即具备很多开箱即用的功能。


### Aptos 博客示例

上面的博客示例的 [Aptos 版本](https://github.com/dddappp/aptos-blog-example)。

### Sui 众筹 Dapp

一个以教学演示为目的“众筹” Dapp：

https://github.com/dddappp/sui-crowdfunding-example

### Move Forms

我们在 Aptos 新加坡黑客马拉松中开发了一个基于 dddappp 的无代码工具侧项目，名为 [Move Forms](https://github.com/dddappp/aptos-forms-demo)，并获得了第二名。

基于我们当前的 Solana PoC 版本，我们已经使社区合作伙伴能够构建一个名为 Solana Forms 的 Web3 原生表单工具。详见：https://github.com/dddappp/solana-forms-demo


### 复杂的 Sui 演示

如果你有进一步了解的兴趣，可以在这里找到一个更复杂的 Sui 演示：["A Sui Demo"](https://github.com/dddappp/A-Sui-Demo)。
我们使用了各种“生造”的场景，来展示 dddappp 的各种能力。

### 低代码开发 AO 应用的 PoC

见：https://github.com/dddappp/A-AO-Demo

### 低代码开发 Solana 应用的 PoC

见：https://github.com/dddappp/A-Solana-Demo

### The Key to AO Ecosystem's Success: Microservices Architecture in the Web3 Era

文章分两部分：

1. https://x.com/ArweaveOasis/status/1808788659828584751
2. https://x.com/ArweaveOasis/status/1815207753654452414

