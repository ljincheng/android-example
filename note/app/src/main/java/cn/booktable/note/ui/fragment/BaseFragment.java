package cn.booktable.note.ui.fragment;

import android.app.Activity;

import cn.booktable.note.ui.activity.UserActivity;
import cn.booktable.note.viewmodel.user.User;
import cn.booktable.uikit.base.UIFragment;

public abstract class BaseFragment extends UIFragment {

    public void setCurrentUser(User user){}

    public User getCurrentUser()
    {
          Activity activity= getActivity() ;
          if(activity instanceof UserActivity)
          {
             return  ((UserActivity) activity).getCurrentUser();
          }
          return null;
    }


}
