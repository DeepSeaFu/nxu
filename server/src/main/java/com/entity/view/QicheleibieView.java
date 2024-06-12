package com.entity.view;

import com.entity.QicheleibieEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
 

/**
 * 汽车类别
 */
@TableName("qicheleibie")
public class QicheleibieView  extends QicheleibieEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public QicheleibieView(){
	}
 
 	public QicheleibieView(QicheleibieEntity qicheleibieEntity){
 	try {
			BeanUtils.copyProperties(this, qicheleibieEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}
}
