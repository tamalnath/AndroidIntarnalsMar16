package com.tamalnath.androidinternals;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Data> dataList = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, @LayoutRes int layout) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout, parent, false);
        if (layout == R.layout.card_key_value) {
            return new KeyValueHolder(view);
        } else {
            return new Holder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        dataList.get(position).decorate(holder);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getLayout();
    }

    void addData(Data data) {
        dataList.add(data);
    }

    void addHeader(final String header) {
        dataList.add(new Data() {

            public int getLayout() {
                return R.layout.card_header;
            }

            @Override
            public void decorate(RecyclerView.ViewHolder holder) {
                ((TextView) holder.itemView).setText(header);
            }
        });
    }

    void addMap(Map<?, ?> map) {
        for (final Map.Entry<?, ?> entry : map.entrySet()) {
            dataList.add(new Data() {

                @Override
                public int getLayout() {
                    return R.layout.card_key_value;
                }

                @Override
                public void decorate(RecyclerView.ViewHolder viewHolder) {
                    KeyValueHolder holder = (KeyValueHolder) viewHolder;
                    holder.keyView.setText(Utils.toString(entry.getKey()));
                    holder.valueView.setText(Utils.toString(entry.getValue(), "\n", "", "", null));
                }
            });
        }
    }

    interface Data {

        void decorate(RecyclerView.ViewHolder viewHolder);

        @LayoutRes
        int getLayout();
    }

    private static class Holder extends RecyclerView.ViewHolder {

        Holder(View itemView) {
            super(itemView);
        }
    }

    private static class KeyValueHolder extends RecyclerView.ViewHolder {

        TextView keyView;
        TextView valueView;

        KeyValueHolder(View itemView) {
            super(itemView);
            keyView = (TextView) itemView.findViewById(R.id.key);
            valueView = (TextView) itemView.findViewById(R.id.value);
        }
    }

}