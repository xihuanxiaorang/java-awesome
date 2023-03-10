package fun.xiaorang.mybatis.pojo;

import lombok.Data;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/10 21:27
 */
@Data
public class Student {
    /**
     * 主键列
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 与老师的对应关系是多对一
     */
    private Teacher teacher;
}
