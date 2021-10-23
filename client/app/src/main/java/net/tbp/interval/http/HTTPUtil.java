package net.tbp.interval.http;

import android.app.Activity;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import net.tbp.interval.core.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Singleton utility class for making HTTP calls.
*/
public class HTTPUtil {

	private static HTTPUtil instance;
	private RequestQueue requestQueue;
	private static Context context;

	public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

	private HTTPUtil(Context context) {
        this.context = context;
        this.requestQueue = getRequestQueue();
    }

	public static synchronized HTTPUtil getInstance(Context context) {
        if (instance == null) {
            instance = new HTTPUtil(context);
        }
        return instance;
    }

	// Basic User Management
	public Integer getUID(String username, Activity activity) {
		String url = "";
		Integer returnValue = null;
		JSONObject returnObject;
		RequestFuture<JSONObject> future = RequestFuture.newFuture();
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, future, future);
		requestQueue.add(request);

		try {
			returnValue = future.get().getInt("uid");

		}  catch (InterruptedException e) {
   		// handle the error
 		} catch (ExecutionException e) {
 		  // handle the error
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return returnValue;
	}

	public Integer allocateUIDToUser(String username, Activity activity) {
		return null;
	}

	public String updateUserName(Integer uid, String newUserName, Activity activity) {
		return null;
	}

	public void addNewUser(Integer uid, Activity activity) {
		
	}

	// Current Events
	public Iterable<Event> getAllCurrentEvents(Integer uid, Activity activity) {
		String url = "";
		JSONArray returnArr;
		RequestFuture<JSONArray> future = RequestFuture.newFuture();
		JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, future, future);
		requestQueue.add(request);

		try {
			returnArr = future.get();
		}  catch (InterruptedException e) {
   		// handle the error
 		} catch (ExecutionException e) {
 		  // handle the error
		}
		
		return null;
	}

	public Event addNewCurrentEvent(Event newEvent, Integer uid, Activity activity) {
		return null;
	}

	public Event updateCurrentEvent(Event newEvent, Integer uid, Integer eventid, Activity activity) {
		return null;
	}

	public void deleteCurrentEvent(Integer uid, Integer eventid, Activity activity) {
		
	}


}