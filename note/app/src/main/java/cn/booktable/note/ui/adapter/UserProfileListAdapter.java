package cn.booktable.note.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.booktable.note.R;
import cn.booktable.note.viewmodel.notices.NoticesDetail;

public class UserProfileListAdapter extends RecyclerView.Adapter<UserProfileListAdapter.ProfileViewHolder> {

    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private ProfileViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<NoticesDetail> mNoticesDetailList; // Cached copy of words

    public UserProfileListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ProfileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProfileViewHolder holder, int position) {
        if (mNoticesDetailList != null) {
            NoticesDetail current = mNoticesDetailList.get(position);
            holder.wordItemView.setText(current.getTitle());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No User");
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
