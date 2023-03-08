package fun.xiaorang;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/7 23:30
 */
public class Something {
    public static String test01() {
        return "hi";
    }

    /**
     * 返回字符串的第一个字符
     *
     * @param str 字符串
     * @return 字符串的第一个字符
     */
    public String startWith(String str) {
        return String.valueOf(str.charAt(0));
    }
}
