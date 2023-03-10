package fun.xiaorang.mybatis.mapper;

import fun.xiaorang.mybatis.pojo.Blog;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/10 21:59
 */
class BlogMapperTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentMapperTest.class);

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
    public void testFindBlogs() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            BlogMapper blogMapper = session.getMapper(BlogMapper.class);
            Map<String, Object> map = new HashMap<>();
            map.put("title", "vue如此简单");
            map.put("author", "xiaohong");
            List<Blog> blogList = blogMapper.findBlogs(map);
            blogList.forEach(blog -> LOGGER.info("{}", blog));
        }
    }

    @Test
    public void testUpdateBlog() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            BlogMapper blogMapper = session.getMapper(BlogMapper.class);
            Map<String, Object> map = new HashMap<>();
            map.put("title", "vue如此简单");
            map.put("author", "xiaohong");
            map.put("id", "c8eac9e8-dd38-8d02-367c-701077bef4a5");
            blogMapper.updateBlog(map);
        }
    }

    @Test
    public void testQueryBlogChoose() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            BlogMapper blogMapper = session.getMapper(BlogMapper.class);
            Map<String, Object> map = new HashMap<>();
//            map.put("title", "vue如此简单");
//            map.put("author", "xiaohong");
            List<Blog> blogList = blogMapper.queryBlogChoose(map);
            blogList.forEach(blog -> LOGGER.info("{}", blog));
        }
    }

    @Test
    public void testFindBlogsByIds() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            BlogMapper blogMapper = session.getMapper(BlogMapper.class);
            List<String> ids = new ArrayList<>(Arrays.asList("95231459-b135-9ca3-386a-24992d5e2c72", "69824e2e-8048-0bf4-3477-0e04299be4a9"));
            List<Blog> blogList = blogMapper.findBlogsByIds(ids);
            blogList.forEach(blog -> LOGGER.info("{}", blog));
        }
    }
}