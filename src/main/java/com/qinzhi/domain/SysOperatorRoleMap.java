package com.qinzhi.domain;

import java.io.Serializable;


/**
 * @className: SysOperatorRoleMap
 * @description: 操作员和角色关系
 * @author: liwei
 * @date: 2017-03-26 下午2:14:25
 */
public class SysOperatorRoleMap implements Serializable {
    private static final long serialVersionUID = 8033217372679134641L;


    /**
     * 主键
     */
    private Long id;

    /**
     * 操作员
     */
    private SysOperator sysOperator;

    /**
     * 角色
     */
    private SysRole sysRole;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SysOperator getSysOperator() {
        return sysOperator;
    }

    public void setSysOperator(SysOperator sysOperator) {
        this.sysOperator = sysOperator;
    }

    public SysRole getSysRole() {
        return sysRole;
    }

    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }


}