package com.scrcu.sys.controller;

import com.scrcu.common.base.BaseController;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.sys.entity.SysOperlog;
import com.scrcu.sys.service.SysOperLogHistService;
import com.scrcu.sys.service.SysOperLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
public class SysOperLogController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(SysOperLogController.class);

	@Autowired
	private SysOperLogService service;
	@Autowired
	private SysOperLogHistService histService;


	/**
	 * <p>
	 * 分页查询操作日志信息
	 * </p>
	 * @param request HttpServletRequest
	 * @param sysOperlog 操作日志信息对象
	 * @return com.scrcu.common.base.vo.DataGrid
	 * @author hepengfei
	 * @date 2019/10/15 15:28
	 **/
	@PostMapping("sysOperlog/getList")
	public @ResponseBody DataGrid getList(HttpServletRequest request, SysOperlog sysOperlog) {
		String ifHis = sysOperlog.getQueryTyp();
		if("SYS0201".equals(ifHis)){   //查询历史数据
			return histService.listSysOperlog(sysOperlog, this.getPage(request));
		}else{
			return this.service.listSysOperlog(sysOperlog, this.getPage(request));
		}
	}

	/**
	 * <p>
	 * 返回数据接口前端页面
	 * </p>
	 * @return java.lang.String
	 * @author hepengfei
	 * @date 2019/9/20 16:16
	 **/
	@RequestMapping("sysOperlog/toSysOperlogList")
	public String index() {
		return "sys/SysOperLogList";
	}


}
