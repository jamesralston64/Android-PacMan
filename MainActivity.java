package com.ralston.james.ashman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    public static TextView cakes;
    public static TextView level;
    public TextView discription;
    public Maze maze;
    public Button cheat;
    public ImageView up, right, left, down;
    boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        maze = (Maze) findViewById(R.id.mazeView);
        cakes =  (TextView) findViewById(R.id.cakes_id);
        level = (TextView) findViewById(R.id.level_id);
        discription = (TextView) findViewById(R.id.description_id);

        up = (ImageView) findViewById(R.id.upButton);
        up.setOnClickListener(this);
        left = (ImageView) findViewById(R.id.leftButton);
        left.setOnClickListener(this);
        right = (ImageView) findViewById(R.id.rightButton);
        right.setOnClickListener(this);
        down = (ImageView) findViewById(R.id.downButton);
        down.setOnClickListener(this);

        discription.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                maze.setCheat();
                return true;
            }
        });



    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.upButton:
                //code
                maze.ash.setNextDirection("up");
                break;


            case R.id.leftButton:
                //my code
                maze.ash.setNextDirection("left");
                break;


            case R.id.rightButton:
                maze.ash.setNextDirection("right");
                break;


            case R.id.downButton:
                maze.ash.setNextDirection("down");
                break;


            case R.id.description_id:
                if(!isRunning){
                    maze.go();
                    isRunning = true;
                }
                else{
                    maze.pause();
                    isRunning = false;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // This adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    { // Handle action bar item clicks here

        switch(item.getItemId()){
            case R.id.activity_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_about:
                Toast.makeText(this,
                        "Ashman, Fall 2016, James Ralston", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onResume(){
//        super.onResume();
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        maze.numGhosts = sharedPreferences.getInt("Ghosts", 4);
//    }
}
