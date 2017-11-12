package nyc.c4q.homework7;

import android.content.SharedPreferences;
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
    private final String scoreNow = "scoreNow";
    private final String arrayListKey = "arrayListKey";
    AlphaAnimation animation = new AlphaAnimation(1f, 0f);
    Random random;
    ArrayList<Integer> arrayListXvALUE;
    ArrayList<Integer> userChoiceList;
    private MediaPlayer gameOver;
    private MediaPlayer mpGame;
    private MediaPlayer sound;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String scoreBest = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupview();
        gameOver = MediaPlayer.create(this, R.raw.gameover);
        mpGame = MediaPlayer.create(this, R.raw.game);
        sound = MediaPlayer.create(this, R.raw.sound);
        color = new ImageButton[]{red, blue, green, yellow};        //storage the color inside the button
        random = new Random();                                 //Generate Random number to access to the color
        arrayListXvALUE = new ArrayList<>();                   //storage the color in the sequence to use them later .
        userChoiceList = new ArrayList<>();                    //storage the user choice to compare later with arryListXValue;
        animation.setDuration(100);

        sharedPreferences = getSharedPreferences("simon_values", MODE_PRIVATE); //Acesss
        editor = sharedPreferences.edit();  //Edit

        scoreBest = sharedPreferences.getString("best_score", "0");//call back
        bestScore.setText(scoreBest);

        //to flash the button
        if (savedInstanceState != null) {

            score.setText(savedInstanceState.getString(scoreNow));
            level.setText(savedInstanceState.getString("level"));
            arrayListXvALUE = savedInstanceState.getIntegerArrayList(arrayListKey);

        }
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
            level.setText("Level 1");
            simon();
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

    private void simon() {

        running = true;
        Random r = new Random();
        int next = r.nextInt(4);
        red.setEnabled(false);
        blue.setEnabled(false);
        yellow.setEnabled(false);
        green.setEnabled(false);
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
        red.setEnabled(true);
        blue.setEnabled(true);
        yellow.setEnabled(true);
        green.setEnabled(true);
    }


    public void clickCheck(View view) {
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
                level.setText("Level " + (userChoiceList.size() + 1));
                running = true;
                if (Integer.parseInt(bestScore.getText().toString()) < userChoiceList.size()) {
                    editor.putString("best_score", (String.valueOf(userChoiceList.size()))).commit();
                    bestScore.setText(String.valueOf(userChoiceList.size()));
                }

                simon();
            } else {
                quiteGame();
            }
        }

    }

    private void quiteGame() {
        running = false;
        Toast.makeText(this, "Game Over Yeah\n(ﾉ◕ヮ◕)ﾉ*:･ﾟ✧!", Toast.LENGTH_LONG).show();
        gameOver.start();
        play.setTag(1);
        play.setText("play");
        score.setText("");

        arrayListXvALUE.clear();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("scoreNow", score.getText().toString());
        outState.putString("level", level.getText().toString());
        outState.putIntegerArrayList("arrayListKey", arrayListXvALUE);
        outState.putIntegerArrayList("userArr", userChoiceList);
        super.onSaveInstanceState(outState);
    }
}
