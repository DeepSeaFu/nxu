package com.entity.view;

import com.entity.SystemintroEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
 

/**
 * 关于我们
 */
@TableName("systemintro")
public class SystemintroView  extends SystemintroEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public SystemintroView(){
	}
 
 	public SystemintroView(SystemintroEntity systemintroEntity){
 	try {
			BeanUtils.copyProperties(this, systemintroEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}
}
