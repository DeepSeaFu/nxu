package com.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;


import ch.qos.logback.core.pattern.SpacePadder;
import com.entity.ZuchedingdanEntity;
import com.service.ZuchedingdanService;
import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.mgt.DelegatingSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.QichexinxiEntity;
import com.entity.view.QichexinxiView;

import com.service.QichexinxiService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;
import java.io.IOException;
import com.service.StoreupService;
import com.entity.StoreupEntity;

/**
 * 汽车信息
 * 后端接口
 */
@RestController
@RequestMapping("/qichexinxi")
public class QichexinxiController {
	@Autowired
	private QichexinxiService qichexinxiService;

	@Autowired
	private StoreupService storeupService;

	@Autowired
	private ZuchedingdanService zuchedingdanService;


	/**
	 * 后端列表
	 */
	@RequestMapping("/page")
	public R page(@RequestParam Map<String, Object> params, QichexinxiEntity qichexinxi,
				  HttpServletRequest request) {
		String tableName = request.getSession().getAttribute("tableName").toString();
		if (tableName.equals("putongguanliyuan")) {
			qichexinxi.setGuanlizhanghao((String) request.getSession().getAttribute("username"));
		}
		EntityWrapper<QichexinxiEntity> ew = new EntityWrapper<QichexinxiEntity>();

		PageUtils page = qichexinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, qichexinxi), params), params));

		return R.ok().put("data", page);
	}

	/**
	 * 前端列表
	 */
	@IgnoreAuth
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params, QichexinxiEntity qichexinxi,
				  HttpServletRequest request) {
		EntityWrapper<QichexinxiEntity> ew = new EntityWrapper<QichexinxiEntity>();

		PageUtils page = qichexinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, qichexinxi), params), params));
		return R.ok().put("data", page);
	}

	/**
	 * 列表
	 */
	@RequestMapping("/lists")
	public R list(QichexinxiEntity qichexinxi) {
		EntityWrapper<QichexinxiEntity> ew = new EntityWrapper<QichexinxiEntity>();
		ew.allEq(MPUtil.allEQMapPre(qichexinxi, "qichexinxi"));
		return R.ok().put("data", qichexinxiService.selectListView(ew));
	}

	/**
	 * 查询
	 */
	@RequestMapping("/query")
	public R query(QichexinxiEntity qichexinxi) {
		EntityWrapper<QichexinxiEntity> ew = new EntityWrapper<QichexinxiEntity>();
		ew.allEq(MPUtil.allEQMapPre(qichexinxi, "qichexinxi"));
		QichexinxiView qichexinxiView = qichexinxiService.selectView(ew);
		return R.ok("查询汽车信息成功").put("data", qichexinxiView);
	}

	/**
	 * 后端详情
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		QichexinxiEntity qichexinxi = qichexinxiService.selectById(id);
		qichexinxi.setClicknum(qichexinxi.getClicknum() + 1);
		qichexinxi.setClicktime(new Date());
		qichexinxiService.updateById(qichexinxi);
		return R.ok().put("data", qichexinxi);
	}

	/**
	 * 前端详情
	 */
	@IgnoreAuth
	@RequestMapping("/detail/{id}")
	public R detail(@PathVariable("id") Long id) {
		QichexinxiEntity qichexinxi = qichexinxiService.selectById(id);
		qichexinxi.setClicknum(qichexinxi.getClicknum() + 1);
		qichexinxi.setClicktime(new Date());
		qichexinxiService.updateById(qichexinxi);
		return R.ok().put("data", qichexinxi);
	}


	/**
	 * 后端保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody QichexinxiEntity qichexinxi, HttpServletRequest request) {
		qichexinxi.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());
		//ValidatorUtils.validateEntity(qichexinxi);
		qichexinxiService.insert(qichexinxi);
		return R.ok();
	}

	/**
	 * 前端保存
	 */
	@RequestMapping("/add")
	public R add(@RequestBody QichexinxiEntity qichexinxi, HttpServletRequest request) {
		qichexinxi.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());
		//ValidatorUtils.validateEntity(qichexinxi);
		qichexinxiService.insert(qichexinxi);
		return R.ok();
	}


	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@Transactional
	public R update(@RequestBody QichexinxiEntity qichexinxi, HttpServletRequest request) {
		//ValidatorUtils.validateEntity(qichexinxi);
		qichexinxiService.updateById(qichexinxi);//全部更新
		return R.ok();
	}


	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] ids) {
		qichexinxiService.deleteBatchIds(Arrays.asList(ids));
		return R.ok();
	}

	/**
	 * 提醒接口
	 */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request,
						 @PathVariable("type") String type, @RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);

		if (type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if (map.get("remindstart") != null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH, remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if (map.get("remindend") != null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH, remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}

		Wrapper<QichexinxiEntity> wrapper = new EntityWrapper<QichexinxiEntity>();
		if (map.get("remindstart") != null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if (map.get("remindend") != null) {
			wrapper.le(columnName, map.get("remindend"));
		}

		String tableName = request.getSession().getAttribute("tableName").toString();
		if (tableName.equals("putongguanliyuan")) {
			wrapper.eq("guanlizhanghao", (String) request.getSession().getAttribute("username"));
		}

		int count = qichexinxiService.selectCount(wrapper);
		return R.ok().put("count", count);
	}

	/**
	 * 按用户点击次数排序推荐（按热门推荐）
	 */
	@IgnoreAuth
	@RequestMapping("/autoSort1")
	public R autoSort(@RequestParam Map<String, Object> params, QichexinxiEntity qichexinxi, HttpServletRequest request, String pre) {
		EntityWrapper<QichexinxiEntity> ew = new EntityWrapper<QichexinxiEntity>();
		Map<String, Object> newMap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = entry.getKey();
			if (pre.endsWith(".")) {
				newMap.put(pre + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(pre)) {
				newMap.put(newKey, entry.getValue());
			} else {
				newMap.put(pre + "." + newKey, entry.getValue());
			}
		}
		params.put("sort", "clicknum");
		params.put("order", "desc");
		PageUtils page = qichexinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, qichexinxi), params), params));
		return R.ok().put("data", page);

	}
//	@RequestMapping("/autoSort2")
//	public R autoSort(@RequestParam Map<String,Object> params){
//
//
//	}


	//基于车辆的推荐算法
	@RequestMapping("/autoSort4")
	public R autoSort(@RequestParam Map<String, Object> params, HttpServletRequest request) {
		List<QichexinxiEntity> qiche = qichexinxiService.selectList(new EntityWrapper<QichexinxiEntity>());
		List<QichexinxiEntity> thisCar = new ArrayList<>();
		String stringValue = request.getParameter("refid");
		long longValue = 0;
		if (stringValue != null) {
			longValue = Long.parseLong(stringValue);
		}
		for (int i = 0; i < qiche.size(); i++) {
			//System.out.println(qiche.get(i).getId());
			if (qiche.get(i).getId() == longValue) {
				QichexinxiEntity car = qiche.remove(i);
				//System.out.println(car.getId());
				thisCar.add(car);
				break;
			}
		}
		Double jiaGe = Double.valueOf(thisCar.get(0).getJiage().doubleValue());
		Map<QichexinxiEntity, Double> xlJuzhen = new HashMap<>(); //向量矩阵
		for (QichexinxiEntity qichexinxi : qiche) {
			List<Double> xl = new ArrayList<>(); //临时向量
			if (Objects.equals(qichexinxi.getCheliangxinghao(), thisCar.get(0).getCheliangxinghao())) //车辆型号
				xl.add(1.0);
			else
				xl.add(0.0);

			if (Objects.equals(qichexinxi.getQicheleibie(), thisCar.get(0).getQicheleibie())) //类别
				xl.add(1.0);
			else
				xl.add(0.0);
			if (Objects.equals(qichexinxi.getCheliangpinpai(), thisCar.get(0).getCheliangpinpai())) //品牌
				xl.add(1.0);
			else
				xl.add(0.0);

			if (Objects.equals(qichexinxi.getYanse(), thisCar.get(0).getYanse()))  //颜色
				xl.add(1.0);
			else
				xl.add(0.0);
			Double jg = Double.valueOf(qichexinxi.getJiage().doubleValue());
			if (Math.abs(jiaGe - jg) < 30)  //价格
				xl.add(1.0);
			else
				xl.add(0.0);
			//System.out.println(xl);
			boolean flag = false;
			for (Double aDouble : xl) {
				if (aDouble == 1) {
					flag = true;
					break;
				}
			}
			if (flag) {
				Double fenzi = xl.get(0) + xl.get(1) + xl.get(2) + xl.get(3) + xl.get(4);
				Double fenmu = (Math.sqrt(Math.pow(xl.get(0), 2) + Math.pow(xl.get(1), 2) + Math.pow(xl.get(2), 2) + Math.pow(xl.get(3), 2) + Math.pow(xl.get(4), 2)) * Math.sqrt(5));
				xlJuzhen.put(qichexinxi, fenzi / fenmu);
			} else {
				xlJuzhen.put(qichexinxi, 0.0);
			}
		}
		// 将 Map 转换成列表
		List<Map.Entry<QichexinxiEntity, Double>> list = new ArrayList<>(xlJuzhen.entrySet());
		// 使用 Collections.sort() 进行降序排序
		Collections.sort(list, new Comparator<Map.Entry<QichexinxiEntity, Double>>() {
			@Override
			public int compare(Map.Entry<QichexinxiEntity, Double> o1, Map.Entry<QichexinxiEntity, Double> o2) {
				return o2.getValue().compareTo(o1.getValue()); // 降序排列
			}
		});
		// 创建一个新的 List 用于存储排序结果的键
		List<QichexinxiEntity> xlJuzhenList = new ArrayList<QichexinxiEntity>(); //推荐列表
		for (Map.Entry<QichexinxiEntity, Double> entry : list) {
			xlJuzhenList.add(entry.getKey());
		}
		List<QichexinxiEntity> tuijianCar = new ArrayList<>();
		String shuliang = request.getParameter("limit");
		int intValue = Integer.parseInt(shuliang);
		for (int i = 0; i < intValue; i++) {
			tuijianCar.add(xlJuzhenList.get(i));
		}
		PageUtils page = qichexinxiService.queryPage(params);
		page.setList(tuijianCar);
		return R.ok().put("data", page);
	}

	/**
	 * 基于物品的协同过滤算法（按收藏推荐）
	 */
	@RequestMapping("/autoSort3")
	public R autoSort(@RequestParam Map<String, Object> params, QichexinxiEntity xinxi, HttpServletRequest request) {
		String userId = request.getSession().getAttribute("userId").toString(); //获取用户id
		//System.out.println(userId);
		List<StoreupEntity> storeups = storeupService.selectList(new EntityWrapper<StoreupEntity>().eq("type", 1).eq("userid", userId).orderBy("addtime", false));

		//从收藏表中提取收藏的车辆名字存到qichename中
		List<String> qichename = new ArrayList<>();
		for (int i = 0; i < storeups.size(); i++) {
			qichename.add(storeups.get(i).getName());
			//System.out.println(storeups.get(i).getName());
		}
		String userName = request.getSession().getAttribute("username").toString(); //获取用户name
		//System.out.println(userName);
		List<ZuchedingdanEntity> dingdan = zuchedingdanService.selectList(new EntityWrapper<ZuchedingdanEntity>()); //获取订单列表
//		for (int i = 0; i < dingdan.size(); i++) {
//			System.out.println(dingdan.get(i).getZhanghao()+" "+dingdan.get(i).getChepaihao());
//		}
		//System.out.println(Objects.equals(userName,dingdan.get(0).getZhanghao()));
		for (int i = 0; i < dingdan.size(); i++) {
			if(Objects.equals(userName,dingdan.get(i).getZhanghao()))
			{
				boolean flag = false;
				for (int i1 = 0; i1 < qichename.size(); i1++) {
					if(dingdan.get(i).getChepaihao() == qichename.get(i1)){
						flag = true;
						break;
					}
				}
				//System.out.println(flag);
				if(!flag)
					qichename.add(dingdan.get(i).getChepaihao());
			}
		}
		//System.out.println(qichename);
		if(qichename.isEmpty()){  //判断用户是否为有收藏车辆或有过租赁车辆
			List<QichexinxiEntity> tuijianList = qichexinxiService.selectList(new EntityWrapper<QichexinxiEntity>());
			Collections.sort(tuijianList, new Comparator<QichexinxiEntity>() {
				@Override
				public int compare(QichexinxiEntity o1, QichexinxiEntity o2) {
					return Integer.compare(o2.getClicknum(), o1.getClicknum());
				}
			});
//			for (int i = 0; i < tuijianList.size(); i++) {
//				System.out.println(tuijianList.get(i).getChepaihao()+" "+tuijianList.get(i).getClicknum());
//			}
			List<QichexinxiEntity> tuijianCar = new ArrayList<>();
			String shuliang = request.getParameter("limit");
			int intValue = Integer.parseInt(shuliang);
			for (int i = 0; i < intValue; i++) {
				tuijianCar.add(tuijianList.get(i));
			}
			PageUtils page = qichexinxiService.queryPage(params);
			page.setList(tuijianCar);
			return R.ok().put("data", page);
		}


		else {
			//查询数据库中所有的汽车信息
			List<QichexinxiEntity> qiche = qichexinxiService.selectList(new EntityWrapper<QichexinxiEntity>());

			//保存收藏的汽车信息在shoucangqichexinxi中
			List<QichexinxiEntity> shoucangqichexinxi = new ArrayList<>();
			for (int i = 0; i < qichename.size(); i++) {
				for (int j = 0; j < qiche.size(); j++) {
					if (Objects.equals(qiche.get(j).getChepaihao(), qichename.get((i)))) {
						shoucangqichexinxi.add(qiche.remove(j));
					}
				}
			}
			//测试shoucangqichexinxi
//			for (int i = 0; i < shoucangqichexinxi.size(); i++) {
//				System.out.println(shoucangqichexinxi.get(i).getChepaihao());
//			}

			//计算余弦相似度矩阵
			List<Map<QichexinxiEntity, Double>> xljuzhenList = new ArrayList<>();
			for (int i = 0; i < shoucangqichexinxi.size(); i++) {
				Double jiaGe = Double.valueOf(shoucangqichexinxi.get(i).getJiage().doubleValue());
				Map<QichexinxiEntity, Double> xlJuzhen = new HashMap<>(); //向量矩阵
				for (QichexinxiEntity qichexinxi : qiche) {
					List<Double> xl = new ArrayList<>(); //临时向量
					if (Objects.equals(qichexinxi.getCheliangxinghao(), shoucangqichexinxi.get(i).getCheliangxinghao())) //车辆型号
						xl.add(1.0);
					else
						xl.add(0.0);

					if (Objects.equals(qichexinxi.getQicheleibie(), shoucangqichexinxi.get(i).getQicheleibie())) //类别
						xl.add(1.0);
					else
						xl.add(0.0);
					if (Objects.equals(qichexinxi.getCheliangpinpai(), shoucangqichexinxi.get(i).getCheliangpinpai())) //品牌
						xl.add(1.0);
					else
						xl.add(0.0);

					if (Objects.equals(qichexinxi.getYanse(), shoucangqichexinxi.get(i).getYanse()))  //颜色
						xl.add(1.0);
					else
						xl.add(0.0);
					Double jg = Double.valueOf(qichexinxi.getJiage().doubleValue());
					if (Math.abs(jiaGe - jg) < 30)  //价格
						xl.add(1.0);
					else
						xl.add(0.0);
					//System.out.println(xl);
					boolean flag = false;
					for (Double aDouble : xl) {
						if (aDouble == 1) {
							flag = true;
							break;
						}
					}
					if (flag) {
						Double fenzi = xl.get(0) + xl.get(1) + xl.get(2) + xl.get(3) + xl.get(4);
						Double fenmu = (Math.sqrt(Math.pow(xl.get(0), 2) + Math.pow(xl.get(1), 2) + Math.pow(xl.get(2), 2) + Math.pow(xl.get(3), 2) + Math.pow(xl.get(4), 2)) * Math.sqrt(5));
						xlJuzhen.put(qichexinxi, fenzi / fenmu);
					} else {
						xlJuzhen.put(qichexinxi, 0.0);
					}
				}

				xljuzhenList.add(xlJuzhen);
			}

			Map<QichexinxiEntity, Double> pianhaoMap = new HashMap<>();
			for (Map.Entry<QichexinxiEntity, Double> entry : xljuzhenList.get(0).entrySet()) {
				Double sum = entry.getValue();
				for (int i = 1; i < xljuzhenList.size(); i++) {
					sum = sum + xljuzhenList.get(i).getOrDefault(entry.getKey(), 0.0);
				}
				Double average = sum / xljuzhenList.size();
				//System.out.println(average);
				pianhaoMap.put(entry.getKey(), average);
			}
//		//测试
//		for (Map.Entry<QichexinxiEntity, Double> entry : pianhaoMap.entrySet()) {
//			System.out.println(entry.getKey().getCheliangxinghao());
//			System.out.println(entry.getValue());
//		}

			// 将 Map 转换成列表
			List<Map.Entry<QichexinxiEntity, Double>> list = new ArrayList<>(pianhaoMap.entrySet());
			// 使用 Collections.sort() 进行降序排序
			Collections.sort(list, new Comparator<Map.Entry<QichexinxiEntity, Double>>() {
				@Override
				public int compare(Map.Entry<QichexinxiEntity, Double> o1, Map.Entry<QichexinxiEntity, Double> o2) {
					return o2.getValue().compareTo(o1.getValue()); // 降序排列
				}
			});
			// 创建一个新的 List 用于存储排序结果的键
			List<QichexinxiEntity> tuijianList = new ArrayList<QichexinxiEntity>(); //推荐列表
			for (Map.Entry<QichexinxiEntity, Double> entry : list) {
				tuijianList.add(entry.getKey());
			}


			List<QichexinxiEntity> tuijianCar = new ArrayList<>();
			String shuliang = request.getParameter("limit");
			int intValue = Integer.parseInt(shuliang);
			for (int i = 0; i < intValue; i++) {
				tuijianCar.add(tuijianList.get(i));
			}
			PageUtils page = qichexinxiService.queryPage(params);
			page.setList(tuijianCar);
			return R.ok().put("data", page);
		}

	}
}

