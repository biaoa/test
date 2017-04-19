package com.linle.mobileapi.push.been;

import java.io.Serializable;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年10月9日
 **/
public class PushPlanMsg implements Serializable {
    /**
     * plan推送been
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
