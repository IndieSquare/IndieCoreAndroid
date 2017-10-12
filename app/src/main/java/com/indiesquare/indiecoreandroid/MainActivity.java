package com.indiesquare.indiecoreandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private String randomMessage;
    IndieCore ic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apiKey = "9dc6v7b6ba8262e153ca2df730d3c317";
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


    }


    public void createSend(View view) {
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


    }

    public void createOrder(View view) {
        ic.createOrder("12sWrxRY7E7Nhmuyjbz4TtGE9jRewGqEZD", "XCP", 1.0, "NETX", 10.0, 100, new IndieCore.CallbackObject() {
            @Override
            public void onFinished(IndieCoreError error, JSONObject result) {
                if (error != null) {
                    Log.i("TAG", "error" + error.message);
                } else {
                    Log.i("TAG", "response " + result);
                }
            }
        });


    }

    public void createCancel(View view) {
        ic.createCancel("1MZUJyKLHsSthHY3z68NxLFPhnrDcsPaDk", "ea8f9eeb36ed8c3001f4c572796d450514b19f108e3d3f35d8631004c5871fbf", new IndieCore.CallbackObject() {
            @Override
            public void onFinished(IndieCoreError error, JSONObject result) {
                if (error != null) {
                    Log.i("TAG", "error" + error.message);
                } else {
                    Log.i("TAG", "response " + result);
                }
            }
        });


    }

    public void createIssuance(View view) {
        ic.createIssuance("1NerXncPAkx4SUZpQPMoREiYyoKVRjjZJf", "NETX", 1000.0, true, new IndieCore.CallbackObject() {
            @Override
            public void onFinished(IndieCoreError error, JSONObject result) {
                if (error != null) {
                    Log.i("TAG", "error" + error.message);
                } else {
                    Log.i("TAG", "response " + result);
                }
            }
        });


    }

    public void createNumericTokenName(View view) {
        ic.createNumericTokenName(new IndieCore.CallbackObject() {
            @Override
            public void onFinished(IndieCoreError error, JSONObject result) {
                if (error != null) {
                    Log.i("TAG", "error" + error.message);
                } else {
                    Log.i("TAG", "response " + result);
                }
            }
        });


    }

    public void getAddressForPassphrase(View view) {
        ic.getAddressForPassphrase("dad drift further wrong pig tough barely daily anywhere twenty deadly anger", 0, new IndieCore.CallbackObject() {
            @Override
            public void onFinished(IndieCoreError error, JSONObject result) {
                if (error != null) {
                    Log.i("TAG", "error" + error.message);
                } else {
                    Log.i("TAG", "response " + result);
                }
            }
        });


    }

    public void signTransacion(View view) {
        ic.signTransaction("0100000001425fc12873a1a09c744ce7e782e95acc5cb69611d9f9b69550a45576383338f7020000001976a914edee861dff4de166683e4c54ae3869cd05c7ae0f88acffffffff0336150000000000001976a9141485d9d03b41aaa9dca7d70d7f63ff4a0826100e88ac00000000000000001e6a1cacd10644550a44ead1ce07effa7abcdd01911e197349b79630f48960b0561400000000001976a914edee861dff4de166683e4c54ae3869cd05c7ae0f88ac00000000", "dad drift further wrong pig tough barely daily anywhere twenty deadly anger", 0, "12sWrxRY7E7Nhmuyjbz4TtGE9jRewGqEZD", new IndieCore.CallbackObject() {
            @Override
            public void onFinished(IndieCoreError error, JSONObject result) {
                if (error != null) {
                    Log.i("TAG", "error" + error.message);
                } else {
                    Log.i("TAG", "response " + result);
                }
            }
        });


    }


    public void signTransacionNoDest(View view) {
        ic.signTransactionNoDest("0100000001425fc12873a1a09c744ce7e782e95acc5cb69611d9f9b69550a45576383338f7020000001976a914edee861dff4de166683e4c54ae3869cd05c7ae0f88acffffffff0336150000000000001976a9141485d9d03b41aaa9dca7d70d7f63ff4a0826100e88ac00000000000000001e6a1cacd10644550a44ead1ce07effa7abcdd01911e197349b79630f48960b0561400000000001976a914edee861dff4de166683e4c54ae3869cd05c7ae0f88ac00000000", "dad drift further wrong pig tough barely daily anywhere twenty deadly anger", 0, new IndieCore.CallbackObject() {
            @Override
            public void onFinished(IndieCoreError error, JSONObject result) {
                if (error != null) {
                    Log.i("TAG", "error" + error.message);
                } else {
                    Log.i("TAG", "response " + result);
                }
            }
        });


    }


    public void createPassphrase(View view) {
        ic.createNewWallet(new IndieCore.CallbackObject() {
            @Override
            public void onFinished(IndieCoreError error, JSONObject result) {
                if (error != null) {
                    Log.i("TAG", "error" + error.message);
                } else {
                    Log.i("TAG", "wallet is  " + result);
                }
            }
        });


    }

    public void generateRandomDetailedWallet(View view) {
        ic.generateRandomDetailedWallet(new IndieCore.CallbackObject() {
            @Override
            public void onFinished(IndieCoreError error, JSONObject result) {
                if (error != null) {
                    Log.i("TAG", "error" + error.message);
                } else {
                    Log.i("TAG", "wallet is  " + result);
                }
            }
        });


    }


    public void getBalances(View view) {

        ic.getBalances("1JynF1GgD279DBZxQBubJXz4NuHcTy65k3", new IndieCore.CallbackArray() {
            @Override
            public void onFinished(IndieCoreError error, JSONArray result) {
                if (error != null) {
                    Log.i("TAG", "error" + error.message);
                } else {
                    Log.i("TAG", "balances " + result);
                }
            }
        });

    }

    public void getHistory(View view) {

        ic.getHistory("1JynF1GgD279DBZxQBubJXz4NuHcTy65k3", new IndieCore.CallbackArray() {
            @Override
            public void onFinished(IndieCoreError error, JSONArray result) {
                if (error != null) {
                    Log.i("TAG", "error" + error.message);
                } else {
                    Log.i("TAG", "history " + result);
                }
            }
        });

    }

    public void getTokenInfo(View view) {

        ic.getTokenInfo("SARUTOBI", new IndieCore.CallbackObject() {
            @Override
            public void onFinished(IndieCoreError error, JSONObject result) {
                if (error != null) {
                    Log.i("TAG", "error" + error.message);
                } else {
                    Log.i("TAG", "token info is  " + result);
                }
            }
        });


    }

    public void getTokenDescription(View view) {

        ic.getTokenDescription("SARUTOBI", new IndieCore.CallbackObject() {
            @Override
            public void onFinished(IndieCoreError error, JSONObject result) {
                if (error != null) {
                    Log.i("TAG", "error" + error.message);
                } else {
                    Log.i("TAG", "token info is  " + result);
                }
            }
        });


    }

    public void getTokenHolders(View view) {

        ic.getTokenHolders("SARUTOBI", new IndieCore.CallbackArray() {
            @Override
            public void onFinished(IndieCoreError error, JSONArray result) {
                if (error != null) {
                    Log.i("TAG", "error" + error.message);
                } else {
                    Log.i("TAG", "token holders" + result);
                }
            }
        });


    }

    public void getTokenHistory(View view) {

        ic.getTokenHistory("SARUTOBI", new IndieCore.CallbackArray() {
            @Override
            public void onFinished(IndieCoreError error, JSONArray result) {
                if (error != null) {
                    Log.i("TAG", "error" + error.message);
                } else {
                    Log.i("TAG", "token history" + result);
                }
            }
        });


    }

    public void isValidBitcoinAddress(View view) {

        ic.checkIfAddressIsValid("1JynF1GgD279DBZxQBubJXz4NuHcTy65k3", new IndieCore.CallbackObject() {
            @Override
            public void onFinished(IndieCoreError error, JSONObject result) {
                if (error != null) {
                    Log.i("TAG", "error" + error.message);
                } else {
                    Log.i("TAG", "valid  " + result);
                }
            }
        });


    }

    public void signTransactionWithWallet(View view) {

        String unsignedTx = "0100000001425fc12873a1a09c744ce7e782e95acc5cb69611d9f9b69550a45576383338f7020000001976a914edee861dff4de166683e4c54ae3869cd05c7ae0f88acffffffff0336150000000000001976a9141485d9d03b41aaa9dca7d70d7f63ff4a0826100e88ac00000000000000001e6a1cacd10644550a44ead1ce07effa7abcdd01911e197349b796338f1fe0b0561400000000001976a914edee861dff4de166683e4c54ae3869cd05c7ae0f88ac00000000";
        ic.signTransactionWithWallet("MYURLSCHEME://link",unsignedTx, new IndieCore.CallbackObject() {
            @Override
            public void onFinished(IndieCoreError error, JSONObject result) {
                if (error != null) {
                    Log.i("TAG", "error" + error.message);
                } else {
                    Log.i("TAG", "sig " + result);
                }
            }
        });



    }

    public void getAddressFromWallet(View view) {

        ic.getAddressFromWallet("MYURLSCHEME://link", new IndieCore.CallbackObject() {
            @Override
            public void onFinished(IndieCoreError error, JSONObject result) {
                if (error != null) {
                    Log.i("TAG", "error" + error.message);
                } else {
                    Log.i("TAG", "address  " + result);
                }
            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
    /*
    public void linkIndiesquare(View view) {

        randomMessage = UUID.randomUUID().toString();

        PackageManager manager = getPackageManager();

        Intent i = manager.getLaunchIntentForPackage("inc.lireneosoft.counterparty");
        if (i == null) {
            Uri uri = Uri.parse("https://indiesquare.me"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            i.putExtra("source", "x-callback-url/getaddress?x-success=MYURLSCHEME://link&msg=" + randomMessage);
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            startActivity(i);
        }


    }*/

    public void verifyMessage(View view) {

        ic.verifyMessage("hello world","H6UZldKQkam8W4Q9D9ROnnKW/EWZIj1pNShEIvHWfIYVWN6dmxqgwyA4WIwDe1bQcnb8RzcYk0QON2kENanpOaA=","1gg14Fiz7uHoxAbAxkBaD2TYkFmGTu73Z", new IndieCore.CallbackObject() {
            @Override
            public void onFinished(IndieCoreError error, JSONObject result) {
                if (error != null) {
                    Log.i("TAG", "error" + error.message);
                } else {
                    Log.i("TAG", "verify  " + result);
                }
            }
        });

    }

    public void signMessage(View view) {

        ic.signMessage("hello world","dad drift further wrong pig tough barely daily anywhere twenty deadly anger",0, new IndieCore.CallbackObject() {
            @Override
            public void onFinished(IndieCoreError error, JSONObject result) {
                if (error != null) {
                    Log.i("TAG", "error" + error.message);
                } else {
                    Log.i("TAG", "sig  " + result);
                }
            }
        });

    }

    public void broadcast(View view) {

        String signedtx="0100000001bf1f87c5041063d8353f3d8e109fb11405456d7972c5f401308ced36eb9e8fea010000001976a914e1869fa1cec7741a502e7a5bd938ed8f5e354b5488acffffffff0200000000000000002e6a2c0b0b8cb664864cdf2ff70668595e63567b9d8ece36b2383513b6eeab7f1c15e70466593f13bb49618b8afe7079e93a00000000001976a914e1869fa1cec7741a502e7a5bd938ed8f5e354b5488ac00000000\"}' https://api.indiesquare.me/v2/transactions/broadcast";
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




}
