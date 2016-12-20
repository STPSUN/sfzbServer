package com.idg.bfzb.server.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HelloController {


	@RequestMapping(value = "hello2",method = RequestMethod.GET)
	public String printWelcome2(ModelMap model) {
		return"index";
	}
}

// http://blog.sina.com.cn/s/blog_66a597880101ioog.html