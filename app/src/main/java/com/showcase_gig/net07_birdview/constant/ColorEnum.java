package com.showcase_gig.net07_birdview.constant;

import com.showcase_gig.net07_birdview.R;

/**
 * Created by kento on 15/02/06.
 */
public enum ColorEnum {

    BLUE(R.drawable.blue_button), RED(R.drawable.red_button), YELLOW(R.drawable.yellow_button), GREEN(R.drawable.green_button);

    private final int color;

    public int getColor() {
        return color;
    }

    ColorEnum(int color) {
        this.color = color;
    }


    public static ColorEnum getColorEnum(int num) {
        if (num < 0 || num > (ColorEnum.values().length - 1)) {
            return null;
        }
        return values()[num];
    }
}
