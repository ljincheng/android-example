package cn.booktable.note.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import cn.booktable.note.R;
import cn.booktable.note.ui.activity.ActivityWeb;
import cn.booktable.note.ui.activity.LoginActivity;
import cn.booktable.note.ui.fragment.FragmentWeb;
import cn.booktable.note.viewmodel.notices.NoticesDetail;
import cn.booktable.uikit.base.UIFragmentActivity;

public class NoticesListAdapter extends RecyclerView.Adapter<NoticesListAdapter.ProfileViewHolder> {

    public class ProfileViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleView;
        private final TextView detailView;
        private final TextView overlineView;

        private ProfileViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.title);
            detailView = itemView.findViewById(R.id.detail);
            overlineView = itemView.findViewById(R.id.overline);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  FragmentWeb fragmentWeb= FragmentWeb.newInstance("http://10.52.10.128:8003/");
                    Context context= v.getContext();
//                    if(context instanceof UIFragmentActivity)
//                    {
//
//                        ((UIFragmentActivity)context).startUIFragment(FragmentWeb.newInstance("http://10.52.10.128:8003/#/home"));
//                    }else {
                        Intent intent = new Intent(v.getContext(), ActivityWeb.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("url", "http://10.52.10.128:8003/");
                        intent.putExtras(bundle);
                        //intent.putExtra("url", "http://10.52.10.128:8003/#/home");
                        context.startActivity(intent, bundle);
//                    }
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private List<NoticesDetail> mNoticesDetailList; // Cached copy of words

    public NoticesListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public NoticesListAdapter.ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item_notices, parent, false);
        return new NoticesListAdapter.ProfileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoticesListAdapter.ProfileViewHolder holder, int position) {
        if (mNoticesDetailList != null) {
            NoticesDetail current = mNoticesDetailList.get(position);

            Date date = current.getCreateTime();
            SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy/MM/dd HH:mm");
            holder.overlineView.setText(myFmt1.format(date));
            holder.titleView.setText(current.getTitle());
            holder.detailView.setText(current.getDetail());

        } else {
            // Covers the case of data not being ready yet.
            holder.titleView.setText("Empty");
        }
    }

    public void setNoticesDetail(List<NoticesDetail> noticesDetails) {
        mNoticesDetailList = noticesDetails;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mNoticesDetailList != null)
            return mNoticesDetailList.size();
        else return 0;
    }
}
