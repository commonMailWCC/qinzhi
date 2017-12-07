package com.qinzhi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "sys_dic")
public class SysDic implements Serializable
{
    
    private static final long serialVersionUID = 833740718070647533L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    // 类型
    @Column(name = "dic_type")
    private String dicType;
    
    // key
    @Column(name = "dic_key")
    private String dicKey;
    
    // value
    @Column(name = "dic_value")
    private String dicValue;
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public Long getId()
    {
        return id;
    }
    
    public String getDicType()
    {
        return dicType;
    }
    
    public void setDicType(String dicType)
    {
        this.dicType = dicType;
    }
    
    public String getDicKey()
    {
        return dicKey;
    }
    
    public void setDicKey(String dicKey)
    {
        this.dicKey = dicKey;
    }
    
    public String getDicValue()
    {
        return dicValue;
    }
    
    public void setDicValue(String dicValue)
    {
        this.dicValue = dicValue;
    }
    
}
