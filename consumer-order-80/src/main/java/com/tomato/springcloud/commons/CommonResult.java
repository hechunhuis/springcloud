package com.tomato.springcloud.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/7 22:13
 * @className : CommonResult
 * @description: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    private Integer code;
    private String message;
    private T data;

    public CommonResult(Integer code, String message) {
        this(code, message, null);
    }
}
