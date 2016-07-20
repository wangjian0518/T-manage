package com.db;

import java.text.SimpleDateFormat;
import java.util.*;

public class test {

	public static void main(String[] args) {
        
        test Testdb2 = new test();
        Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		String datt = format.format(date);
		System.out.println(datt);
       // Testdb2.insert_trains_Info();
        /**
         * 查询操作
         */
        //Testdb2.select();
         
        /**
         * 增、删、改操作
         */
      //  Testdb2.update();
 
    }
	
	@SuppressWarnings("rawtypes")
	public void select() {
        //创建一个ConnectionDB2的对象
		Sql db2 = new Sql();
         
        //写一条SQL语句
        String sql = "select t.user_pwd from userinfo t where t.user_number = ?";
         
        //创建一个和SQL语句匹配的参数数组
        Object[] arrays = {1000};
         
        //调用db2的“查询数据库”方法,返回List
        List list = db2.getDataBySql(sql, arrays);
 
        //遍历list，并打印出结果
        for (Object object : list) {
            //list里放的是Map
            Map map = (Map) object;
             
            Iterator it = map.keySet().iterator();
             
            while(it.hasNext()) {
                //要查询的字段名（列名）
                String key = (String) it.next();
                 
                //该列对应的值
                Object value = map.get(key);
                 
                System.out.print("column:"+key+"  value:"+value);
                System.out.println();
            }
        }
    }
     
     
    public void insert_trains_Info() {
        //创建一个ConnectionDB2的对象
    	Sql db2 = new Sql();
         
        //写一条SQL语句
        String sql = "insert into trains_Info values(?,?,?,?,?,?,?,?)";
         
        //创建一个和SQL语句匹配12的参数数组
        Object arrays[] = {"2112","参数数组","参数数组","参数数组","22:21","21:00","参数数组","参数数组"};
         
        //调用db2的“更新数据库”方法，返回影响的行数
        int line = db2.updateBySql(sql, arrays);
         
        //line大于0则更新成功
        if(line > 0){
            System.out.println("更新成功");
        } else {
            System.out.println("更新失败");
        }
    }
    public void insert_trains_schedule(Object arrays[]) {
    	//创建一个ConnectionDB2的对象
    	Sql db2 = new Sql();
    	
    	//写一条SQL语句
    	String sql = "insert into trains_schedule values(?,?,?,?,?,?,?,?,?,?,?)";
    	
    	//创建一个和SQL语句匹配的参数数组
    	//Object arrays[] = {7,"毛泽东",78};
    	
    	//调用db2的“更新数据库”方法，返回影响的行数
    	int line = db2.updateBySql(sql, arrays);
    	
    	//line大于0则更新成功
    	if(line > 0){
    		System.out.println("更新成功");
    	} else {
    		System.out.println("更新失败");
    	}
    }
}
