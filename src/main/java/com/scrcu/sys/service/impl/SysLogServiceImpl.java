package com.scrcu.sys.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.common.utils.FileUtil;
import com.scrcu.common.utils.TreeNodeUtil;
import com.scrcu.sys.entity.SysLog;
import com.scrcu.sys.entity.SysParam;
import com.scrcu.sys.mapper.SysLogMapper;
import com.scrcu.sys.service.SysLogService;
import com.scrcu.sys.service.SysParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述： 系统日志接口实现类
 * @创建人： hepengf
 * @创建时间： 2019/10/24 10:07
 */
@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Autowired(required = false)
    private SysLogMapper mapper;
    @Autowired
    private SysParamService sysParamService;


    @Override
    public  String listAllSysLog(SysLog sysLog,String id) {
        List<SysLog> list = null;
        try{
            if (id == null || "".equals(id)){
                list = FileUtil.listFileName(sysLog.getIp(),
                        Integer.parseInt(sysLog.getPort()),
                        sysLog.getUser(),
                        sysLog.getPwd(),
                        sysLog.getUrl());
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("系统日志：系统日志列表查询失败");
        }
        return JSON.toJSONString(TreeNodeUtil.fillSysLogTree(list));
    }


    public DataGrid listAllSysLogParam(PageParameters pageParameters,String paramType) {
        DataGrid result = sysParamService.getSysParamByParamType(pageParameters, paramType);
        List<SysParam> sysParamList = result.getRows();
        List<SysLog> returnList = new ArrayList<>();
        if (sysParamList != null && sysParamList.size() > 0){
            for (SysParam param : sysParamList){
                String paramValue = param.getParamValue();
                String[] arr = paramValue.trim().split("&");
                Map<String,String> map = new HashMap<>();
                for (int i = 0; i < arr.length; i ++){
                    int index = arr[i].indexOf("=");
                    if (index != -1){
                        map.put(arr[i].substring(0,index),arr[i].substring(index+1));
                    }
                }
                SysLog sysLog = new SysLog();
                sysLog.setIp(map.get("ip"));
                sysLog.setPort(map.get("port"));
                sysLog.setUrl(map.get("url"));
                sysLog.setUser(map.get("user"));
                sysLog.setPwd(map.get("pwd"));
                returnList.add(sysLog);
            }
        }
        result.setRows(returnList);
        return result;
    }


}
