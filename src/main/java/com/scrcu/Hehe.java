package com.scrcu;

import com.scrcu.config.dataBase.DBUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@RestController
public class Hehe {

    /**
     * 随机抛出异常
     */
    private void randomException() throws Exception {
        Exception[] exceptions = { //异常集合
                new NullPointerException(),
                new ArrayIndexOutOfBoundsException(),
                new NumberFormatException(),
                new SQLException()};
        //发生概率
        double probability = 0.75;
        if (Math.random() < probability) {
            //情况1：要么抛出异常
            throw exceptions[(int) (Math.random() * exceptions.length)];
        } else {
            //情况2：要么继续运行
        }

    }

    @RequestMapping("/hello")
    public List<String> hehe() throws Exception {
        randomException();
        return Arrays.asList("正常用户数据1!", "正常用户数据2! 请按F5刷新!!");
    }
    @RequestMapping("/hh")
    public void test() throws Exception {
        Connection conn = DBUtils.getConn("10000002");
        System.out.println("conn:"+conn.toString());
    }
}
