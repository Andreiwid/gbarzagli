package com.it.turtle.embrapashare.socialnetwork.views.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.it.turtle.embrapashare.R;
import com.it.turtle.embrapashare.socialnetwork.models.Post;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Adapts posts information to show in a ListView.
 */
public class PostAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<Post> mValues;

    public PostAdapter(Context context, List<Post> values) {
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
        convertView = View.inflate(mContext, R.layout.post_layout, null);

        ImageView userImageView = (ImageView) convertView.findViewById(R.id.userPhoto);
        Glide.with(userImageView.getContext()).load(Uri.parse(mValues.get(position).getUserPhoto())).into(userImageView);

        TextView userTextView = (TextView) convertView.findViewById(R.id.userName);
        userTextView.setText(mValues.get(position).getUserName());

        ImageView postImageView = (ImageView) convertView.findViewById(R.id.postImage);
        Glide.with(postImageView.getContext()).load(Uri.parse(mValues.get(position).getPostImage())).into(postImageView);

        TextView postTextView = (TextView) convertView.findViewById(R.id.postName);
        postTextView.setText(mValues.get(position).getPostDescription());

        return convertView;
    }
}
