package com.example.Battleship;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.Toast;

public class BoardView extends ImageView {
    int border = 10;
    int topBoardX = border;
    int topBoardY = 50;
    int bottomBoardY = 440;
    int fontSize = 20;
    String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
    String[] numbers = { " 1", " 2", " 3", " 4", " 5", " 6", " 7", " 8", " 9", "10" };
    private static Paint paint;
    Point origin = new Point( this.getLeft(), this.getTop() );
    public BoardView( Context context, AttributeSet attrs ) {
        super( context, attrs );
        paint = new Paint();
        paint.setStyle( Paint.Style.STROKE );
        paint.setTextSize( 20 );
    }

    @Override
    protected void onDraw( Canvas canvas ) {
        super.onDraw( canvas );
        this.getLeft();
        int screenHeight = this.getMeasuredHeight();
        int count = screenHeight / 11;
        int screenWidth = this.getMeasuredWidth();
        int middle = screenHeight / 2;
        paint.setTextSize(20);
        int cellWidth = (screenWidth / 11) -1;
        int cellHeight = (middle - border) / 12;
        Game.gameGrid[0][0].setCellHeight(cellHeight);
        Game.gameGrid[0][0].setCellWidth(cellWidth);
        Game.gameGrid[0][0].setViewOrigin(origin);

        paint.setStrokeWidth( 2 );
        paint.setColor( Color.WHITE );
        paint.setStyle( Paint.Style.FILL_AND_STROKE );
        canvas.drawText( "Defending Board", 40, 20, paint );
        paint.setColor(Color.MAGENTA);
        for(int y=0;y<11;y++){
            for(int x=0;x<11;x++){
                Game.gameGrid[x][y].setTopleft(new Point(x*cellWidth + border, y*cellHeight + topBoardY ));
                Game.gameGrid[x][y].setBottomright(new Point((x + 1)*cellWidth+border, (y + 1)*cellHeight + topBoardX));
            }
        }
        for( int i = 0; i < 11; i++ ) {
            canvas.drawLine( i * cellWidth + border, topBoardY, i * cellWidth + border, middle - border, paint );
            canvas.drawLine( border, i * cellHeight + topBoardY, screenWidth - border, i * cellHeight + topBoardY, paint );
        }
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        float w = paint.measureText(numbers[0],0,numbers[0].length());
        float center = (cellWidth / 2) - (w/2);
        for( int x = 0; x < 10; x++ ) {
            canvas.drawText( letters[x], Game.gameGrid[x][0].getTopleft().x + center + cellWidth, Game.gameGrid[x][0].getTopleft().y + fontSize + border, paint );
        }
        for( int y = 0; y < 10; y++ ) {
            canvas.drawText( numbers[y], Game.gameGrid[0][y].getTopleft().x + center, Game.gameGrid[0][y + 1].getTopleft().y + cellHeight - border, paint );
        }

        paint.setStrokeWidth( 2 );
        paint.setColor(Color.BLUE);

        if( Game.gameStarted ) {
            Game.gameGrid[0][0].setCellHeight( cellHeight );
            Game.gameGrid[0][0].setCellWidth( cellWidth );
            Game.gameGrid[0][0].setViewOrigin(origin);

            for( int y = 0; y < 11; y++ ) {
                for( int x = 0; x < 11; x++ ) {
                    Game.gameGrid[x][y].setTopleft( new Point( x * cellWidth + border, y * cellHeight + topBoardY ) );
                    Game.gameGrid[x][y].setBottomright( new Point( ( x + 1 ) * cellWidth + border, ( y + 1 ) * cellHeight + topBoardX ) );
                }
            }

            for( int i = 0; i < 11; i++ ) {
                canvas.drawLine( i * cellWidth + border, bottomBoardY, i * cellWidth + border, middle + 440 - border, paint );
                canvas.drawLine( border, i * cellHeight + bottomBoardY, screenWidth - border, i * cellHeight + bottomBoardY, paint );
            }
            paint.setStrokeWidth( 2 );
            paint.setColor( Color.WHITE );
            paint.setStyle( Paint.Style.FILL_AND_STROKE );
            canvas.drawText( "Attacking Board", 30, 440, paint );


            for( int x = 0; x < 10; x++ ) {
                canvas.drawText( letters[x], Game.gameGrid[x][0].getTopleft().x + center + cellWidth, Game.gameGrid[x][0].getTopleft().y + 390 + fontSize + border, paint );
            }
            for( int y = 0; y < 10; y++ ) {
                canvas.drawText( numbers[y], Game.gameGrid[0][y].getTopleft().x + center, Game.gameGrid[0][y + 1].getTopleft().y + 400 + cellHeight - border, paint );
            }
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth( 2 );
            for( int y = 0; y < 11; y++ ) {
                for( int x = 0; x < 11; x++ ) {
                    if( Game.gameGrid[x][y].getHas_ship() )
                        drawCell( "S", x, y, center, canvas );
                    if( Game.gameGrid[x][y].getWaiting() )
                        drawCell( "W", x, y, center, canvas );
                    if( Game.gameGrid[x][y].getMiss() )
                        drawCell( "M", x, y, center, canvas );
                    if( Game.gameGrid[x][y].getHit() )
                        drawCell( "H", x, y, center, canvas );
                }

            }
        }
    }

    void drawCell( String contents, int x, int y, float center, Canvas canvas ) {
        canvas.drawText( contents, Game.gameGrid[x][y].getTopleft().x + center, Game.gameGrid[x][y].getTopleft().y + center, paint );
    }
}
