package com.scrcu.common.utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Vector;

/**
 * @ClassName ReportFileUtil
 * @Description TODO
 * @Author pengjuntao
 * @Date 2020/1/17 9:24
 * @Version 1.0
 */
public class ReportFileUtil {

    static Logger logger = LoggerFactory.getLogger(ReportFileUtil.class);

    static ChannelSftp channelSftp = null;

    /**
     * 描述：SFTP连接服务器
     * @return
     */
    public static void connectSftp(String url,
                                          String port,
                                          String password,
                                          String username){
        logger.info("准备sftp连接：ip=" + url + " port=" + port);
        SftpUtil sftpUtil = new SftpUtil(username,password,url,Integer.parseInt(port));
        ChannelSftp sftp = sftpUtil.getSftpConnect();
        int connectCount = 0;
        while (sftp == null){
            logger.info("sftp连接失败，尝试重新连接....");
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sftp = sftpUtil.getSftpConnect();
            //如果尝试重新连接了60次，不再连接，直接退出；
            if (connectCount > 60){
                break;
            }
            connectCount ++;
        }
        channelSftp = sftp;
    }

    /**
     * 描述，判断目标下载文件是否存在
     * @param filepath
     * @return
     */
    public static boolean isExistFile(String filepath){
        boolean result = false;
        if (channelSftp == null){
            logger.info("sftp连接失败....");
            result = false;
        }else{
            logger.info("sftp连接成功....");
            try {
                String path = filepath.substring(0,filepath.lastIndexOf("/"));
                String fileName = filepath.substring(filepath.lastIndexOf("/")+1);
                channelSftp.cd(path);
                //-获取该路径下的文件目录
                Vector<ChannelSftp.LsEntry> files = channelSftp.ls(channelSftp.pwd());
                if (files != null && files.size() > 0){
                    for (int i=0; i < files.size(); i++){
                        if (files.get(i).getFilename().equals(fileName)){
                            result = true;
                        }
                    }
                }else{
                    result = false;
                }
            } catch (SftpException e) {
                logger.info("查询指定路径下的目标文件失败....");
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 描述：下载报文文件
     * @param url
     * @param port
     * @param password
     * @param username
     * @param filePath
     */
    public static boolean downloadReportFile(String url,
                                          String port,
                                          String password,
                                          String username,
                                          String filePath,
                                          String localPath){
        boolean result = false;
        //-sftp连接
        connectSftp(url,port,password,username);
        //-判断目标文件是否存在
        boolean existFile = isExistFile(filePath);
        if (existFile){
            try {
                logger.info("准备下载报文文件....");
                channelSftp.get(filePath,localPath);
                logger.info("报文文件下载成功....");
                result = true;
            } catch (SftpException e) {
                e.printStackTrace();
            }
        }else{
            logger.info("报文文件不存在，请检查路径....");
        }
        return result;
    }
}
