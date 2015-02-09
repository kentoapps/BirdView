package com.showcase_gig.net07_birdview.fragment;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.showcase_gig.net07_birdview.R;
import com.showcase_gig.net07_birdview.adapter.RankingAdapter;
import com.showcase_gig.net07_birdview.constant.Const;
import com.showcase_gig.net07_birdview.intfc.ScoreCallback;
import com.showcase_gig.net07_birdview.intfc.ScoreCheckCallback;
import com.showcase_gig.net07_birdview.model.ScoreModel;
import com.showcase_gig.net07_birdview.view.CircleProgress;

import java.util.List;

public class ResultFragment extends Fragment {
    private static final String TAG = ResultFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private double correct;
    private double incorrect;
    private View mView;
    private ScoreModel scoreModel;
    private CircleProgress mCircleProgress;
    private int mProgress;
    private double percentage;
    private int score;

    public static ResultFragment newInstance(double param1, double param2) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_PARAM1, param1);
        args.putDouble(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ResultFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            correct = getArguments().getDouble(ARG_PARAM1);
            incorrect = getArguments().getDouble(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_result, container, false);

        scoreModel = new ScoreModel();

        setScore();

        setResult();

        setRate();

        checkRanking();

        setRankingButton();

        setOtherButtons();

        return mView;
    }

    private void checkRanking() {
        final TextView resultMsg = (TextView) mView.findViewById(R.id.result_message);
        scoreModel.checkAllBest(score, new ScoreCheckCallback() {
            @Override
            public void response(boolean isBest) {
                if(isBest) {
                    resultMsg.setVisibility(View.VISIBLE);
                    resultMsg.setText(getResources().getString(R.string.result_all_best));
                } else {
                    scoreModel.checkMyBest(score, new ScoreCheckCallback() {
                        @Override
                        public void response(boolean isBest) {
                            if(isBest) {
                                resultMsg.setVisibility(View.VISIBLE);
                                resultMsg.setText(getResources().getString(R.string.result_my_best));
                            }
                        }
                    });
                }
            }
        });
    }

    private void setRankingButton() {
        mView.findViewById(R.id.result_ranking_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreModel.getScore(new ScoreCallback() {
                    @Override
                    public void response(List list) {
                        ListView listView = new ListView(getActivity());
                        listView.setAdapter(new RankingAdapter(getActivity(), list));
                        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                                .setTitle(getResources().getString(R.string.result_ranking))
                                .setView(listView).create();
                        dialog.show();
                    }
                });
            }
        });
    }

    private void setOtherButtons() {
        mView.findViewById(R.id.result_retry_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, GameFragment.newInstance());
                fragmentTransaction.commit();
            }
        });

        mView.findViewById(R.id.result_back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, StartFragment.newInstance());
                fragmentTransaction.commit();
            }
        });
    }

    private void setRate() {
        TextView rateText = (TextView) mView.findViewById(R.id.result_rate_text);
        percentage = correct / (correct + incorrect) * 100;
        rateText.setText((int) percentage + "%");

        mCircleProgress = (CircleProgress) mView.findViewById(R.id.result_progress);
        mCircleProgress.post(runnableProgress);
        mProgress = 0;
    }

    private Runnable runnableProgress = new Runnable() {
        @Override
        public void run() {
            if (mProgress < (int)percentage) {
                mProgress++;
                mCircleProgress.setProgress(mProgress);
                mCircleProgress.postInvalidate();
                mCircleProgress.postDelayed(this, 5);
            } else {
                mCircleProgress.removeCallbacks(this);
            }
        }
    };

    private void setResult() {
        TextView correctText = (TextView) mView.findViewById(R.id.result_correct);
        correctText.setText(String.valueOf((int)correct));

        TextView incorrectText = (TextView) mView.findViewById(R.id.result_incorrect);
        incorrectText.setText(String.valueOf((int)incorrect));
    }

    private void setScore() {
        TextView scoreText = (TextView) mView.findViewById(R.id.result_score_text);
        score = (int) (correct * Const.CORRECT_SCORE + incorrect * Const.INCORRECT_SCORE);
        scoreText.setText(String.valueOf(score));

        saveScore(score);
    }

    private void saveScore(int score) {
        scoreModel.saveScore(score);
    }
}
