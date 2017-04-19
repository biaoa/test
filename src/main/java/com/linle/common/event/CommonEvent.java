package com.linle.common.event;

import org.springframework.context.ApplicationEvent;


public class CommonEvent extends ApplicationEvent {

   
	private static final long serialVersionUID = 5791003084863034409L;

	public CommonEvent(CommonEventModel baseEventModel) {
        super(baseEventModel);
    }

    @Override
    public CommonEventModel getSource() {
        return (CommonEventModel) super.getSource();
    }
}
