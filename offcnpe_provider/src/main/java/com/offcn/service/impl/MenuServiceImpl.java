package com.offcn.service.impl;

import com.offcn.pojo.Menu;
import com.offcn.mapper.MenuMapper;
import com.offcn.service.IMenuService;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
