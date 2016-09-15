package zacke.ghostbound;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.Random;

/**
 * Created by Zacke on 2016-09-15.
 */
public class GameView extends SurfaceView implements Runnable {

    Thread thread = null;
    SurfaceHolder holder;
    boolean gameRunning = false;

    Bitmap fire = BitmapFactory.decodeResource(getResources(), R.drawable.flame64);
    int x = 0;
    int y = 0;
    int randMin = 0;
    int randMax = 4;
    int startX;
    Rect ourRect = new Rect();
    Paint blue = new Paint();
    Paint p = new Paint();
    Random r = new Random();

    public GameView(Context context) {
        super(context);
        holder = getHolder();
    }

    @Override
    public void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        ourRect.set(0, 0, canvas.getWidth(), canvas.getHeight());
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

        startX = r.nextInt(randMax - randMin + 1) + randMin;
        startX = Math.round((startX*(canvas.getWidth()-fire.getWidth())/randMax));
        canvas.drawBitmap(fire, startX, y, p);
        invalidate();

    }


    @Override
    public void run() {

        if(gameRunning) {

        }

    }


    public void pause() {
        gameRunning = false;
        while(true) {
            try {
                thread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        thread = null;
    }

    public void resume() {
        gameRunning = true;
        thread = new Thread(this);
        thread.start();
    }

}
