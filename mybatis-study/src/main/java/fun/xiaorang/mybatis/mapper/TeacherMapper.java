package fun.xiaorang.mybatis.mapper;

import fun.xiaorang.mybatis.pojo.Teacher;

import java.util.List;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/10 21:29
 */
public interface TeacherMapper {
    /**
     * 查询所有的老师
     *
     * @return 老师集合
     */
    List<Teacher> selectTeacherList();
}
