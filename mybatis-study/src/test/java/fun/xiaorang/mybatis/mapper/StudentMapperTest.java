package fun.xiaorang.mybatis.mapper;

import fun.xiaorang.mybatis.pojo.Student;
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
import java.util.List;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/10 21:46
 */
class StudentMapperTest {
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
    public void testSelectUserList() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            StudentMapper studentMapper = session.getMapper(StudentMapper.class);
            List<Student> studentList = studentMapper.selectStudents();
            studentList.forEach(student -> LOGGER.info("{}", student));
        }
    }
}