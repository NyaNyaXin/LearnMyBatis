package com.cx.mybatis.bean;

/**
 * 希望数据库保存的是状态码，而不是索引或枚举名
 **/
public enum EmpStatus {
	LOGIN(100, "用户登录"), LOGOUT(200, "用户登出"), REMOVE(300, "用户不存在");

	private Integer code;
	private String msg;

	private EmpStatus(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public static EmpStatus getEmpStatusByCode(Integer code) {
		switch (code) {
		case 100:
			return EmpStatus.LOGIN;
		case 200:
			return EmpStatus.LOGOUT;
		case 300:
			return EmpStatus.REMOVE;
		default:
			return EmpStatus.LOGOUT;
		}
	}

}
