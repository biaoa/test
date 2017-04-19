package com.linle.common.util;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @描述 参数工具类
 * @作者 Guitarist
 * @版本 linle
 * @创建时间：2015年8月18日 上午10:32:43 青春气贯长虹 勇锐盖过怯弱 进取压倒苟安 年岁有加,并非垂老,理想丢弃,方堕暮年
 */
public class ParamsUtil {
	private static Logger logger = LoggerFactory.getLogger(ParamsUtil.class);

	public static void checkParamIsNull(Object object) {

		logger.debug("Param:{}", object);

		if (object == null) {
			throw new RuntimeException("参数不能为空!");
		}
	}

	public static void checkMapParamHasNull(Map<Object, Object> paramMap) {

		for (Entry<Object, Object> entry : paramMap.entrySet()) {
			checkParamIsNull(entry.getValue());
		}
	}

}
