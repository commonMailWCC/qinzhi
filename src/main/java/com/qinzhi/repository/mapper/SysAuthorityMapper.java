package com.qinzhi.repository.mapper;

import com.qinzhi.domain.SysAuthority;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @className: AuthorityMapper
 * @description: TODO
 * @author: liwei
 * @date: 2017-03-26 下午2:20:58
 */
public interface SysAuthorityMapper extends BaseMapper<SysAuthority, Long> {
    /**
     * 查询系统操作员信息(支持分页)
     *
     * @param pageNo   起始页
     * @param pageSize 每页显示多少条
     * @return List<Authority>
     */
    List<SysAuthority> findAuthoritys(@Param("currentPage") int currentPage, @Param("pageSize") int pageSize);

    /**
     * 获取角色的权限信息
     *
     * @param roleIds 角色Ids
     * @return
     */
    List<SysAuthority> findByRoleIds(@Param("roleIds") List<Long> roleIds);

    List<SysAuthority> findByOperatorId(@Param("operatorId") Long operatorId);

    /**
     * 查询父节点下的子节点
     *
     * @param id
     * @return
     */
    List<SysAuthority> findAuthoritiesByParentId(@Param("parentId") Long id);

    /**
     * 分页查询父节点下的子节点
     *
     * @param id
     * @return
     */
    List<SysAuthority> findAuthorityByParentId(@Param("parentId") Long id, @Param("currentPage") int currentPage, @Param("pageSize") int pageSize);

    /**
     * 批量删除权限
     *
     * @param ids
     * @return
     */
    int deleteByIds(@Param("ids") List<Long> ids);


}