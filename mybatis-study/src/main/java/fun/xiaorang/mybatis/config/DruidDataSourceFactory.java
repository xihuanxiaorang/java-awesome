package fun.xiaorang.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;

/**
 * @author liulei
 * @description <p style = " font-weight:bold ; "><p/>
 * @github <a href="https://github.com/xihuanxiaorang/java-awesome">java-awesome</a>
 * @Copyright 博客：<a href="https://blog.xiaorang.fun">小让的糖果屋</a>  - show me the code
 * @date 2023/3/10 20:43
 */
public class DruidDataSourceFactory extends PooledDataSourceFactory {
    public DruidDataSourceFactory() {
        this.dataSource = new DruidDataSource();
    }
}
