package fun.xiaorang.mybatis.mapper;

import fun.xiaorang.mybatis.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/10 18:11
 */
public class UserMapperTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserMapperTest.class);

    private static SqlSessionFactory sqlSessionFactory = null;

    @BeforeAll
    public static void beforeAll() {
        try (InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml")) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testSelectUserList() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            List<User> userList = userMapper.selectUserList();
            userList.forEach(user -> LOGGER.info("{}", user));
        }
    }

    @Test
    public void testSelectUsers() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            Map<String, Object> map = new HashMap<>();
            map.put("id", 1);
            map.put("name", "xiao");
            List<User> users = userMapper.selectUsers(map);
            users.forEach(user -> LOGGER.info("{}", user));
        }
    }

    @Test
    public void testSelectUserByIdForFirstCache() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User user = userMapper.selectUserById(1);
            LOGGER.info("{}", user);
            User user2 = userMapper.selectUserById(1);
            LOGGER.info("{}", user2);
            LOGGER.info("再次查询id=1的用户信息是是否是从一级缓存中获取的？{}", user == user2);
        }
    }

    @Test
    public void selectUserByIdForSecondCache() {
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
        User user = userMapper1.selectUserById(1);
        LOGGER.info("user = {}", user);
        sqlSession1.close();
        LOGGER.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        User user2 = userMapper2.selectUserById(1);
        LOGGER.info("user = {}", user2);
        sqlSession2.close();
    }
}
