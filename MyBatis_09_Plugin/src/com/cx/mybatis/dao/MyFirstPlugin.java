package com.cx.mybatis.dao;

import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

/**
 * 完成插件签名：
 * 	告诉MyBatis当前插件用来拦截哪个对象的哪个方法
 * **/
@Intercepts({
			@Signature(type=StatementHandler.class,method="parameterize",args=java.sql.Statement.class)
		})
public class MyFirstPlugin implements Interceptor {

	@Override
	/**
	 * intercept：拦截目标对象的目标方法的执行
	 * **/
	public Object intercept(Invocation invocation) throws Throwable {
		//执行目标方法
		Object proceed = invocation.proceed();
		System.out.println("MyFirstPlugin....intercept.....:"+invocation.getMethod());
		//返回执行后的返回值
		return proceed;
	}

	/**
	 * plugin:包装目标对象：包装，为目标对象创建代理对象
	 * **/
	@Override
	public Object plugin(Object target) {
		
		System.out.println("MyFirstPlugin....plugin.....:mybatis将要包装的对象："+target);
		//可以借助Plugin类的warp方法来使用当前Interceptor包装目标对象
		Object wrap = Plugin.wrap(target, this);
		//返回为当前target创建的动态代理
		return wrap;
	}

	/**
	 * 	setProperties:将插件注册时的property属性设置进来
	 * **/
	@Override
	public void setProperties(Properties properties) {
		System.out.println("MyFirstPlugin....setProperties.....");
		System.out.println("插件配置的信息："+properties);
	}

}
