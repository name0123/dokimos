package com.example.helloaspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONArray;

@Aspect
public class AsyncCacheResult {
	
	 @Pointcut("execution(JSONArray *.doInBackground(..)) && args(o)")
	    void getPlacesName(Object[] o) {}

	    @Around("getPlacesName(o)")
	    public JSONArray getPlacesName(ProceedingJoinPoint thisJoinPoint, Object[] o) {
	    	System.out.println("Mike is here: "+o.length);
	    	boolean cachedSearch = false;
	    	return null;
	    	/*
	    	Activity a = 
	    	if(a != null && !s.equals("")) {
				System.out.println("Activity is not null: ");
				Context context = a.getApplicationContext();
				SharedPreferences sharedprf = context.getSharedPreferences("SearchCache",Context.MODE_PRIVATE);
				if(sharedprf != null){
					Map<String, ?> allEntries = sharedprf.getAll();
					for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
						System.out.println(entry.getKey()+" "+entry.getValue().toString());
						if(s.equals(entry.getKey())) {
							SharedPreferences.Editor editor = sharedprf.edit();
							editor.putString(s,"emplty");
							editor.commit();
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
				
			} */
	    } 
}
