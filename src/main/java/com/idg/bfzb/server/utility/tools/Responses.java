package com.idg.bfzb.server.utility.tools;

import java.util.HashMap;
import java.util.Map;


public class Responses{

	//不允许被实例化
    private Responses(){};
    
    private final static String SUCCESS = "success";
    private final static String URL = "url";
    private final static String MSG = "msg";
    
    public static Map writeFailAndMsg(String error,String url){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put(SUCCESS,false);
        map.put(URL,url);
        map.put(MSG,error);
        return map;
    }
    
    public static Map writeFailAndMsg(String error){
        return writeFailAndMsg(error, null);
    }
    
    public static Map writeSuccessAndMsg(String msg,String url){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put(SUCCESS,true);
        map.put(URL,url);
        map.put(MSG,msg);
        return map;
    }
    
    public static Map writeSuccessAndMsg(String msg){
    	return writeSuccessAndMsg(msg,null);
    }
    
    public static Map writeSuccessAndMap(String key,Object value){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put(SUCCESS,true);
        map.put(key,value);
        return map;
    }
    
}
