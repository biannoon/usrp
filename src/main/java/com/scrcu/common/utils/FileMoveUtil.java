package com.scrcu.common.utils;

import java.io.*;

public class FileMoveUtil {

    public void move(String srcFilePath,String targetFilePath){
        File srcFile = new File(srcFilePath);
        srcFile.renameTo(new File(targetFilePath));
    }
}
