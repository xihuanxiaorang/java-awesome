package fun.xiaorang;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/7 23:41
 */
class IPersonFactoryTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(IPersonFactoryTest.class);

    @Test
    public void test_00() {
        IPersonFactory<Person> personFactory = Person::new; // 引用构造函数
        Person person = personFactory.create("Peter", "Parker");
        LOGGER.info("person={}", person);
    }
}