package fun.xiaorang;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/8 23:12
 */
public class OptionalTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(OptionalTest.class);

    @Test
    public void test_00() {
        Optional<String> optional = Optional.of("bam");
        System.out.println(optional.isPresent()); // true
        System.out.println(optional.orElse("fallback")); // "bam"
        optional.ifPresent(s -> System.out.println(s.charAt(0))); // "b"

        Optional<Person> optionalPerson = Optional.of(new Person());
        optionalPerson.ifPresent(s -> System.out.println(s.getFirstName())); // null
    }

    @Test
    public void test_01() {
        Optional<String> optional = Optional.ofNullable(null);
        optional.ifPresent(s -> System.out.println("123")); // 不会打印"123，因为现在Optional类中的value属性为null，Consumer的函数式接口不会被调用
        System.out.println(optional.orElse("fallback")); // "fallback"
        System.out.println(optional.orElseGet(() -> {
            System.out.println("我没有过度使用Lambda表达式");
            return "fallback2";
        })); // "fallback2"

        try {
            optional.orElseThrow(() -> new RuntimeException("值为空，请检查！"));
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void test_02() {
        // map()
        Student student = new Student("zhangsan", 10);
        Optional.of(student).map(Student::getName).ifPresent(System.out::println); // "zhangsan"

        // flatMap()
        Student student1 = new Student("zhangsan", 10);
        Optional.of(student1).flatMap(stu -> Optional.of(stu.getAge())).ifPresent(System.out::println); // 10

        // filter()
        Optional.ofNullable("123456").filter(element -> element.contains("123")).ifPresent(System.out::println); // "123456"
        Optional.ofNullable("123456").filter(element -> element.contains("789")).ifPresent(System.out::println); // 没输出
    }
}
