package com.it.turtle.embrapashare.socialnetwork.views.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.it.turtle.embrapashare.R;
import com.it.turtle.embrapashare.socialnetwork.models.Culture;

import java.util.List;


public class CultureFocusAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<Culture> mValues;

    public CultureFocusAdapter(Context context, List<Culture> values) {
        mContext = context;
        mValues = values;
    }

    @Override
    public int getCount() {
        return mValues.size();
    }

    @Override
    public Object getItem(int i) {
        return mValues.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(mContext, R.layout.culture_element, null);

        ImageView cultureImageView = (ImageView) convertView.findViewById(R.id.cultureImageView);
        String image = mValues.get(position).getImage();

        try {
            Glide.with(cultureImageView.getContext()).load(Uri.parse(image)).into(cultureImageView);
        } catch (Exception e) {
            e.printStackTrace();
            Glide.with(cultureImageView.getContext()).load(Uri.parse("http://jogosdecarros3.com/wp-content/uploads/2013/10/carrrrs.png")).into(cultureImageView);
        }

        TextView cultureName = (TextView) convertView.findViewById(R.id.cultureName);
        cultureName.setText(mValues.get(position).getName());

        return convertView;
    }
}
