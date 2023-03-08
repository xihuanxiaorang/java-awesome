package fun.xiaorang;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/8 1:27
 */
public class FunctionTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(FunctionTest.class);

    @Test
    public void test() {
        // 需求：先将字符串反转之后再取第一个字符再转换成数字 "123" => 3
        Function<String, Integer> toIntegerFunction = Integer::valueOf;
        Function<String, String> reserveFunction = this::reserve;
        Function<String, String> startWithFunction = new Something()::startWith;
        String str = "123";

        // andThen的实现
        Function<String, Integer> function = reserveFunction.andThen(startWithFunction).andThen(toIntegerFunction);
        LOGGER.info("字符串'{}'反转之后再取第一个字符再转换成数字是{}", str, function.apply(str));

        // compose的实现
        Function<String, Integer> function1 = toIntegerFunction.compose(startWithFunction).compose(reserveFunction);
        LOGGER.info("字符串'{}'反转之后再取第一个字符再转换成数字是{}", str, function1.apply(str));
    }

    /**
     * 反转字符串，如 "abc" => "cba"
     *
     * @param str 待反转的字符串
     * @return 反转之后的字符串
     */
    public String reserve(String str) {
        int length = str.length();
        if (length == 1) return str;
        String left = str.substring(0, length / 2);
        String right = str.substring(length / 2, length);
        return reserve(right) + reserve(left);
    }
}
