package com.zaad.common;

import java.lang.reflect.Field;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ZaadMetaDomain {
	public ZaadMetaDomain() {
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (new ReflectionToStringBuilder(this) {
	         protected boolean accept(Field f) {
	             return super.accept(f) && !f.getName().equals("password");
	         }
	     }).toString();
	}
}
