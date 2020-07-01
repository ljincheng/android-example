package cn.booktable.note.viewmodel.notices;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cn.booktable.note.viewmodel.user.User;


public class NoticesViewModel extends AndroidViewModel {

    private NoticesRepository mRepository;
    private LiveData<List<NoticesDetail>> mNoticesDetails;
    private LiveData<List<NoticesDetail>> mTodayNoticesList;

    public NoticesViewModel(Application application) {
        super(application);
        mRepository = new NoticesRepository(application);
        mNoticesDetails = mRepository.getAllNoticesDetail();
        mTodayNoticesList=mRepository.todayNotices();
    }

    public NoticesRepository noticesRepository()
    {
        return mRepository;
    }

    public LiveData<List<NoticesDetail>> todayNotices(){
        return mTodayNoticesList;
    }

    public LiveData<List<NoticesDetail>> getAllNoticesDetail() {
        return mNoticesDetails;
    }


    public  void insert(NoticesDetail noticesDetail) {
        mRepository.insert(noticesDetail);
    }

    public NoticesDetail getByNoticeId(String noticeId,String source){
        return mRepository.getByNoticeId(noticeId, source);
    }

}

