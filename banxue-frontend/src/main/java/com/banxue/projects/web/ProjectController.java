package com.banxue.projects.web;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.banxue.projects.service.IProjectService;
import com.banxue.utils.log.FileLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author feng
 * @since 2018-09-25
 */
@Controller
@RequestMapping("/project")
public class ProjectController {
	
	@Autowired
	private IProjectService projectService;
	@Value("${server.port}")
	private String port;
	
	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		FileLog.debugLog("你是一个二货");
		return "22";
	}

}
