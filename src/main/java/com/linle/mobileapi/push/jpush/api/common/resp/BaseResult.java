package com.linle.mobileapi.push.jpush.api.common.resp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class BaseResult implements IRateLimiting {
	public static final Logger _logger = LoggerFactory.getLogger(IRateLimiting.class);
    public static final int ERROR_CODE_NONE = -1;
    public static final int ERROR_CODE_OK = 0;
    	 static final String ERROR_MESSAGE_NONE = "None error message.";
    
    protected static final int RESPONSE_OK = 200;
    protected static Gson _gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    
    private ResponseWrapper responseWrapper;
    
    public void setResponseWrapper(ResponseWrapper responseWrapper) {
        this.responseWrapper = responseWrapper;
    }
    
    public String getOriginalContent() {
        if (null != responseWrapper) {
            return responseWrapper.responseContent;
        }
        return null;
    }

    public int getResponseCode() {
        if(null != responseWrapper) {
            return responseWrapper.responseCode;
        }
        return -1;
    }
    
    public boolean isResultOK() {
        if(null != responseWrapper) {
            return ( responseWrapper.responseCode / 200 ) == 1;
        }
        return false;
    }
    
    public static <T extends BaseResult> T fromResponse(
            ResponseWrapper responseWrapper, Class<T> clazz) {
        T result = null;
        
        if (responseWrapper.isServerResponse()) {
            result = _gson.fromJson(responseWrapper.responseContent, clazz);
        } else {
            try {
                result = clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace(); _logger.error("出错了", e);
            } catch (IllegalAccessException e) {
                e.printStackTrace(); _logger.error("出错了", e);
            }
        }
        
        result.setResponseWrapper(responseWrapper);
        
        return result;
    }

    
    @Override
    public int getRateLimitQuota() {
        if (null != responseWrapper) {
            return responseWrapper.rateLimitQuota;
        }
        return 0;
    }
    
    @Override
    public int getRateLimitRemaining() {
        if (null != responseWrapper) {
            return responseWrapper.rateLimitRemaining;
        }
        return 0;
    }
    
    @Override
    public int getRateLimitReset() {
        if (null != responseWrapper) {
            return responseWrapper.rateLimitReset;
        }
        return 0;
    }
    
    @Override
    public String toString() {
        return _gson.toJson(this);
    }

    
}
