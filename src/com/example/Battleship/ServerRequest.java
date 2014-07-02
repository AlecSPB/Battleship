package com.example.Battleship;

public class ServerRequest {
    String url;
    BaseActivity.ServerCommands command;
    Integer resultCode;
    String jsonDataResult;
    String errorString;

    public ServerRequest( String _url, BaseActivity.ServerCommands _command ) {
        url = _url;
        command = _command;
        resultCode = null;
        jsonDataResult = null;
        errorString = null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl( String _url ) {
        url = _url;
    }

    public BaseActivity.ServerCommands getCommand() {
        return command;
    }

    public void setCommand( BaseActivity.ServerCommands _command ) {
        command = _command;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode( Integer _resultCode ) {
        resultCode = _resultCode;
    }

    public String getJsonDataResult() {
        return jsonDataResult;
    }

    public void setJsonDataResult( String _jsonDataResult ) {
        jsonDataResult = _jsonDataResult;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString( String _errorString ) {
        errorString = _errorString;
    }

}
