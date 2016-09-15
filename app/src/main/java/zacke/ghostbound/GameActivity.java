package zacke.ghostbound;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class GameActivity extends AppCompatActivity {

    GameView game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game);
        game = new GameView(this);
        setContentView(game);
    }

    @Override
    protected void onPause() {
        super.onPause();
        game.pause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        game.resume();
    }

    public void animate(View view) {

        ImageView fireball = (ImageView) findViewById(R.id.flame1);
        ImageView ghost = (ImageView) findViewById(R.id.animateGhost);

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
}
