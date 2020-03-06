package com.scrcu.common.annotation;

import com.scrcu.common.utils.CommonUtil;
import com.scrcu.sys.entity.SysOperlog;
import com.scrcu.sys.entity.SysUser;
import com.scrcu.sys.service.SysOperLogService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.ShiroHttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;


/**
 * @Author pengjuntao
 * @Date 2019/10/8 8:44
 * @Version 1.0
 * @function AOP切面-生成操作日志并插入数据库
 */
@Aspect
@Component
public class LogAspect {

    private final static Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private SysOperLogService sysOperLogService;

    @Pointcut("@annotation(com.scrcu.common.annotation.LogAnnotation)")
    public void pointcut(){}

    /**
     * 描述：around切面
     * @param point
     * @return
     *//*
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point){
        Object result = null;
        try {
            log.info("开始生成操作日志");
            result = point.proceed();
            insertLog(point);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }*/

    /**
     * 描述：after切面
     * @param point
     * @return
     */
    @After("pointcut()")
    public void after(JoinPoint point){
        try {
            log.info("----开始生成操作日志----");
            insertLog(point);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * 数据库插入操作日志
     * @param point
     */
    public void insertLog(JoinPoint point){
        log.info("----开始向数据库插入操作日志----");
        MethodSignature signature = (MethodSignature) point.getSignature();
        //获取业务方法
        Method method = signature.getMethod();
        //反射获取业务方法参数值
        Object[] parameterValues = point.getArgs();
        //封装日志对象
        SysOperlog sysOperlog = constructLog(method,parameterValues);
        try {
            sysOperLogService.insert(sysOperlog);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("----插入'系统操作日志'失败----");
        }
    }

    /**
     * 封装日志对象
     * @param method 目标业务方法
     * @param parameterValues 目标业务方法参数
     * @return
     */
    public SysOperlog constructLog(Method method, Object[] parameterValues){

        SysOperlog operLog = new SysOperlog();
        //获取方法上自定义注解的属性,并作为日志信息，插入到操作日志中
        LogAnnotation annotation = method.getAnnotation(LogAnnotation.class);
        if (annotation != null){
            operLog.setOperTyp(annotation.operType());//操作类型
            operLog.setOperation(annotation.operation());//操作描述
        }
        if(null != parameterValues && parameterValues.length > 0){//如果参数不为null
            String objId = "";//操作主键
            for (int i = 0; i < parameterValues.length; i++) {//遍历参数值
                if (parameterValues[i] != null){
                    if (parameterValues[i].getClass().equals(ShiroHttpServletRequest.class)){
                        //如果参数值的类型为ShiroHttpServletRequest,则通过request获取IP
                        operLog.setOperIp(CommonUtil.getIp((HttpServletRequest) parameterValues[i]));
                    }else if(parameterValues[i].getClass().equals(ShiroHttpServletResponse.class)){
                        objId = "";
                    }else if (parameterValues[i].getClass().equals(String.class)){
                        //如果参数值的类型为String类型，则默认该参数值为主键ID；
                        //如果存在联合主键，则进行拼接
                        if ("".equalsIgnoreCase(objId)){
                            objId = objId + parameterValues[i].toString();
                        }else{
                            objId = objId + "_" + parameterValues[i].toString();
                        }
                    }else if(parameterValues[i] instanceof Object[]){
                        //如果参数类型为数组
                        objId = "";
                    }else if(parameterValues[i] instanceof Collection){
                        //参数类型为集合
                        objId = "";
                    }else if(parameterValues[i] instanceof Map){
                        //参数类型为map集合
                        objId = "";
                    }else{
                        try {
                            //通过注解的属性id获取到实体类的主键或者联合主键，通过主键获取对于的值
                            for (int j = 0; j < annotation.id().length; j++) {
                                if ("".equalsIgnoreCase(objId)){
                                    objId = objId + String.valueOf(PropertyUtils.getProperty(parameterValues[i],annotation.id()[j]));
                                }else{
                                    objId = objId + "_" + String.valueOf(PropertyUtils.getProperty(parameterValues[i],annotation.id()[j]));
                                }
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            operLog.setObjId(objId);
        }
        //日志ID
        operLog.setId(CommonUtil.getUUID(32));
        //获取操作时间
        operLog.setOperTm(new Timestamp(System.currentTimeMillis()).toString());
        //获取操作人
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getSession().getAttribute("sysUser");
        operLog.setOperator(sysUser.getUserId());

        return operLog;
    }

}
