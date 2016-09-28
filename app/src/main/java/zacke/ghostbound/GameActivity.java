package zacke.ghostbound;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * Activity class which is created when a new game starts. It sets the
 * GamePanel class as it's content view and also handles the in-game music.
 *
 * @author Zacke
 * @version 2016-09-28
 */
public class GameActivity extends AppCompatActivity {

    private MediaPlayer gameMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GamePanel view = new GamePanel(this);
        setContentView(view);
        gameMusic = MediaPlayer.create(this, R.raw.music);
        gameMusic.setVolume(0.8f, 0.8f);
        gameMusic.setLooping(true);
        gameMusic.start();
    }

    /**
     * Pauses the game music as the activity pauses.
     */
    @Override
    protected void onPause() {
        super.onPause();
        gameMusic.pause();

    }

    /**
     * Resumes the game music as the activity resumes.
     */
    @Override
    protected void onResume() {
        super.onResume();
        gameMusic.start();
    }
}
