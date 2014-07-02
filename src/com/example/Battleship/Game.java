package com.example.Battleship;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
public class Game extends BaseActivity {
    public static GameCell[][] gameGrid = new GameCell[11][11];
    View gameBoard = null;
    Activity customGrid = this;
    public static boolean gameStarted = false;
    Spinner shipSpinner;
    String[] shipsArray;
    Map<String, Integer> shipsMap = new HashMap<String, Integer>();
    ArrayAdapter<String> spinnerArrayAdapter;
    private static Paint painter;
    private static Canvas canvas;
    public int shipSize = shipsMap.keySet().size();
    Float myString;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        initializeApp();
        setContentView( R.layout.gameboard );
        gameBoard = findViewById( R.id.boardView );
        shipSpinner = (Spinner)findViewById( R.id.shipSpinner );
        gameStarted = false;
    }

    private void initializeApp() {
        for( int y = 0; y < 11; y++ ) {
            for( int x = 0; x < 11; x++ ) {
                gameGrid[x][y] = new GameCell();
            }
        }
        ChallengeComputer();
        GetShips();
    }

    public void GetShips() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth( loginUsername, loginPassword );
        String challengeUrl = "http://battlegameserver.com/api/v1/available_ships.json";
        client.get( challengeUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess( JSONObject ships ) {
                try {
                    Iterator iterate = ships.keys();
                    while( iterate.hasNext() ) {
                        String key = (String)iterate.next();
                        Integer value = ships.getInt( key );
                        shipsMap.put( key + "(" + value + ")", value );
                    }

                    int size = shipsMap.keySet().size();
                    shipsArray = new String[size];
                    shipsArray = shipsMap.keySet().toArray( new String[0] );

                    spinnerArrayAdapter = new ArrayAdapter<String>( getApplicationContext(),
                            android.R.layout.simple_spinner_item, shipsArray );
                    spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item ); // The drop down view
                    shipSpinner.setAdapter( spinnerArrayAdapter );

                } catch( Exception e ) {
                    e.printStackTrace();
                    toastIt( e.getLocalizedMessage() );
                }
            }

            @Override
            public void onFailure( int statusCode, Header[] headers, byte[] responseBody, Throwable error ) {
                try {
                    toastIt( new String( responseBody, "UTF-8" ) );
                } catch( Exception e ) {
                    e.printStackTrace();
                }
            }
        } );
    }

    public static void challengeComputerSuccess( JSONObject game ) {
        try {
            gameID = Integer.parseInt( game.getString( "game_id" ) );
        } catch( Exception e ) {
            e.printStackTrace();
        }
    }

    public void ChallengeComputer() {
        new BattleShipAPI( loginUsername, loginPassword ).challengeComputer();
    }

    public void onClickAddShip( View view ) {
        String selectedShip = shipSpinner.getSelectedItem().toString();

        myString = Float.parseFloat(selectedShip.substring((Math.max(0, selectedShip.length() - 2)), (Math.max(0, selectedShip.length() - 1))));
        toastIt("Ship size: " + myString);
        //drawShip( myString );

        shipsMap.remove(selectedShip);
        shipsArray = new String[shipSize];
        shipsArray = shipsMap.keySet().toArray( new String[0] );
        spinnerArrayAdapter = new ArrayAdapter<String>( getApplicationContext(),
                android.R.layout.simple_spinner_item, shipsArray );
        shipSpinner.setAdapter( spinnerArrayAdapter );
        if( shipSize == 0 ) {
            gameStarted = true;
            gameBoard.invalidate();
        }
    }

    public void drawShip(Float ship) {
        toastIt("Ship size: " + myString);
        painter = new Paint();
        painter.setStyle( Paint.Style.STROKE );
        painter.setColor( Color.WHITE );
        painter.setStrokeWidth( 10 );
        canvas.drawLine(0, ship, 0, ship, painter);

//cellHeight = 34
//cellWidth = 67
//North = y - ((cellHeight  * shipSize) + (border * (shipSize - 1))
//North = y - (34 * shipSize)
//South = y + ((cellHeight  * shipSize) + (border * (shipSize - 1))
//South = y + (34 * shipSize)
//East = x + ((cellWidth * shipSize) + (border * (shipSize - 1))
//East = x + (67 * shipSize)
//West = x - ((cellWidth * shipSize) + (border * (shipSize - 1))
        //West = x - (67 * shipSize)
    }

    public static float[] getRelativeCoords( Activity activity,
                                             MotionEvent e ) {
        View contentView = activity.getWindow().
                findViewById( Window.ID_ANDROID_CONTENT );
        return new float[] {
                e.getRawX() - contentView.getLeft(),
                e.getRawY() - contentView.getTop() };
    }

    @Override
    public boolean onTouchEvent( MotionEvent event ) {
        int eventAction = event.getAction();

        switch( eventAction ) {
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                float[] xy = getRelativeCoords( customGrid, event );

//cellHeight = 34
//cellWidth = 67
//North = y - ((cellHeight  * shipSize) + (border * (shipSize - 1))
//North = y - (34 * shipSize)
//South = y + ((cellHeight  * shipSize) + (border * (shipSize - 1))
//South = y + (34 * shipSize)
//East = x + ((cellWidth * shipSize) + (border * (shipSize - 1))
//East = x + (67 * shipSize)
//West = x - ((cellWidth * shipSize) + (border * (shipSize - 1))
                //West = x - (67 * shipSize)

                Point indicies = findXYIndexes( xy[0], xy[1] );
                Log.i( "TOUCH", "ix: " + indicies.x + " iy: " + indicies.y );
                gameGrid[indicies.x][indicies.y].setWaiting( true );
                gameBoard.invalidate();
                break;
        }
        return true;
    }

    Point findXYIndexes( float x, float y ) {
        int height = gameGrid[0][0].getCellHeight();
        int width = gameGrid[0][0].getCellWidth();
        int xo = Game.gameGrid[0][0].getViewOrigin().x;
        int yo = Game.gameGrid[0][0].getViewOrigin().y;
        return new Point( (int)( ( x - xo ) / width ),
                (int)( ( y - gridTop - yo ) / height ) );
    }
}