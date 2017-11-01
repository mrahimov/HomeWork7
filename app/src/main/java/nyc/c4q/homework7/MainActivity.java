package nyc.c4q.homework7;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {
    Button red, blue, green, yellow, play;
    TextView score, bestScore, level;
    Button[] color;
    boolean running = false;
    int buttonTag = 1;
    int count;

    AlphaAnimation animation;
    int x;
    Random random;
    ArrayList<Integer> arrayListXvALUE;
    ArrayList<Integer> userChoiceList;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //identifate the button
        red = (Button) findViewById(R.id.red);
        blue = (Button) findViewById(R.id.blue);
        green = (Button) findViewById(R.id.green);
        yellow = (Button) findViewById(R.id.yellow);
        //for the stauts of game
        score = (TextView) findViewById(R.id.socre);
        bestScore = (TextView) findViewById(R.id.bestscore);
        level = (TextView) findViewById(R.id.level);
        //to start play or to stop
        play = (Button) findViewById(R.id.play);
        play.setTag(1);
        //storage the color inside the button
        color = new Button[]{red, blue, green, yellow};

        //Generate Random number to access to the color
        random = new Random();
        //storge the color in the sequence to use them later .
        arrayListXvALUE = new ArrayList<>();
        userChoiceList = new ArrayList<>();


        //to flash the button
        animation = new AlphaAnimation(1f, 0f);
        animation.setDuration(1000);

    }


    public void onplay(final View view) {
        buttonTag = (int) play.getTag();
        if (buttonTag == 1) {
            play.setTag(2);
            //  red.startAnimation(animation);
            play.setText("Quit game");
            running = true;


            x = random.nextInt(4);  //Generate Random number

            arrayListXvALUE.add(x);
            Log.e("random number is", "" + x);  //Storage the Random number inside the ArrayList

            count = 1;

            for (int value : arrayListXvALUE) {
                Log.e("array list value is: ", "" + value);    //iterate through the ArrayList Value;


                switch (color[value].getId()) {
                    case R.id.red:


                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                red.startAnimation(animation);
                            }
                            //  Toast.makeText(getBaseContext(), "Flash: " + finalJ, Toast.LENGTH_SHORT).show();
                        }, 2000 * count);
                        count++;


                        break;
                    case R.id.blue:


                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                blue.startAnimation(animation);
                            }
                            //  Toast.makeText(getBaseContext(), "Flash: " + finalJ, Toast.LENGTH_SHORT).show();
                        }, 2000 * count);

                        count++;
                        break;
                    case R.id.green:


                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                green.startAnimation(animation);
                            }
                            //  Toast.makeText(getBaseContext(), "Flash: " + finalJ, Toast.LENGTH_SHORT).show();
                        }, 2000 * count);
                        count++;

                        break;
                    case R.id.yellow:


                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                yellow.startAnimation(animation);
                            }
                            //  Toast.makeText(getBaseContext(), "Flash: " + finalJ, Toast.LENGTH_SHORT).show();
                        }, 2000 * count);
                        count++;

                        break;

                }
            }

//                switch (view.getId()){
//                    case R.id.
//                }


            Log.e("running changed to", "" + running);




            //  }//end while loop running

        } else {
            if (buttonTag == 2) {
                play.setTag(1);
                play.setText("play");
                running = false;

                Toast.makeText(getBaseContext(), "Game Finsh: ", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void oncheck(View view) {

        switch (view.getId()) {
            case R.id.red:
                userChoiceList.add(0);
                break;
            case R.id.blue:
                userChoiceList.add(1);
                break;
            case R.id.green:
                userChoiceList.add(2);
                break;
            case R.id.yellow:
                userChoiceList.add(3);
                break;

        }
        Log.e("on check bee called","now");
        if(arrayListXvALUE.equals(userChoiceList)){
            Toast.makeText(this, "Great jobe , Click for Next", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "not good", Toast.LENGTH_SHORT).show();
        }




    }









//
}























