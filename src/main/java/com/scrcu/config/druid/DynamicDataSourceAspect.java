package com.scrcu.config.druid;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 描述: 动态数据源通知，利用AOP切面实现数据源的动态切换
 * @创建人: jiyuanbo
 * @创建时间: 2019-8-22 上午11:19:33
 */
@Aspect
@Component
@Order(-10) // 切面排序,保证该AOP在@Transactional之前执行
public class DynamicDataSourceAspect {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 描述: 切入点只对@Service注解的类上的@DataSource方法生效
	 * @param dataSource
	 * @创建人: jiyuanbo
	 * @创建时间: 2019-8-22 上午11:25:11
	 */
    @Pointcut(value="@within(org.springframework.stereotype.Service) && @annotation(dataSource)" )
    public void dynamicDataSourcePointCut(DataSourceTarget dataSource) {

	}

	/**
	 * 描述: @Before("@annotation(DataSource)")的意思是： @Before：在方法执行之前进行执行：
	 *  annotation(DataSource)：会拦截注解DataSource的方法，否则不拦截;
	 * @param point
	 * @param dataSource
	 * @创建人: jiyuanbo
	 * @创建时间: 2019-8-22 上午11:25:11
	 */
	@Before(value = "@annotation(dataSourceTarget)")
	public void switchDataSource(JoinPoint point, DataSourceTarget dataSourceTarget) throws Throwable {
		// 获取当前的指定的数据源
		String dsId = dataSourceTarget.name().getDbName();
		// 如果不在我们注入的所有的数据源范围之内，那么输出警告信息，系统自动使用默认的数据源
		if (!DynamicDataSourceContextHolder.containsDataSource(dsId)) {
			logger.error("数据源[" + dataSourceTarget.name() + "]不存在，使用默认数据源 > " + point.getSignature());
		} else {
			logger.info("使用数据源 > " + dataSourceTarget.name());
			// 找到的话，那么设置到动态数据源上下文中
			DynamicDataSourceContextHolder.setDataSource(dataSourceTarget.name().getDbName());
		}
	}

	/**
	 * 描述: 回收数据源
	 * @param point
	 * @param dataSource
	 * @创建人: jiyuanbo
	 * @创建时间: 2019-8-22 上午11:25:11
	 */
	@After(value = "@annotation(dataSourceTarget)")
	public void restoreDataSource(JoinPoint point, DataSourceTarget dataSourceTarget) {
		logger.info("Revert DataSource  > " + dataSourceTarget.name() + " >" + point.getSignature());
		// 方法执行完毕之后，销毁当前数据源信息，进行垃圾回收
		DynamicDataSourceContextHolder.cleanDataSource();
	}
}
