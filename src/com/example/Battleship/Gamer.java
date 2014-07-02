package com.example.Battleship;

public class Gamer {
    private String first_name, last_name, email, avatar_name, avatar_path;
    private Boolean online, gaming, available;
    private Integer level, coins, battles_won, battles_lost, battles_tied, xp;
    public Gamer( String _first_name, String _last_name,
                  String _email, Boolean _online, Boolean _available, Boolean _gaming,
                  String _avatar_name, String _avatar_path,
                  Integer _level,Integer _coins,Integer _battles_won,Integer _battles_lost,Integer _battles_tied,Integer _xp)
    {
        first_name = _first_name;
        last_name = _last_name;
        email = _email;
        online = _online;
        available = _available;
        gaming = _gaming;
        avatar_name = _avatar_name;
        avatar_path = _avatar_path;
        level = _level;
        coins = _coins;
        battles_won = _battles_won;
        battles_lost = _battles_lost;
        battles_tied = _battles_tied;
        xp = _xp;
    }

    public String getFirstName() {
        return first_name;
    }
    public void setFirst_name( String _first_name ) {
        first_name = _first_name;
    }

    public String getLastName() {
        return last_name;
    }
    public void setLast_name( String _last_name ) {
        last_name = _last_name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail( String _email ) {
        email = _email;
    }

    public Boolean getOnline() {
        return online;
    }
    public void setOnline( Boolean _online ) {
        online = _online;
    }

    public Boolean getAvailable() {
        return available;
    }
    public void setAvailable( Boolean _available ) {
        available = _available;
    }

    public Boolean getGaming() {
        return gaming;
    }
    public void setGaming( Boolean _gaming ) {
        gaming = _gaming;
    }

    public String getAvatarName() {
        return avatar_name;
    }
    public void setAvatarPath( String _avatar_name ) {
        avatar_name = _avatar_name;
    }

    public String getAvatarPath() {
        return avatar_path;
    }
    public void setAvatarName( String _avatar_path ) {
        avatar_path = _avatar_path;
    }

    public Integer getLevel() {
        return level;
    }
    public void setLevel( Integer _level ) {
        level = _level;
    }

    public Integer getCoins() {
        return coins;
    }
    public void setCoins( Integer _coins ) {
        coins = _coins;
    }

    public Integer getBattlesWon() {
        return battles_won;
    }
    public void setBattlesWon( Integer _battles_won ) {
        battles_won = _battles_won;
    }

    public Integer getBattlesLost() {
        return battles_lost;
    }
    public void setBattlesLost( Integer _battles_lost ) {
        battles_lost = _battles_lost;
    }

    public Integer getBattlesTied() {
        return battles_tied;
    }
    public void setBattlesTied( Integer _battles_tied ) {
        battles_tied = _battles_tied;
    }

    public Integer getXp() {
        return xp;
    }
    public void setXp( Integer _xp ) {
        xp = _xp;
    }

    public String getAvailableDisplay() {
        return available ? "NO" : "YES";
    }

    public String getOnlineDisplay() {
        return online ? "NO" : "YES";
    }

    public String getGamingDisplay() {
        return gaming ? "NO" : "YES";
    }

}
