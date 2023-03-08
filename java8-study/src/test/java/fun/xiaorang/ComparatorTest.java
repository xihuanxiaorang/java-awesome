package fun.xiaorang;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/8 2:24
 */
public class ComparatorTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComparatorTest.class);

    @Test
    public void test() {
        Comparator<Person> comparator01 = (p1, p2) -> p1.getFirstName().compareTo(p2.getFirstName());
        Comparator<Person> comparator02 = Comparator.comparing(Person::getFirstName);
        Person p1 = new Person("John", "Doe");
        Person p2 = new Person("Alice", "Wonderland");
        LOGGER.info("{}的姓是否排在{}的后面？{}", p1, p2, comparator01.compare(p1, p2) > 0 ? "是" : "否");
        LOGGER.info("{}的姓是否排在{}的前面？{}", p1, p2, comparator01.reversed().compare(p1, p2) > 0 ? "是" : "否");
    }
}
