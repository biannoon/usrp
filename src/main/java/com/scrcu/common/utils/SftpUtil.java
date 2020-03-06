package com.scrcu.common.utils;

import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.Vector;

/**
 * 描述：sftp通信工具类
 */
public class SftpUtil {
	public  static Logger log = LoggerFactory.getLogger(SftpUtil.class);
	private String username;// 用户名
	private String password;// 密码
	private String host;
	private int port;// 端口号
	private ChannelSftp sftp = null;
	private Session sshSession = null;

	public SftpUtil() {
	}

	public SftpUtil(String username, String password, String host, int port) {
		super();
		this.username = username;
		this.password = password;
		this.host = host;
		this.port = port;
	}

	/**
	 * 获取ChannelSftp连接对象
	 * 
	 * @return
	 */
	public ChannelSftp getSftpConnect() {
		JSch jsch = new JSch();
		try {
			log.info("准备连接到sftp服务器...");
			sshSession = jsch.getSession(username, host, port);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(config);
			sshSession.setPassword(password);
			sshSession.setTimeout(60000);
			sshSession.connect();
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			log.info("成功连接到：" + host);
		} catch (JSchException e) {
			log.info("连接到sftp服务器失败...");
			e.printStackTrace();
		}
		return sftp;
	}

	/**
	 * 关闭连接
	 */
	public void disconnect() {
		if (sftp != null) {
			if (sftp.isConnected()) {
				sftp.disconnect();
				log.info("ChannelSftp成功断开连接");
			}
		}
		if (sshSession != null) {
			if (sshSession.isConnected()) {
				sshSession.disconnect();
				log.info("Session成功断开连接");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public boolean isExists(String filename) {
		boolean flag = false;
		try {
			Vector<LsEntry> v = sftp.ls(sftp.pwd());
			if (v == null || v.size() <= 0) {
				return flag;
			}
			for (int i = 0; i < v.size(); i++) {
				String sname = v.get(i).getFilename();
				if (!sname.startsWith(".") && sname.equals(filename)) {
					flag = true;
					break;
				} else {
					flag = false;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
