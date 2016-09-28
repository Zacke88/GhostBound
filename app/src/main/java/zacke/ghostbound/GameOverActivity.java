package zacke.ghostbound;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which is run after a game has ended. It shows the player
 * score and also saves the score in a database if the player wants to by
 * clicking the "Save Score" text.
 *
 * @author Zacke
 * @version 2016-09-28
 */
public class GameOverActivity extends AppCompatActivity {

    int score = 0;
    HighScoreDB DB;
    String name = "";
    TextView scoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        //Gets the player score from the previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            score = Integer.parseInt(extras.getString("score"));
        }
        scoreView = (TextView) findViewById(R.id.scoreInteger);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Method tied to the save score text and saves the player score and
     * player name to a SQL database. It creates a new alert dialog which it
     * shows to the user where a user can enter the username to tie to the
     * player score.
     *
     * @param view The view which was clicked to call this class.
     */
    public void saveScore(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder
                (GameOverActivity.this);
        alertDialog.setTitle("High Score");
        alertDialog.setMessage("Enter Name");
        final EditText input = new EditText(GameOverActivity.this);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        alertDialog.setView(input);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // get user input and set it to name
                        name = String.valueOf(input.getText());
                        //Insert to database
                        insertToDB();
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    /**
     * Inserts data into the database. Shows a toast if it succeeded or failed.
     */
    public void insertToDB() {
        DB = new HighScoreDB(this);
        boolean inserted = DB.insertData(name, score);
        if (inserted) {
            Toast.makeText(GameOverActivity.this, "High score saved", Toast
                    .LENGTH_LONG).show();
            findViewById(R.id.saveScoreText).setVisibility(View.GONE);
        } else {
            Toast.makeText(GameOverActivity.this, "Error: Could not save high" +
                    " score", Toast
                    .LENGTH_LONG).show();
        }
    }

    /**
     * Method tied to the back text and starts the main activity when the
     * player press this text field.
     *
     * @param view The view which was clicked to call this class.
     */
    public void backFromGameOver(View view) {
        startActivity(new Intent(GameOverActivity.this, MainActivity.class));
        finish();
    }
}
