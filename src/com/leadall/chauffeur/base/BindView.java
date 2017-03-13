package com.leadall.chauffeur.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindView {
	
	public int id() default 0;

	public boolean click() default false;

	public boolean longClick() default false;

	public boolean itemClick() default false;

	public boolean itemLongClick() default false;
}