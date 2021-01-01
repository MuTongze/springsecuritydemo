package com.mtz.common;

/**
 * 封装API的错误码
 *
 * @author Mutz
 */
public interface IErrorCode {
    long getCode();
    String getMessage();
}