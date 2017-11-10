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
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button play;
    private ImageButton red, blue, green, yellow;
    private TextView scoreView, bestScore;
    private Score score;
    private boolean running;
    private int buttonTag;
    private int count = 1;
    private static final String KEY_SCORE = "score";
    private static final String KEY_ARRAY = "arr";
    private static final String KEY_TEMP_ARRAY = "tempArray";
    private MediaPlayer gameOver;
    private MediaPlayer mpGame;
    private MediaPlayer sound;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String scoreBest = "";

    AlphaAnimation animation = new AlphaAnimation(1f, 0f);
    Random random;


    Queue<Integer> queue = new LinkedList<Integer>();
    ArrayList<Integer> deletedNum = new ArrayList<>();
    ArrayList<Integer> temp = new ArrayList<>();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int y = score.getValue();

        outState.putInt(KEY_SCORE, y);
        outState.putIntegerArrayList(KEY_ARRAY, deletedNum);
        outState.putIntegerArrayList(KEY_TEMP_ARRAY, temp);
        temp.addAll(queue);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupview();

        gameOver = MediaPlayer.create(this, R.raw.gameover);
        mpGame = MediaPlayer.create(this, R.raw.game);
        sound = MediaPlayer.create(this, R.raw.sound);
        random = new Random();
        animation.setDuration(100);
        score = new Score(0);

        sharedPreferences = getSharedPreferences("simon_values", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        scoreBest = sharedPreferences.getString("best_score", "0");
        bestScore.setText(scoreBest);

        if (savedInstanceState != null) {

            int savedScore = savedInstanceState.getInt(KEY_SCORE);
            score.setValue(savedScore);
            scoreView.setText(savedInstanceState.getString("level" + savedScore));
            deletedNum = savedInstanceState.getIntegerArrayList(KEY_ARRAY);
            temp = savedInstanceState.getIntegerArrayList(KEY_TEMP_ARRAY);
            queue.addAll(temp);

        }
    }

    public void onplay(final View view) {
        buttonTag = (int) play.getTag();
        if (buttonTag == 1) {
            play.setTag(2);
            play.setText("Quit game");
            clickCheck(view);
            deletedNum.clear();
            queue.clear();
            scoreView.setText("");
            simon();
        } else if (buttonTag == 2) {
            play.setTag(1);
            play.setText("play");
            deletedNum.clear();
            scoreView.setText("");
            Toast.makeText(getBaseContext(), "Game Finsh: ", Toast.LENGTH_SHORT).show();
        }
    }

    private void simon() {

        running = true;
        Random r = new Random();
        int next = r.nextInt(4);
        queue.add(next);

        for (Integer light : queue) {
            if (light == 0) {
                count++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        red.startAnimation(animation);
                        mpGame.start();
                    }
                }, 600 * count);

            } else if (light == 1) {
                count++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        blue.startAnimation(animation);
                        mpGame.start();
                    }
                }, 600 * count);

            } else if (light == 2) {
                count++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        green.startAnimation(animation);
                        mpGame.start();
                    }
                }, 600 * count);

            } else if (light == 3) {
                count++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        yellow.startAnimation(animation);
                        mpGame.start();
                    }
                }, 600 * count);

            }

        }
        count = 1;
        running = false;
//        Log.e("r change after while to", "" + running);
//        queue.clear();
    }


    public void clickCheck(View view) {
        switch (view.getId()) {
            case R.id.red:
                int number = addToArrayList(red);
                if (number != 0) {
                    Toast.makeText(getApplicationContext(), "You lose!", Toast.LENGTH_LONG).show();
                    red.startAnimation(animation);
                } else {

                    queue.add(0);
                    red.startAnimation(animation);
                    sound.start();
                    startNewRound();
                    break;

                }
            case R.id.blue:
                number = addToArrayList(blue);
                if (number != 1) {

                    Toast.makeText(getApplicationContext(), "You lose!", Toast.LENGTH_LONG).show();
                    blue.startAnimation(animation);
                } else {

                    queue.add(1);
                    blue.startAnimation(animation);
                    sound.start();
                    startNewRound();
                    break;

                }
            case R.id.green:
                number = addToArrayList(green);
                if (number != 2) {
                    Toast.makeText(getApplicationContext(), "You lose!", Toast.LENGTH_LONG).show();
                    green.startAnimation(animation);
                } else {

                    queue.add(2);
                    green.startAnimation(animation);
                    sound.start();
                    startNewRound();
                    break;

                }

            case R.id.yellow:
                number = addToArrayList(yellow);
                if (number != 3) {
                    Toast.makeText(getApplicationContext(), "You lose!", Toast.LENGTH_LONG).show();
                    yellow.startAnimation(animation);
                } else {
                    queue.add(3);
                    yellow.startAnimation(animation);
                    sound.start();
                    startNewRound();
                    break;

                }
        }

        Log.e("on check bee called", "now");
        Log.e("userChoiceLIST SIZE IS:", "" + temp.size());
        if (queue.size() == temp.size()) {
            if (queue.equals(temp)) {

                Toast.makeText(this, "Great job , Next level: " + (temp.size() + 1), Toast.LENGTH_SHORT).show();
                scoreView.setText("" + temp.size());
                running = true;
                if (Integer.parseInt(bestScore.getText().toString()) < temp.size()) {
                    editor.putString("best_score", (String.valueOf(temp.size()))).commit();
                    bestScore.setText(String.valueOf(temp.size()));
                }

                simon();
            } else {
                quiteGame();
            }
        }

    }

    private void quiteGame() {
        running = false;
        Toast.makeText(this, "Game Over!", Toast.LENGTH_LONG).show();
        gameOver.start();
        play.setTag(1);
        play.setText("play");
        scoreView.setText("");

        queue.clear();

    }

    public int addToArrayList(ImageButton button) {
        button.startAnimation(animation);
        int number = queue.poll();
        deletedNum.add(number);
        return number;
    }

    public void startNewRound() {
        int x = score.getValue() + 1;
        score.setValue(x);
        scoreView.setText("level" + x);
        queue.addAll(deletedNum);
        deletedNum.clear();
        simon();
    }

    public void setupview() {
        red = (ImageButton) findViewById(R.id.red);
        blue = (ImageButton) findViewById(R.id.blue);
        green = (ImageButton) findViewById(R.id.green);
        yellow = (ImageButton) findViewById(R.id.yellow);
        scoreView = (TextView) findViewById(R.id.socre_view);
        bestScore = (TextView) findViewById(R.id.bestscore);
        play = (Button) findViewById(R.id.play);
        play.setTag(1);
    }


}
