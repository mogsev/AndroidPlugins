package com.mogsev.androidplugins.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mogsev.androidplugins.R;
import com.mogsev.androidplugins.model.HashingSpeed;
import com.mogsev.androidplugins.util.ObjectsHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eugene Sikaylo (mogsev@gmail.com)
 */
public class HashingSpeedRVAdapter extends RecyclerView.Adapter<HashingSpeedRVAdapter.ViewHolder> {
    private static final String TAG = HashingSpeedRVAdapter.class.getSimpleName();

    private final List<HashingSpeed> mList = new ArrayList<>();

    public HashingSpeedRVAdapter() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_nicehash_hashing_speed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final HashingSpeed hashingSpeed = mList.get(position);
        holder.tvProfitabilityAboveLtc.setText(hashingSpeed.getProfitabilityAboveLtc());
        holder.tvPrice.setText(hashingSpeed.getPrice());
        holder.tvProfitabilityLtc.setText(hashingSpeed.getProfitabilityLtc());
        holder.tvAlgo.setText(hashingSpeed.getAlgorithmName());
        holder.tvSpeed.setText(hashingSpeed.getSpeed());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(final List<HashingSpeed> list) {
        if (ObjectsHelper.isNull(list)) {
            return;
        }
        if (mList.isEmpty()) {
            mList.addAll(list);
        } else {
            List<HashingSpeed> newItems = new ArrayList<>();
            for (HashingSpeed item1 : list) {
                for (HashingSpeed item2 : mList) {
                    if (item1.equals(item2)) break;
                    if (item1.getAlgo() == item2.getAlgo() && !item1.equals(item2)) {
                        item2 = item1;
                        break;
                    }
                    newItems.add(item1);
                }
            }
            mList.addAll(mList.size() - 1, newItems);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvProfitabilityAboveLtc;
        private TextView tvPrice;
        private TextView tvProfitabilityLtc;
        private TextView tvAlgo;
        private TextView tvSpeed;

        public ViewHolder(final View view) {
            super(view);

            tvProfitabilityAboveLtc = (TextView) view.findViewById(R.id.tvProfitabilityAboveLtc);
            tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            tvProfitabilityLtc = (TextView) view.findViewById(R.id.tvProfitabilityLtc);
            tvAlgo = (TextView) view.findViewById(R.id.tvAlgo);
            tvSpeed = (TextView) view.findViewById(R.id.tvSpeed);
        }
    }
}
