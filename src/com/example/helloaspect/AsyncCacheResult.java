package com.example.helloaspect;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

@Aspect
public class AsyncCacheResult {
	
	@Pointcut("execution(* *.getPlacesName(..)) && args(s,a)")
	void getPlacesName(String s, Activity a) {}

	@Pointcut("execution(* *.showPlaces(..)) && args(j,a)")
	    void storePlaces(JSONArray j, Activity a) {}
	
	@Pointcut("execution(* *.votePlace(..)) && args(nvl, four_id, i,a)")
    void votePlace(String nvl, String four_id, Integer i, Activity a) {}
	
	@Pointcut("execution(* *.afterVote(..)) && args(j,a)")
    void afterVote(JSONObject j, Activity a) {}
	
	@Pointcut("execution(* *.notGetPlaces(..)) && args(a)")
    void notGetPlaces(Activity a) {}
	
	@Around("notGetPlaces(a)")
	public String notGetPlaces(ProceedingJoinPoint thisJoinPoint, Activity a) throws UnknownHostException, IOException{
// LA FUNCION YA N EXISTE:: AQUI NO PONGAS ASPECTO SI NO CAMBIAS EL NOMBRE!
		// result of posting is null, check internet, and dependency-
		return "IMGOOD";
		
	}
	 
	@Around("afterVote(j,a)")
	public void afterVote(ProceedingJoinPoint thisJoinPoint, JSONObject j, Activity a) throws JSONException {
		if(a != null) {
			System.out.println("afterVote: Activity not null: ");
			Context context = a.getApplicationContext();
			SharedPreferences sharedprf = context.getSharedPreferences("VoteCache",Context.MODE_PRIVATE);
			if(sharedprf != null){
				SharedPreferences.Editor ed = sharedprf.edit();
				if(j != null){
					System.out.println("Meet j: "+j.toString());
					String key = j.getString("four_id");
					System.out.println("We are going to delte this: "+key);
					ed.remove(key);
					ed.commit();
					thisJoinPoint.proceed(); // guardada - before would do the job	
				}
				// si j is null we do not execute after vote!
			}
		}
		thisJoinPoint.proceed(); // we still execute the afterVote: for userInfo (next: start check on the connection)
	}
	
	@Around("votePlace(nvl,four_id, i,a)")
	public void votePlace(ProceedingJoinPoint thisJoinPoint, String nvl, String four_id, Integer i, Activity a) {
		if(a != null) {
			System.out.println("storeVote: Activity not null: ");
			Context context = a.getApplicationContext();
			SharedPreferences sharedprf = context.getSharedPreferences("VoteCache",Context.MODE_PRIVATE);
			if(sharedprf != null){
				SharedPreferences.Editor ed = sharedprf.edit();
				if(nvl != null && four_id!= null){
					System.err.println(" value: "+nvl);
					ed.putString(four_id, nvl);
					ed.commit();
					thisJoinPoint.proceed(); // guardada - before would do the job	
				}
			}
		}
	}
	
	@Around("storePlaces(j,a)")
		public void storePlaces(ProceedingJoinPoint thisJoinPoint, JSONArray j, Activity a ) {
	    	//System.out.println("Spike is here: ");
	    	if(a != null) {
				System.out.println("Activity and json not null: ");
				Context context = a.getApplicationContext();
				SharedPreferences sharedprf = context.getSharedPreferences("SearchCache",Context.MODE_PRIVATE);
				if(sharedprf != null){
					SharedPreferences.Editor ed = sharedprf.edit();
					Map<String, String> allEntries = (Map<String, String>) sharedprf.getAll();
					for (Map.Entry<String, String> entry : allEntries.entrySet()) {
						String key = entry.getKey();
						String value = entry.getValue();
						System.err.println("Map: "+key+' ');
						if(j != null){
							System.err.println(" value: "+j.toString());
							if("empty".equals(value)) {
								String newValue = j.toString();
								ed.putString(key, newValue);
								ed.commit();
								System.out.println("Cached info: "+j.toString());
							}	
						}
						else {
				    		// here we discover no places for the searchedName, so we need to delete entry in cache
				    		ed.remove(key); 
				    		ed.apply();
				    		ed.commit();
				    		
				    	}
						thisJoinPoint.proceed(); // no canviem els args, pero mostrem els resultats
					}					
				}
				else thisJoinPoint.proceed();
			}
		}
	 
   	@Around("getPlacesName(s,a)")
	    public JSONArray getPlacesName(ProceedingJoinPoint thisJoinPoint, String s, Activity a ) {
	    	System.out.println("Mike is back: ");
	    	try {
				JSONArray places = null;	
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
								// this might be, proceed, with new params!check cache, show markers?
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
							SharedPreferences.Editor editor = sharedprf.edit();
							editor.putString(s,"empty");
							editor.commit();
					    	thisJoinPoint.proceed(); // no canviem els args
					    	System.out.println("Despres de la crida a proceed, keep doing");
					    	
							
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
