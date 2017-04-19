package com.linle.mobileapi.push.jpush.api.device;

import java.util.ArrayList;
import java.util.List;

import com.linle.mobileapi.push.jpush.api.common.resp.BaseResult;

import com.google.gson.annotations.Expose;

public class TagListResult extends BaseResult {
    
    @Expose public List<String> tags = new ArrayList<String>();
    
}

