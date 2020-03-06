package com.scrcu.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * 描述： 反射的Util函数集合
 * @创建人： jiyuanbo
 * @创建时间： 2019/10/9 9:08
 */
public class ReflectionUtil {

    protected static Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 描述： 返回对象中对应的属性值。
     * @param fieldName
     * @param object 对象，目前只支持3种类型对象：map、bean
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/9 9:08
     */
    public static Object returnFieldValue(String fieldName,Object object) {
        Object value = null;
        if (object instanceof Map) {
            value = ((Map)object).get(fieldName);
            if(null == value) {
                logger.info("无法获取对象中的" + fieldName + "属性值");
            }
            return value;
        } else {
            try {
                value = ReflectionUtil.getFieldValue(fieldName, object);
            } catch(Exception e) {
                logger.info("无法获取对象中的" + fieldName + "属性值");
            }
            return value;
        }
    }

    /**
     * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
     * @param fieldName
     * @param object
     * @return
     */
    public static Object getFieldValue(String fieldName, Object object) {
        Field field = getDeclaredField(fieldName, object);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
        }
        makeAccessible(field);
        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            logger.error("getFieldValue执行抛出异常", e);
        }
        return result;
    }

    /**
     * 循环向上转型, 获取对象的DeclaredField
     * @param filedName
     * @param object
     * @return
     */
    public static Field getDeclaredField(String filedName, Object object) {
        for (Class<?> superClass = object.getClass(); superClass != Object.class;
             superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(filedName);
            } catch (NoSuchFieldException e) {
                // Field 不在当前类定义, 继续向上转型
            }
        }
        return null;
    }

    /**
     * 使filed变为可访问
     * @param field
     */
    public static void makeAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers())) {
            field.setAccessible(true);
        }
    }
}
