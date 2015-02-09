package com.showcase_gig.net07_birdview.model;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.showcase_gig.net07_birdview.constant.Db;
import com.showcase_gig.net07_birdview.intfc.ScoreCallback;

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
        query.orderByAscending(Db.SCORE_CLM);
        query.setLimit(10);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                callback.response();
            }
        });

    }

    public void saveScore(int score) {
        ParseObject parseObject = new ParseObject(Db.SCORE_TBL);
        parseObject.put(Db.SCORE_CLM, score);
        parseObject.put(Db.USER_NAME_CLM, ParseUser.getCurrentUser().getUsername());
        parseObject.saveInBackground();
    }
}
