package com.qijiabin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qijiabin.entity.Clazz;
import com.qijiabin.manager.ClazzManager;
import com.qijiabin.query.ClazzQuery;

@Controller
@RequestMapping("/clazz/")
public class ClazzController {

	@Autowired
	private ClazzManager clazzManager;
	
	
	@ModelAttribute("preloadClazz")
	public Clazz getClazz(@RequestParam(value="cid",required=false) Integer cid)  {
		if (cid != null && !"".equals(cid) && cid!=0) {//由于cid为int类型，因此新增时默认为0
			return  clazzManager.getById(cid);
		} else {
			return new Clazz();
		}
	}
	
	@RequestMapping("list")
	public String list(ClazzQuery query,Model model){
		List<Clazz> list = clazzManager.findList(query);
		model.addAttribute("list", list);
		return "clazz/clazz-list";
	}
	
	@RequestMapping("create")
	public String create(@ModelAttribute("preloadClazz") Clazz entity,Model model){
		model.addAttribute("entity", entity);
		return "clazz/clazz-input";
	}
	
	@RequestMapping("save")
	public String save(@ModelAttribute("preloadClazz") Clazz entity,Model model){
		clazzManager.save(entity);
		return "redirect:/clazz/list";
	}
	
	@RequestMapping("update")
	public String update(@ModelAttribute("preloadClazz") Clazz entity,Model model){
		model.addAttribute("entity", entity);
		return "clazz/clazz-input";
	}
	
	@RequestMapping("delete")
	public String delete(@ModelAttribute("preloadClazz") Clazz entity,Model model){
		clazzManager.delete(entity);
		return "redirect:/clazz/list";
	}
	
	@RequestMapping("detail")
	public String detail(@ModelAttribute("preloadClazz") Clazz entity,Model model){
		model.addAttribute("entity", entity);
		return "clazz/clazz-detail";
	}

	public void setClazzManager(ClazzManager clazzManager) {
		this.clazzManager = clazzManager;
	}
	
	
	
}
