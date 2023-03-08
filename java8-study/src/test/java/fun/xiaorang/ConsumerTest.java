package fun.xiaorang;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/8 2:15
 */
public class ConsumerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerTest.class);

    @Test
    public void test() {
        // 参照物，方便知道下面的Lambda表达式写法
        Consumer<Person> greeter01 = new Consumer<Person>() {
            @Override
            public void accept(Person person) {
                System.out.println("Hello, " + person.getFirstName());
            }
        };
        Consumer<Person> greeter02 = person -> LOGGER.info("Hello, " + person.getFirstName());
        Person person = new Person("Peter", "Parker");
        greeter02.accept(person);
    }
}
