package fun.xiaorang.mybatis.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/10 18:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    /**
     * 主键列
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 密码
     */
    private String password;
}
