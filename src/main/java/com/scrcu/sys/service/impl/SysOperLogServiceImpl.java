package com.scrcu.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.common.utils.EhcacheUtil;
import com.scrcu.sys.entity.SysOperlog;
import com.scrcu.sys.mapper.SysOperLogMapper;
import com.scrcu.sys.service.SysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 描述： 操作日志接口实现类
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/16 10:07
 */
@Service("sysOperLogService")
public class SysOperLogServiceImpl
        extends ServiceImpl<SysOperLogMapper, SysOperlog> implements SysOperLogService {
    @Autowired(required = false)
    private SysOperLogMapper mapper;

    @Override
    public int insert(SysOperlog sysOperLog) {
        return this.baseMapper.insert(sysOperLog);
    }

    @Override
    public int delete(SysOperlog sysOperLog) {
        return this.baseMapper.deleteById(sysOperLog.getId());
    }

    @Override
    public int update(SysOperlog sysOperLog) {
        return this.baseMapper.updateById(sysOperLog);
    }

    @Override
    public SysOperlog findById(String id) {
        return this.baseMapper.selectById(id);
    }

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
        Page<SysOperlog> page = new Page<>(pageParameters.getPage(),pageParameters.getRows());
        //-设置查询开始时间：前30天
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_MONTH,-30);
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now.getTime());
        if (sysOperLog.getOperTm() == null && sysOperLog.getOperIp() == null){
            sysOperLog.setOperTm(date);
        }
        if ("".equals(sysOperLog.getOperTm()) && "".equals(sysOperLog.getOperIp())){
            sysOperLog.setOperTm(date);
        }
        page.setRecords(this.baseMapper.getSysOperLog(page,sysOperLog));
        List<SysOperlog> list = page.getRecords();
        for (SysOperlog log : list){
            log.setObjId(EhcacheUtil.getSysOrgInfoByOrgCd(log.getObjId()).getName());
        }
        return new DataGrid(page.getTotal(), page.getRecords());
    }
}
