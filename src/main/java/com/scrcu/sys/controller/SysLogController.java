package com.scrcu.sys.controller;

import com.scrcu.common.base.BaseController;
import com.scrcu.common.base.vo.AjaxResult;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.common.utils.FileUtil;
import com.scrcu.sys.entity.SysLog;
import com.scrcu.sys.entity.SysParam;
import com.scrcu.sys.service.SysLogService;
import com.scrcu.sys.service.SysParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 系统日志管理
 * @Author 贺鹏飞
 * @Date 2019/10/24 17:55
 * @Version 1.0
 **/
@Controller
public class SysLogController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(SysLogController.class);

	@Autowired
	private SysLogService service;
	@Autowired
	private SysParamService sysParamService;

	/**
	 * <p>
	 * 返回系统日志查看服务器的前端页面
	 * </p>
	 * @return java.lang.String
	 * @author hepengfei
	 * @date 2019/9/20 16:16
	 **/
	@RequestMapping("syslog/toSysLogList")
	public String syslogIndex(HttpServletRequest request) {
		return "sys/SysLogIndex";
	}
	/**
	 * <p>
	 * 返回数据接口前端页面
	 * </p>
	 * @return java.lang.String
	 * @author hepengfei
	 * @date 2019/9/20 16:16
	 **/
	@RequestMapping("syslog/toSearchSysLog")
	public String syslogList(HttpServletRequest request,SysLog sysLog) {
		request.setAttribute("sysLog",sysLog);
		return "sys/SysLogList";
	}

	/**
	 * <p>
	 * 返回所有的系统参数
	 * </p>
	 * @return java.lang.String
	 * @author hepengfei
	 * @date 2019/9/20 16:16
	 **/
	@RequestMapping("syslog/listAllSysLogParam")
	public String listAllSysLogParam(HttpServletRequest request)
	{
		return "sys/SysLogList";
	}

	/**
	 * 描述：获取日志服务器参数
	 * @param request
	 * @return
	 */
	@PostMapping("syslog/getSysParamList")
	public @ResponseBody DataGrid getSysParamList(HttpServletRequest request,String paramType) {
		return this.service.listAllSysLogParam(this.getPage(request),paramType);
	}

	/**
	 * 描述：获取服务器上的日志文件
	 * @param sysLog
	 * @param id		树节点ID
	 * @return
	 */
	@RequestMapping("syslog/ListAllSysLog")
	@ResponseBody
	public String listAllSysLog(SysLog sysLog,String id){
		return service.listAllSysLog(sysLog,id);
	}


	/**
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("syslog/downloadSysLogFile")
	@ResponseBody
	public AjaxResult downloadSysLogFile(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
		String[] arr = url.trim().split(",");
		String path = arr[0];
		String ip = arr[1];
		String port = arr[2];
		String userName = arr[3];
		String pwd = arr[4];
		String localPath = "";
		try{
			//获取文件下载路径
			SysParam sysParam = sysParamService.getById("SYSLOG0011");
			localPath  =  sysParam.getParamValue();
		}catch(Exception e){
          e.printStackTrace();
		}
		//-下载文件
		boolean result = FileUtil.downloadFileOne(ip,Integer.parseInt(port),userName,pwd,path,localPath);
		if (result){
			return new AjaxResult(true,"下载成功","","");
		}else{
			return new AjaxResult(false,"下载失败","","");
		}
	}


}
