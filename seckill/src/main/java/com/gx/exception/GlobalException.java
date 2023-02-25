package com.gx.exception;

import com.gx.vo.RespBeanEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalException extends RuntimeException{   //继承运行时异常
    private RespBeanEnum respBeanEnum;
}
