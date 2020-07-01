package cn.booktable.note.viewmodel.notices;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.security.Timestamp;
import java.util.Date;

import cn.booktable.note.viewmodel.converter.DateConverter;
import cn.booktable.uikit.util.DateHelper;

@Entity(tableName = "notices_detail")
public class NoticesDetail {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    int id;

    @ColumnInfo(name = "notice_id")
    String noticeId;//通知源的消息ID

    @NonNull
    @ColumnInfo(name = "user_id")
    String userId;//当前登录用户

    @ColumnInfo(name = "title")
    String title;//标题

    @ColumnInfo(name = "icon")
    String icon;//图标

    @ColumnInfo(name = "detail")
    String detail;//描述

    @ColumnInfo(name = "url")
    String url;//地址

    @ColumnInfo(name = "create_time")
    @TypeConverters(DateConverter.class)
    Date createTime;//创建时间

    @ColumnInfo(name = "start_time")
    @TypeConverters(DateConverter.class)
    Date startTime;

    @ColumnInfo(name = "end_time")
    @TypeConverters(DateConverter.class)
    Date endTime;
    @ColumnInfo(name = "all_day")
    int allDay;//全天

    @ColumnInfo(name = "source")
    String source;//通知源

    @ColumnInfo(name = "reader_status")
    int readerStatus;//阅读状态

    @ColumnInfo(name = "reader_time")
    @TypeConverters(DateConverter.class)
    Date readerTime;//最后一次阅读时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getAllDay() {
        return allDay;
    }

    public void setAllDay(int allDay) {
        this.allDay = allDay;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getReaderStatus() {
        return readerStatus;
    }

    public void setReaderStatus(int readerStatus) {
        this.readerStatus = readerStatus;
    }

    public Date getReaderTime() {
        return readerTime;
    }

    public void setReaderTime(Date readerTime) {
        this.readerTime = readerTime;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuffer sb=new StringBuffer();
        sb.append("id=").append(id).append(",");
        sb.append("userId=").append(userId).append(",");
        sb.append("title=").append(title).append(",");
        sb.append("createTime=").append(DateHelper.formatFullTime(createTime)).append(",");
        sb.append("startTime=").append(DateHelper.formatFullTime(startTime)).append(",");
        sb.append("endTime=").append(DateHelper.formatFullTime(endTime));
        return sb.toString();
    }
}
