# IndieCoreAndroid

# Description
Android SDK for IndieSquare API written in Java, further api documentation can be found [here](https://developer.indiesquare.me/)
 
# Installation

Download the indie_core-release.aar file from the releases section and add it to your project. You can also download the source code and build/run the example project in MainActivity.java (make sure apply plugin: 'com.android.application' is set the app gradle)

The included project contains the usage, (all results are logged in logcat)
 
# Basic Usage
Full usage can be seen in the example project included, specifically in the MainActivity.java (all results are logged in the console via logcat)
 

# Example Usage

## Create a send transcation and request signing on the users IndieSquare Wallet

First initialize IndieCore in your main viewcontroller and wait for callback
```
String apiKey = "9dc6v7b6ba8262e153ca2df730d3c317"; //use your own apikey if you have one
        ic = new IndieCore(this, apiKey, false, new IndieCore.CallbackObject() {
            @Override
            public void onFinished(IndieCoreError error, JSONObject result) {
                if (error != null) {
                    Log.i("TAG", "initiated");
                } else {
                    Log.i("TAG", "initiated");
                }
            }
        });
 ```
Create a send transaction
```
   ic.createSend("1965areciqapsuL2hsia2yKkRLfAsH1smG", "NETX", 1.1, "12sWrxRY7E7Nhmuyjbz4TtGE9jRewGqEZD", new IndieCore.CallbackObject() {
            @Override
            public void onFinished(IndieCoreError error, JSONObject result) {
                if (error != null) {
                    Log.i("TAG", "error" + error.message);
                } else {
                    Log.i("TAG", "response " + result);
                }
            }
        });
 ```
Sign transaction with users installed IndieSquare Wallet
You must add your apps urlscheme (represented by MYURLSCHEME) in your plist, so IndieSquare and return to your app after users authorizes the signature.

 ```  
  ic.signTransactionWithWallet("MYURLSCHEME",unsignedTx, new IndieCore.CallbackObject() {
            @Override
            public void onFinished(IndieCoreError error, JSONObject result) {
                if (error != null) {
                    Log.i("TAG", "error" + error.message);
                } else {
                    Log.i("TAG", "sig " + result);
                }
            }
        });
   ``` 
Broadcast signed transaction
```
  ic.broadcast(signedtx, new IndieCore.CallbackObject() {
            @Override
            public void onFinished(IndieCoreError error, JSONObject result) {
                if (error != null) {
                    Log.i("TAG", "error" + error.message);
                } else {
                    Log.i("TAG", "tx  " + result);
                }
            }
        });

    }
    
 ```
 
## Request user's IndieSquare wallet address and verify user owns address through message signature

You must add your apps urlscheme (represented by MYURLSCHEME) in your plist, so IndieSquare and return to your app after users authorizes address usage.

```
  ic.getAddressFromWallet("MYURLSCHEME", new IndieCore.CallbackObject() {
            @Override
            public void onFinished(IndieCoreError error, JSONObject result) {
                if (error != null) {
                    Log.i("TAG", "error" + error.message);
                } else {
                    Log.i("TAG", "address  " + result);
                }
            }
        });
```

# Further Reading

* [Official Project Documentation](https://developer.indiesquare.me)
