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
    private Button red, blue, green, yellow, play;
    private TextView score, bestScore, level;
    private Button[] color;
    private boolean running;
    private int buttonTag ;
    private int count = 1;
    AlphaAnimation animation = new AlphaAnimation(1f, 0f);
    int x;
    Random random;
    ArrayList<Integer> arrayListXvALUE;
    ArrayList<Integer> userChoiceList;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupview();
        //storage the color inside the button
        color = new Button[]{red, blue, green, yellow};
        //Generate Random number to access to the color
        random = new Random();
        //storge the color in the sequence to use them later .
        arrayListXvALUE = new ArrayList<>();
        //storge the user choice to compare later with arryListXValue;
        userChoiceList = new ArrayList<>();
        //to flash the button
        animation.setDuration(1000);
    }

    public void setupview() {
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
            // android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
    private void simon(View view) {
        running = true;
        while (running) {
            x = random.nextInt(4);  //Generate Random number
            arrayListXvALUE.add(x);
            Toast.makeText(this, "level number :"+arrayListXvALUE.size(), Toast.LENGTH_SHORT).show();
            Log.e("random number is", "" + x);  //Storage the Random number inside the ArrayList
            for (int value : arrayListXvALUE) {
                Log.e("array list value is: ", "" + value);    //iterate through the ArrayList Value;
                switch (color[value].getId()) {
                    case R.id.red:
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                red.startAnimation(animation);
                            }
                        }, 2000 * count);
                        count++;
                        break;
                    case R.id.blue:
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                blue.startAnimation(animation);
                            }
                        }, 2000 * count);
                        count++;
                        break;
                    case R.id.green:
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                green.startAnimation(animation);
                            }
                        }, 2000 * count);
                        count++;
                        break;
                    case R.id.yellow:
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                yellow.startAnimation(animation);
                            }
                        }, 2000 * count);
                        count++;
                        break;
                }
            }
            running = false;
            Log.e("r change after while to", "" + running);
            userChoiceList.clear();
        }
    }
    public void clickCheck(View view) {
        boolean test = false;
        count=1;   //to rest the count to keep the Animation in good time  when ill back again to the simon for the next levels
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
        Log.e("on check bee called", "now");
        Log.e("userChoiceLIST SIZE IS:", "" + userChoiceList.size());
        if (arrayListXvALUE.size() == userChoiceList.size()) {
            test = true;
            Log.e(" both array same size :", "" + test);
            if (test) {
                Log.e(" test door open 2check:", "" + test);
                if (arrayListXvALUE.equals(userChoiceList)) {
                    Toast.makeText(this, "Great job , Next level: " + userChoiceList.size()+1, Toast.LENGTH_LONG).show();
                    score.setText(""+userChoiceList.size());
                    running = true;
                    simon(view);
                } else {
                    running = false;
                    Toast.makeText(this, "Not Match,Game over", Toast.LENGTH_LONG).show();
                    play.setTag(1);
                    play.setText("play");
                    score.setText("");
                    arrayListXvALUE.clear();
                }
            }
        }
    }
}