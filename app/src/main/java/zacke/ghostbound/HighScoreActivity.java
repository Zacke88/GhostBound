package zacke.ghostbound;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * This class is created when the game shows the current highscore list.
 */
public class HighScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
    }

    /**
     * Class tied to the back text and starts the main activity when the
     * player press this text field.
     *
     * @param view The view which was clicked to call this class.
     */
    public void backFromHighScore(View view) {
        startActivity(new Intent(HighScoreActivity.this, MainActivity.class));
        finish();
    }
}
