package com.offcn.service.impl;

import com.offcn.pojo.User;
import com.offcn.mapper.UserMapper;
import com.offcn.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Service;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zs
 * @since 2021-05-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
