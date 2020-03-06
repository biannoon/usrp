package com.scrcu.sys.controller;

import com.scrcu.common.base.BaseController;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.AjaxResult;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.common.utils.EhcacheUtil;
import com.scrcu.sys.entity.SysDictryCd;
import com.scrcu.sys.entity.SysUser;
import com.scrcu.sys.service.SysDictryCdService;
import com.scrcu.sys.service.impl.SysDictryCdServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class SysDictryCdController extends BaseController {

	@Autowired
	private SysDictryCdService service;
	@Autowired
	private SysDictryCdServiceImpl sysDictryCdService;

	/**
	 * <p>
	 * 分页查询字典代码信息
	 * </p>
	 * @param request HttpServletRequest
	 * @param dictionary 字典代码信息对象
	 * @return com.scrcu.common.base.vo.DataGrid
	 * @author hepengfei
	 * @modifier pengjuntao
	 * @date 2019/9/26 15:28
	 **/
	@PostMapping("dictionary/getList")
	public @ResponseBody DataGrid getList(HttpServletRequest request, SysDictryCd dictionary) {
		return this.service.listDictionaryCode(dictionary, this.getPage(request));
	}

	/**
	 * <p>
	 * 分页查询父节点下字典代码信息
	 * </p>
	 * @param request HttpServletRequest
	 * @return com.scrcu.common.base.vo.DataGrid
	 * @author hepengfei
	 * @date 2019/9/26 15:28
	 **/
	@PostMapping("dictionary/getChildList")
	public @ResponseBody DataGrid getChildList(HttpServletRequest request,String blngtoDictryId) {
		SysUser user = this.getUser();
		PageParameters pageParameters = this.getPage(request);
		return this.service.listDictionaryCodeByPid(blngtoDictryId, pageParameters,user);
	}

	/**
	 * <p>
	 * 返回数据接口前端页面
	 * </p>
	 * @return java.lang.String
	 * @author hepengfei
	 * @date 2019/9/20 16:16
	 **/
	@RequestMapping("dictionary/toSysDictryCdList")
	public String index() {
		return "sys/SysDictryCdList";
	}

	/**
	 * 描述：跳转任务新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("dictionary/toSysDictryCdInsert")
	public String toInsert(HttpServletRequest request, HttpServletResponse response){
		return "sys/SysDictryCd_insert";
	}

	/**
	 * 描述：打开代码值新增窗口
	 * @param request
	 * @param flag				标记打开新增字典代码值窗口的位置    insert:新增时  list:列表页面
	 * @param winId
	 * @return
	 * @author pengjuntao
	 */
	@RequestMapping("dictionary/toSysDictryCdInsertValue")
	public String toInsertValue(HttpServletRequest request,
								String flag,
								String winId){
		request.setAttribute("flag",flag);
		request.setAttribute("winId",winId);
		return "sys/SysDictryCd_insertVal";
	}

	/**
	 * 描述：打开代码值修改窗口
	 * @param request
	 * @param dictryId
	 * @param flag				标记打开新增字典代码值窗口的位置    insert:新增时  list:列表页面
	 * @param winId
	 * @return
	 * @author pengjuntao
	 */
	@RequestMapping("dictionary/toSysDictryCdUpdateValue")
	public String toUpdateValue(HttpServletRequest request,
								String blngtoDictryId,
								String dictryId,
								String flag,
								String winId){
		SysUser user = this.getUser();
		if ("cache".equals(flag)){
			request.setAttribute("sysDictryCd",sysDictryCdService.getSysDictryCdByDictryIdFromCache(dictryId,user));
		} else {
			request.setAttribute("sysDictryCd",sysDictryCdService.getSysDictryCdByDictryId(dictryId,blngtoDictryId));
		}
		request.setAttribute("flag",flag);
		request.setAttribute("winId",winId);
		return "sys/SysDictryCd_updateVal";
	}

	/**
	 * 描述：新增代码值到缓存中，待字典类型提交时，一并提交
	 * @return
	 * @author pengjuntao
	 */
	@RequestMapping("dictionary/insertValueToCache")
	@ResponseBody
	public AjaxResult insertValueToCache(SysDictryCd sysDictryCd){
		SysUser user = this.getUser();
		return sysDictryCdService.insertValueToCache(sysDictryCd,user);
	}

	/**
	 * 描述：从缓存中修改代码值信息
	 * @param sysDictryCd
	 * @return
	 * @author pengjuntao
	 */
	@RequestMapping("dictionary/updateValueFromCache")
	@ResponseBody
	public AjaxResult updateValueFromCache(SysDictryCd sysDictryCd, String dictryId_hid){
		SysUser user = this.getUser();
		return sysDictryCdService.updateValueFromCache(sysDictryCd,dictryId_hid,user);
	}

	/**
	 * 描述：从缓存中删除代码值
	 * @param dictryId
	 * @return
	 * @author pengjuntao
	 */
	@RequestMapping("dictionary/deleteValueFromCache")
	@ResponseBody
	public AjaxResult deleteValueFromCache(String dictryId){
		SysUser user = this.getUser();
		return sysDictryCdService.deleteValueFromCache(dictryId,user);
	}

	/**
	 * 描述：从数据库中新增代码值
	 * @param sysDictryCd
	 * @param dictryCategory
	 * @return
	 * @author pengjuntao
	 */
	@RequestMapping("dictionary/insertValueToDB")
	@ResponseBody
	public AjaxResult insertValueToDB(SysDictryCd sysDictryCd,String dictryCategory){
		return sysDictryCdService.insertValueToDB(sysDictryCd,dictryCategory);
	}

	/**
	 * 描述：从数据库中修改代码值
	 * @param sysDictryCd
	 * @param dictryId_hid
	 * @return
	 */
	@RequestMapping("dictionary/updateValueFromDB")
	@ResponseBody
	public AjaxResult updateValueFromDB(SysDictryCd sysDictryCd, String dictryId_hid){
		return sysDictryCdService.updateValueFromDB(sysDictryCd,dictryId_hid);
	}
	/**
	 * 描述：从数据库中删除代码值
	 * @param dictryId
	 * @param blngtoDictryId
	 * @return
	 * @author pengjuntao
	 */
	@RequestMapping("dictionary/deleteValueFromDB")
	@ResponseBody
	public AjaxResult deleteValueFromDB(String dictryId, String blngtoDictryId){
		return sysDictryCdService.deleteValueFromDB(dictryId,blngtoDictryId);
	}


	/**
	 * 描述：新增系统字典代码
	 * @param sysDictryCd
	 * @author pengjuntao
	 * @return
	 */
	@RequestMapping("dictionary/insertSysDictryCd")
	@ResponseBody
	public AjaxResult insertSysDictryCd(SysDictryCd sysDictryCd){
		SysUser user = this.getUser();
		return sysDictryCdService.insertSysDictryCd(sysDictryCd,user);
	}

	/**
	 * 描述：跳转字典代码修改窗口
	 * @param request
	 * @param dictryId
	 * @param blngtoDictryId
	 * @return
	 * @author pengjuntao
	 */
	@RequestMapping("dictionary/toSysDictryCdUpdate")
	public String toUpdate(HttpServletRequest request, String dictryId, String blngtoDictryId){
		SysDictryCd sysDictryCd = sysDictryCdService.getSysDictryCdByDictryId(dictryId, blngtoDictryId);
		request.setAttribute("sysDictryCd",sysDictryCd);
		//-将查询出来的代码值集合存入缓存中
		SysDictryCd dic = new SysDictryCd();
		dic.setBlngtoDictryId(blngtoDictryId);
		dic.setCdTyp("SYS0102");
		EhcacheUtil.initTempCache("SYS_DICTRY_VALUE_INSERT_" + this.getUser().getUserId(), sysDictryCdService.listDictionaryCodeByPidWithoutPage(dic));
		return "sys/SysDictryCd_update";
	}

	/**
	 * 描述：修改系统字典代码
	 * @param sysDictryCd
	 * @author pengjuntao
	 * @return
	 */
	@RequestMapping("dictionary/updateSysDictryCd")
	@ResponseBody
	public AjaxResult updateSysDictryCd(SysDictryCd sysDictryCd){
		SysUser user = this.getUser();
		return sysDictryCdService.updateSysDictryCd(sysDictryCd, user);
	}

	/**
	 * 描述：删除系统字典
	 * @param sysDictryCd
	 * @return
	 */
	@RequestMapping("dictionary/deleteSysDictryCd")
	@ResponseBody
	public AjaxResult deleteSysDictryCd(SysDictryCd sysDictryCd){
		return sysDictryCdService.deleteSysDictryCd(sysDictryCd);

	}


	/**
	 * 描述：检查字典代码的唯一性
	 * @param dictryId
	 * @param blngtoDictryId
	 * @param flag
	 * @return
	 */
	@RequestMapping("dictionary/checkDictryId")
	@ResponseBody
	public AjaxResult checkDictryId(String dictryId,String blngtoDictryId,String flag){
		SysDictryCd sysDictryCd = null;
		if ("cache".equals(flag)){
			SysUser user = this.getUser();
			sysDictryCd = sysDictryCdService.getSysDictryCdByDictryIdFromCache(dictryId,user);
		}else{
			sysDictryCd = sysDictryCdService.getSysDictryCdByDictryId(dictryId,blngtoDictryId);
		}
		if (sysDictryCd == null || sysDictryCd.getDictryId() == null || "".equals(sysDictryCd.getDictryId())){
			return new AjaxResult(true,"","","");
		}else {
			return new AjaxResult(false,"","","");
		}
	}

	/**
	 * 跳转查看引用页面
	 * @param request
	 * @return
	 */
	@RequestMapping("dictionary/toSysDictryCdShowDraw")
	public String toSysDictryCdShowDraw(HttpServletRequest request){
		return "sys/SysDictryCd_showDraw";
	}

	/**
	 * 描述获取引用列表
	 * @param request
	 * @return
	 */
	@RequestMapping("dictionary/getListDraw")
	public @ResponseBody DataGrid getDrawListById(HttpServletRequest request,String dictryId ){
		return this.service.getDrawListById(dictryId, this.getPage(request));
	}


	@RequestMapping("dictionary/clearCache")
	@ResponseBody
	public void clearCache(){
		SysUser user = this.getUser();
		EhcacheUtil.cacheRemoveByKey(EhcacheUtil.CACHE_TEMP,"SYS_DICTRY_VALUE_INSERT_" + user.getUserId());
	}




	/**
	 * <p>
	 * 删除字典代码码值接口信息
	 * </p>
	 * @param request HttpServletRequest
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author hepengfei
	 * @date 2019/9/20 9:39
	 **/
	@PostMapping("dictionary/deleteVal")
	public @ResponseBody AjaxResult delete(HttpServletRequest request) {
		try {
			String dictryId = request.getParameter("dictryId");
			String blngtoDictryId = request.getParameter("blngtoDictryId");

			return this.service.deleteDictionCode(dictryId, blngtoDictryId,
					this.getUser().getUserId(),this.getIp(request));
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxResult(false, "删除字典代码信息时发生异常。");
		}
	}
	/**
	 * <p>
	 * 删除字典代码种类和码值接口信息
	 * </p>
	 * @param request HttpServletRequest
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author hepengfei
	 * @date 2019/10/29 9:39
	 **/
	@PostMapping("dictionary/delete")
	public @ResponseBody AjaxResult deleteDicAndVal(HttpServletRequest request) {
		try {
			String dictryId = request.getParameter("dictryId");
			String blngtoDictryId = request.getParameter("blngtoDictryId");
			return this.service.deleteDictionCodeAndVal(dictryId, blngtoDictryId,
					this.getUser().getUserId(),this.getIp(request));
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxResult(false, "删除字典代码信息时发生异常。");
		}
	}
	/**
	 * 通过字典代码类型查询字典代码集合
	 * @param request
	 * @param dictryId
	 * @author pengjuntao
	 * @return
	 */
	@RequestMapping("dictionary/getSysDictryCdListById")
	@ResponseBody
	public List<SysDictryCd> getSysDictryCdListById(HttpServletRequest request,String dictryId){
		List<SysDictryCd> list = null;
		if ("root".equals(dictryId)){
			list = sysDictryCdService.listForComboBox("root");
		}else{
			list = EhcacheUtil.getSysDictryCdByCache(dictryId);
		}
		return list;
	}

}
