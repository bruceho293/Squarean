package com.game.hogia.boringman;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.view.MotionEvent;


public class StartGameScene implements Scene{

    private Bitmap title, buttonStartGame, instruction, pointerBit;
    private Rect button, titleHolder, instrHolder, pointer;
    private Rect r = new Rect();

    public StartGameScene(){
        int left = Constants.SCREEN_WIDTH / 3;
        int right = 2 * Constants.SCREEN_WIDTH/3;
        int top = Constants.SCREEN_HEIGHT - Constants.SCREEN_HEIGHT/3;
        int bottom = Constants.SCREEN_HEIGHT - Constants.SCREEN_HEIGHT/3 + right - left;

        button =  new Rect(left, top, right, bottom);

        left = Constants.SCREEN_WIDTH / 9;
        right = Constants.SCREEN_WIDTH - Constants.SCREEN_WIDTH / 9;
        top = Constants.SCREEN_HEIGHT /4;
        bottom = Constants.SCREEN_HEIGHT / 2;

        titleHolder = new Rect(left, top, right, bottom);

        title = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.titlea1);
        buttonStartGame = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.attach_to_wall);

        left = Constants.SCREEN_WIDTH - Constants.SCREEN_WIDTH / 10 - Constants.SCREEN_WIDTH / 10;
        right = Constants.SCREEN_WIDTH - Constants.SCREEN_WIDTH / 10 ;
        top = Constants.SCREEN_HEIGHT / 15;
        bottom = top + Constants.SCREEN_WIDTH / 10;

        instrHolder = new Rect(left, top, right, bottom);

        left = Constants.SCREEN_WIDTH / 6;
        right = 3 * Constants.SCREEN_WIDTH / 5 + Constants.SCREEN_WIDTH/6;
        bottom += Constants.SCREEN_HEIGHT / 20;

        pointer = new Rect(left, top, right, bottom);
        pointerBit = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.instruction_pointer);
        instruction = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.intruction_icon1);
    }

    private void drawCenterText(Canvas canvas, float y, Paint paint, String text) {
        canvas.getClipBounds(r);
        int cWidth = r.width();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        canvas.drawText(text, x, y, paint);
    }


    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (button.contains((int)event.getX(), (int)event.getY()))
                SceneManager.ACTIVE_SCENE = 1;
            if (instrHolder.contains((int)event.getX(), (int)event.getY())){
                Constants.CURRENT_CONTEXT.startActivity(new Intent(Constants.CURRENT_CONTEXT, Instruction.class));
            }
        }

    }


    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setTextSize(Constants.SCREEN_WIDTH /10 );
        paint.setColor(Color.BLACK);
        String text = "Tap to start";


        canvas.drawBitmap(instruction, null, instrHolder, null);
        canvas.drawBitmap(title, null, titleHolder, null);
        canvas.drawBitmap(buttonStartGame, null, button, null);
        canvas.drawBitmap(pointerBit, null, pointer, null);
        drawCenterText(canvas, button.bottom + Constants.SCREEN_HEIGHT/10, paint, text);
    }

}
