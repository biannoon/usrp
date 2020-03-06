package com.scrcu.common.exception;

/**
 * 描述： 基础异常封装
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/19 9:22
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -807493459710868553L;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
