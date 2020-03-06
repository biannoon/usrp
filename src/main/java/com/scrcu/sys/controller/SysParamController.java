package com.scrcu.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scrcu.common.base.BaseController;
import com.scrcu.common.base.vo.AjaxResult;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.common.utils.DateUtil;
import com.scrcu.common.utils.EhcacheUtil;
import com.scrcu.sys.entity.SysParam;
import com.scrcu.sys.service.SysParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 描述： 系统参数控制层
 * @创建人： jiyuanbo
 * @创建时间： 2019/10/17 20:07
 */
@Controller
@RequestMapping(value = "/sysParam")
public class SysParamController extends BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    public SysParamService sysParamService;

    @RequestMapping("/toInsert")
    public ModelAndView toInsert(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/SysParamInsert");
        return mv;
    }
    @RequestMapping("/toUpdate")
    public ModelAndView toUpdate(HttpServletRequest request) throws Exception {
        String paramId = request.getParameter("paramId");
        SysParam sysParam = sysParamService.getById(paramId);
        request.setAttribute("sysParam",sysParam);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/SysParamUpdate");
        return mv;
    }
    @RequestMapping("/input")
    public ModelAndView input(HttpServletRequest request) throws Exception {
        SysParam sysParam = new SysParam();
        sysParam.setCreatr(this.getUser().getUserId());
        sysParam.setCrtDt(DateUtil.getCurrentDate("yyyy-MM-dd"));
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/SysParamDetail");
        mv.getModel().put("resultMap", sysParam);
        mv.getModel().put("action", "insert");
        return mv;
    }

    @RequestMapping("/insert")
    @ResponseBody
    public AjaxResult insert(SysParam sysParam, HttpServletRequest request) throws Exception {
        boolean bool = sysParamService.isExists(sysParam.getParamId());
        if (bool) {
            return new AjaxResult(false, "该参数已存在");
        } else {
            sysParam.setCreatr(this.getUser().getUserId());
            sysParam.setCrtDt(DateUtil.getCurrentDate("yyyy-MM-dd"));
            bool = sysParamService.insert(sysParam);
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
        String paramIdArry[] = ids.split(",");
        boolean bool = false;
        for (String paramId : paramIdArry) {
            bool = sysParamService.delete(paramId);
        }
        if (bool) {
            return new AjaxResult(true, "删除成功");
        } else {
            return new AjaxResult(false, "删除失败");
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public AjaxResult update(SysParam sysParam, HttpServletRequest request) throws Exception {
        sysParam.setFinlModifr(this.getUser().getUserId());
        sysParam.setFinlModfyDt(DateUtil.getCurrentDate("yyyy-MM-dd"));
        boolean bool = sysParamService.update(sysParam);
        if (bool) {
            return new AjaxResult(true, "修改成功");
        } else {
            return new AjaxResult(false, "修改失败");
        }
    }

    @RequestMapping("/getById")
    public ModelAndView getById(String paramId, String readonly, HttpServletRequest request) throws Exception {
        SysParam sysParam = sysParamService.getById(paramId);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/SysParamDetail");
        mv.getModel().put("resultMap", sysParam);
        mv.getModel().put("readonly", readonly);
        mv.getModel().put("action", "update");
        return mv;
    }

    @RequestMapping("/getByPage")
    @ResponseBody
    public DataGrid getByPage(SysParam sysParam, HttpServletRequest request) throws Exception {
        Page page = new Page(this.getPage(request).getPage(), this.getPage(request).getRows());
        IPage<SysParam> iPage = sysParamService.getByPage(page, sysParam);
        List<SysParam> list = iPage.getRecords();
        for (SysParam param : list){
            if (param != null){
                param.setParamTyp(EhcacheUtil.getSingleSysDictryCdByCache(param.getParamTyp(),"SYS08").getDictryNm());
            }
        }
        return new DataGrid(iPage.getTotal(), iPage.getRecords());
    }

    @RequestMapping("/uniqueParamId")
    @ResponseBody
    public String uniqueParamId(String paramId) throws Exception {
        String result = "0";//0代表false
        SysParam param = sysParamService.getById(paramId);
        if (param != null){
            result = "1";
        }
        return result;
    }
}
