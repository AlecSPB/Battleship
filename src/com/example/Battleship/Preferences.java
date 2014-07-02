package com.example.Battleship;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Preferences extends BaseActivity {
    TextView editTextAvatar, editTextFirst, editTextLast, editTextEmail;
    TextView textViewXP, textViewLevel, textViewCoins, textViewOnline, textViewAvailable, textViewGaming, textViewWon, textViewLost, textViewTied;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.preferences );

        initializeApp();
    }

    private void initializeApp() {
        editTextAvatar = (TextView)findViewById( R.id.editTexttAvatar );
        editTextFirst = (TextView)findViewById( R.id.editTextFirst );
        editTextLast = (TextView)findViewById( R.id.editTextLast );
        editTextEmail = (TextView)findViewById( R.id.editTextEmail );

        textViewXP = (TextView)findViewById( R.id.textViewXP );
        textViewLevel = (TextView)findViewById( R.id.textViewLevel );
        textViewCoins = (TextView)findViewById( R.id.textViewCoins );
        textViewOnline = (TextView)findViewById( R.id.textViewOnline );
        textViewAvailable = (TextView)findViewById( R.id.textViewAvailable );
        textViewGaming = (TextView)findViewById( R.id.textViewGaming );
        textViewWon = (TextView)findViewById( R.id.textViewWon );
        textViewLost = (TextView)findViewById( R.id.textViewLost );
        textViewTied = (TextView)findViewById( R.id.textViewTied );

        SetFieldsFromGamer();
    }

    void SetFieldsFromGamer() {
        editTextAvatar.setText( currentUser.getAvatarName() );
        editTextFirst.setText( currentUser.getFirstName() );
        editTextLast.setText( currentUser.getLastName() );
        editTextEmail.setText( currentUser.getEmail() );
        textViewXP.setText( currentUser.getXp().toString() );
        textViewLevel.setText( currentUser.getLevel().toString() );
        textViewCoins.setText( currentUser.getCoins().toString() );
        textViewOnline.setText( currentUser.getOnlineDisplay() );
        textViewAvailable.setText( currentUser.getAvailableDisplay() );
        textViewGaming.setText( currentUser.getGamingDisplay() );
        textViewWon.setText( currentUser.getBattlesWon().toString() );
        textViewLost.setText( currentUser.getBattlesLost().toString() );
        textViewTied.setText( currentUser.getBattlesTied().toString() );
    }

    public void savePrefsOnClick( View v ) {

    }
    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch( item.getItemId() ) {

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
}

