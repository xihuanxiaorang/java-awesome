package fun.xiaorang;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; ">员工<p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/8 15:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private int age;
    /**
     * 工资
     */
    private double salary;
    /**
     * 性别（0：男，1：女，2：其他）
     */
    private int sex;
    /**
     * 部门
     */
    private String dept;
}
