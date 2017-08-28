package com.ralston.james.ashman;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.ToneGenerator;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by jamesralston on 11/28/16.
 */

public class Maze extends View {

    final int black = Color.rgb(0,0,0);
    final int blue = Color.rgb(0,0,255);
    boolean mIsRunning = false;
    int yellow = Color.YELLOW;
    float mAspectRatio ;
    private float mHeight = 1400;
    private float mWidth = 1400;
    Ashman ash = new Ashman(650, 1210, " ", this.getContext());
    private ArrayList<Ghost> ghosties = new ArrayList<>();
    public int numGhosts = 4;
    public int numCakes = 0;
    public int level = 1;
    public final int TIMER_MSEC = 45;
    public Wall mWall = new Wall(0,0,100,0,0,100);
    public boolean cheat = false;

    Runnable mTimer;
    Handler mHandler;

    char currLevel[][] = new char[14][14];


    char MazeLayout[][] =  {{'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w'},
                            {'w', 'c', 'c', 'c', 'w', 'c', 'c', 'c', 'c', 'w', 'c', 'c', 'c', 'w'},
                            {'w', 'c', 'w', 'c', 'w', 'w', 'c', 'c', 'w', 'w', 'c', 'w', 'c', 'w'},
                            {'w', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'w'},
                            {'w', 'c', 'w', 'c', 'w', 'c', 'c', 'c', 'c', 'w', 'c', 'w', 'c', 'w'},
                            {'w', 'c', 'w', 'c', 'w', 'c', 'c', 'c', 'c', 'w', 'c', 'w', 'c', 'w'},
                            {'w', 'c', 'c', 'c', 'w', 'c', 'c', 'c', 'c', 'w', 'c', 'c', 'c', 'w'},
                            {'w', 'w', 'w', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'w', 'w', 'w'},
                            {'w', 'c', 'c', 'c', 'w', 'c', 'c', 'c', 'c', 'w', 'c', 'c', 'c', 'w'},
                            {'w', 'c', 'w', 'c', 'w', 'c', 'c', 'c', 'c', 'w', 'c', 'w', 'c', 'w'},
                            {'w', 'c', 'c', 'c', 'w', 'w', 'w', 'w', 'w', 'w', 'c', 'c', 'c', 'w'},
                            {'w', 'w', 'w', 'c', 'c', 'c', 'w', 'w', 'c', 'c', 'c', 'w', 'w', 'w'},
                            {'w', 'c', 'c', 'c', 'w', 'c', 'c', 'c', 'c', 'w', 'c', 'c', 'c', 'w'},
                            {'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w'}};

    char Cheat[][] = {{'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w'},
                      {'w', 'o', 'o', 'o', 'w', 'o', 'o', 'o', 'o', 'w', 'o', 'o', 'o', 'w'},
                      {'w', 'o', 'w', 'o', 'w', 'w', 'o', 'o', 'w', 'w', 'o', 'w', 'o', 'w'},
                      {'w', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'w'},
                      {'w', 'o', 'w', 'o', 'w', 'o', 'o', 'o', 'o', 'w', 'o', 'w', 'o', 'w'},
                      {'w', 'o', 'w', 'o', 'w', 'o', 'o', 'o', 'o', 'w', 'o', 'w', 'o', 'w'},
                      {'w', 'o', 'o', 'o', 'w', 'o', 'o', 'o', 'o', 'w', 'o', 'o', 'o', 'w'},
                      {'w', 'w', 'w', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'w', 'w', 'w'},
                      {'w', 'o', 'o', 'o', 'w', 'o', 'o', 'o', 'o', 'w', 'o', 'o', 'o', 'w'},
                      {'w', 'o', 'w', 'o', 'w', 'o', 'o', 'o', 'o', 'w', 'o', 'w', 'o', 'w'},
                      {'w', 'o', 'o', 'o', 'w', 'w', 'w', 'w', 'w', 'w', 'o', 'o', 'o', 'w'},
                      {'w', 'w', 'w', 'o', 'o', 'o', 'w', 'w', 'o', 'o', 'o', 'w', 'w', 'w'},
                      {'w', 'o', 'o', 'c', 'w', 'o', 'o', 'o', 'o', 'w', 'o', 'o', 'o', 'w'},
                      {'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w'}};

    char Level2[][] = {{'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w'},
                       {'w', 'c', 'c', 'c', 'c', 'g', 'g', 'g', 'g', 'c', 'c', 'c', 'c', 'w'},
                       {'w', 'c', 'w', 'w', 'c', 'c', 'w', 'w', 'c', 'c', 'w', 'w', 'c', 'w'},
                       {'w', 'c', 'w', 'w', 'c', 'c', 'w', 'w', 'c', 'c', 'w', 'w', 'c', 'w'},
                       {'w', 'c', 'c', 'c', 'c', 'c', 'w', 'w', 'c', 'c', 'c', 'c', 'c', 'w'},
                       {'w', 'c', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'c', 'w'},
                       {'w', 'c', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'c', 'w'},
                       {'w', 'c', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'c', 'w'},
                       {'w', 'c', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'c', 'w'},
                       {'w', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'w'},
                       {'w', 'c', 'w', 'w', 'c', 'c', 'w', 'w', 'c', 'c', 'w', 'w', 'c', 'w'},
                       {'w', 'c', 'w', 'w', 'c', 'c', 'w', 'w', 'c', 'c', 'w', 'w', 'c', 'w'},
                       {'w', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'w'},
                       {'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w'}};

    char cakesLevel2[] = new char[] {'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w',
            'w', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'w',
            'w', 'c', 'w', 'w', 'c', 'c', 'w', 'w', 'c', 'c', 'w', 'w', 'c', 'w',
            'w', 'c', 'w', 'w', 'c', 'c', 'w', 'w', 'c', 'c', 'w', 'w', 'c', 'w',
            'w', 'c', 'c', 'c', 'c', 'c', 'w', 'w', 'c', 'c', 'c', 'c', 'c', 'w',
            'w', 'c', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'c', 'w',
            'w', 'c', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'c', 'w',
            'w', 'c', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'c', 'w',
            'w', 'c', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'c', 'w',
            'w', 'c', 'c', 'c', 'c', 'c', 'w', 'w', 'c', 'c', 'c', 'c', 'c', 'w',
            'w', 'c', 'w', 'w', 'c', 'c', 'w', 'w', 'c', 'c', 'w', 'w', 'c', 'w',
            'w', 'c', 'w', 'w', 'c', 'c', 'w', 'w', 'c', 'c', 'w', 'w', 'c', 'w',
            'w', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'w',
            'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w'};

    char cakesCheat2[] = new char[] {'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w',
            'w', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'w',
            'w', 'o', 'w', 'w', 'o', 'o', 'w', 'w', 'o', 'o', 'w', 'w', 'o', 'w',
            'w', 'o', 'w', 'w', 'o', 'o', 'w', 'w', 'o', 'o', 'w', 'w', 'o', 'w',
            'w', 'o', 'o', 'o', 'o', 'o', 'w', 'w', 'o', 'o', 'o', 'o', 'o', 'w',
            'w', 'o', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'o', 'w',
            'w', 'o', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'o', 'w',
            'w', 'o', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'o', 'w',
            'w', 'o', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'o', 'w',
            'w', 'o', 'o', 'o', 'o', 'o', 'w', 'w', 'o', 'o', 'o', 'o', 'o', 'w',
            'w', 'o', 'w', 'w', 'o', 'o', 'w', 'w', 'o', 'o', 'w', 'w', 'o', 'w',
            'w', 'o', 'w', 'w', 'o', 'o', 'w', 'w', 'o', 'o', 'w', 'w', 'o', 'w',
            'w', 'c', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'w',
            'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w'};

    char cakesCheat[] = new char[]       {'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w',
                                          'w', 'o', 'o', 'o', 'w', 'o', 'o', 'o', 'o', 'w', 'o', 'o', 'o', 'w',
                                          'w', 'o', 'w', 'o', 'w', 'w', 'o', 'o', 'w', 'w', 'o', 'w', 'o', 'w',
                                          'w', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'w',
                                          'w', 'o', 'w', 'o', 'w', 'o', 'o', 'o', 'o', 'w', 'o', 'w', 'o', 'w',
                                          'w', 'o', 'w', 'o', 'w', 'o', 'o', 'o', 'o', 'w', 'o', 'w', 'o', 'w',
                                          'w', 'o', 'o', 'o', 'w', 'o', 'o', 'o', 'o', 'w', 'o', 'o', 'o', 'w',
                                          'w', 'w', 'w', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'w', 'w', 'w',
                                          'w', 'o', 'o', 'o', 'w', 'o', 'o', 'o', 'o', 'w', 'o', 'o', 'o', 'w',
                                          'w', 'o', 'w', 'o', 'w', 'g', 'g', 'g', 'g', 'w', 'o', 'w', 'o', 'w',
                                          'w', 'o', 'o', 'o', 'w', 'w', 'w', 'w', 'w', 'w', 'o', 'o', 'o', 'w',
                                          'w', 'w', 'w', 'o', 'o', 'o', 'w', 'w', 'o', 'o', 'o', 'w', 'w', 'w',
                                          'w', 'o', 'o', 'c', 'w', 'o', 'o', 'o', 'o', 'w', 'o', 'o', 'o', 'w',
                                          'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w'};


char cakes1[] = new char[]      {'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w',
                                 'w', 'c', 'c', 'c', 'w', 'c', 'c', 'c', 'c', 'w', 'c', 'c', 'c', 'w',
                                 'w', 'c', 'w', 'c', 'w', 'w', 'c', 'c', 'w', 'w', 'c', 'w', 'c', 'w',
                                 'w', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'w',
                                 'w', 'c', 'w', 'c', 'w', 'c', 'c', 'c', 'c', 'w', 'c', 'w', 'c', 'w',
                                 'w', 'c', 'w', 'c', 'w', 'c', 'c', 'c', 'c', 'w', 'c', 'w', 'c', 'w',
                                 'w', 'c', 'c', 'c', 'w', 'c', 'c', 'c', 'c', 'w', 'c', 'c', 'c', 'w',
                                 'w', 'w', 'w', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'w', 'w', 'w',
                                 'w', 'c', 'c', 'c', 'w', 'c', 'c', 'c', 'c', 'w', 'c', 'c', 'c', 'w',
                                 'w', 'c', 'w', 'c', 'w', 'g', 'g', 'g', 'g', 'w', 'c', 'w', 'c', 'w',
                                 'w', 'c', 'c', 'c', 'w', 'w', 'w', 'w', 'w', 'w', 'c', 'c', 'c', 'w',
                                 'w', 'w', 'w', 'c', 'c', 'c', 'w', 'w', 'c', 'c', 'c', 'w', 'w', 'w',
                                 'w', 'c', 'c', 'c', 'w', 'c', 'c', 'c', 'c', 'w', 'c', 'c', 'c', 'w',
                                 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w'};

    char cakes[] = new char[196];



    public Maze(Context context){
        super(context);
        commonInit();
        ghosts();
    }

    public Maze(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        getAttrs(context, attrs);
        ghosts();
        commonInit();
    }

    public Maze(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        getAttrs(context, attrs);
        ghosts();
        commonInit();
    }

    public void ghosts(){
        ghosties.add(new Ghost(550, 950, Color.RED, "up"));
        ghosties.add(new Ghost(650, 950, Color.MAGENTA, "left"));
        ghosties.add(new Ghost(750, 950, Color.CYAN, "right"));
        ghosties.add(new Ghost(850, 950, Color.rgb(255, 165, 0), "down"));
        ghosties.add(new Ghost(550, 950, Color.rgb(0, 255, 0), "down"));
        ghosties.add(new Ghost(650, 950, Color.rgb(0, 128, 128), "right"));
        ghosties.add(new Ghost(750, 950, Color.rgb(0, 100, 0), "left"));
        ghosties.add(new Ghost(750, 950, Color.rgb(75, 0, 130), "up"));

    }

    public void go(){
        mIsRunning = true;
        mHandler.postDelayed(mTimer, TIMER_MSEC);
    }

    public void pause(){
        mIsRunning = false;
        mHandler.removeCallbacks(mTimer);
    }

    private void commonInit(){
        mHandler = new Handler();
        numCakes = cakesRemaining();
//        MainActivity.cakes.setText("Cakes Remaining: " + numCakes);
        mTimer = new Runnable() {
            @Override
            public void run() {
                onTimer();
                if(mIsRunning)
                    mHandler.postDelayed(this, TIMER_MSEC);
            }
        };

        if(cheat){
            for(int i = 0; i < 14; i++){
                for(int j = 0; j < 14; j++){
                    currLevel[i][j] = Cheat[i][j];
                }//end for
            }//end for
            for(int i = 0; i < cakesCheat.length; i++){
                cakes[i] = cakesCheat[i];
            }
        }//end

        else if(!cheat && level == 1){
            for(int i = 0; i < 14; i++){
                for(int j = 0; j < 14; j++){
                    currLevel[i][j] = MazeLayout[i][j];
                }//end for
            }//end for
            for(int i = 0; i < cakes1.length; i++){
                cakes[i] = cakes1[i];
            }
        }

        else if(!cheat && level == 2){
            for(int i = 0; i < 14; i++){
                for(int j = 0; j < 14; j++){
                    currLevel[i][j] = Level2[i][j];
                }//end for
            }//end for
            for(int i = 0; i < cakesLevel2.length; i++){
                cakes[i] = cakesLevel2[i];
            }
        }
    }

    public void onTimer(){
        updatePosition();
        updateGhostPosition();
        invalidate();
    }//end

    public void restart(int level, boolean b, int num){
        int x;
        int y = 950;
        ash = new Ashman(650, 1210, " ", this.getContext());
        this.level = level;
        if(this.level == 1){
            x = 550;
        }
        else{
            x = 350;
        }
        this.cheat = b;
        this.numGhosts = num;
        for(int i = 0; i < numGhosts; i++){
            ghosties.get(i).setX_pos(x + (i*100));
            ghosties.get(i).setY_pos(y);
        }

        if(cheat){
            for(int i = 0; i < 14; i++){
                for(int j = 0; j < 14; j++){
                    currLevel[i][j] = Cheat[i][j];
                }//end for
            }//end for
            for(int i = 0; i < cakesCheat.length; i++){
                cakes[i] = cakesCheat[i];
            }
        }//end

        else if(!cheat && level == 1){
            for(int i = 0; i < 14; i++){
                for(int j = 0; j < 14; j++){
                    currLevel[i][j] = MazeLayout[i][j];
                }//end for
            }//end for
            for(int i = 0; i < cakes1.length; i++){
                cakes[i] = cakes1[i];
            }
        }

        else if(!cheat && level == 2){
            for(int i = 0; i < 14; i++){
                for(int j = 0; j < 14; j++){
                    currLevel[i][j] = Level2[i][j];
                }//end for
            }//end for
            for(int i = 0; i < cakesLevel2.length; i++){
                cakes[i] = cakesLevel2[i];
            }
        }
        invalidate();

    }

    public void updateGhostPosition(){
        int speed;
        if(level == 1){
            speed = 5;
        }
        else{
            speed = 10;
        }

        for(int i = 0; i < numGhosts; i++){
            Ghost ghost = ghosties.get(i);


            switch(ghost.getDirection()){
                case("up"):
                    ghost.setY_pos(ghost.getY_pos() - speed);
                    if(cakes[((ghost.getY_pos()-55)/100 * 14) + (ghost.getX_pos()/100)] == 'w'){
                        int randomNum;
                        Random rand = new Random();
                        randomNum = rand.nextInt(4) + 1;
                        switch(randomNum){
                            case(1):
                                ghost.setDirection("up");
                                break;
                            case(2):
                                ghost.setDirection("down");
                                break;
                            case(3):
                                ghost.setDirection("right");
                                break;
                            case(4):
                                ghost.setDirection("left");
                                break;
                        }//end switch
                    }//end if
                    break;
                case("down"):
                    ghost.setY_pos(ghost.getY_pos() + speed);
                    if(cakes[((ghost.getY_pos()+55)/100 * 14) + (ghost.getX_pos()/100)] == 'w'){
                        int randomNum;
                        Random rand = new Random();
                        randomNum = rand.nextInt(4) + 1;
                        switch(randomNum){
                            case(1):
                                ghost.setDirection("up");
                                break;
                            case(2):
                                ghost.setDirection("down");
                                break;
                            case(3):
                                ghost.setDirection("right");
                                break;
                            case(4):
                                ghost.setDirection("left");
                                break;
                        }//end switch
                    }
                    break;
                case("left"):
                    ghost.setX_pos(ghost.getX_pos() - speed);
                    if(cakes[(ghost.getY_pos()/100*14)+ (ghost.getX_pos()-55)/100] == 'w'){
                        int randomNum;
                        Random rand = new Random();
                        randomNum = rand.nextInt(4) + 1;
                        switch(randomNum){
                            case(1):
                                ghost.setDirection("up");
                                break;
                            case(2):
                                ghost.setDirection("down");
                                break;
                            case(3):
                                ghost.setDirection("right");
                                break;
                            case(4):
                                ghost.setDirection("left");
                                break;
                        }//end switch
                    }//end if
                    break;
                case("right"):
                    ghost.setX_pos(ghost.getX_pos() + speed);
                    if(cakes[((ghost.getY_pos()/100 * 14) + (ghost.getX_pos()+55)/100)] == 'w'){
                        int randomNum;
                        Random rand = new Random();
                        randomNum = rand.nextInt(4) + 1;
                        switch(randomNum){
                            case(1):
                                ghost.setDirection("up");
                                break;
                            case(2):
                                ghost.setDirection("down");
                                break;
                            case(3):
                                ghost.setDirection("right");
                                break;
                            case(4):
                                ghost.setDirection("left");
                                break;
                        }//end switch
                    }
                    break;
            }

            if (Math.abs(ghost.getX_pos() - ash.getX_pos()) < 50 && Math.abs(ghost.getY_pos() - ash.getY_pos()) < 50){
                MediaPlayer mp = MediaPlayer.create(this.getContext(), R.raw.scream);
                mp.setVolume(1.0f, 1.0f);
                mp.start();
                Toast.makeText(this.getContext(), "You Lost", Toast.LENGTH_SHORT).show();
                restart(this.level, false, this.numGhosts);
                pause();
            }

        }
    }



    public void updatePosition(){

        Bitmap originalUpOpen = BitmapFactory.decodeResource(getResources(), R.drawable.openup100);
        Bitmap originalLeftOpen = BitmapFactory.decodeResource(getResources(), R.drawable.openleft100);
        Bitmap originalDownOpen = BitmapFactory.decodeResource(getResources(), R.drawable.opendown100);
        Bitmap originalOpen = BitmapFactory.decodeResource(getResources(), R.drawable.open100);
        ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
        MediaPlayer mp = MediaPlayer.create(this.getContext(), R.raw.winning);
        mp.setVolume(1.0f, 1.0f);

        switch(ash.getCurrDirection()){
            case("up"):
                int x, y;
                x = ash.getX_pos();
                y = ash.getY_pos();
                ash.pac[0] = Bitmap.createScaledBitmap(originalUpOpen, 80, 80, false);

                if(cakes[((ash.getY_pos()-1)/100 * 14) + (ash.getX_pos()/100)] == 'w'){
                    ash.setY_pos(ash.getY_pos());
                }//end else

                else {
                    if(cakes[((ash.getY_pos()-1)/100 * 14) + (ash.getX_pos()/100)] == 'c'){
                        tg.startTone(ToneGenerator.TONE_PROP_BEEP);
                        cakes[((ash.getY_pos()-1)/100 * 14) + (ash.getX_pos()/100)] = 'o';
                        numCakes -= 1;
                        MainActivity.cakes.setText("Cakes Remaining: " + numCakes);
                        if(numCakes == 0){
                            Toast.makeText(this.getContext(), "YOU WON!!!!", Toast.LENGTH_SHORT).show();
                            mp.start();
                            restart(this.level + 1, false, 8);
                            pause();
                        }
                    }//end if
                    ash.setY_pos(ash.getY_pos() - 10);
                    invalidate();
                }
                break;
            case("down"):
                x = ash.getX_pos();
                y = ash.getY_pos();
                ash.pac[0] = Bitmap.createScaledBitmap(originalDownOpen, 80, 80, false);

                if(cakes[((ash.getY_pos()+80)/100 * 14) + (ash.getX_pos()/100)] == 'w'){
                    ash.setY_pos(ash.getY_pos());
                }//end else if

                else {
                    if(cakes[((ash.getY_pos()+80)/100 * 14) + (ash.getX_pos()/100)] == 'c'){
                        tg.startTone(ToneGenerator.TONE_PROP_BEEP);
                        cakes[((ash.getY_pos()+80)/100 * 14) + ash.getX_pos()/100] = 'o';
                        numCakes -= 1;
                        MainActivity.cakes.setText("Cakes Remaining: " + numCakes);
                        if(numCakes == 0){
                            Toast.makeText(this.getContext(), "YOU WON!!!!", Toast.LENGTH_SHORT).show();
                            mp.start();
                            restart(this.level + 1, false, 8);
                            pause();
                        }
                    }
                    ash.setY_pos(ash.getY_pos() + 10);
                    invalidate();
                }//end else
                break;
            case("left"):
                x = ash.getX_pos();
                y = ash.getY_pos();
                ash.pac[0] = Bitmap.createScaledBitmap(originalLeftOpen, 80, 80, false);

                if(cakes[((ash.getY_pos()/100)*14)+ (ash.getX_pos()-1)/100] == 'w'){
                    ash.setX_pos(ash.getX_pos());
                }//end else if

                else {
                    if(cakes[((ash.getY_pos()/100)*14)+ (ash.getX_pos()-1)/100] == 'c')
                    {
                        tg.startTone(ToneGenerator.TONE_PROP_BEEP);
                        cakes[(ash.getY_pos()/100 * 14) + (ash.getX_pos()-1)/100] = 'o';
                        numCakes -= 1;
                        MainActivity.cakes.setText("Cakes Remaining: " + numCakes);
                        if(numCakes == 0){
                            Toast.makeText(this.getContext(), "YOU WON!!!!", Toast.LENGTH_SHORT).show();
                            mp.start();
                            restart(this.level + 1, false, 8);
                            pause();
                        }
                    }
                    ash.setX_pos(ash.getX_pos() - 10);
                    invalidate();
                }//end else
                break;
            case("right"):
                x = ash.getX_pos();
                y = ash.getY_pos();
                ash.pac[0] = Bitmap.createScaledBitmap(originalOpen, 80, 80, false);

                if(cakes[(((ash.getY_pos()/100) * 14) + (ash.getX_pos()+80)/100)] == 'w'){
                    ash.setX_pos(ash.getX_pos());
                }//end else if

                else {
                    if(cakes[(ash.getY_pos()/100 * 14) + (ash.getX_pos()+80)/100] == 'c'){
                        tg.startTone(ToneGenerator.TONE_PROP_BEEP);
                        cakes[(ash.getY_pos()/100 * 14) + (ash.getX_pos()+80)/100] = 'o';
                        numCakes -= 1;
                        MainActivity.cakes.setText("Cakes Remaining: " + numCakes);
                        if(numCakes == 0){
                            Toast.makeText(this.getContext(), "YOU WON!!!!", Toast.LENGTH_SHORT).show();
                            mp.start();
                            restart(this.level + 1, false, 8);
                            pause();
                        }
                    }//end if
                    ash.setX_pos(ash.getX_pos() + 10);
                    invalidate();
                }//end else
                break;
        }//end switch
    }//end updatePosition

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //CheckLevel();

        float height = getHeight();
        float width = getWidth();

        Paint blackP = new Paint();
        Paint blueP = new Paint();
        Paint yellowP = new Paint();
        blackP.setColor(black);
        blueP.setColor(blue);
        yellowP.setColor(yellow);

        blackP.setStyle(Paint.Style.FILL);
        blueP.setStyle(Paint.Style.FILL);
        yellowP.setStyle(Paint.Style.FILL);

        canvas.scale(width/mWidth, height/mHeight);
        numCakes = cakesRemaining();
        float wall = 100f;

        MainActivity.level.setText("Level: " + level);
        MainActivity.cakes.setText("Cakes Remaining: " + numCakes);
        if(cheat && level == 1){
            for(int i = 0; i < 14; i++){
                for(int j = 0; j < 14; j++){
                    currLevel[i][j] = Cheat[i][j];
                }//end for
            }//end for
            for(int i = 0; i < cakesCheat.length; i++){
                cakes[i] = cakesCheat[i];
            }
        }//end
        
        else if(cheat && level == 2){
            for(int i = 0; i < 14; i++){
                for(int j = 0; j < 14; j++){
                    currLevel[i][j] = Level2[i][j];
                }
            }
            for(int i = 0; i < cakesCheat2.length; i++){
                cakes[i] = cakesCheat2[i];
            }
        }

            for (int i = 0; i < 14; i++) {

                for (int j = 0; j < 14; j++) {


                    if (currLevel[j][i] == 'w') {
                        canvas.drawRect(new RectF(i*wall, j*wall, (i*wall) + wall, (j*wall) + wall), blackP);
                    }//end if

                    else {
                        canvas.drawRect(new RectF(i * wall, j * wall, (i * wall) + wall, (j * wall) + wall), blueP);
                        if (cakes[j * 14 + i] == 'c') {
                            int trimCakes = 25;
                            canvas.drawOval(new RectF((i * wall) + trimCakes, (j * wall) + trimCakes, (i * wall) + 75, (j * wall) + 75), yellowP);
                        }
                    }//end else
                }//end inner for
            }//end outer for

            canvas.save();
            canvas.translate(ash.getX_pos(), ash.getY_pos());
            ash.draw(canvas);
            canvas.restore();

            for (int i = 0; i < numGhosts; i++) {
                ghosties.get(i).draw(canvas);
            }

            //numCakes = cakesRemaining();
            //numCakes += 1;
    }//end onDraw

    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AspectRatioImageView,
                0, 0);
        try {
            mAspectRatio = a.getFloat(R.styleable.AspectRatioImageView_aspectRatio, 1.0f);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec) ;
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec) ;

        int calcWidth = (int)((float)parentHeight * mAspectRatio) ;
        int calcHeight = (int)((float)parentWidth / mAspectRatio) ;

        int finalWidth, finalHeight ;

        if (calcHeight > parentHeight) {
            finalWidth = calcWidth ;
            finalHeight = parentHeight ;
        } else {
            finalWidth = parentWidth ;
            finalHeight = calcHeight ;
        }

        setMeasuredDimension (finalWidth, finalHeight) ;
    }


    public int cakesRemaining(){
        int count = 0;
        for(int i = 0; i < 14; i++){
            for(int j = 0; j<14; j++){
                if(cakes[j*14+i] == 'c'){
                    count += 1;
                }//end if
            }//end inner for
        }//end outer for
        return count;
    }//end cakesRemaining


public void setCheat(){
    this.cheat = true;
}

    public void setLevel(int level) {
        this.level = level;
    }
}//end class
