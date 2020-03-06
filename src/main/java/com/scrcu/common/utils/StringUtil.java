package com.scrcu.common.utils;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * 字符串工具类
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/11/28 16:54
 **/
public class StringUtil {

	/**
	 * <p>
	 * 填充0字符串
	 * </p>
	 * @param length 返回字符串的最大长度
	 * @param startStr 指定返回字符串的开头字符串
	 * @param endStr 指定返回字符串的结尾字符串
	 * @return java.lang.String
	 * @author wuyu
	 * @date 2019/11/28 17:08
	 **/
	public static String paddingZero(int length, String startStr, String endStr) {
		if (length < 1) {
			return "";
		}
		if (StringUtils.isEmpty(startStr)) {
			startStr = "";
		}
		if (StringUtils.isEmpty(endStr)) {
			endStr = "";
		}
		StringBuffer sb = new StringBuffer(startStr);
		if (sb.length() > length) {
			return sb.substring(0, length);
		}
		int tempLength = sb.length() + endStr.length();
		if (tempLength > length) {
			return sb.append(endStr).substring(0, length);
		}
		for (int i = 0, len = length - tempLength; i < len; i++) {
			sb.append(0);
		}
		sb.append(endStr);
		return sb.toString();
	}

	/**
	 * <p>
	 * 校验SQL语句中是否存在非法字符
	 * </p>
	 * @param sql SQL语句
	 * @return boolean
	 * @author wuyu
	 * @date 2019/12/4 11:23
	 **/
	public static boolean hasIllegalCharInSql(String sql) {
		sql = eliminateFormatting(sql, " ").replaceAll(" +", " ")
				.trim().toLowerCase();
		if (!sql.startsWith("select ") || sql.contains("drop table ") || sql.contains("alert table ")
				|| sql.contains("delete from ")	|| sql.contains("delete * from ") || sql.contains("create table ")
				|| sql.contains("create db ") || sql.contains("create view ") || sql.contains("update ")
				|| sql.contains(";") || sql.contains("\\") || sql.contains("-- ")) {
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * 消除字符串中的文本格式信息
	 * </p>
	 * @param string
	 * @param replacement
	 * @return java.lang.String
	 * @author wuyu
	 * @date 2019/12/18 10:48
	 **/
	public static String eliminateFormatting(String string, String replacement) {
		return string.replaceAll("[\\n\\t\\r]", replacement);
	}
}
