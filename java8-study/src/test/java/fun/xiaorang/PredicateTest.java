package fun.xiaorang;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/8 0:13
 */
public class PredicateTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(IPersonFactoryTest.class);

    @Test
    public void test() {
        Predicate<String> predicate = s -> s.length() > 0;
        String str = "abc";
        LOGGER.info("字符串'{}'的长度是否大于0？{}", str, predicate.test(str));
        LOGGER.info("字符串'{}'的长度是否小于等于0？{}", str, predicate.negate().test(str));

        Boolean b = true;
        Predicate<Boolean> nonNull = Objects::nonNull;
        Predicate<Boolean> isNull = Objects::isNull;
        LOGGER.info("Boolean类型的'{}'是否不为null？{}", b, nonNull.test(b));
        LOGGER.info("Boolean类型的'{}'是否为null？{}", b, isNull.test(b));

        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();
        LOGGER.info("字符串'{}'的长度是否等于0？{}", str, isEmpty.test(str));
        LOGGER.info("字符串'{}'的长度是否不等于0？{}", str, isNotEmpty.test(str));
    }
}
