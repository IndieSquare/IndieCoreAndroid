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
        ic.apiKey = "9dc6v7b6ba8262e153ca2df730d3c317";


    }



    public void createPassphrase(View view) {
        ic.createNewWallet();
        //ic.generateRandomDetailedWallet();
      //  ic.createNumericTokenName();
        //ic.broadcastTransaction("01000000017d1e39debaf303e37c8e72f025af208c5b110434e61dfbaff33d9c1775229c0a020000006a473044022063366614a11db1d3c549e3894acec4e45a75c964a3d468a63227d64f1b9422b3022007b2670d39f8ea102011d552c74ad33303a08b84ae4b4bda098e135c5799203a012102eecbf9551f08fd1518408d7ea5a0234d834e97393cf549fb9089b3d53c1a0c26ffffffff0200000000000000004d6a4beca7155f5d64e252c4b4bfe35669f4778cdeecbd9ecd093e188472ebf4b646f7d75c537ca2eb43a360942e205406817f10a5887c7d8b88b4f24501847f38904ee823348240210178b0477b66d30b00000000001976a914edee861dff4de166683e4c54ae3869cd05c7ae0f88ac00000000");


      //  String unsignedTx = "01000000017d1e39debaf303e37c8e72f025af208c5b110434e61dfbaff33d9c1775229c0a020000001976a914edee861dff4de166683e4c54ae3869cd05c7ae0f88acffffffff0200000000000000004d6a4beca7155f5d64e252c4b4bfe35669f4778cdeecbd9ecd093e188472ebf4b646f7d75c537ca2eb43a360942e205406817f10a5887c7d8b88b4f24501847f38904ee823348240210178b0477b66d30b00000000001976a914edee861dff4de166683e4c54ae3869cd05c7ae0f88ac00000000";
       // signTransaction(String tx,String passphrase,int index, String destination){

        //ic.signTransaction(unsignedTx,"rule dew anyway leap ash guitar realize crowd serve play perfectly horizon",1,"12sWrxRY7E7Nhmuyjbz4TtGE9jRewGqEZD");

       //ic.signTransactionNoDest(unsignedTx,"rule dew anyway leap ash guitar realize crowd serve play perfectly horizon",0);
       // ic.checkIfTokenExists("SARUTOBIsadfwefdads");

    //    ic.issueToken("1Nh4tPtQjHZSoYdToTF7T3xbaKrTNKM3wP","STBKINK",1000000.0,false,"Kink token","http://www.mandelduck.com","http://is2.mzstatic.com/image/thumb/Purple3/v4/aa/ea/99/aaea9968-7bc0-9977-9251-68ab0bb08336/source/1200x630bf.jpg",10000);

       // ic.getAddressForPassphrase("rule dew anyway leap ash guitar realize crowd serve play perfectly horizon",0);


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
