package com.scrcu.common.base;

import com.scrcu.common.base.bo.DataGridAscTaskSearchParas;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.utils.CommonUtil;
import com.scrcu.common.utils.DateUtil;
import com.scrcu.sys.entity.SysOperlog;
import com.scrcu.sys.entity.SysUser;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 基础前端控制器类
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/9/6 16:41
 **/
public class BaseController {

	/**
	 * <p>
	 * 分页查询时从request请求中获取页数和行数信息
	 * </p>
	 * @param request HttpServletRequest
	 * @return com.scrcu.common.base.bo.PageParameters
	 * @author wuyu
	 * @date 2019/9/5 14:17
	 **/
	protected PageParameters getPage(HttpServletRequest request) {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		return new PageParameters(StringUtils.isEmpty(page) ? 0 : Integer.valueOf(page),
				StringUtils.isEmpty(rows) ? 0 : Integer.valueOf(rows));
	}

	/**
	 * <p>
	 * 获取请求IP地址
	 * </p>
	 *
	 * 2019-10-18 <br/>
	 * 由于记录操作日志使用切面完成，因此不必在此次获取IP，获取IP转由CommonUtil.getIp(request)方法完成。
	 *
	 * @param request HttpServletRequest
	 * @return java.lang.String
	 * @author wuyu
	 * @date 2019/9/13 1:15
	 **/
	@Deprecated
	protected String getIp(HttpServletRequest request) {
		return CommonUtil.getIp(request);
	}

	/**
	 * <p>
	 * 获取操作日志对象
	 * </p>
	 * @param request HttpServletRequest
	 * @return com.scrcu.sys.entity.SysOperlog
	 * @author wuyu
	 * @date 2019/10/17 9:16
	 **/
	protected SysOperlog getLogObject(HttpServletRequest request) {
		SysOperlog log = new SysOperlog();
		log.setId(CommonUtil.getUUID(32));
		log.setOperIp(CommonUtil.getIp(request));
		log.setOperator(getUser().getUserId());
		log.setOperTm(DateUtil.getNowDate("yyyy-MM-dd HH:mm:ss"));
		return log;
	}

	/**
	 * <p>
	 * 获取用户信息对象
	 * </p>
	 * @return com.scrcu.sys.entity.SysUser
	 * @author wuyu
	 * @date 2019/9/20 17:14
	 **/
	protected SysUser getUser() {
		return (SysUser) SecurityUtils.getSubject().getSession().getAttribute("sysUser");
	}

	/**
	 * 描述：获取任务调度查询条件参数
	 * @author pengjuntao
	 */
	public DataGridAscTaskSearchParas getDataGridAscTaskSearchParas(HttpServletRequest request){
		DataGridAscTaskSearchParas data = new DataGridAscTaskSearchParas();
		data.setTaskId(request.getParameter("taskId")==null?"":request.getParameter("taskId"));
		data.setTaskName(request.getParameter("taskName")==null?"":request.getParameter("taskName"));
		data.setTaskCategory(request.getParameter("taskCategory")==null?"":request.getParameter("taskCategory"));
		data.setFrequency(request.getParameter("frequency")==null?"":request.getParameter("frequency"));
		data.setPriority(request.getParameter("priority")==null?"":request.getParameter("priority"));
		data.setState(request.getParameter("state")==null?"":request.getParameter("state"));
		data.setNextDate(request.getParameter("nextDate")==null?"":request.getParameter("nextDate"));
		data.setNextDate_start(request.getParameter("nextDate_start")==null?"":request.getParameter("nextDate_start"));
		data.setNextDate_end(request.getParameter("nextDate_end")==null?"":request.getParameter("nextDate_end"));
		data.setCrtDate_start(request.getParameter("crtDate_start")==null?"":request.getParameter("crtDate_start"));
		data.setCrtDate_end(request.getParameter("crtDate_end")==null?"":request.getParameter("crtDate_end"));
		return data;
	}
}
