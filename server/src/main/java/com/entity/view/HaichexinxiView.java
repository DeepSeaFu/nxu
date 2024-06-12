package com.entity.view;

import com.entity.HaichexinxiEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
 

/**
 * 还车信息
 */
@TableName("haichexinxi")
public class HaichexinxiView  extends HaichexinxiEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public HaichexinxiView(){
	}
 
 	public HaichexinxiView(HaichexinxiEntity haichexinxiEntity){
 	try {
			BeanUtils.copyProperties(this, haichexinxiEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}
}
