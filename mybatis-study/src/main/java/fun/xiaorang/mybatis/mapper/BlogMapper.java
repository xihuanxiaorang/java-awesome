package fun.xiaorang.mybatis.mapper;

import fun.xiaorang.mybatis.pojo.Blog;

import java.util.List;
import java.util.Map;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/10 21:55
 */
public interface BlogMapper {
    /**
     * 根据条件查询所有的博客列表
     *
     * @param params 查询条件
     * @return 博客列表
     */
    List<Blog> findBlogs(Map<String, Object> params);

    /**
     * 根据条件更新博客信息
     *
     * @param params 条件
     */
    void updateBlog(Map<String, Object> params);

    /**
     * 根据条件查询所有的博客列表
     *
     * @param params 查询条件
     * @return 博客列表
     */
    List<Blog> queryBlogChoose(Map<String, Object> params);

    /**
     * 根据主键集合查询博客列表
     *
     * @param ids 主键集合
     * @return 博客列表
     */
    List<Blog> findBlogsByIds(List<String> ids);
}
