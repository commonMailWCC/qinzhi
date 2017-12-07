package com.qinzhi.domain;

import java.io.Serializable;

/**
 * @className: 权限
 * @author: liwei
 * @date: 2017-03-27 下午1:56:23
 */
public class SysAuthority implements Serializable {
    private static final long serialVersionUID = -7059331243549997464L;

    /**
     * 主键
     */
    private Long authorityId;

    /**
     * 权限编号
     */
    private String authorityCode;

    /**
     * 权限名称
     */
    private String authorityName;

    /**
     * 权限描述
     */
    private String authorityDesc;
    /**
     * 父节点ID
     */
    private Long authorityFid;

    private Long authorityOrder;


    public SysAuthority() {
    }


    public SysAuthority(Long authorityId, String authorityCode,
                        String authorityName, String authorityDesc, Long authorityFid,
                        Long authorityOrder) {
        this.authorityId = authorityId;
        this.authorityCode = authorityCode;
        this.authorityName = authorityName;
        this.authorityDesc = authorityDesc;
        this.authorityFid = authorityFid;
        this.authorityOrder = authorityOrder;
    }


    public Long getAuthorityId() {
        return authorityId;
    }


    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }


    public String getAuthorityCode() {
        return authorityCode;
    }


    public void setAuthorityCode(String authorityCode) {
        this.authorityCode = authorityCode;
    }


    public String getAuthorityName() {
        return authorityName;
    }


    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }


    public String getAuthorityDesc() {
        return authorityDesc;
    }


    public void setAuthorityDesc(String authorityDesc) {
        this.authorityDesc = authorityDesc;
    }


    public Long getAuthorityFid() {
        return authorityFid;
    }


    public void setAuthorityFid(Long authorityFid) {
        this.authorityFid = authorityFid;
    }


    public Long getAuthorityOrder() {
        return authorityOrder;
    }


    public void setAuthorityOrder(Long authorityOrder) {
        this.authorityOrder = authorityOrder;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Authority [id=").append(authorityId).append(", code=").append(authorityCode).append(", name=").append(authorityName).append(", description=").append(authorityDesc)
                .append(", parentId=").append(authorityFid).append(", sortOrder=").append(authorityOrder).append("]");
        return builder.toString();
    }
}