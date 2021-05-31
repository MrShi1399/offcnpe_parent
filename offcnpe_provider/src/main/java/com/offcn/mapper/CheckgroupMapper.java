package com.offcn.mapper;

import com.offcn.pojo.Checkgroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zs
 * @since 2021-05-25
 */
public interface CheckgroupMapper extends BaseMapper<Checkgroup> {
    /**
     * 根据id查找记录
     * @param id
     * @return
     */
    Checkgroup getById(Integer id);
}
