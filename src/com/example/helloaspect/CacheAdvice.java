package com.example.helloaspect;

import org.aspectj.lang.annotation.Aspect;

import android.app.Activity;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class CacheAdvice {
    @Pointcut("call(* getPlacesName(..)) && args(s,a)")
    void getPlacesName(String s, Activity a) {}

    @Around("getPlacesName(s, a)")
    public Object cacheOrConnection(ProceedingJoinPoint thisJoinPoint, String s, Activity a) {
    	System.out.println("s kitty");
    	System.out.println(a.getApplicationContext().getClass().toString());
    	System.out.println("bye kitty");
    	
    try {
    	return thisJoinPoint.proceed(); // no canviem els args
	} catch (Throwable e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} //using Java 5 autoboxing
	return null;
    }

}
