package com.linle.mobileapi.push.jpush.api.device;

import java.util.ArrayList;
import java.util.List;

import com.linle.mobileapi.push.jpush.api.common.resp.BaseResult;

import com.google.gson.annotations.Expose;

public class AliasDeviceListResult extends BaseResult {
   
	@Expose public List<String> registration_ids = new ArrayList<String>();

}

