package fun.xiaorang.mybatis.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/10 21:26
 */
@Data
public class Teacher {
    /**
     * 主键列
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 一对多和多对一的关系只选一种方式维护即可，这里是为了学习，所以才在teacher中维护students集合的同时，又在student中维护teacher
     */
    private List<Student> students;
}
