package fun.xiaorang;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/7 23:17
 */
@FunctionalInterface
public interface IConverter<F, T> {
    /**
     * 将F类型的对象转换为T类型的对象
     *
     * @param from F类型的对象
     * @return T类型的对象
     */
    T convert(F from);
}
