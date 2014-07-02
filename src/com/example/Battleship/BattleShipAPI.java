package com.example.Battleship;

import com.loopj.android.http.*;
import org.apache.http.*;
import org.json.*;

public class BattleShipAPI extends BaseActivity {
    final String loginUrl = "http://battlegameserver.com/api/v1/login.json";
    String userName, userPassword;
    BattleShipAPI( String _userName, String _password ) {
        userName = _userName;
        userPassword = _password;
    }

    public void challengeComputer() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth( loginUsername, loginPassword );
        String challengeUrl = "http://battlegameserver.com/api/v1/challenge_computer.json";
        client.get( challengeUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess( JSONObject game ) {
                Game.challengeComputerSuccess( game );
            }

            @Override
            public void onFailure( int statusCode, Header[] headers, byte[] responseBody, Throwable error ) {
                apiFailure( statusCode, headers, responseBody, error );
            }
        } );
    }

    public void apiFailure( int statusCode, Header[] headers, byte[] responseBody, Throwable error ) {
        try {
            toastIt( new String( responseBody, "UTF-8" ) );
        } catch( Exception e ) {
            e.printStackTrace();
        }
    }
}
