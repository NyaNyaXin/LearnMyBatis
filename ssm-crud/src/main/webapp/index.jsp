<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工列表</title>

<%
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<!-- 
	web路径：
	不以/开始的相对路径，找资源，以当前资源的路径为基准，经常会出现问题
	以/开始的相对路径，找资源，以服务器的路径为标准
	http://localhost:8080/crud:需要加上项目名
 -->
<script src="${APP_PATH}/static/js/jquery-1.12.4.min.js"></script>
<link
	href="${APP_PATH}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="${APP_PATH}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>
	<!-- 搭建显示页面 -->
	<div class="container">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h1>SSM-CRUD</h1>
			</div>
		</div>
		<!-- 按钮 -->
		<div class="row">
			<div class="col-md-4 col-md-offset-8">
				<button class="btn btn-primary">新增</button>
				<button class="btn btn-danger">刪除</button>
			</div>
		</div>
		<!-- 表格数据 -->
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover" id="emps_table">
					<thead>
					<tr>
						<th>#</th>
						<th>empName</th>
						<th>gender</th>
						<th>email</th>
						<th>deptName</th>
						<th>操作</th>
					</tr>
					</thead>
					<tbody>
					
					</tbody>
				</table>
			</div>
		</div>
		<!-- 分页信息栏 -->
		<div class="row">
			<!-- 文字信息 -->
			<div class="col-md-6" id="page_info_area"></div>
			<!-- 分页条信息 -->
			<div class="col-md-6" id="page_nav_area">
				
			</div>
		</div>
	</div>
	<script type="text/javascript">
		//1.页面加载完毕以后直接发送一个Ajax请求，获取分页数据
		$(function () {
			//去首页
			toPage(1);
		});
		
		function toPage(pn){
			$.ajax({
				url:"${APP_PATH}/emps",
				data:"pn="+pn,
				type:"get",
				success:function(result){
					//console.log(result);
					//1.解析并显示员工数据
					build_emps_table(result);
					//2.解析并显示分页数据
					build_pages_info(result);
					//3.解析并显示分页条
					build_pages_nav(result);
				}
			});
		}
		//解析显示员工信息
		function build_emps_table(result) {
			//清空table表格
			$("#emps_table tbody").empty();
			var emps = result.extend.pageInfo.list;
			$.each(emps,function(index,item){
				var empIdTd = $("<td></td>").append(item.empId);
				var empNameTd = $("<td></td>").append(item.empName);
				var gender = item.gender=='M'?'男':'女';
				var empGenderTd = $("<td></td>").append(gender);
				var empEmailTd = $("<td></td>").append(item.email);
				var empDepeName = $("<td></td>").append(item.department.deptName);
				/*
					<button class="btn btn-primary btn-sm" aria-label="Left Align">
									<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑
								</button>
				*/
				var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm")
				              .append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("编辑")
				
				var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm")
	                         .append($("<span></span>").addClass("glyphicon glyphicon-remove")).append("删除")
				var BtnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn);
				//append方法执行完成后还是返回原来的元素
				$("<tr></tr>").append(empIdTd).append(empNameTd).append(empGenderTd)
							  .append(empEmailTd).append(empDepeName)
							  .append(BtnTd).appendTo("#emps_table tbody");
			});
		}
		//解析显示分页信息
		function build_pages_info(result) {
			//清空分页信息
			$("#page_info_area").empty();
			$("#page_info_area").append("当前"+result.extend.pageInfo.pageNum+"页，总页码"+result.extend.pageInfo.pages+",总共条"+result.extend.pageInfo.total+"记录");
		}
		//解析显示分页条,点击分页要能去指定页面
		function build_pages_nav(result) {
			//清空分页条
			$("#page_nav_area").empty();
			var ul = $("<ul></ul>").addClass("pagination");
			
			//构建元素
			var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));
			var firstPageLi= $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
			
			if(result.extend.pageInfo.hasPreviousPage == false){
				firstPageLi.addClass("disabled");
				prePageLi.addClass("disabled");
			}else{
				//为元素添加点击翻页的事件
				firstPageLi.click(function () {
					toPage(1);
				});
				prePageLi.click(function () {
					toPage(result.extend.pageInfo.pageNum-1);
				});
			}
			
			var lastPageLi = $("<li></li>").append($("<a></a>").append("尾页").attr("href","#"));
			var NextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
			if(result.extend.pageInfo.hasNextPage == false){
				lastPageLi.addClass("disabled");
				NextPageLi.addClass("disabled");
			}else{
				//为元素添加点击翻页的事件
				lastPageLi.click(function () {
					toPage(result.extend.pageInfo.pages);
				});
				NextPageLi.click(function () {
					toPage(result.extend.pageInfo.pageNum+1);
				});
			}
			
			//添加首页和前一页的提示
			ul.append(firstPageLi).append(prePageLi);
			//页码遍历
			$.each(result.extend.pageInfo.navigatepageNums,function(index,item){
				
				var numLi = $("<li></li>").append($("<a></a>").append(item).attr("href","#"));
				if(result.extend.pageInfo.pageNum == item){
					numLi.addClass("active");
				}
				//添加页码提示信息
				numLi.click(function () {
					toPage(item);
				})
				ul.append(numLi);
			});
			
			//添加下一页和尾页的提示
			ul.append(NextPageLi).append(lastPageLi);
			//把ul加入到nav
			var navEle=$("<nav></nav>").append(ul);
			navEle.appendTo("#page_nav_area");
		}
	</script>
</body>
</html>