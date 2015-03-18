package com.yougou.common;

/**
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.Field;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class Solr {

	//指定solr服务器的地址  
    private final static String URL = "http://10.10.10.142/solr/core0";  
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			test();
			test4();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void test() {
		
        //1、创建SolrServer对象，该对象有两个可以使用，都是线程安全的  
//      CommonsHttpSolrServer：启动web服务器使用的，通过http请求的  
//      EmbeddedSolrServer：内嵌式的，导入solr的jar包就可以使用了  
        try {  
        	CommonsHttpSolrServer server = new CommonsHttpSolrServer(URL); 
//			HttpSolrServer server = new HttpSolrServer(URL);  
            //把查询出来的数据全部删除  
//          server.deleteByQuery("*:*");  
//          server.commit();  
              
            SolrInputDocument doc = new SolrInputDocument();  
            //id是必填的，并且是String类型的  
            //<field name="id" type="string" indexed="true" stored="true" required="true" />  
            //id是唯一的主键，当多次添加的时候，最后添加的相同id会覆盖前面的域  
            doc.addField("id", "338");  
            doc.addField("name", "这是我的第336个solrj程序，呵呵");  
            doc.addField("core0", "solr程序的运行337!");  
            doc.addField("userName", "yinyunfei337!");
            server.add(doc);  
            server.commit();  
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (SolrServerException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
	}
	
    public static void test4() throws SolrServerException, MalformedURLException{  
        CommonsHttpSolrServer server = new CommonsHttpSolrServer(URL);  
        //定义查询字符串  
        SolrQuery query = new SolrQuery("*:*");  
        //实现分页的查询  
        query.setStart(0);  
        query.setRows(10);  
        QueryResponse res = server.query(query);  
        //查询出来的结果都保存在SolrDocumentList中  
        SolrDocumentList sdl = res.getResults();  
        System.out.println("总数："+sdl.getNumFound());  
        for(SolrDocument sd : sdl){  
        	
            System.out.println(sd.get("id")+"#"+sd.get("name")+"#"+sd.get("userName"));
        }  
    }  
	
	public static void test5() throws MalformedURLException, SolrServerException{  
        CommonsHttpSolrServer server = new CommonsHttpSolrServer(URL);  
        //相当于QueryParser  
        SolrQuery query = new SolrQuery("*:*");  
        query.setStart(1);  
        query.setRows(3);  
        QueryResponse res = server.query(query);  
        //可以直接查询相应的bean对象，但是不是很常用  
        //使用这种方式无法获取总数量  
        List<Message> list = res.getBeans(Message.class);  
        System.out.println("当前总数："+list.size());  
        for(Message msg : list){  
            System.out.println(msg.getId()+"#"+msg.getName()+"#"+msg.getCore0());  
        }  
    }
	
    public static void test6() throws SolrServerException, MalformedURLException{  
        CommonsHttpSolrServer server = new CommonsHttpSolrServer(URL);  
        SolrQuery query = new SolrQuery("name:solr");  
        query.setHighlight(true).setHighlightSimplePre("<span class='red'>").setHighlightSimplePost("</span>")  
        .setStart(0).setRows(10);  
        //hl.fl表示高亮的field，也就是高亮的区域  
        query.setParam("hl.fl", "name,core0,userName");  
        QueryResponse res = server.query(query);  
          
        SolrDocumentList sdl = res.getResults();  
        System.out.println("总数："+sdl.getNumFound());  
        for(SolrDocument sd : sdl){  
//          System.out.println(sd.get("id")+"#"+sd.get("msg_title")+"#"+sd.get("msg_content"));  
            String id = (String) sd.get("id");  
            //在solr这里对需要加高亮的字段必须要在索引中的store=true才行  
            System.out.println(id+"#"+res.getHighlighting().get(id).get("core0"));;  
        }  
    }
    
    public class Message {  
        private String id;  
        public String getName() {
			return name;
		}

        @Field("name")
		public void setName(String name) {
			this.name = name;
		}

		public String getCore0() {
			return core0;
		}

		@Field("core0")
		public void setCore0(String core0) {
			this.core0 = core0;
		}

		private String name;  
        private String core0;  
          
          
        public Message() {  
            super();  
        }  
      
        public Message(String id, String name, String core0) {  
            super();  
            this.id = id;  
            this.name = name;  
            this.core0 = core0;  
        }  
      
        public String getId() {  
            return id;  
        }  
          
        @Field
        public void setId(String id) {  
            this.id = id;  
        }  

    }  

}**/
