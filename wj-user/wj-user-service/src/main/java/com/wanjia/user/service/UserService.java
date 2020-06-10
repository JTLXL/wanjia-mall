package com.wanjia.user.service;

import com.wanjia.common.enums.ExceptionEnum;
import com.wanjia.common.exception.WjException;
import com.wanjia.common.utils.NumberUtils;
import com.wanjia.user.mapper.UserMapper;
import com.wanjia.user.pojo.User;
import com.wanjia.user.utils.CodecUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author JT.L
 * @date 2020/5/26 13:43:39
 * @description
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "user:verify:phone:";

    /**
     * 校验数据
     *
     * @param data
     * @param type
     * @return
     */
    public Boolean checkData(String data, Integer type) {
        User record = new User();
        // 判断数据类型
        switch (type) {
            case 1:
                record.setUsername(data);
                break;
            case 2:
                record.setPhone(data);
                break;
            default:
                throw new WjException(ExceptionEnum.INVALID_USER_DATA_TYPE);
        }
        return userMapper.selectCount(record) == 0;
    }

    public void sendVerifyCode(String phone) {
        // 生成key
        String key = KEY_PREFIX + phone;
        // 生成验证码
        String code = NumberUtils.generateCode(6);

        Map<String, String> msg = new HashMap<>();
        msg.put("phone", phone);
        msg.put("code", code);

        // 发送验证码
        amqpTemplate.convertAndSend("wj.sms.exchange", "sms.verify.code", msg);

        // 保存验证码，有效时长5分钟
        redisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);
    }

    /**
     * 注册
     * @param user
     * @param code
     * @return
     */
    public Boolean register(User user, String code) {
        // 校验验证码
        String key = KEY_PREFIX + user.getPhone();
        String cacheCode = redisTemplate.opsForValue().get(key);
        if (!StringUtils.equals(code, cacheCode)) {
            return false;
        }
        // 生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        // 加盐加密
        String codePwd = CodecUtils.md5Hex(user.getPassword(), salt);
        user.setPassword(codePwd);
        // 新增用户
        user.setId(null);
        user.setCreated(new Date());
        boolean res = userMapper.insertSelective(user) == 1;
        if (res) {
            redisTemplate.delete(key);
        } else {
            return false;
        }
        return true;
    }

    /**
     * 根据用户名和密码查询用户
     *
     * @param username
     * @param password
     * @return
     */
    public User queryUser(String username, String password) {
        User record = new User();
        record.setUsername(username);
        User user = userMapper.selectOne(record);
        if (user == null) {
            return null;
        }
        // 获取盐，对输入的密码加盐加密
        password = CodecUtils.md5Hex(password, user.getSalt());
        // 和数据库中的密码比较
        if (StringUtils.equals(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}
