package com.snailmann.seckill.validator.constraints;

import com.snailmann.seckill.util.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 手机号码校验器
 * 这是陪着@IsMobile注解来实现javax参数校验
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;

    /**
     * 初始化方法，可以获得注解中的属性
     *
     * @param constraintAnnotation
     */
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    /**
     * 验证
     *
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (required) {
            return ValidatorUtils.isMobile(value);
        } else {
            if (StringUtils.isBlank(value)) {
                return true;
            } else {
                return ValidatorUtils.isMobile(value);
            }

        }
    }
}
