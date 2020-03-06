package com.scrcu.config.druid;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 动态数据源上下文管理，创建用来保存数据源名字的线程安全的类。通过ThreadLocal栈封闭方式保证线程安全
 * @创建人: jiyuanbo
 * @创建时间: 2019-8-22 上午10:25:40
 */
class DynamicDataSourceContextHolder {

	// 存放当前线程使用的数据源类型信息
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
	// 数据源ID
	public static List<String> dataSourceIds = new ArrayList<>();

	/**
	 * 描述: 设置数据源
	 * @param name 数据源名称
	 * @创建人: jiyuanbo
	 * @创建时间: 2019-8-22 上午10:25:40
	 */
	static void setDataSource(String name) {
		contextHolder.set(name);
	}

	/**
	 * 描述: 获取数据源
	 * @return String
	 * @创建人: jiyuanbo
	 * @创建时间: 2019-8-22 上午10:25:40
	 */
	public static String getDataSource() {
		return contextHolder.get();
	}

	/**
	 * 描述: 清除数据源
	 * @创建人: jiyuanbo
	 * @创建时间: 2019-8-22 上午10:25:40
	 */
	public static void cleanDataSource() {
		contextHolder.remove();
	}

	/**
	 * 描述: 判断当前数据源是否存在
	 * @param dataSourceId DataSrouce
	 * @return boolean
	 * @创建人: jiyuanbo
	 * @创建时间: 2019-8-22 上午10:25:40
	 */
	public static boolean containsDataSource(String dataSourceId) {
		return dataSourceIds.contains(dataSourceId);
	}
}
