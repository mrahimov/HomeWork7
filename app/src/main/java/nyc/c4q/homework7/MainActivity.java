package nyc.c4q.homework7;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button play;
    private ImageButton red, blue, green, yellow;
    private TextView score, bestScore, level;
    private ImageButton[] color;
    private boolean running;
    private int buttonTag;
    private int count = 1;
    AlphaAnimation animation = new AlphaAnimation(1f, 0f);
    Random random;
    ArrayList<Integer> arrayListXvALUE;
    ArrayList<Integer> userChoiceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupview();
        color = new ImageButton[]{red, blue, green, yellow};        //storage the color inside the button
        random = new Random();                                 //Generate Random number to access to the color
        arrayListXvALUE = new ArrayList<>();                   //storage the color in the sequence to use them later .
        userChoiceList = new ArrayList<>();                    //storage the user choice to compare later with arryListXValue;
        animation.setDuration(100);                            //to flash the button

    }

    public void setupview() {
        red = (ImageButton) findViewById(R.id.red);
        blue = (ImageButton) findViewById(R.id.blue);
        green = (ImageButton) findViewById(R.id.green);
        yellow = (ImageButton) findViewById(R.id.yellow);
        score = (TextView) findViewById(R.id.socre);
        bestScore = (TextView) findViewById(R.id.bestscore);
        level = (TextView) findViewById(R.id.level);
        play = (Button) findViewById(R.id.play);                //to start play or to stop
        play.setTag(1);
    }

    public void onplay(final View view) {
        buttonTag = (int) play.getTag();
        if (buttonTag == 1) {
            play.setTag(2);
            play.setText("Quit game");
            simon(view);
            clickCheck(view);
            score.setText("");
        } else if (buttonTag == 2) {
            play.setTag(1);
            play.setText("play");
            arrayListXvALUE.clear();
            score.setText("");
            Toast.makeText(getBaseContext(), "Game Finsh: ", Toast.LENGTH_SHORT).show();
        }
    }

    private void simon(View view) {
        final MediaPlayer mpGame = MediaPlayer.create(this, R.raw.game);

        running = true;
        Random r = new Random();
        int next = r.nextInt(4);
        arrayListXvALUE.add(next);

        for (final Integer light : arrayListXvALUE) {
            if (light == 0) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        red.startAnimation(animation);
                        mpGame.start();
                    }
                }, 600 * count);

                count++;
            } else if (light == 1) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        blue.startAnimation(animation);
                        mpGame.start();
                    }
                }, 600 * count);
                count++;
            } else if (light == 2) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        green.startAnimation(animation);
                        mpGame.start();
                    }
                }, 600 * count);

                count++;
            } else if (light == 3) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        yellow.startAnimation(animation);
                        mpGame.start();
                    }
                }, 600 * count);

                count++;
            }

        }
        count = 1;
        running = false;
        Log.e("r change after while to", "" + running);
        userChoiceList.clear();
    }

    public void clickCheck(View view) {
        final MediaPlayer sound = MediaPlayer.create(this, R.raw.sound);
        switch (view.getId()) {
            case R.id.red:
                userChoiceList.add(0);
                sound.start();
                break;
            case R.id.blue:
                userChoiceList.add(1);
                sound.start();
                break;
            case R.id.green:
                userChoiceList.add(2);
                sound.start();
                break;
            case R.id.yellow:
                userChoiceList.add(3);
                sound.start();
                break;
        }
        Log.e("on check bee called", "now");
        Log.e("userChoiceLIST SIZE IS:", "" + userChoiceList.size());
        if (arrayListXvALUE.size() == userChoiceList.size()) {
                if (arrayListXvALUE.equals(userChoiceList)) {
                    Toast.makeText(this, "Great job , Next level: " + (userChoiceList.size() + 1), Toast.LENGTH_SHORT).show();
                    score.setText("" + userChoiceList.size());
                    running = true;
                    simon(view);
                } else {
                    running = false;
                    Toast.makeText(this, "Not Match,Game over", Toast.LENGTH_SHORT).show();
                    play.setTag(1);
                    play.setText("play");
                    score.setText("");
                    arrayListXvALUE.clear();
                }
            }
        }
    }
