package com.qijiabin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qijiabin.entity.Student;
import com.qijiabin.manager.StudentManager;

@Controller
@RequestMapping("/student/")
public class StudentController {

	@Autowired
	private StudentManager studentManager;
	
	
	@ModelAttribute("preloadStudent")
	public Student getStudent(@RequestParam(value="sid",required=false) Integer sid)  {
		if (sid != null && !"".equals(sid) && sid!=0) {//由于sid为int类型，因此新增时默认为0
			return  studentManager.getById(sid);
		} else {
			return new Student();
		}
	}
	
	@RequestMapping("create")
	public String create(@ModelAttribute("preloadStudent") Student entity,Integer cid,Model model){
		model.addAttribute("entity", entity);
		model.addAttribute("cid", cid);
		return "student/student-input";
	}
	
	@RequestMapping("save")
	public String save(@ModelAttribute("preloadStudent") Student entity,Integer cid,Model model,HttpServletRequest request,HttpServletResponse response) throws IOException{
		studentManager.saveEntity(entity,cid);
	//	return "redirect:/student/list";
		
		String url = request.getContextPath() + "/clazz/list";
		response.sendRedirect(url);
		return null;
	}

	public void setStudentManager(StudentManager studentManager) {
		this.studentManager = studentManager;
	}
	
	
}
