package com.showcase_gig.net07_birdview.fragment;


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

import com.showcase_gig.net07_birdview.R;
import com.showcase_gig.net07_birdview.constant.ColorEnum;
import com.showcase_gig.net07_birdview.constant.Const;
import com.showcase_gig.net07_birdview.view.GameButton;

import java.util.ArrayList;
import java.util.Collections;

public class GameFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = GameFragment.class.getSimpleName();

    private View mView;
    private int blockLength; // ブロックの長さ
    private int blockAmount; // ブロックの数

    private int roundCount; // ラウンド数
    private ColorEnum correctColor;
    private Vibrator vibrator;
    private long[] pattern;
    private TableLayout tableLayout;

    private double correctCount;
    private double incorrectCount;
    private TextView timeUpText;
    private TextView count_txt;

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

        setGame(3);

        startTimer();

        return mView;
    }

    /** 初期化 */
    private void init() {
        count_txt = (TextView)mView.findViewById(R.id.game_count);
        correctCount = 0;
        incorrectCount = 0;
        blockLength = 3;
        blockAmount = blockLength * blockLength;
        roundCount = 0;
        tableLayout = (TableLayout) mView.findViewById(R.id.game_table);
        timeUpText = (TextView) mView.findViewById(R.id.game_time_up);
        vibrator = (Vibrator) getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);
        pattern = new long[]{0, 200};
    }

    private void setGame(int amount) {
        Log.d(TAG, "amount :" + amount);
        int[] colorAmount = new int[amount];

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
            tableLayout.addView(tableRow);
            for(int j = 0; j < blockLength; j++) {
                tableRow.addView(buttonList.get(buttonNum));
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
        new CountDownTimer(Const.PLAY_TIME, 1000){
            // カウントダウン処理
            public void onTick(long millisUntilFinished){
                if(millisUntilFinished <= 4000) {
                    count_txt.setVisibility(View.VISIBLE);
                    count_txt.setText(String.valueOf(millisUntilFinished / 1000));
                }
            }
            // カウントが0になった時の処理
            public void onFinish(){
                tableLayout.removeAllViews();
                count_txt.setVisibility(View.INVISIBLE);
                timeUpText.setVisibility(View.VISIBLE);
                new Handler().postDelayed(moveToResult, Const.TIME_UP_DISPLAY_TIME);
            }
        }.start();
    }

    private final Runnable moveToResult = new Runnable() {
        @Override
        public void run() {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_container, ResultFragment.newInstance(correctCount, incorrectCount));
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
            tableLayout.removeAllViews();
            setGame(3);
        } else {
            // 不正解
            incorrectCount++;
            vibrator.vibrate(pattern, -1);
        }
    }
}
