package com.qinzhi.repository.mapper;

import com.qinzhi.domain.SysRoleAuthorityMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @className: RoleAuthorityMapMapper
 * @description: TODO
 * @author: liwei
 * @date: 2017-03-26 下午2:19:50
 */
public interface SysRoleAuthorityMapMapper extends BaseMapper<SysRoleAuthorityMap, Long> {
    /**
     * 获取角色所有的权限
     *
     * @param roleId 角色ID
     * @return
     */
    List<SysRoleAuthorityMap> findByRoleId(@Param("roleId") Long roleId);

    /**
     * 通过角色ID删除角色与权限关联关系
     *
     * @param roleId
     * @return
     */
    int deleteRoleAuthorityMapByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据角色Id和权限Id保存角色和权限之间的关系
     *
     * @param roleId
     * @param authorityIds
     * @return int
     * @author yanhl
     * @since 2015年8月25日 上午11:51:43
     */
    int saveBatchRoleAuthority(@Param("roleId") Long roleId, @Param("authorityIds") List<Long> authorityIds);


}