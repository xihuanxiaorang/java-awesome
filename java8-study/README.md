---
title: Java8新特性
tags: 
  - 基础 
created: 2023-03-07 15:40:23
modified: 2023-03-07 15:47:18
number headings: auto, first-level 1, max 6, _.1.1.
---

# Java8 新特性

> Java8 的新特性：Lambda 表达式、强大的 Stream API、全新时间日期 API。这新特性使 Java 的运行速度更快、代码更少（Lambda 表达式）、便于并行、最大化减少空指针异常。

## 1. 回顾一下抽象类

jdk1.8 之前，**接口只能做方法定义不能有方法的实现**，因此咱们通常会 **在抽象类里面实现默认的方法** {该方法一般是抽象后公用的方法，不需要每个继承者都去实现，只需调用即可}，***模板设计模式 * 利用这个特性实现的**。

> 在定义的时候：

```java
public abstract class AFormula { // 抽象类
	
    abstract double calculate(int a);// 抽象方法

    // 平方
    double sqrt(int a) { // 具有方法体的方法，如果该类的子类不需要自己的实现，则子类的实例对象可以直接调用
        return Math.sqrt(a);
    }

}
```

> 在使用的时候：

```java
@Test
public void test_00() {
    AFormula aFormula = new AFormula() {
        @Override
        double calculate(int a) {
            return a * a;
        }
    };
    System.out.println(aFormula.calculate(2)); //求平方：4
    System.out.println(aFormula.sqrt(2));     //求开方：1.4142135623730951
}
```

## 2. 在接口中提供默认的方法实现

在 jdk1.8 中，不仅可以定义接口，还可以 **在接口中提供默认的方法实现**。

> 在定义的时候：**default** 关键字不能缺省

```java
public interface IFormula {

    double calculate(int a);

    // 平方
    default double sqrt(int a) {
        return Math.sqrt(a);
    }

}
```

> 在使用的时候：

```java
@Test
public void test_01() {
    IFormula formula = new IFormula() {
        @Override
        public double calculate(int a) {
            return a * a;
        }
    };
    System.out.println(formula.calculate(2));
    System.out.println(formula.sqrt(2));
}
```

## 3. Lambda 表达式

### 3.1. 基础语法

Java8 中引入了一个新的操作符 "->"，该操作符称为箭头操作符，箭头操作符将 Lambda 表达式拆分称两部分：

- 左侧：Lambda 表达式的参数列表，空参则括号里面什么都不写
- 右侧：Lambda 表达式中所需执行的功能，即 `Lambda` 体

![](https://fastly.jsdelivr.net/gh/xihuanxiaorang/images/202303071650858.webp)

#### 3.1.1. 语法格式一：无参数，无返回值

```java
() -> System.out.println("Hello Lambda!");
```

#### 3.1.2. 语法格式二：有一个参数（小括号可以省略），无返回值

```java
x -> System.out.println(x);
```

#### 3.1.3. 语法格式三：有两个以上参数，有返回值，Lambda 体中有多条语句（只有一条语句时，return 和大括号可以省略）

```java
Comparator<Integer> com = (x, y) -> {
	System.out.println("函数式接口");
    return Integer.compare(x, y);
}
```

#### 3.1.4. 语法格式四：Lambda 表达式的参数列表的数据类型可以省略不写，因为 JVM 编译器通过上下文可以推断出数据类型，即 " 类型推断 "

```java
(Integer x,Integer y)-> Integer.compare(x, y);
```

### 3.2. 方法引用

若 Lambda 表达式体中的内容已有方法实现，则可以使用 " 方法引用 "。  
**Lambda 表达式体中调用方法的参数列表、返回值类型必须与函数式接口中的抽象方法保持一致**。

#### 3.2.1. 方法引用

##### 3.2.1.1. 对象:: 实例方法

```java
@Test
public void test01(){
    PrintStream ps = System.out;
    Consumer<String> con1 = (s) -> ps.println(s);
    con1.accept("aaa");

    Consumer<String> con2 = ps::println;
    con2.accept("bbb");
}
```

##### 3.2.1.2. 类:: 静态方法

```java
@Test
public void test02(){
    Comparator<Integer> com1 = (x, y) -> Integer.compare(x, y);
    System.out.println(com1.compare(1, 2));

    Comparator<Integer> com2 = Integer::compare;
    System.out.println(com2.compare(2, 1));
}
```

##### 3.2.1.3. 类:: 实例方法

注意：**Lambda 参数列表中的第一个参数是方法的调用者，第二个参数是方法的参数时，才能使用 ClassName::Method**。

```java
@Test
public void test03(){
    BiPredicate<String, String> bp1 = (x, y) -> x.equals(y);
    System.out.println(bp1.test("a","b"));

    BiPredicate<String, String> bp2 = String::equals;
    System.out.println(bp2.test("c","c"));
}
```

#### 3.2.2. 构造器引用

注意：**需要调用的构造器的参数列表要与函数式接口中的抽象方法的参数列表保持一致。**

```java
@Test
public void test04(){
    Supplier<Employee> sup = Employee::new;
}
```

### 3.3. Lambda 作用范围

> Accessing outer scope variables from lambda expressions is very similar to anonymous objects. You can access final variables from the local outer scope as well as instance fields and static variables.

Lambda 表达式访问外部的变量 (局部变量，成员变量，静态变量，接口的默认方法),它与匿名内部类访问外部变量非常相似。

#### 3.3.1. 访问局部变量

```java
int num = 1;
IConverter<Integer, String> stringConverter = from -> String.valueOf(from + num);
String convert = stringConverter.convert(2);
System.out.println(convert); // 3
```

但是这个 num 是不可变值，这样改变值会报错；

```java
int num = 1;
IConverter<Integer, String> stringConverter =
        (from) -> String.valueOf(from + num);
num = 3;
```

> Variable used in lambda expression should be final or effectively final

另外在 lambda 表达式内部修改也是不允许的；

```java
int num = 1;
IConverter<Integer, String> converter = (from) -> {
    String value = String.valueOf(from + num);
    num = 3;
    return value;
};
```

#### 3.3.2. 访问成员变量和静态变量

在 Lambda 表达式中访问局部变量。与局部变量相比，在 Lambda 表达式中对成员变量和静态变量拥有读写权限：

```java
public class Lambda4 {

    // 静态变量
    static int outerStaticNum;
    // 成员变量
    int outerNum;

    void testScopes() {
        IConverter<Integer, String> stringConverter1 = (from) -> {
            // 对成员变量赋值
            outerNum = 23;
            return String.valueOf(from);
        };

        IConverter<Integer, String> stringConverter2 = (from) -> {
            // 对静态变量赋值
            outerStaticNum = 72;
            return String.valueOf(from);
        };
    }

}
```

#### 3.3.3. 访问默认接口方法

还记得第一节的 IFormula 示例吗？

```java
public interface IFormula {

    double calculate(int a);

    // 平方
    default double sqrt(int a) {
        return Math.sqrt(a);
    }

}
```

当时，我们在接口中定义了一个带有默认实现的 sqrt 求平方根方法，在匿名内部类中我们可以很方便的访问此方法：

```java
IFormula formula = new IFormula() {
    @Override
    public double calculate(int a) {
        return a * a;
    }
}; 
```

但是不能通过 lambda 表达式访问默认方法，这样的代码没法通过编译；

```java
IFormula formula = (a) -> sqrt(a * a);  
```

**带有默认实现的接口方法，是不能在 lambda 表达式中访问的**，上面这段代码将无法被编译通过。

### 3.4. 函数式接口

**Lambda 表达式需要 " 函数式接口 " 的支持**，函数式接口：**接口中只有一个抽象方法**。一般在函数式接口上都有个注解 `@FunctionalInterface` ，该注解的作用类似 `@Override` 一样告诉编译器这是一个函数式接口，用于编译器期间检测该接口是否仅有一个抽象方法，如果拥有多个则编译不通过。如下图所示

#### 3.4.1. 内置的函数式接口

##### 3.4.1.1. Predicate 断言型接口

内部抽象方法：`boolean test(T t);` 确定类型为 T 的对象是否满足某约束，并返回 boolean 值。它内部提供了一些带有默认实现的方法，可以被用来组合一个复杂的逻辑判断（and, or, negate）。

```java
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
```

##### 3.4.1.2. Function 函数型接口

内部抽象方法：`R apply(T t);` 对类型为 T 的对象应用操作，并返回结果，结果是 R 类型的对象。通过它内部提供的一些默认方法，组合，链行处理 (compose, andThen)。

```java
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
```

##### 3.4.1.3. Supplier 供给型接口

内部抽象方法：`T get();` 返回类型为 T 的对象。

```java
@Test  
public void test() {  
    Supplier<Person> personSupplier = Person::new;  
    LOGGER.info("调用Person类的无参构造方法创建一个person对象实例{}", personSupplier.get());  
    Supplier<String> test01Supplier = Something::test01;  
    LOGGER.info("调用Something类的test01静态方法，返回的结果是{}", test01Supplier.get());  
}
```

##### 3.4.1.4. Consumer 消费型接口

内部抽象方法：`void accept(T t);` 对类型为 T 的对象应用操作。

```java
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
```

#### 3.4.2. 其他函数式接口

| 函数式接口                          | 参数类型 | 返回类型 | 用途                                                         |
| ----------------------------------- | -------- | -------- | ------------------------------------------------------------ |
| `BiFunction<T,U,R>`                 | T, U     | R        | 对类型为 T,U 参数应用操作，返回 R 类型的结果。包含方法为：`Rapply(T t,U u)`; |
| `UnaryOperator`(Function 子接口)    | T        | T        | 对类型为 T 的对象进行一元运算，并返回 T 类型的结果。包含方法为：`Tapply(T t)`; |
| `BinaryOperator`(BiFunction 子接口) | T,T      | T        | 对类型为 T 的对象进行二元运算，并返回 T 类型的结果。包含方法为：`Tapply(T t1,T t2)`; |
| `BiConsumer<T,U>`                   | T,U      | void     | 对类型为 T,U 参数应用操作。包含方法为：`voidaccept(Tt,Uu)`   |
| `BiPredicate<T,U>`                  | T,U      | boolean  | 包含方法为：`booleantest(Tt,Uu)`                             |
| `ToIntFunction`                     | T        | int      | 计算 `int` 值的函数                                          |
| `ToLongFunction`                    | T        | long     | 计算 `long` 值的函数                                         |
| `ToDoubleFunction`                  | T        | double   | 计算 `double` 值的函数                                       |
| `IntFunction`                       | int      | R        | 参数为 `int` 类型的函数                                      |
| `LongFunction`                      | long     | R        | 参数为 `long` 类型的函数                                     |
| `DoubleFunction`                    | double   | R        | 参数为 `double` 类型的函数                                   |

## 4. Stream API

### 4.1. 简述

流（Stream）是数据渠道，用于操作数据源（集合和数组等）所生成的元素序列。  
" 集合讲的是数据，流讲的是计算 "。  
注意：

- Stream 自己不会存储元素
- Stream 不会改变源对象。相反，会返回一个持有结果的新 Stream。
- Stream 操作是延迟执行的。这意味着它们会等到需要结果的时候才会执行。

![image](https://fastly.jsdelivr.net/gh/xihuanxiaorang/images/202303071650120.png)

### 4.2. 创建流的方法

```java
/**
* 创建流
*/
@Test
public void test01(){
    /**
    * 集合流
    *  - Collection.stream() 串行流
    *  - Collection.parallelStream() 并行流
    */
    List<String> list = new ArrayList<>();
    Stream<String> stream1 = list.stream();

    //数组流
    //Arrays.stream(array)
    String[] strings = new String[10];
    Stream<String> stream2 = Arrays.stream(strings);

    //Stream 静态方法
    //Stream.of(...)
    Stream<Integer> stream3 = Stream.of(1, 2, 3);

    //无限流
    //迭代
    Stream<Integer> stream4 = Stream.iterate(0, (i) -> ++i+i++);
    stream4.forEach(System.out::println);

    //生成
    Stream.generate(() -> Math.random())
        .limit(5)
        .forEach(System.out::println);
}
```

### 4.3. 筛选与切片

#### 4.3.1. filter

> `Stream<T> filter(Predicate<? super T> predicate)` 返回此流中匹配元素组成的流

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
numbers
        .stream()
        .filter(i -> i % 2 == 0) //过滤掉奇数
        .forEach(System.out::println); // 终端操作，打印结果
// 输出：2 4 6 8 10
```

#### 4.3.2. distinct

> `Stream<T> distinct()` 通过流所生成的元素的 hashCode() 和 equals() 去除重复元素

```java
Arrays.asList(1, 2, 3, 2, 3, 4)
                .stream()
                .distinct() // 去除重复
                .forEach(System.out::println);
// 输出: 1234
```

#### 4.3.3. limit

> Stream limit(long maxSize) 截取流，返回一个不超过给定长度的流

```java
Arrays.asList(1, 2, 3, 4, 5)
                .stream()
                .limit(3) // 截取前三个元素
                .forEach(System.out::println);
// 输出: 123
```

#### 4.3.4. skip

> `Stream<T> skip(long n)` 跳过给定长度的流

```java
Arrays.asList(1, 2, 3, 4, 5)
                .stream()
                .skip(2) // 跳过前两个元素
                .forEach(System.out::println);
// 输出: 345
```

### 4.4. 映射

#### 4.4.1. map

> `<R> Stream<R> map(Function<? super T, ? extends R> mapper)` 接收 Lambda ，将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
numbers
        .stream()
        .map(i -> i + "str ") // 转换成String
        .forEach(System.out::print); // 终端操作，打印结果
// 输出：1str 2str 3str 4str 5str 6str 7str 8str 9str 10str
```

#### 4.4.2. flatmap

> `<R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper)` 接收一个函数作为参数，将流中的每个值都转换成另一个流，然后把所有流连接一个流，即会将每个元素扁平化

```java
List<String> strings = Arrays.asList("Hello", "World");
List<String> list = strings
                .stream() // 将集合转成流
                .map(s -> s.split("")) // 转换成['H','e','l','l','o'],['W','o','r','l','d'] 两个数组
                .flatMap(Arrays::stream) // 将两个数组扁平化成为['H','e','l','l','o','W','o','r','l','d']，实际上还是把两个数组再次转成流
                .distinct() // 去除重复元素
                .collect(Collectors.toList()); // 终端操作，转化成集合
System.out.println(list);
// 输出: [H, e, l, o, W, r, d]
```

![](https://fastly.jsdelivr.net/gh/xihuanxiaorang/images/202303071654746.jpeg)

```java
List<Integer> numbers1 = Arrays.asList(1, 2, 3);
List<Integer> numbers2 = Arrays.asList(4, 5, 6);
Stream.of(numbers1, numbers2) // 将两个集合转成流
        .flatMap(numbers -> numbers.stream()) // 两个集合流扁平化为[1,2,3,4,5,6]
        .forEach(System.out::println);
// 输出: 1，2，3，4，5，6
```

### 4.5. 排序

#### 4.5.1. sorted

> `Stream<T> sorted()` 对流中的元素自然排序

```java
Arrays.asList(1, 3, 5, 2, 4)
                .stream()
                .sorted() // 顺序排序
                .forEach(System.out::println);
// 输出: 12345
```

#### 4.5.2. sorted(Comparator com)

> `Stream<T> sorted(Comparator com)` 可以传入一个 Comparator 来实现自己的排序规则

```java
Arrays.asList(1, 3, 5, 2, 4)
                .stream()
                .sorted(Comparator.reverseOrder()) // 倒序排序
                .forEach(System.out::println);
// 输出: 54321
```

### 4.6. 查找与匹配

#### 4.6.1. 1、allMatch

> `boolean allMatch(Predicate<? super T> predicate)` allMatch：此流的所有元素是都匹配返回 ture，否者为 false

```java
// 全部匹配
System.out.println(Stream.of(5, 6, 7, 8, 9).allMatch(i -> i >= 5)); // true
System.out.println(Stream.of(5, 6, 7, 8, 9).allMatch(i -> i > 5)); // false
```

#### 4.6.2. anyMatch

> `boolean anyMatch(Predicate<? super T> predicate)` anyMatch：此流的任意元素有一个匹配返回 ture，都不匹配返回 false

```java
// 任意一个匹配
System.out.println(Stream.of(5, 6, 7, 8, 9).anyMatch(i -> i > 5)); // true
System.out.println(Stream.of(5, 6, 7, 8, 9).anyMatch(i -> i > 9)); // false
```

#### 4.6.3. noneMatch

> `boolean noneMatch(Predicate<? super T> predicate)` noneMatch：此流中没有一个元素匹配返回 ture,否者返回 false

```java
// 都不匹配
System.out.println(Stream.of(5, 6, 7, 8, 9).noneMatch(i -> i > 5)); // false
System.out.println(Stream.of(5, 6, 7, 8, 9).noneMatch(i -> i > 9)); // true
```

#### 4.6.4. findFirst,findAny

> `Optional<T> findFirst()` 返回此流的第一个元素的 Optional，如果流为空，则返回空 Optional。 `Optional<T> findAny()` 返回此流的任意一个元素的 Optional，如果流为空，则返回空 Optional。 findFirst 在并行流中的执行代价非常大，需要注意

```java
Optional<Integer> first = Arrays.asList(1, 2, 3, 4, 5)
                                .stream().findFirst();
System.out.println(first.get()); // 输出 1
Optional<Integer> any = Arrays.asList(1, 2, 3, 4, 5)
                            .stream().findAny();
System.out.println(any.get()); // 因为是顺序流，所以输出1
```

### 4.7. 规约

> 聚合操作 sum()、max()、min()、count() 调用的都是 reduce `Optional<T> reduce(BinaryOperator<T> accumulator)` 无初始值，按传入的 lambda 的累加规则来聚合数据

```java
// 无默认值，求和
Optional<Integer> sum1 = Arrays.asList(1, 2, 3, 4, 5)
                            .stream()
                            .reduce((a, b) -> a + b);
System.out.println(sum1.get()); // 输出：15
```

> `T reduce(T identity, BinaryOperator<T> accumulator)` 第一个参数为初始值，第二个参数为累加器（归并数据的 lambda）

```java
// 有默认值，求和
Integer sum2 = Arrays.asList(1, 2, 3, 4, 5)
                    .stream()
                    .reduce(5, (a, b) -> a + b);
System.out.println(sum2); // 输出：20
// 求最大值
Integer max = Arrays.asList(1, 2, 3, 4, 5)
                    .stream()
                    .reduce(0, Integer::max); // 也可以写成 reduce(0, (a, b) -> a > b ? a : b);
System.out.println(max); // 输出：20
```

### 4.8. 收集

> `<R, A> R collect(Collector<? super T, A, R> collector)` 收集，对数据做聚合，将流转换为其他形式,比如 List，Map，Integer，Long...但是 Collectors 实用类提供了很多静态方法，可以方便地创建常见收集齐实例。

```java
//个数
Long count= employees.stream()
    .collect(Collectors.counting());
System.out.println(count);
//平均值
Double avg= employees.stream()
    .collect(Collectors.averagingDouble(Employee::getSalary));
System.out.println(avg);
//总和
Double sum= employees.stream()
    .collect(Collectors.summingDouble(Employee::getSalary));
System.out.println(sum);
//最大值d的员工
Optional<Employee> max = employees.stream()
    .collect(Collectors.maxBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
System.out.println(max.get());
//最小值
Optional<Double> min = employees.stream()
    .map(Employee::getSalary)
    .collect(Collectors.minBy(Double::compare));
//分组
Map<Employee.Status, List<Employee>> map = employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus));
System.out.println(map);
//多级分组
Map<Employee.Status, Map<String, List<Employee>>> map = employees.stream()
    .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e) -> {
        if (e.getAge() <= 35) {
            return "青年";
        } else if (e.getAge() <= 50) {
            return "中年";
        } else {
            return "老年";
        }
    })));
System.out.println(map);
//分区
Map<Boolean, List<Employee>> map = employees.stream()
    .collect(Collectors.partitioningBy((e) -> e.getSalary() > 8000));
System.out.println(map);
```

```java
// 准备一些初始数据
@Data
@AllArgsConstructorclass 
Student {
    private String name;    
    private Integer age;
}
// 初始化数据
Student student1 = new Student("zhangsan", 20);
Student student2 = new Student("lisi", 15);
Student student3 = new Student("wangwu", 10);
Student student4 = new Student("zhaoliu", 20);
List<Student> students = Arrays.asList(student1, student2, student3, student4);
// 如果要取出所有学生的姓名并转成集合可以写成
List<String> names = students.stream()
    .map(Student::getName) // 获取name
    .collect(Collectors.toList()); // 转成List
System.out.println(names); // 输出：[zhangsan, lisi, wangwu, zhaoliu]
// 以年龄为key,姓名为value转成Map可以写成
Map<Integer, String> map = students.stream()
    .collect(Collectors.toMap(Student::getAge, Student::getName)); // 此写法会有问题，如果Map的key重复了，会报java.lang.IllegalStateException: Duplicate key  如果可以确保key不会重复就可以省略第三个参数        
Map<Integer, String> map = students.stream()
    .collect(Collectors.toMap(Student::getAge, Student::getName, (first, second) -> second)); // 前面两个参数是映射key和value，第三个参数为如果key重复了要如何处理，是保留旧的还是选择新的
System.out.println(map); // 输出：{20=zhaoliu, 10=wangwu, 15=lisi}  因为zhangsan和zhaoliu的年龄都是20,按照我们的策略，始终选择新的，所以key为20的value是zhaoliu
Map<Integer, List<Student>> groupByAge = students.stream()
    .collect(Collectors.groupingBy(Student::getAge)); // 根据age分组
System.out.println(groupByAge);
// 输出：{20=[Student(name=zhangsan, age=20), Student(name=zhaoliu, age=20)], 10=[Student(name=wangwu, age=10)], 15=[Student(name=lisi, age=15)]}
```

> `<R> R collect(Supplier<R> supplier,BiConsumer<R, ? super T> accumulator,BiConsumer<R, R> combiner)`  
> supplier：定义一个容器  
> accumulator：该容器怎么添加流中的数据  
> combiner：容器如何去聚合

![](https://fastly.jsdelivr.net/gh/xihuanxiaorang/images/202303071656981.png)

```java
// 仿Collectors.toList(),简单实现一个toList()
// 1.定义一个List容器
// 2.调用List的add方法将元素添加到容器中
// 3.采用List的addAll方法聚合容器
List<Integer> toList = Arrays.asList(1, 2, 3, 4).stream().collect(ArrayList::new, List::add, List::addAll);
System.out.println(toList);
// 输出：[1, 2, 3, 4]
// 仿Collectors.toMap(),简单实现toMap()
// 1.定义一个Map容器
// 2.调用Map的merge方法将元素添加到容器中
// 3.采用Map的putAll方法聚合容器
Map<Object, Object> map = students.stream()
    .collect(HashMap::new, 
             (map, element) -> {
                 map.merge(element.getAge(), element.getName(), (u, v) -> {
                     return u;        
                     // throw new IllegalStateException(String.format("Duplicate key %s", u));
                 });
             }, Map::putAll);
System.out.println(map);
// 输出：{20=zhangsan, 10=wangwu, 15=lisi}
```

其中，map 中的 merge 方法源码：

```java
default V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
    Objects.requireNonNull(remappingFunction);
    Objects.requireNonNull(value);
    V oldValue = this.get(key);
    V newValue = oldValue == null ? value : remappingFunction.apply(oldValue, value);
    if (newValue == null) {
        this.remove(key);
    } else {
        this.put(key, newValue);
    }
    return newValue;
}
```

> merge 方法有三个参数，key：map 中的键，value：使用者传入的值，remappingFunction：BiFunction 函数接口 (该接口接收两个值，执行自定义功能并返回最终值)。当 map 中不存在指定的 key 时，便将传入的 value 设置为 key 的值，当 key 存在值时，执行一个方法该方法接收 key 的旧值和传入的 value，执行自定义的方法返回最终结果设置为 key 的值。

## 5. Optional

> java8 添加的容器对象，在一些场景下避免使用 null 检查而设定的类，尽可能避免的 NullPointerException。

#### 5.1.1. 创建 Optional 实例的静态方法

Optional 私有了构造函数，只能通过 Optional 对外提供的三个静态方法构造实例

##### 5.1.1.1. empty

> `public static<T> Optional<T> empty()` 返回一个空 Optional 实例

##### 5.1.1.2. of

> `public static <T> Optional<T> of(T value)` 创建一个包含非 null 值的 Optional 容器，如果值为 null 会直接抛出 NullPointerException

##### 5.1.1.3. ofNullable

> `public static <T> Optional<T> ofNullable(T value)` 创建一个可以包含 null 值的 Optional 容器

#### 5.1.2. Optional 实例方法

##### 5.1.2.1. get

> `public T get()` 返回 Optional 容器保存的非 null 值，如果值为 null 会抛出 NoSuchElementException

```java
Optional<Object> empty = Optional.empty();
System.out.println(empty.get()); // 抛NoSuchElementException
Optional<Object> nullable = Optional.ofNullable(null);
System.out.println(nullable.get()); //  抛NoSuchElementException
Optional<Object> non = Optional.ofNullable("test");
System.out.println(non.get()); // 输出：test
```

##### 5.1.2.2. orElse

> `public T orElse(T other)` 如果存在则返回值，否则返回 other，是否存在的判断为 null 或者是一个 empty

```java
Object data1 = Optional.ofNullable(null).orElse("data");
System.out.println(data1); // 输出：data
Object data2 = Optional.ofNullable("data1").orElse("data2");
System.out.println(data2); // 输出：data1
```

##### 5.1.2.3. orElseGet

> `public T orElseGet(Supplier<? extends T> other)` 如果存在则返回值，否则调用 other 并返回该调用的结果。

```java
Object data = Optional.ofNullable(null).orElseGet(() -> "data");
System.out.println(data); // 输出：data
```

##### 5.1.2.4. orElse 与 orElseGet 的区别

> orElse 即使值不为空的情况下，也会调用 orElse() 中的函数，而 orElseGet 是在值为空的情况下才会调用 other 中的函数

```java
public static <T> T getValue(T oldValue, T newValue) {
    System.out.println(newValue);    
    return newValue;
}
Optional.ofNullable("a").orElse(getValue("test1"));
Optional.ofNullable("b").orElseGet(() -> "test2");
// 此时会看到控制台输出了test1
// orElse即使值不为空的情况下，也会调用orElse()中的函数，而orElseGet是在值为空的情况下才会调用other中的函数
// 如果有如下代码那么将是致命的
User u1 = Optional.ofNullable(user).orElse(userMapper.findUserById("1")); // 1
User u2 = Optional.ofNullable(user).orElse(new User()); // 2
// 哪怕user不为空也会去查询一次数据库，或者user不为空也会创建一个对象，加大了性能消耗，所以在使用时需要注意
```

##### 5.1.2.5. orElseThrow

> `public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X` 如果存在返回包含的值，否则抛出定义的异常。

```java
// 当user为空的时候，抛出定义的异常
User user = Optional.ofNullable(user).orElseThrow(() -> new RuntimeException("User为空"));
```

##### 5.1.2.6. isPresent

> `public boolean isPresent()` 如果存在值则返回 true，否则返回 false。

```java
boolean b1 = Optional.empty().isPresent(); // false
boolean b2 = Optional.ofNullable(null).isPresent(); // false
boolean b3 = Optional.ofNullable("").isPresent(); // ture
```

##### 5.1.2.7. ifPresent

> `public void ifPresent(Consumer<? super T> consumer)` 如果存在值，则执行指定的操作，否则不执行任何操作。

```java
Optional.ofNullable(null).ifPresent(System.out::println); // 不会执行
Optional.ofNullable("data").ifPresent(System.out::println); // 输出data
```

##### 5.1.2.8. filter

> `public Optional<T> filter(Predicate<? super T> predicate)` 根据给定的条件过滤值，过滤后如果存在值则返回 Optional 包装的值，否则返回空的 Optional。

```java
Optional<String> str1 = Optional.ofNullable("123456").filter(element -> element.contains("123"));
System.out.println(str1); // 输出：Optional[123456]
Optional<String> str2 = Optional.ofNullable("123456").filter(element -> element.contains("789"));
System.out.println(str2); // 输出：Optional.empty
```

##### 5.1.2.9. map

> `public<U> Optional<U> map(Function<? super T, ? extends U> mapper)` 如果存在值，就对该值执行 mapping 调用，返回 mapping 后 Optional 包装的值，否则返回空的 Optional。

```java
Student student = new Student("zhangsan",10);
Optional<String> name = Optional.of(student).map(Student::getName); // 返回一个Optional包装的值
```

##### 5.1.2.10. flatMap

> `public<U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper)` 如果值存在，就对该值执行 mapping 调用，返回一个 Optional 类型的值，否则就返回一个空的 Optional 对象

```java
Student student = new Student("zhangsan",10);
Optional<Integer> age = Optional.of(student).flatMap(s -> Optional.ofNullable(s.getAge())); // 返回一个Optional包装的值
```

##### 5.1.2.11. map 与 flatMap 的区别

> map 接受的入参为任意类型，flatMap 接受的入参为 Optional 类型，返回的也为 Optional

## 6. 新时间与日期 API

待续。。。

