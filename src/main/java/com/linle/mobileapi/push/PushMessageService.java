package com.linle.mobileapi.push;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.linle.mobileapi.push.been.PushBean;
import com.linle.mobileapi.push.been.PushFrom;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年10月9日
 **/
public interface PushMessageService {

    public boolean pushTagMessage(PushBean pushBean, String tagName);

    public boolean pushAliasMessage(PushBean pushBean, String alias, int mssageCount);
    public boolean pushTagAndAliasMessage(PushBean pushBean, String tagName, String alias);

    public boolean pushTagMessageWithExtras(PushBean pushBean, String all_plan_user, ImmutablePair<String, Long> pair);

	boolean pushAliasMessage(PushBean pushBean, String alias);
	
	boolean pushAliasMessageByMaps(PushBean pushBean, String msg, String alias,PushFrom from);
	//推送多个目标
	boolean  pushAliasMessage(PushBean pushBean, String msg,PushFrom from, String... alias) ;
}
