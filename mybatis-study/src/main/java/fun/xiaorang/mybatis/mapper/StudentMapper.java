package fun.xiaorang.mybatis.mapper;

import fun.xiaorang.mybatis.pojo.Student;

import java.util.List;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/10 21:42
 */
public interface StudentMapper {
    /**
     * 查询所有的学生
     *
     * @return 学生集合
     */
    List<Student> selectStudents();
}
