package com.qinzhi.service;

import com.qinzhi.bean.JsonResult;
import com.qinzhi.bean.ResultInfo;
import com.qinzhi.bean.Tree;
import com.qinzhi.domain.*;
import com.qinzhi.utils.Pageable;
import org.springframework.ui.ModelMap;

import java.util.List;


/**
 * ISystemService
 *
 * @author songyc
 * @since 2015-1-16
 */

public interface ISystemService {

    /**
     * 新增系统操作员
     *
     * @param operator
     * @param roles
     * @param operatorIp
     * @param sysoperator
     * @return boolean
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    boolean addOperator(SysOperator operator, String roles) throws Exception;

    /**
     * 修改系统操作员
     *
     * @param operator
     * @param operatorState
     * @param pwd
     * @param roles
     * @param operatorIp
     * @param sysOperator
     * @return boolean
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    boolean updateOperator(SysOperator operator, String operatorState, String pwd, String roles) throws Exception;

    /**
     * 修改系统操作员
     *
     * @param operator
     * @return boolean
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    boolean updateOperator(SysOperator operator) throws Exception;


    /**
     * 删除系统操作员
     *
     * @param id
     * @return boolean
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    boolean deleteOperator(Long id) throws Exception;

    /**
     * 查询系统操作员信息(支持分页)
     *
     * @param operatorName
     * @param loginName
     * @param pageNo       起始页
     * @param pageSize     每页显示多少条
     * @return Pageable<SysOperator>
     * @throws Exception
     */
    Pageable<SysOperator> findOperators(SysOperator sysOperator) throws Exception;

    /**
     * 通过ID查询操作员信息
     *
     * @param id
     * @return SysOperator
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    SysOperator getOperatorById(Long id) throws Exception;


    /**
     * 通过登录名称查询操作员信息
     *
     * @param loginName 登录名称
     * @return SysOperator
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    SysOperator getOperatorByLoginName(String loginName) throws Exception;

    /**
     * 新增角色信息
     *
     * @param role
     * @param operatorIp
     * @param operator
     * @return boolean
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    boolean addRole(SysRole role) throws Exception;

    /**
     * 修改角色信息
     *
     * @param role
     * @param operatorIp
     * @param operator
     * @return boolean
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    boolean updateRole(SysRole role) throws Exception;

    /**
     * 删除角色信息
     *
     * @param id
     * @return boolean
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    boolean deleteRole(Long id) throws Exception;

    /**
     * 查询角色信息(支持分页)
     *
     * @param roleName
     * @param pageNo   起始页
     * @param pageSize 每页显示多少条
     * @return Pageable<SysRole>
     * @throws Exception
     */
    Pageable<SysRole> findRoles(String roleName, int pageNo, int pageSize) throws Exception;

    /**
     * 查询角色信息
     *
     * @return List<SysRole>
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    List<SysRole> findAllRole() throws Exception;

    /**
     * 通过ID角色信息
     *
     * @param id
     * @return SysRole
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    SysRole getRoleById(Long id) throws Exception;

    /**
     * 新增权限信息
     *
     * @param authority
     * @param operatorIp
     * @param operator
     * @return boolean
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    boolean addAuthority(SysAuthority authority) throws Exception;

    /**
     * 修改权限信息
     *
     * @param authority
     * @param operatorIp
     * @param operator
     * @return boolean
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    boolean updateAuthority(SysAuthority authority) throws Exception;

    /**
     * 级联删除权限信息
     *
     * @param id
     * @param operatorIp
     * @param operator
     * @return boolean
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    boolean deleteAuthority(Long id) throws Exception;

    /**
     * 查询权限信息(支持分页)
     *
     * @param pageNo   起始页
     * @param pageSize 每页显示多少条
     * @return Pageable<SysAuthority>
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    Pageable<SysAuthority> findAuthoritys(int pageNo, int pageSize) throws Exception;

    /**
     * 通过ID查询权限信息
     *
     * @param id
     * @return SysAuthority
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    SysAuthority getAuthorityById(Long id) throws Exception;

    /**
     * 查询所有系统操作员
     *
     * @return List<SysOperator>
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    List<SysOperator> findAllOperator() throws Exception;

    /**
     * 查找操作人员所具有的权限
     *
     * @param operatorId 操作员ID
     * @return String
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    String findByOperatorId(Long operatorId) throws Exception;

    /**
     * 新增操作员角色信息
     *
     * @param operatorRoleMap
     * @return boolean
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    boolean addOperatorRoleMap(SysOperatorRoleMap operatorRoleMap) throws Exception;

    /**
     * 通过操作员ID和角色ID查找操作员与角色对应关系
     *
     * @param operatorId
     * @param roleId
     * @return SysOperatorRoleMap
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    SysOperatorRoleMap getOperatorRoleMapByRoleId(String operatorId, String roleId) throws Exception;

    /**
     * 通过操作员ID删除操作员与角色对应关系
     *
     * @param operatorId
     * @return boolean
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    boolean deleteOperatorRoleMapByOperatorId(Long operatorId) throws Exception;

    /**
     * 查询所有权限
     *
     * @return List<SysAuthority>
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    List<SysAuthority> findAllAuthority() throws Exception;

    /**
     * 获取角色所有的权限
     *
     * @param roleId
     * @return List<SysRoleAuthorityMap>
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    List<SysRoleAuthorityMap> findByRoleId(Long roleId) throws Exception;

    /**
     * 通过角色ID删除角色与权限关联关系
     *
     * @param roleId
     * @return boolean
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    boolean deleteRoleAuthorityMapByRoleId(Long roleId) throws Exception;

    /**
     * 新增角色与权限关联关系
     *
     * @param roleAuthorityMap
     * @return boolean
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    boolean addRoleAuthorityMap(SysRoleAuthorityMap roleAuthorityMap) throws Exception;


    /**
     * 获取角色的权限信息
     *
     * @param roleIds 角色Ids
     * @return List<SysAuthority>
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    List<SysAuthority> findByRoleIds(List<Long> roleIds) throws Exception;

    /**
     * 获取操作员的所有权限
     *
     * @param operatorId
     * @return List<SysAuthority>
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    List<SysAuthority> findAuthorityByOperatorId(Long operatorId) throws Exception;

    /**
     * 查询所有的权限列表
     * @param operatorId
     * @return
     */
    List<String> getAuthoritiesName(Long operatorId);

    /**
     * 获取Operator id
     *
     * @return int
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    int getOperatorLastId() throws Exception;

    /**
     * 获取Role id
     *
     * @return int
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    int getRoleLastId() throws Exception;

    /**
     * 获取权限树状菜单列表
     *
     * @return List<Tree>
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    List<Tree> getAuthorityTree() throws Exception;

    /**
     * 操作员与角色关联
     *
     * @param operatorId
     * @param roleIds
     * @return Boolean
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    Boolean addOperatorRoleMap(Long operatorId, List<Long> roleIds) throws Exception;

    /**
     * 角色与权限关联
     *
     * @param roleId
     * @param authorityIds
     * @return ResultInfo
     * @author songyc
     * @since 2015-1-16
     */
    void assignAuthorityForRole(Long roleId, List<Long> authorityIds);

    /**
     * 获取角色所有的权限叶子结点填充权限树
     *
     * @param roleId 角色ID
     * @return List<SysRoleAuthorityMap>
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    List<SysRoleAuthorityMap> findAuthorityForDisplayTree(Long roleId) throws Exception;


    /**
     * 删除角色
     *
     * @param ids
     * @param operatorIp
     * @param operator
     * @throws Exception
     * @return: Boolean
     * @author liwei
     * @since 2014-12-1
     */
    Boolean deleteRole(String ids) throws Exception;

    /**
     * 删除操作员
     *
     * @param ids
     * @return: Boolean
     * @author liwei
     * @since 2014-12-1
     */
    Boolean deleteOperator(String ids) throws Exception;

    void findOperatorInfo(String id, ModelMap model) throws Exception;

    void toFindAuthorityInfo(String id, ModelMap model) throws Exception;

    /**
     * 更新角色前置操作
     *
     * @param id
     * @param model
     * @throws Exception
     * @author songyc
     * @since 2015-1-22
     */
    void toUpdateRole(String id, ModelMap model) throws Exception;

    /**
     * 动态修改操作员角色关联
     *
     * @param operatorId
     * @return
     * @throws Exception
     * @author songyc
     * @since 2015-1-22
     */
    Object[] dynamicOperatorRole(long operatorId) throws Exception;

    /**
     * 增加操作员和角色关联
     *
     * @param rId
     * @param operatorId
     * @return boolean
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    boolean addOperatorRoleMap(String rId, String operatorId) throws Exception;

    /**
     * 动态修改角色权限关联
     *
     * @param roleId
     * @return Object[]
     * @throws Exception
     * @author songyc
     * @since 2015-1-22
     */
    Object[] dynamicRoleAuthority(long roleId) throws Exception;

    /**
     * 增加角色权限关联
     *
     * @param rId
     * @param roleId
     * @return boolean
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    boolean addRoleAuthorityMap(String rId, String roleId) throws Exception;

    /**
     * 检查登陆名称是否存在
     *
     * @param userName
     * @param pwd
     * @return JsonResult
     * @throws Exception
     * @author songyc
     * @since 2015-1-16
     */
    JsonResult checkLoginNameExists(String userName, String pwd) throws Exception;


    /**
     * 通过父权限id查询所以的子权限
     *
     * @param id
     * @param pageNo
     * @param pageSize
     * @param response
     * @return
     * @see [类、类#方法、类#成员]
     */
    Pageable<SysAuthority> findAuthorityByPId(long id, int pageNo, int pageSize);

    /**
     * 根据用户Id查询该用户下的角色Id
     *
     * @param operatorId
     * @return List<Long>
     * @author yanhl
     * @since 2015年12月30日 上午9:20:32
     */
    List<Long> findRoleIdByOperatorId(Long operatorId);

    /** 
     *<删除字典>
     *  
     * @param ids
     * @return
     * @see [类、类#方法、类#成员]
     */
    Boolean deleteDic(String ids);

    /**
     *根据type查询字典信息
     *
     * @param type
     * @return List<Long>
     * @author yanhl
     * @since 2015年12月30日 上午9:20:32
     */
    Pageable<SysDic> findDics(String type, int i, int pageSize);

    /** 
     *<更新字典>
     *  
     * @param dic
     * @return
     * @see [类、类#方法、类#成员]
     */
    boolean updateDic(SysDic dic);

    /** 
     *<新增>
     *  
     * @param dic
     * @return
     * @see [类、类#方法、类#成员]
     */
    boolean addDic(SysDic dic);

    /** 
     *<根据类型查询字典>
     *  
     * @param type
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<SysDic> findDics(String type);

	SysDic findDicByTypeAndKey(String key, String typeNavigation);

	SysOperator getByLoginNameAndState(String username);

}
