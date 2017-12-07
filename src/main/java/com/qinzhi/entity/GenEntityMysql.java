package com.qinzhi.entity;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.UUID;

public class GenEntityMysql
{
    
    // 指定实体生成所在包的路径
    private String packageOutPath = "com.qinzhi.entity";
    
    // 主键
    private String primaryKey = "";
    
    // 列名数组
    private String[] colnames;
    
    // 列名类型数组
    private String[] colTypes;
    
    // 列名大小数组
    private int[] colSizes;
    
    // 是否需要导入包java.util.*
    private boolean f_util = false;
    
    /**
     * 数据库连接
     */
    // 表名
    private static final String TABLENAME = "level";
    
    // 库名
    private static final String DATABASENAME = "qinzhi";
    
    // 数据库地址
    private static final String URL = "jdbc:mysql://localhost:3306/";
    
    // 数据库用户名
    private static final String NAME = "root";
    
    // 数据库密码
    private static final String PASS = "123456";
    
    // 数据库驱动
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    
    /*
     * 构造函数
     */
    public GenEntityMysql()
    {
        // 创建连接
        Connection con;
        // 查要生成实体类的表
        String sql = "select * from " + TABLENAME;
        PreparedStatement pStemt = null;
        String content = null;
        try
        {
            try
            {
                Class.forName(DRIVER);
            }
            catch (ClassNotFoundException e1)
            {
                e1.printStackTrace();
            }
            con = DriverManager.getConnection(URL + DATABASENAME, NAME, PASS);
            pStemt = con.prepareStatement(sql);
            DatabaseMetaData metadata = con.getMetaData();
            ResultSetMetaData rsmd = pStemt.getMetaData();
            int size = rsmd.getColumnCount(); // 统计列
            
            colnames = new String[size];
            colTypes = new String[size];
            colSizes = new int[size];
            ResultSet a = metadata.getPrimaryKeys(DATABASENAME, null, TABLENAME);
            while (a.next())
            {
                primaryKey = a.getString(4);
            }
            for (int i = 0; i < size; i++)
            {
                colnames[i] = rsmd.getColumnName(i + 1);
                colTypes[i] = rsmd.getColumnTypeName(i + 1);
                
                if (colTypes[i].equalsIgnoreCase("datetime"))
                {
                    f_util = true;
                }
                colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
            }
            
            content = parse(colnames, colTypes, colSizes);
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            System.out.println(content);
        }
    }
    
    /**
     * 功能：生成实体类主体代码
     * 
     * @param colnames
     * @param colTypes
     * @param colSizes
     * @return
     */
    private String parse(String[] colnames, String[] colTypes, int[] colSizes)
    {
        StringBuffer sb = new StringBuffer();
        
        // 判断是否导入工具包
        sb.append("package " + this.packageOutPath + ";\r\n");
        
        sb.append("\r\n import javax.persistence.Column;");
        sb.append("\r\n import javax.persistence.Entity;");
        sb.append("\r\n import javax.persistence.GeneratedValue;");
        sb.append("\r\n import javax.persistence.GenerationType;");
        sb.append("\r\n import javax.persistence.Id;");
        sb.append("\r\n import javax.persistence.Table;");
        sb.append("\r\n import java.io.Serializable;");
        
        sb.append("\r\n import org.apache.commons.lang3.builder.ToStringBuilder;");
        
        if (f_util)
        {
            sb.append("import java.util.Date;\r\n");
        }
        sb.append("\r\n");
        // 实体部分
        sb.append("\r\n@Entity");
        sb.append("\r\n@Table(name =\"" + TABLENAME + "\")");
        sb.append("\r\n\r\npublic class " + initcap(TABLENAME) + " implements Serializable  {\r\n");
        sb.append("\r\n");
        sb.append("\r\n");
        // 自动生成 serialVersionUID
        Long id = (Long)UUID.randomUUID().getMostSignificantBits();
        sb.append("\tprivate static final long serialVersionUID = " + id + "L;\r\n");
        sb.append("\r\n");
        // 属性
        processAllAttrs(sb);
        // get set方法
        processAllMethod(sb);
        // 生成toString [根据情况添加]
        processToString(sb);
        sb.append("}\r\n");
        
        return sb.toString();
    }
    
    /**
     * 功能：生成toString
     * 
     * @param sb
     */
    private void processToString(StringBuffer sb)
    {
        sb.append("\t@Override\r\n");
        sb.append("\tpublic String toString()\r\n");
        sb.append("\t{\r\n");
        sb.append("\t   return ToStringBuilder.reflectionToString(this);\r\n");
        sb.append("\t}\r\n");
    }
    
    /**
     * 功能：生成所有属性
     * 
     * @param sb
     */
    private void processAllAttrs(StringBuffer sb)
    {
        
        for (int i = 0; i < colnames.length; i++)
        {
            String[] names = colnames[i].split("_");
            StringBuffer columnName = new StringBuffer();
            for (String n : names)
            {
                if (columnName.length() > 0)
                {
                    columnName.append(initcap(n.toLowerCase()));
                }
                else
                {
                    columnName.append(n.toLowerCase());
                }
            }
            sb.append("\t// 注释\r\n");
            if (primaryKey.equals(colnames[i]))
            {
                sb.append("\t@Id\r\n");
                sb.append("\t@GeneratedValue(strategy = GenerationType.IDENTITY)\r\n");
            }
            sb.append("\t@Column(name=\"" + colnames[i] + "\")\r\n");
            sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + columnName.toString() + ";\r\n");
            sb.append("\r\n");
        }
        
    }
    
    /**
     * 功能：生成所有方法
     * 
     * @param sb
     */
    private void processAllMethod(StringBuffer sb)
    {
        
        for (int i = 0; i < colnames.length; i++)
        {
            String[] names = colnames[i].split("_");
            StringBuffer columnName = new StringBuffer();
            for (String n : names)
            {
                if (columnName.length() > 0)
                {
                    columnName.append(initcap(n.toLowerCase()));
                }
                else
                {
                    columnName.append(n.toLowerCase());
                }
            }
            sb.append("\tpublic void set" + initcap(columnName.toString()) + "(" + sqlType2JavaType(colTypes[i]) + " "
                + columnName.toString() + "){\r\n");
            sb.append("\t   this." + columnName.toString() + "=" + columnName.toString() + ";\r\n");
            sb.append("\t}\r\n");
            sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get" + initcap(columnName.toString()) + "(){\r\n");
            sb.append("\t   return " + columnName.toString() + ";\r\n");
            sb.append("\t}\r\n");
        }
        
    }
    
    /**
     * 功能：将输入字符串的首字母改成大写
     * 
     * @param str
     * @return
     */
    private String initcap(String str)
    {
        
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z')
        {
            ch[0] = (char)(ch[0] - 32);
        }
        
        return new String(ch);
    }
    
    /**
     * 功能：获得列的数据类型
     * 
     * @param sqlType
     * @return
     */
    private String sqlType2JavaType(String sqlType)
    {
        
        if (sqlType.equalsIgnoreCase("bit"))
        {
            return "boolean";
        }
        else if (sqlType.equalsIgnoreCase("tinyint"))
        {
            return "byte";
        }
        else if (sqlType.equalsIgnoreCase("smallint"))
        {
            return "short";
        }
        else if (sqlType.equalsIgnoreCase("int"))
        {
            return "Integer";
        }
        else if (sqlType.equalsIgnoreCase("bigint"))
        {
            return "Long";
        }
        else if (sqlType.equalsIgnoreCase("float"))
        {
            return "Float";
        }
        else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
            || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
            || sqlType.equalsIgnoreCase("smallmoney"))
        {
            return "Double";
        }
        else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
            || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
            || sqlType.equalsIgnoreCase("text"))
        {
            return "String";
        }
        else if (sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("timestamp"))
        {
            return "Date";
        }
        else if (sqlType.equalsIgnoreCase("image"))
        {
            return "Blod";
        }
        
        return null;
    }
    
    /**
     * 出口 TODO
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        
        new GenEntityMysql();
        
    }
    
}