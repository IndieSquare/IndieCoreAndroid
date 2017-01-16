package com.indiesquare.indiecoreandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements IndieCore.Listener {

    IndieCore ic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ic = new IndieCore(this,this);
        ic.apiKey = "4d3dccf94abc3286cfa43b51d008ccb5";


    }



    public void createPassphrase(View view) {

        //ic.generateRandomDetailedWallet();
      //  ic.createNumericTokenName();
        //ic.broadcastTransaction("0100000001bf1f87c5041063d8353f3d8e109fb11405456d7972c5f401308ced36eb9e8fea010000001976a914e1869fa1cec7741a502e7a5bd938ed8f5e354b5488acffffffff0200000000000000002e6a2c0b0b8cb664864cdf2ff70668595e63567b9d8ece36b2383513b6eeab7f1c15e70466593f13bb49618b8afe7079e93a00000000001976a914e1869fa1cec7741a502e7a5bd938ed8f5e354b5488ac00000000");


        String unsignedTx = "010000000414ae2264cdbe754b9ae4be18d84bfeb4f578af553b9b9e4f9cb2303d04ee9e45000000001976a91458b6e991b45487df810f4d96d5315da739637f1788acffffffffec15d27b74516fefd921cecbe043ea63124d28a3903aef8fb1682ccc926b1c62000000001976a91458b6e991b45487df810f4d96d5315da739637f1788acffffffff9878f76e1424c1d1eeb6b15d06902dd8f0c78f9bdb61263e4ca3ae68c571a292000000001976a91458b6e991b45487df810f4d96d5315da739637f1788acfffffffffdac7f1c83b01a8924a8965d356b804c2608bc261fc18041116ddb4a143bc499000000001976a91458b6e991b45487df810f4d96d5315da739637f1788acffffffff0336150000000000001976a9141485d9d03b41aaa9dca7d70d7f63ff4a0826100e88ac00000000000000001e6a1c246698efc5d81b78ceadf3179316b5eb6cc5c2c347c0b7b42121a94e92180000000000001976a91458b6e991b45487df810f4d96d5315da739637f1788ac00000000";
       // signTransacation(String tx,String passphrase,int index, String destination){

        //ic.signTransacation(unsignedTx,"rule dew anyway leap ash guitar realize crowd serve play perfectly horizon",1,"12sWrxRY7E7Nhmuyjbz4TtGE9jRewGqEZD");

      //  ic.signTransacationNoDest(unsignedTx,"rule dew anyway leap ash guitar realize crowd serve play perfectly horizon",1);
       // ic.checkIfTokenExists("SARUTOBIsadfwefdads");

       // ic.issueToken("1Nh4tPtQjHZSoYdToTF7T3xbaKrTNKM3wP","KINK",1000000.0,false,"Kink token","http://www.mandelduck.com","http://is2.mzstatic.com/image/thumb/Purple3/v4/aa/ea/99/aaea9968-7bc0-9977-9251-68ab0bb08336/source/1200x630bf.jpg",10000);

        ic.getAddressForPassphrase("rule dew anyway leap ash guitar realize crowd serve play perfectly horizon",0);

    }



    public void didGetAddress(String address){
        Log.i("TAG","address is "+address);
    }

    public void didIssueToken(String response){
        Log.i("TAG","issue is "+response);
    }

    public void didSignTransaction(String response){
        Log.i("TAG","sign is "+response);
    }

    public void didBroadcastTransaction(String response){
        Log.i("TAG","broadcast is "+response);
    }

    public void didCreateNumericTokenName(String tokenName){
        Log.i("TAG","tokenName is "+tokenName);
    }
    public void didCreateDetailedWallet(String privateKey, String wif, String address, String publicKey){
        Log.i("TAG","privateKey is "+privateKey+" wif is "+wif+" address is "+address+" publicKey is "+ publicKey);
    }

    public void didCreateWallet(String passphrase){
        Log.i("TAG","passphrase is "+passphrase);
    }

    public void didCheckAddress(Boolean result){
        Log.i("TAG","result is "+result);
    }

    public void initialized() {
        Log.i("TAG", "indiecore initialized");

    }
 }
