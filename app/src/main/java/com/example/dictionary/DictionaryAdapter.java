package com.example.dictionary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DictionaryAdapter extends BaseAdapter {
    List<HistoryModel> items;

    public DictionaryAdapter(List<HistoryModel> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemViewHolder viewHolder;

        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dictionary_item, null);
            viewHolder = new ItemViewHolder();
            viewHolder.textTitle = view.findViewById(R.id.textItem);
            view.setTag(viewHolder);
        }
        else
            viewHolder = (ItemViewHolder) view.getTag();

        HistoryModel item = items.get(i);

        viewHolder.textTitle.setText(item.getWords());

        return view;
    }
    static class ItemViewHolder {
        TextView textTitle;

    }
}
