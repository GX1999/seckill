package com.gx.validator;

import com.gx.vo.IsMobileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

//自定义验证手机号码的注解
//手机号码校验方法：1.直接调用Util校验类 2. 配合validation参数校验组件(jsr303)使用注解，不用每个类都重复写校验代码

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {IsMobileValidator.class}     //自定义校验规则，为IsMobileValidator类
)
public @interface IsMobile {

    boolean required() default true;  //必填消息

    String message() default "手机号码格式错误";   //报错消息

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
