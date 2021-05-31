package com.offcn.service;

import com.offcn.pojo.Checkitem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;

/**
 * <p>
 *  服务类
 * </p>
 * 预约管理
 * @author zs
 * @since 2021-05-25
 */
public interface ICheckitemService extends IService<Checkitem> {
    /**
     * 获取所有预约并进行分页
     * @param queryPageBean
     * @return
     */
    PageResult listPage(QueryPageBean queryPageBean);
}
