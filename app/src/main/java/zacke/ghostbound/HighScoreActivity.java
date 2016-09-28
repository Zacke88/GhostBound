package zacke.ghostbound;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * This activity class is used to show the high scores from the SQL database
 * for the user.
 *
 * @author Zacke
 * @version 2016-09-28
 */
public class HighScoreActivity extends AppCompatActivity {

    HighScoreDB DB;
    TextView scoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        scoreView = (TextView) findViewById(R.id.dataText);
        showData();
    }

    /**
     * Reads all data from the database and shows top 5 scores for the user.
     */
    public void showData() {
        DB = new HighScoreDB(this);
        Cursor data = DB.getData();

        // If no data is stored in database
        if(data.getCount() == 0) {
            scoreView.setText("No data to show");
            return;
        }

        StringBuffer dataString = new StringBuffer();
        int i = 0;
        while(data.moveToNext()) {
            if(i >= 5) {
                break;
            }
            dataString.append(data.getInt(2) + "  " +
                    String.valueOf(data.getString(1)) +
                    "\n");
            i++;
        }
        scoreView.setText(dataString.toString());
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
