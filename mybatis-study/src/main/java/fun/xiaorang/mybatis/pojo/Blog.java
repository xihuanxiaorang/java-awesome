package fun.xiaorang.mybatis.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/10 21:54
 */
@Data
public class Blog implements Serializable {
    /**
     * 主键列
     */
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 作者
     */
    private String author;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 阅读数量
     */
    private int views;
}
