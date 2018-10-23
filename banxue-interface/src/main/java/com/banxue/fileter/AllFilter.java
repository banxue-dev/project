package com.banxue.fileter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.banxue.utils.log.FileLog;

/**
作者：fengchase
时间：2018年9月27日
文件：AllFilter.java
项目：banxue-interface
*/
@WebFilter(filterName="myFilter",urlPatterns="/*")
@Configuration
public class AllFilter implements Filter {
	@Value("${jsonpurl}")
	private static String jsonpurl;
	@Override
	public void destroy() {
		// TODO 此处为方法主题
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		// TODO 此处为方法主题
	     HttpServletResponse response = (HttpServletResponse) servletResponse;
	     response.setHeader("Access-Control-Allow-origin", "http://127.0.0.1:8084");
	     response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
	     response.setHeader("Access-Control-Max-Age", "0");
	     response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
	     response.setHeader("Access-Control-Allow-Credentials", "true");
	     response.setHeader("XDomainRequestAllowed","1");
	     filterChain.doFilter(servletRequest, response);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO 此处为方法主题
		
	}

}

