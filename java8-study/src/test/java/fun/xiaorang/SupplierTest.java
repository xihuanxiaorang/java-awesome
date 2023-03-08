package fun.xiaorang;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/8 2:07
 */
public class SupplierTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SupplierTest.class);

    @Test
    public void test() {
        Supplier<Person> personSupplier = Person::new;
        LOGGER.info("调用Person类的无参构造方法创建一个person对象实例{}", personSupplier.get());
        Supplier<String> test01Supplier = Something::test01;
        LOGGER.info("调用Something类的test01静态方法，返回的结果是{}", test01Supplier.get());
    }
}
