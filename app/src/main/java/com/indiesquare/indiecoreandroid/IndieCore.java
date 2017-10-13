package com.indiesquare.indiecoreandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.webkit.JavascriptInterface;
import android.webkit.URLUtil;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class IndieCore{


    //define callback interface
    public interface CallbackArray {

        void onFinished(IndieCoreError error, JSONArray result);
    }

    private CallbackArray cba;

    public interface CallbackObject {

        void onFinished(IndieCoreError error, JSONObject result);
    }

    private CallbackObject cbo;

    private IndieCore ic;


    WebView webview;
    Activity parent;
    public String apiKey;

    String base = "https://indiesquare.me";
    String baseUrl = "https://api.indiesquare.me/v2/";


    CallbackObject currentCallbackObject;
    CallbackArray currentCallbackArray;
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

    private class WebViewInterface {

        @JavascriptInterface
        public void onError(String error) {
            throw new Error(error);
        }
    }

    public IndieCore(Activity activity,String key,boolean testnet, final CallbackObject callback) {

    if(testnet){
        baseUrl = "https://apitestnet.indiesquare.me/v2/";
    }
        apiKey = key;
        parent = activity;
        webview = new WebView(activity);
        webview.getSettings().setJavaScriptEnabled(true);
        final IndieCore.IndieCoreInterface myJavaScriptInterface
                = new IndieCore.IndieCoreInterface(activity);
        webview.addJavascriptInterface(myJavaScriptInterface, "AndroidFunction");


        webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                loaded = true;
                JSONObject res = new JSONObject();
                try {
                    res.put("initialized","true");

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                callback.onFinished(null,res);


            }
        });

        webview.loadUrl("file:///android_asset/functions.html");
        webview.setBackgroundColor(Color.TRANSPARENT);


    }


    public void broadcast(final String signedTx, final CallbackObject callback) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("tx",signedTx);

        final String URL = baseUrl + "transactions/broadcast";

        postRequest(URL,params, new CallbackObject(){

            public void onFinished(IndieCoreError error, JSONObject result) {

                callback.onFinished(error,result);
            }
        });


    }


    public void createCancel(String source, String offerHash, int fee, int feePerKB,final CallbackObject callback ) {

        createCancelMaster(source, offerHash, fee, feePerKB, new CallbackObject(){

            public void onFinished(IndieCoreError error, JSONObject result) {

                callback.onFinished(error,result);
            }

        });

    }

    public void createCancel(String source, String offerHash,final CallbackObject callback ) {

        createCancelMaster(source, offerHash, -1, -1, new CallbackObject(){

            public void onFinished(IndieCoreError error, JSONObject result) {

                callback.onFinished(error,result);
            }

        });

    }

    private void createCancelMaster(String source, String offerHash, int fee, int feePerKB, final CallbackObject callback) {


        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("source", source);

        params.put("offer_hash", offerHash);


        if (feePerKB != -1) {
            params.put("fee_per_kb", feePerKB + "");
        }
        if (fee != -1) {
            params.put("fee", fee + "");
        }

        final String URL = baseUrl + "transactions/cancel";

        postRequest(URL,params, new CallbackObject(){

            public void onFinished(IndieCoreError error, JSONObject result) {

                callback.onFinished(error,result);
            }
        });




    }

    public void createOrder(String source, String getToken, Double getQuantity, String giveToken, Double giveQuantity, int expiration, int fee, int feePerKB,final CallbackObject callback ) {
        createOrderMaster(source, getToken, getQuantity, giveToken, giveQuantity, expiration, fee, feePerKB,new CallbackObject(){

            public void onFinished(IndieCoreError error, JSONObject result) {

                callback.onFinished(error,result);
            }

        });
    }

    public void createOrder(String source, String getToken, Double getQuantity, String giveToken, Double giveQuantity, int expiration,final CallbackObject callback ) {
        createOrderMaster(source, getToken, getQuantity, giveToken, giveQuantity, expiration, -1, -1,new CallbackObject(){

            public void onFinished(IndieCoreError error, JSONObject result) {

                callback.onFinished(error,result);
            }

        });
    }

    private void createOrderMaster(String source, String getToken, Double getQuantity, String giveToken, Double giveQuantity, int expiration, int fee, int feePerKB, final CallbackObject callback) {


        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("source", source);



        if ((giveQuantity == Math.floor(giveQuantity)) && !Double.isInfinite(giveQuantity)) {
            params.put("give_quantity", giveQuantity.intValue()+ "");

        }else{
            params.put("give_quantity", giveQuantity + "");
        }

        if ((getQuantity == Math.floor(getQuantity)) && !Double.isInfinite(getQuantity)) {
            params.put("get_quantity", getQuantity.intValue()+ "");

        }else{
            params.put("get_quantity", getQuantity + "");
        }


        params.put("expiration", expiration+ "");
        params.put("give_token", giveToken);
        params.put("get_token", getToken);

        if (feePerKB != -1) {
            params.put("fee_per_kb", feePerKB + "");
        }
        if (fee != -1) {
            params.put("fee", fee + "");
        }


        final String URL =  baseUrl + "transactions/order";

        postRequest(URL,params, new CallbackObject(){

            public void onFinished(IndieCoreError error, JSONObject result) {

                callback.onFinished(error,result);
            }
        });




    }

    public void createSend(String source, String tokenName, Double quantity, String destination, int fee, int feePerKB, final CallbackObject callback) {

        createSendMaster(source, tokenName, quantity, destination, fee, feePerKB,new CallbackObject(){

            public void onFinished(IndieCoreError error, JSONObject result) {

                callback.onFinished(error,result);
            }

        });

    }

    public void createSend(String source, String tokenName, Double quantity, String destination, final CallbackObject callback) {

        createSendMaster(source, tokenName, quantity, destination, -1, -1,new CallbackObject(){

            public void onFinished(IndieCoreError error, JSONObject result) {

                callback.onFinished(error,result);
            }

        });

    }


    private void createSendMaster(String source, String tokenName, Double quantity, String destination, int fee, int feePerKB, final CallbackObject callback) {



        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("source", source);
        if ((quantity == Math.floor(quantity)) && !Double.isInfinite(quantity)) {
            params.put("quantity", quantity.intValue()+ "");

        }else{
            params.put("quantity", quantity + "");
        }



        params.put("token", tokenName);
        params.put("destination", destination);
        if (feePerKB != -1) {
            params.put("fee_per_kb", feePerKB + "");
        }
        if (fee != -1) {
            params.put("fee", fee + "");
        }



        final String URL =  baseUrl + "transactions/send";

        postRequest(URL,params, new CallbackObject(){

            public void onFinished(IndieCoreError error, JSONObject result) {

                callback.onFinished(error,result);
            }
        });


    }

    public void createIssuance(String source, String tokenName, Double quantity, boolean divisible,final CallbackObject callback) {


       createIssuanceMaster(source,tokenName,quantity,divisible,-1,-1,new CallbackObject(){

            public void onFinished(IndieCoreError error, JSONObject result) {

                callback.onFinished(error,result);
            }
        });



    }

    public void createIssuance(String source, String tokenName, Double quantity, boolean divisible,int fee,final CallbackObject callback) {


        createIssuanceMaster(source,tokenName,quantity,divisible,fee,-1,new CallbackObject(){

            public void onFinished(IndieCoreError error, JSONObject result) {

                callback.onFinished(error,result);
            }
        });



    }

    public void createIssuance(String source, String tokenName, Double quantity, boolean divisible, int fee, int feePerKB,final CallbackObject callback) {


        createIssuanceMaster(source,tokenName,quantity,divisible,fee,feePerKB,new CallbackObject(){

            public void onFinished(IndieCoreError error, JSONObject result) {

                callback.onFinished(error,result);
            }
        });



    }



    private void createIssuanceMaster(String source, String tokenName, Double quantity, boolean divisible, int fee, int feePerKB,final CallbackObject callback) {


        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("source", source);
        if (divisible == true) {
            params.put("quantity", quantity + "");
        } else {
            params.put("quantity", quantity.intValue() + "");
        }

        params.put("token", tokenName);
        params.put("divisible", divisible + "");
        if (feePerKB != -1) {
            params.put("fee_per_kb", feePerKB + "");
        }
        if (fee != -1) {
            params.put("fee", fee + "");
        }



        final String URL = baseUrl + "transactions/issuance";

        postRequest(URL,params, new CallbackObject(){

            public void onFinished(IndieCoreError error, JSONObject result) {

                callback.onFinished(error,result);
            }
        });





    }



    public void createNumericTokenName(final CallbackObject callback) {
        if(!checkCanCall(callback)){
            return;
        }
        currentCallbackObject = callback;
        callJavaScript("createNumericTokenName");

    }

    public void signTransaction(String tx, String passphrase, int index, String destination,final CallbackObject callback) {
        if(!checkCanCall(callback)){
            return;
        }
        currentCallbackObject = callback;
        if (apiKey != null) {
            callJavaScript("signTransaction", passphrase, index + "", tx, destination, apiKey,baseUrl);
        } else {

        }
    }

    public void signTransactionNoDest(String tx, String passphrase, int index,final CallbackObject callback) {
        if(!checkCanCall(callback)){
            return;
        }
        currentCallbackObject = callback;
        if (apiKey != null) {
            callJavaScript("signTransactionNoDest", passphrase, index + "", tx, apiKey,baseUrl);
        }
    }

    public void createNewWallet(final CallbackObject callback) {

        if(!checkCanCall(callback)){
            return;
        }
        currentCallbackObject = callback;
        callJavaScript("createNewPassphrase");


    }

    public void getAddressForPassphrase(String passphrase, int index,final CallbackObject callback) {
        if(!checkCanCall(callback)){
            return;
        }
        currentCallbackObject = callback;
        callJavaScript("getAddressForPassphrase", passphrase, index + "");
    }




    public void getRequest(String url, final CallbackArray callback) {
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        callback.onFinished(null, response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {


                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data


                        IndieCoreError ic = new IndieCoreError();
                        ic.message = "error";
                        callback.onFinished(ic, null);

                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();

                        IndieCoreError ic = new IndieCoreError();
                        ic.message = "error";
                        callback.onFinished(ic, null);
                    }
                } else {
                    IndieCoreError ic = new IndieCoreError();
                    ic.message = "error";
                    callback.onFinished(ic, null);
                }


            }

        }


        ) {
            @Override

            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                if (apiKey != null) {
                    headers.put("X-Api-Key", apiKey);
                }
                return headers;
            }
        };

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

    public void getRequestObject(String url, final CallbackObject callback) {

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        callback.onFinished(null, response);

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


                        IndieCoreError ic = new IndieCoreError();
                        ic.message = error.getMessage();
                        callback.onFinished(ic, null);

                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();

                        IndieCoreError ic = new IndieCoreError();
                        ic.message =error.getMessage();
                        callback.onFinished(ic, null);
                    }
                } else {
                    IndieCoreError ic = new IndieCoreError();
                    ic.message = error.getMessage();
                    callback.onFinished(ic, null);
                }


            }

        }


        ) {
            @Override

            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                if (apiKey != null) {
                    headers.put("X-Api-Key", apiKey);
                }
                return headers;
            }
        };

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

    public void postRequest(String url, HashMap<String,String> params, final CallbackObject callback){

    JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {



                    callback.onFinished(null,response);
                   return;

                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
          VolleyLog.e("Error: ", error.getMessage());
            error.printStackTrace();


            NetworkResponse response = error.networkResponse;
            if (error instanceof ServerError && response != null) {
                try {
                    String res = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                    // Now you can use any deserializer to make sense of data

                    IndieCoreError ic = new IndieCoreError();
                    ic.message = res;
                    callback.onFinished(ic,null);



                } catch (UnsupportedEncodingException e1) {
                    // Couldn't properly decode data to string
                    e1.printStackTrace();
                }
            } else {

                IndieCoreError ic = new IndieCoreError();
                ic.message = error.getMessage();
                callback.onFinished(ic,null);
            }


        }

    }


    ) {
        @Override

        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/json; charset=utf-8");
            if (apiKey != null) {
                headers.put("X-Api-Key", apiKey);
            }
            return headers;
        }
    };

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
/*
-(void)signTransactionWithWallet:(NSString*)myUrlScheme andTx:(NSString *)unsignedTx andCompletion:(completionBlock)completionBlock{

    NSString* channel = [NSString stringWithFormat:@"indie-%@",[[NSProcessInfo processInfo] globallyUniqueString]];

    [self pubsub:channel andParams:[NSDictionary dictionaryWithObjectsAndKeys:@"sign",@"request",unsignedTx,@"unsigned_hex", nil] andUrlScheme:myUrlScheme andCompletion:^(NSError *error, NSDictionary *response) {

        completionBlock(error,response);

    }];
}
 */
    public void signTransactionWithWallet(String myUrlScheme, String unsignedTx, final CallbackObject callback){
        String channel = "indie-" + UUID.randomUUID().toString();


        HashMap<String, String> params = new HashMap<String, String>();
        params .put("request", "sign");
        params .put("unsigned_hex", unsignedTx);

        pubsub(channel,params, myUrlScheme, new CallbackObject(){

            public void onFinished(IndieCoreError error, JSONObject result) {

                callback.onFinished(error,result);
            }

        });


    }

    public void getAddressFromWallet(String myUrlScheme, final CallbackObject callback){
        final String channel = "indie-" + UUID.randomUUID().toString();


        HashMap<String, String> params = new HashMap<String, String>();
        params .put("request", "getaddress");


        pubsub(channel,params, myUrlScheme, new CallbackObject(){

            public void onFinished(IndieCoreError error, JSONObject result) {

                try {
                    if(result == null){
                        IndieCoreError ic = new IndieCoreError();
                        ic.message = "JSON Error";
                        callback.onFinished(ic,null);
                        return;
                    }
                    final JSONObject res = new JSONObject(result.getString("response"));
                    JSONObject data = res.getJSONObject("data");
                    final String address = data.getString("address");
                    String signature= data.getString("signature");

                        verifyMessage(channel,signature,address, new CallbackObject() {

                                    public void onFinished(IndieCoreError error, JSONObject result) {

                                         if(error != null){
                                              callback.onFinished(error,result);
                                             return;
                                         }
                                        try {
                                        if(result.getString("result").equals("true")){
                                            JSONObject res = new JSONObject();
                                            res.put("address",address);
                                            callback.onFinished(error,res);
                                        }
                                        }
                                        catch (JSONException e){
                                            IndieCoreError ic = new IndieCoreError();
                                            ic.message = "JSON Error";
                                            callback.onFinished(ic,null);
                                        }

                                    }
                                });



                }
                catch (JSONException e){
                    IndieCoreError ic = new IndieCoreError();
                    ic.message = "JSON Error";
                    callback.onFinished(ic,null);
                }


            }

        });


    }

    public void linkageWallet( HashMap<String,String> params, String channel, String MyUrlScheme){
        String request = params.get("request");
        String nonce = UUID.randomUUID().toString();

        HashMap<String, String> newParams = new HashMap<String, String>();
        newParams.put("channel", channel);
        newParams.put("nonce", nonce);
        newParams.put("x-success", MyUrlScheme);

        if(request.equals("sign")){
            newParams.put("unsigned_hex",params.get("unsigned_hex"));
        }

        JSONObject json = new JSONObject(newParams);
       try{
           String jsonString = json.toString(2);

           String urlParams = request+"?params="+jsonString;

           if(request.equals("getaddress") || request.equals("verifyuser")) {

              String xcallback_params = "channel="+channel+"&nonce="+nonce+"&x-success="+MyUrlScheme+"&msg="+channel;
               urlParams = "x-callback-url/"+request+"?"+xcallback_params;
           }


           PackageManager manager = parent.getPackageManager();

           Intent i = manager.getLaunchIntentForPackage("inc.lireneosoft.counterparty");

               i.putExtra("source",  urlParams);
               i.addCategory(Intent.CATEGORY_LAUNCHER);
           parent.startActivity(i);


       }
       catch (JSONException e){

       }


    }
    public void pubsub(final String channel,  final HashMap<String,String> params, final String urlScheme, final CallbackObject callback){
        HashMap<String, String> paramsNew = new HashMap<String, String>();
       paramsNew.put("channel", channel);

         String URL = base + "/pubsub/topic";

        postRequestXML(URL, paramsNew, new CallbackObject(){

            public void onFinished(IndieCoreError error, JSONObject result) {

                if(error != null) {
                    callback.onFinished(error, result);
                }

                linkageWallet(params,channel,urlScheme);

                HashMap<String, String> paramsNew = new HashMap<String, String>();
                paramsNew.put("channel", channel);
                paramsNew.put("type", "1");
                String URL = base + "/pubsub/subscribe";
                postRequestXML(URL, paramsNew, new CallbackObject() {

                    public void onFinished(IndieCoreError error, JSONObject result) {

                        callback.onFinished(error, result);

                    }

                });



            }
        });
    }

    public void postRequestXML(String url, final HashMap<String,String> params, final CallbackObject callback){

        StringRequest req = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject res = new JSONObject();
                        try {
                            res.put("response",response);

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }


                        callback.onFinished(null,res);
                        return;

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                error.printStackTrace();


                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data

                        IndieCoreError ic = new IndieCoreError();
                        ic.message = res;
                        callback.onFinished(ic,null);



                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    }
                } else {

                    IndieCoreError ic = new IndieCoreError();
                    ic.message = error.getMessage();
                    callback.onFinished(ic,null);
                }


            }

        }


        ) {
            @Override

            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

                return headers;
            }
            @Override
            protected Map<String,String> getParams(){


                return params;
            }
        };

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

    public void getTokenInfo(String token, final CallbackObject callback){

        getRequestObject(baseUrl+"tokens/"+token, new CallbackObject(){

            public void onFinished(IndieCoreError error, JSONObject result) {

                callback.onFinished(error,result);
            }
        });


    }

    public void getTokenHistory(String token, final CallbackArray callback){

        getRequest(baseUrl+"tokens/"+token+"/history", new CallbackArray(){

            public void onFinished(IndieCoreError error, JSONArray result) {

                callback.onFinished(error,result);
            }
        });


    }

    public void getTokenHolders(String token, final CallbackArray callback){

        getRequest(baseUrl+"tokens/"+token+"/holders", new CallbackArray(){

            public void onFinished(IndieCoreError error, JSONArray result) {

                callback.onFinished(error,result);
            }
        });


    }

    public void getTokenDescription(String token, final CallbackObject callback){

        getTokenInfo(token, new CallbackObject(){

            public void onFinished(IndieCoreError error, JSONObject result) {
                if(error != null){
                    callback.onFinished(error,result);
                    return;
                }

                try{

                    if(result.getString("description") != null){
                    String description = result.getString("description");
                        boolean isValid = URLUtil.isValidUrl( description );
                        if(isValid){

                            getRequestObject(description, new CallbackObject(){

                                public void onFinished(IndieCoreError error, JSONObject result) {

                                    callback.onFinished(error,result);
                                    return;

                                }
                            });




                        }else{
                            JSONObject res = new JSONObject();
                            try {
                                res.put("description",description);
                                callback.onFinished(null,res);
                                return;
                            } catch (JSONException e) {

                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                IndieCoreError ic = new IndieCoreError();
                                ic.message = "error returning description";
                                callback.onFinished(ic,null);
                                return;
                            }
                        }


                    }

                }
                catch (JSONException e){
                    IndieCoreError ic = new IndieCoreError();
                    ic.message = "error json exception";
                    callback.onFinished(ic,null);
                    return;
                }

            }
        });



    }


    public void getHistory(String source, final CallbackArray callback){

        getRequest(baseUrl+"addresses/"+source+"/history", new CallbackArray(){

            public void onFinished(IndieCoreError error, JSONArray result) {

                callback.onFinished(error,result);
            }
        });


    }

    public void getBalances(String source, final CallbackArray callback){

        getRequest(baseUrl+"addresses/"+source+"/balances", new CallbackArray(){

            public void onFinished(IndieCoreError error, JSONArray result) {

                callback.onFinished(error,result);
            }
        });


    }
    public void generateRandomDetailedWallet(final CallbackObject callback){
        if(!checkCanCall(callback)){
            return;
        }
        currentCallbackObject = callback;
        callJavaScript("createRandomDetailedWallet");


    }
    public void checkIfAddressIsValid(String address,final CallbackObject callback){
        if(!checkCanCall(callback)){
            return;
        }
        currentCallbackObject = callback;
        callJavaScript("isValidBitcoinAddress",address);
    }

    public void signMessage(String message, String passphrase, int index, final CallbackObject callback){
        if(!checkCanCall(callback)){
            return;
        }
        currentCallbackObject = callback;
        callJavaScript("signMessage",message,passphrase,index+"");
    }

    public void verifyMessage(String message,String signature,String address,final CallbackObject callback){
        if(!checkCanCall(callback)){
            return;
        }
        currentCallbackObject = callback;
        callJavaScript("verifyMessage",message,signature,address);
    }

    public boolean checkCanCall(CallbackObject callback){
        if(currentCallbackObject != null){
            IndieCoreError ic = new IndieCoreError();
            ic.message = "last callback not finished";
            callback.onFinished(ic,null);
            return false;
        }
        if(loaded == false){
            IndieCoreError ic = new IndieCoreError();
            ic.message = "IndieCore not initialized yet";
            callback.onFinished(ic,null);
            return false;
        }
        return true;
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

        webview.loadUrl(stringBuilder.toString());
    }

    public class IndieCoreInterface {
        Context mContext;

        IndieCoreInterface(Context c) {
            mContext = c;
        }
        @JavascriptInterface public void didSignTransaction(String response) {
            JSONObject res = new JSONObject();
            try {
                res.put("tx",response);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            currentCallbackObject.onFinished(null,res);
            currentCallbackObject = null;
            return;

        }
        @JavascriptInterface public void didCreateNumericTokenName(String tokenName) {
            JSONObject res = new JSONObject();
            try {
                res.put("result",tokenName);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            currentCallbackObject.onFinished(null,res);
            currentCallbackObject = null;
            return;

        }
        @JavascriptInterface public void addressChecked(String result) {
            JSONObject res = new JSONObject();
            try {
                res.put("result",result);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            currentCallbackObject.onFinished(null,res);
            currentCallbackObject = null;
            return;

        }

        @JavascriptInterface public void passphraseCreated(String msg) {

            JSONObject res = new JSONObject();
            try {
                res.put("passphrase",msg);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            currentCallbackObject.onFinished(null,res);
            currentCallbackObject = null;
            return;


        }

        @JavascriptInterface public void detailedWalletCreated(String privateKey,String wif,String address,String publicKey) {

            JSONObject res = new JSONObject();
            try {
                res.put("private_key",privateKey);
                res.put("wif",wif);
                res.put("address",address);
                res.put("public_key",publicKey);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            currentCallbackObject.onFinished(null,res);
            currentCallbackObject = null;
            return;


        }

        @JavascriptInterface public void getAddressForPassphrase(String address){
            JSONObject res = new JSONObject();
            try {
                res.put("address",address);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            currentCallbackObject.onFinished(null,res);
            currentCallbackObject = null;
            return;

        }

        @JavascriptInterface public void didVerifyMessage(String result){
            JSONObject res = new JSONObject();
            try {
                res.put("result",result);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            currentCallbackObject.onFinished(null,res);
            currentCallbackObject = null;
            return;

        }
        @JavascriptInterface public void didSignMessage(String sig){
            JSONObject res = new JSONObject();
            try {
                res.put("result",sig);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            currentCallbackObject.onFinished(null,res);
            currentCallbackObject = null;
            return;

        }


        @JavascriptInterface public void onError(String msg){

            IndieCoreError ic = new IndieCoreError();
            ic.message = msg;
            currentCallbackObject.onFinished(ic, null);
            currentCallbackObject = null;
            return;
        }






    }



}
