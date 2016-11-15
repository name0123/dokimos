package com.example.helloaspect;

import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public privileged aspect CacheAdvicer {
	/*
	pointcut doBack(Object[] o) : execution(protected JSONArray *.doInBackground(..)) && args(o);
	
	@Around("doBack(Object[] o"){)
	void 
		
		
	}
	
	pointcut checkThis(Activity a): target(a) && (call (void checkExternalDependencies(..)));
	pointcut checkCache(Activity a): target(a) && (call (void cacheSearchResult(Activity)));
	pointcut getPlacesName(Activity a, String searched): target(a) && (call (void getPlacesName(..))&& args(searched));
	
	
	before(Activity a): checkThis(a) {
		Object s =  thisJoinPoint.getThis();
		Object t =  thisJoinPoint.getTarget(); // basicaly the same
		System.err.println("This is new");
		if(a != null) {
			System.out.println("A is not null: "+a.getApplicationContext().toString());
			Context context = a.getApplicationContext();
			// this is working
			// TODO: need to create a repo for HelloAspect! - mayby try @notations
			
			// knowing well android flow: contract is less exigent! onCreate -> check all services! 
			// contract says what you should do if resilience aspects fail!
			Toast.makeText(context, "Hurray!", Toast.LENGTH_LONG).show();
		}
	}
	after(Activity a): checkThis(a){
		
	}
	after(Activity a): checkCache(a) {
		System.out.println("Testing: Check Cache && acces to SearchCache from AOP");
	
		if(a != null) {
			System.out.println("A is not null: ");
			Context context = a.getApplicationContext();
			SharedPreferences sharedprf = context.getSharedPreferences("SearchCache",Context.MODE_PRIVATE);
			if(sharedprf != null){
				Map<String, ?> allEntries = sharedprf.getAll();
				for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
					System.out.println(entry.getKey()+" "+entry.getValue().toString());
				}		
			}
		}
	} */
}
