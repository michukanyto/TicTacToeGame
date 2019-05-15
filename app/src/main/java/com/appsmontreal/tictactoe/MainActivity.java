package com.appsmontreal.tictactoe;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    private static final String FINALMESSAGE = "GAME FINISHED!" ;
    private static final String WON = " WON THE GAME!!!" ;
    int player = 1; //player = 1 => android   or player = 0 => apple
    int[] gridPosition = {2,2,2,2,2,2,2,2,2};
    int[][] winPosition = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    int positionTag;
    boolean statusGame = true;
    Button playAgainButton;
    Button exitButton;
    TextView textViewWinner;
    MediaPlayer mediaPlayer;


    public enum Winner{
        APPLE,
        ANDROID
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        exitButton = (Button) findViewById(R.id.exitButton);
        textViewWinner = (TextView) findViewById(R.id.textViewWinner);
        mediaPlayer = MediaPlayer.create(this,R.raw.winner);
    }

    public void goingDown (View view){
        ImageView counter = (ImageView) view;
        positionTag = Integer.parseInt(counter.getTag().toString());;

            Log.i("position", Integer.toString(positionTag));


            if(gridPosition[positionTag] == 2 && statusGame) {
                gridPosition[positionTag] = player;
                counter.setTranslationY(-1500);

                if (player == 1) {
                    player = 0;
                    counter.setImageResource(R.drawable.android);
                    gridPosition[positionTag] = 1;
                } else {
                    player = 1;
                    counter.setImageResource(R.drawable.apl);
                    gridPosition[positionTag] = 0;
                }
                counter.animate().rotation(3600).translationYBy(1500).setDuration(500);


                for (int[] winPosition : winPosition) {

                    if (gridPosition[winPosition[0]] == gridPosition[winPosition[1]] && gridPosition[winPosition[1]] == gridPosition[winPosition[2]] && gridPosition[winPosition[0]] != 2) {
                        statusGame = false;
                        // Someone has won!
                       Toast.makeText(this, FINALMESSAGE, Toast.LENGTH_LONG).show();
//                        statusGame = false;

                        String winner = "";

                        if (player == 1) {

                            winner = Winner.APPLE.name();

                        } else {

                            winner = Winner.ANDROID.name();

                        }
                        playSound(mediaPlayer);
                        textViewWinner.animate().rotation(3600).setDuration(600);
                        textViewWinner.setText(winner + WON);
                        playAgainButton.setVisibility(view.VISIBLE);
                        textViewWinner.setVisibility(view.VISIBLE);

                    }

                }
            }

    }

    public void playAgain(View view) {
        mediaPlayer = MediaPlayer.create(this,R.raw.woosha);
        playSound(mediaPlayer);
        initialize();


        playAgainButton.setVisibility(View.INVISIBLE);

        textViewWinner.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int x =0 ; x < gridLayout.getChildCount(); x++) {
            ImageView counter = (ImageView) gridLayout.getChildAt(x);
            counter.setImageDrawable(null);
        }

        for (int i=0; i < gridPosition.length; i++) {
            gridPosition[i] = 2;
        }

        player = 1;
        statusGame = true;

    }

    public void exitGame(View view){
        mediaPlayer = MediaPlayer.create(this,R.raw.goodbye);
        playSound(mediaPlayer);
        finish();
    }

    private void playSound(MediaPlayer play){
        play.start();
        play.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaP) {
                mediaP.release();
            };
        });

    }


}
