package com.showcase_gig.net07_birdview.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.showcase_gig.net07_birdview.R;


public class CircleProgress extends View {

    private final static String STR_ATTR_RADIUS = "radius";         //半径
    private final static String STR_ATTR_WEIGHT = "weight";         //線の太さ
    private final static String STR_ATTR_COLOR = "color";           //色
    private final static String STR_ATTR_BASE_COLOR= "base_color";  //進捗してない所の色

    private int _progress;  //現在の進捗
    private int _max;       //最大値
    private int _weight;    //線の太さ
    private int _color;     //色
    private int _base_color;//進捗してない所の色

    private int _radius;    //半径

    /**
     * 現在の進捗
     * @param _progress the _progress to set
     */
    public void setProgress(int _progress) {
        if(_progress > getMax()){
            _progress = getMax();
        }
        this._progress = _progress;
    }


    /**
     * 現在の進捗
     * @return the _progress
     */
    public int getProgress() {
        return _progress;
    }


    /**
     * 最大値
     * @param _max the _max to set
     */
    public void setMax(int _max) {
        if(_max <= 0){
            _max = 1;
        }
        this._max = _max;
    }


    /**
     * 最大値
     * @return the _max
     */
    public int getMax() {
        return _max;
    }

    /**
     * 線の太さ
     * @param _weight the _weight to set
     */
    public void setWeight(int _weight) {
        if(_weight < 1){
            _weight = 1;
        }
        this._weight = _weight;
    }


    /**
     * 線の太さ
     * @return the _weight
     */
    public int getWeight() {
        return _weight;
    }

    /**
     * 色
     * @param _color the _color to set
     */
    public void setColor(int _color) {
        this._color = _color;
    }


    /**
     * 色
     * @return the _color
     */
    public int getColor() {
        return _color;
    }

    /**
     * 進捗してない所の色
     * @param _base_color the _base_color to set
     */
    public void setBaseColor(int _base_color) {
        this._base_color = _base_color;
    }


    /**
     * 進捗してない所の色
     * @return the _base_color
     */
    public int getBaseColor() {
        return _base_color;
    }


    /**
     * 円形のプログレスバー
     * @param context
     * @param attrs
     */
    public CircleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);

        Resources r = getResources();
        int radius = (int)r.getDimension(R.dimen.circle_radius);
        int weight = (int)r.getDimension(R.dimen.circle_wight);
        int color = r.getColor(R.color.app_blue);
        setProgress(0);
        setMax(100);
        setWeight(weight);
        setColor(color);
        _radius = radius;

        String temp;

        float density = getContext().getResources().getDisplayMetrics().density;

        //半径
        temp = attrs.getAttributeValue(null, STR_ATTR_RADIUS);
        if(temp != null){
            if(temp.indexOf("dip") >= 0){
                temp = temp.replace("dip", "");
                _radius = (int) (Float.valueOf(temp) * density);
            }else if(temp.indexOf("dp") >= 0){
                temp = temp.replace("dp", "");
                _radius = (int) (Float.valueOf(temp) * density);
            }else if(temp.indexOf("sp") >= 0){
                temp = temp.replace("sp", "");
                _radius = (int) (Float.valueOf(temp) * density);
            }else{
                try{
                    _radius = Integer.valueOf(temp);
                }catch(Exception e){
                }
            }
        }

        //線の太さ
        temp = attrs.getAttributeValue(null, STR_ATTR_WEIGHT);
        if(temp != null){
            setWeight(Integer.valueOf(temp));
        }

        //色
        temp = attrs.getAttributeValue(null, STR_ATTR_COLOR);
        if(temp != null){
            setColor(Color.parseColor(temp));
        }

        //ベース色
        temp = attrs.getAttributeValue(null, STR_ATTR_BASE_COLOR);
        if(temp != null){
            setBaseColor(Color.parseColor(temp));
        }

    }

    /**
     * サイズを決定する
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int spec_width = MeasureSpec.getSize(widthMeasureSpec);
        int spec_height = MeasureSpec.getSize(heightMeasureSpec);
        int mine = _radius * 2;
        //一番小さいのを使う
        int spec_size = Math.min(mine, spec_width);
        spec_size = Math.min(spec_size, spec_height);
        //サイズ設定
        setMeasuredDimension(spec_size, spec_size);
    }

    /**
     * 描画処理
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();              //描画フォーマット作成
        paint.setAntiAlias(true);               //アンチエイリアス有効
        paint.setStyle(Paint.Style.STROKE);     //線だけにする

        // 円のエリア定義
        RectF rect = new RectF(0 + getWeight(), 0 + getWeight()
                , getWidth() - getWeight(), getHeight() - getWeight());

        // 進捗枠
        paint.setColor(getBaseColor());
        paint.setStrokeWidth(getWeight());
        canvas.drawArc(rect, 0, 360, false, paint);

        // 進捗
        paint.setColor(getColor());
        paint.setStrokeWidth(getWeight());
        canvas.drawArc(rect, 270, (360 * (float) getProgress() / (float)getMax()), false, paint);
    }

}
