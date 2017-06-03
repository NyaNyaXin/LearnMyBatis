package com.cx.crud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cx.crud.bean.Employee;
import com.cx.crud.bean.Message;
import com.cx.crud.service.EmployeeService;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers.BigIntegerDeserializer;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 处理员工CRUD请求
 **/
@Controller
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;
	
	/**
	 * 导入jackson包
	 * **/
	@ResponseBody
	@RequestMapping("/emps")
	public Message getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
		// 这不是一个分页查询
		// 引入pageHelper插件
		// 在查询之前只需调用如下方法,传入页码以及每页的大小
		PageHelper.startPage(pn, 5);
		// startPage紧跟的查询就是分页查寻
		List<Employee> emps = employeeService.getAll();
		// 使用pageInfo包装查询之后的结果，只需要将pageInfo交给页面就行
		// 封装了详细的分页信息，包括数据,可以传入连续显示的页数
		PageInfo page = new PageInfo(emps, 5);
		model.addAttribute("pageInfo", page);
		return Message.success().add("pageInfo",page);
	}

	/**
	 * 查询员工数据（分页）
	 **/
	// @RequestMapping("/emps")
	public String getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
		// 这不是一个分页查询
		// 引入pageHelper插件
		// 在查询之前只需调用如下方法,传入页码以及每页的大小
		PageHelper.startPage(pn, 8);
		// startPage紧跟的查询就是分页查寻
		List<Employee> emps = employeeService.getAll();
		// 使用pageInfo包装查询之后的结果，只需要将pageInfo交给页面就行
		// 封装了详细的分页信息，包括数据,可以传入连续显示的页数
		PageInfo page = new PageInfo(emps, 5);
		model.addAttribute("pageInfo", page);
		return "list";
	}
	/**
	 * 1.支持JSR303校验
	 * 2.导入Hibernate-validator
	 * **/
	@RequestMapping(value="/emp",method=RequestMethod.POST)
	@ResponseBody
	public Message saveEmp(@Valid Employee employee,BindingResult result) {
		if(result.hasErrors()) {
			Map<String, Object> map = new HashMap<>();
			//校验失败，返回失败，在模态框中显示校验失败的错误信息
			List<FieldError> fieldErrors = result.getFieldErrors();
			for(FieldError fieldError:fieldErrors) {
				System.out.println("错误的字段名："+fieldError.getField()+"，错误信息是："+fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Message.fail().add("errorFields", map);
		}else {
			employeeService.saveEmp(employee);
			return Message.success();
		}
		
	}
	/**
	 * 检查用户名
	 * **/
	@ResponseBody
	@RequestMapping("/checkuser")
	public Message checkUser(@RequestParam("empName") String empName) {
		//先判断用户名是否是合法的表达式
		String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
		if(!empName.matches(regx)) {
			return Message.fail().add("va_msg", "用户名不合法");
		}
		//数据库用户名重复校验
		Boolean b =  employeeService.checkUser(empName);
		if(b) {
			return Message.success();
		}else {
			return Message.fail().add("va_msg", "用户名不可用");
		}
	}
}
