package cn.booktable.note.ui.activity;

import cn.booktable.note.viewmodel.user.User;
import cn.booktable.uikit.base.UIFragmentActivity;

public abstract class UserActivity extends UIFragmentActivity {
    protected User currentUser;

    public void setCurrentUser(User user)
    {
        this.currentUser=user;
    }

    public User getCurrentUser()
    {
        return currentUser;
    }


}
