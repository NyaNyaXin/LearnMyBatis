package com.cx.crud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.RequestScope;

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
	
	/**
	 * 查询单个员工
	 * **/
	@RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Message getEmp(@PathVariable("id")Integer id) {
		Employee employee = employeeService.getEmp(id);
		return Message.success().add("emp", employee);
	}
	
	/**
	 * 
	 * 保存更新
	 * 
	 * 如果直接发送Ajax=put形式的请求
	 * 封装的数据将为
	 * Employee [empId=1026, empName=null, gender=null, email=null, dId=null, department=null]
	 * 
	 * 问题：
	 * 请求体中有数据，但是employee对象封装不上
	 * update tbl_emp where emp_id =111
	 * 
	 * 原因：
	 * Tomcat：
	 * 		1.将请求体中的数据封装程一个map
	 * 		2.request.getParamter("empName")就会从这个map中取值
	 * 		3.SpringMVC封装POJO对象的时候。会把POJO中的每一个都通过getParamter方法获取到
	 * Ajax发送PUT请求引发的问题
	 * 		PUT请求体中的数据getParamter拿不到
	 * 		Tomcat看见了PUT请求不会讲请求封装为map
	 * 		只有POST才可以
	 * org.apache.catalina.connector.Request;
	 * 
	 * 解決方式
	 * 要想支持PUT請求且封裝數據
	 * 要使用HttpPutFormContentFilter
	 * 把請求躰中的數據包裝成map
	 * request被重新包裝：request.getParamter方法被重寫，就會從自己封裝的map中獲取
	 * **/
	@ResponseBody
	@RequestMapping(value="/emp/{empId}",method=RequestMethod.PUT)
	public Message saveEmp(Employee employee ,HttpServletRequest request) {
		System.out.println(request.getParameter("empName"));
		System.out.println("将要更新的数据"+employee);
		employeeService.updateEmp(employee);
		return Message.success();
	}
	/**
	 * 单个，批量二合一
	 * 批量删除1-2-3
	 * **/
	@ResponseBody
	@RequestMapping(value="/emp/{ids}",method=RequestMethod.DELETE)
	public Message deleteEmpById(@PathVariable("ids") String ids) {
		//批量删除
		if(ids.contains("-")) {
			String[] str_ids = ids.split("-");
			List<Integer> del_ids = new ArrayList<>();
			//组装id的集合
			for(String string:str_ids) {
				del_ids.add(Integer.parseInt(string));
			}
			employeeService.deleteBatch(del_ids);
		}else {
			Integer id = Integer.parseInt(ids);
			employeeService.deleteEmp(id);
		}
		
		
		
		return Message.success();
	}
}
