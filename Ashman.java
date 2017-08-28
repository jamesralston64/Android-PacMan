package com.ralston.james.ashman;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by jamesralston on 12/2/16.
 */

public class Ashman {
    private int x_pos;
    private int y_pos;
    private String currDirection;
    private String nextDirection;
    public Bitmap[] pac = new Bitmap[2];
    private boolean openClosed = true;
    int mouth;


    public Ashman(int x, int y, String curr, Context context){

        Bitmap originalOpen = BitmapFactory.decodeResource(context.getResources(), R.drawable.open100);
        Bitmap originalClosed = BitmapFactory.decodeResource(context.getResources(), R.drawable.closed100);
        pac[0] = Bitmap.createScaledBitmap(originalOpen, 80, 80, false);
        pac[1] = Bitmap.createScaledBitmap(originalClosed, 80, 80, false);
        this.x_pos = x;
        this.y_pos = y;
        this.currDirection = curr;
        this.nextDirection = " ";
        this.mouth = 0;
    }


    public int getX_pos() {
        return x_pos;
    }

    public int getY_pos() {
        return y_pos;
    }

    public String getCurrDirection() {
        return currDirection;
    }

    public String getNextDirection() {
        return nextDirection;
    }

    public void setX_pos(int x_pos) {
        this.x_pos = x_pos;
    }

    public void setY_pos(int y_pos) {
        this.y_pos = y_pos;
    }

    public void setCurrDirection(String currDirection) {
        this.currDirection = currDirection;
    }

    public void setNextDirection(String nextDirection) {
        this.nextDirection = nextDirection;
        this.currDirection = nextDirection;
    }

    public void draw(Canvas canvas){
        Paint p = new Paint();
        this.mouth += 1;
        int x = this.getX_pos();
        int y = this.getY_pos();

        if(openClosed) {
            canvas.drawBitmap(pac[0], 0, 0, null);
        }//end if

        else{
            canvas.drawBitmap(pac[1], 0, 0, null);
        }


        if(mouth % 2 == 0){
            openClosed = !openClosed;
        }
    }//end draw

}//end ashman
