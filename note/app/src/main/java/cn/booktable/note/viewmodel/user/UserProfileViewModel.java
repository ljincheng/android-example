package cn.booktable.note.viewmodel.user;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class UserProfileViewModel extends AndroidViewModel {

    private UserRepository mRepository;

    private MutableLiveData<User> mCurrentUser;//当前用户

    public UserProfileViewModel(Application application) {
        super(application);
        mRepository = new UserRepository(application);
        mCurrentUser = new MutableLiveData<User>();
    }

    public UserRepository userRepository()
    {
        return mRepository;
    }

    public MutableLiveData<User> currentUser()
    {
        return mCurrentUser;
    }

    public void setCurrentUser(User user)
    {
        mCurrentUser.postValue(user);
    }




}
