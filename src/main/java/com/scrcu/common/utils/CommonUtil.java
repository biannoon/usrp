package com.scrcu.common.utils;

import com.scrcu.sys.entity.SysDictryCd;
import com.scrcu.sys.entity.SysOrganization;
import com.scrcu.sys.entity.SysUser;
import com.scrcu.sys.service.SysOrganizationService;
import com.scrcu.sys.service.impl.SysGroupServiceImpl;
import com.scrcu.sys.service.impl.SysOrganizationServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.*;

/**
 * 描述: 公用工具类
 *  hepengfei
 *  2019-9-4
 */
public class CommonUtil {


	/**
	 * 描述：通过传入的用户ID，获取到用户所分配到的用户组对应的机构资源
	 * @return
	 */
	public static List getAllOrgInfoByUserId(SysUser sysUser){
		SysOrganizationService sysOrganizationService =
				(SysOrganizationServiceImpl)SpringUtil.getBean("sysOrganizationServiceImpl");
		List<SysOrganization> orgList = sysOrganizationService.getSysOrgInfoFromUser(sysUser);
		List<SysOrganization> topList = getTopOrgInfo(orgList);
		return getAllOrgInfoByTop(topList);
	}

	/**
	 * 描述：通过最上层机构，获取到其对应的所有下级机构
	 * @return
	 */
	public static List getAllOrgInfoByTop(List<SysOrganization> topList){
		List<SysOrganization> resultList = new ArrayList<>();
		List<SysOrganization> allOrg = EhcacheUtil.getSysOrgInfoByCache("ALL_ORG_CACHE");
		for (SysOrganization organization : topList){
			for (SysOrganization sysOrganization : allOrg){
				if (sysOrganization.getGradationCode().length() >= organization.getGradationCode().length()
						&& sysOrganization.getGradationCode().substring(0,organization.getGradationCode().length()).equals(organization.getGradationCode())){
					resultList.add(sysOrganization);
				}
			}
		}
		return resultList;
	}
	/**
	 * 描述：通过传入机构集合，获取到最上层的机构
	 * @param user
	 * @return
	 * @author pengjuntao
	 * @Date 2020-01-16 15:04
	 */
	public static List getTopOrgInfo(List<SysOrganization> orgList){
		Map<String,List<SysOrganization>> map = new HashMap<>();
		List<SysOrganization> result = new ArrayList<>();
 		List<SysOrganization> tempOrgList = new ArrayList<>();
		tempOrgList.addAll(orgList);
		while (tempOrgList.size() != 0){
			SysOrganization minOrg = getMinOrgGrdtnCode(tempOrgList);
			map.put(minOrg.getGradationCode(),new ArrayList<>());
			for (SysOrganization sysOrganization : orgList){
				if (sysOrganization.getGradationCode().length() > minOrg.getGradationCode().length()
						&& sysOrganization.getGradationCode().substring(0,minOrg.getGradationCode().length()).equals(minOrg.getGradationCode())){
					map.get(minOrg.getGradationCode()).add(sysOrganization);
					tempOrgList.remove(sysOrganization);
				}
			}
			tempOrgList.remove(minOrg);
		}
		Set<String> keys = map.keySet();
		for (SysOrganization organization:orgList){
			if (keys.contains(organization.getGradationCode())){
				result.add(organization);
			}
		}
		return result;
	}

	/**
	 * 获取最短的机构层级代码
	 * @param orgList
	 * @return
	 * @author pengjuntao
	 * @Date 2020-01-16 15:04
	 */
	public static SysOrganization getMinOrgGrdtnCode(List<SysOrganization> orgList){
		SysOrganization org = null;
		for (int i = 0; i < orgList.size(); i++){
			if (i == 0){
				org = orgList.get(i);
			}else{
				if (org.getGradationCode().length() > orgList.get(i).getGradationCode().length()){
					org = orgList.get(i);
				}
			}
		}
		return org;
	}

	/**
	 * 描述：将传入的用户id查询出对应的查看机构权限
	 * @param  userId
	 * @return String
	 * @author pengjuntao
	 */
	public static String getDataOrgInfos(String userId) throws  Exception{
		SysGroupServiceImpl sysGroupService = (SysGroupServiceImpl)SpringUtil.getBean("sysGroupServiceImpl");
		List<String> orgList = sysGroupService.getOrgResouseByUserId(userId);
		String result = "";
		for(int i=0;i<orgList.size();i++){
         if(i==0){
			result+="'"+orgList.get(i)+"'";
		 }else if(i==orgList.size()-1){
         	result+="'"+orgList.get(i)+"'";
		 }else{
		 	result+="'"+orgList.get(i)+",";
		 }
		}
		return "and brno in("+result+")";
	}
	/**
	 * 描述：将传入的字符串格式化
	 * 例如：传入 a,b,c 格式化后 'a','b','c'
	 * @param sSource String
	 * @return String
	 * @author pengjuntao
	 */
	public static String formatStringA(String sSource) {
		if (sSource == null)
			sSource = "''";
		else if (sSource.equals(""))
			sSource = "''";
		else {
			sSource = sSource.replaceAll(",", "','");
			sSource = "'" + sSource + "'";
		}

		return sSource;
	}

	/**
	 * 描述：判断参数是否为空，为空则返回一个长度为0的整型数组，否则返回其值
	 * @param aSource 源字符串数组
	 * @return 整型数组
	 * @author pengjuntao
	 */
	public static int[] getIntArray(String[] aSource)
	{
		int iReturn[] = new int[0];
		if (aSource != null)
		{
			iReturn = new int[aSource.length];
			for (int i = 0; i < aSource.length; i++)
			{
				iReturn[i] = Integer.parseInt(aSource[i]);
			}
		}
		return iReturn;
	}

	/**
	 * 描述：将数组中的元素连成一个以逗号分隔的字符串
	 * @param aSource 源数组
	 * @return 字符串
	 * @author pengjuntao
	 */
	public static String arrayToString(String[] aSource)
	{
		return arrayToString(aSource, ",");
	}

	/**
	 * 描述：将数组中的元素连成一个以给定字符分隔的字符串
	 * @param aSource 源数组
	 * @param sChar 分隔符
	 * @return 字符串
	 * @author pengjuntao
	 */
	public static String arrayToString(String[] aSource, String sChar)
	{
		String sReturn = "";
		for (int i = 0; i < aSource.length; i++)
		{
			if (i > 0)
				sReturn += sChar;
			sReturn += aSource[i];
		}
		return sReturn;
	}

	/**
	 * 描述：将两个字符串的所有元素连结为一个字符串数组
	 * @param array1 源字符串数组1
	 * @param array2 源字符串数组2
	 * @return String[]
	 * @author pengjuntao
	 */
	public static String[] arrayAppend(String[] array1, String[] array2)
	{
		return (String[]) (arrayAppend(array1, array2));
	}

	/**
	 * 将两个对象数组中的所有元素连结为一个对象数组
	 * @param array1 源字符串数组1
	 * @param array2 源字符串数组2
	 * @return Object[]
	 */
	public static Object[] arrayAppend(Object[] array1, Object[] array2)
	{
		int iLen = 0;
		Object aReturn[];
		if (array1 == null)
			array1 = new Object[0];
		if (array2 == null)
			array2 = new Object[0];
		//获得第一个对象数组的元素个数
		iLen = array1.length;
		aReturn = new Object[iLen + array2.length];
		//将第一个对象数组的元素加到结果数组中
		for (int i = 0; i < iLen; i++)
			aReturn[i] = array1[i];
		//将第二个对象数组的元素加到结果数组中
		for (int i = 0; i < array2.length; i++)
			aReturn[iLen + i] = array2[i];
		return aReturn;
	}

	/**
	 * 拆分以逗号分隔的字符串,并存入String数组中
	 * @param sSource 源字符串
	 * @return String[]
	 */
	public static String[] stringToArray(String sSource)
	{
		return stringToArray(sSource, ",");
	}
	/**
	 * 拆分以给定分隔符分隔的字符串,并存入字符串数组中
	 * @param sSource 源字符串
	 * @param sChar 分隔符
	 * @return String[]
	 */
	public static String[] stringToArray(String sSource, String sChar)
	{
		String aReturn[] = null;
		StringTokenizer st = null;
		st = new StringTokenizer(sSource, sChar);
		int i = 0;
		aReturn = new String[st.countTokens()];
		while (st.hasMoreTokens())
		{
			aReturn[i] = st.nextToken();
			i++;
		}
		return aReturn;
	}
	/**
	 * 拆分以给定分隔符分隔的字符串,并存入整型数组中
	 * @param sSource 源字符串
	 * @param sChar 分隔符
	 * @return int[]
	 */
	public static int[] stringToArray(String sSource, char sChar)
	{
		return getIntArray(stringToArray(sSource, String.valueOf(sChar)));
	}

	/**
	 * 将数组转换成字符串，转换后的字符串首尾不含分隔符，格式如下：a,b,c 。
	 * @param a int[]
	 * @param dot 分隔符，比如: ,
	 * @param mark 引号，比如: '
	 * @return 字符串
	 */
	public static String arrayToString(int a[], String dot, String mark)
	{
		String strReturn = "";
		if (a.length == 0)
			strReturn = "";
		else if (a.length == 1)
			strReturn = mark + a[0] + mark;
		else
		{
			for (int i = 0; i < a.length - 1; i++)
				strReturn = strReturn + mark + String.valueOf(a[i]) + mark + dot;
			strReturn = strReturn + mark + String.valueOf(a[a.length - 1]) + mark;
		}
		return strReturn;
	}

	/**
	 * 将数组转换成字符串，转换后的字符串首尾不含分隔符，格式如下：a,b,c 。
	 * @param a String[]
	 * @param dot 分隔符，比如: ,
	 * @param mark 引号，比如: '
	 * @return 字符串
	 */
	public static String arrayToString(String a[], String dot, String mark)
	{
		String strReturn = "";
		if (a.length == 0)
			strReturn = "";
		else if (a.length == 1)
			strReturn = mark + a[0] + mark;
		else
		{
			for (int i = 0; i < a.length - 1; i++)
				strReturn = strReturn + mark + String.valueOf(a[i]) + mark + dot;
			strReturn = strReturn + mark + String.valueOf(a[a.length - 1]) + mark;
		}
		return strReturn;
	}

	/**
	 * 删除磁盘上的文件
	 * @param fileName 文件全路径
	 * @return boolean
	 */
	public static boolean deleteFile(String fileName)
	{
		File file = new File(fileName);
		return file.delete();
	}

	/**
	 * 获取点分格式(123,456,789.88)的显示用数据
	 * @param dlSrc 源数值
	 * @param bitNum 小数位数
	 * @return boolean
	 * @throws Exception
	 */
	public static String getDecimalAsString(double dlSrc, int bitNum)
	{
		String sSrc = String.valueOf(dlSrc);
		return getDecimalAsString(sSrc, bitNum);
	}

	/**
	 * 获取点分格式(123,456,789.88)的显示用数据
	 * @param sSrc 源数值
	 * @param bitNum 小数位数
	 * @return boolean
	 */
	public static String getDecimalAsString(String sSrc, int bitNum)
	{
		String input = "";
		String restr = "";
		String head = "";
		int flag = 0;

		double dl = Double.parseDouble(sSrc);

		BigDecimal bd = new BigDecimal(dl);
		//对传入的数字四舍五入，小数点后位数
		bd = bd.setScale(bitNum, 5);
		input = String.valueOf(bd).trim();
		int i = input.indexOf('.');
		if (i == -1)
			i = input.length();

		if (input.substring(0, 1).equals("+") || input.substring(0, 1).equals("-"))
		{
			head = input.substring(0, 1);
			flag = 1;
		}

		String inputsub = input.substring(flag, i);
		if (inputsub.length() <= 3)
		{
			restr = input;
			return restr;
		}

		int j = inputsub.length();
		while (j >= 0)
		{
			if (j > 3)
			{
				restr = "," + inputsub.substring(j - 3, j) + restr;
			}
			else
			{
				restr = inputsub.substring(0, j) + restr;
			}
			j = j - 3;
		}

		return head + restr + input.substring(i, input.length());
	}

	/**
	 * 判断参数是否为空，为空则返回0,不为空则返回其整型值
	 * @param sSource 源字符串
	 * @return 整型数
	 */
	public static int getInt(String sSource)
	{
		int iReturn = 0;
		if (sSource != null && !sSource.equals(""))
			iReturn = Integer.parseInt(sSource);
		return iReturn;
	}

	/**
	 * 判断参数是否为空，为空则返回""，否则返回其值
	 * @param sSource 源字符串
	 * @return 字符串
	 */
	public static String getString(String sSource)
	{
		String sReturn = "";
		if (sSource != null) sReturn = sSource;
		return sReturn.trim();
	}

	/**
	 * 将给定的源字符串加1
	 * 例如：“0001”　经本函数转换后返回为“0002”
	 * @param sSource :源字符串
	 * @return 返回字符串
	 */
	public static String increaseOne(String sSource)
	{
		String sReturn = null;
		int iSize = 0;

		iSize = sSource.length();

		long l = (new Long(sSource)).longValue();
		l++;
		sReturn = String.valueOf(l);

		for (int i = sReturn.length(); i < iSize; i++)
		{
			sReturn = "0" + sReturn;
		}

		return sReturn;
	}

	/**
	 * 将给定的整数转化成字符串，
	 * 结果字符串的长度为给定长度，不足位数的左端补"0"
	 * （此方法为林伟伟于 2002-10-30 所加）
	 * @param val val
	 * @param len len
	 * @return String
	 */
	public static String intToStr(int val, int len)
	{
		String sReturn = new String();

		sReturn = String.valueOf(val);

		if (sReturn.length() < len)
		{
			for (int i = len - sReturn.length(); i > 0; i--)
			{
				sReturn = "0" + sReturn;
			}
		}

		return sReturn;
	}

	/**
	 * 将String转换成HTML文本<br>
	 * 规则：<br>
	 * 1、将其中的\r\n转换为网页中换行<br>
	 * 2、将其中某段超过rowLen的文字拆成以每行rowLen字的多行;<br>
	 * 3、将其中的空格替换成网页中的空格;<br>
	 * @param str String 要转换的String
	 * @param rowLen int 每行字数（英文字数[汉字*2]）
	 * @return String 转换后的String
	 */
	public static String stringToHTML(String str, int rowLen)
	{
		StringBuffer newStr = new StringBuffer();
		String aTmp[] = str.split("\r\n");
		for (int i = 0; i < aTmp.length; i++)
		{
			byte aByte[] = aTmp[i].getBytes();

			if (aByte.length <= rowLen)
			{
				newStr.append(aTmp[i] + "<br>");
				continue;
			}

			int sbLen = 0;
			StringBuffer sbTmp = new StringBuffer();
			for (int j = 0; j < aTmp[i].length(); j++)
			{
				String s1 = aTmp[i].substring(j, j + 1);
				int nowLen = s1.getBytes().length;
				sbLen = sbLen + nowLen;

				if (sbLen > rowLen)
				{
					sbTmp.append("<RN><br>" + s1);
					sbLen = nowLen;
				}
				else if (sbLen == rowLen)
				{
					sbTmp.append(s1 + "<RN><br>");
					sbLen = 0;
				}
				else
					sbTmp.append(s1);
			}
			String lastStr = sbTmp.substring(sbTmp.length() - 4);
			if (lastStr.equals("<br>"))
				newStr.append(sbTmp.toString());
			else
				newStr.append(sbTmp.toString() + "<br>");

			if(newStr.length()>8 && newStr.substring(newStr.length()-8).equals("<RN><br>"))
				newStr.delete(newStr.length()-8,newStr.length()).append("<br>");
		}

		return newStr.toString().replaceAll(" ", "&nbsp;");
	}

	/**
	 * 将以逗号分隔的字符串里相同的内容去掉。此方法不记忆原字符串顺序。
	 * @param str String
	 * @param mark String 以此符号分隔
	 * @return String
	 * @author shantao
	 */
	public static String noRepeat(String str, String mark)
	{
		StringTokenizer st = new StringTokenizer(str, mark);
		List lt = new ArrayList();
		StringBuffer sb = new StringBuffer();
		String tmp = null;
		while (st.hasMoreTokens())
		{
			tmp = st.nextToken();
			if (!lt.contains(tmp))
				lt.add(tmp);
		}
		if (!lt.isEmpty())
			sb.append(lt.get(0));
		for (int i = 1; i < lt.size(); i++)
			sb.append(mark + lt.get(i));
		return sb.toString();
	}

	public static String getFormatDate(String chDate, String dot)
	{
		if (chDate.length() == 8)
			return chDate.substring(0, 4) + dot + chDate.substring(4, 6) + dot + chDate.substring(6, 8);
		else
			return chDate;
	}

	public static String getFormatTime(String chTime, String dot)
	{
		if (chTime.length() == 6)
			return chTime.substring(0, 2) + dot + chTime.substring(2, 4) + dot + chTime.substring(4, 6);
		else
			return chTime;
	}
	/**
	 * 将String转换成doc文本<br>
	 * 规则：<br>
	 * 1、将其中的\r\n转换为DOC中换行<br>
	 * 3、将其中的空格替换成DOC中的空格;<br>
	 * @param str String 要转换的String
	 * //@param rowLen int 每行字数（英文字数[汉字*2]）
	 * @return String 转换后的String
	 */
	public static String stringToDoc(String str)
	{

		char a =92;
		return str.replaceAll("\r\n", String.valueOf(a) + "\\par ");
	}
	public static void main(String[] args)
	{
		String[] a = new String[0];
//    a[0] = "a";
//    a[1] = "b";
//    a[2] = "c";
		String ss = CommonUtil.arrayToString(a, ",", "'");
		System.out.println("[" + ss + "]");
	}


	/**
	 * 功能：格式化字符串到规定长度
	 * @param  value size
	 * @return 格式化后的字符串
	 */
	public static  String formatStr(String value, int size) {
		StringBuffer sb = new StringBuffer();
		if (value != null) {
			sb.append(value);
			int times = 0;
			try {
				times = size -value.getBytes("GBK").length;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if (times >= 0) {
				for (int i = 0; i < times; i++) {
					sb.append(" ");
				}
			} else {
				String temp = "";
				try {
					temp = new String(value.getBytes("GBK"), 0, size);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				sb.delete(0, sb.length());
				sb.append(temp);
			}
		} else if (value == null) {
			value = "";
			sb.append(value);
			for (int i = 0; i < size; i++) {
				sb.append(" ");
			}
		}
		return sb.toString();
	}
	/**
	 * 功能：按规定长度格式化金额或数字
	 * @param  value size
	 * @return 格式化后的字符串
	 */
	public static  String formatNum(String value, int size) {
		StringBuffer sb = new StringBuffer();
		if (value != null) {
			value = value.replace(".", "");
			int len = value.getBytes().length;
			int times = size - len;
			if(times<0){
				sb.append(value.substring(0,size));
			}else if (times >= 0) {
				sb.append(value);
				for (int i = 0; i < times; i++) {
					sb.insert(0, "0");
				}
			} else {
				String temp = "";
				sb.append(temp);
				for (int i = 0; i < size; i++) {
					sb.insert(0, "0");
				}
			}
		} else if (value == null) {
			value = "";
			sb.append(value);
			for (int i = 0; i < size; i++) {
				sb.insert(0, "0");
			}
		}

		return sb.toString();
	}

	/**
	 * <p>
	 * 简单获取UUID
	 * </p>
	 * @param maxLength 返回id的最大长度
	 * @return java.lang.String
	 * @author wuyu
	 * @date 2019/9/19 8:38
	 **/
	public static String getUUID(int maxLength) {
		String id = UUID.randomUUID().toString().replace("-", "");
		if (maxLength < 1 || maxLength > id.length()) {
			return id;
		} else {
			return id.substring(0, maxLength);
		}
	}

	/**
	 * <p>
	 * 校验集合是否为空
	 * </p>
	 * @param list List集合
	 * @return boolean
	 * @author wuyu
	 * @date 2019/9/20 16:51
	 **/
	public static boolean isEmptyList(List<?> list) {
		return list == null || list.isEmpty();
	}

	/**
	 * 获取IP
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if (StringUtils.equals(ip, "127.0.0.1")) {
				//根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (Exception e) {
					e.printStackTrace();
				}
				ip = inet.getHostAddress();
			}
		}
		// 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ip != null && ip.length() > 15) {
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
		return ip;
	}

	public static boolean isEmpty(Object object) {
		boolean bool = false;
		if (null == object || "".equals(object.toString())) {
			bool = true;
		}
		return bool;
	}

	public static String changePwd(String password) {
		if (!isEmpty(password)) {
			return "******";
		} else {
			return "";
		}
	}

	public static String getMd5Pwd(String password) {
		String md5Pwd = "";
		if (StringUtils.isNotBlank(password)) {
			md5Pwd = new Md5Hash(password).toHex();
		}
		return md5Pwd;
	}

	public static List<String> arrayToList(String [] strings) {
		List<String> list = null;
		if (!isEmpty(strings)) {
			list = new ArrayList();
			for (String str : strings) {
				list.add(str);
			}
		}
		return list;
	}

	/**
	 * <p>
	 * 获取字典代码值
	 * </p>
	 * @param parentId 字典代码上级代码id
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 * @author wuyu
	 * @date 2019/10/31 10:23
	 **/
	public static Map<String, String> dictionaryCodes(String parentId) {
		List<SysDictryCd> cdList = EhcacheUtil.getSysDictryCdByCache(parentId);
		if (CommonUtil.isEmptyList(cdList)) {
			return new HashMap<>(1);
		}
		Map<String, String> result = new HashMap<>(initCapacity(cdList.size()));
		for (SysDictryCd code : cdList) {
			result.put(code.getDictryId(), code.getDictryNm());
		}
		return result;
	}

	/**
	 * <p>
	 * 获取单个字典代码名称
	 * </p>
	 * @param parentId 当前字典代码的父机字典代码id
	 * @param codeId 当前字典代码id
	 * @return java.lang.String
	 * @author wuyu
	 * @date 2019/10/31 16:26
	 **/
	public static String dictionaryCodeName(String parentId, String codeId) {
		return dictionaryCodes(parentId).get(codeId);
	}

	/**
	 * <p>
	 * 初始Map集合容量
	 * </p>
	 * @param size 数据量大小
	 * @return int
	 * @author wuyu
	 * @date 2019/12/27 8:52
	 **/
	public static int initCapacity(int size) {
		if (size < 0) {
			return 0;
		}
		return (int) Math.ceil(size * 4.0 / 3);
	}
}
