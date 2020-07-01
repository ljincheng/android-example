package cn.booktable.note.viewmodel.notices;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import cn.booktable.note.viewmodel.user.User;

@Dao
public interface NoticesDao {

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NoticesDetail noticesDetail);

    @Insert
    void insertAll(List<NoticesDetail> noticesDetailList);

    @Query("SELECT t1.* from notices_detail t1 left join sys_user t2 on t1.user_id=t2.id where t2.is_top = 1 ORDER BY datetime(t1.end_time) DESC")
    LiveData<List<NoticesDetail>> getNoticesDetailAll();

    @Query("DELETE FROM notices_detail")
    void deleteNoticesDetailAll();

    @Query("DELETE FROM notices_detail where user_id = :userId ")
    void deleteUserNoticesDetail(Integer userId);

    @Query("SELECT * from notices_detail where id = :id ")
    NoticesDetail getById(Integer id);

    @Query("SELECT * from notices_detail where notice_id = :noticeId and source=:source")
    NoticesDetail getByNoticeId(String noticeId,String source);

    @Query("DELETE FROM notices_detail where id = :id ")
    void deleteById(int id);

    @Query("DELETE FROM notices_detail where source = :source ")
    void deleteBySource(String source);

    @Query("SELECT t1.* from notices_detail t1 left join sys_user t2 on t1.user_id=t2.id where t2.is_top = 1  and  date(t1.end_time)>= date('now') and date(t1.start_time) < date('now','start of day','+1 day') order by datetime(create_time) desc")
    LiveData<List<NoticesDetail>> todayNotices();
}
