package com.ralston.james.ashman;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by jamesralston on 12/2/16.
 */

public class Ghost {

    private int x_pos;
    private int y_pos;
    private int color;
    private String direction;
    private Paint p;

    public Ghost(int x, int y, int color, String direction){
        this.x_pos = x;
        this.y_pos = y;
        this.direction = direction;
        this.p = new Paint();
        p.setColor(color);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public int getY_pos() {
        return y_pos;
    }

    public int getX_pos() {
        return x_pos;
    }

    public int getColor() {
        return color;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setY_pos(int y_pos) {
        this.y_pos = y_pos;
    }

    public void setX_pos(int x_pos) {
        this.x_pos = x_pos;
    }

    public Paint getP() {
        return p;
    }


    public void draw(Canvas canvas){
        int x = this.x_pos;
        int y = this.y_pos;

        canvas.drawOval(new RectF(x - 30, y-30, x+30, y+30), this.p);
    }
}
