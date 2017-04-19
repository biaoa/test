package com.linle.IM;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.linle.common.base.BaseController;

@Controller
@RequestMapping("/IM")
public class IMController extends BaseController {
	
	@RequestMapping("/toIM")
	public String toIM(){
		return "IM/IM";
	}
}
