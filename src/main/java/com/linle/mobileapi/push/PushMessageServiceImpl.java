package com.linle.mobileapi.push;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.linle.common.util.SysConfig;
import com.linle.mobileapi.push.been.PushBean;
import com.linle.mobileapi.push.been.PushFrom;
import com.linle.mobileapi.push.been.PushPlanMsg;
import com.linle.mobileapi.push.jpush.api.JPushClient;
import com.linle.mobileapi.push.jpush.api.common.resp.APIConnectionException;
import com.linle.mobileapi.push.jpush.api.common.resp.APIRequestException;
import com.linle.mobileapi.push.jpush.api.push.PushResult;
import com.linle.mobileapi.push.jpush.api.push.model.Messagejpush;
import com.linle.mobileapi.push.jpush.api.push.model.Options;
import com.linle.mobileapi.push.jpush.api.push.model.Platform;
import com.linle.mobileapi.push.jpush.api.push.model.PushPayload;
import com.linle.mobileapi.push.jpush.api.push.model.audience.Audience;
import com.linle.mobileapi.push.jpush.api.push.model.audience.AudienceTarget;
import com.linle.mobileapi.push.jpush.api.push.model.notification.AndroidNotification;
import com.linle.mobileapi.push.jpush.api.push.model.notification.IosNotification;
import com.linle.mobileapi.push.jpush.api.push.model.notification.Notification;


/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年10月9日
 **/
@Service("pushMessageService")
public class PushMessageServiceImpl implements PushMessageService {
    private final static Logger _logger = LoggerFactory.getLogger(PushMessageServiceImpl.class);


    private final static String APP_KEY = SysConfig.JPUSH_KEY;
    private final static String SECRET = SysConfig.JPUSH_SECRET;
    
    private final static String SHOP_APP_KEY = SysConfig.SHOP_JPUSH_KEY;
    private final static String SHOP_SECRET = SysConfig.SHOP_JPUSH_SECRET;
    
    private final static String COMMUNITY_APP_KEY = SysConfig.COMMUNITY_JPUSH_KEY;
    private final static String COMMUNITY_SECRET = SysConfig.COMMUNITY_JPUSH_SECRET;
    
    public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
    	// 将json字符串转成json对象  
//    	String jsonStr="extras: {content: hello my is jack!}";
//    	JSONObject  json = JSONObject.fromObject(jsonStr);  
//        System.out.println(json);

	    JPushClient jpushClient = new JPushClient(SECRET, APP_KEY, 3);
//	    JPushClient jpushClient = new JPushClient("1976cc601371755228181bbb", "56e2e6ff8a0f026ad836f4af", 3);
//	    PushPayload payload = buildPushObject_android_and_ios_alias_type_refId(msg, alias,"topic",refId);
	    
	    //对象
//	    PushBean pushBean=new PushBean();
//        pushBean.setType(1);
//        pushBean.setContent("来自服务端的问候2222");
//        pushBean.setRefId(28);
//	    PushPayload payload =buildPushObject_android_and_ios_alias(pushBean, msg, alias);
	    
//        ObjectMapper objM=new ObjectMapper();
        /*
	    String alias="4414";
//	    String alias="4320";
	    String msg="该考勤了";
     	Map<String, String> extras = new HashMap<>();

		extras.put("type","attendance");
		extras.put("refId", "");
//		Map<String ,Object> map=new HashMap<String, Object>();
//		map.put("orderType", "space");
//		map.put("orderStatus", 4);
//		extras.put("jsonStr", objM.writeValueAsString(map));
		PushPayload payload = buildPushObject_android_and_ios_extras(extras, msg, alias);
      
        */
        /**
         * 开发环境测试需要将设置为false   setApnsProduction(false)
         * 生产环境设置为true，不然收不到推送
         */
        //多个目标
        String msg="中秋节通知33";
        Map<String, String> extras = new HashMap<>();
        extras.put("type","communityNotice");
		extras.put("refId", "15");
		List<String> toUserIds=new ArrayList<>();
		toUserIds.add("4307");
	    String[] array = new String[toUserIds.size()];
	    array =  toUserIds.toArray(array);
	    PushPayload payload =buildPushObject_android_and_ios_extras_alias(extras, msg, array);
	    
	    sendPush(jpushClient, payload);
	}
    
    
    /**
     * chenkai
     * @param extras
     * @param msg
     * @param alias
     * @return
     */
    public boolean pushAliasMessageByMaps(PushBean pushBean, String msg, String alias,PushFrom from) {
    	JPushClient jpushClient = null;
    	Map<String, String> extras = new HashMap<>();
    	if(pushBean.getType()!=null){
    		extras.put("type", pushBean.getType().getName());
    	}
    	if(pushBean.getRefId()!=null){
    		extras.put("refId", pushBean.getRefId()+"");
    	}
    	if(pushBean.getJsonStr()!=null){
    		extras.put("jsonStr", pushBean.getJsonStr()+"");
    	}
    	_logger.debug("即将push的消息是：" + msg);
    	if (from.equals(PushFrom.LINLE_USER)) {
    		jpushClient = new JPushClient(SECRET, APP_KEY, 3);
		}else if(from.equals(PushFrom.LINLE_SHOP)){
			jpushClient = new JPushClient(SHOP_SECRET, SHOP_APP_KEY, 3);
		}else if(from.equals(PushFrom.LINLE_COMMUNITY)){
			jpushClient = new JPushClient(COMMUNITY_SECRET, COMMUNITY_APP_KEY,3);
		}
        PushPayload payload = buildPushObject_android_and_ios_extras(extras, msg, alias);
        sendPush(jpushClient, payload);
        return false;
    }
    
    //多个目标
    public boolean pushAliasMessage(PushBean pushBean, String msg,PushFrom from, String... alias) {
    	JPushClient jpushClient = null;
    	Map<String, String> extras = new HashMap<>();
    	if(pushBean.getType()!=null){
    		extras.put("type", pushBean.getType().getName());
    	}
    	if(pushBean.getRefId()!=null){
    		extras.put("refId", pushBean.getRefId()+"");
    	}
    	if(pushBean.getJsonStr()!=null){
    		extras.put("jsonStr", pushBean.getJsonStr()+"");
    	}
    	_logger.debug("即将push的消息是：" + msg);
    	if (from.equals(PushFrom.LINLE_USER)) {
    		jpushClient = new JPushClient(SECRET, APP_KEY, 3);
		}else if(from.equals(PushFrom.LINLE_SHOP)){
			jpushClient = new JPushClient(SHOP_SECRET, SHOP_APP_KEY, 3);
		}else if(from.equals(PushFrom.LINLE_COMMUNITY)){
			jpushClient = new JPushClient(COMMUNITY_SECRET, COMMUNITY_APP_KEY,3);
		}
        PushPayload payload = buildPushObject_android_and_ios_extras_alias(extras, msg, alias);
        sendPush(jpushClient, payload);
        return false;
    }
    
    
    /**
     * @param msg
     * @param tagName 标签  
     * @param alias 别名   userid
     * @return
     */
    private boolean pushTagAndAliasMessage(String msg, String tagName, String alias) {
        _logger.debug("即将push的消息是：" + msg);
        JPushClient jpushClient = new JPushClient(SECRET, APP_KEY, 3);
        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_ios_audienceMore_messageWithExtras(tagName, alias);
        sendPush(jpushClient, payload);
        System.out.println("sendPush--payload:"+payload.toString());  
        
        return false;
    }

    //快捷地构建推送对象：所有平台，所有设备，内容为 ALERT 的通知
    private static PushPayload buildPushObject_all_all_alert(String msg) {
        return PushPayload.alertAll(msg);
    }

    private static PushPayload buildPushObject_android_and_ios_tag(String msg, String tagName) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())//设置接受的平台  
                .setAudience(Audience.tag(tagName))//Audience设置为all，说明采用广播方式推送，所有用户都可以接收到  
                .setNotification(Notification.newBuilder()
                        .setAlert(msg)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle("您有1条未读消息").build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)//角标数字
                                .addExtra("content", msg).build())
                        .build())

                .build();
    }


    
   /**
    *  	构建推送对象：平台是 android和ios，推送目标是 "tag1", "tag_all" 的交集，
    *	推送内容同时包括通知与消息 - 通知信息是 ALERT，角标数字为 5，通知声音为 "happy"，并且附加字段 from = "JPush"；
  	*	消息内容是 MSG_CONTENT。通知是 APNs 推送通道的，消息是 JPush 应用内消息通道的。
  	*	APNs 的推送环境是“生产”（如果不显式设置的话，Library 会默认指定为开发）
    * @param pushBean
    * @param msg
    * @param alias
    * @param count
    * @return
    */
    private static PushPayload buildPushObject_android_and_ios_alias(PushBean pushBean, String msg, String alias, int count) {
        String alert = "您有1条未读消息";
        if (pushBean.getType().getCode() == 1) {
            PushPlanMsg pushMsg = (PushPlanMsg) (pushBean.getContent());
            alert = pushMsg.getTitle();
        }
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))//推送目标
                .setNotification(Notification.newBuilder()
                        .setAlert(alert)						//通知信息是 ALERT
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(alert).addExtra("content", msg)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(count)				//角标数字为 5
                                .addExtra("content", msg).build())//msg
                        .build())
                .build();
        
    }

    //.addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))  
    //.addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))  
    private static PushPayload buildPushObject_ios_audienceMore_messageWithExtras(String tag, String alias) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.tag(tag))
                        .addAudienceTarget(AudienceTarget.alias(alias))
                        .build())
                .setMessage(Messagejpush.newBuilder()
                        .setMsgContent("我是内容")
                        .addExtra("from", "JPush")
//                        .addExtra("url", "www.baidu.com")
                        .build())
                .build();
    }

    //sendPush返回结果
    private static void sendPush(JPushClient jpushClient, PushPayload payload) {
        try {
            PushResult result = jpushClient.sendPush(payload);
            _logger.debug("返回结果Got result - " + result);

        } catch (APIConnectionException e) {
            _logger.debug("Connection error. Should retry later. ", e);

        } catch (APIRequestException e) {
            _logger.error("Error response from JPush server. Should review and fix it. ", e);
            _logger.info("HTTP Status: " + e.getStatus());
            _logger.info("Error Code: " + e.getErrorCode());
            _logger.info("Error Message: " + e.getErrorMessage());
            _logger.info("Msg ID: " + e.getMsgId());
        }
    }
    private boolean pushTagMessage(String msg, String tagName) {
        _logger.debug("即将push的消息是：" + msg);
        JPushClient jpushClient = new JPushClient(SECRET, APP_KEY, 3);
        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_android_and_ios_tag(msg, tagName);
        sendPush(jpushClient, payload);

        return false;
    }
    private boolean pushAliasMessage(PushBean pushBean, String msg, String alias, int count) {
        _logger.debug("即将push的消息是：" + msg);
        JPushClient jpushClient = new JPushClient(SECRET, APP_KEY, 3);
        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_android_and_ios_alias(pushBean, msg, alias, count);
        sendPush(jpushClient, payload);

        return false;
    }

    @Override
    public boolean pushTagMessage(PushBean pushBean, String tagName) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String msg = mapper.writeValueAsString(pushBean);
            return pushTagMessage(msg, tagName);
        } catch (JsonGenerationException e) {
            _logger.error("出错了", e);
            return false;
        } catch (JsonMappingException e) {
            _logger.error("出错了", e);
            return false;
        } catch (IOException e) {
            _logger.error("出错了", e);
            return false;
        }
    }
    
    public boolean pushAliasMessage(PushBean pushBean, String alias) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String msg = mapper.writeValueAsString(pushBean);
            return pushAliasMessage(pushBean, msg, alias);
        } catch (JsonGenerationException | JsonMappingException e) {
            _logger.error("出错了", e);
            return false;
        } catch (IOException e) {
            _logger.error("出错了", e);
            return false;
        }
    }
    
    private static boolean pushAliasMessage(PushBean pushBean, String msg, String alias) {
        _logger.debug("即将push的消息是：" + msg);
        JPushClient jpushClient = new JPushClient(SECRET, APP_KEY, 3);
        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_android_and_ios_alias(pushBean, msg, alias);
        sendPush(jpushClient, payload);

        return false;
    }
    private static PushPayload buildPushObject_android_and_ios_alias(PushBean pushBean, String msg, String alias) {
        String alert = "您有1条未读消息";
        if (pushBean.getType().getCode() == 1) {
            PushPlanMsg pushMsg = (PushPlanMsg) (pushBean.getContent());
            alert = pushMsg.getTitle();
        }
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))//推送目标
                .setNotification(Notification.newBuilder()
                        .setAlert(alert)						//通知信息是 ALERT
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(alert).addExtra("content", msg)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .addExtra("content", msg).build())//msg
                        .build())
                .build();
        
    }
    /**
     * ck
     * @param extras 额外参数
     * @param msg  消息
     * @param alias 别名
     * 	addExtra和addExtras不能一起用
     * ios默认角标badge+1
     * apns_production 指定 APNS 通知发送环境：0: 开发环境，1：生产环境。
     * 如果不携带此参数则推送环境与 JPush Web 上的应用 APNS 环境设置相同。
     * @return
     */
    private static PushPayload buildPushObject_android_and_ios_extras( Map<String, String> extras, String msg, String alias) {
    	String alert = "您有1条未读消息";
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))//推送目标
                .setOptions(Options.newBuilder().setApnsProduction(true).build())
                .setNotification(Notification.newBuilder()
                        .setAlert(msg)						//通知信息是 ALERT
//                        .addPlatformNotification(AndroidNotification.newBuilder()
//                              .setAlert(alert)
//                                .addExtra("refId", refId)
//                                .addExtra("type", type)
//                                .addExtra("fromUserId", 4310)
//                                .build())
//                        .addPlatformNotification(IosNotification.newBuilder()
//                        		.setSound("default")
//                        		.addExtra("refId", refId)
//                                .addExtra("type",type)
//                                .addExtra("fromUserId", 4310)//回复者
//                        		.build())
                        
                        .addPlatformNotification(IosNotification.newBuilder()
                                .addExtras(extras)//ios多个附加参数
                                .setSound("default")
                                .build())
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtras(extras)//Android多个附加参数
                                .build())
                        .build())
                .build();
    }
   
    //多个目标
    private static PushPayload buildPushObject_android_and_ios_extras_alias( Map<String, String> extras, String msg, String... alias) {
    	String alert = "您有1条未读消息";
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))//推送目标
                .setOptions(Options.newBuilder().setApnsProduction(true).build())//生产环境:true
                .setNotification(Notification.newBuilder()
                        .setAlert(msg)						//通知信息是 ALERT
                        .addPlatformNotification(IosNotification.newBuilder()
                                .addExtras(extras)//ios多个附加参数
                                .setSound("default")
                                .build())
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtras(extras)//Android多个附加参数
                                .build())
                        .build())
                .build();
    }
    //end
    
    @Override
    public boolean pushAliasMessage(PushBean pushBean, String alias, int count) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String msg = mapper.writeValueAsString(pushBean);
            return pushAliasMessage(pushBean, msg, alias, count);
        } catch (JsonGenerationException | JsonMappingException e) {
            _logger.error("出错了", e);
            return false;
        } catch (IOException e) {
            _logger.error("出错了", e);
            return false;
        }
    }

    @Override
    public boolean pushTagAndAliasMessage(PushBean pushBean, String tagName, String alias) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String msg = mapper.writeValueAsString(pushBean);
            return pushTagAndAliasMessage(msg, tagName, alias);
        } catch (JsonGenerationException | JsonMappingException e) {
            _logger.error("出错了", e);
            return false;
        } catch (IOException e) {
            _logger.error("出错了", e);
            return false;
        }
    }

    @Override
    public boolean pushTagMessageWithExtras(PushBean pushBean, String tagName, ImmutablePair<String, Long> pair) {
//        ObjectMapper mapper = new ObjectMapper();
        pushBean.setContent(pair.getRight());
//            String msg = mapper.writeValueAsString(pushBean);
        String msg = JSON.toJSONString(pushBean).replaceAll("\"", "");

        _logger.debug("即将push的消息是：" + msg);
        JPushClient jpushClient = new JPushClient(SECRET, APP_KEY, 3);
        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_android_and_ios_tag_with_extras(msg, tagName, pair);
        sendPush(jpushClient, payload);

        return false;
    }

    private PushPayload buildPushObject_android_and_ios_tag_with_extras(String msg, String tagName, ImmutablePair<String, Long> pair) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder().addAudienceTarget(AudienceTarget.tag(tagName)).build())
                .setNotification(Notification.newBuilder()
                        .setAlert("点击查看详情")
                        .addPlatformNotification(AndroidNotification.newBuilder().setTitle("您有1条未读消息").addExtra("content", msg).build())
                        .addPlatformNotification(IosNotification.newBuilder().incrBadge(1).addExtra("content", msg).build())
                        .build())
                .setMessage(Messagejpush.newBuilder().setMsgContent("我是内容").addExtra("content", msg).build()).build();
    }


}
