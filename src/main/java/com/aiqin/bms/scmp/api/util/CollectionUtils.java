package com.aiqin.bms.scmp.api.util;


import java.util.*;

/**
 * @describe:
 * @author: jiahong.xing
 * @version: v1.0
 * @date 2018/01/08下午 2:04
 */
public class CollectionUtils {

	/**
	 * 判断空集合等，非空返回true，空返回false
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isNotEmptyCollection(Collection<?> collection) {
		if (null == collection || collection.size() <= 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判断空集合等，空返回true，非空返回false
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isEmptyCollection(Collection<?> collection) {
		return !isNotEmptyCollection(collection);
	}

	@SuppressWarnings("rawtypes")
	public static boolean isNotEmptyMap(Map map) {
		if (null != map && map.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	public static boolean isEmptyMap(Map map) {
		return !isNotEmptyMap(map);
	}

	/**
	 * 随机返回几条数据，不重复
	 * 
	 * @param list
	 *            数据源
	 * @param row
	 *            随机返回几条
	 * @return
	 */
	public static List<String> rand(List<String> list, int row) {
		// 如果不够随机
		if (!isNotEmptyCollection(list) || list.size() < row) {
			return list;
		}

		Set<String> nlist = new HashSet<String>();
		Random random = new Random();
		// 仅仅取几条
		while (nlist.size() < row) {
			nlist.add(list.get(random.nextInt(list.size())));
		}
		return new ArrayList<>(nlist);
	}


}
