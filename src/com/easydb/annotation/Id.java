package com.easydb.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Id {
	public enum Type{
		String,AUTO
	}
	
	Type type() default Type.AUTO;//类型
	long length() default 32;//长度
}
