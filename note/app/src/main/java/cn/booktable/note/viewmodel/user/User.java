package cn.booktable.note.viewmodel.user;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sys_user")
public class User {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    String id;
    @ColumnInfo(name = "user_name")
    String userName;
    @ColumnInfo(name = "icon")
    String icon;
    @ColumnInfo(name = "gender")
    String gender;
    @ColumnInfo(name = "token")
    String token;

    @ColumnInfo(name = "is_top")
    Integer isTop;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }
}
