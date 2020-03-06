/*
package com.scrcu.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

public class ZIPUtil {
    private static final int  BUFFER_SIZE = 2 * 1024;


    public void zipFiles(List files,String zipFileName) throws RuntimeException{
        long start = System.currentTimeMillis();
        FileOutputStream out = null;
        ZipOutputStream zos = null ;
        byte[] buf = new byte[BUFFER_SIZE];
        try{
            out = new FileOutputStream(new File(zipFileName));
            zos = new ZipOutputStream(out);
            zos.setEncoding("GBK");
            for(int i=0;i<files.size();i++){
                File file = (File)files.get(i);
                if(!file.isDirectory()){
                    zos.putNextEntry(new ZipEntry(file.getName()));
                    int len;
                    FileInputStream in = new FileInputStream(file);
                    while ((len = in.read(buf)) != -1){
                        zos.write(buf, 0, len);
                        // Complete the entry
                    }
                    zos.closeEntry();
                    in.close();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) +" ms");
        }catch(Exception e){
            throw new RuntimeException("zip error from ZipUtils",e);
        }finally{
            if(zos != null){
                try {
                    zos.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    */
/**
     * 压缩成ZIP 方法1
     * @param srcDir 压缩文件夹路径
     * @param zipFileName   带路径的压缩文件名称
     * @param keepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     * @param keepRootDir 压缩时是否把根目录打入压缩包
     * false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     *//*

    public void zipDir(String srcDir, String zipFileName, boolean keepDirStructure,boolean keepRootDir)
            throws RuntimeException{
        long start = System.currentTimeMillis();
        FileOutputStream out = null;
        ZipOutputStream zos = null ;
        try{
            out = new FileOutputStream(new File(zipFileName));
            zos = new ZipOutputStream(out);
            zos.setEncoding("GBK");
            File rootFile = new File(srcDir);
            String rootFileName = null;
            if(keepRootDir)
                rootFileName = rootFile.getName();
            compress(rootFile,zos,rootFileName,keepDirStructure,keepRootDir);
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) +" ms");

        }catch(Exception e){
            throw new RuntimeException("zip error from ZipUtils",e);
        }finally{
            if(zos != null){
                try {
                    zos.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    */
/**
     * 递归压缩方法
     * @param sourceFile 源文件
     * @param zos zip输出流
     * @param name 压缩后的名称
     * @param keepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     * false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     *//*

    private void compress(File sourceFile, ZipOutputStream zos,
                          String name,boolean keepDirStructure,boolean keepRootDir) throws Exception{
        byte[] buf = new byte[BUFFER_SIZE];
        if(sourceFile.isFile()){
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1){
                zos.write(buf, 0, len);
                // Complete the entry
            }
            zos.closeEntry();
            in.close();
        }else{
            if(keepDirStructure && keepRootDir){
                // 文件夹的处理
                zos.putNextEntry(new ZipEntry(name + "/"));
                // 没有文件，不需要文件的copy
                zos.closeEntry();
            }

            File[] listFiles = sourceFile.listFiles();
            if(listFiles != null && listFiles.length > 0){
                for(int i=0;i<listFiles.length;i++){
                    File file = listFiles[i];
                    String fileName = file.getName();
                    if(name!=null){
                        fileName = name + "/" + file.getName();
                    }
                    if (keepDirStructure) {
                        compress(file, zos, fileName,keepDirStructure,false);
                    }else{
                        compress(file, zos, file.getName(),keepDirStructure,false);
                    }
                }

            }
        }
    }



    public static void main(String[] args) throws Exception{
        ZIPUtil zipUtil = new ZIPUtil();
        long max_size= 1024 * 1024 * 10;//10M大小
        File rootDir = new File("F:\\hq");
        File[] files = rootDir.listFiles();
        String zipName = "F:\\Test";
        int seqNo = 1;
        long currentSize = 0;
        int i = 0;
        List fileList = new LinkedList();
        while(true){

            if(i==files.length){//最后一个文件
                if(!fileList.isEmpty()){
                    String seq = ""+seqNo;//Function.intToStr(seqNo, 3);
                    zipUtil.zipFiles(fileList, zipName+seq+".zip");
                }
                break;
            }

            if(files[i].isDirectory()){
                i++;
                continue;
            }
            long fileSize = files[i].length();
            if(fileSize>max_size)
                throw new Exception("["+files[i].getPath()+"]文件大小["+fileSize+"]超出设定值["+max_size+"]");
            currentSize += files[i].length();
            if(currentSize<=max_size){
                fileList.add(files[i]);
            }else{
                String seq = ""+seqNo;//Function.intToStr(seqNo, 3);
                seqNo++;
                zipUtil.zipFiles(fileList, zipName+seq+".zip");
                fileList = new LinkedList();
                fileList.add(files[i]);
                currentSize = files[i].length();
            }
            i++;

        }
    }
}
*/
