package com.qijiabin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qijiabin.entity.Userinfo;
import com.qijiabin.util.EncryptUtils;

@Controller
@RequestMapping(value = "login")
public class LoginController {
	
	/**
	 * 用户登录
	 */
	@RequestMapping("")
	public String login(Userinfo user,HttpSession session, HttpServletRequest request,Model model) {
		//得到Subject及创建用户名/密码身份验证Token(即用户身份/凭证)
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUname(), EncryptUtils.encryptMD5(user.getUpass()));
		token.setRememberMe(true);
		try {
			currentUser.login(token);
		} catch (Exception e) {
			return "redirect:/login.jsp?type=1";
		}
		if(currentUser.isAuthenticated()){
			session.setAttribute("loginUser", user);
			return "main";
		}else{
			return "redirect:/login.jsp?type=1";
		}
	}

	/**
	 * 退出登录
	 */
	@RequestMapping("logout")
	public String logout() {
		Subject currentUser = SecurityUtils.getSubject();
		try {
			currentUser.logout();
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
		return "redirect:/login.jsp";
	}

}
