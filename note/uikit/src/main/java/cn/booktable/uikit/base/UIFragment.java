package cn.booktable.uikit.base;

import android.app.Activity;

import androidx.fragment.app.Fragment;

public abstract class UIFragment extends Fragment {

    public abstract String getTagName();


    public UIFragmentActivity getUIFragmentActivity()
    {
        Activity parentActivity=getActivity();
        if(parentActivity instanceof UIFragmentActivity) {
            return (UIFragmentActivity)parentActivity;
        }
        return  null;
    }

    public void setActivityActionBarVisible(boolean visible)
    {
        UIFragmentActivity parentActivity=getUIFragmentActivity();
        if(parentActivity!=null) {
            parentActivity.setActionBarVisible(visible);
        }
    }

}
