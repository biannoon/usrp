package com.scrcu.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scrcu.common.base.BaseController;
import com.scrcu.common.base.vo.AjaxResult;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.common.utils.DateUtil;
import com.scrcu.sys.entity.SysDatabase;
import com.scrcu.sys.service.SysDatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 描述： 数据源信息控制层
 * @创建人： jiyuanbo
 * @创建时间： 2019/10/17 20:07
 */
@Controller
@RequestMapping(value = "/sysDatabase")
public class SysDatabaseController extends BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    public SysDatabaseService sysDatabaseService;

    @RequestMapping("/input")
    public ModelAndView input(HttpServletRequest request) throws Exception {
        SysDatabase sysDatabase = new SysDatabase();
        sysDatabase.setCreatr(this.getUser().getUserId());
        sysDatabase.setCrtDt(DateUtil.getCurrentDate("yyyy-MM-dd"));
        sysDatabase.setFinlModifr(this.getUser().getUserId());
        sysDatabase.setFinlModfyDt(DateUtil.getCurrentDate("yyyy-MM-dd"));
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/SysDatabaseDetail");
        mv.getModel().put("resultMap", sysDatabase);
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
            request.setAttribute("sysDatabase",sysDatabaseService.getById(id));
        }
        request.setAttribute("type",type);
        return "sys/SysDatabase_detail";
    }

    @RequestMapping("/insert")
    @ResponseBody
    public AjaxResult insert(SysDatabase sysDatabase, HttpServletRequest request) throws Exception {
        sysDatabase.setCreatr(this.getUser().getUserId());
        sysDatabase.setCrtDt(DateUtil.getCurrentDate("yyyy-MM-dd"));
        sysDatabase.setFinlModifr(this.getUser().getUserId());
        sysDatabase.setFinlModfyDt(DateUtil.getCurrentDate("yyyy-MM-dd"));
        boolean bool = sysDatabaseService.insert(sysDatabase);
        if (bool) {
            return new AjaxResult(true, "新增成功");
        } else {
            return new AjaxResult(false, "新增失败");
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public AjaxResult delete(String ids, HttpServletRequest request) throws Exception {
        String idArry[] = ids.split(",");
        boolean bool = false;
        for (String id : idArry) {
            bool = sysDatabaseService.delete(id);
        }
        if (bool) {
            return new AjaxResult(true, "删除成功");
        } else {
            return new AjaxResult(false, "删除失败");
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public AjaxResult update(SysDatabase sysDatabase, HttpServletRequest request) throws Exception {
        sysDatabase.setFinlModifr(this.getUser().getUserId());
        sysDatabase.setFinlModfyDt(DateUtil.getCurrentDate("yyyy-MM-dd"));
        boolean bool = sysDatabaseService.update(sysDatabase);
        if (bool) {
            return new AjaxResult(true, "修改成功");
        } else {
            return new AjaxResult(false, "修改失败");
        }
    }

    @RequestMapping("/getById")
    public ModelAndView getById(String id, String readonly, HttpServletRequest request) throws Exception {
        SysDatabase sysDatabase = sysDatabaseService.getById(id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/SysDatabaseDetail");
        mv.getModel().put("resultMap", sysDatabase);
        mv.getModel().put("readonly", readonly);
        mv.getModel().put("action", "update");
        return mv;
    }

    @RequestMapping("/getByPage")
    @ResponseBody
    public DataGrid getByPage(SysDatabase sysDatabase, HttpServletRequest request) throws Exception {
        Page page = new Page(this.getPage(request).getPage(), this.getPage(request).getRows());
        IPage<SysDatabase> iPage = sysDatabaseService.getByPage(page, sysDatabase);
        return new DataGrid(iPage.getTotal(), iPage.getRecords());
    }
}
