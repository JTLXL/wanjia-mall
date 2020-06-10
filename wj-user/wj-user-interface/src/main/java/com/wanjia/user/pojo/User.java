package com.wanjia.user.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import java.util.Date;

/**
 * @author JT.L
 * @date 2020/5/26 13:31:16
 * @description
 */
@Data
@Table(name = "tb_user")
public class User {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    /**
     * 用户名
     */
    @Length(min = 4, max = 30, message = "用户名必须在4-30位之间")
    private String username;

    /**
     * 密码
     */
    @JsonIgnore
    @Length(min = 4, max = 30, message = "密码必须在4-30位之间")
    private String password;

    /**
     * 电话
     */
    @Pattern(regexp = "^1[356789]\\d{9}$", message = "手机号不合法")
    private String phone;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 密码的盐值
     */
    @JsonIgnore
    private String salt;
}