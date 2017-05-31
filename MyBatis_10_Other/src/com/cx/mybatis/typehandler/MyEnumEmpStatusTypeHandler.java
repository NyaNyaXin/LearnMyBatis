package com.cx.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.cx.mybatis.bean.EmpStatus;

/**
 * 1.实现TypeHandler接口或继承BaseTypeHandler
 **/
public class MyEnumEmpStatusTypeHandler implements TypeHandler<EmpStatus> {

	@Override
	public EmpStatus getResult(ResultSet rs, String columnName) throws SQLException {
		//需要根据从数据空中拿到的状态码返回一个枚举对象
		int code = rs.getInt(columnName);
		System.out.println("从数据库中获取的状态码"+code);
		EmpStatus empStatus = EmpStatus.getEmpStatusByCode(code);
		return empStatus;
	}

	@Override
	public EmpStatus getResult(ResultSet rs, int columnIndex) throws SQLException {
		//需要根据从数据空中拿到的状态码返回一个枚举对象
				int code = rs.getInt(columnIndex);
				System.out.println("从数据库中获取的状态码"+code);
				EmpStatus empStatus = EmpStatus.getEmpStatusByCode(code);
				return empStatus;
	}

	@Override
	public EmpStatus getResult(CallableStatement cs, int columnIndex) throws SQLException {
		//需要根据从数据空中拿到的状态码返回一个枚举对象
		int code = cs.getInt(columnIndex);
		System.out.println("从数据库中获取的状态码"+code);
		EmpStatus empStatus = EmpStatus.getEmpStatusByCode(code);
		return empStatus;
	}

	/**
	 * 定义当前数据如何保存到数据库中
	 **/
	@Override
	public void setParameter(PreparedStatement ps, int i, EmpStatus empStatus, JdbcType arg3) throws SQLException {
		ps.setString(i, empStatus.getCode().toString());
	}

}
