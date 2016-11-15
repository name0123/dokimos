package com.example.helloaspect;

import org.aspectj.lang.annotation.Aspect;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class CacheAdvice {
	/*
    @Pointcut("call(* getPlacesName(..)) && args(s,a)")
    void getPlacesName(String s, Activity a) {}

    @Around("getPlacesName(s, a)")
    public void cacheOrConnection(ProceedingJoinPoint thisJoinPoint, String s, Activity a) {
    	System.out.println("s kitty");
    	System.out.println(s+' ' +a.getApplicationContext().getClass().toString());
    	System.out.println("bye kitty");
    	boolean cachedSearch = false;
    	
    	if(a != null && !s.equals("")) {
			System.out.println("Activity is not null: ");
			Context context = a.getApplicationContext();
			SharedPreferences sharedprf = context.getSharedPreferences("SearchCache",Context.MODE_PRIVATE);
			if(sharedprf != null){
				Map<String, ?> allEntries = sharedprf.getAll();
				for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
					System.out.println(entry.getKey()+" "+entry.getValue().toString());
					if(s.equals(entry.getKey())) {
						cachedSearch = true;
					}
				}
			}
			if(!cachedSearch){
				// no tenim en cache la direccio buscada
				try {
					// la busquem - en el proxim Aspecte guardem els resultats en el lloc vaule="empty"
			    	thisJoinPoint.proceed(); // no canviem els args
			    	SharedPreferences.Editor editor = sharedprf.edit();
					editor.putString(s,"emplty");
					editor.commit();
					
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //using Java 5 autoboxing
			}
		}
    } */
}
