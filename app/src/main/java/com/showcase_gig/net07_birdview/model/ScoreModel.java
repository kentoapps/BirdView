package com.showcase_gig.net07_birdview.model;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.showcase_gig.net07_birdview.constant.Const;
import com.showcase_gig.net07_birdview.constant.Db;
import com.showcase_gig.net07_birdview.data.Ranking;
import com.showcase_gig.net07_birdview.intfc.ScoreCallback;
import com.showcase_gig.net07_birdview.intfc.ScoreCheckCallback;
import com.showcase_gig.net07_birdview.intfc.ScorePastBestCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kento on 15/02/07.
 */
public class ScoreModel {
    private static final String TAG = ScoreModel.class.getSimpleName();

    public ScoreModel() {
    }

    public void getScore(final ScoreCallback callback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(Db.SCORE_TBL);
        query.orderByDescending(Db.SCORE_CLM);
        query.setLimit(Const.RANK_LIMIT);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                List<Ranking> rankings = new ArrayList<>();
                if (parseObjects != null) {
                    for(ParseObject po : parseObjects) {
                        Ranking ranking = new Ranking();
                        ranking.setScore(po.getInt(Db.SCORE_CLM));
                        ranking.setUserName(po.getString(Db.USER_NAME_CLM));
                        rankings.add(ranking);
                    }
                }
                callback.response(rankings);
            }
        });

    }

    public void checkAllBest(int score, final ScoreCheckCallback callback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(Db.SCORE_TBL);
        query.orderByDescending(Db.SCORE_CLM);
        query.whereGreaterThan(Db.SCORE_CLM, score);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                callback.response(parseObject == null);
            }
        });
    }


    public void checkMyBest(int score, final ScoreCheckCallback callback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(Db.SCORE_TBL);
        query.orderByDescending(Db.SCORE_CLM);
        query.whereGreaterThan(Db.SCORE_CLM, score);
        query.whereEqualTo(Db.USER_NAME_CLM, ParseUser.getCurrentUser().getUsername());
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                callback.response(parseObject == null);
            }
        });
    }

    public void saveScore(int score) {
        ParseObject parseObject = new ParseObject(Db.SCORE_TBL);
        parseObject.put(Db.SCORE_CLM, score);
        parseObject.put(Db.USER_NAME_CLM, ParseUser.getCurrentUser().getUsername());
        parseObject.saveInBackground();
    }

    public void checkBeat(final ScorePastBestCallback callback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(Db.SCORE_TBL);
        query.orderByDescending(Db.SCORE_CLM);
        query.setLimit(2);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(parseObjects != null) {
                    // 2位のやつが自分じゃなければ誰かのトップを奪った
                    String userName = parseObjects.get(1).getString(Db.USER_NAME_CLM);
                    callback.response(userName);
                }
            }
        });
    }
}
