package com.example.readnewspapes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TinTucAdapter extends BaseAdapter{
    private Context context;
    private int layout;
    private List<ItemTinTuc> listitemtintuc;

    public TinTucAdapter(Context context, int layout, List<ItemTinTuc> itemtintuc) {
        this.context = context;
        this.layout = layout;
        this.listitemtintuc = itemtintuc;
    }

    @Override
    public int getCount() {
        return listitemtintuc.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);

        //Ánh xạ
        ImageView imgtintuc = (ImageView) view.findViewById(R.id.imgtintuc);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView description = (TextView) view.findViewById(R.id.description);
        TextView date = (TextView) view.findViewById(R.id.date);

        ItemTinTuc itemTinTuc = listitemtintuc.get(i);

        imgtintuc.setImageBitmap(itemTinTuc.getHinh());
        title.setText(itemTinTuc.getTieude());
        description.setText(itemTinTuc.getMota());
        date.setText(itemTinTuc.getNgay());

        return view;
    }
}



