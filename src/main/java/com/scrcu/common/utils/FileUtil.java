package com.scrcu.common.utils;

import com.jcraft.jsch.*;
import com.scrcu.sys.entity.SysLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import java.util.*;

/**
 * 描述: 文件工具类
 *  hepengfei
 *  2019-09-4
 */

public class FileUtil {

	public static Logger log = LoggerFactory.getLogger(FileUtil.class);

	private static ChannelSftp channelSftp = null;
	//-用于临时存储系统日志文件对象
	private static List<SysLog> tempSysLogList = new ArrayList<>();

	/**
	 * 描述：获取服务器上的日志文件，判断文件是否存在
	 *
	 * @param url
	 *            服务器ip
	 * @param port
	 *            服务器端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param path
	 *            文件发送路径(如果不存在则新创建)
	 *            数据集合(key-文件名 value-数据)
	 * @return 返回处理成功的数据
	 */
	public static List<SysLog> listFileName(String url, int port, String username, String password, String path) {
		log.info("----准备连接sftp服务-----");
		SftpUtil sftpUtil = new SftpUtil(username, password, url, port);
		ChannelSftp sftp = sftpUtil.getSftpConnect();
		if (checkSftpSuccess(sftpUtil,sftp)){
			try {
				log.info("------sftp服务连接成功--------");
				if (tempSysLogList.size() > 0){
					tempSysLogList = new ArrayList<>();
				}
				String sysLogId = "SYSLOG_";
				getAllSysLogBySftp(sftp,path,url,port,username,password,sysLogId,"-1");
				log.info("文件处理完成");
				// 回到上一级目录
				sftp.cd("..");
			} catch (Exception e) {
				log.info("查询目录下是否存在文件出错：" + e);
				return null;
			} finally {
				sftpUtil.disconnect();
				log.info("关闭sftp连接------");
			}
		}
		return tempSysLogList ;
	}

	/**
	 * 描述：判断sftp是否链接成功，如果链接成功，尝试继续链接
	 * @param sftp
	 * @return
	 */
	public static boolean checkSftpSuccess(SftpUtil sftpUtil,ChannelSftp sftp){
		int tryConnNum = 0;
		while (sftp == null) {
			try {
				log.info("等待重新连接sftp服务.......");
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			log.info("------尝试重新连接sftp----");
			sftp = sftpUtil.getSftpConnect();
			if (tryConnNum > 60) {
				break;
			}
			tryConnNum++;
		}
		if (tryConnNum > 60){
			return false;
		}else{
			return true;
		}
	}


	/**
	 * 描述：获取日志对象(递归方法)
	 * @param sftp
	 * @param path
	 * @param url
	 * @param port
	 * @param user
	 * @param password
	 * @param logId			日志ID
	 * @param parentId		父级ID
	 * @return
	 * @author		pengjuntao
	 */
	public static void getAllSysLogBySftp(ChannelSftp sftp,
												  String path,
												  String url,
												  int port,
												  String user,
												  String password,
												  String logId,
												  String parentId){
		try{
			sftp.cd(path);
			Vector<ChannelSftp.LsEntry> ftpFiles = sftp.ls(sftp.pwd());
			if (ftpFiles != null && ftpFiles.size() > 0){
				for (int i = 0; i < ftpFiles.size(); i ++){
					if (!ftpFiles.get(i).getFilename().startsWith(".")) {//过滤 ./..文件
						String fileNm = ftpFiles.get(i).getFilename();
						String id = logId + i;
						SysLog log = new SysLog();
						log.setSysLogId(id);
						log.setFileNm(fileNm);
						log.setFileUpdDate(ftpFiles.get(i).getAttrs().getMtimeString());//最近更新时间      （SftpATTRS对象是文件的属性对象）
						log.setIp(url);
						log.setUser(user);
						log.setPwd(password);
						log.setPort(port+"");
						log.setUrl(path+"/"+ftpFiles.get(i).getFilename());
						log.setParentId(parentId);
						if (!fileNm.contains(".")){//是目录
							log.setState("closed");
							log.setIsDic("SYS0201");
							tempSysLogList.add(log);
							getAllSysLogBySftp(sftp,
									path+"/"+ftpFiles.get(i).getFilename(),
									url,
									port,
									user,
									password,
									id,
									id);
						}else{//是文件
							log.setState("open");
							log.setIsDic("SYS0202");
							tempSysLogList.add(log);
						}
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 描述：
	 * @param url
	 * @param port
	 * @param username
	 * @param password
	 * @param cPath			客户端上传文件路径
	 * @param sPath			服务端上传文件存放路径
	 * @author	pengjuntao
	 */
	public static void uploadSftpFile(String url,
								  int port,
								  String username,
								  String password,
								  String cPath,
								  String sPath){
		SftpUtil sftpUtil = new SftpUtil(username, password, url, port);
		ChannelSftp sftp = sftpUtil.getSftpConnect();
		if (checkSftpSuccess(sftpUtil,sftp)){
			uploadFileRel(sftp,cPath,sPath);
		}
	}

	/**
	 * 描述：递归上传文件
	 * @param sftp
	 * @param cPath		客户端上传文件路径
	 * @param sPath		服务端上传文件存放路径
	 * @author	   pengjuntao
	 */
	public static void uploadFileRel(ChannelSftp sftp, String cPath, String sPath){
		try{
			File file = new File(cPath);
			if (file.isDirectory()){//上传文件是文件夹
				String fileName = file.getName();
				log.info("cd："+sPath);
				sftp.cd(sPath);
				log.info("创建目录：" + fileName);
				sftp.mkdir(fileName);
				//-修改sPath到创建目录
				sPath = sPath + "/" + fileName;
				File[] fileList = file.listFiles();
				if (fileList != null && fileList.length > 0){
					for (int i = 0; i < fileList.length; i++){
						uploadFileRel(sftp,fileList[i].getAbsolutePath(),sPath);
					}
				}
			} else{//是文件
				log.info("开始上传文件：" + file.getAbsolutePath());
				sftp.put(cPath,	sPath, ChannelSftp.OVERWRITE);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}



	/**
	 * 描述：通过sftp从服务器下载文件
	 * @param url
	 * @param port
	 * @param username
	 * @param password
	 * @param fileName		下载的文件名
	 * @param filePath		服务器文件路径
	 * @param localPath		本地文件存放路径
	 */
	public static boolean downloadSftpFile(String url,
										int port,
										String username,
										String password,
										String fileName,
										String filePath,
										String localPath){
		boolean result = false;
		//-sftp连接
		SftpUtil sftpUtil = new SftpUtil(username, password, url, port);
		ChannelSftp sftp = sftpUtil.getSftpConnect();
		if (checkSftpSuccess(sftpUtil,sftp)){
			//-判断目标文件是否存在
			boolean existFile = isExistFile(url,port, username,password,filePath,fileName,false);
			if (existFile){
				log.info("准备下载文件....");
				try{
					File local_directory = new File(localPath);
					if (!local_directory.exists()){
						local_directory.mkdirs();
					}
					localPath = localPath + "\\" + fileName;
					File local_file = new File(localPath);
					if (!local_file.exists()){
						local_file.createNewFile();
					}
					filePath = filePath + "/" + fileName;
					sftp.get(filePath,localPath);
					result = true;
				}catch (Exception e){
					e.printStackTrace();
					throw new RuntimeException("下载文件出错："+e.getMessage());
				}
			}
		}
		return result;
	}

	/**
	 * 描述：sftp连接服务器，删除指定文件
	 * @param url
	 * @param port
	 * @param username
	 * @param password
	 * @param fileName
	 * @param filePath
	 * @return
	 * @author pengjuntao
	 */
	public static boolean deleteFile(String url,
									 int port,
									 String username,
									 String password,
									 String fileName,
									 String filePath){
		boolean result = false;
		SftpUtil sftpUtil = new SftpUtil(username, password, url, port);
		ChannelSftp sftp = sftpUtil.getSftpConnect();
		if (checkSftpSuccess(sftpUtil,sftp)){
			try{
				boolean isExist = isExistFile(url, port, username, password, filePath, fileName,false);
				if (isExist){
					filePath = filePath + "/" + fileName;
					sftp.rm(filePath);//删除
					result = true;
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}

	/*public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
		File targetFile = new File(filePath);
		if(!targetFile.exists()){
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(filePath+"\\"+fileName);
		out.write(file);
		out.flush();
		out.close();
	}*/

	/**
	 * 将文件传到指定服务器
	 *
	 * @param url
	 *            服务器ip
	 * @param port
	 *            服务器端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param path
	 *            文件发送路径(如果不存在则新创建)
	 * @param dirName
	 *            文件夹名称
	 * @param fileMap
	 *            数据集合(key-文件名 value-数据)
	 * @return 返回处理成功的数据
	 */
	public static Map<String, String> uploadFileList(String url,
													 int port,
													 String username,
													 String password,
													 String path,
													 String dirName,
													 Map<String, String> fileMap,
													 String encode) {
		log.info("准备连接sftp服务-----");
		SftpUtil sftpUtil = new SftpUtil(username, password, url, port);
		ChannelSftp sftp = sftpUtil.getSftpConnect();
		int tryConnNum = 0;
		// 判断连接是否成功
		while (sftp == null) {
			try {
				log.info("等待重新连接sftp服务...");
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			log.info("------尝试重新连接sftp----");
			sftp = sftpUtil.getSftpConnect();
			if (tryConnNum > 60) {
				break;
			}
			tryConnNum++;
		}

		if (sftp == null) {
			log.error("sftp连接失败...");
			return null;
		}
		try {
			log.info("sftp服务连接成功--------");
			// 设置目录
			sftp.cd(path);
			log.info("当前目录：" + sftp.pwd());
			// 创建以控制文件命名的文件夹
			if (!sftpUtil.isExists(dirName)) {
				sftp.mkdir(dirName);
				log.info("创建目录：" + dirName);
			}

			// 更改路径为当前目录
			sftp.cd(dirName);
			log.info("当前目录为:" + sftp.pwd());
			// 保存文件
			log.info("准备开始往当前目录写文件------------------");
			int i = 1;
			int count = fileMap.size();// 文件总数
			Iterator<Map.Entry<String, String>> iterator = fileMap.entrySet().iterator();
			if (encode == null || "".equals(encode)) {
				encode = "gb18030";
			}
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				InputStream ins = new ByteArrayInputStream(entry.getValue().getBytes(encode));
				log.info("一共【" + count + "】个文件，" + "开始写第【" + i + "】个文件，" + "文件名称为：" + entry.getKey());
				// 写入文件
				try {
					sftp.put(ins, entry.getKey());
					log.info("第【" + i + "】个文件写入成功，" + "文件名称为：" + entry.getKey());
				} catch (Exception e) {
					// 写入失败，则从map中移除
					iterator.remove();
					log.info("第【" + i + "】个文件写入失败，" + "文件名称为：" + entry.getKey());
					log.error(e.getMessage());
				}
				ins.close();
				i++;
			}
			log.info("文件处理完成");
			// 回到上一级目录
			sftp.cd("..");
		} catch (Exception e) {
			log.info("上传文件出错：" + e);
			return null;
		} finally {
			sftpUtil.disconnect();
			log.info("关闭sftp连接------");
		}
		return fileMap;
	}

	/**
	 * 描述：通过sftp从服务器下载文件
	 * @param ftpHost IP地址
	 * @param ftpUserName  用户名
	 * @param ftpPassword 用户名密码
	 * @param ftpPort 端口
	 * @param ftpPath SFTP服务器中文件所在路径
	 * @param localPath 下载到本地的位置
	 * @param fileName 文件名称
	 */
	public static void downloadSftpFile(String ftpHost,
										String ftpUserName,
										String ftpPassword, int ftpPort, String ftpPath, String localPath,
										String fileName) throws Exception {
		Session session = null;
		Channel channel = null;

		JSch jsch = new JSch();
		session = jsch.getSession(ftpUserName, ftpHost, ftpPort);
		session.setPassword(ftpPassword);
		session.setTimeout(100000);
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();

		channel = session.openChannel("sftp");
		channel.connect();
		ChannelSftp chSftp = (ChannelSftp) channel;

		String ftpFilePath = ftpPath + "/" + fileName;
		String localFilePath = localPath + File.separatorChar + fileName;

		try {
			chSftp.get(ftpFilePath, localPath);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("download error.");
		} finally {
			chSftp.quit();
			channel.disconnect();
			session.disconnect();
		}
	}

	/**
	 * 描述：判断文件是否存在
	 * @param url			服务器ip
	 * @param port			服务器端口
	 * @param username		用户名
	 * @param password		密码
	 * @param path			服务器文件存放目录路径
	 * @param fileName		文件名
	 * @param flag 			标志是否需要断开sftp连接
	 * @return 返回处理成功的数据
	 * @author pengjuntao
	 */
	public static boolean isExistFile(String url,
									  int port,
									  String username,
									  String password,
									  String path,
									  String fileName,
									  boolean flag) {
		log.info("准备连接sftp服务....");
		SftpUtil sftpUtil = new SftpUtil(username, password, url, port);
		ChannelSftp sftp = sftpUtil.getSftpConnect();
		boolean isExistFile = false;
		if (checkSftpSuccess(sftpUtil,sftp)){
			try {
				log.info("sftp服务连接成功");
				sftp.cd(path);
				log.info("当前目录：" + sftp.pwd());
				log.info("检查目录下文件是否存在....");
				Vector<ChannelSftp.LsEntry> ftpFile = sftp.ls(sftp.pwd());
				if (ftpFile != null && ftpFile.size() > 0) {
					for (int i = 0; i < ftpFile.size(); i++) {
						log.info("文件名称:"+ftpFile.get(i).getFilename());
						log.info("目标文件："+fileName);
						if ((ftpFile.get(i).getFilename().equals(fileName))) {
							log.info("目录下存在文件：" + fileName);
							isExistFile = true;
							break;
						}
					}
				} else {
					isExistFile = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.info("查询目录下是否存在文件出错：" + e);
			} finally {
				if (flag){
					sftpUtil.disconnect();
					log.info("关闭sftp连接------");
				}
			}
		}
		return isExistFile;
	}


	/**
	 * 下载单个文件
	 *
	 * @param url
	 *            服务器ip
	 * @param port
	 *            服务器端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param path
	 *            文件发送路径(如果不存在则新创建)
	 *            数据集合(key-文件名 value-数据)
	 * @return 返回处理成功的数据
	 */

	public static boolean downloadFileOne(String url,
										  int port,
										  String username,
										  String password,
										  String path,
										  String localPath) {
		log.info("准备连接sftp服务-----");
		SftpUtil sftpUtil = new SftpUtil(username, password, url, port);
		ChannelSftp sftp = sftpUtil.getSftpConnect();
		// 判断连接是否成功
		if (checkSftpSuccess(sftpUtil,sftp)){
			try {
				log.info("sftp服务连接成功--------");
				String fileNm = path.substring(path.lastIndexOf("/")+1);//文件名
				//-创建本地文件夹
				File localFile = new File(localPath);
				if (!localFile.exists()){
					localFile.mkdirs();
				}
				//-创建本地文件
				File file = null;
				if (localPath.contains("/")){//linux服务器的路径
					System.out.println("路径1：" + localPath +"/" +fileNm);
					file = new File(localPath +"/" +fileNm);
				}else{//window系统路径
					System.out.println("路径2：" + localPath + "\\" +fileNm);
					file = new File(localPath + "\\" +fileNm);
				}
				//-创建文件
				if (!file.exists()){
					file.createNewFile();
				}
				//-下载远程服务器日志文件
				FileOutputStream logFile = new FileOutputStream(file);
				sftp.get(path,logFile);
				log.info("---日志文件下载成功---");
				return true;
			} catch (Exception e) {
				log.error("下载日志文件出错：" + e);
			} finally {
				sftpUtil.disconnect();
				log.info("---关闭sftp连接------");
			}
		}
		return false;
	}

}

