package com.wanjia.user.mapper;

import com.wanjia.user.pojo.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author JT.L
 * @date 2020/5/26 13:41:42
 * @description
 */
@Repository
public interface UserMapper extends Mapper<User> {
}
