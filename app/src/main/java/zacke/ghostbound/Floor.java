package zacke.ghostbound;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Zacke on 2016-09-17.
 */
public class Floor extends GameObject {

    private int gameWidth;
    int randMin = 0;
    int randMax = 2;
    int value;
    private Random rand = new Random();
    private Bitmap image;
    private List<Floor> floors = new ArrayList<Floor>();
    private Floor floor1;
    private Floor floor2;
    private Floor floor3;
    private Floor floor4;
    private Floor floor5;
    private Floor floor6;
    private Floor floor7;
    private Floor floor8;

    public Floor(Bitmap image, int gameWidth) {
        this.gameWidth = gameWidth;
        this.image = image;

    }

    public void draw(Canvas canvas) {
            canvas.drawBitmap(image, x, y, null);

    }
}
