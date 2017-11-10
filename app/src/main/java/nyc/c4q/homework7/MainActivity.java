package nyc.c4q.homework7;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
public class MainActivity extends AppCompatActivity {
    private ImageButton red;
    private ImageButton green;
    private ImageButton blue;
    private ImageButton yellow;
    private Button play;
    private int count = 1;
    private Score score;
    private TextView bestScoreView;
    private TextView levelView;
    private int bestScore;
    Animation animation = new AlphaAnimation(1f, 0f);
    private static final String KEY_ARR = "arrayList";
    private static final String TEMP_ARR = "temp";
    private static final String KEY = "key";
    private static final String SHARED_PREFERENCES = "savedScore";
    private static final String KEY_BESTSCORE = "savedBestScore";
    private MediaPlayer gameOver;
    private MediaPlayer mpGame;
    private MediaPlayer sound;
    Queue<Integer> queue = new LinkedList<>();
    ArrayList<Integer> deletedNum = new ArrayList<>();
    ArrayList<Integer> temp = new ArrayList<>();
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY, score);
        outState.putIntegerArrayList(KEY_ARR, deletedNum);
        temp.addAll(queue);
        outState.putIntegerArrayList(TEMP_ARR, temp);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        red = (ImageButton) findViewById(R.id.red);
        blue = (ImageButton) findViewById(R.id.blue);
        green = (ImageButton) findViewById(R.id.green);
        yellow = (ImageButton) findViewById(R.id.yellow);
        play = (Button) findViewById(R.id.play);
        levelView = (TextView) findViewById(R.id.socre_view);
        bestScoreView = (TextView) findViewById(R.id.bestscore);
        animation.setDuration(80);
        bestScoreView.setText(String.valueOf(retrieveBestScore()));
        gameOver = MediaPlayer.create(this, R.raw.gameover);
        mpGame = MediaPlayer.create(this, R.raw.game);
        sound = MediaPlayer.create(this, R.raw.sound);
        if (savedInstanceState != null) {
            score = savedInstanceState.getParcelable(KEY);
            levelView.setText("SCORE" + score.getValue());
            deletedNum = savedInstanceState.getIntegerArrayList(KEY_ARR);
            temp = savedInstanceState.getIntegerArrayList(TEMP_ARR);
            queue.addAll(temp);
        } else {
            score = new Score(0);
        }
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queue.clear();
                deletedNum.clear();
                randomNumber();
            }
        });
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound.start();
                int number = addToArrayList(red);
                if (number != 0) {
                    Toast.makeText(getApplicationContext(), "you lost!", Toast.LENGTH_LONG).show();
                    saveBestSCore();
                    score.setValue(0);
                    gameOver.start();
                } else if (queue.size() == 0) {
                    startNewRound();
                }
            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound.start();
                int number = addToArrayList(blue);
                if (number != 1) {
                    Toast.makeText(getApplicationContext(), "you lost!", Toast.LENGTH_LONG).show();
                    saveBestSCore();
                    score.setValue(0);
                    gameOver.start();
                } else if (queue.size() == 0) {
                    startNewRound();
                }
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound.start();
                int number = addToArrayList(green);
                if (number != 2) {
                    Toast.makeText(getApplicationContext(), "you lost!", Toast.LENGTH_LONG).show();
                    saveBestSCore();
                    score.setValue(0);
                    gameOver.start();
                } else if (queue.size() == 0) {
                    startNewRound();
                }
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound.start();
                int number = addToArrayList(yellow);
                if (number != 3) {
                    Toast.makeText(getApplicationContext(), "you lost!", Toast.LENGTH_LONG).show();
                    saveBestSCore();
                    score.setValue(0);
                    gameOver.start();
                } else if (queue.size() == 0) {
                    startNewRound();
                }
            }
        });
    }
    public void randomNumber() {
        Random r = new Random();
        int n = r.nextInt(4);
        queue.add(n);
        for (int i : queue) {
            if (i == 0) {
                count++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        red.startAnimation(animation);
                        mpGame.start();
                    }
                }, 500 * count);
            } else if (i == 1) {
                count++;
                mpGame.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        blue.startAnimation(animation);
                        mpGame.start();
                    }
                }, 500 * count);
            } else if (i == 2) {
                count++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        green.startAnimation(animation);
                        mpGame.start();
                    }
                }, 500 * count);
            } else if (i == 3) {
                count++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        yellow.startAnimation(animation);
                        mpGame.start();
                    }
                }, 500 * count);
            }
        }
        count = 1;
    }
    public void startNewRound() {
        int x = score.getValue() + 1;
        score.setValue(x);
        levelView.setText("level " + x);
        queue.addAll(deletedNum);
        deletedNum.clear();
        randomNumber();
    }
    public int addToArrayList(ImageButton button) {
        button.startAnimation(animation);
        int number = queue.poll();
        deletedNum.add(number);
        return number;
    }
    public void saveBestSCore() {
        int currentScore = score.getValue();
        if (currentScore > bestScore) {
            bestScoreView.setText(String.valueOf(currentScore));
            SharedPreferences prefs = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(KEY_BESTSCORE, currentScore);
            editor.apply();
            bestScore = currentScore;
        }
    }
    public int retrieveBestScore() {
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        int retrievedScore = preferences.getInt(KEY_BESTSCORE, 0);
        return retrievedScore;
    }
}