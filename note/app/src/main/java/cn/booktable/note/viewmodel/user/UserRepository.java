package cn.booktable.note.viewmodel.user;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import cn.booktable.note.db.SystemRoomDatabase;

public class UserRepository {

    private UserDao mUserDao;

    public UserRepository(Application application) {
        SystemRoomDatabase db = SystemRoomDatabase.getDatabase(application);
        mUserDao = db.userDao();
        //mAllUsers = mUserDao.getAlphabetizedUsers();
    }


    void insert(User user) {
        SystemRoomDatabase.databaseWriteExecutor.execute(() -> {
            mUserDao.insert(user);
        });
    }

    public User findUserById(String id){
        return mUserDao.findById(id);
    }


    public User findUserByToken(String token)
    {
        return mUserDao.findByToken(token);
    }

    public User topUser()
    {
        return mUserDao.getTopUser();
    }

    public void saveUser(User user,boolean isTop)
    {
        SystemRoomDatabase.databaseWriteExecutor.execute(() -> {
            User dbUser= mUserDao.findById(user.getId());
            if(isTop)
            {
                mUserDao.updateDownUserAll(user.getId());

            }
            if(dbUser!=null) {
                mUserDao.updateBaseInfo(user.getId(), user.token, user.userName);
            }else{
                mUserDao.insert(user);
            }
            if(isTop)
            {
                mUserDao.updateTopUser(user.getId());
            }
        });
    }
}
