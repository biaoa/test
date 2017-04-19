package com.linle.mobileapi.push.jpush.api.device;

import java.util.List;

import com.linle.mobileapi.push.jpush.api.common.resp.BaseResult;

import com.google.gson.annotations.Expose;

public class TagAliasResult extends BaseResult {

    @Expose public List<String> tags;
    @Expose public String alias;
        
}

