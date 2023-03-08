package fun.xiaorang;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/8 14:21
 */
public class StreamTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamTest.class);
    private static final List<String> STRING_COLLECTION = new ArrayList<>();
    private static final List<Employee> EMPLOYEE_LIST = new ArrayList<>();
    private static final List<Student> STUDENT_LIST = new ArrayList<>();

    @BeforeAll
    public static void init() {
        STRING_COLLECTION.add("ddd2");
        STRING_COLLECTION.add("aaa2");
        STRING_COLLECTION.add("bbb1");
        STRING_COLLECTION.add("aaa1");
        STRING_COLLECTION.add("bbb3");
        STRING_COLLECTION.add("ccc");
        STRING_COLLECTION.add("bbb2");
        STRING_COLLECTION.add("ddd1");

        EMPLOYEE_LIST.add(new Employee("老张", 40, 9000, 0, "运营部"));
        EMPLOYEE_LIST.add(new Employee("小刘", 24, 5000, 1, "开发部"));
        EMPLOYEE_LIST.add(new Employee("大刚", 32, 7500, 0, "销售部"));
        EMPLOYEE_LIST.add(new Employee("翠花", 28, 5500, 1, "销售部"));
        EMPLOYEE_LIST.add(new Employee("小马", 21, 3000, 0, "开发部"));
        EMPLOYEE_LIST.add(new Employee("老王", 35, 6000, 1, "人事部"));
        EMPLOYEE_LIST.add(new Employee("小王", 21, 3000, 1, "人事部"));

        STUDENT_LIST.add(new Student("zhangsan", 20));
        STUDENT_LIST.add(new Student("lisi", 15));
        STUDENT_LIST.add(new Student("wangwu", 10));
        STUDENT_LIST.add(new Student("zhaoliu", 20));
    }

    @Test
    public void testFilter() {
        STRING_COLLECTION.stream().filter(s -> s.startsWith("a")).forEach(System.out::println);
    }

    @Test
    public void testSorted() {
        STRING_COLLECTION.stream().sorted().filter(s -> s.startsWith("a")).forEach(System.out::println);
        System.out.println("~~~~~~~~~~~~~~");
        STRING_COLLECTION.forEach(System.out::println);
    }

    @Test
    public void testMap() {
        // 需求：将集合中的每一项数据转成大写然后倒序排序输出
        STRING_COLLECTION.stream()
                .map(String::toUpperCase)
                .sorted(Comparator.reverseOrder())
                .forEach(System.out::println);

        // 取出所有学生的姓名并转成集合
        STUDENT_LIST.stream().map(Student::getName).forEach(System.out::println);
    }

    @Test
    public void testFlatMap() {
        // 单词流
        Stream.of("Hello", "World")
                .map(s -> s.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .forEach(System.out::println);
    }

    @Test
    public void testMatch() {
        // 需求：验证集合中是否存在以"a"开头的字符串，匹配到第一个，即返回true
        boolean anyStartWithA = STRING_COLLECTION.stream().anyMatch(s -> s.startsWith("a"));
        LOGGER.info("集合{}中是否存在以{}开头的字符串？{}", STRING_COLLECTION, "a", anyStartWithA);

        // 需求：验证集合中的字符串是否都是以"a"开头
        boolean allStartWithA = STRING_COLLECTION.stream().allMatch(s -> s.startsWith("a"));
        LOGGER.info("集合{}中的字符串是否都是以{}开头？{}", STRING_COLLECTION, "a", allStartWithA);

        // 需求：验证集合中的字符串是否都不是以"z"开头
        boolean noneStartWithZ = STRING_COLLECTION.stream().noneMatch(s -> s.startsWith("z"));
        LOGGER.info("集合{}中的字符串是否都不是以{}开头？{}", STRING_COLLECTION, "z", noneStartWithZ);
    }

    @Test
    public void testCount() {
        // 需求：先对集合中以"b"开头的字符串进行过滤，然后再统计元素个数
        long count = STRING_COLLECTION.stream().filter(s -> s.startsWith("b")).count();
        LOGGER.info("集合{}中以{}开头的字符串共有{}个", STRING_COLLECTION, "b", count);
    }

    @Test
    public void testReduce() {
        STRING_COLLECTION.stream().sorted().reduce((s1, s2) -> s1 + "#" + s2).ifPresent(System.out::println);

        Integer[] nums = {1, 2, 3, 4, 5};
        Integer sum1 = Stream.of(nums).reduce(0, Integer::sum);
        LOGGER.info("{}集合中所有元素加起来的和为{}", nums, sum1);
        int sum2 = Stream.of(nums).mapToInt(Integer::intValue).sum();
        LOGGER.info("{}集合中所有元素加起来的和为{}", nums, sum2);

        int max1 = Stream.of(nums).reduce(0, Math::max);
        LOGGER.info("{}集合中元素的最大值为{}", nums, max1);
        int max2 = Stream.of(nums).mapToInt(Integer::intValue).max().getAsInt();
        LOGGER.info("{}集合中元素的最大值为{}", nums, max2);
    }

    @Test
    public void testLimit() {
        // 输出集合中的前三个元素
        STRING_COLLECTION.stream().limit(3).forEach(System.out::println);
    }

    @Test
    public void testFindFirst() {
        // 找到集合中元素最后一个字符是'2'的字符串
        STRING_COLLECTION.stream().filter(s -> s.endsWith("2")).findFirst().ifPresent(System.out::println);
    }

    @Test
    public void testCollect() {
        // 个数
        Long count = EMPLOYEE_LIST.stream().collect(Collectors.counting());
        LOGGER.info("公司共有{}个员工", count);

        // 平均值
        Double avg = EMPLOYEE_LIST.stream().collect(Collectors.averagingDouble(Employee::getSalary));
        LOGGER.info("公司所有员工的平均工资为{}", avg);

        // 总和
        Double sum = EMPLOYEE_LIST.stream().collect(Collectors.summingDouble(Employee::getSalary));
        LOGGER.info("公司所有员工的工资总和为{}", sum);

        // 最大值
        EMPLOYEE_LIST.stream()
                .collect(Collectors.maxBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())))
                .ifPresent(e -> LOGGER.info("公司中员工工资最高的是{}", e));

        // 最小值
        EMPLOYEE_LIST.stream()
                .collect(Collectors.minBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())))
                .ifPresent(e -> LOGGER.info("公司中员工工资最低的是{}", e));

        // 按部门分组
        EMPLOYEE_LIST.stream()
                .collect(Collectors.groupingBy(Employee::getDept))
                .forEach((dept, employees) -> LOGGER.info("{}部门有{}", dept, employees));
        LOGGER.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        // 先按部门分组，再按性别分组
        EMPLOYEE_LIST.stream()
                .collect(Collectors.groupingBy(Employee::getDept, Collectors.groupingBy(Employee::getSex)))
                .forEach((dept, map) -> map.forEach((sex, employees) ->
                        LOGGER.info("{}部门有{}员工{}", dept, sex == 0 ? "男" : sex == 1 ? "女" : "其他", employees)));
        LOGGER.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        // 工资大于等于8000的员工，分区
        EMPLOYEE_LIST.stream()
                .collect(Collectors.partitioningBy(employee -> employee.getSalary() >= 8000))
                .forEach(((b, employees) -> LOGGER.info("工资{}8000的员工有{}", b ? "大于等于" : "小于", employees)));
        LOGGER.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        // 以年龄为key,姓名为value转成Map
        STUDENT_LIST.stream()
                .collect(Collectors.toMap(Student::getAge, Student::getName, ((name1, name2) -> name2)))
                .forEach((age, name) -> LOGGER.info("{}={}", age, name));
        LOGGER.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        // 按学生的年龄分组
        STUDENT_LIST.stream()
                .collect(Collectors.groupingBy(Student::getAge))
                .forEach((age, students) -> LOGGER.info("年龄为{}的学生有{}", age, students));
    }

    @Test
    public void test() {
        Stream.of(1, 2, 3, 4)
                .collect(() -> new ArrayList<Integer>(), List::add, List::addAll)
                .forEach(System.out::println);
        LOGGER.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        STUDENT_LIST.stream()
                .collect(() -> new HashMap<Integer, String>(), ((map, student) -> map.merge(student.getAge(), student.getName(), (s1, s2) -> s1)), Map::putAll)
                .forEach((age, name) -> LOGGER.info("{}={}", age, name));
    }
}
