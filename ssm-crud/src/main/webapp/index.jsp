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

<!-- 员工修改的模态框 -->
	<div class="modal fade" id="empUpdateModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">员工修改</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">员工名字</label>
							<div class="col-sm-10">
								<p class="form-control-static" id="empName_update_static"></p>
								<span id="" class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Email</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="email_update_input"
									name="email" placeholder="email@163.com"> 
								<span id="" class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">性别</label>
							<div class="col-sm-10">
								<label class="radio-inline"> <input type="radio"
									name="gender" id="gender1_update_input" value="M"
									checked="checked"> 男
								</label> <label class="radio-inline"> <input type="radio"
									name="gender" id="gender2_update_input" value="F"> 女
								</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">部门</label>
							<div class="col-sm-4">
								<!-- 部门提交部门id -->
								<select class="form-control" name="dId" id="dept_update_select">

								</select>
							</div>
						</div>


					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="emp_update_btn">更新</button>
				</div>
			</div>
		</div>
	</div>





	<!-- 员工添加的模态框 -->
	<div class="modal fade" id="empAddModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">员工添加</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">员工名字</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="empName_add_input"
									name="empName" placeholder="员工名字"> 
								<span id="" class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Email</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="email_add_input"
									name="email" placeholder="email@163.com"> 
								<span id="" class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">性别</label>
							<div class="col-sm-10">
								<label class="radio-inline"> <input type="radio"
									name="gender" id="gender1_add_input" value="M"
									checked="checked"> 男
								</label> <label class="radio-inline"> <input type="radio"
									name="gender" id="gender2_add_input" value="F"> 女
								</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">部门</label>
							<div class="col-sm-4">
								<!-- 部门提交部门id -->
								<select class="form-control" name="dId" id="dept_add_select">

								</select>
							</div>
						</div>


					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="emp_save_btn">保存</button>
				</div>
			</div>
		</div>
	</div>

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
				<button class="btn btn-primary" id="emp_add_modal_btn">新增</button>
				<button class="btn btn-danger">刪除</button>
			</div>
		</div>
		<!-- 表格数据 -->
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover" id="emps_table">
					<thead>
						<tr>
							<th>
								<input type="checkbox" id="check_all" />
								
							</th>
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
			<div class="col-md-6" id="page_nav_area"></div>
		</div>
	</div>
	<script type="text/javascript">
		var totalRecord,currentNum;
		//1.页面加载完毕以后直接发送一个Ajax请求，获取分页数据
		$(function() {
			//去首页
			toPage(1);
		});

		function toPage(pn) {
			$.ajax({
				url : "${APP_PATH}/emps",
				data : "pn=" + pn,
				type : "get",
				success : function(result) {
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
			$.each(emps, function(index, item) {
				var checkBoxTd = $("<td><input type='checkbox' class='check_item'></td>");
				var empIdTd = $("<td></td>").append(item.empId);
				var empNameTd = $("<td></td>").append(item.empName);
				var gender = item.gender == 'M' ? '男' : '女';
				var empGenderTd = $("<td></td>").append(gender);
				var empEmailTd = $("<td></td>").append(item.email);
				var empDepeName = $("<td></td>").append(
						item.department.deptName);
				var editBtn = $("<button></button>").addClass(
						"btn btn-primary btn-sm edit_btn").append(
						$("<span></span>").addClass(
								"glyphicon glyphicon-pencil")).append("编辑");
				
				//为编辑按钮添加一个自定义的属性，来表示当前员工的id
				editBtn.attr("edit-id",item.empId);
				 
				var delBtn = $("<button></button>").addClass(
						"btn btn-danger btn-sm delete_btn").append(
						$("<span></span>").addClass(
								"glyphicon glyphicon-remove")).append("删除");
				//为编辑按钮添加一个自定义的属性，来表示当前员工的id
				delBtn.attr("del-id",item.empId);
				var BtnTd = $("<td></td>").append(editBtn).append(" ").append(
						delBtn);
				//append方法执行完成后还是返回原来的元素
				$("<tr></tr>").append(checkBoxTd).append(empIdTd).append(empNameTd).append(
						empGenderTd).append(empEmailTd).append(empDepeName)
						.append(BtnTd).appendTo("#emps_table tbody");
			});
		}
		//解析显示分页信息
		function build_pages_info(result) {
			//清空分页信息
			$("#page_info_area").empty();
			$("#page_info_area").append(
					"当前" + result.extend.pageInfo.pageNum + "页，总页码"
							+ result.extend.pageInfo.pages + ",总共条"
							+ result.extend.pageInfo.total + "记录");
			totalRecord = result.extend.pageInfo.total;
			currentNum = result.extend.pageInfo.pageNum;
		}
		//解析显示分页条,点击分页要能去指定页面
		function build_pages_nav(result) {
			//清空分页条
			$("#page_nav_area").empty();
			var ul = $("<ul></ul>").addClass("pagination");

			//构建元素
			var prePageLi = $("<li></li>").append(
					$("<a></a>").append("&laquo;"));
			var firstPageLi = $("<li></li>").append(
					$("<a></a>").append("首页").attr("href", "#"));

			if (result.extend.pageInfo.hasPreviousPage == false) {
				firstPageLi.addClass("disabled");
				prePageLi.addClass("disabled");
			} else {
				//为元素添加点击翻页的事件
				firstPageLi.click(function() {
					toPage(1);
				});
				prePageLi.click(function() {
					toPage(result.extend.pageInfo.pageNum - 1);
				});
			}

			var lastPageLi = $("<li></li>").append(
					$("<a></a>").append("尾页").attr("href", "#"));
			var NextPageLi = $("<li></li>").append(
					$("<a></a>").append("&raquo;"));
			if (result.extend.pageInfo.hasNextPage == false) {
				lastPageLi.addClass("disabled");
				NextPageLi.addClass("disabled");
			} else {
				//为元素添加点击翻页的事件
				lastPageLi.click(function() {
					toPage(result.extend.pageInfo.pages);
				});
				NextPageLi.click(function() {
					toPage(result.extend.pageInfo.pageNum + 1);
				});
			}

			//添加首页和前一页的提示
			ul.append(firstPageLi).append(prePageLi);
			//页码遍历
			$.each(result.extend.pageInfo.navigatepageNums, function(index,
					item) {

				var numLi = $("<li></li>").append(
						$("<a></a>").append(item).attr("href", "#"));
				if (result.extend.pageInfo.pageNum == item) {
					numLi.addClass("active");
				}
				//添加页码提示信息
				numLi.click(function() {
					toPage(item);
				})
				ul.append(numLi);
			});

			//添加下一页和尾页的提示
			ul.append(NextPageLi).append(lastPageLi);
			//把ul加入到nav
			var navEle = $("<nav></nav>").append(ul);
			navEle.appendTo("#page_nav_area");
		}
		
		function resetform(ele) {
			//充值表单内容
			$(ele)[0].reset();
			//清空验证样式
			$(ele).find("*").removeClass("has-error has-success");
			$(ele).find(".help-block").text("");
		}
		//点击新增按钮弹出模态框
		$("#emp_add_modal_btn").click(function() {
			//清除表单数据（表单完整重置，表单数据即样式）
			resetform("#empAddModal form")
			//发送ajax请求获得部门信息屏显示下拉列表
			getDepts("#empAddModal select");
			$('#empAddModal').modal({
				backdrop : "static"
			});
		});
		//校验表单数据
		function validate_add_form() {
			//1.获取到要校验的数据，使用正则表达式
			var empName = $("#empName_add_input").val();
			var regName = /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
			if (!regName.test(empName)) {
				show_validate_msg("#empName_add_input","error","请输入正确的用户名");
				return false;
			}else{
				
				show_validate_msg("#empName_add_input","success","");
				
			}
			//2.校验邮箱信息
			var email = $("#email_add_input").val();
			var emailReg = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
			if (!emailReg.test(email)) {
				show_validate_msg("#email_add_input","error","请输入正确的邮箱");
				return false;
			}else{
				
				show_validate_msg("#email_add_input","success","");
			}
			return true;
		}
		//显示校验结果的提示信息
		function show_validate_msg(ele,status,msg){
			//清空这个元素之前的样式
			$(ele).parent().removeClass("has-success has-error");
			$(ele).next("span").text("");
			if(status=="success"){
				$(ele).parent().addClass("has-success");
				$(ele).next("span").text(msg);
			}else if(status=="error"){
				$(ele).parent().addClass("has-error");
				$(ele).next("span").text(msg);
			}
		}
		//点击保存，保存员工
		$("#emp_save_btn").click(function() {
			//1.将模态框中添加的数据提交给服务器进行保存
			//1.先对要提交给服务器的数据进行校验
			if (!validate_add_form()) {
				return false;
			} 
			//1.判断之前的Ajax用户名校验是否成功。如果成功
			if($(this).attr("ajax_va")=="error"){
				return false;	
			}
			//2.发送ajax请求保存数据
			$.ajax({
				url : "${APP_PATH}/emp",
				type : "POST",
				data : $("#empAddModal form").serialize(),
				success : function(result) {
					if(result.code==100){
						//alert(result.msg);
						//1.关闭模态框
						$('#empAddModal').modal('hide');
						//2.来到最后一页,发送ajax请求显示
						toPage(totalRecord);
					}else{
						//显示失败信息
						//console.log(result);
						//有哪个字段的错误信息，就显示那个字段的错误信息
						if(undefined != result.extend.errorFields.email){
							show_validate_msg("#email_add_input","error",result.extend.errorFields.email);
						}
						if(undefined != result.extend.errorFields.empName){
							show_validate_msg("#empName_add_input","error",result.extend.errorFields.empName);
						}
					}

				}
			});

		});

		function getDepts(ele) {
			//清空之前下拉列表的值
			$(ele).empty();
			$.ajax({
				url : "${APP_PATH}/depts",
				type : "GET",
				success : function(result) {
					$.each(result.extend.depts, function() {
						var optionElement = $("<option></option>").append(
								this.deptName).attr("value", this.deptId);
						optionElement.appendTo(ele);
					});
				}
			});
		}
		
		$("#empName_add_input").change(function () {
			var empName = this.value;
			//发送Ajax请求校验用户名是否可用
			$.ajax({
				url:"${APP_PATH}/checkuser",
				data:"empName="+empName,
				type:"POST",
				success:function(result){
					if(result.code==100){
						show_validate_msg("#empName_add_input","success","用户名可用");
						$("#emp_save_btn").attr("ajax_va","success");
					}else{
						show_validate_msg("#empName_add_input","error",result.extend.va_msg);
						$("#emp_save_btn").attr("ajax_va","error");
					}
				}
			});
		});
		
		//1.按钮创建之前是无法绑定事件的
		//jQuery新版没有live方法，要是用on进行替代
		$(document).on("click",".edit_btn",function(){
			
			//1.查询出部门信息，并显示部门列表
			getDepts("#empUpdateModal select");
			//2.查出并显示员工信息
			getEmp($(this).attr("edit-id"));
			//3.把员工id传递给模态框的更新按钮
			$("#emp_update_btn").attr("edit-id",$(this).attr("edit-id"));
			$('#empUpdateModal').modal({
				backdrop : "static"
			});
		});
		
		function getEmp(id) {
			$.ajax({
				url:"${APP_PATH}/emp/"+id,
				type:"GET",
				success:function(result){
					var empData = result.extend.emp;
					$("#empName_update_static").text(empData.empName);
					$("#email_update_input").val(empData.email);
					$("#empUpdateModal input[name=gender]").val([empData.gender]);
					$("#empUpdateModal select").val([empData.dId]);
				}
			});
		}
		
		//点击更新，更新员工信息
		$("#emp_update_btn").click(function () {
			//验证邮箱是否合法
			var email = $("#email_update_input").val();
			var emailReg = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
			if (!emailReg.test(email)) {
				show_validate_msg("#email_update_input","error","请输入正确的邮箱");
				return false;
			}else{
				
				show_validate_msg("#email_update_input","success","");
			}
			//2.发送ajax请求，保存更新的员工数据
			$.ajax({
				url:"${APP_PATH}/emp/"+$(this).attr("edit-id"),
				type:"PUT",
				data:$("#empUpdateModal form").serialize(),
				success:function(result){
					//1.关闭对话框，回到原来的页面
					$('#empUpdateModal').modal("hide");
					toPage(currentNum);
				}
			});
		});
		//单个删除
		$(document).on("click",".delete_btn",function(){
			//1.弹出确认删除对话框
			var empName = $(this).parents("tr").find("td:eq(1)").text();
			var empId = $(this).attr("del-id");
			if(confirm("确认删除【"+empName+"】吗？")){
				//确认发送Ajax请求
				$.ajax({
					url:"${APP_PATH}/emp/"+empId,
					type:"DELETE",
					success:function(result){
						//1.关闭对话框，回到原来的页面
						toPage(currentNum);
					}
				});
			}
		});
		//完成全选全部选功能
		$("#check_all").click(function(){
			//attr获取checked是undefined
			//DOM原生的属性使用prop获取值，attr获取自定义的属性值
			//alert($(this).prop("checked"));
			$(".check_item").prop("checked",$(this).prop("checked"));
		});
		
		$(document).on("click",".check_item",function(){
			//判断当前选中的元素是否为五个
			var flag = $(".check_item:checked").length==$(".check_item").length;
			$("#check_all").prop("checked",flag);
			
		});
	</script>
</body>
</html>