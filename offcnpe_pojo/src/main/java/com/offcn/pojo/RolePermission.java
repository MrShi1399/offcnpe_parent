package com.offcn.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zs
 * @since 2021-05-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_role_permission")
public class RolePermission extends Model {

    private static final long serialVersionUID = 1L;

    private Integer roleId;

    private Integer permissionId;


}
