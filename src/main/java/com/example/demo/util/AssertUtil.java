package com.example.demo.util;

import java.util.Collection;

public class AssertUtil {
	
	public static Boolean isEmpty(Object obj){
		Boolean flag = false;
		if(obj instanceof String){
			if(obj == null || obj == ""){
				flag = true;
			}
		}else{
			if(obj == null){
				flag = true;
			}
		}
		return flag;
	}
	
	public static Boolean isEmpty(Collection collection){
		Boolean flag = false;
		if(collection == null || collection.size() <= 0){
			flag = true;
		}
		return flag;
	}

}
