package com.qijiabin.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qijiabin.entity.Userinfo;
import com.qijiabin.manager.UserManager;
import com.qijiabin.query.UserQuery;

@Controller
@RequestMapping("/user/")
public class UserController {
	
	private UserManager userManager;
	
	
	@ModelAttribute("preloadUserinfo")
	public Userinfo getUserinfo(@RequestParam(value="uid",required=false) Integer uid)  {
		if (uid != null && !"".equals(uid) && uid!=0) {//由于uid为int类型，因此新增时默认为0
			return  userManager.getById(uid);
		} else {
			return new Userinfo();
		}
	}
	
	//代码式权限验证
	@RequestMapping("list")
	public String list(UserQuery query,Model model){
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.isPermitted("/user/list")){
			List<Userinfo> list = userManager.findList(query);
			model.addAttribute("list", list);
			return "user/user-list";
		}else{
			return "redirect:/noperms.jsp";
		}
	}
	
	@RequestMapping("create")
	public String create(@ModelAttribute("preloadUserinfo") Userinfo entity,Model model){
		model.addAttribute("entity", entity);
		return "user/user-input";
	}
	
	@RequestMapping("save")
	public String save(@ModelAttribute("preloadUserinfo") Userinfo entity,Model model){
		userManager.save(entity);
		return "redirect:/user/list";
	}
	
	//注解式权限验证，若匹配失败则抛出异常，被spring-mvc.xml中异常捕捉器捕捉到，跳转到提示页面
	@RequiresRoles("superAdmin")
	@RequestMapping("update")
	public String update(@ModelAttribute("preloadUserinfo") Userinfo entity,Model model){
			model.addAttribute("entity", entity);
			return "user/user-input";
	}
	
	@RequestMapping("delete")
	public String delete(@ModelAttribute("preloadUserinfo") Userinfo entity,Model model){
		userManager.delete(entity);
		return "redirect:/user/list";
	}
	
	@Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
}
