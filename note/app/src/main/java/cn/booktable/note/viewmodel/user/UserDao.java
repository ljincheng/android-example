package cn.booktable.note.viewmodel.user;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;


@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Query("SELECT * from sys_user where id=:id ")
    User findById(String id);

    @Query("SELECT * from sys_user where token=:token ")
    User findByToken(String token);

    @Query("UPDATE sys_user set token=:token,user_name=:userName where id =:id")
    void updateBaseInfo(String id,String token,String userName);

    @Query("SELECT * from sys_user ORDER BY user_name ASC")
    LiveData<List<User>> getAlphabetizedUsers();

    @Query("DELETE FROM sys_user")
    void deleteAll();

    @Query("DELETE FROM sys_user where id =:id")
    void deleteByUserId(String id);

    @Query("UPDATE sys_user set is_top=1 where id =:id")
    void updateTopUser(String id);

    @Query("UPDATE sys_user set is_top=0 where id != :id")
    void updateDownUserAll(String id);

    @Query("SELECT * from sys_user where is_top=1 ")
    User getTopUser();

    @Query("SELECT * from sys_user where is_top=1 ")
    User getCurrentUser();
}
