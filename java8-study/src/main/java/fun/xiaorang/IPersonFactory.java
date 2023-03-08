package fun.xiaorang;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/7 23:39
 */
@FunctionalInterface
public interface IPersonFactory<p extends Person> {
    /**
     * 创建一个Person或其子类的实例对象
     *
     * @param firstName 姓
     * @param lastName  名
     * @return Person或其子类的实例对象
     */
    p create(String firstName, String lastName);
}
