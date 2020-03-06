package com.scrcu.common.utils;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName demotest
 * @Description TODO    项目的启动任务
 * @Author pengjuntao
 * @Date 2020/2/19 11:57
 * @Version 1.0
 */
@Component
@Order(2)
public class AppcalitionTaskUtil implements ApplicationRunner{

    @Override
    public void run(ApplicationArguments args) throws Exception {
       /* SysFuncService sysFuncService =
                (SysFuncServiceImpl) SpringUtil.getBean("sysFuncServiceImpl");
        List<SysFunc> funcs = sysFuncService.getAllFunc();
        SysRoleFuncRelService sysRoleFuncRelService =
                (SysRoleFuncRelService) SpringUtil.getBean("sysRoleFuncRelServiceImpl");
        int count = 1;
        List<SysRoleFuncRel> relList = new ArrayList<>();
        for (SysFunc func : funcs){
            SysRoleFuncRel rel = new SysRoleFuncRel();
            rel.setId(Integer.parseInt(sysRoleFuncRelService.selectMaxId())+count);
            rel.setRoleId("ADMIN");
            rel.setFuncId(func.getFuncId());
            relList.add(rel);
            count ++ ;
        }
        sysRoleFuncRelService.insertRoleFuncs(relList);*/
    }
}
