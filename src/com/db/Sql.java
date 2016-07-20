package com.db;

import java.sql.*;
import java.util.Vector;

public class Sql {

	/**
     * 驱动包
     */
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    /**
     * 数据库地址
     */
    private final String URL = "jdbc:mysql://127.0.0.1:3306/train?useUnicode=true&characterEncoding=utf-8";
    /**
     * 用户名
     */
    private final String USER = "root";
     
    /**
     * 密码
     */
    private final String PASSWORD = "root";
    /**
     * 数据库连接
     */
    Connection conn;
     
    /**
     * SQL命令
     */
    PreparedStatement pst;
     
    /**
     * 结果集
     */
    ResultSet rs;
    
    /**
     * 加载数据库驱动
     */
    static{
        try {
            //加载驱动
            Class.forName(DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("加载驱动失败");
        }
    }
    
    /**
     * 取得和数据库的连接
     * @return Connection
     */
    private Connection getConnection() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("连接数据库失败");
        }
        return conn;
    }
    
    /**
     * 查询数据库
     * @param sql SQL语句
     * @param arrays 参数数组
     * @return 参数列表
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Vector<Vector> getDataBySql(String sql,Object arrays[]) {
    	Vector list = new Vector<Vector>();
    	Vector row = new Vector<String>();
        try {
            //取得连接
            conn = this.getConnection();
            if(conn == null) {
                return list;
            }
            //用sql语句对数据库进行操作
            pst  =conn.prepareStatement(sql);
            //设置参数
            if(arrays.length>0){
            	for (int i = 0; i < arrays.length; i++) {
            		pst.setObject(i+1, arrays[i]);
            	}
            }
            //将查询结果放入结果集rs中
            rs = pst.executeQuery();
            //遍历rs，并将rs的内容放入row中
            while(rs.next()) {
                // 获取包含有关 ResultSet 对象列信息的 ResultSetMetaData 对象
                ResultSetMetaData rm = pst.getMetaData();
                row = new Vector<String>();
                for (int i = 1; i <=rm.getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                //将row放入list中
                list.add(row);
                //System.out.println(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return list;
    }
    
    /**
     * 插入、删除、更新操作
     * @param sql SQL语句
     * @param arrays 参数数组
     * @return 影响的行数
     */
	public int updateBySql(String sql,Object arrays[]) {
        //初始化影响的行数
        int line = 0;
        try {
            conn = this.getConnection();
            if(conn == null) {
                return 0;
            }
            pst  =conn.prepareStatement(sql);
            //设置参数
            if(arrays.length>0){
	            for (int i = 0; i < arrays.length; i++) {
	                pst.setObject(i+1, arrays[i]);
	            }
            }
            //操作数据库，返回行数
            line = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return line;
    }
     
    /**
     * 关闭数据库连接
     */
    public void close() {
        try {
            if(rs != null){
                rs.close();
            }
            if(pst != null){
                pst.close();
            }
            if(conn != null){
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("关闭连接出错");
            System.exit(0);
        }
    }
}
