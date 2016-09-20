package zacke.ghostbound;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener {

    GamePanel game;
    MediaPlayer gameMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new GamePanel(this);
        game.setOnTouchListener(this);
        setContentView(game);
        gameMusic = MediaPlayer.create(GameActivity.this, R.raw.gamemusic);
    }

    @Override
    protected void onPause() {
        super.onPause();
        game.pause();
        gameMusic.pause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        game.resume();
        gameMusic.start();
    }
/*
    public void animate(View view) {

        ImageView fireball = (ImageView) findViewById(R.id.fire64);
        ImageView ghost = (ImageView) findViewById(R.id.ghost64);

        Log.e("hej", String.valueOf(fireball.getBottom()));

        TranslateAnimation animation = new TranslateAnimation(0, 0,
                0, ghost.getBottom()-fireball.getBottom());          //  new
        // TranslateAnimation(xFrom,
        // xTo,
        // yFrom,yTo)
        animation.setDuration(5000);  // animation duration
        animation.setRepeatCount(5);  // animation repeat count
        animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
        //animation.setFillAfter(true);


        fireball.startAnimation(animation);  // start animation

    }
    */

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
