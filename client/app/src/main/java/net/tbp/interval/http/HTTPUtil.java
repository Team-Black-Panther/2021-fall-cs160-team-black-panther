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
import net.tbp.interval.core.UserProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Singleton class for HTTP Calls. NOT FUNCTIONAL YET.
 */
public class HTTPUtil {

	private static HTTPUtil instance;
	private RequestQueue requestQueue;
	private static Context context;

	/**
	 *
	 * @return The request queue of the util instance.
	 */
	public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

	/**
	 * Add the HTTP request to the request queue. Do not use unless it's nessesary.
	 * @param req
	 * @param <T>
	 */
	public <T> void addToRequestQueue(Request<T> req) {
		getRequestQueue().add(req);
	}

	/**
	 * Private constructor for HTTPUtil.
	 * @param context
	 */
	private HTTPUtil(Context context) {
        this.context = context;
        this.requestQueue = getRequestQueue();
    }

	/**
	 *
	 * @param context
	 * @return The singleton instance of HTTPUtil. Use this for making HTTP calls.
	 */
	public static synchronized HTTPUtil getInstance(Context context) {
        if (instance == null) {
            instance = new HTTPUtil(context);
        }
        return instance;
    }


	// Basic User Management

	/**
	 *
	 * @param username
	 * @return The UID associated with the username.
	 */
	public Integer getUID(String username) {
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

	/**
	 *
	 * @param username
	 * @return The UID associated with the username after the database allocates it.
	 */
	public Integer allocateUIDToUser(String username) {
		return null;
	}

	/**
	 *
	 * @param uid
	 * @param newUserName
	 * @return The new username.
	 */
	public String updateUserName(Integer uid, String newUserName) {
		return null;
	}

	/**
	 *
	 * @param profile
	 * @return The UID of the newly added user in the database.
	 */
	public Integer addNewUser(UserProfile profile) {
		return null;
	}

	// Current Events

	/**
	 *
	 * @param uid
	 * @return All current events associated with the uid.
	 */
	public Iterable<Event> getAllCurrentEvents(Integer uid) {
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

	/**
	 * Add the provided event to the database.
	 * @param newEvent
	 * @param uid
	 * @return The event added.
	 */
	public Event addNewCurrentEvent(Event newEvent, Integer uid) {
		return null;
	}

	/**
	 * Update the event associated with the provided uid with information of newEvent.
	 *
	 * @param newEvent
	 * @param uid
	 * @param eventid
	 * @return The updated event.
	 */
	public Event updateCurrentEvent(Event newEvent, Integer uid, Integer eventid) {
		return null;
	}

	/**
	 * Delete the event associated with the eventid.
	 * @param uid
	 * @param eventid
	 */
	public void deleteCurrentEvent(Integer uid, Integer eventid) {
		
	}


}