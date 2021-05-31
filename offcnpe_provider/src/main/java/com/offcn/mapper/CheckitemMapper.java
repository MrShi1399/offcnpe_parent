package com.offcn.mapper;

import com.offcn.pojo.Checkitem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zs
 * @since 2021-05-25
 */
public interface CheckitemMapper extends BaseMapper<Checkitem> {
    /**
     * 根据id查找记录
     * @param id
     * @return
     */
    Checkitem getById(Integer id);
}
