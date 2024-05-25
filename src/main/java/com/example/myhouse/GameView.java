package com.example.myhouse;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

public class GameView extends View {
    int money;
    int level = -1;
    int shopPage = 1;
    Timer timer;
    int[] spots = null;
    int[] spotsDurability = null;
    int dishwashingLiquidLevel;
    int wateringCanLevel;
    int fryingPanLevel;
    int levelTwoStep = 1;
    int levelThreeStep = 1;
    int seedProgress = 0;
    int seedLimit = 0;
    int seedLimitWidth = 150;
    int shovelAmount = 0;
    int shovelCurrent = 0;
    int shovelProgress = 0;
    boolean shovelClicked = false;
    int eggPower = 0;
    int eggLimit = 0;
    int eggLimitWidth = 150;
    int eggsBroken = 0;
    boolean eggReverse = false;
    int omeletteCookingProgress = 0;
    SharedPreferences gameData;
    SharedPreferences.Editor editor;
    int whiskAmount = 6;
    int whiskCurrent = 0;
    int whiskProgress = 0;
    boolean whiskClicked = false;
    public GameView(Context context) {
        super(context);
        gameData = context.getSharedPreferences("gameData", Context.MODE_PRIVATE);
        editor = gameData.edit();
        money = gameData.getInt("money", 0);
        dishwashingLiquidLevel = gameData.getInt("dishwashingLiquidLevel", 1);
        wateringCanLevel = gameData.getInt("wateringCanLevel", 0);
        fryingPanLevel = gameData.getInt("fryingPanLevel", 0);
        timer = new Timer(Integer.MAX_VALUE, 100);
        timer.start();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        if (level == -1) {
            canvas.drawColor(0xFF000068);
            paint.setColor(0xFFC86EB9);
            canvas.drawRect(new Rect(300, 670, getWidth() - 300, 790), paint);
            canvas.drawCircle(300, 730, 60, paint);
            canvas.drawCircle(getWidth() - 300, 730, 60, paint);
            canvas.drawRect(new Rect(300, 1050, getWidth() - 300, 1170), paint);
            canvas.drawCircle(300, 1110, 60, paint);
            canvas.drawCircle(getWidth() - 300, 1110, 60, paint);
            paint.setColor(0xFF333333);
            canvas.drawRect(new Rect(getWidth() - 550, 50, getWidth() - 50, 170), paint);
            paint.setColor(0xFFFFFFFF);
            paint.setTextSize(60.0f);
            canvas.drawText(String.valueOf(money), getWidth() - 410, 130, paint);
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.money), null, new Rect(getWidth() - 550, 50, getWidth() - 432, 170), paint);
            canvas.drawText("Задания", getWidth() / 2 - 120, 750, paint);
            canvas.drawText("Магазин", getWidth() / 2 - 120, 1130, paint);
        } else if (level == 0) {
            canvas.drawColor(0xFF000068);
            paint.setColor(0xFF333333);
            canvas.drawRect(new Rect(getWidth() - 550, 50, getWidth() - 50, 170), paint);
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.money), null, new Rect(getWidth() - 550, 50, getWidth() - 432, 170), paint);
            paint.setColor(0xFFC86EB9);
            canvas.drawRect(new Rect((int) (getWidth()*0.1), (int) (getHeight()*0.1), (int) (getWidth()*0.9), (int) (getHeight()*0.16)), paint);
            if (wateringCanLevel==0) paint.setColor(0xFF676767);
            canvas.drawRect(new Rect((int) (getWidth()*0.1), (int) (getHeight()*0.2), (int) (getWidth()*0.9), (int) (getHeight()*0.26)), paint);
            if (fryingPanLevel==0) paint.setColor(0xFF676767);
            else paint.setColor(0xFFC86EB9);
            canvas.drawRect(new Rect((int) (getWidth()*0.1), (int) (getHeight()*0.3), (int) (getWidth()*0.9), (int) (getHeight()*0.36)), paint);
            paint.setColor(0xFFC86EB9);
            canvas.drawCircle(getWidth()*0.1f, getHeight()*0.13f, getHeight()*0.03f, paint);
            canvas.drawCircle(getWidth()*0.9f, getHeight()*0.13f, getHeight()*0.03f, paint);
            if (wateringCanLevel==0) paint.setColor(0xFF676767);
            canvas.drawCircle(getWidth()*0.1f, getHeight()*0.23f, getHeight()*0.03f, paint);
            canvas.drawCircle(getWidth()*0.9f, getHeight()*0.23f, getHeight()*0.03f, paint);
            if (fryingPanLevel==0) paint.setColor(0xFF676767);
            else paint.setColor(0xFFC86EB9);
            canvas.drawCircle(getWidth()*0.1f, getHeight()*0.33f, getHeight()*0.03f, paint);
            canvas.drawCircle(getWidth()*0.9f, getHeight()*0.33f, getHeight()*0.03f, paint);
            paint.setColor(0xFFC86EB9);
            canvas.drawCircle(100, 100, 80, paint);
            paint.setColor(0xFFC86EB9);
            paint.setColor(0xFFFFFFFF);
            paint.setTextSize(60.0f);
            canvas.drawText(String.valueOf(money), getWidth() - 410, 130, paint);
            canvas.drawText("1. Помыть посуду", getWidth() / 2 - 240, getHeight()*0.14f, paint);
            if (wateringCanLevel==0) {
                canvas.drawText("Нужен предмет Лейка", getWidth() / 2 - 310, getHeight() * 0.24f, paint);
            } else {
                canvas.drawText("2. Вырастить цветы", getWidth() / 2 - 278, getHeight() * 0.24f, paint);
            }
            if (fryingPanLevel==0) {
                canvas.drawText("Нужен предмет Сковорода", getWidth() / 2 - 380, getHeight()*0.34f, paint);
            } else {
                canvas.drawText("3. Приготовить омлет", getWidth() / 2 - 305, getHeight() * 0.34f, paint);
            }
            paint.setTextSize(160.0f);
            canvas.drawText("←", 20, 127, paint);
        } else if (level == 1) {
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.sink), null, new Rect(0, 0, getWidth(), getHeight()), paint);
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.plate), null, new Rect(0, getHeight()/2-530, getWidth(), getHeight()/2+530), paint);
            paint.setColor(0xFFC86EB9);
            canvas.drawCircle(100, 100, 80, paint);
            paint.setColor(0xFFFFFFFF);
            paint.setTextSize(160.0f);
            canvas.drawText("←", 20, 127, paint);
            if (spots==null) spots = new int[]{(int) (Math.random() * (getWidth()/2))+getWidth()/4, (int) (Math.random() * (getHeight()/3))+getHeight()/3, (int) (Math.random() * (getWidth()/2))+getWidth()/4, (int) (Math.random() * (getHeight()/3))+getHeight()/3, (int) (Math.random() * (getWidth()/2))+getWidth()/4, (int) (Math.random() * (getHeight()/3))+getHeight()/3, (int) (Math.random() * (getWidth()/2))+getWidth()/4, (int) (Math.random() * (getHeight()/3))+getHeight()/3, (int) (Math.random() * (getWidth()/2))+getWidth()/4, (int) (Math.random() * (getHeight()/3))+getHeight()/3, (int) (Math.random() * (getWidth()/2))+getWidth()/4, (int) (Math.random() * (getHeight()/3))+getHeight()/3, (int) (Math.random() * (getWidth()/2))+getWidth()/4, (int) (Math.random() * (getHeight()/3))+getHeight()/3, (int) (Math.random() * (getWidth()/2))+getWidth()/4, (int) (Math.random() * (getHeight()/3))+getHeight()/3, (int) (Math.random() * (getWidth()/2))+getWidth()/4, (int) (Math.random() * (getHeight()/3))+getHeight()/3, (int) (Math.random() * (getWidth()/2))+getWidth()/4, (int) (Math.random() * (getHeight()/3))+getHeight()/3};
            if (spotsDurability==null) spotsDurability = new int[]{10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
            for (int i = 0; i < spots.length; i += 2) {
                if (spots[i] != 0 && spots[i+1] != 0) {
                    if (spotsDurability[i/2] == 10)
                        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.spot10), null, new Rect(spots[i], spots[i + 1], spots[i] + 90, spots[i + 1] + 90), paint);
                    if (spotsDurability[i/2] == 9)
                        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.spot9), null, new Rect(spots[i], spots[i + 1], spots[i] + 90, spots[i + 1] + 90), paint);
                    if (spotsDurability[i/2] == 8)
                        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.spot8), null, new Rect(spots[i], spots[i + 1], spots[i] + 90, spots[i + 1] + 90), paint);
                    if (spotsDurability[i/2] == 7)
                        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.spot7), null, new Rect(spots[i], spots[i + 1], spots[i] + 90, spots[i + 1] + 90), paint);
                    if (spotsDurability[i/2] == 6)
                        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.spot6), null, new Rect(spots[i], spots[i + 1], spots[i] + 90, spots[i + 1] + 90), paint);
                    if (spotsDurability[i/2] == 5)
                        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.spot5), null, new Rect(spots[i], spots[i + 1], spots[i] + 90, spots[i + 1] + 90), paint);
                    if (spotsDurability[i/2] == 4)
                        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.spot4), null, new Rect(spots[i], spots[i + 1], spots[i] + 90, spots[i + 1] + 90), paint);
                    if (spotsDurability[i/2] == 3)
                        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.spot3), null, new Rect(spots[i], spots[i + 1], spots[i] + 90, spots[i + 1] + 90), paint);
                    if (spotsDurability[i/2] == 2)
                        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.spot2), null, new Rect(spots[i], spots[i + 1], spots[i] + 90, spots[i + 1] + 90), paint);
                    if (spotsDurability[i/2] == 1)
                        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.spot1), null, new Rect(spots[i], spots[i + 1], spots[i] + 90, spots[i + 1] + 90), paint);
                }
            }
            paint.setColor(0xFF333333);
            canvas.drawRect(new Rect(getWidth() - 550, 50, getWidth() - 50, 170), paint);
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.money), null, new Rect(getWidth() - 550, 50, getWidth() - 432, 170), paint);
            paint.setColor(0xFFFFFFFF);
            paint.setTextSize(60.0f);
            canvas.drawText(String.valueOf(money), getWidth() - 410, 130, paint);
        } else if (level == -2) {
            canvas.drawColor(0xFF000068);
            paint.setColor(0xFFC86EB9);
            canvas.drawCircle(100, 100, 80, paint);
            paint.setTextSize(160.0f);
            paint.setColor(0xFFFFFFFF);
            canvas.drawText("←", 20, 127, paint);
            paint.setColor(0xFF333333);
            canvas.drawRect(new Rect(getWidth() - 550, 50, getWidth() - 50, 170), paint);
            canvas.drawRect(new Rect(50, 250, getWidth() - 50, getHeight() / 2 - 50), paint);
            paint.setColor(0xFF222222);
            canvas.drawRect(new Rect(100, 300, getWidth() - 100, getHeight() / 2 - 400), paint);
            paint.setTextSize(60.0f);
            paint.setColor(0xFFFFFFFF);
            canvas.drawText(String.valueOf(money), getWidth() - 410, 130, paint);
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.money), null, new Rect(getWidth() - 550, 50, getWidth() - 432, 170), paint);
            if (shopPage==1) {
                paint.setColor(0xFF333333);
                canvas.drawRect(new Rect(50, getHeight() / 2, getWidth() - 50, getHeight() - 250), paint);
                paint.setColor(0xFF222222);
                canvas.drawRect(new Rect(100, getHeight() / 2 + 50, getWidth() - 100, getHeight() - 600), paint);
                paint.setColor(0xFFFFFFFF);
                paint.setTextSize(60.0f);
                canvas.drawText("Средство для мытья посуды", 100, getHeight() / 2 - 325, paint);
                canvas.drawText("Лейка", 100, getHeight() - 525, paint);
                paint.setTextSize(50.0f);
                if (dishwashingLiquidLevel == 10)
                    canvas.drawText("Максимальный уровень", 100, getHeight() / 2 - 260, paint);
                else
                    canvas.drawText("Уровень " + dishwashingLiquidLevel + "/10", 100, getHeight() / 2 - 260, paint);
                if (wateringCanLevel == 10)
                    canvas.drawText("Максимальный уровень", 100, getHeight() - 460, paint);
                else if (wateringCanLevel != 0) {
                    canvas.drawText("Уровень " + wateringCanLevel + "/10", 100, getHeight() - 460, paint);
                }
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.dishwashing), null, new Rect(getWidth() / 2 - 100, 350, getWidth() / 2 + 100, getHeight() / 2 - 450), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wateringcan), null, new Rect(getWidth() / 2 - 300, getHeight() / 2 + 100, getWidth() / 2 + 300, getHeight() - 650), paint);
                paint.setColor(0xFFC86EB9);
                canvas.drawCircle(getWidth()-100, getHeight()-100, 80, paint);
                if (dishwashingLiquidLevel != 10) {
                    canvas.drawRect(new Rect(getWidth() / 2, getHeight() / 2 - 190, getWidth() - 170, getHeight() / 2 - 80), paint);
                    canvas.drawCircle(getWidth() / 2, getHeight() / 2 - 135, 55, paint);
                    canvas.drawCircle(getWidth() - 170, getHeight() / 2 - 135, 55, paint);
                }
                if (wateringCanLevel != 10) {
                    canvas.drawRect(new Rect(getWidth() / 2, getHeight() - 390, getWidth() - 170, getHeight() - 280), paint);
                    canvas.drawCircle(getWidth() / 2, getHeight() - 335, 55, paint);
                    canvas.drawCircle(getWidth() - 170, getHeight() - 335, 55, paint);
                }
                paint.setTextSize(55.0f);
                paint.setColor(0xFFFFFFFF);
                if (dishwashingLiquidLevel != 10) {
                    canvas.drawText("Улучшить", getWidth() - 480, getHeight() / 2 - 119, paint);
                    canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.money), null, new Rect(100, getHeight() / 2 - 190, 210, getHeight() / 2 - 80), paint);
                }
                if (wateringCanLevel != 10) {
                    if (wateringCanLevel==0)
                        canvas.drawText("Купить", getWidth() - 444, getHeight() - 319, paint);
                    else
                        canvas.drawText("Улучшить", getWidth() - 480, getHeight() - 319, paint);
                    canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.money), null, new Rect(100, getHeight() - 390, 210, getHeight() - 280), paint);
                }
                if (dishwashingLiquidLevel == 1)
                    canvas.drawText("50", 230, getHeight() / 2 - 117, paint);
                else if (dishwashingLiquidLevel == 2)
                    canvas.drawText("75", 230, getHeight() / 2 - 117, paint);
                else if (dishwashingLiquidLevel == 3)
                    canvas.drawText("100", 230, getHeight() / 2 - 117, paint);
                else if (dishwashingLiquidLevel == 4)
                    canvas.drawText("150", 230, getHeight() / 2 - 117, paint);
                else if (dishwashingLiquidLevel == 5)
                    canvas.drawText("200", 230, getHeight() / 2 - 117, paint);
                else if (dishwashingLiquidLevel == 6)
                    canvas.drawText("250", 230, getHeight() / 2 - 117, paint);
                else if (dishwashingLiquidLevel == 7)
                    canvas.drawText("325", 230, getHeight() / 2 - 117, paint);
                else if (dishwashingLiquidLevel == 8)
                    canvas.drawText("400", 230, getHeight() / 2 - 117, paint);
                else if (dishwashingLiquidLevel == 9)
                    canvas.drawText("500", 230, getHeight() / 2 - 118, paint);
                if (wateringCanLevel == 0)
                    canvas.drawText("1000", 230, getHeight() - 317, paint);
                else if (wateringCanLevel == 1)
                    canvas.drawText("2000", 230, getHeight() - 317, paint);
                else if (wateringCanLevel == 2)
                    canvas.drawText("3000", 230, getHeight() - 317, paint);
                else if (wateringCanLevel == 3)
                    canvas.drawText("5000", 230, getHeight() - 317, paint);
                else if (wateringCanLevel == 4)
                    canvas.drawText("7000", 230, getHeight() - 317, paint);
                else if (wateringCanLevel == 5)
                    canvas.drawText("10000", 230, getHeight() - 317, paint);
                else if (wateringCanLevel == 6)
                    canvas.drawText("20000", 230, getHeight() - 317, paint);
                else if (wateringCanLevel == 7)
                    canvas.drawText("30000", 230, getHeight() - 317, paint);
                else if (wateringCanLevel == 8)
                    canvas.drawText("40000", 230, getHeight() - 317, paint);
                else if (wateringCanLevel == 9)
                    canvas.drawText("50000", 230, getHeight() - 317, paint);
                paint.setTextSize(160.0f);
                canvas.drawText(">", getWidth()-140, getHeight()-50, paint);
            } else if (shopPage==2) {
                paint.setTextSize(60.0f);
                canvas.drawText("Сковорода", 100, getHeight() / 2 - 325, paint);
                paint.setColor(0xFFC86EB9);
                if (fryingPanLevel != 10) {
                    canvas.drawRect(new Rect(getWidth() / 2, getHeight() / 2 - 190, getWidth() - 170, getHeight() / 2 - 80), paint);
                    canvas.drawCircle(getWidth() / 2, getHeight() / 2 - 135, 55, paint);
                    canvas.drawCircle(getWidth() - 170, getHeight() / 2 - 135, 55, paint);
                }
                canvas.drawCircle(100, getHeight()-100, 80, paint);
                paint.setColor(0xFFFFFFFF);
                paint.setTextSize(160.0f);
                canvas.drawText("<", 60, getHeight()-50, paint);
                paint.setTextSize(55.0f);
                if (fryingPanLevel != 10) {
                    if (fryingPanLevel==0)
                        canvas.drawText("Купить", getWidth() - 444, getHeight() / 2 - 119, paint);
                    else
                        canvas.drawText("Улучшить", getWidth() - 480, getHeight() / 2 - 119, paint);
                    canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.money), null, new Rect(100, getHeight() / 2 - 190, 210, getHeight() / 2 - 80), paint);
                }
                if (fryingPanLevel == 10)
                    canvas.drawText("Максимальный уровень", 100, getHeight() / 2 - 260, paint);
                else
                    canvas.drawText("Уровень " + fryingPanLevel + "/10", 100, getHeight() / 2 - 260, paint);
                if (fryingPanLevel == 0)
                    canvas.drawText("80000", 230, getHeight() / 2 - 117, paint);
                else if (fryingPanLevel == 1)
                    canvas.drawText("100000", 230, getHeight() / 2 - 117, paint);
                else if (dishwashingLiquidLevel == 2)
                    canvas.drawText("120000", 230, getHeight() / 2 - 117, paint);
                else if (dishwashingLiquidLevel == 3)
                    canvas.drawText("150000", 230, getHeight() / 2 - 117, paint);
                else if (dishwashingLiquidLevel == 4)
                    canvas.drawText("170000", 230, getHeight() / 2 - 117, paint);
                else if (dishwashingLiquidLevel == 5)
                    canvas.drawText("200000", 230, getHeight() / 2 - 117, paint);
                else if (dishwashingLiquidLevel == 6)
                    canvas.drawText("220000", 230, getHeight() / 2 - 117, paint);
                else if (dishwashingLiquidLevel == 7)
                    canvas.drawText("250000", 230, getHeight() / 2 - 117, paint);
                else if (dishwashingLiquidLevel == 8)
                    canvas.drawText("300000", 230, getHeight() / 2 - 117, paint);
                else if (dishwashingLiquidLevel == 9)
                    canvas.drawText("400000", 230, getHeight() / 2 - 118, paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fryingpan), null, new Rect(getWidth() / 2 - 300, 350, getWidth() / 2 + 300, getHeight()/2 - 450), paint);
            }
        } else if (level == 2) {
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.house), null, new Rect(0, 0, getWidth(), getHeight()), paint);
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.flowerpot), null, new Rect((int) (getWidth()*0.1), (int) (getHeight()*0.8), (int) (getWidth()*0.9), (int) (getHeight()*0.9)), paint);
            paint.setColor(0xFFC86EB9);
            paint.setTextSize(160.0f);
            canvas.drawCircle(100, 100, 80, paint);
            paint.setColor(0xFFFFFFFF);
            canvas.drawText("←", 20, 127, paint);
            if (levelTwoStep == 1) {
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wateringcan), null, new Rect(20, 370, 420, 520), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.shovel), null, new Rect(getWidth()/2-175, 20, getWidth()/2+175, 270), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.seeds), null, new Rect(getWidth()-170, 120, getWidth()-20, 370), paint);
            } else if (levelTwoStep == 2) {
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wateringcan), null, new Rect(20, 370, 420, 520), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.shovel), null, new Rect(getWidth()/2-175, 20, getWidth()/2+175, 270), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.seeds), null, new Rect(getWidth()/2-75, getHeight()/2, getWidth()/2+75, getHeight()/2+250), paint);
                paint.setColor(0xFFFF0000);
                canvas.drawRect(new Rect((int) (getWidth()*0.2), getHeight()/2+350, (int) (getWidth()*0.2+seedLimit*getWidth()*0.6/1000), getHeight()/2+450), paint);
                canvas.drawRect(new Rect((int) (getWidth()*0.2+seedLimit*getWidth()*0.6/1000+seedLimitWidth*getWidth()*0.6/1000), getHeight()/2+350, (int) (getWidth()*0.8), getHeight()/2+450), paint);
                paint.setColor(0xFF00FF00);
                canvas.drawRect(new Rect((int) (getWidth()*0.2+seedLimit*getWidth()*0.6/1000), getHeight()/2+350, (int) (getWidth()*0.2+seedLimit*getWidth()*0.6/1000+seedLimitWidth*getWidth()*0.6/1000), getHeight()/2+450), paint);
                paint.setColor(0xFF000000);
                canvas.drawLine((float) (getWidth()*0.2+seedProgress*getWidth()*0.6/1000), getHeight()/2+350, (float) (getWidth()*0.2+seedProgress*getWidth()*0.6/1000), getHeight()/2+450, paint);
            } else if (levelTwoStep == 3) {
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wateringcan), null, new Rect(20, 370, 420, 520), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.shovel), null, new Rect(getWidth()/2-175, 20, getWidth()/2+175, 270), paint);
            } else if (levelTwoStep == 4) {
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wateringcan), null, new Rect(20, 370, 420, 520), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.shovelinearth), null, new Rect(getWidth()/2-30, getHeight()/2+200, getWidth()/2+30, getHeight()/2+450), paint);
                paint.setColor(0xFF000000);
                canvas.drawRect(new Rect((int) (getWidth()*0.2), getHeight()/2+550, (int) (getWidth()*0.8), getHeight()/2+650), paint);
                paint.setColor(0xFF00FF00);
                canvas.drawRect(new Rect((int) (getWidth()*0.2), getHeight()/2+550, (int) (getWidth()*0.2+shovelCurrent*getWidth()*0.6/shovelAmount+(shovelProgress/1000.0)*(getWidth()*0.6/shovelAmount)), getHeight()/2+650), paint);
                paint.setColor(0xFFFFFFFF);
                for (float i = 1; i < shovelAmount; i++) {
                    canvas.drawLine((float) (getWidth()*0.2+i/shovelAmount*getWidth()*0.6), getHeight()/2+550, (float) (getWidth()*0.2+i/shovelAmount*getWidth()*0.6), getHeight()/2+650, paint);
                }
            } else if (levelTwoStep == 5) {
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wateringcan), null, new Rect(20, 370, 420, 520), paint);
            } else if (levelTwoStep == 6) {
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wateringcan), null, new Rect(getWidth()/2-200, getHeight()/2+200, getWidth()/2+200, getHeight()/2+350), paint);
                paint.setColor(0xFFFF0000);
                canvas.drawRect(new Rect((int) (getWidth()*0.2), getHeight()/2+350, (int) (getWidth()*0.2+seedLimit*getWidth()*0.6/1000), getHeight()/2+450), paint);
                canvas.drawRect(new Rect((int) (getWidth()*0.2+seedLimit*getWidth()*0.6/1000+seedLimitWidth*getWidth()*0.6/1000), getHeight()/2+350, (int) (getWidth()*0.8), getHeight()/2+450), paint);
                paint.setColor(0xFF00FF00);
                canvas.drawRect(new Rect((int) (getWidth()*0.2+seedLimit*getWidth()*0.6/1000), getHeight()/2+350, (int) (getWidth()*0.2+seedLimit*getWidth()*0.6/1000+seedLimitWidth*getWidth()*0.6/1000), getHeight()/2+450), paint);
                paint.setColor(0xFF000000);
                canvas.drawLine((float) (getWidth()*0.2+seedProgress*getWidth()*0.6/1000), getHeight()/2+350, (float) (getWidth()*0.2+seedProgress*getWidth()*0.6/1000), getHeight()/2+450, paint);
            }
        } else if (level == 3) {
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.kitchen), null, new Rect(0, 0, getWidth(), getHeight()), paint);
            paint.setColor(0xFFC86EB9);
            paint.setTextSize(160.0f);
            canvas.drawCircle(100, 100, 80, paint);
            paint.setColor(0xFFFFFFFF);
            canvas.drawText("←", 20, 127, paint);
            paint.setColor(0xFF333333);
            canvas.drawRect(new Rect(getWidth() - 550, 50, getWidth() - 50, 170), paint);
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.money), null, new Rect(getWidth() - 550, 50, getWidth() - 432, 170), paint);
            paint.setColor(0xFFFFFFFF);
            paint.setTextSize(60.0f);
            canvas.drawText(String.valueOf(money), getWidth() - 410, 130, paint);
            if (levelThreeStep == 1) {
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.milk), null, new Rect(45, getHeight() / 2 - 400, 180, getHeight() / 2 - 50), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.eggsclosed), null, new Rect(getWidth() - 300, getHeight() / 2 - 275, getWidth(), getHeight() / 2 - 75), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fryingpan), null, new Rect(getWidth() / 2 - 300, getHeight() / 2 - 300, getWidth() / 2 + 300, getHeight() / 2), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.salt), null, new Rect(130, getHeight() / 2 - 150, 210, getHeight() / 2), paint);
            } else if (levelThreeStep == 2) {
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.milk), null, new Rect(45, getHeight() / 2 - 400, 180, getHeight() / 2 - 50), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.eggs), null, new Rect(getWidth() - 300, getHeight() / 2 - 275, getWidth(), getHeight() / 2 - 75), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fryingpan), null, new Rect(getWidth() / 2 - 300, getHeight() / 2 - 300, getWidth() / 2 + 300, getHeight() / 2), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.salt), null, new Rect(130, getHeight() / 2 - 150, 210, getHeight() / 2), paint);
            } else if (levelThreeStep == 3) {
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.milk), null, new Rect(45, getHeight() / 2 - 400, 180, getHeight() / 2 - 50), paint);
                if (eggsBroken == 0)
                    canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bowl1), null, new Rect(getWidth() - 300, getHeight() / 2 - 275, getWidth(), getHeight() / 2 - 75), paint);
                else if (eggsBroken == 1)
                    canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bowl2), null, new Rect(getWidth() - 300, getHeight() / 2 - 275, getWidth(), getHeight() / 2 - 75), paint);
                else if (eggsBroken == 2)
                    canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bowl3), null, new Rect(getWidth() - 300, getHeight() / 2 - 275, getWidth(), getHeight() / 2 - 75), paint);
                else if (eggsBroken == 3)
                    canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bowl4), null, new Rect(getWidth() - 300, getHeight() / 2 - 275, getWidth(), getHeight() / 2 - 75), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fryingpan), null, new Rect(getWidth() / 2 - 300, getHeight() / 2 - 300, getWidth() / 2 + 300, getHeight() / 2), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.egg), null, new Rect(getWidth()-220, getHeight()/2-550, getWidth()-100, getHeight()/2-400), paint);
                paint.setColor(0xFFFF0000);
                canvas.drawRect(new Rect((int) (getWidth()*0.2), getHeight()/2+350, (int) (getWidth()*0.2+eggLimit*getWidth()*0.6/1000), getHeight()/2+450), paint);
                canvas.drawRect(new Rect((int) (getWidth()*0.2+eggLimit*getWidth()*0.6/1000+eggLimitWidth*getWidth()*0.6/1000), getHeight()/2+350, (int) (getWidth()*0.8), getHeight()/2+450), paint);
                paint.setColor(0xFF00FF00);
                canvas.drawRect(new Rect((int) (getWidth()*0.2+eggLimit*getWidth()*0.6/1000), getHeight()/2+350, (int) (getWidth()*0.2+eggLimit*getWidth()*0.6/1000+eggLimitWidth*getWidth()*0.6/1000), getHeight()/2+450), paint);
                paint.setColor(0xFF000000);
                canvas.drawLine((float) (getWidth()*0.2+eggPower*getWidth()*0.6/1000), getHeight()/2+350, (float) (getWidth()*0.2+eggPower*getWidth()*0.6/1000), getHeight()/2+450, paint);
                canvas.drawText("Разбито яиц: "+eggsBroken+"/4", getWidth()/2-246, getHeight()/2-600, paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.salt), null, new Rect(130, getHeight() / 2 - 150, 210, getHeight() / 2), paint);
            } else if (levelThreeStep == 4) {
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.milk), null, new Rect(45, getHeight() / 2 - 400, 180, getHeight() / 2 - 50), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bowl5), null, new Rect(getWidth() - 300, getHeight() / 2 - 275, getWidth(), getHeight() / 2 - 75), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fryingpan), null, new Rect(getWidth() / 2 - 300, getHeight() / 2 - 300, getWidth() / 2 + 300, getHeight() / 2), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.salt), null, new Rect(130, getHeight() / 2 - 150, 210, getHeight() / 2), paint);
            } else if (levelThreeStep == 5) {
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.milk), null, new Rect(45, getHeight() / 2 - 400, 180, getHeight() / 2 - 50), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bowl5), null, new Rect(getWidth() - 300, getHeight() / 2 - 275, getWidth(), getHeight() / 2 - 75), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fryingpan), null, new Rect(getWidth() / 2 - 300, getHeight() / 2 - 300, getWidth() / 2 + 300, getHeight() / 2), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.salt), null, new Rect(getWidth()-220, getHeight()/2-600, getWidth()-100, getHeight()/2-350), paint);
                paint.setColor(0xFFFF0000);
                canvas.drawRect(new Rect((int) (getWidth()*0.2), getHeight()/2+350, (int) (getWidth()*0.2+eggLimit*getWidth()*0.6/1000), getHeight()/2+450), paint);
                canvas.drawRect(new Rect((int) (getWidth()*0.2+eggLimit*getWidth()*0.6/1000+eggLimitWidth*getWidth()*0.6/1000), getHeight()/2+350, (int) (getWidth()*0.8), getHeight()/2+450), paint);
                paint.setColor(0xFF00FF00);
                canvas.drawRect(new Rect((int) (getWidth()*0.2+eggLimit*getWidth()*0.6/1000), getHeight()/2+350, (int) (getWidth()*0.2+eggLimit*getWidth()*0.6/1000+eggLimitWidth*getWidth()*0.6/1000), getHeight()/2+450), paint);
                paint.setColor(0xFF000000);
                canvas.drawLine((float) (getWidth()*0.2+eggPower*getWidth()*0.6/1000), getHeight()/2+350, (float) (getWidth()*0.2+eggPower*getWidth()*0.6/1000), getHeight()/2+450, paint);
            } else if (levelThreeStep == 6) {
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.milk), null, new Rect(45, getHeight() / 2 - 400, 180, getHeight() / 2 - 50), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bowl5), null, new Rect(getWidth() - 300, getHeight() / 2 - 275, getWidth(), getHeight() / 2 - 75), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fryingpan), null, new Rect(getWidth() / 2 - 300, getHeight() / 2 - 300, getWidth() / 2 + 300, getHeight() / 2), paint);
            } else if (levelThreeStep == 7) {
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.milk), null, new Rect(45, getHeight() / 2 - 400, 180, getHeight() / 2 - 50), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bowl5), null, new Rect(getWidth() - 300, getHeight() / 2 - 275, getWidth(), getHeight() / 2 - 75), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fryingpan), null, new Rect(getWidth() / 2 - 300, getHeight() / 2 - 300, getWidth() / 2 + 300, getHeight() / 2), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.whisk), null, new Rect(getWidth()-400, getHeight()/2-450, getWidth()-150, getHeight()/2-200), paint);
                paint.setColor(0xFF000000);
                canvas.drawRect(new Rect((int) (getWidth()*0.2), getHeight()/2+550, (int) (getWidth()*0.8), getHeight()/2+650), paint);
                paint.setColor(0xFF00FF00);
                canvas.drawRect(new Rect((int) (getWidth()*0.2), getHeight()/2+550, (int) (getWidth()*0.2+whiskCurrent*getWidth()*0.6/whiskAmount+(whiskProgress/1000.0)*(getWidth()*0.6/whiskAmount)), getHeight()/2+650), paint);
                paint.setColor(0xFFFFFFFF);
                for (float i = 1; i < whiskAmount; i++) {
                    canvas.drawLine((float) (getWidth()*0.2+i/whiskAmount*getWidth()*0.6), getHeight()/2+550, (float) (getWidth()*0.2+i/whiskAmount*getWidth()*0.6), getHeight()/2+650, paint);
                }
            } else if (levelThreeStep == 8) {
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.milk), null, new Rect(45, getHeight() / 2 - 400, 180, getHeight() / 2 - 50), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bowl5), null, new Rect(getWidth() - 300, getHeight() / 2 - 275, getWidth(), getHeight() / 2 - 75), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fryingpan), null, new Rect(getWidth() / 2 - 300, getHeight() / 2 - 300, getWidth() / 2 + 300, getHeight() / 2), paint);
            } else if (levelThreeStep == 9) {
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bowl6), null, new Rect(getWidth() - 300, getHeight() / 2 - 275, getWidth(), getHeight() / 2 - 75), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fryingpan), null, new Rect(getWidth() / 2 - 300, getHeight() / 2 - 300, getWidth() / 2 + 300, getHeight() / 2), paint);
            } else if (levelThreeStep == 10) {
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bowl6), null, new Rect(getWidth() - 300, getHeight() / 2 - 275, getWidth(), getHeight() / 2 - 75), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fryingpan), null, new Rect(getWidth() / 2 - 300, getHeight() / 2 - 300, getWidth() / 2 + 300, getHeight() / 2), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.whisk), null, new Rect(getWidth()-400, getHeight()/2-450, getWidth()-150, getHeight()/2-200), paint);
                paint.setColor(0xFF000000);
                canvas.drawRect(new Rect((int) (getWidth()*0.2), getHeight()/2+550, (int) (getWidth()*0.8), getHeight()/2+650), paint);
                paint.setColor(0xFF00FF00);
                canvas.drawRect(new Rect((int) (getWidth()*0.2), getHeight()/2+550, (int) (getWidth()*0.2+whiskCurrent*getWidth()*0.6/whiskAmount+(whiskProgress/1000.0)*(getWidth()*0.6/whiskAmount)), getHeight()/2+650), paint);
                paint.setColor(0xFFFFFFFF);
                for (float i = 1; i < whiskAmount; i++) {
                    canvas.drawLine((float) (getWidth()*0.2+i/whiskAmount*getWidth()*0.6), getHeight()/2+550, (float) (getWidth()*0.2+i/whiskAmount*getWidth()*0.6), getHeight()/2+650, paint);
                }
            } else if (levelThreeStep == 11) {
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bowl6), null, new Rect(getWidth() - 300, getHeight() / 2 - 275, getWidth(), getHeight() / 2 - 75), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fryingpan), null, new Rect(getWidth() / 2 - 300, getHeight() / 2 - 300, getWidth() / 2 + 300, getHeight() / 2), paint);
            } else if (levelThreeStep == 12) {
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bowl1), null, new Rect(getWidth() - 300, getHeight() / 2 - 275, getWidth(), getHeight() / 2 - 75), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fryingpan), null, new Rect(getWidth() / 2 - 300, getHeight() / 2 - 300, getWidth() / 2 + 300, getHeight() / 2), paint);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fryingpantop), null, new Rect(getWidth() / 2 - 290, getHeight() / 2 - 260, getWidth() / 2 + 90, getHeight() / 2 - 70), paint);
                paint.setColor(0xFF000000);
                canvas.drawRect(new Rect((int) (getWidth()*0.2), getHeight()/2+550, (int) (getWidth()*0.8), getHeight()/2+650), paint);
                paint.setColor(0xFF00FF00);
                canvas.drawRect(new Rect((int) (getWidth()*0.2), getHeight()/2+550, (int) (getWidth()*0.2+(omeletteCookingProgress/1000.0)*getWidth()*0.6), getHeight()/2+650), paint);
            }
        }
    }
    class Timer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public Timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (level == 2 && levelTwoStep == 2) {
                seedProgress += 20;
                if (seedProgress>1000) {
                    levelTwoStep = 1;
                    seedProgress = 0;
                }
                invalidate();
            } else if (level == 2 && levelTwoStep == 4) {
                if (shovelClicked) {
                    shovelProgress += 50;
                    if (shovelProgress>1000) {
                        shovelClicked = false;
                        shovelProgress = 0;
                        shovelCurrent++;
                        if (shovelCurrent==shovelAmount) levelTwoStep++;
                    }
                    invalidate();
                }
            } else if (level == 2 && levelTwoStep == 6) {
                seedProgress += 5;
                if (seedProgress>1000) {
                    levelTwoStep = 1;
                    seedProgress = 0;
                }
                invalidate();
            } else if (level == 3 && levelThreeStep == 3) {
                if (eggReverse)
                    eggPower -= 40;
                else
                    eggPower += 40;
                if (eggPower >= 1000 || eggPower <= 0) {
                    eggReverse = !eggReverse;
                }
                invalidate();
            } else if (level == 3 && levelThreeStep == 5) {
                eggPower += 40;
                if (eggPower>1000) {
                    levelThreeStep = 1;
                    eggPower = 0;
                }
                invalidate();
            } else if (level == 3 && levelThreeStep == 7) {
                if (whiskClicked) {
                    whiskProgress += 35;
                    if (whiskProgress>1000) {
                        whiskClicked = false;
                        whiskProgress = 0;
                        whiskCurrent++;
                        if (whiskCurrent==whiskAmount) levelThreeStep++;
                    }
                    invalidate();
                }
            } else if (level == 3 && levelThreeStep == 10) {
                if (whiskClicked) {
                    whiskProgress += 35;
                    if (whiskProgress>1000) {
                        whiskClicked = false;
                        whiskProgress = 0;
                        whiskCurrent++;
                        if (whiskCurrent==whiskAmount) levelThreeStep++;
                    }
                    invalidate();
                }
            } else if (level == 3 && levelThreeStep == 12) {
                omeletteCookingProgress += 5+fryingPanLevel;
                if (omeletteCookingProgress>1000) {
                    levelThreeStep = 1;
                    money += 2150;
                    editor.putInt("money", money);
                    editor.commit();
                }
                invalidate();
            }
        }

        @Override
        public void onFinish() {

        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();
        if (eventAction == MotionEvent.ACTION_DOWN) {
            if (level == -1) {
                if (event.getX() >= 240 && event.getX() <= getWidth()-240 && event.getY() >= 670 && event.getY() <= 790) level = 0;
                if (event.getX() >= 240 && event.getX() <= getWidth()-240 && event.getY() >= 1050 && event.getY() <= 1170) level = -2;
            } else if (level == 0) {
                if (event.getX() >= 20 && event.getX() <= 180 && event.getY() >= 20 && event.getY() <= 180) level = -1;
                else if (event.getX() >= getWidth()*0.1f-60 && event.getX() <= getWidth()*0.9f+60 && event.getY() >= getHeight()*0.1 && event.getY() <= getHeight()*0.16) level = 1;
                else if (event.getX() >= getWidth()*0.1f-60 && event.getX() <= getWidth()*0.9f+60 && event.getY() >= getHeight()*0.2 && event.getY() <= getHeight()*0.26 && wateringCanLevel>0) level = 2;
                else if (event.getX() >= getWidth()*0.1f-60 && event.getX() <= getWidth()*0.9f+60 && event.getY() >= getHeight()*0.3 && event.getY() <= getHeight()*0.36 && fryingPanLevel>0) level = 3;
            } else if (level == 1) {
                if (event.getX() >= 20 && event.getX() <= 180 && event.getY() >= 20 && event.getY() <= 180) level = 0;
                else {
                    for (int i = 0; i < spots.length; i+=2) {
                        if (event.getX()>=spots[i] && event.getX()<=spots[i]+90 && event.getY()>=spots[i+1] && event.getY()<=spots[i+1]+90) {
                            if (spotsDurability[i/2] > 0) {
                                if (spotsDurability[i/2]==10) {
                                    spotsDurability[i/2]-=dishwashingLiquidLevel-1;
                                }
                                spotsDurability[i / 2]--;
                            }
                            if (spotsDurability[i/2] <= 0) {
                                spots[i] = 0;
                                spots[i + 1] = 0;
                                money++;
                            }
                        }
                    }
                    boolean plateCleaned = true;
                    for (int i: spots) {
                        if (i>0) {
                            plateCleaned = false;
                            break;
                        }
                    }
                    if (plateCleaned) {
                        spots = new int[]{(int) (Math.random() * (getWidth()/2))+getWidth()/4, (int) (Math.random() * (getHeight()/3))+getHeight()/3, (int) (Math.random() * (getWidth()/2))+getWidth()/4, (int) (Math.random() * (getHeight()/3))+getHeight()/3, (int) (Math.random() * (getWidth()/2))+getWidth()/4, (int) (Math.random() * (getHeight()/3))+getHeight()/3, (int) (Math.random() * (getWidth()/2))+getWidth()/4, (int) (Math.random() * (getHeight()/3))+getHeight()/3, (int) (Math.random() * (getWidth()/2))+getWidth()/4, (int) (Math.random() * (getHeight()/3))+getHeight()/3, (int) (Math.random() * (getWidth()/2))+getWidth()/4, (int) (Math.random() * (getHeight()/3))+getHeight()/3, (int) (Math.random() * (getWidth()/2))+getWidth()/4, (int) (Math.random() * (getHeight()/3))+getHeight()/3, (int) (Math.random() * (getWidth()/2))+getWidth()/4, (int) (Math.random() * (getHeight()/3))+getHeight()/3, (int) (Math.random() * (getWidth()/2))+getWidth()/4, (int) (Math.random() * (getHeight()/3))+getHeight()/3, (int) (Math.random() * (getWidth()/2))+getWidth()/4, (int) (Math.random() * (getHeight()/3))+getHeight()/3};
                        spotsDurability = new int[]{10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
                    }
                }
            } else if (level == -2) {
                if (event.getX() >= 20 && event.getX() <= 180 && event.getY() >= 20 && event.getY() <= 180)
                    level = -1;
                if (shopPage == 1) {
                    if (event.getX() >= getWidth() / 2 - 55 && event.getX() <= getWidth() - 115 && event.getY() >= getHeight() / 2 - 190 && event.getY() <= getHeight() / 2 - 80) {
                        if (dishwashingLiquidLevel == 1 && money >= 50) {
                            dishwashingLiquidLevel++;
                            money -= 50;
                        } else if (dishwashingLiquidLevel == 2 && money >= 75) {
                            dishwashingLiquidLevel++;
                            money -= 75;
                        } else if (dishwashingLiquidLevel == 3 && money >= 100) {
                            dishwashingLiquidLevel++;
                            money -= 100;
                        } else if (dishwashingLiquidLevel == 4 && money >= 150) {
                            dishwashingLiquidLevel++;
                            money -= 150;
                        } else if (dishwashingLiquidLevel == 5 && money >= 200) {
                            dishwashingLiquidLevel++;
                            money -= 200;
                        } else if (dishwashingLiquidLevel == 6 && money >= 250) {
                            dishwashingLiquidLevel++;
                            money -= 250;
                        } else if (dishwashingLiquidLevel == 7 && money >= 325) {
                            dishwashingLiquidLevel++;
                            money -= 325;
                        } else if (dishwashingLiquidLevel == 8 && money >= 400) {
                            dishwashingLiquidLevel++;
                            money -= 400;
                        } else if (dishwashingLiquidLevel == 9 && money >= 500) {
                            dishwashingLiquidLevel++;
                            money -= 500;
                        }
                        editor.putInt("dishwashingLiquidLevel", dishwashingLiquidLevel);
                    }
                    if (event.getX() >= getWidth() / 2 - 55 && event.getX() <= getWidth() - 115 && event.getY() >= getHeight() - 390 && event.getY() <= getHeight() - 280) {
                        if (wateringCanLevel == 0 && money >= 1000) {
                            wateringCanLevel++;
                            money -= 1000;
                        } else if (wateringCanLevel == 1 && money >= 2000) {
                            wateringCanLevel++;
                            money -= 2000;
                        } else if (wateringCanLevel == 2 && money >= 3000) {
                            wateringCanLevel++;
                            money -= 3000;
                        } else if (wateringCanLevel == 3 && money >= 5000) {
                            wateringCanLevel++;
                            money -= 5000;
                        } else if (wateringCanLevel == 4 && money >= 7000) {
                            wateringCanLevel++;
                            money -= 7000;
                        } else if (wateringCanLevel == 5 && money >= 10000) {
                            wateringCanLevel++;
                            money -= 10000;
                        } else if (wateringCanLevel == 6 && money >= 20000) {
                            wateringCanLevel++;
                            money -= 20000;
                        } else if (wateringCanLevel == 7 && money >= 30000) {
                            wateringCanLevel++;
                            money -= 30000;
                        } else if (wateringCanLevel == 8 && money >= 40000) {
                            wateringCanLevel++;
                            money -= 40000;
                        } else if (wateringCanLevel == 9 && money >= 50000) {
                            wateringCanLevel++;
                            money -= 50000;
                        }
                        editor.putInt("wateringCanLevel", wateringCanLevel);
                    }
                    if (event.getX() >= getWidth()-180 && event.getX() <= getWidth()-20 && event.getY() >= getHeight()-180 && event.getY() <= getHeight()-20) shopPage++;
                } else if (shopPage == 2) {
                    if (event.getX() >= 20 && event.getX() <= 180 && event.getY() >= getHeight()-180 && event.getY() <= getHeight()-20) shopPage--;
                    if (event.getX() >= getWidth() / 2 - 55 && event.getX() <= getWidth() - 115 && event.getY() >= getHeight() / 2 - 190 && event.getY() <= getHeight() / 2 - 80) {
                        if (fryingPanLevel == 0 && money >= 80000) {
                            fryingPanLevel++;
                            money -= 80000;
                        } else if (fryingPanLevel == 1 && money >= 100000) {
                            fryingPanLevel++;
                            money -= 100000;
                        } else if (fryingPanLevel == 2 && money >= 120000) {
                            fryingPanLevel++;
                            money -= 120000;
                        } else if (fryingPanLevel == 3 && money >= 150000) {
                            fryingPanLevel++;
                            money -= 150000;
                        } else if (fryingPanLevel == 4 && money >= 170000) {
                            fryingPanLevel++;
                            money -= 170000;
                        } else if (fryingPanLevel == 5 && money >= 200000) {
                            fryingPanLevel++;
                            money -= 200000;
                        } else if (fryingPanLevel == 6 && money >= 220000) {
                            fryingPanLevel++;
                            money -= 220000;
                        } else if (fryingPanLevel == 7 && money >= 250000) {
                            fryingPanLevel++;
                            money -= 250000;
                        } else if (fryingPanLevel == 8 && money >= 300000) {
                            fryingPanLevel++;
                            money -= 300000;
                        } else if (fryingPanLevel == 9 && money >= 400000) {
                            fryingPanLevel++;
                            money -= 400000;
                        }
                        editor.putInt("fryingPanLevel", fryingPanLevel);
                    }
                }
            } else if (level == 2) {
                if (event.getX() >= 20 && event.getX() <= 180 && event.getY() >= 20 && event.getY() <= 180) level = 0;
                else if (levelTwoStep == 1 && event.getX() >= getWidth()-170 && event.getX() <= getWidth()-20 && event.getY() >= 120 && event.getY() <= 370) {
                    levelTwoStep++;
                    seedLimitWidth = 150+wateringCanLevel*50;
                    seedLimit = (int) (Math.random()*(1000-seedLimitWidth));
                    seedProgress = 0;
                } else if (levelTwoStep == 2) {
                    if (seedProgress >= seedLimit && seedProgress <= seedLimit+seedLimitWidth) {
                        levelTwoStep++;
                        seedProgress = 0;
                    } else {
                        levelTwoStep--;
                    }
                } else if (levelTwoStep == 3 && event.getX() >= getWidth()/2-175 && event.getX() <= getWidth()/2+175 && event.getY() >= 20 && event.getY() <= 270) {
                    levelTwoStep++;
                    shovelAmount = seedLimit/125+1;
                    shovelCurrent = 0;
                } else if (levelTwoStep == 4) shovelClicked = true;
                else if (levelTwoStep == 5 && event.getX() >= 20 && event.getX() <= 420 && event.getY() >= 370 && event.getY() <= 520) levelTwoStep = 6;
                else if (levelTwoStep == 6) {
                    if (seedProgress >= seedLimit && seedProgress <= seedLimit+seedLimitWidth) {
                        money += 400;
                    }
                    levelTwoStep = 1;
                }
            } else if (level == 3) {
                if (event.getX() >= 20 && event.getX() <= 180 && event.getY() >= 20 && event.getY() <= 180) level = 0;
                else if (levelThreeStep == 1 && event.getX() >= getWidth()-300 && event.getX() <= getWidth() && event.getY() >= getHeight()/2-275 && event.getY() <= getHeight()/2-75) levelThreeStep++;
                else if (levelThreeStep == 2 && event.getX() >= getWidth()-300 && event.getX() <= getWidth() && event.getY() >= getHeight()/2-275 && event.getY() <= getHeight()/2-75) {
                    levelThreeStep++;
                    eggLimitWidth = 150;
                    eggLimit = 425;
                    eggPower = 0;
                } else if (levelThreeStep == 3) {
                    if (eggPower >= eggLimit) {
                        if (eggPower <= eggLimit+eggLimitWidth) {
                            eggsBroken++;
                            if (eggsBroken == 4) {
                                levelThreeStep++;
                                eggsBroken = 0;
                            }
                        }
                        eggLimit = 425;
                    } else {
                        eggLimit -= eggPower;
                    }
                    eggPower = 0;
                    eggReverse = false;
                } else if (levelThreeStep == 4 && event.getX() >= 130 && event.getX() <= 210 && event.getY() >= getHeight()/2-150 && event.getY() <= getHeight()/2) {
                    levelThreeStep = 5;
                    eggLimitWidth = 200;
                    eggLimit = 400;
                } else if (levelThreeStep == 5) {
                    if (eggPower >= eggLimit) {
                        if (eggPower <= eggLimit+eggLimitWidth) {
                            levelThreeStep++;
                        } else {
                            levelThreeStep = 1;
                        }
                    } else {
                        eggLimit -= eggPower;
                    }
                    eggPower = 0;
                } else if (levelThreeStep == 6 && event.getX() >= getWidth()-300 && event.getX() <= getWidth() && event.getY() >= getHeight() / 2 - 275 && event.getY() <= getHeight() / 2 - 75) {
                    levelThreeStep++;
                    whiskCurrent = 0;
                } else if (levelThreeStep == 7) whiskClicked = true;
                else if (levelThreeStep == 8 && event.getX() >= 45 && event.getX() <= 180 && event.getY() >= getHeight()/2-400 && event.getY() <= getHeight()/2-50)  levelThreeStep++;
                else if (levelThreeStep == 9 && event.getX() >= getWidth()-300 && event.getX() <= getWidth() && event.getY() >= getHeight() / 2 - 275 && event.getY() <= getHeight() / 2 - 75) {
                    levelThreeStep++;
                    whiskCurrent = 0;
                } else if (levelThreeStep == 10) whiskClicked = true;
                else if (levelThreeStep == 11 && event.getX() >= getWidth()-300 && event.getX() <= getWidth() && event.getY() >= getHeight() / 2 - 275 && event.getY() <= getHeight() / 2 - 75) {
                    levelThreeStep++;
                    omeletteCookingProgress = 0;
                }
            }
        }
        editor.putInt("money", money);
        editor.commit();
        invalidate();
        return true;
    }
}
