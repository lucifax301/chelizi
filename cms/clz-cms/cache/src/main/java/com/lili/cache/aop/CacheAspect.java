package com.lili.cache.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.ognl.OgnlException;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.lili.cache.LiLiCacheInterface;
import com.lili.cache.annotation.LiLiCache;

@Aspect
@Component
public class CacheAspect {

	private static Logger logger=Logger.getLogger(CacheAspect.class);
	
	@Pointcut("@annotation(com.lili.cache.annotation.LiLiCache)")  
    public  void cacheAspect() {  
    } 
    
	@Resource(name="redisCache")
    private LiLiCacheInterface cache;
    
    @Around("cacheAspect()")
    public Object get(ProceedingJoinPoint call)throws Throwable {
    	System.out.println("docache---------------------------------------");
    	LiLiCache anno=getAnnotation(call,LiLiCache.class);
        
        String key = anno.key();
        String namespace=anno.namespace();
        String action=anno.action();
        
        key = getKeyNameFromParam(key,call);
        
        Object value=cache.get(namespace+"-"+key);
        
        if(value == null){//缓存为空
            value = call.proceed();
            
            if("ADD".equals(action)){
            	if(value != null){
                    cache.put(namespace+"-"+key,value);
                }
            }else if("UPDATE".equals(action)){
                if(value != null){
                    cache.remove(namespace+"-"+key);
                    cache.put(namespace+"-"+key,value);
                }
            }
        }else{//缓存不为空
        	if("DELETE".equals(action)){//判断是否要删除
                cache.remove(namespace+"-"+key);
            }
        }
  
        return value;
    }
    
    
    private <T  extends Annotation> T getAnnotation(ProceedingJoinPoint jp,Class<T> clazz){
         MethodSignature joinPointObject = (MethodSignature) jp.getSignature();  
         Method method = joinPointObject.getMethod();
         return method.getAnnotation(clazz);  
    }
    
    private Class<?> getType(ProceedingJoinPoint jp){
        MethodSignature joinPointObject = (MethodSignature) jp.getSignature();  
        Method method = joinPointObject.getMethod();
        return method.getReturnType();
    }
    
    
     /**
     * 
     * @Title: getKeyNameFromParam
     * @Description: 获得组合后的KEY值
     * @param @param key
     * @param @param jp
     * @param @return
     * @return String
     * @throws
     */
    private String getKeyNameFromParam(String key,ProceedingJoinPoint jp){
        if(!key.contains("$")){
            return key;
        }
        
        String regexp = "\\$\\{[^\\}]+\\}";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(key);
        List<String> names = new ArrayList<String>();
        try{
            while(matcher.find()){
                names.add(matcher.group());
            }
            key = executeNames(key,names,jp);
        }catch (Exception e) {
            logger.error("Regex Parse Error!", e);
        }
        
        
        return key;
    }
    
    /**
     * 
     * @Title: executeNames
     * @Description: 对KEY中的参数进行替换
     * @param @param key
     * @param @param names
     * @param @param jp
     * @param @return
     * @param @throws OgnlException
     * @return String
     * @throws
     */
    private String executeNames(String key, List<String> names,ProceedingJoinPoint jp) throws OgnlException {
        
        Method method = ((MethodSignature)jp.getSignature()).getMethod();
        
        //形参列表
        List<String> param = MethodParamNamesScaner.getParamNames(method);
        
        if(names==null||names.size()==0){
            return key;
        }
        
        Object[] params = jp.getArgs();
        
        Map<String,Object> map = new HashMap<String,Object>();
        for(int i=0;i<param.size();i++){
            map.put(param.get(i), params[i]);
        }
        
        for(String name:names){
            String temp = name.substring(2);
            temp = temp.substring(0,temp.length()-1);
            key = myReplace(key,name, Ognl.getValue(temp, map)+"");
        }
        
        return key;
    }
    
    /**
     * 
     * @Title: myReplace
     * @Description: 不依赖Regex的替换，避免$符号、{}等在String.replaceAll方法中当做Regex处理时候的问题。
     * @param @param src
     * @param @param from
     * @param @param to
     * @param @return
     * @return String
     * @throws
     */
    private String myReplace(String src,String from,String to){
        int index = src.indexOf(from);
        if(index == -1){
            return src;
        }
        
        return src.substring(0,index)+to+src.substring(index+from.length());
    }
}
