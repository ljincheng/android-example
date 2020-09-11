package cn.booktable.note.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import cn.booktable.note.viewmodel.converter.DateConverter;

@Entity(tableName = "sys_widgets")

public class WidgetDo {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "section")
    private Integer section;

    @ColumnInfo(name = "data")
    private String data;

    @ColumnInfo(name = "weight")
    private Integer weight;

    @ColumnInfo(name = "create_time")
    @TypeConverters(DateConverter.class)
    Date createTime;//创建时间


    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
