package com.kentoapps.birdview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kentoapps.birdview.R;
import com.kentoapps.birdview.data.Ranking;

import java.util.List;

/**
 * Created by kento on 15/02/09.
 */
public class RankingAdapter extends ArrayAdapter<Ranking> {
    private static final String TAG = RankingAdapter.class.getSimpleName();
    private final LayoutInflater mInflater;

    public RankingAdapter(Context context, List<Ranking> datas) {
        super(context, 0, datas);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_ranking, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Ranking itemData = getItem(position);

        if (itemData == null) {
            return convertView;
        }

        holder.num.setText(String.valueOf(position + 1));
        holder.score.setText(String.valueOf(itemData.getScore()));
        holder.name.setText(itemData.getUserName());
        return convertView;
    }

    private static class ViewHolder {
        private final TextView num;
        private final TextView score;
        private final TextView name;

        public ViewHolder(View view) {
            num = (TextView) view.findViewById(R.id.ranking_num);
            score = (TextView) view.findViewById(R.id.ranking_score);
            name = (TextView) view.findViewById(R.id.ranking_name);
        }
    }
}
