package ru.kpfu.itis.annotations.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface HtmlForm {

    String method() default "get";

    String action() default "/";

    String enctype() default "text/plain";

}
