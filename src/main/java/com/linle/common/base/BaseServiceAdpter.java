package com.linle.common.base;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import component.BaseMapper;

@Transactional
@Component
public abstract class BaseServiceAdpter<T> implements BaseService<T> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    protected ApplicationContext applicationContext;
    @Autowired
    protected BaseMapper<T> baseMapper;

    public int deleteByPrimaryKey(Long id) {
        return baseMapper.deleteByPrimaryKey(id);
    }

    public int insert(T record) {
        return baseMapper.insert(record);
    }

    public int insertSelective(T record) {
        return baseMapper.insertSelective(record);
    }

    public T selectByPrimaryKey(Long id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(T record) {
        return baseMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(T record) {
        return baseMapper.updateByPrimaryKey(record);
    }

    protected Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    protected Subject getSubject() {
        return SecurityUtils.getSubject();
    }
}
