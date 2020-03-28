package com.dongfang.advanced.jdbc.base;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcDemo {
    private Connection connection;

    @Before
    public void prepare() throws SQLException {
        // 获取连接:
        String JDBC_PASSWORD = "123456";
        String JDBC_USER = "root";
        // JDBC连接的URL, 不同数据库有不同的格式:
        String JDBC_URL = "jdbc:mysql://localhost:3306/learnjdbc";
        connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }


    @After
    public void after() throws SQLException {
        connection.close();
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
}