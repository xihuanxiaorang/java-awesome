package fun.xiaorang;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/7 21:45
 */
class AbstractFormulaTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(AbstractFormulaTest.class);

    @Test
    public void test_00() {
        AbstractFormula formula = new AbstractFormula() {
            @Override
            double calculate(int a) {
                return a * a;
            }
        };
        LOGGER.info("{}的平方={}", 2, formula.calculate(2));
        LOGGER.info("{}的算术平方根={}", 2, formula.sqrt(2));
    }
}