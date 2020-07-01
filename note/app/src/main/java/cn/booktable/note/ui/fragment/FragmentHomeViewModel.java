package cn.booktable.note.ui.fragment;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.List;

import cn.booktable.note.viewmodel.notices.NoticesDetail;
import cn.booktable.note.viewmodel.notices.NoticesRepository;
import cn.booktable.note.viewmodel.user.User;
import cn.booktable.note.viewmodel.user.UserRepository;
import cn.booktable.uikit.util.StringHelper;

public class FragmentHomeViewModel extends AndroidViewModel {

    private NoticesRepository mRepository;
    private UserRepository mUserRepository;
    private LiveData<List<NoticesDetail>> mTodayNoticesList;

    public FragmentHomeViewModel(Application application) {
        super(application);
        mRepository = new NoticesRepository(application);
        mUserRepository=new UserRepository(application);
        mTodayNoticesList=mRepository.todayNotices();
    }

    public LiveData<List<NoticesDetail>> todayNotices(){
        return mTodayNoticesList;
    }


    public void saveNotice(List<NoticesDetail> noticesDetailList)
    {
        User topUser= mUserRepository.topUser();
        if(topUser!=null && noticesDetailList.size()>0)
        {
            for(int i=0,k=noticesDetailList.size();i<k;i++)
            {
                NoticesDetail noticesDetail=noticesDetailList.get(i);
                NoticesDetail dbNotice=mRepository.getByNoticeId(noticesDetail.getNoticeId(),noticesDetail.getSource());
                if(dbNotice!=null)
                {
                    noticesDetail.setId(dbNotice.getId());
                    noticesDetail.setReaderStatus(dbNotice.getReaderStatus());
                    noticesDetail.setCreateTime(dbNotice.getCreateTime());
                    noticesDetail.setUserId(dbNotice.getUserId());
                    noticesDetail.setReaderTime(dbNotice.getReaderTime());

                }else {

                    noticesDetail.setUserId(topUser.getId());
                    noticesDetail.setReaderStatus(0);
                    noticesDetail.setCreateTime(new Date());
                }
                mRepository.insert(noticesDetail);
            }
        }

    }

    public void deleteNoticeBySource(String source)
    {
        mRepository.deleteBySource(source);
    }

}
