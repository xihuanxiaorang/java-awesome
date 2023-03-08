package fun.xiaorang;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/7 21:38
 */
public abstract class AbstractFormula {
    /**
     * 将参数传入进来进行运算
     *
     * @param a 参与计算的参数
     * @return 参与计算的之后的结果
     */
    abstract double calculate(int a);

    /**
     * 求参数的算术平方根
     *
     * @param a 将要被开平方根的参数
     * @return 返回参数的算术平方根
     */
    double sqrt(int a) {
        return Math.sqrt(a);
    }
}
