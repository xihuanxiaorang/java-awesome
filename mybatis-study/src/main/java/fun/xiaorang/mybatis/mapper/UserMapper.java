package fun.xiaorang.mybatis.mapper;

import fun.xiaorang.mybatis.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/10 18:15
 */
public interface UserMapper {
    /**
     * 查询所有的用户
     *
     * @return 用户集合
     */
    List<User> selectUserList();

    /**
     * 根据条件查询用户
     *
     * @param params 查询条件
     * @return 用户集合
     */
    List<User> selectUsers(Map<String, Object> params);

    /**
     * 根据id查询用户
     *
     * @param id id
     * @return 用户
     */
    User selectUserById(int id);
}
