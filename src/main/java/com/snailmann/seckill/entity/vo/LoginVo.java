package com.snailmann.seckill.entity.vo;

import com.snailmann.seckill.validator.constraints.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class LoginVo {

    @NotNull(message = "手机号不允许为空")
    @IsMobile
    String mobile;
    @NotNull(message = "密码不允许为空")
    @Length(min = 32)
    String password;

}
