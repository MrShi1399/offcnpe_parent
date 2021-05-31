package com.offcn.service.impl;

import com.offcn.pojo.UserRole;
import com.offcn.mapper.UserRoleMapper;
import com.offcn.service.IUserRoleService;
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
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
