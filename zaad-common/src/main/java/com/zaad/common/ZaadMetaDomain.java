package com.zaad.common;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.lang.reflect.Field;

public class ZaadMetaDomain {
    public ZaadMetaDomain() {

    }

    @Override
    public String toString() {
        return (new ReflectionToStringBuilder(this) {
            protected boolean accept(Field f) {
                return super.accept(f) && !f.getName().equals("password");
            }
        }).toString();
    }
}
