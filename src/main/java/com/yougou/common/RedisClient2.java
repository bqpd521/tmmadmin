package com.yougou.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 用途说明：缓存redis操作相关类 创建时间：2014-09-29 yinyunfei
 */
public class RedisClient2 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 789869111095749690L;

	/**
	 * 数据源
	 */
	private JedisPool jedisPool;

	/**
	 * 获取连接
	 * @return conn
	 */
	public void setJedisPool(JedisPool jp) {
		
		this.jedisPool = jp;
	}

	public JedisPool getJedisPool() {
		
		return this.jedisPool;
	}

	public Jedis getConnection() {
		
		try {
			
			Jedis jedis =jedisPool.getResource();
			return jedis;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 关闭连接
	 * @param conn
	 */
	public void closeConnection(Jedis jedis){
		
		if (null != jedis) {

			try {

				jedisPool.returnResource(jedis);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
    public static class ListTranscoder{  
        public static byte[] serialize(Object value) {  
            if (value == null) {  
                throw new NullPointerException("Can't serialize null");  
            }  
            byte[] rv=null;  
            ByteArrayOutputStream bos = null;  
            ObjectOutputStream os = null;  
            try {  
                bos = new ByteArrayOutputStream();  
                os = new ObjectOutputStream(bos);  
                os.writeObject(value);  
                os.close();  
                bos.close();  
                rv = bos.toByteArray();  
            } catch (IOException e) {  
                throw new IllegalArgumentException("Non-serializable object", e);  
            } finally {  
                close(os);  
                close(bos);  
            }  
            return rv;  
        }  
        
        public static void close(Closeable closeable) {  
            if (closeable != null) {  
                try {  
                    closeable.close();  
                } catch (Exception e) {  
                    //logger.info("Unable to close %s", closeable, e);  
                	e.printStackTrace();
                }  
            }  
        } 
  
        public static Object deserialize(byte[] in) {  
            Object rv=null;  
            ByteArrayInputStream bis = null;  
            ObjectInputStream is = null;  
            try {  
                if(in != null) {  
                    bis=new ByteArrayInputStream(in);  
                    is=new ObjectInputStream(bis);  
                    rv=is.readObject();  
                    is.close();  
                    bis.close();  
                }  
            } catch (IOException e) {  
                //logger.warn("Caught IOException decoding %d bytes of data", in == null ? 0 : in.length, e);  
            	e.printStackTrace();
            } catch (ClassNotFoundException e) {  
                //          	logger.warn("Caught CNFE decoding %d bytes of data", in == null ? 0 : in.length, e); 
            	e.printStackTrace();
            } finally {  
            	
                try {
                	
					is.close();
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }  
            return rv;  
        }  
    } 
	
	/**
	 * 精选商品key (要取哪天加上当天日期即可 格式 yyyy-MM-dd)
	 */
	public String CHOSEN_GOODS_KEY="chosen_goods_key_";
	
	/**
	 * 所有精选商品key
	 */
	public String ALL_CHOSEN_GOODS_KEY="all_chosen_goods_key";
	
	/**
	 * 用户日志key
	 */
	public String USER_LOG_KEY="user_log_key_00-ac";
	
	/**
	 * 丑榜key 
	 */
	public String UGLY_LEST_KEY="ugly_list_key";
	
	/**
	 * 赞榜-key 
	 */
	public String PRAISE_LEST_KEY="praise_list_key";
	
	/**
	 * 我赞过的 赞榜-key 
	 */
	public String MYSELFT_PRAISE_LEST_KEY="myself_praise_list_key";
	
	/**
	 * 所有用户的userid与token -key 
	 */
	public String ALL_USERID2TOKE_KEY="all_userid2token_key";
}
