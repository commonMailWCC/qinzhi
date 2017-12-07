package com.qinzhi.repository;

import com.qinzhi.domain.*;
import com.qinzhi.utils.Pageable;

import java.util.List;

/**
 * ISystemRepository
 *
 * @author liwei
 * @since 2015年10月29日 下午2:25:19
 */
public interface ISystemRepository {
    /**
     * 新增系统操作员
     *
     * @param sysOperator
     * @return boolean
     * @author songyc
     * @since 2015-1-16
     */
    boolean addOperator(SysOperator sysOperator);

    /**
     * 修改系统操作员
     *
     * @param sysOperator
     * @return boolean
     * @author songyc
     * @since 2015-1-16
     */
    boolean updateOperator(SysOperator sysOperator);

    /**
     * 删除系统操作员
     *
     * @param id
     * @return boolean
     * @author songyc
     * @since 2015-1-16
     */
    boolean deleteOperator(Long id);

    /**
     * 查询系统操作员信息(支持分页)
     *
     * @param pageNo   起始页
     * @param pageSize 每页显示多少条
     * @return Pageable<SysOperator>
     * @author songyc
     * @since 2015-1-16
     */
    Pageable<SysOperator> findOperators(SysOperator sysOperator);

    /**
     * 通过ID查询操作员信息
     *
     * @param id
     * @return SysOperator
     * @author songyc
     * @since 2015-1-16
     */
    SysOperator getOperatorById(Long id);

    /**
     * 通过登录名称查询操作员信息
     *
     * @param loginName 登录名称
     * @return SysOperator
     * @author songyc
     * @since 2015-1-16
     */
    SysOperator getOperatorByLoginName(String loginName);

    /**
     * 通过登录名称和state查询操作员信息
     *
     * @param loginName 登录名称
     * @param state
     * @return SysOperator
     * @author songyc
     * @since 2015-1-16
     */
    SysOperator getByLoginNameAndState(String loginName, Integer state);

    /**
     * 新增角色信息
     *
     * @param sysRole
     * @return boolean
     * @author songyc
     * @since 2015-1-16
     */
    boolean addRole(SysRole sysRole);

    /**
     * 修改角色信息
     *
     * @param sysRole
     * @return boolean
     * @author songyc
     * @since 2015-1-16
     */
    boolean updateRole(SysRole sysRole);

    /**
     * 删除角色信息
     *
     * @param id roleId
     * @return boolean
     * @author songyc
     * @since 2015-1-16
     */
    boolean deleteRole(Long id);

    /**
     * 查询角色信息(支持分页)
     *
     * @param roleName
     * @param pageNo   起始页
     * @param pageSize 每页显示多少条
     * @return Pageable<SysRole>
     * @author songyc
     * @since 2015-1-16
     */
    Pageable<SysRole> findRoles(String roleName, int pageNo, int pageSize);

    /**
     * 通过ID角色信息
     *
     * @param id
     * @return SysRole
     * @author songyc
     * @since 2015-1-16
     */
    SysRole getRoleById(Long id);

    /**
     * 新增权限信息
     *
     * @param sysAuthority
     * @return boolean
     * @author songyc
     * @since 2015-1-16
     */
    boolean addAuthority(SysAuthority sysAuthority);

    /**
     * 修改权限信息
     *
     * @param sysAuthority
     * @return boolean
     * @author songyc
     * @since 2015-1-16
     */
    boolean updateAuthority(SysAuthority sysAuthority);

    /**
     * 删除权限信息
     *
     * @param id
     * @return boolean
     * @author songyc
     * @since 2015-1-16
     */
    boolean deleteAuthority(Long id);

    /**
     * 查询权限信息(支持分页)
     *
     * @param pageNo   起始页
     * @param pageSize 每页显示多少条
     * @return Pageable<SysAuthority>
     * @author songyc
     * @since 2015-1-16
     */
    Pageable<SysAuthority> findAuthoritys(int pageNo, int pageSize);

    /**
     * 通过ID查询权限信息
     *
     * @param id
     */
    SysAuthority getAuthorityById(Long id);

    /**
     * 查询所有系统操作员
     *
     * @return List<SysOperator>
     * @author songyc
     * @since 2015-1-16
     */
    List<SysOperator> findAllOperator();

    /**
     * 查找操作人员所具有的权限
     *
     * @param operatorId 操作员ID
     * @return List<SysOperatorRoleMap>
     * @author songyc
     * @since 2015-1-16
     */
    List<SysOperatorRoleMap> findByOperatorId(Long operatorId);

    /**
     * 查询角色信息
     *
     * @return List<SysRole>
     * @author songyc
     * @since 2015-1-16
     */
    List<SysRole> findAllRole();

    /**
     * 新增操作员角色信息
     *
     * @param sysOperatorRoleMap
     * @return boolean
     * @author songyc
     * @since 2015-1-16
     */
    boolean addOperatorRoleMap(SysOperatorRoleMap sysOperatorRoleMap);

    /**
     * 通过操作员ID和角色ID查找操作员与角色对应关系
     *
     * @param operatorId
     * @param roleId
     * @return SysOperatorRoleMap
     * @author songyc
     * @since 2015-1-16
     */
    SysOperatorRoleMap getOperatorRoleMapByRoleId(String operatorId, String roleId);

    /**
     * 通过操作员ID删除操作员与角色对应关系
     *
     * @param operatorId
     * @return boolean
     * @author songyc
     * @since 2015-1-16
     */
    boolean deleteOperatorRoleMapByOperatorId(Long operatorId);

    /**
     * 查询所有权限
     *
     * @return List<SysAuthority>
     * @author songyc
     * @since 2015-1-16
     */
    List<SysAuthority> findAllAuthority();

    /**
     * 获取角色所有的权限
     *
     * @param roleId 角色ID
     * @return List<SysRoleAuthorityMap>
     * @author songyc
     * @since 2015-1-16
     */
    List<SysRoleAuthorityMap> findByRoleId(Long roleId);

    /**
     * 通过角色ID删除角色与权限关联关系
     *
     * @param roleId
     * @return boolean
     * @author songyc
     * @since 2015-1-16
     */
    boolean deleteRoleAuthorityMapByRoleId(Long roleId);

    /**
     * 新增角色与权限关联关系
     *
     * @param sysRoleAuthorityMap
     * @return boolean
     * @author songyc
     * @since 2015-1-16
     */
    boolean addRoleAuthorityMap(SysRoleAuthorityMap sysRoleAuthorityMap);


    /**
     * 获取角色的权限信息
     *
     * @param roleIds 角色Ids
     * @return List<SysAuthority>
     * @author songyc
     * @since 2015-1-16
     */
    List<SysAuthority> findByRoleIds(List<Long> roleIds);

    /**
     * 获取操作员的权限
     *
     * @param operatorId
     * @return List<SysAuthority>
     * @author songyc
     * @since 2015-1-16
     */
    List<SysAuthority> findAuthorityByOperatorId(Long operatorId);

    /**
     * 获取OperatorID
     *
     * @return int
     * @author songyc
     * @since 2015-1-16
     */
    int getOperatorLastId();

    /**
     * 获取RoleID
     *
     * @return int
     * @author songyc
     * @since 2015-1-16
     */
    int getRoleLastId();

    /**
     * 查询所有子权限
     *
     * @param condition
     * @return List<SysAuthority>
     * @author songyc
     * @since 2015-1-16
     */
    List<SysAuthority> findRelatedAuthorities(List<SysAuthority> condition);

    /**
     * 批量删除权限
     *
     * @param ids
     * @return boolean
     * @author songyc
     * @since 2015-1-16
     */
    boolean deleteAuthorityByIds(List<Long> ids);

    /**
     * 查询父节点下的一级子节点
     *
     * @param parentId
     * @return List<SysAuthority>
     * @author songyc
     * @since 2015-1-16
     */
    List<SysAuthority> findAuthoritiesByParentId(Long parentId);

    /**
     * @param ids
     * @description: 删除角色
     * @author liwei
     * @date 2014-12-1 下午4:14:35
     * @return: Boolean
     */
    Boolean deleteRole(List<Long> ids);

    /**
     * @param idsList
     * @description: 删除操作员
     * @author liwei
     * @date 2014-12-1 下午7:27:03
     * @return: Boolean
     */
    Boolean deleteOperator(List<Long> idsList);


    Pageable<SysAuthority> findAuthorityByPId(long id, int pageNo, int pageSize);

    /**
     * 批量保存角色与权限之间的关系
     *
     * @param roleId
     * @param authorityIds
     * @return boolean
     * @author yanhl
     * @since 2015年8月25日 上午11:50:27
     */
    boolean saveBatchRoleAuthority(Long roleId, List<Long> authorityIds);

    /**
     * 根据人员Id查找角色Id
     *
     * @param operatorId
     * @return List<Long>
     * @author yanhl
     * @since 2015年12月30日 上午9:23:00
     */
    List<Long> findRoleIdByOperatorId(Long operatorId);
    
    
    /**
     * 新增系统字典
     *
     * @param addSysDic
     * @return boolean
     * @author songyc
     * @since 2015-1-16
     */
    boolean addSysDic(SysDic sysDic);

    /**
     * 修改系统字典
     *
     * @param SysDic
     * @return boolean
     * @author songyc
     * @since 2015-1-16
     */
    boolean updateSysDic(SysDic sysDic);

    /**
     * 删除系统字典
     *
     * @param id
     * @return boolean
     * @author songyc
     * @since 2015-1-16
     */
    boolean deleteSysDic(List<Long> ids);

    /**
     * 查询系统字典(支持分页)
     *
     * @param pageNo   起始页
     * @param pageSize 每页显示多少条
     * @return Pageable<SysOperator>
     * @author songyc
     * @since 2015-1-16
     */
    Pageable<SysDic> findSysDics(String type, int pageNo,
                                        int pageSize);
    
    /**
     * 查询系统字典 根据类型
     *
     * @return Pageable<SysOperator>
     * @author songyc
     * @since 2015-1-16
     */
    List<SysDic> findDicsByType(String type);
    
    String getSysDicValueByKey(String key);
    
    public SysDic getSysDicByKeyAndType(String key,String type);
}
