package zacke.ghostbound;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * The main activity which holds the main menu for the game. This is used for
 * letting the player choose between starting a new game or see current
 * high scores within the game.
 *
 * @author Zacke
 * @version 2016-09-28
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Class tied to the new game text and starts the game activity when the
     * player press this text field.
     *
     * @param view The view which was clicked to call this class.
     */
    public void newGame(View view) {
        startActivity(new Intent(MainActivity.this, GameActivity.class));
        finish();
    }

    /**
     * Class tied to the high score text and starts the high score activity when
     * the
     * player press this text field.
     *
     * @param view The view which was clicked to call this class.
     */
    public void highScore(View view) {
        startActivity(new Intent(MainActivity.this, HighScoreActivity.class));
        finish();
    }
}
