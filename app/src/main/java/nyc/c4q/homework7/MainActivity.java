package nyc.c4q.homework7;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static nyc.c4q.homework7.R.color.light_red;

public class MainActivity extends AppCompatActivity {
   Button red,blue,green,yellow,play;
   TextView score,bestScore,level;
   Button[] color={red,blue,green,yellow};
   boolean running =false;
   int buttonTag=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //taking user input to check later
        red= (Button) findViewById(R.id.red);
        blue = (Button) findViewById(R.id.blue);
        green= (Button) findViewById(R.id.green);
        yellow= (Button) findViewById(R.id.yellow);


        //for the stauts of game
        score= (TextView) findViewById(R.id.socre);
        bestScore= (TextView) findViewById(R.id.bestscore);
        level= (TextView) findViewById(R.id.level);

       //to stratthe play ot to stop
        play= (Button) findViewById(R.id.play);
        play.setTag(1);



    }

    public void onplay(View view){

        buttonTag= (int) play.getTag();
            if(buttonTag==1){
                play.setTag(2);
                red.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.light_red));
                delayRed(2);
                play.setText("Quit game");

        }
        else{
                if(buttonTag==2){
                    play.setTag(1);
                    play.setText("play");

                     Toast.makeText(getBaseContext(), "Game Finsh: " , Toast.LENGTH_SHORT).show();
                }

            }




    }


















//
//
//    public void onplay(View view){
//        buttonTag= (int) play.getTag();
//        if(buttonTag==1){
//            play.setTag(2);
//            running=true;
//            int i=1;
//
//                red.setBackgroundColor(getResources().getColor(R.color.light_red));
//                delay(2);
//                blue.setBackgroundColor(getResources().getColor(R.color.light_blue));
//
//
//
//
//
//           // yellow.setBackgroundColor(getResources().getColor(R.color.light_yellow));
//
//
//           // green.setBackgroundColor(getResources().getColor(R.color.light_green));
//
//
//
//
//        }else if(buttonTag==2){
//                play.setTag(1);
//                //running=false;
//            red.setBackgroundColor(getResources().getColor(R.color.red));
//            blue.setBackgroundColor(getResources().getColor(R.color.blue));
//            yellow.setBackgroundColor(getResources().getColor(R.color.yellow));
//            green.setBackgroundColor(getResources().getColor(R.color.green));
//
//
//            }
//        }
//
//
//
////
//
//

























    private void delayRed(int totalTimesOfFlashes){
        for (int i=1; i<totalTimesOfFlashes; i++) {
            final int finalJ = i;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Your code here

                    red.setBackgroundColor(getResources().getColor(R.color.red));


                  //  Toast.makeText(getBaseContext(), "Flash: " + finalJ, Toast.LENGTH_SHORT).show();
                }
            }, 1000*totalTimesOfFlashes);
        }
    }


//
        
    }


















