package zacke.ghostbound;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    String score = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            score = extras.getString("Score");
            //The key argument here must match that used in the other activity
        }
        TextView scoreView = (TextView) findViewById(R.id.scoreInteger);
        scoreView.setText(score);
    }

    /**
     * Class tied to the back text and starts the main activity when the
     * player press this text field.
     *
     * @param view The view which was clicked to call this class.
     */
    public void backFromGameOver(View view) {
        startActivity(new Intent(GameOverActivity.this, MainActivity.class));
        finish();
    }
}
