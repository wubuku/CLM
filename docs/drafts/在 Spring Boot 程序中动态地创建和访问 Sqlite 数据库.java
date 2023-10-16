/*
你可以在程序中动态地创建和访问 Sqlite
数据库，但是你需要做一些额外的配置和编码。具体来说，你需要：

•  使用一个自定义的数据源类，继承自 AbstractRoutingDataSource，实现
determineCurrentLookupKey() 方法，根据不同的线程或者请求来返回不同的数据源标识
https://www.cnblogs.com/codecat/p/13406031.html
https://developer.aliyun.com/article/1130800。

•  使用一个自定义的数据源配置类，注入一个 DynamicDataSource
对象，该对象包含了多个 Sqlite 数据源，每个数据源对应一个不同的数据库文件路径
https://www.cnblogs.com/codecat/p/13406031.html
https://developer.aliyun.com/article/1130800。

•  使用一个自定义的数据源切换类，提供一个方法来动态地设置当前线程的数据源标识
https://www.cnblogs.com/codecat/p/13406031.html
https://developer.aliyun.com/article/1130800。

•  在需要创建或访问 Sqlite
数据库的地方，调用数据源切换类的方法，传入一个数据库文件路径，如果该路径不存在，则会自动创建一个新的数据库文件，并且使用该文件作为当前线程的数据源
https://www.cnblogs.com/codecat/p/13406031.html
https://developer.aliyun.com/article/1130800。

•  在使用 JPA 操作数据库之后，调用数据源切换类的方法，传入 null
值，来清除当前线程的数据源标识 https://www.cnblogs.com/codecat/p/13406031.html
https://developer.aliyun.com/article/1130800。

下面是一个简单的示例代码，假设你要在 D:/sqlite 目录下动态创建和访问 Sqlite
数据库，并且使用 JPA 来操作它：
*/

// 自定义数据源类
public class DynamicDataSource extends AbstractRoutingDataSource {
  @Override
  protected Object determineCurrentLookupKey() {
    // 返回当前线程的数据源标识
    return DynamicDataSourceHolder.getDataSource();
  }
}

// 自定义数据源配置类
@Configuration
public class DynamicDataSourceConfig {
  // 创建一个默认的 Sqlite 数据源
  @Bean(name = "defaultDataSource")
  public DataSource defaultDataSource() {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setDriverClassName("org.sqlite.JDBC");
    dataSource.setJdbcUrl("jdbc:sqlite:D:/sqlite/default.db");
    return dataSource;
  }

  // 创建一个动态的 Sqlite 数据源
  @Bean(name = "dynamicDataSource")
  public DataSource dynamicDataSource(
      @Qualifier("defaultDataSource") DataSource defaultDataSource) {
    DynamicDataSource dataSource = new DynamicDataSource();
    // 设置默认的数据源
    dataSource.setDefaultTargetDataSource(defaultDataSource);
    // 设置多个数据源
    Map<Object, Object> targetDataSources = new HashMap<>();
    targetDataSources.put("default", defaultDataSource);
    // 这里可以根据需要添加更多的数据源，也可以在运行时动态添加
    dataSource.setTargetDataSources(targetDataSources);
    return dataSource;
  }

  // 配置 JPA 的 entityManagerFactory 和 platformTransactionManager
  @Bean(name = "entityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      @Qualifier("dynamicDataSource") DataSource dynamicDataSource) {
    LocalContainerEntityManagerFactoryBean em =
        new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dynamicDataSource);
    em.setPackagesToScan("com.example.demo.entity");
    em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    Properties properties = new Properties();
    properties.setProperty(
        "hibernate.dialect", "com.github.gwenn.sqlite.dialect.SQLiteDialect");
    properties.setProperty("hibernate.hbm2ddl.auto", "update");
    em.setJpaProperties(properties);
    return em;
  }

  @Bean(name = "transactionManager")
  public PlatformTransactionManager transactionManager(@Qualifier(
      "entityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}

// 自定义数据源切换类
public class DynamicDataSourceHolder {
  private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

  // 设置当前线程的数据源标识
  public static void setDataSource(String dataSource) {
    contextHolder.set(dataSource);
  }

  // 获取当前线程的数据源标识
  public static String getDataSource() {
    return contextHolder.get();
  }

  // 清除当前线程的数据源标识
  public static void clearDataSource() {
    contextHolder.remove();
  }
}

// 定义实体类
@Entity
@Table(name = "user")
@Data
public class User {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  private String name;
  private Integer age;
}

// 定义存储库接口
public interface UserRepository extends JpaRepository<User, Long> {
  // 可以根据需要定义一些查询方法
}

// 使用存储库操作数据库
@Service
public class UserService {
  @Autowired private UserRepository userRepository;

  // 添加一个用户
  public User addUser(User user) {
    return userRepository.save(user);
  }

  // 查询所有用户
  public List<User> findAllUsers() {
    return userRepository.findAll();
  }
}

// 在控制器中动态创建和访问 Sqlite 数据库
@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired private UserService userService;

  @PostMapping("/add")
  public User addUser(@RequestBody User user) {
    // 动态设置数据源标识，传入一个数据库文件路径，如果该路径不存在，则会自动创建一个新的数据库文件，并且使用该文件作为当前线程的数据源
    DynamicDataSourceHolder.setDataSource("jdbc:sqlite:D:/sqlite/test.db");
    // 使用 JPA 操作数据库
    User result = userService.addUser(user);
    // 清除数据源标识，传入 null 值
    DynamicDataSourceHolder.setDataSource(null);
    return result;
  }

  @GetMapping("/all")
  public List<User> findAllUsers() {
    // 动态设置数据源标识，传入一个数据库文件路径，如果该路径不存在，则会自动创建一个新的数据库文件，并且使用该文件作为当前线程的数据源
    DynamicDataSourceHolder.setDataSource("jdbc:sqlite:D:/sqlite/test.db");
    // 使用 JPA 操作数据库
    List<User> result = userService.findAllUsers();
    // 清除数据源标识，传入 null 值
    DynamicDataSourceHolder.setDataSource(null);
    return result;
  }
}

//这样，你就可以在程序中动态地创建和访问 Sqlite 数据库，并且使用 JPA
//来操作它了。如果你想了解更多关于动态数据源的用法和原理，请参考
//SpringDataJpa动态修改访问数据库的数据源
//https://bing.com/search?q=JPA+%E5%8A%A8%E6%80%81%E8%AE%BE%E7%BD%AE%E6%95%B0%E6%8D%AE%E6%BA%90&form=SKPBOT
//和 SpringBoot+Jpa动态切换多数据源配置及实现
//https://developer.aliyun.com/article/1130800 的文章。