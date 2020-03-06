package com.scrcu.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;


/**
 * 描述: 读取配置文件工具类
 *  hepengfei
 *  2019-9-4
 */

public class ReadConfigPropUtil {
	public  static Logger log = LoggerFactory.getLogger(SftpUtil.class);


	/**
	 * @desc 配置文件读取
	 * @author hepengfei
	 * @param读取文件路径
	 * @date 2019-09-04
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap<String,String> ReadConfigData(String path) {
		HashMap dataMap = new HashMap();
		log.debug("装载properties文件开始");
		InputStream fis = ReadConfigPropUtil.class.getClassLoader()
				.getResourceAsStream(path);
		Properties pop = new Properties();
		try {
			pop.load(fis);
		} catch (IOException e) {
			log.error("装载properties文件异常" + e.getMessage());
		}
		Enumeration enumeration = pop.keys();

		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement().toString();
			log.debug("配置文件内容[ " + key + " ]=" + pop.getProperty(key));
			dataMap.put(key, pop.getProperty(key));
		}
		log.debug("装载properties文件结束");
		return dataMap;
	}

	/**
	 * 读取properties配置文件（防止中文乱码）
	 * @param path 路径
	 * @author pengjuntao
	 * @return
	 */
	public static HashMap<String,String> ReadPropConfigData(String path){
		HashMap dataMap = new HashMap();
		log.debug("----加载.properties文件开始----");
		InputStreamReader reader = null;
		Properties prop = null;
		try {
			reader = new InputStreamReader(ReadConfigPropUtil.class.getClassLoader().getResourceAsStream(path),"GBK");
			prop= new Properties();
			prop.load(reader);
		} catch (Exception e) {
			log.error("-----加载config.properties文件异常----" + e.getMessage());
		}
		//遍历加载数据
		Enumeration enumeration = prop.keys();
		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement().toString();
			dataMap.put(key, prop.getProperty(key));
		}
		log.debug("-----加载.properties文件结束----");
		return dataMap;
	}



}
