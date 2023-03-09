package fun.xiaorang;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/9 17:57
 */
public class DruidDataSourceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DruidDataSourceTest.class);

    private static DataSource dataSource = null;

    @BeforeAll
    public static void before() {
        try {
            Properties properties = new Properties();
            properties.load(DruidDataSourceTest.class.getResourceAsStream("/druid.properties"));
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAdd() {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO `user`(`name`, `age`, `birthday`, `salary`, `note`) VALUES('小让', 18, '1995-07-13', 16000.0, '程序员');";
            int count = statement.executeUpdate(sql);
            LOGGER.info("【数据更新行数】：{}", count);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
