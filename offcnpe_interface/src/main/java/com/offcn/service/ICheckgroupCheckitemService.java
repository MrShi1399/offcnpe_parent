package com.offcn.service;

import com.offcn.pojo.CheckgroupCheckitem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.offcn.util.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zs
 * @since 2021-05-25
 */
public interface ICheckgroupCheckitemService extends IService<CheckgroupCheckitem> {
    /**
     * 获取检查组下面有哪些检查项
     * @param checkgroupId 检查组id
     * @return 查询结果
     */
    Result listByChcekgroupId(Integer checkgroupId);
}
