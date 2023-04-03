package com.example.ihuntwithjavalins;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.net.URL;


/**
 * A java version of the php script for MonsterID by splitbrain as taken from https://github.com/splitbrain/monsterID/blob/master/monsterid.php
 * */
public class MonsterID {

    private static final int SIZE = 120;

    private static final String[] PARTS = { "arms", "body", "eyes", "hair", "legs", "mouth" };
    private static final int[] NUM_PARTS = { 5, 15, 15, 5, 5, 10 };
    private static final String PARTS_PATH = "parts/";

    private static Random rand = new Random();

    public static void generate(OutputStream out, String hash, int size, AssetManager assetManager) throws IOException {
        // use hash code as seed
        long seed = hash.hashCode();
        rand.setSeed(seed);

        // throw the dice for body parts
        int[] parts = new int[NUM_PARTS.length];
        for (int i = 0; i < NUM_PARTS.length; i++) {
            parts[i] = rand.nextInt(NUM_PARTS[i]) + 1;
        }

        // create backgound
        Bitmap monster = Bitmap.createBitmap(SIZE, SIZE, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(monster);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawRect(new Rect(0, 0, SIZE, SIZE), paint);

        // add parts
        for (int i = 0; i < PARTS.length; i++) {
            String part = PARTS[i];
            int num = parts[i];
            String filename = PARTS_PATH + part + "_" + num + ".png";

            Bitmap image = BitmapFactory.decodeStream(assetManager.open(filename));

            if (image == null) {
                throw new IOException("Failed to load this " + filename);
            }
            canvas.drawBitmap(image, null, new Rect(0, 0, SIZE, SIZE), null);

            // color the body
            if (part.equals("body")) {
                rand.setSeed(seed); // use same seed for coloring
                paint.setColor(Color.rgb(rand.nextInt(215) + 20, rand.nextInt(215) + 20, rand.nextInt(215) + 20));
                canvas.drawRect(new Rect(SIZE / 2 - 10, SIZE / 2 - 10, SIZE / 2 + 10, SIZE / 2 + 10), paint);
            }
        }

        // restore random seed
        rand = new Random();

        // resize if needed, then output
        if (size > 0 && size < 400) {
            Bitmap scaled = Bitmap.createScaledBitmap(monster, size, size, true);
            scaled.compress(Bitmap.CompressFormat.PNG, 100, out);
        } else if (size <= 0) {
            monster.compress(Bitmap.CompressFormat.PNG, 100, out);
        } else if (size >= 400) {
            throw new IllegalArgumentException("Image size too large: " + size);
        }
    }

    public static void generateAndSetImage(View view, String hash) {
        AssetManager assetManager = view.getContext().getAssets();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            generate(baos, hash, 0, assetManager);
        } catch (IOException e) {
            Toast.makeText(view.getContext(), "Failed to generate monster image", Toast.LENGTH_SHORT).show();
            return;
        }
        byte[] monsterData = baos.toByteArray();
        Bitmap monsterBitmap = BitmapFactory.decodeByteArray(monsterData, 0, monsterData.length);

        if (view instanceof ImageView) {
            ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_CROP);
            ((ImageView) view).setImageBitmap(monsterBitmap);
        } else if (view instanceof ImageButton) {
            ((ImageButton) view).setScaleType(ImageView.ScaleType.CENTER_CROP);
            ((ImageButton) view).setImageBitmap(monsterBitmap);
        } else {
            throw new IllegalArgumentException("View must be either an ImageView or ImageButton");
        }
    }



}
