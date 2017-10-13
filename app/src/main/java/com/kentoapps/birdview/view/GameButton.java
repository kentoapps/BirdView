package com.kentoapps.birdview.view;

import android.content.Context;
import android.widget.Button;

import com.kentoapps.birdview.constant.ColorEnum;

/**
 * Created by kento on 15/02/05.
 */
public class GameButton extends Button {
    private static final String TAG = GameButton.class.getSimpleName();

    public GameButton(Context context) {
        super(context);
    }

    private ColorEnum color;

    public ColorEnum getColor() {
        return color;
    }

    public void setColor(ColorEnum color) {
        this.color = color;
        setBackgroundResource(color.getColor());
    }
}
