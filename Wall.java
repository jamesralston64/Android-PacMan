package com.ralston.james.ashman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by jamesralston on 12/5/16.
 */

public class Wall {
    private int xPos;
    private int yPos;
    private int bottom;
    private int top;
    private int left;
    private int right;

    public Wall(int x, int y, int b, int t, int l, int r){
        this.bottom=b;
        this.left = l;
        this.right = r;
        this.top = t;
        this.xPos = x;
        this.yPos = y;
    }//end Wall

    public void draw(Canvas canvas, int i, int j){
        int y = this.getyPos();
        int x = this.getxPos();
        Paint blackP = new Paint();
        blackP.setStyle(Paint.Style.FILL_AND_STROKE);
        blackP.setColor(Color.rgb(0,0,0));

        float wall = 100f;

        canvas.drawRect(new RectF(i * wall, j * wall, (i * wall) + wall, (j * wall) + wall), blackP);

    }//end draw

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int getBottom() {
        return bottom;
    }

    public int getTop() {
        return top;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setRight(int right) {
        this.right = right;
    }
}
