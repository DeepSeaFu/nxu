package com.entity.view;

import com.entity.QuxiaodingdanEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
 

/**
 * 取消订单
 */
@TableName("quxiaodingdan")
public class QuxiaodingdanView  extends QuxiaodingdanEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public QuxiaodingdanView(){
	}
 
 	public QuxiaodingdanView(QuxiaodingdanEntity quxiaodingdanEntity){
 	try {
			BeanUtils.copyProperties(this, quxiaodingdanEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}
}
