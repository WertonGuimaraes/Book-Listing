package com.udacity.wertonguimaraes.booklisting;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wertonguimaraes on 24/04/18.
 */

public class InfoListAdapter extends RecyclerView.Adapter<InfoListAdapter.ViewHolder> {

    private List<Info> mInfoList;

    public InfoListAdapter(List<Info> infoList) {
        mInfoList = infoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_view_items, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return (null != mInfoList ? mInfoList.size() : 0);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Info info = mInfoList.get(position);
        holder.mTitleView.setText(info.getTitle());
        holder.mAuthorView.setText(info.getAuthor());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitleView;
        private TextView mAuthorView;

        private ViewHolder(View view) {
            super(view);
            mTitleView = view.findViewById(R.id.book_title);
            mAuthorView = view.findViewById(R.id.book_author);
        }
    }

}

