package fun.xiaorang;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/9 14:49
 */
public class JdbcTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcTest.class);
    private static Connection CONNECTION = null;

    @BeforeEach
    public void before() {
        try (InputStream inputStream = JdbcTest.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            String url = properties.getProperty("jdbc.url");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");
            CONNECTION = DriverManager.getConnection(url, username, password);
            LOGGER.info("【建立数据库连接】：{}", CONNECTION);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void after() {
        if (CONNECTION != null) {
            try {
                CONNECTION.close();
                LOGGER.info("【关闭数据库连接】：{}", CONNECTION);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    public void testConnection() {
        // 用于测试数据库连接是否生效
    }

    @Test
    public void testAdd() throws SQLException {
        Statement statement = CONNECTION.createStatement();
        String sql = "INSERT INTO `user`(`name`, `age`, `birthday`, `salary`, `note`) VALUES('小让', 18, '1995-07-13', 16000.0, '程序员');";
        int count = statement.executeUpdate(sql);
        LOGGER.info("【数据更新行数】：{}", count);
    }

    @Test
    public void testDelete() throws SQLException {
        Statement statement = CONNECTION.createStatement();
        String sql = "DELETE FROM `user` WHERE uid = 1";
        int count = statement.executeUpdate(sql);
        LOGGER.info("【数据更新行数】：{}", count);
    }

    @Test
    public void testQuery() throws SQLException {
        Statement statement = CONNECTION.createStatement();
        String sql = "SELECT * FROM `user`";
        try (ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                int uid = rs.getInt("uid");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                Date birthday = rs.getDate("birthday");
                float salary = rs.getFloat("salary");
                String note = rs.getString("note");
                User user = new User(uid, name, age, birthday, salary, note);
                LOGGER.info("{}", user);
            }
        }
    }

    @Test
    public void testSQLInjection() throws SQLException {
        Statement statement = CONNECTION.createStatement();
        String username = "'小白' or 1 = 1";
        String sql = "SELECT * FROM `user` WHERE `name` = " + username;
        try (ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                int uid = rs.getInt("uid");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                Date birthday = rs.getDate("birthday");
                float salary = rs.getFloat("salary");
                String note = rs.getString("note");
                User user = new User(uid, name, age, birthday, salary, note);
                LOGGER.info("{}", user);
            }
        }
    }

    @Test
    public void testPreparedStatementAdd() throws SQLException {
        String sql = "INSERT INTO `user`(`name`, `age`, `birthday`, `salary`, `note`) VALUES(?, ?, ?, ?, ?);";
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setString(1, "小白");
            preparedStatement.setInt(2, 18);
            preparedStatement.setDate(3, new Date(new java.util.Date().getTime()));
            preparedStatement.setFloat(4, 18000.0f);
            preparedStatement.setString(5, "销售");
            int count = preparedStatement.executeUpdate();
            LOGGER.info("【数据更新行数】：{}", count);
        }
    }

    @Test
    public void testPreparedStatementDelete() throws SQLException {
        String sql = "DELETE FROM `user` WHERE uid = ?;";
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setInt(1, 1);
            int count = preparedStatement.executeUpdate();
            LOGGER.info("【数据更新行数】：{}", count);
        }
    }

    @Test
    public void testPreparedStatementQuery() throws SQLException {
        String sql = "SELECT * FROM `user` WHERE `name` like ?;";
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setString(1, "%%");
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    int uid = rs.getInt("uid");
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    Date birthday = rs.getDate("birthday");
                    float salary = rs.getFloat("salary");
                    String note = rs.getString("note");
                    User user = new User(uid, name, age, birthday, salary, note);
                    LOGGER.info("{}", user);
                }
            }
        }
    }

    @Test
    public void testPreparedStatementSQLInjection() throws SQLException {
        String sql = "SELECT * FROM `user` WHERE `name` = ?";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql);
        preparedStatement.setString(1, "'小白' or 1 = 1");
        try (ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                int uid = rs.getInt("uid");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                Date birthday = rs.getDate("birthday");
                float salary = rs.getFloat("salary");
                String note = rs.getString("note");
                User user = new User(uid, name, age, birthday, salary, note);
                LOGGER.info("{}", user);
            }
        }
    }

    @Test
    public void testSupportsBatchUpdates() throws SQLException {
        DatabaseMetaData databaseMetaData = CONNECTION.getMetaData();
        boolean supportsBatchUpdates = databaseMetaData.supportsBatchUpdates();
        LOGGER.info("是否支持批处理？{}", supportsBatchUpdates);
    }

    @Test
    public void testPreparedStatementBatchAdd() {
        try {
            CONNECTION.setAutoCommit(false);
            String sql = "INSERT INTO `user`(`name`, `age`, `birthday`, `salary`, `note`) VALUES(?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
                for (int i = 0; i < 5; i++) {
                    preparedStatement.setString(1, "小白" + i);
                    preparedStatement.setInt(2, 18);
                    preparedStatement.setDate(3, new Date(new java.util.Date().getTime()));
                    preparedStatement.setFloat(4, 18000.0f);
                    preparedStatement.setString(5, "销售");
                    preparedStatement.addBatch();
                }
                int[] counts = preparedStatement.executeBatch();
                CONNECTION.commit();
                LOGGER.info("【数据更新行数】：{}", counts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Test
    public void testPreparedStatementBatchAdd2() {
        try {
            CONNECTION.setAutoCommit(false);
            String sql = "INSERT INTO `user`(`name`, `age`, `birthday`, `salary`, `note`) VALUES(?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
                for (int i = 1; i <= 1000000; i++) {
                    preparedStatement.setString(1, "小白" + i);
                    preparedStatement.setInt(2, 18);
                    preparedStatement.setDate(3, new Date(new java.util.Date().getTime()));
                    preparedStatement.setFloat(4, 18000.0f);
                    preparedStatement.setString(5, "销售");
                    preparedStatement.addBatch();
                }
                int[] counts = preparedStatement.executeBatch();
                CONNECTION.commit();
                LOGGER.info("【数据更新行数】：{}", counts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Test
    public void testPreparedStatementBatchAdd3() {
        long start = System.currentTimeMillis();
        try {
            CONNECTION.setAutoCommit(false);
            String sql = "INSERT INTO `user`(`name`, `age`, `birthday`, `salary`, `note`) VALUES(?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
                for (int i = 1; i < 1000000; i++) {
                    preparedStatement.setString(1, "小白" + i);
                    preparedStatement.setInt(2, 18);
                    preparedStatement.setDate(3, new Date(new java.util.Date().getTime()));
                    preparedStatement.setFloat(4, 18000.0f);
                    preparedStatement.setString(5, "销售");
                    preparedStatement.addBatch();
                    if (i % 500 == 0) {
                        preparedStatement.executeBatch();
                        preparedStatement.clearBatch();
                    }
                }
                preparedStatement.clearBatch();
                CONNECTION.commit();
                LOGGER.info("百万条数据插入用时：{}【单位：毫秒】", (System.currentTimeMillis() - start));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Test
    public void testTransferNonTransaction() {
        String sql1 = "UPDATE `user` SET `salary` = `salary` - ? WHERE `uid` = ?;";
        String sql2 = "UPDATE `user` SET `salary` = `salary` + ? WHERE `uid` = ?;";
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql1);
             PreparedStatement preparedStatement2 = CONNECTION.prepareStatement(sql2)) {
            preparedStatement.setFloat(1, 1000.0f);
            preparedStatement.setInt(2, 1);
            preparedStatement.executeUpdate();
            int i = 1 / 0;
            preparedStatement2.setFloat(1, 1000.0f);
            preparedStatement2.setInt(2, 2);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testTransferWithTransaction() {
        try {
            CONNECTION.setAutoCommit(false);
            String sql1 = "UPDATE `user` SET `salary` = `salary` - ? WHERE `uid` = ?;";
            String sql2 = "UPDATE `user` SET `salary` = `salary` + ? WHERE `uid` = ?;";
            try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql1);
                 PreparedStatement preparedStatement2 = CONNECTION.prepareStatement(sql2)) {
                preparedStatement.setFloat(1, 1000.0f);
                preparedStatement.setInt(2, 1);
                preparedStatement.executeUpdate();
                int i = 1 / 0;
                preparedStatement2.setFloat(1, 1000.0f);
                preparedStatement2.setInt(2, 2);
                preparedStatement.executeUpdate();
                CONNECTION.commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
