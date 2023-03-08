package fun.xiaorang;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/7 23:22
 */
class IConverterTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(IConverterTest.class);

    @Test
    public void test_00() {
        IConverter<String, Integer> converter = Integer::valueOf; // 引用静态方法
        String str = "123";
        LOGGER.info("字符串'{}'转换成数字之后的结果是{}", str, converter.convert(str));
    }

    @Test
    public void test_01() {
        Something something = new Something();
        IConverter<String, String> converter = s -> String.valueOf(s.charAt(0));
        IConverter<String, String> converter2 = something::startWith; // 引用普通方法
        String str = "123";
        LOGGER.info("字符串'{}'的第一个字符是{}", str, converter.convert(str));
        LOGGER.info("字符串'{}'的第一个字符是{}", str, converter2.convert(str));
    }

    @Test
    public void test_02() {
        final int num = 1; // num类型在Lambda表达式中使用必须是final类型的，不可被修改
        IConverter<Integer, String> stringConverter = from -> String.valueOf(from + num);
        LOGGER.info("2转换之后的结果是{}", stringConverter.convert(2));
    }
}