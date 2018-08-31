package com.harish.test.project.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.harish.test.project.BuildConfig;
import com.harish.test.project.entities.NewsArticle;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class APIHelper {

    private static final String BASE_URL = "https://api.nytimes.com/svc/search/v2";
    private static final String BASE_URL_IMAGES = "https://www.nytimes.com";
    private static final String API_KEY = "d31fe793adf546658bd67e2b6a7fd11a";
    private static final String URL_NEWS_ARTICLES = BASE_URL + "/articlesearch.json";

    public static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static GsonBuilder gsonBuilder = new GsonBuilder();
    public static Gson gson;

    public static void init() {
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
        gson = gsonBuilder.create();
    }

    public static void getAdCampaignsInBackground(Context context, String keyword, int page, final TaskListener listener) {
        if (!isNetworkAvailable(context)) {
            listener.onConnectionError();
            return;
        }
        try {
            RequestParams params = new RequestParams();
            params.add("api-key", API_KEY);
            params.add("page", page + "");
            params.add("sort", "newest");
            if (Utils.isValid(keyword)) {
                params.add("q", keyword);
            }
            getHTTPClient().get(URL_NEWS_ARTICLES, params, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String response) {
                    try {
                        JSONObject responseJson = new JSONObject(response);
                        String status = responseJson.getString("status");
                        if ("OK".equalsIgnoreCase(status)) {
                            JSONObject data = responseJson.getJSONObject("response");
                            JSONArray docs = data.getJSONArray("docs");
                            List<NewsArticle> newsArticles = new ArrayList<>();
                            for (int i = 0; i < docs.length(); i++) {
                                String dataString = docs.getJSONObject(i).toString();
                                NewsArticle adCampaign = gson.fromJson(dataString, NewsArticle.class);
                                newsArticles.add(adCampaign);
                            }
                            listener.onTaskDone(newsArticles);
                        } else {
                            String message = responseJson.optString("message", "Unknown Error");
                            listener.onTaskFailed(message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        listener.onTaskFailed("Unknown Error");
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
                    handleCommonFailure(response, throwable, listener);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            listener.onTaskFailed(e.getMessage());
        }
    }

    private static void handleCommonFailure(String response, Throwable throwable, TaskListener listener) {
        if (listener == null)
            return;
        try {
            JSONObject responseJson = new JSONObject(response);
            String message = responseJson.getString("message");
            listener.onTaskFailed(message);
        } catch (Exception e) {
            if (throwable != null) {
                listener.onTaskFailed(throwable.getMessage());
            } else {
                listener.onTaskFailed("Unknown Error!");
            }
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        try {
            if (context == null)
                return true;
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (Exception e) {
            return true;
        }
    }

    public static String getImageUrl(String path) {
        if (Utils.isValid(path)) {
            return BASE_URL_IMAGES + "/" + path + "?api-key=" + API_KEY;
        }
        return "";
    }

    ////////////////////////////////////////////

    private static AsyncHttpClient client;

    private static AsyncHttpClient getHTTPClient() {
        if (client == null) {
            client = new AsyncHttpClient();
            client.setLoggingEnabled(BuildConfig.DEBUG);
            //client.setMaxConnections(10);
            try {
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);
                MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
                sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                client.setSSLSocketFactory(sf);
            } catch (Exception e) {
                e.printStackTrace();
            }
            client.setTimeout(5000);
        }
        return client;
    }

    ////////////////////////////////////////////

    public interface TaskListener {
        void onTaskDone(Object object);

        void onTaskFailed(String problem);

        void onConnectionError();
    }
}
