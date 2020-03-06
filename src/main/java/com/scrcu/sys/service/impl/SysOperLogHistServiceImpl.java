package com.scrcu.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.common.utils.EhcacheUtil;
import com.scrcu.sys.entity.SysOperlog;
import com.scrcu.sys.entity.SysOperlogHist;
import com.scrcu.sys.mapper.SysOperLogHistMapper;
import com.scrcu.sys.service.SysOperLogHistService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 描述： 操作日志历史接口实现类
 * @创建人： hepengfei
 * @创建时间： 2019/11/04 10:07
 */
@Service("sysOperLogHistService")
public class SysOperLogHistServiceImpl
        extends ServiceImpl<SysOperLogHistMapper, SysOperlogHist> implements SysOperLogHistService {

    /**
     * <p>
     * 分页查询操作日志信息
     * </p>
     * @param pageParameters 操作日志信息对象
     * @return com.scrcu.common.base.vo.DataGrid
     * @author hepengfei
     * @date 2019/10/16 15:24
     **/
    public DataGrid listSysOperlog(SysOperlog sysOperLog, PageParameters pageParameters) {
        Page<SysOperlogHist> page = new Page<>(pageParameters.getPage(),pageParameters.getRows());
        //-设置查询开始时间：前30天
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_MONTH,-60);
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now.getTime());
        if (sysOperLog.getOperTm() == null && sysOperLog.getOperIp() == null){
            sysOperLog.setOperTm(date);
        }
        if ("".equals(sysOperLog.getOperTm()) && "".equals(sysOperLog.getOperIp())){
            sysOperLog.setOperTm(date);
        }
        page.setRecords(this.baseMapper.getSysOperLog(page,sysOperLog));
        List<SysOperlogHist> list = page.getRecords();
        for (SysOperlogHist log : list){
            log.setObjId(EhcacheUtil.getSysOrgInfoByOrgCd(log.getObjId()).getName());
        }
        return new DataGrid(page.getTotal(), page.getRecords());
    }

}
