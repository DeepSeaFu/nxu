package com.entity.view;

import com.entity.QichezixunEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
 

/**
 * 汽车资讯
 */
@TableName("qichezixun")
public class QichezixunView  extends QichezixunEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public QichezixunView(){
	}
 
 	public QichezixunView(QichezixunEntity qichezixunEntity){
 	try {
			BeanUtils.copyProperties(this, qichezixunEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}
}
