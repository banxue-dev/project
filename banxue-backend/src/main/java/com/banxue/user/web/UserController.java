package com.banxue.user.web;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.banxue.shiro.ShiroUtils;
import com.banxue.user.entity.User;
import com.banxue.utils.R;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author feng
 * @since 2018-09-28
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
    public R login(String username, String password,String vcode,Boolean rememberMe){
        System.out.println(username);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password,rememberMe);
        SecurityUtils.getSubject().login(token);

        return R.ok();
    }
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(){
		
		return "login";
	}

    @RequestMapping(value="/index",method=RequestMethod.GET)
    public String index(){
        Subject subject = SecurityUtils.getSubject();
        User principal = (User)subject.getPrincipal();

        return "index";
    }
    @RequestMapping(value="/logout",method=RequestMethod.GET)
    public String logout(){
    	ShiroUtils.logout();
    	return "redirect:/login";
    }
    @RequestMapping(value="/error",method=RequestMethod.GET)
    public String error(){
    	return "404";
    }


}
