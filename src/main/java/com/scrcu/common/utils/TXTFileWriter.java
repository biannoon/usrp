package com.scrcu.common.utils;


import java.io.File;
import java.io.RandomAccessFile;

/**
 * 实现将内容写入文本型文件
 * 通过appendFlag控制是继续追加写入还是重新创建新文件写入 appendFlag为true表示是追加写入
 */
public class TXTFileWriter {

    private String fileName;

    private RandomAccessFile rf;

    private boolean appendFlag;

    public TXTFileWriter(String fileName,boolean appendFlag) {
        this.fileName = fileName;
        this.appendFlag = appendFlag;
    }

    public void init() throws Exception{
        File file = new File(fileName);
        if(!file.exists())//如果文件不存在，创建新文件
            file.createNewFile();
        else if(!appendFlag){//如果文件存在，但是不是以追加的方式继续写入，则先删除后再创建新文件
            file.delete();
            file.createNewFile();
        }
        rf = new RandomAccessFile(fileName,"rw");//追加数据进文件
        rf.seek(rf.length());//将指针移动到文件末尾
    }

    public void close(){
        try{
            if(rf!=null)
                rf.close();
            rf = null;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void write(String content) throws Exception{
        rf.write(content.getBytes());
    }
}
