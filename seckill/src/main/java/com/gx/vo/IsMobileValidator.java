package com.gx.vo;

import com.gx.utils.ValidatorUtil;
import com.gx.validator.IsMobile;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//手机号码校验规则的校验类
//第一个参数是自定义注解类，第二个是要校验的数据类型。
public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {

    private boolean required = false;

    //进行校验的判断逻辑
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (required){
            return ValidatorUtil.isMobile(s);

        }else {
            if (StringUtils.isEmpty(s)){
                return true;
            }else {
                return ValidatorUtil.isMobile(s);
            }
        }
    }

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }
}
