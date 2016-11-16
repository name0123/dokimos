package com.example.helloaspect;

import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

@Aspect
public class AsyncCacheResult {
	
	@Pointcut("execution(* *.getPlacesName(..)) && args(s,a)")
	    void getPlacesName(String s, Activity a) {}

	@Pointcut("execution(* *.showPlaces(..)) && args(j,a)")
	    void storePlaces(JSONArray j, Activity a) {}
	
	
	@Around("storePlaces(j,a)")
		public void storePlaces(ProceedingJoinPoint thisJoinPoint, JSONArray j, Activity a ) {
	    	System.out.println("Spike is here: ");
	    	if(a != null && j != null) {
				System.out.println("Activity and json not null: ");
				Context context = a.getApplicationContext();
				SharedPreferences sharedprf = context.getSharedPreferences("SearchCache",Context.MODE_PRIVATE);
				if(sharedprf != null){
					Map<String, String> allEntries = (Map<String, String>) sharedprf.getAll();
					for (Map.Entry<String, String> entry : allEntries.entrySet()) {
						String key = entry.getKey();
						String value = entry.getValue();
						System.out.println("Values: "+key+' '+value);
						if("empty".equals(value)) {
							SharedPreferences.Editor ed = sharedprf.edit();
							String newValue = j.toString();
							ed.putString(key, newValue);
							ed.commit();
						}
					}
				}
				System.out.println("Cached info: "+j.toString());
				thisJoinPoint.proceed(); // no canviem els args, pero mostrem els resultats
				
			}
		}
	 
   	@Around("getPlacesName(s,a)")
	    public JSONArray getPlacesName(ProceedingJoinPoint thisJoinPoint, String s, Activity a ) {
	    	System.out.println("Mike is here: ");
	    	try {
				JSONArray places = new JSONArray("[]");	
		    	boolean cachedSearch = false;
		    	if(a != null && !s.equals("")) {
					System.out.println("Activity is not null: ");
					Context context = a.getApplicationContext();
					SharedPreferences sharedprf = context.getSharedPreferences("SearchCache",Context.MODE_PRIVATE);
					if(sharedprf != null){
						Map<String, String> allEntries = (Map<String, String>) sharedprf.getAll();
						for (Map.Entry<String, String> entry : allEntries.entrySet()) {
							String key = entry.getKey();
							String value = entry.getValue();
							System.out.println("Values: "+key+' '+value);
							if(s.equals(key) && !"empty".equals(value)) {
								places = new JSONArray(value); 
								cachedSearch = true;
								return places;
							}
						}
					}
					System.out.println("Cache: "+cachedSearch);
					if(!cachedSearch){
						System.out.println("Correcte, keep doing");
						// no tenim en cache la direccio buscada (redundant if)
						try {
							// la busquem - en el proxim Aspecte guardem els resultats en el lloc vaule="empty"
							System.out.println("Abans de la crida a proced, keep doing");
					    	thisJoinPoint.proceed(); // no canviem els args
					    	System.out.println("Despres de la crida a proceed, keep doing");
					    	SharedPreferences.Editor editor = sharedprf.edit();
							editor.putString(s,"empty");
							editor.commit();
							
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} //using Java 5 autoboxing
					}
					
				}
	    	} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	return null;
	    } 
}
