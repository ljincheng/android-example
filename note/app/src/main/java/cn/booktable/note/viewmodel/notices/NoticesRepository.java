package cn.booktable.note.viewmodel.notices;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Date;
import java.util.List;

import cn.booktable.note.db.SystemRoomDatabase;
import cn.booktable.note.myApplication;
import cn.booktable.note.viewmodel.user.User;
import cn.booktable.note.viewmodel.user.UserDao;

public class NoticesRepository {

    private NoticesDao mNoticesDao;
    private UserDao mUserDao;
    private LiveData<List<NoticesDetail>> mAllNoticesDetail;
    private LiveData<User> mCurrentUser;


    public NoticesRepository(Application application) {
        SystemRoomDatabase db = SystemRoomDatabase.getDatabase(application);
        mNoticesDao = db.noticesDao();
        mUserDao=db.userDao();
        mAllNoticesDetail=mNoticesDao.getNoticesDetailAll();
    }


    public LiveData<List<NoticesDetail>> getAllNoticesDetail() {

        return mAllNoticesDetail;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(NoticesDetail noticesDetail) {

            SystemRoomDatabase.databaseWriteExecutor.execute(() -> {

                    if (noticesDetail.getCreateTime() == null) {
                        noticesDetail.setCreateTime(new Date());
                    }
                    System.out.println("准备保存进数据库:"+noticesDetail.getNoticeId()+",title="+noticesDetail.getTitle());
                    mNoticesDao.insert(noticesDetail);

            });

    }

    public NoticesDetail getByNoticeId(String noticeId,String source){
        return mNoticesDao.getByNoticeId(noticeId, source);
    }

    public  LiveData<List<NoticesDetail>> todayNotices(){
        return mNoticesDao.todayNotices();
    }


    public void deleteById(int id)
    {
        mNoticesDao.deleteById(id);
    }

    public void deleteBySource(String source){
        mNoticesDao.deleteBySource(source);
    }

}
