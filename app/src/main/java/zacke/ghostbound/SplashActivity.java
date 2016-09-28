package zacke.ghostbound;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Activity class for the splash screen which is shown for a set duration
 * during startup. When duration ends this activity finishes and loads the
 * main activity.
 *
 * @author Zacke
 * @version 2016-09-28
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Animates the image logo and title text.
        Animation animMoveUp = AnimationUtils.loadAnimation(this, R.anim
                .move_up);
        Animation animFadeIn = AnimationUtils.loadAnimation(this, R.anim
                .fade_in);
        Animation animFadeInSlow = AnimationUtils.loadAnimation(this, R.anim
                .fade_in_slow);
        ImageView imageLogo = (ImageView) findViewById(R.id.splashLogo);
        TextView textTitle = (TextView) findViewById(R.id.title);
        TextView textZakrisson = (TextView) findViewById(R.id.zakrissonText);
        imageLogo.setAnimation(animMoveUp);
        textTitle.setAnimation(animFadeIn);
        textZakrisson.setAnimation(animFadeInSlow);

        // Handler which calls the main activity after a set duration.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity
                        .class));
                finish();
            }
        }, 5000);
    }
}
