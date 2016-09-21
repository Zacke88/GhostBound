package zacke.ghostbound;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 *
 */
public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }

    /**
     * Class tied to the back text and starts the main activity when the
     * player press this text field.
     *
     * @param view The view which was clicked to call this class.
     */
    public void backFromCredits(View view) {
        startActivity(new Intent(CreditsActivity.this, MainActivity.class));
        finish();
    }
}
