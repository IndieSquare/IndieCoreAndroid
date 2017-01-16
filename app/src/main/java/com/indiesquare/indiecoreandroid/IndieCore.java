package com.indiesquare.indiecoreandroid;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by a on 16/01/2017.
 */

public class IndieCore {
    public interface Listener {

        void didCreateWallet(String passphrase);
        void didCheckAddress(Boolean result);
        void didCreateNumericTokenName(String name);
        void didCreateDetailedWallet(String privateKey, String wif, String address, String publicKey);
        void didBroadcastTransaction(String response);
        void didSignTransaction(String response);
        void didIssueToken(String response);
        void didGetAddress(String address);
        void initialized();

    }
    private Listener ds;
    WebView webview;
    Activity parent;
    public String apiKey;



    String sourceMaster;
    String tokenNameMaster;
    Double quantityMaster;
    boolean divisibleMaster;
    String descriptionMaster;
    String websiteURLMaster;
    String imageURLMaster;
    int feeMaster;
    int feePerKBMaster;
    boolean loaded;

    private class WebViewInterface{

        @JavascriptInterface
        public void onError(String error){
            throw new Error(error);
        }
    }

    public IndieCore(Activity activity,Listener datasource) {
        ds = datasource;

        parent = activity;
        webview = new WebView(activity);
        //webview.setVisibility(View.INVISIBLE);
        webview.getSettings().setJavaScriptEnabled(true);
        final IndieCore.IndieCoreInterface myJavaScriptInterface
                = new IndieCore.IndieCoreInterface(activity);
        webview.addJavascriptInterface(myJavaScriptInterface, "AndroidFunction");


        webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                ds.initialized();
               loaded = true;
            }
        });

       // webview.addJavascriptInterface(new IndieCore.WebViewInterface(), "AndroidErrorReporter");


        webview.loadUrl("file:///android_asset/functions.html");
        webview.setBackgroundColor(Color.TRANSPARENT);



    }


    public void broadcastTransaction(final String signedTx){


        final String URL = "https://api.indiesquare.me/v2/transactions/broadcast";
        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("tx", signedTx);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                            ds.didBroadcastTransaction(response.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.e("Error: ", error.getMessage());
                error.printStackTrace();
                Log.d("abd", "Error: " + error
                        + ">>" + error.networkResponse.statusCode
                        + ">>" + error.networkResponse.data
                        + ">>" + error.getCause()
                        + ">>" + error.getMessage());

                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data

                        ds.didBroadcastTransaction(res);
                        JSONObject obj = new JSONObject(res);


                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }else{
                    ds.didBroadcastTransaction("error");
                }


            }

        }



        ){ @Override

        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/json; charset=utf-8");
            if(apiKey != null){
                headers.put("X-Api-Key",apiKey);
            }
            return headers;
        }};

        RequestQueue requestQueue = Volley.newRequestQueue(parent);
        requestQueue.add(req);





    }


    public void issueToken(String source, String tokenName, Double quantity, boolean divisible){
        if(loaded == false){
            ds.didIssueToken("error: IndieCore not initialized yet");
        }
        sourceMaster = source;
        tokenNameMaster = tokenName;
        quantityMaster = quantity;
        divisibleMaster = divisible;
        descriptionMaster = null;
        websiteURLMaster = null;
        imageURLMaster = null;
        feeMaster = -1;
        feePerKBMaster = -1;

        checkIfTokenExists(tokenName);



    }

    public void issueToken(String source, String tokenName, Double quantity, boolean divisible, String description){
        if(loaded == false){
            ds.didIssueToken("error: IndieCore not initialized yet");
        }
        sourceMaster = source;
        tokenNameMaster = tokenName;
        quantityMaster = quantity;
        divisibleMaster = divisible;
        descriptionMaster = description;
        websiteURLMaster = null;
        imageURLMaster = null;
        feeMaster = -1;
        feePerKBMaster = -1;

        checkIfTokenExists(tokenName);



    }

    public void issueToken(String source, String tokenName, Double quantity, boolean divisible, String description, String websiteURL){
        if(loaded == false){
            ds.didIssueToken("error: IndieCore not initialized yet");
        }
        sourceMaster = source;
        tokenNameMaster = tokenName;
        quantityMaster = quantity;
        divisibleMaster = divisible;
        descriptionMaster = description;
        websiteURLMaster = websiteURL;
        imageURLMaster = null;
        feeMaster = -1;
        feePerKBMaster = -1;

        checkIfTokenExists(tokenName);



    }

    public void issueToken(String source, String tokenName, Double quantity, boolean divisible, String description, String websiteURL, String imageURL){
        if(loaded == false){
            ds.didIssueToken("error: IndieCore not initialized yet");
        }
        sourceMaster = source;
        tokenNameMaster = tokenName;
        quantityMaster = quantity;
        divisibleMaster = divisible;
        descriptionMaster = description;
        websiteURLMaster = websiteURL;
        imageURLMaster = imageURL;
        feeMaster = -1;
        feePerKBMaster = -1;

        checkIfTokenExists(tokenName);



    }
    public void issueToken(String source, String tokenName, Double quantity, boolean divisible, String description, String websiteURL, String imageURL, int fee){
        if(loaded == false){
            ds.didIssueToken("error: IndieCore not initialized yet");
        }
        sourceMaster = source;
        tokenNameMaster = tokenName;
        quantityMaster = quantity;
        divisibleMaster = divisible;
        descriptionMaster = description;
        websiteURLMaster = websiteURL;
        imageURLMaster = imageURL;
        feeMaster = fee;
        feePerKBMaster = -1;

        checkIfTokenExists(tokenName);



    }

    public void issueToken(String source, String tokenName, Double quantity, boolean divisible, String description, String websiteURL, String imageURL, int fee, int feePerKB){

        if(loaded == false){
            ds.didIssueToken("error: IndieCore not initialized yet");
        }

        sourceMaster = source;
        tokenNameMaster = tokenName;
        quantityMaster = quantity;
        divisibleMaster = divisible;
        descriptionMaster = description;
        websiteURLMaster = websiteURL;
        imageURLMaster = imageURL;
        feeMaster = fee;
        feePerKBMaster = feePerKB;

        checkIfTokenExists(tokenName);



    }
    private void createIssuanceTransaction(String source, String tokenName, Double quantity, boolean divisible, String description, int fee, int feePerKB){


        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("source", source);
        if(divisible == true){
            params.put("quantity", quantity+"");
        }else{
            params.put("quantity", quantity.intValue()+"");
        }

        params.put("token", tokenName);
        params.put("divisible", divisible+"");
        if(feePerKB != -1){
            params.put("fee_per_kb",feePerKB+"");
        }
        if(fee != -1){
            params.put("fee",fee+"");
        }
        if(description != null){
            params.put("description", description+"");
        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,"https://api.indiesquare.me/v2/transactions/issuance", new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        ds.didIssueToken(response.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.e("Error: ", error.getMessage());
                error.printStackTrace();

                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data


                        ds.didIssueToken("error"+res);


                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                        ds.didIssueToken("error");
                    }
                }else{
                    ds.didIssueToken("error");
                }


            }

        }



        ){ @Override

        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/json; charset=utf-8");
            if(apiKey != null){
                headers.put("X-Api-Key",apiKey);
            }
            return headers;
        }};

        req.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(parent);
        requestQueue.add(req);



    }
    private void createEnhancedAssetInfo(String token, String description, String websiteUrl, String imageUrl){


        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("description", description);
        params.put("website", websiteUrl);
        params.put("image", imageUrl);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,"https://api.indiesquare.me/v2/files/enhancedtokeninfo/"+token, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("TAG","enha ok"+response.toString());

                        createIssuanceTransaction(sourceMaster,tokenNameMaster,quantityMaster,divisibleMaster,descriptionMaster,feeMaster,feePerKBMaster);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.e("Error: ", error.getMessage());
                error.printStackTrace();


                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data



                        ds.didIssueToken(res);

                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                        ds.didIssueToken("error");
                    }
                }else{
                    ds.didIssueToken("error");
                }


            }

        }



        ){ @Override

        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/json; charset=utf-8");
            if(apiKey != null){
                headers.put("X-Api-Key",apiKey);
            }
            return headers;
        }};

        req.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(parent);
        requestQueue.add(req);



    }
    private void checkIfTokenExists(String token){

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,"https://api.indiesquare.me/v2/tokens/"+token,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("TAG", "token check ok" + response.toString());
                        ds.didIssueToken("error: token already exists");

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.e("Error: ", error.getMessage());
                error.printStackTrace();

                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data



                        if(descriptionMaster == null && websiteURLMaster == null && imageURLMaster == null) {
                            createIssuanceTransaction(sourceMaster,tokenNameMaster,quantityMaster,divisibleMaster,descriptionMaster,feeMaster,feePerKBMaster);
                        }else{
                            createEnhancedAssetInfo(tokenNameMaster,descriptionMaster,websiteURLMaster,imageURLMaster);
                        }


                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                        ds.didIssueToken("error");
                    }
                }else{
                    ds.didIssueToken("error");
                }


            }

        }



        ){ @Override

        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/json; charset=utf-8");
            if(apiKey != null){
                headers.put("X-Api-Key",apiKey);
            }
            return headers;
        }};

        req.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(parent);
        requestQueue.add(req);



    }
    public void createNumericTokenName(){
        if(loaded == false){
            ds.didCreateNumericTokenName("error: IndieCore not initialized yet");
        }
        callJavaScript("createNumericTokenName");

    }

    public void signTransaction(String tx,String passphrase,int index, String destination){
        if(loaded == false){
            ds.didSignTransaction("error: IndieCore not initialized yet");
        }
        if(apiKey != null) {
            callJavaScript("signTransaction", passphrase, index + "", tx, destination, apiKey);
        }
        else{

        }
    }

    public void signTransactionNoDest(String tx,String passphrase,int index){
        if(loaded == false){
            ds.didSignTransaction("error: IndieCore not initialized yet");
        }
        if(apiKey != null) {
            callJavaScript("signTransactionNoDest", passphrase, index+"", tx, apiKey);
        }else{

        }
    }

    public void createNewWallet(){

        if(loaded == false){
            ds.didCreateWallet("error: IndieCore not initialized yet");
        }
        callJavaScript("createNewPassphrase");


    }
    public void getAddressForPassphrase(String passphrase, int index){
        if(loaded == false){
            ds.didGetAddress("error: IndieCore not initialized yet");
        }
        callJavaScript("getAddressForPassphrase",passphrase,index+"");
    }
    public void generateRandomDetailedWallet(){

        if(loaded == false){
            ds.didCreateDetailedWallet("error: IndieCore not initialized yet","error: IndieCore not initialized yet","error: IndieCore not initialized yet","error: IndieCore not initialized yet");
        }
        callJavaScript("createRandomDetailedWallet");


    }
    public void checkIfAddressIsValid(String address){
        if(loaded == false){
            ds.didCheckAddress(false);
        }
       // webview.loadUrl("javascript:isValidBitcoinAddress('"+address+"')");
        callJavaScript("isValidBitcoinAddress",address);
    }

    private void callJavaScript(String methodName, Object...params){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("javascript:try{");
        stringBuilder.append(methodName);
        stringBuilder.append("(");
        for (int i = 0; i < params.length; i++) {
            Object param = params[i];
            if(param instanceof String){
                stringBuilder.append("'");
                stringBuilder.append(param);
                stringBuilder.append("'");
            }
            if(i < params.length - 1){
                stringBuilder.append(",");
            }
        }

        stringBuilder.append(")}catch(error){AndroidFunction.onError(error.message);}");
        //Log.e("TAG","string:"+stringBuilder.toString());
        webview.loadUrl(stringBuilder.toString());
    }

    public class IndieCoreInterface {
        Context mContext;

        IndieCoreInterface(Context c) {
            mContext = c;
        }
        @JavascriptInterface public void didSignTransaction(String response) {
            ds.didSignTransaction(response);

        }
        @JavascriptInterface public void didCreateNumericTokenName(String tokenName) {
            ds.didCreateNumericTokenName(tokenName);

        }
        @JavascriptInterface public void addressChecked(String result) {
            if(result.equals("true")) {
                ds.didCheckAddress(true);
            }
            else{
                ds.didCheckAddress(false);
            }

        }

        @JavascriptInterface public void passphraseCreated(String msg) {
            ds.didCreateWallet(msg);

        }

        @JavascriptInterface public void detailedWalletCreated(String privateKey,String wif,String address,String publicKey) {
            ds.didCreateDetailedWallet(privateKey,wif,address,publicKey);

        }

        @JavascriptInterface public void getAddressForPassphrase(String address){
            ds.didGetAddress(address);
        }

        @JavascriptInterface public void onError(String msg){

            Log.e("tag","error: "+msg);
        }






    }

}
