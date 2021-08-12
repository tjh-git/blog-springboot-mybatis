package com.tjh;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 *
 * message 控制台上
 * com.tjh.MyNotFoundException: 博客找不到
 */
//指定状态码
//MyNotFoundException指定为NOT_FOUND
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyNotFoundException extends RuntimeException {
    public MyNotFoundException() {
    }

    public MyNotFoundException(String message) {
        super(message);
    }

    public MyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
