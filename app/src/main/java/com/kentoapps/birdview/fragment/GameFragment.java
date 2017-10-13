package com.kentoapps.birdview.fragment;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.kentoapps.birdview.R;
import com.kentoapps.birdview.constant.ColorEnum;
import com.kentoapps.birdview.constant.Const;
import com.kentoapps.birdview.view.GameButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = GameFragment.class.getSimpleName();

    private View mView;
    private int blockLength; // ブロックの長さ
    private int blockAmount; // ブロックの数

    private int amount; // 色の数

    private ColorEnum correctColor;
    private Vibrator vibrator;
    private long[] pattern;
    private TableLayout tableLayout;

    private double correctCount;
    private double incorrectCount;
    private double score;
    private double scale;
    private TextView timeUpText;
    private TextView count_txt;
    private CountDownTimer countDownTimer;
    private Handler handler;

    private Random random = new Random();

    public static GameFragment newInstance() {
        GameFragment fragment = new GameFragment();
        return fragment;
    }

    public GameFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_game, container, false);

        init();

        setGame();

        startTimer();

        return mView;
    }

    /** 初期化 */
    private void init() {
        count_txt = (TextView)mView.findViewById(R.id.game_count);
        correctCount = 0;
        incorrectCount = 0;
        score = 0;
        scale = 1;
        blockLength = 3;
        amount = 2;
        tableLayout = (TableLayout) mView.findViewById(R.id.game_table);
        timeUpText = (TextView) mView.findViewById(R.id.game_time_up);
        vibrator = (Vibrator) getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);
        pattern = new long[]{0, 200};
    }

    private void setGame() {
        Log.d(TAG, "amount :" + amount);
        int[] colorAmount = new int[amount];
        blockAmount = blockLength * blockLength;

        // amountで指定された数だけ色の種類を取ってくる
        ArrayList<ColorEnum> colorList = new ArrayList<>();
        for(int i = 0; i < amount; i++) {
            colorList.add(ColorEnum.getColorEnum(i));
        }
        Log.d(TAG, "colorEnums size :" + colorList.size());

        // ランダムにする
        Collections.shuffle(colorList);
        correctColor = colorList.get(0);

        // 正解の数を決める
        colorAmount[0] = blockAmount / amount + getRandom();
        Log.d(TAG, "Correct amount :" + colorAmount[0] + " blockAmount :" + blockAmount);

        // 不正解の数を決める
        int temp = (blockAmount - colorAmount[0]) / (amount - 1);
        for(int i = 1; i < amount; i++) {
            colorAmount[i] = temp;
        }

        // 余りを足す
        int remainder = (blockAmount - colorAmount[0]) % (amount - 1);
        Log.d(TAG, "remainder :" + remainder);
        int colorNum = 1;
        while(remainder > 0) {
            colorAmount[colorNum] += 1;
            remainder--;
            colorNum++;
        }
        log(colorAmount);

        // ボタンを作る
        ArrayList<GameButton> buttonList = new ArrayList<>();
        for(int i = 0; i < amount; i++) {
            for(int j = 0; j < colorAmount[i]; j++) {
                buttonList.add(createButton(colorList.get(i)));
            }
        }
        Log.d(TAG, "buttonList size :" + buttonList.size());

        // ボタンをランダム
        Collections.shuffle(buttonList);

        // テーブルを作ってボタンを配置
        createTable(buttonList);
    }

    private void createTable(ArrayList<GameButton> buttonList) {
        int buttonNum = 0;
        for(int i = 0; i < blockLength; i++) {
            TableRow tableRow = new TableRow(getActivity());
            tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1));
            TableRow.LayoutParams lp = new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT, 1);
            lp.setMargins(10, 10, 10, 10);
            for(int j = 0; j < blockLength; j++) {
                tableRow.addView(buttonList.get(buttonNum), lp);
                buttonNum++;
            }
        }
    }

    private void log(int[] colorAmount) {
        for(int i = 1; i < colorAmount.length; i++) {
            Log.d(TAG, "Incorrect amount" + i +" :" + colorAmount[i]);
        }
    }

    private GameButton createButton(ColorEnum colorEnum) {
        GameButton gameButton = new GameButton(getActivity());
        gameButton.setColor(colorEnum);
        gameButton.setOnClickListener(this);
        return gameButton;
    }

    private int getRandom() {
        return (int)(Math.random() * 2) + 1;
    }

    private void startTimer() {
        // カウントダウンする
        countDownTimer = new CountDownTimer(Const.PLAY_TIME, 1000) {
            // カウントダウン処理
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished <= 4000) {
                    count_txt.setVisibility(View.VISIBLE);
                    count_txt.setText(String.valueOf(millisUntilFinished / 1000));
                }
            }

            // カウントが0になった時の処理
            public void onFinish() {
                tableLayout.removeAllViews();
                count_txt.setVisibility(View.INVISIBLE);
                timeUpText.setVisibility(View.VISIBLE);
                handler = new Handler();
                handler.postDelayed(moveToResult, Const.TIME_UP_DISPLAY_TIME);
            }
        };
        countDownTimer.start();
    }

    private final Runnable moveToResult = new Runnable() {
        @Override
        public void run() {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_container, ResultFragment.newInstance(correctCount, incorrectCount, (int) score));
            fragmentTransaction.commit();
            Log.d(TAG, "correctCount :" + correctCount + " incorrectCount :" + incorrectCount);
        }
    };

    @Override
    public void onClick(View v) {
        ColorEnum colorEnum = ((GameButton)v).getColor();
        if (correctColor.equals(colorEnum)) {
            // 正解
            correctCount++;
            score += Const.CORRECT_SCORE * getScale();
            tableLayout.removeAllViews();

            setCondition();

            setGame();
        } else {
            // 不正解
            incorrectCount++;
            score -= Const.INCORRECT_SCORE;
            vibrator.vibrate(pattern, -1);
        }
    }

    private void setCondition() {
        if(correctCount < Const.STAGE_2) {
            setColor(2);
        } else if (correctCount < Const.STAGE_3) {
            setColor(3);
        } else if (correctCount < Const.STAGE_4) {
            setColor(3, 2);
        } else if (correctCount < Const.STAGE_5) {
            setColor(4);
        } else if (correctCount < Const.STAGE_6) {
            setColor(4, 3);
        } else if (correctCount < Const.STAGE_7) {
            setLength(4);
            setColor(2);
        } else if (correctCount < Const.STAGE_8) {
            setColor(3);
        } else if (correctCount < Const.STAGE_9){
            setLength(4, 3);
            setColor(3, 2);
        } else if (correctCount < Const.STAGE_10){
            setLength(4);
            setColor(4, 3);
        } else if (correctCount < Const.STAGE_11){
            setLength(5);
            setColor(2);
        } else if (correctCount < Const.STAGE_12){
            setColor(3, 2);
        } else {
            setColor(4, 3);
        }
    }

    private double getScale() {
        double scale = 1;
        switch(amount) {
            case 3:
                scale *=  Const.COLOR_3_RATE;
                break;
            case 4:
                scale *=  Const.COLOR_4_RATE;
                break;
        }

        switch(blockLength) {
            case 4:
                scale *= Const.LENGTH_4_RATE;
                break;
            case 5:
                scale *= Const.LENGTH_5_RATE;
                break;
        }
        Log.d(TAG, "scale: " + scale);
        return scale;
    }

    private void setLength(int blockLength) {
        this.blockLength = blockLength;
    }

    private void setLength(int priority, int second) {
        int n = random.nextInt(10);
        if(n < 4) {
            this.blockLength = second;
        } else {
            this.blockLength = priority;
        }
    }


    private void setColor(int color) {
        amount = color;
    }

    private void setColor(int priority, int second) {
        int n = random.nextInt(10);
        if(n < 4) {
            amount = second;
        } else {
            amount = priority;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(countDownTimer != null) {
            countDownTimer.cancel();
        }
        if(handler != null && moveToResult != null) {
            handler.removeCallbacks(moveToResult);
        }
    }
}
