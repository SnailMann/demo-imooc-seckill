package com.snailmann.seckill.validator.constraints;

import com.snailmann.seckill.util.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 手机号码校验器
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;

    /**
     * 初始化方法
     *
     * @param constraintAnnotation
     */
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (required) {
            return ValidatorUtil.isMobile(value);
        } else {
            if (StringUtils.isBlank(value)) {
                return true;
            } else {
                return ValidatorUtil.isMobile(value);
            }

        }
    }
}
