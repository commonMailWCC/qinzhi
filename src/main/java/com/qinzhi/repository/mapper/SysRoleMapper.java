package com.qinzhi.repository.mapper;

import com.qinzhi.domain.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @className: SysRoleMapper
 * @description: TODO
 * @author: liwei
 * @date: 2017-03-26 下午2:19:32
 */
public interface SysRoleMapper extends BaseMapper<SysRole, Long> {
    /**
     * 查询角色信息(支持分页)
     *
     * @param roleName
     * @param pageNo   起始页
     * @param pageSize 每页显示多少条
     * @return List <Operator>
     */
    List<SysRole> findRoles(@Param("roleName") String roleName, @Param("currentPage") int currentPage, @Param("pageSize") int pageSize);

    /**
     * 获取操作员的所有角色信息
     *
     * @param operatorId 操作员ID
     * @return
     */
    List<SysRole> findByOperatorId(@Param("operatorId") Long operatorId);

    /**
     * 获取操作员的ID
     *
     * @return id
     */
    int getRoleLastId();

    /**
     * 根据名称模糊查询角色个数
     *
     * @param roleName
     * @return
     */
    int getCountByCondition(@Param("roleName") String roleName);

    /**
     * @param ids
     * @return
     * @description: 删除角色
     * @author liwei
     * @date 2014-12-1 下午4:18:19
     * @return: int
     */
    int deleteRole(@Param("ids") List<Long> ids);

}