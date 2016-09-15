package zacke.ghostbound;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import java.util.Random;

/**
 * Created by Zacke on 2016-09-15.
 */
public class DrawFire extends View {

    Bitmap fire;
    int x;
    int y;
    int randMin = 0;
    int randMax = 4;
    int startX;

    public DrawFire(Context context) {
        super(context);
        fire = BitmapFactory.decodeResource(getResources(), R.drawable.flame64);
        x = 0;
        y = 0;

    }

    @Override
    public void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        Rect ourRect = new Rect();
        ourRect.set(0, 0, canvas.getWidth(), canvas.getHeight()/2);

        Paint blue = new Paint();
        blue.setColor(getResources().getColor(R.color.colorPrimaryDark));
        blue.setStyle(Paint.Style.FILL);

        canvas.drawRect(ourRect, blue);

        if(x < canvas.getWidth()) {
            x += 0;
        } else {
            x = 0;
        }
        if(y < canvas.getHeight()) {
            y += 5;
        } else {
            y = 0;
        }
        Random r = new Random();

        startX = r.nextInt(randMax - randMin + 1) + randMin;
        Log.e("random", String.valueOf(startX));
        Log.e("random", String.valueOf(canvas.getWidth()-fire.getWidth()));
        startX = Math.round((startX*(canvas.getWidth()-fire.getWidth())/randMax));
        Log.e("hej", String.valueOf(startX));
        canvas.drawBitmap(fire, startX, 0, new Paint());
        invalidate();



    }





}
