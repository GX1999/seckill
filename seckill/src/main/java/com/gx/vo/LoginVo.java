package com.gx.vo;

import com.gx.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

//登陆参数
@Data
public class LoginVo {
    //通过注解实现登录数据的校验
    @NotNull    //validation参数校验组件
    @IsMobile  //自定义注解（顶替掉Util中ValidatorUtil工具类）
    private String mobile;

    @NotNull    //validation参数校验组件
    @Length(min = 32)
    private String password;
}
