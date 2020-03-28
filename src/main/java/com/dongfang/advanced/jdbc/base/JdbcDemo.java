package com.dongfang.advanced.jdbc.base;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Java为关系数据库定义了一套标准的访问接口：JDBC（Java Database Connectivity）
 *
 * 使用Java程序访问数据库时，Java代码并不是直接通过TCP连接去访问数据库，而是通过JDBC接口来访问，
 * 而JDBC接口则通过JDBC驱动来实现真正对数据库的访问。
 *
 * 例如，我们在Java代码中如果要访问MySQL，那么必须编写代码操作JDBC接口。注意到JDBC接口是Java标准库自带的，
 * 所以可以直接编译。而具体的JDBC驱动是由数据库厂商提供的，例如，MySQL的JDBC驱动由Oracle提供。因此，访问某个具体的数据库，
 * 我们只需要引入该厂商提供的JDBC驱动，就可以通过JDBC接口来访问，这样保证了Java程序编写的是一套数据库访问代码，
 * 却可以访问各种不同的数据库，因为他们都提供了标准的JDBC驱动：
 *
 * ┌ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┐
 *
 * │  ┌───────────────┐  │
 *    │   Java App    │
 * │  └───────────────┘  │
 *            │
 * │          ▼          │
 *    ┌───────────────┐
 * │  │JDBC Interface │<─┼─── JDK
 *    └───────────────┘
 * │          │          │
 *            ▼
 * │  ┌───────────────┐  │
 *    │  JDBC Driver  │<───── Vendor
 * │  └───────────────┘  │
 *            │
 * └ ─ ─ ─ ─ ─│─ ─ ─ ─ ─ ┘
 *            ▼
 *    ┌───────────────┐
 *    │   Database    │
 *    └───────────────┘
 *
 * 从代码来看，Java标准库自带的JDBC接口其实就是定义了一组接口，而某个具体的JDBC驱动其实就是实现了这些接口的类：
 *
 * ┌ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┐
 *
 * │  ┌───────────────┐  │
 *    │   Java App    │
 * │  └───────────────┘  │
 *            │
 * │          ▼          │
 *    ┌───────────────┐
 * │  │JDBC Interface │<─┼─── JDK
 *    └───────────────┘
 * │          │          │
 *            ▼
 * │  ┌───────────────┐  │
 *    │ MySQL Driver  │<───── Oracle
 * │  └───────────────┘  │
 *            │
 * └ ─ ─ ─ ─ ─│─ ─ ─ ─ ─ ┘
 *            ▼
 *    ┌───────────────┐
 *    │     MySQL     │
 *    └───────────────┘
 * 实际上，一个MySQL的JDBC的驱动就是一个jar包，它本身也是纯Java编写的。
 * 我们自己编写的代码只需要引用Java标准库提供的java.sql包下面的相关接口，
 * 由此再间接地通过MySQL驱动的jar包通过网络访问MySQL服务器，所有复杂的网络通讯都被封装到JDBC驱动中，
 * 因此，Java程序本身只需要引入一个MySQL驱动的jar包就可以正常访问MySQL服务器：
 *
 * ┌ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┐
 *    ┌───────────────┐
 * │  │   App.class   │  │
 *    └───────────────┘
 * │          │          │
 *            ▼
 * │  ┌───────────────┐  │
 *    │  java.sql.*   │
 * │  └───────────────┘  │
 *            │
 * │          ▼          │
 *    ┌───────────────┐     TCP    ┌───────────────┐
 * │  │ mysql-xxx.jar │──┼────────>│     MySQL     │
 *    └───────────────┘            └───────────────┘
 * └ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘
 *           JVM
 * 小结
 * 使用JDBC的好处是：
 *
 * 各数据库厂商使用相同的接口，Java代码不需要针对不同数据库分别开发；
 *
 * Java程序编译期仅依赖java.sql包，不依赖具体数据库的jar包；
 *
 * 可随时替换底层数据库，访问数据库的Java代码基本不变。
 */
public class JdbcDemo {
    private Connection connection;

    @Ignore
    @Before
    public void prepare() throws SQLException {
        // 获取连接:
        /*String JDBC_PASSWORD = "123456";
        String JDBC_USER = "root";
        // JDBC连接的URL, 不同数据库有不同的格式:
        String JDBC_URL = "jdbc:mysql://localhost:3306/test";
        connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);*/
    }

    @Ignore
    @After
    public void after() throws SQLException {
        // connection.close();
    }

    @Test
    public void testConnection() {
        System.out.println("connection = " + connection);
    }

    @Test
    public void testJdbcSelect() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select id, grade, name, gender from students where gender = \'M\' ");
        while (resultSet.next()) {
            System.out.println("resultSet.getLong(1) = " + resultSet.getLong(1));
            System.out.println("resultSet.getLong(2) = " + resultSet.getLong(2));
            System.out.println("resultSet.getString(3) = " + resultSet.getString(3));
            System.out.println("resultSet.getString(4) = " + resultSet.getString(4));
        }
        statement.close();
        resultSet.close();
    }

    @Test
    public void testPrepareSql() throws SQLException {
        final PreparedStatement ps = connection.prepareStatement("select id, grade, name, gender from students where gender = ? and grade = ?");
        ps.setObject(1, "M");
        ps.setObject(2, 3);
        final ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            System.out.println("resultSet.getLong(\"id\") = " + resultSet.getLong("id"));
            System.out.println("resultSet.getLong(\"grade\") = " + resultSet.getLong("grade"));
            System.out.println("resultSet.getString(\"name\") = " + resultSet.getString("name"));
            System.out.println("resultSet.getString(\"gender\") = " + resultSet.getString("gender"));
        }
    }

    @Test
    public void testInsert() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO students (id, grade, name, gender, score) VALUES (?, ?, ?, ?, ?)");

        ps.setObject(1, 999); // 注意：索引从1开始
        ps.setObject(2, 1); // grade
        ps.setObject(3, "Bob"); // name
        ps.setObject(4, 1); // gender
        ps.setObject(5, 99);
        int affectedRows = ps.executeUpdate(); // 1
        System.out.println("affectedRows = " + affectedRows);
    }

    /**
     * 如果数据库的表设置了自增主键，那么在执行INSERT语句时，并不需要指定主键，数据库会自动分配主键。对于使用自增主键的程序，有个额外的步骤，就是如何获取插入后的自增主键的值。
     *
     * 要获取自增主键，不能先插入，再查询。因为两条SQL执行期间可能有别的程序也插入了同一个表。获取自增主键的正确写法是在创建PreparedStatement的时候，
     * 指定一个RETURN_GENERATED_KEYS标志位，表示JDBC驱动必须返回插入的自增主键。
     *
     * 观察上述代码，有两点注意事项：
     *
     * 一是调用prepareStatement()时，第二个参数必须传入常量Statement.RETURN_GENERATED_KEYS，否则JDBC驱动不会返回自增主键；
     *
     * 二是执行executeUpdate()方法后，必须调用getGeneratedKeys()获取一个ResultSet对象，这个对象包含了数据库自动生成的主键的值，读取该对象的每一行来获取自增主键的值。如果一次插入多条记录，
     * 那么这个ResultSet对象就会有多行返回值。如果插入时有多列自增，那么ResultSet对象的每一行都会对应多个自增值（自增列不一定必须是主键）。
     *
     * @throws SQLException
     */
    @Test
    public void testGetPrimaryKey() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO students (grade, name, gender, score) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

        ps.setObject(1, 1); // grade
        ps.setObject(2, "dong fang"); // name
        ps.setObject(3, 1); // gender
        ps.setObject(4, 99);
        int affectedRows = ps.executeUpdate(); // 1
        System.out.println("affectedRows = " + affectedRows);
        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                long id = rs.getLong(1); // 注意：索引从1开始
                System.out.println("id = " + id);
            }
        }
    }

    @Test
    public void testUpdate() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE students SET name = ? WHERE id = ?");

        ps.setObject(1, "Bob"); // 注意：索引从1开始
        ps.setObject(2, 999);
        int affectedRows = ps.executeUpdate(); // 1
        System.out.println("affectedRows = " + affectedRows);
    }

    @Test
    public void testDelete() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM students WHERE id = ?");
        ps.setObject(1, 999); // 注意：索引从1开始
        int affectedRows = ps.executeUpdate(); // 删除的行数
        System.out.println("affectedRows = " + affectedRows);
    }

    /**
     * 数据库事务（Transaction）具有ACID特性：
     *
     * Atomicity：原子性
     * Consistency：一致性
     * Isolation：隔离性
     * Durability：持久性
     * JDBC提供了事务的支持，使用Connection可以开启、提交或回滚事务。
     *
     *
     * 要在JDBC中执行事务，本质上就是如何把多条SQL包裹在一个数据库事务中执行。我们来看JDBC的事务代码：
     *
     * 其中，开启事务的关键代码是conn.setAutoCommit(false)，表示关闭自动提交。提交事务的代码在执行完指定的若干条SQL语句后，调用conn.commit()。
     * 要注意事务不是总能成功，如果事务提交失败，会抛出SQL异常（也可能在执行SQL语句的时候就抛出了），此时我们必须捕获并调用conn.rollback()回滚事务。
     * 最后，在finally中通过conn.setAutoCommit(true)把Connection对象的状态恢复到初始值。
     *
     * 实际上，默认情况下，我们获取到Connection连接后，总是处于“自动提交”模式，也就是每执行一条SQL都是作为事务自动执行的，
     * 这也是为什么前面几节我们的更新操作总能成功的原因：因为默认有这种“隐式事务”。只要关闭了Connection的autoCommit，那么就可以在一个事务中执行多条语句，事务以commit()方法结束。
     *
     * 如果要设定事务的隔离级别，可以使用如下代码：
     *
     * // 设定隔离级别为READ COMMITTED:
     * connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
     * 如果没有调用上述方法，那么会使用数据库的默认隔离级别。MySQL的默认隔离级别是REPEATABLE READ。
     * @throws SQLException
     */
    @Test
    public void testTransaction() throws SQLException {
        try {
            //connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            // 关闭自动提交:
            connection.setAutoCommit(false);
            // 执行多条SQL语句:
            //insert(); update(); delete();
            // 提交事务:
            connection.commit();
        } catch (SQLException e) {
            // 回滚事务:
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    /**
     * 使用JDBC操作数据库的时候，经常会执行一些批量操作。
     *
     * INSERT INTO coupons (user_id, type, expires) VALUES (123, 'DISCOUNT', '2030-12-31');
     * INSERT INTO coupons (user_id, type, expires) VALUES (234, 'DISCOUNT', '2030-12-31');
     * INSERT INTO coupons (user_id, type, expires) VALUES (345, 'DISCOUNT', '2030-12-31');
     * INSERT INTO coupons (user_id, type, expires) VALUES (456, 'DISCOUNT', '2030-12-31');
     *
     * 实际上执行JDBC时，因为只有占位符参数不同，所以SQL实际上是一样的：
     *
     * for (var params : paramsList) {
     *     PreparedStatement ps = conn.preparedStatement("INSERT INTO coupons (user_id, type, expires) VALUES (?,?,?)");
     *     ps.setLong(params.get(0));
     *     ps.setString(params.get(1));
     *     ps.setString(params.get(2));
     *     ps.executeUpdate();
     * }
     *
     * 通过一个循环来执行每个PreparedStatement虽然可行，但是性能很低。SQL数据库对SQL语句相同，
     * 但只有参数不同的若干语句可以作为batch执行，即批量执行，这种操作有特别优化，速度远远快于循环执行每个SQL
     *
     *在JDBC代码中，我们可以利用SQL数据库的这一特性，把同一个SQL但参数不同的若干次操作合并为一个batch执行。我们以批量插入为例，示例代码如下
     *
     * 执行batch和执行一个SQL不同点在于，需要对同一个PreparedStatement反复设置参数并调用addBatch()，这样就相当于给一个SQL加上了多组参数，相当于变成了“多行”SQL。
     *
     * 第二个不同点是调用的不是executeUpdate()，而是executeBatch()，因为我们设置了多组参数，相应地，返回结果也是多个int值，
     * 因此返回类型是int[]，循环int[]数组即可获取每组参数执行后影响的结果数量。
     */
    @Test
    public void testBatchUpdate() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO students (name, gender, grade, score) VALUES (?, ?, ?, ?)");
        // 对同一个PreparedStatement反复设置参数并调用addBatch():
        /**
         * for (String name : names) {
            ps.setString(1, name);
            ps.setBoolean(2, gender);
            ps.setInt(3, grade);
            ps.setInt(4, score);
            ps.addBatch(); // 添加到batch
        }*/
        // 执行batch:
        int[] ns = ps.executeBatch();
        for (int n : ns) {
            System.out.println(n + " inserted."); // batch中每个SQL执行的结果数量
        }
    }

    /**
     * 创建线程是一个昂贵的操作，如果有大量的小任务需要执行，并且频繁地创建和销毁线程，实际上会消耗大量的系统资源，
     * 往往创建和消耗线程所耗费的时间比执行任务的时间还长，所以，为了提高效率，可以用线程池。
     *
     * 类似的，在执行JDBC的增删改查的操作时，如果每一次操作都来一次打开连接，操作，关闭连接，
     * 那么创建和销毁JDBC连接的开销就太大了。为了避免频繁地创建和销毁JDBC连接，我们可以通过连接池（Connection Pool）复用已经创建好的连接。
     *
     * JDBC连接池有一个标准的接口javax.sql.DataSource，注意这个类位于Java标准库中，但仅仅是接口。
     * 要使用JDBC连接池，我们必须选择一个JDBC连接池的实现。常用的JDBC连接池有：
     *
     * HikariCP
     * C3P0
     * BoneCP
     * Druid
     *
     * 注意创建DataSource也是一个非常昂贵的操作，所以通常DataSource实例总是作为一个全局变量存储，并贯穿整个应用程序的生命周期。
     *
     * 有了连接池以后，我们如何使用它呢？和前面的代码类似，只是获取Connection时，把DriverManage.getConnection()改为ds.getConnection()：
     *
     * 通过连接池获取连接时，并不需要指定JDBC的相关URL、用户名、口令等信息，因为这些信息已经存储在连接池内部了
     * （创建HikariDataSource时传入的HikariConfig持有这些信息）。一开始，连接池内部并没有连接，
     * 所以，第一次调用ds.getConnection()，会迫使连接池内部先创建一个Connection，再返回给客户端使用。
     * 当我们调用conn.close()方法时（在try(resource){...}结束处），不是真正“关闭”连接，而是释放到连接池中，以便下次获取连接时能直接返回。
     *
     * 因此，连接池内部维护了若干个Connection实例，如果调用ds.getConnection()，就选择一个空闲连接，
     * 并标记它为“正在使用”然后返回，如果对Connection调用close()，那么就把连接再次标记为“空闲”从而等待下次调用。
     * 这样一来，我们就通过连接池维护了少量连接，但可以频繁地执行大量的SQL语句。
     *
     * 通常连接池提供了大量的参数可以配置，例如，维护的最小、最大活动连接数，指定一个连接在空闲一段时间后自动关闭等，
     * 需要根据应用程序的负载合理地配置这些参数。此外，大多数连接池都提供了详细的实时状态以便进行监控。
     * @throws SQLException
     */
    @Test
    public void testConnectionPool() throws SQLException {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/test?serverTimezone=UTC");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("123456");
        hikariConfig.addDataSourceProperty("connectionTimeout", "1000"); // 连接超时：1秒
        hikariConfig.addDataSourceProperty("idleTimeout", "60000"); // 空闲超时：60秒
        hikariConfig.addDataSourceProperty("maximumPoolSize", "10"); // 最大连接数：10

        DataSource dataSource = new HikariDataSource(hikariConfig);
        Connection connection = dataSource.getConnection();
        System.out.println("connection = " + connection);
        PreparedStatement ps = connection.prepareStatement("create database learnjdbc");
        boolean ans = ps.execute();
        System.out.println("ans = " + ans);
    }

}