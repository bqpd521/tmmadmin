package com.yougou.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yougou.model.AdminUserModel;

public class test {

	public static void main2(String[] args) {
		// TODO Auto-generated method stub

		                    //说明：两个反斜线 前一个是转义 C#里面好像是@， java里面转义是 \
		String excelInfo="wg1\\电机1:100*100。wg\\电机2:100*200。wg2\\电机3:100*300。wg3\\电机4:100*400。wg4\\电机5:100*500。";
		
		if(excelInfo!=null&&!"".equals(excelInfo)){
			
			String str1Array[] =excelInfo.split("。");
			int iIndexLenght=str1Array.length;
			
			for(int i=0;i<iIndexLenght;i++){
				
				try {
					
					String str1=str1Array[i];
					//str1[i] 内容如下：
//					wg1\电机1:100*100
//					wg\电机2:100*200
//					wg2\电机3:100*300
//					wg3\电机4:100*400
//					wg4\电机5:100*500
//					System.out.println(str1);
					
					if(str1!=null&&!"".equals(str1)){
						                                 //此处有转义  在C# 里面用@
						String str1Array2[] = str1.split("\\\\");
						//变量wg变量就是 wg1   
						String wg=str1Array2[0];
						String dianji=str1Array2[1].split(":")[0];
						String heightAndWeight=str1Array2[1].split(":")[1];
						
						System.out.println(wg+","+dianji+","+heightAndWeight);
						//接下扫行你要做的数据操作
						//insert or udate 
					}
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main3(String[] args)  {
		
		Map<String, Object> map = new HashMap<String, Object>();
		 map.put("username", "zhangsan"); 
		 map.put("age", 24); 
		 map.put("sex", "男");  
		 
		 Map<String, Object> temp = new HashMap<String, Object>(); 
		  temp.put("name", "xiaohong"); 
		  temp.put("age", "23"); 
		  map.put("girlInfo", temp);  
		  
		//list集合 
		  List<String> list = new ArrayList<String>(); 
		  list.add("爬山"); 
		  list.add("骑车"); 
		  list.add("旅游"); 
		  map.put("hobby", list);  
		  AdminUserModel al =new AdminUserModel();
		  al.setUserAccount("abcd");
		  Map<String, AdminUserModel> mapV = new HashMap<String, AdminUserModel>();
		  mapV.put("yinyf", al);
		  
		  try {
			String jsonString = JSON.toJSONString(map,true);
			//http://blog.sina.com.cn/s/blog_4f925fc30102egx0.html
			System.out.println(jsonString);
			Map<String, Object> temp2 =(Map<String, Object>)JSON.parse(jsonString);
			
			String jsonString2 = JSON.toJSONString(mapV,true);
			System.out.println(jsonString2);
			Map<String, AdminUserModel> mapV2=(Map<String, AdminUserModel>)JSON.parse(jsonString2);
//			String uString="";
//			if(mapV2!=null){
//				
//				AdminUserModel al3=(AdminUserModel)mapV2.get("yinyf");
//				uString=al3.getUserAccount();
//			}
//			
//			System.out.print(temp2.size()+",,,,"+uString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main4(String[] args)  {
		
		//1:List生成json 
		List<String> tempList1=new ArrayList<String>();
		tempList1.add("yin");
		tempList1.add("yun");
		tempList1.add("fei");
		String jsonString1 = JSON.toJSONString(tempList1,false);
		System.out.println("jsonString1==:"+jsonString1);
		List<String> tempList11=(List<String>)JSON.parse(jsonString1);
		System.out.println("jsonString1====:"+tempList11.size());
		for(String str:tempList11){
			
			System.out.println(str);
		}
		
		//list 自定义对象 start
		List<AdminUserModel> tempList12=new ArrayList<AdminUserModel>();
		AdminUserModel al12=new AdminUserModel();
		al12.setUserAccount("yinyfcount");
		al12.setId(3);
		tempList12.add(al12);
		SerializerFeature[] featureArr = { SerializerFeature.WriteClassName };  
		String jsonString2 = JSON.toJSONString(tempList12,featureArr);
		System.out.println("jsonString12==:"+jsonString2);
		
		List<AdminUserModel> tempList122=(List<AdminUserModel>)JSON.parse(jsonString2);
		System.out.println("jsonString122==:"+tempList122.size());
		for(AdminUserModel al22:tempList122){
			
			System.out.println(al22.getUserAccount()+","+al22.getId());
		}
		//list 自定义对象 end
		
		//自定义对象  start
		AdminUserModel al3=new AdminUserModel();
		al3.setInsertTime("2014");
		al3.setUserName("尹云飞");
		String jsonAdminUserModel = JSON.toJSONString(al3,featureArr);
		
		AdminUserModel al33=(AdminUserModel)JSON.parse(jsonAdminUserModel);
		
		System.out.println("AdminUserModel al33==:"+al33.getUserName());
		//自定义对象  end
		
		//Map 存放 基本类型 start
		
		Map<String, AdminUserModel> tempMap31=new HashMap<String, AdminUserModel>();
		AdminUserModel al311=new AdminUserModel();
		al311.setInsertTime("2015");
		al311.setUserName("尹云飞3");		
		tempMap31.put("a31", al311);
		
		String jsonMap31 = JSON.toJSONString(tempMap31,featureArr);
		System.out.println("jsonMap31 al33==:"+jsonMap31);
		
		Map<String, AdminUserModel> tempMap311=(Map<String, AdminUserModel>)JSON.parse(jsonMap31);
		AdminUserModel al3111=tempMap311.get("a31");
		System.out.println("jsonMap31 al3111==:"+al3111.getUserName());
		//Map 存放 基本类型 end
		
		//Map 存放List start
		
		Map<String, List<AdminUserModel>> tempMapList41=new HashMap<String, List<AdminUserModel>>();
		List<AdminUserModel> tempList41=new ArrayList<AdminUserModel>();
		AdminUserModel al411=new AdminUserModel();
		al411.setInsertTime("2016");
		al411.setUserName("尹云飞4");
		tempList41.add(al411);
		tempMapList41.put("a41", tempList41);
		
		String jsonMap41 = JSON.toJSONString(tempMapList41,featureArr);
		System.out.println("jsonMap41==:"+jsonMap41);
		RedisClient rClient=new RedisClient();
		rClient.jedis.set("rediskey", jsonMap41);
		
		String jsonRedis=rClient.jedis.get("rediskey");
		Map<String, List<AdminUserModel>> tempMapList411=(Map<String, List<AdminUserModel>>)JSON.parse(jsonRedis);
		List<AdminUserModel> tempList411=tempMapList411.get("a41");
		AdminUserModel al4111=tempList411.get(0);
		
		System.out.println("jsonMap41==setUserName:"+al4111.getUserName());
		//Map 存放List end
	}
	
	public static void main(String[] args)  {
		
		String str = "信号与系统（第三版）";
		str=str.replaceAll(" ", "");
		System.out.println("========"+str);
	}
}
