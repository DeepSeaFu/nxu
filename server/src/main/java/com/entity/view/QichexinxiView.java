package com.entity.view;

import com.entity.QichexinxiEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
 

/**
 * 汽车信息
 */
@TableName("qichexinxi")
public class QichexinxiView  extends QichexinxiEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public QichexinxiView(){
	}
 
 	public QichexinxiView(QichexinxiEntity qichexinxiEntity){
 	try {
			BeanUtils.copyProperties(this, qichexinxiEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}
}
