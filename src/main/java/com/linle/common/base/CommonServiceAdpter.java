package com.linle.common.base;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class CommonServiceAdpter<T> extends BaseServiceAdpter<T> {

    
    protected Subject getSubject() {
        return SecurityUtils.getSubject();
    }
    
    protected final Logger _logger = LoggerFactory.getLogger(getClass());

	public ObjectMapper m = new ObjectMapper();
}

