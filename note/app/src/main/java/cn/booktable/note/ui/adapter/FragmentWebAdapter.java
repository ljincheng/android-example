package cn.booktable.note.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import cn.booktable.note.ui.fragment.FragmentWeb;

public class FragmentWebAdapter extends FragmentPagerAdapter {

    private String[] mWebUrls;

    public FragmentWebAdapter(FragmentManager fm, int behavior,  String... mWebUrls) {
        super(fm, behavior);
        this.mWebUrls = mWebUrls;
    }

    @Override
    public FragmentWeb getItem(int position) {
        if(mWebUrls==null)
        {
            return null;
        }
        return FragmentWeb.newInstance(mWebUrls[position]);

    }

    @Override
    public int getCount() {
        return mWebUrls==null?0:mWebUrls.length;
    }

}
