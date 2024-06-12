package com.entity.view;

import com.entity.PutongguanliyuanEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
 

/**
 * 普通管理员
 */
@TableName("putongguanliyuan")
public class PutongguanliyuanView  extends PutongguanliyuanEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public PutongguanliyuanView(){
	}
 
 	public PutongguanliyuanView(PutongguanliyuanEntity putongguanliyuanEntity){
 	try {
			BeanUtils.copyProperties(this, putongguanliyuanEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}
}
