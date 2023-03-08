package fun.xiaorang;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/7 23:58
 */
public class Lambda4 {
    /**
     * 静态变量
     */
    static int outerStaticNum;
    /**
     * 成员变量
     */
    int outerNum;

    /**
     * 测试 Lambda 表达式中对成员变量和静态变量拥有读写权限
     */
    void testScopes() {
        IConverter<Integer, String> stringConverter1 = from -> {
            // 给成员变量赋值 => 拥有对成员变量写的权限
            outerNum = 23;
            return String.valueOf(from + outerNum); // => 拥有对成员变量读的权限
        };

        IConverter<Integer, String> stringConverter2 = from -> {
            // 给静态变量赋值  => 拥有对静态变量写的权限
            outerStaticNum = 72;
            return String.valueOf(from + outerStaticNum); // => 拥有对静态变量读的权限
        };
    }
}
