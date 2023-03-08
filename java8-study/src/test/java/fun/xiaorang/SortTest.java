package fun.xiaorang;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/7 23:02
 */
public class SortTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SortTest.class);

    @Test
    public void test_00() {
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        // names倒序排序
        names.sort(Comparator.reverseOrder());
        LOGGER.info("names={}", names);
    }
}
