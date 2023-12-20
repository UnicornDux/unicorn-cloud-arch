package com.edu.code.redis.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    // 注入操作模板
    private RedisTemplate<String,Object> redisTemplate;


    /**
     * 指定缓存失效时间
     * @param key
     * @param seconds
     * @return
     */
    public boolean expire(String key, long seconds) {
        try {
           if (seconds > 0)  {
               redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
           }
           return true;
        }catch (Exception e){
           e.printStackTrace();
           return false;
        }
    }

    /**
     * 获取过期时间
     * @param key
     * @return
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);

    }

    /**
     * 判断 key 是否存在
     * @param key
     * @return
     */
    public  boolean hasKey(String key){
       try {
           return redisTemplate.hasKey(key);
       }catch (Exception e){
           e.printStackTrace();
           return false;
       }
    }

    /**
     * 删除缓存
     * @param key, 可以传递一个或者多个 key
     */
    public void del(String ...key){
       if (key != null && key.length > 0) {
          if (key.length == 1) {
             redisTemplate.delete(key[0]);
          }else {
              redisTemplate.delete(Arrays.asList(key));
          }
       }
    }

    // ===================== String ========================


}
