package com.qinzhi.repository.mapper;

import com.qinzhi.domain.SysOperator;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @className: SysOperatorMapper
 * @description: TODO
 * @author: liwei
 * @date: 2017-03-26 下午2:20:35
 */
public interface SysOperatorMapper extends BaseMapper<SysOperator, Long> {

    /**
     * 查询系统操作员信息(支持分页)
     *
     * @param pageNo   起始页
     * @param pageSize 每页显示多少条
     * @return List <Operator>
     */
    List<SysOperator> findOperators(SysOperator sysOperator);

    /**
     * 通过登录名称查询操作员信息
     *
     * @param loginName 登录名称
     * @return
     */
    SysOperator getOperatorByLoginName(@Param("loginName") String loginName);

    /**
     * 通过登录名称和state查询操作员信息
     *
     * @param loginName 登录名称
     * @param state
     * @return SysOperator
     * @author songyc
     * @since 2015-1-16
     */
    SysOperator getByLoginNameAndState(@Param("loginName") String loginName, @Param("state") Integer state);

    /**
     * 根据名称获取总数
     *
     * @param operatorName
     * @param loginName
     * @return
     */
    int getCountByName(SysOperator sysOperator);

    int getOperatorLastId();

    /**
     * @param ids
     * @return
     * @description: 删除操作员
     * @author liwei
     * @date 2014-12-1 下午7:27:54
     * @return: int
     */
    int deleteOperator(@Param("ids") List<Long> ids);
}