package br.com.alfaconcursos;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.pushio.manager.PushIOManager;

import java.util.ArrayList;
import java.util.List;

public class PushIOManagerPlugin extends CordovaPlugin {
    private static final String TAG = "PushIOManagerPlugin";
    private static final String REGISTER_USER_ID = "registerUserId";
    private static final String UNREGISTER_ID = "unregisterId";
    private static final String GET_REGISTERED_USER_ID = "getRegisteredUserId";
    private static final String GET_UUID = "getUUID";
    private static final String GET_API_KEY = "getAPIKey";
    private static final String REGISTER_CATEGORIES = "registerCategories";
    private static final String UNREGISTER_CATEGORIES = "unregisterCategories";
    private static final String REGISTER_CATEGORY = "registerCategory";
    private static final String TRACK_ENGAGEMENT = "trackEngagement";
    private static final String RESET_EID = "resetEID";

    Context applicationContext;
    PushIOManager pushIOManager;

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        Log.d(TAG, "Initializing PushIOManagerPlugin");

        applicationContext = cordova.getActivity().getApplicationContext();
        pushIOManager = PushIOManager.getInstance(applicationContext);

        super.initialize(cordova, webView);
        pushIOManager.ensureRegistration();
    }

    public boolean execute(final String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (REGISTER_USER_ID.equals(action)) {
            registerUserId(args);
        } else if (UNREGISTER_ID.equals(action)) {
            unregisterId();
        } else if (GET_REGISTERED_USER_ID.equals(action)) {
            getRegisteredUserId(callbackContext);
        } else if (GET_UUID.equals(action)) {
            getUUID(callbackContext);
        } else if (GET_API_KEY.equals(action)) {
            getAPIKey(callbackContext);
        } else if (REGISTER_CATEGORIES.equals(action)) {
            registerCategories(args);
        } else if (UNREGISTER_CATEGORIES.equals(action)) {
            unregisterCategories(args);
        } else if (REGISTER_CATEGORY.equals(action)) {
            registerCategory(args);
        } else if (TRACK_ENGAGEMENT.equals(action)) {
            trackEngagement(args);
        } else if (RESET_EID.equals(action)) {
            resetEID();
        } else {
            // method not found
            return false;
        }

        return true;
    }

    public void registerUserId(final JSONArray args) throws JSONException{
        final String userId = args.getString(0);

        this.cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                pushIOManager.registerUserId(userId);
            }
        });
    }

    public void unregisterId() {
        this.cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                pushIOManager.unregisterUserId();
            }
        });
    }

    public void getRegisteredUserId(final CallbackContext callbackContext) {
        this.cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                String userId = pushIOManager.getRegisteredUserId();
                callbackContext.success(userId);
            }
        });
    }

    public void getUUID(final CallbackContext callbackContext) {
        this.cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                String UUID = pushIOManager.getUUID();;
                callbackContext.success(UUID);
            }
        });
    }

    public void getAPIKey(final CallbackContext callbackContext) {
        this.cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                String APIKey = pushIOManager.getAPIKey();
                callbackContext.success(APIKey);
            }
        });
    }

    public void registerCategories(final JSONArray args) throws JSONException {
        final List<String> categories = new ArrayList<String>();
        JSONArray categoriesArray = args.getJSONArray(0);

        for (int i = 0; i < categoriesArray.length(); i++) {
            categories.add(categoriesArray.getString(i));
        }

        this.cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                pushIOManager.registerCategories(categories, false);
            }
        });
    }

    public void unregisterCategories(final JSONArray args) throws JSONException {
        final List<String> categories = new ArrayList<String>();
        JSONArray categoriesArray = args.getJSONArray(0);

        for (int i = 0; i < categoriesArray.length(); i++) {
            categories.add(categoriesArray.getString(i));
        }

        this.cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                pushIOManager.unregisterCategories(categories);
            }
        });
    }

    public void registerCategory(final JSONArray args) throws JSONException {
        final String category = args.getString(0);

        this.cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                pushIOManager.registerCategory(category);
            }
        });
    }

    public void trackEngagement(final JSONArray args) throws JSONException {
        final int metric = args.getInt(0);

        this.cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                pushIOManager.trackEngagement(metric);
            }
        });
    }

    public void resetEID() {
        this.cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                pushIOManager.resetEID();
            }
        });
    }
}


