package com.scrcu.sys.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scrcu.common.base.BaseController;
import com.scrcu.common.base.vo.AjaxResult;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.common.utils.DateUtil;
import com.scrcu.sys.entity.SysFunc;
import com.scrcu.sys.entity.SysSubsystem;
import com.scrcu.sys.service.SysFuncService;
import com.scrcu.sys.service.SysSubsystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 描述： 子系统信息控制层
 * @创建人： jiyuanbo
 * @创建时间： 2019/10/24 15:03
 */
@Controller
@RequestMapping(value = "/sysSubsystem")
public class SysSubsystemController extends BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private SysSubsystemService sysSubsystemService;
    @Autowired
    private SysFuncService sysFuncService;

    @RequestMapping("/input")
    public ModelAndView input(HttpServletRequest request) throws Exception {
        SysSubsystem sysSubsystem = new SysSubsystem();
        sysSubsystem.setCreatr(this.getUser().getUserId());
        sysSubsystem.setCrtDt(DateUtil.getCurrentDate("yyyy-MM-dd"));
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/SysSubsystemDetail");
        mv.getModel().put("resultMap", sysSubsystem);
        mv.getModel().put("action", "insert");
        return mv;
    }


    /**
     * 描述：打开功能窗口
     * @param request
     * @param type      类型：insert-新增    update-修改   detail-详情
     * @param id
     * @return
     */
    @RequestMapping("/toDetailPage")
    public String toDetailPage(HttpServletRequest request, String type, String id) throws Exception {
        if (id != null && !"".equals(id)){
            request.setAttribute("sysSubsystem",sysSubsystemService.getById(id));
        }
        request.setAttribute("type",type);
        return "sys/SysSubsystem_detail";
    }
    @RequestMapping("/insert")
    @ResponseBody
    public AjaxResult insert(SysSubsystem sysSubsystem, HttpServletRequest request) throws Exception {
        boolean bool = sysSubsystemService.isExists(sysSubsystem.getSubsystemId());
        if (bool) {
            return new AjaxResult(false, "该子系统信息已存在");
        } else {
            sysSubsystem.setCrtDt(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            sysSubsystem.setCreatr(this.getUser().getUserId());
            sysSubsystem.setFinlModfyDt(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            sysSubsystem.setFinlModifr(this.getUser().getUserId());
            bool = sysSubsystemService.insert(sysSubsystem);
            if (bool) {
                return new AjaxResult(true, "新增成功");
            } else {
                return new AjaxResult(false, "新增失败");
            }
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public AjaxResult delete(String ids, HttpServletRequest request) throws Exception {
        System.out.println("ids:"+ids);
        String subsystemIdArry[] = ids.split(",");
        boolean bool = false;
        for (String subsystemId : subsystemIdArry) {
            bool = sysSubsystemService.delete(subsystemId);
        }
        if (bool) {
            return new AjaxResult(true, "删除成功");
        } else {
            return new AjaxResult(false, "删除失败");
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public AjaxResult update(SysSubsystem sysSubsystem, HttpServletRequest request) throws Exception {
        sysSubsystem.setFinlModifr(this.getUser().getUserId());
        sysSubsystem.setFinlModfyDt(DateUtil.getCurrentDate("yyyy-MM-dd"));
        boolean bool = sysSubsystemService.update(sysSubsystem);
        if (bool) {
            return new AjaxResult(true, "修改成功");
        } else {
            return new AjaxResult(false, "修改失败");
        }
    }

    @RequestMapping("/getById")
    public ModelAndView getById(String subsystemId, String readonly, HttpServletRequest request) throws Exception {
        SysSubsystem sysSubsystem = sysSubsystemService.getById(subsystemId);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/SysSubsystemDetail");
        mv.getModel().put("resultMap", sysSubsystem);
        mv.getModel().put("readonly", readonly);
        mv.getModel().put("action", "update");
        return mv;
    }

    @RequestMapping("/getByPage")
    @ResponseBody
    public DataGrid getByPage(SysSubsystem sysSubsystem, HttpServletRequest request) throws Exception {
        Page page = new Page(this.getPage(request).getPage(), this.getPage(request).getRows());
        IPage<SysSubsystem> iPage = sysSubsystemService.getByPage(page, sysSubsystem);
        return new DataGrid(iPage.getTotal(), iPage.getRecords());
    }

    @RequestMapping("/getSysSubsystemList")
    @ResponseBody
    public AjaxResult getSysSubsystemList(String groupId) throws Exception {
        List<SysSubsystem> subsystemList = sysSubsystemService.getSysSubsystemList(groupId);
        return new AjaxResult(true, "", JSON.toJSONString(subsystemList), "");
    }

    @RequestMapping("/uniqueSubsystemId")
    @ResponseBody
    public String uniqueSubsystemId(String subsystemId) throws Exception {
        String result = "0";//0代表false
        SysSubsystem sysSubsystem = sysSubsystemService.getById(subsystemId);
        if (sysSubsystem != null){
            result = "1";
        }
        return result;
    }

    /**
     * 描述：判断子系统下是否存在功能菜单
     * @param id
     * @return
     */
    @RequestMapping("/hasFuncUnderSystem")
    @ResponseBody
    public String hasFuncUnderSystem(String id){
        String result = "0";
        String[] arr = id.split(",");
        List<String> list = Arrays.asList(arr);
        for (int i = 0; i < list.size(); i++){
            List<SysFunc> funcList = sysFuncService.getFuncBySubsystemId(list.get(i));
            if (funcList != null && funcList.size() > 0){
                result = "1";
                break;
            }
        }
        return result;
    }
}
