package com.yougou.common;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 作用：给MAP集合排序
 * 创建：yinyunfei 2014-07-29
 */
public class SortMapByValue {

    /*** 
     *  排序主方法, 把Map集合按value值降序排序
     */  
    public static Map.Entry[] ascend(Map<String, Integer> h) {  
        Set set = h.entrySet();  
        Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set.size()]);  
        Arrays.sort(entries, new Comparator() {  
            public int compare(Object arg0, Object arg1) {  
                Long key1 = Long.valueOf(((Map.Entry) arg0).getValue().toString());  
                Long key2 = Long.valueOf(((Map.Entry) arg1).getValue().toString());  
                //升序 或者降序 调此参数 
                return key2.compareTo(key1);  
            }  
        });  
  
        return entries;  
    }  
    
    public static Map<String, Integer> mapCount(Map<String, Integer> map,int iRows){
    	
    	if(map!=null&&map.size()>0){
    		
            Map.Entry[] entries= ascend(map);  
            
            for(int i=0;i<entries.length;i++){  
            	
                Map.Entry entrie=entries[i];  
                System.out.println(entrie.getKey()+"="+entrie.getValue());  
                
                if(i<iRows){
                	
                	break;
                }
            } 
    	}
    	
    	return null;
    }
    
    /*** 
     *  排序方法二
     */  
    public static Map.Entry[] ascend2(Map<String, Long> h) {  
        Set set = h.entrySet();  
        Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set.size()]);  
        Arrays.sort(entries, new Comparator() {  
            public int compare(Object arg0, Object arg1) {  
                Long key1 = Long.valueOf(((Map.Entry) arg0).getValue().toString());  
                Long key2 = Long.valueOf(((Map.Entry) arg1).getValue().toString());  
                //升序 或者降序 调此参数 
                return key2.compareTo(key1);  
            }  
        });  
  
        return entries;  
    }  
    
    public static Map<String, Long> mapCount2(Map<String, Long> map,int iRows){
    	
    	if(map!=null&&map.size()>0){
    		
            Map.Entry[] entries= ascend2(map);  
            
            for(int i=0;i<entries.length;i++){  
            	
                Map.Entry entrie=entries[i];  
                System.out.println(entrie.getKey()+"="+entrie.getValue());  
                
                if(i<iRows){
                	
                	break;
                }
            } 
    	}
    	
    	return null;
    }
    
    public static void main(String[] args){  
        
    	Map<String, Integer> map=new HashMap<String, Integer>();  
        map.put("a",1);  
        map.put("b",2);  
        map.put("c",-1);  
        map.put("d",9999);  
        map.put("dd",89); 
        
        Map.Entry[] entries= ascend(map);  
        for(int i=0;i<entries.length;i++){  
        	
            Map.Entry entrie=entries[i];  
            System.out.println(entrie.getKey()+"="+entrie.getValue());  
        }  
    }  
}
