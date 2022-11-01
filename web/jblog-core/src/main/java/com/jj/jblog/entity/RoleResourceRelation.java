package com.jj.jblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 角色资源关系
 *
 * @author 任人子
 * @date 2022/5/10  - {TIME}
 */
@TableName(value = "tb_role_resource_relation")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RoleResourceRelation implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 角色ID
     */
    private Integer roleId;
    /**
     * 资源ID
     */
    private Integer resourceId;

}
