package com.idg.bfzb.server.utility.tools;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ObjectUtils {

	public static boolean isEmpty(Object o) throws IllegalArgumentException {
		if(o == null) return true;
		if(o instanceof String) {
			return (o == null) || (o.toString().trim().length() == 0) || "null".equalsIgnoreCase(o.toString());
		} else if(o instanceof Collection) {
			if(((Collection)o).isEmpty()){
				return true;
			}
		} else if(o.getClass().isArray()) {
			if(Array.getLength(o) == 0){
				return true;
			}
		} else if(o instanceof Map) {
			if(((Map)o).isEmpty()){
				return true;
			}
		}else {
			return false;
		}

		return false;
	}
	

	public static boolean isNotEmpty(Object o) {
		return !isEmpty(o);
	}
	


	public static Object mergeObject(List<?> list) {
		  if(list == null) return null;
		  Object object  = null;
		  try{
		  object = list.get(0).getClass().newInstance();
		  Field[] fs = object.getClass().getDeclaredFields();
		  for(Object o : list){
			  for (int i=0; i<fs.length; i++) {
				    fs[i].setAccessible(true);
				    if(fs[i].getType().isAssignableFrom(int.class) || fs[i].getType().isAssignableFrom(Integer.class)){
				    	if((Integer)fs[i].get(object)==null){
						    fs[i].set(object,(Integer)fs[i].get(o));
				    	}else if((Integer)fs[i].get(o)==null){
				    		fs[i].set(object,(Integer)fs[i].get(object));
				    	}else{
						    fs[i].set(object,(Integer)fs[i].get(object) + (Integer)fs[i].get(o));
				    	}
				    }else if( fs[i].getType().isAssignableFrom(String.class)){
				    	if((String)fs[i].get(object)==null){
						    fs[i].set(object,(String)fs[i].get(o));
				    	}else if((String)fs[i].get(o)==null){
				    		fs[i].set(object,(String)fs[i].get(object));
				    	}else{
						    fs[i].set(object,(String)fs[i].get(object) + (String)fs[i].get(o));
				    	}
				    }else if( fs[i].getType().isAssignableFrom(Byte.class)){
				    	if((Byte)fs[i].get(object)==null){
						    fs[i].set(object,(Byte)fs[i].get(o));
				    	}else if((Byte)fs[i].get(o)==null){
				    		fs[i].set(object,(Byte)fs[i].get(object));
				    	}else{
						    fs[i].set(object,(byte) ((Integer)((Byte)fs[i].get(object)& 0xff) + (Integer)((Byte)fs[i].get(o)& 0xff)));
				    	}
				    }else{
//				    	System.out.println("璇峰畾涔夊悎骞剁被鍨嬬殑鍚堝苟鏂规硶:"+fs[i].getType());
				    }
				    fs[i].setAccessible(false);
				  }
			  }
	 		}catch(Exception e){
	 			e.printStackTrace();
		   }
	 		return object;
		 }
}

