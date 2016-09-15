package zacke.ghostbound;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Zacke on 2016-09-15.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);

    }

    @Override


}
