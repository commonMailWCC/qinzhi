package com.qinzhi.repository.mapper;

import com.qinzhi.domain.SysOperatorRoleMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @className: SysOperatorRoleMapMapper
 * @description: TODO
 * @author: liwei
 * @date: 2017-03-26 下午2:20:10
 */
public interface SysOperatorRoleMapMapper extends BaseMapper<SysOperatorRoleMap, Long> {

    /**
     * 查找操作人员所具有的权限
     *
     * @param operatorId 操作员ID
     * @return
     */
    List<SysOperatorRoleMap> findByOperatorId(@Param("operatorId") Long operatorId);

    /**
     * 通过操作员ID和角色ID查找操作员与角色对应关系
     *
     * @param operatorId ,
     * @param roleId
     * @return
     */
    SysOperatorRoleMap getOperatorRoleMapByRoleId(@Param("operatorId") String operatorId, @Param("roleId") String roleId);

    /**
     * 通过操作员ID删除操作员与角色对应关系
     *
     * @param operatorId
     * @return
     */
    int deleteOperatorRoleMapByOperatorId(@Param("operatorId") Long operatorId);

    int deleteByOperatorIds(@Param("ids") List<Long> ids);

	/**
	 * 根据人员Id查找角色Id
	 * 
	 * @param operatorId
	 * @return List<Long>
	 * @author yanhl
	 * @since 2015年12月30日 上午9:25:38
	 */
	List<Long> findRoleIdByOperatorId(@Param("operatorId") Long operatorId);

}