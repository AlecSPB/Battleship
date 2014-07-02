package com.example.Battleship;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class BaseActivity extends Activity {
    LinearLayout linearLayout;
    public static Gamer currentUser = null;
    public final int gridTop = 50;
    public static String loginUsername = "";
    public static String loginPassword = "";
    public static int gameID = 0;
    EditText editTextUser, editTextPassword;
    TextView textViewUser,textViewPassword, textViewShowUsers;
    Button buttonLogin, buttonShowUsers;
    String getUsersURL = "http://battlegameserver.com/users/index.json";
    String loginUrl = "http://battlegameserver.com/users/login.json";
    ProgressBar progressBar;
    String base64EncodedCredentials = "";
    List<String> userList = new ArrayList<String>();
    ListView listViewUsers;
    ArrayAdapter adapter = null;
    public enum ServerCommands {
        LOGIN, GET_USERS
    }


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(R.layout.main);
        initializeView();
        toastIt("Login hardcoded line 75 BaseActivity");
    }
    public void initializeView(){
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonShowUsers = (Button) findViewById(R.id.buttonShowUsers);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUser = (EditText) findViewById(R.id.editTextUser);
        textViewShowUsers = (TextView) findViewById(R.id.textViewUser);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listViewUsers = (ListView)findViewById( R.id.listViewUsers );
        textViewPassword = (TextView) findViewById(R.id.textViewPassword);
        textViewUser = (TextView) findViewById(R.id.textViewUser);
        editTextUser.setText("justkrys1@gmail.com");
        editTextPassword.setText("priscilla");


    }

    public void loginToServer(View view){
        loginUsername = editTextUser.getText().toString();
        loginPassword = editTextPassword.getText().toString() ;
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(loginUsername, loginPassword);
        client.get(loginUrl, new AsyncHttpResponseHandler(){

            @Override
            public void onProgress( int position, int length ) {
                Log.i("LOGIN", "position: " + position + " length: " + length);
                progressBar.setProgress( position );
            }

            @Override

            public void onSuccess(String response){
                Log.i("Login", response);
                try{
                    JSONObject user = new JSONObject(response);
                    Log.i("JSON", user.getString("first_name") + user.getString("last_name") + "\n");
                    currentUser = new Gamer(
                            user.getString("first_name"),
                            user.getString("last_name"),
                            user.getString("email"),
                            user.getBoolean("online"),
                            user.getBoolean("available"),
                            user.getBoolean("gaming"),
                            user.getString("avatar_name"),
                            user.getString("avatar_image"),
                            user.getInt("level"),
                            user.getInt("coins"),
                            user.getInt("battles_won"),
                            user.getInt("battles_lost"),
                            user.getInt("battles_tied"),
                            user.getInt("experience_points")
                    );
                    toastIt( "Login success! Users and preferences available!" );
                }catch(Exception e){
                    e.printStackTrace();
                    toastIt(e.getLocalizedMessage());
                }
                buttonShowUsers.setEnabled(true);
                buttonShowUsers.setVisibility(View.VISIBLE);
                listViewUsers.setVisibility(View.VISIBLE);

                textViewUser.setVisibility(View.INVISIBLE);
                textViewPassword.setVisibility(View.INVISIBLE);
                editTextUser.setVisibility(View.INVISIBLE);
                editTextPassword.setVisibility(View.INVISIBLE);
                buttonLogin.setVisibility(View.INVISIBLE);
                invalidateOptionsMenu();
            }

            @Override
            public void onFailure( int statusCode, Header[] headers, byte[] errorMsg, Throwable error ) {
                try {
                    Log.i( "LOGIN", errorMsg + " i:" + statusCode );
                    toastIt( "Connection Error: " + new String( errorMsg, "UTF-8" ) );
                } catch( Exception e ) {
                    e.printStackTrace();
                }
            }
        } );
    }

    public void getUsersOnClick(View v){
        progressBar.setVisibility(View.VISIBLE);
        ServerRequest sr = new ServerRequest(getUsersURL, BaseActivity.ServerCommands.GET_USERS);
        GetJSONAsync task = new GetJSONAsync();
        task.execute(new ServerRequest[]{sr});
    }

    private class GetJSONAsync extends AsyncTask<ServerRequest, Integer, ServerRequest> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override

        protected ServerRequest doInBackground(ServerRequest... params){
            ServerRequest sr = params[0];
            String usersUrl = params[0].getUrl();
            URL url = null;
            InputStream in = null;
            int progress = 0;
            HttpURLConnection urlConnection = null;
            String jsonData = null;
            try {
                url = new URL( usersUrl );
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestProperty("Authorization", "Basic" + base64EncodedCredentials);
                urlConnection.connect();
            }catch(IOException ioe){
                ioe.printStackTrace();
            }
            publishProgress( progress += 25 );
            try {
                if(urlConnection != null){
                    in = new BufferedInputStream(urlConnection.getInputStream());
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF8"), 8);
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null){
                    stringBuilder.append(line).append("\n");
                }
                in.close();
                jsonData = stringBuilder.toString();
            }catch(Exception e){
                e.printStackTrace();
                in = new BufferedInputStream( urlConnection.getErrorStream() );
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(
                            new InputStreamReader( in, "UTF8" ), 8 );
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while( ( line = reader.readLine() ) != null ) {
                        sb.append( line ).append( "\n" );
                    }
                    in.close();
                    params[0].setErrorString( sb.toString() );
                } catch( Exception e1 ) {
                    e1.printStackTrace();
                }
            }finally{
                urlConnection.disconnect();
            }

            publishProgress(progress += 50);

            Log.i("JSON", jsonData);
            params[0].setJsonDataResult(jsonData);
            return params[0];
        }
        @Override
        protected void onProgressUpdate( Integer... values ) {
            super.onProgressUpdate( values );
            progressBar.setProgress( values[0] );
        }
        @Override
        protected void onPostExecute( ServerRequest sr ) {
            super.onPostExecute( sr );
            String jsonData = sr.getJsonDataResult();

            switch( sr.getCommand() ) {

                case GET_USERS:
                    try {
                        JSONArray allUsers = new JSONArray( jsonData );
                        float numUsers = allUsers.length();
                        for( int i = 0; i < numUsers; i++ ) {
                            JSONObject user = (JSONObject)allUsers.get( i );
                            Log.i( "JSON", user.getString( "first_name" ) + " " + user.getString( "last_name" ) + "\n" );
                            userList.add( user.getString( "avatar_name" ) );
                        }
                        adapter = new ArrayAdapter( getApplicationContext(),
                                android.R.layout.simple_list_item_1, userList );
                        listViewUsers.setAdapter( adapter );

                    } catch( Exception e ) {
                        e.printStackTrace();
                        toastIt( e.getLocalizedMessage() );
                    }

                    progressBar.setVisibility( progressBar.getRootView().INVISIBLE );
                    break;

                case LOGIN:
                    if( sr.getErrorString() != null ) {
                        toastIt( "Login: " + sr.getErrorString() );
                    } else {
                        try {
                            JSONObject user = new JSONObject( jsonData );
                            Log.i( "JSON", user.getString( "first_name" ) + " " + user.getString( "last_name" ) + "\n" );
                            currentUser = new Gamer(
                                    user.getString( "first_name" ),
                                    user.getString( "last_name" ),
                                    user.getString( "email" ),
                                    user.getBoolean( "online" ),
                                    user.getBoolean( "available" ),
                                    user.getBoolean( "gaming" ),
                                    user.getString( "avatar_name" ),
                                    user.getString( "avatar_image" ),
                                    user.getInt( "level" ),
                                    user.getInt( "coins" ),
                                    user.getInt( "battles_won" ),
                                    user.getInt( "battles_lost" ),
                                    user.getInt( "battles_tied" ),
                                    user.getInt( "experience_points" )
                            );
                        } catch( Exception e ) {
                            e.printStackTrace();
                            toastIt( e.getLocalizedMessage() );
                        }

                        buttonShowUsers.setEnabled(true);
                        buttonShowUsers.setVisibility( View.VISIBLE );
                        textViewShowUsers.setVisibility(View.VISIBLE);
                        invalidateOptionsMenu();
                    }
                    break;

                default:
                    break;
            }
        }
    }

    public static Drawable LoadImageFromWeb( String name, String url ) {
        try {
            InputStream is = (InputStream)new URL( url ).getContent();
            return Drawable.createFromStream( is, name );
        } catch( Exception e ) {
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate( R.menu.mastermenu, menu );
        onPrepareMenuOptions(menu);
        return true;
    }

    public boolean onPrepareMenuOptions(Menu menu) {
        if(currentUser == null) {
            menu.findItem(R.id.goToLogin).setVisible(false);
            menu.findItem(R.id.goToGame).setVisible(false);
            menu.findItem(R.id.goToPreferences).setVisible(false);
        } else {
            menu.findItem(R.id.goToLogin).setVisible(true);
            menu.findItem(R.id.goToGame).setVisible(true);
            menu.findItem(R.id.goToPreferences).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch( item.getItemId() ) {

            case R.id.goToLogin:
                startActivity( new Intent( this, BaseActivity.class ) );
                break;
            case R.id.goToPreferences:
                startActivity( new Intent( this, Preferences.class ) );
                break;
            case R.id.goToGame:
                startActivity( new Intent( this, Game.class ) );
                break;
            default:
                return super.onOptionsItemSelected( item );
        }
        return true;
    }

    public void toastIt( String msg ) {
        Toast.makeText( getApplicationContext(), msg, Toast.LENGTH_SHORT ).show();
    }
}

