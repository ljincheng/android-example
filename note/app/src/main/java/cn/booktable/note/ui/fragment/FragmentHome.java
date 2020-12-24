package cn.booktable.note.ui.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.booktable.note.R;
import cn.booktable.note.viewmodel.notices.NoticesDetail;
import cn.booktable.note.viewmodel.notices.NoticesViewModel;
import cn.booktable.uikit.ui.widget.CreditCardBillView;
import cn.booktable.uikit.ui.widget.GalleryView;
import cn.booktable.uikit.ui.widget.GridView;
import cn.booktable.uikit.ui.widget.HeaderView;
import cn.booktable.uikit.ui.widget.ListItemView;
import cn.booktable.uikit.ui.widget.RadiusImageView;
import cn.booktable.uikit.ui.widget.SectionView;
import cn.booktable.uikit.util.DisplayHelper;
import cn.booktable.uikit.util.DrawableHelper;

public class FragmentHome extends BaseFragment {

    public static String TAG_HOME = "NAV_HOME";

    private ViewGroup root;

    private AppCompatButton btnCal;
    private AppCompatButton btnCalEvents;
    private AppCompatButton btnAddCalEvents;
    private LinearLayout mLayoutRoot;

    private  FragmentHomeViewModel mFragmentHomeViewModel;
    private HomeViewTask mHomeViewTask;

    @Override
    public String getTagName() {
        return TAG_HOME;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // View root= super.onCreateView(inflater, container, savedInstanceState);
        root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        if (savedInstanceState == null) {


        }
        mLayoutRoot=root.findViewById(R.id.layout_root);
        btnCal=root.findViewById(R.id.btn_cal);
        btnCalEvents=root.findViewById(R.id.btn_calEvent);
        btnAddCalEvents=root.findViewById(R.id.btn_addCalEvent);
        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryCalendars();
            }
        });
        btnCalEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryCalEvents();
            }
        });
        btnAddCalEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertCalEvents();
            }
        });

        {
            //添加Gallery

            GalleryView galleryView = new GalleryView(getContext());
//        galleryView.initBannerImageView(Arrays.asList("https://p3.pstatp.com/list/dfic-imagehandler/e8fe84a1-71cd-4b82-8595-13ce704cfb60","https://p3.pstatp.com/list/190x124/pgc-image/6f60a63be7f94218955301177dc82069"),120,20,20);
//            String viewData="{\"height\":120,\"data\":[{\"type\":3,\"text\":\"快速删除、语音输入\",\"img\":\"https://p3.pstatp.com/list/dfic-imagehandler/e8fe84a1-71cd-4b82-8595-13ce704cfb60\",\"scaleType\":\"FIT_XY\"},{\"type\":3,\"text\":\"短信2\",\"scaleType\":\"FIT_XY\",\"img\":\"https://p3.pstatp.com/list/190x124/pgc-image/6f60a63be7f94218955301177dc82069\",\"scaleType\":\"FIT_XY\"},{\"type\":3,\"text\":\"5+环境下使用语音\",\"img\":\"https://i.stack.imgur.com/XeKat.png\",\"scaleType\":\"FIT_XY\"},{\"type\":1,\"text\":\"短信4\",\"img\":\"https://i.stack.imgur.com/XeKat.png\",\"scaleType\":\"FIT_XY\"}]}";
            String viewData="{\"height\":150,\"margin\":16,\"radius\":20,\"data\":[{\"type\":3,\"text\":\"快速删除、语音输入\",\"img\":\"https://p3.pstatp.com/list/dfic-imagehandler/e8fe84a1-71cd-4b82-8595-13ce704cfb60\",\"scaleType\":\"FIT_XY\"},{\"type\":3,\"text\":\"短信2\",\"scaleType\":\"FIT_XY\",\"img\":\"https://p3.pstatp.com/list/190x124/pgc-image/6f60a63be7f94218955301177dc82069\",\"scaleType\":\"FIT_XY\"},{\"type\":3,\"text\":\"5+环境下使用语音\",\"img\":\"https://i.stack.imgur.com/XeKat.png\",\"scaleType\":\"FIT_XY\"},{\"type\":1,\"text\":\"短信4\",\"img\":\"https://i.stack.imgur.com/XeKat.png\",\"scaleType\":\"FIT_XY\"},{\"type\":1,\"text\":\"短信4\",\"img\":\"https://p6-tt.byteimg.com/large/dfic-imagehandler/bc01c08d-27e6-4e6e-a8d1-6f3258d4c92a?from=pc\",\"scaleType\":\"FIT_XY\"},{\"type\":3,\"text\":\"短信2\",\"scaleType\":\"FIT_XY\",\"img\":\"https://p6-tt.byteimg.com/large/dfic-imagehandler/bc01c08d-27e6-4e6e-a8d1-6f3258d4c92a?from=pc\",\"scaleType\":\"FIT_XY\"}]}";
            galleryView.setViewData(JSONObject.parseObject(viewData));
            mLayoutRoot.addView(galleryView);
        }
        {
//            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            RadiusImageView radiusImageView=new RadiusImageView(getContext());
//            radiusImageView.setLayoutParams(lp);
           // radiusImageView.setSrc("https://p6-tt.byteimg.com/large/dfic-imagehandler/bc01c08d-27e6-4e6e-a8d1-6f3258d4c92a?from=pc");
            String viewData="{\"height\":80,\"margin\":16,\"radius\":20,\"scaleType\":\"FIT_XY\",\"img\":\"https://imgm.gmw.cn/attachement/jpg/site215/20200813/4477278567318073720.jpg\"}";
            radiusImageView.setViewData(JSONObject.parseObject(viewData));
            mLayoutRoot.addView(radiusImageView);
        }
        //添加GridView
        try {
           // LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            GridView gridView = new GridView(getContext());
            //gridView.setLayoutParams(lp);
//            String gridData = "{\"cols\":4,\"data\":[{\"type\":1,\"text\":\"快速删除、语音输入\",\"img\":\"https://i.stack.imgur.com/XeKat.png\"},{\"type\":1,\"text\":\"短信2\",\"img\":\"https://i.stack.imgur.com/XeKat.png\"},{\"type\":1,\"text\":\"5+环境下使用语音\",\"img\":\"https://i.stack.imgur.com/XeKat.png\"},{\"type\":1,\"text\":\"短信4\",\"img\":\"https://i.stack.imgur.com/XeKat.png\"},{\"type\":1,\"text\":\"快速删除、语音输入\",\"img\":\"https://i.stack.imgur.com/XeKat.png\"},{\"type\":1,\"text\":\"短信2\",\"img\":\"https://i.stack.imgur.com/XeKat.png\"},{\"type\":1,\"text\":\"5+环境下使用语音\",\"img\":\"https://i.stack.imgur.com/XeKat.png\"},{\"type\":1,\"text\":\"短信4\",\"img\":\"https://i.stack.imgur.com/XeKat.png\"}]}";
            String gridData="{\"cols\":4,\"data\":[{\"type\":1,\"text\":\"快速删除、语音输入\",\"img\":\"https://i.stack.imgur.com/XeKat.png\"},{\"type\":1,\"text\":\"短信2\",\"img\":\"https://i.stack.imgur.com/XeKat.png\"},{\"type\":1,\"text\":\"5+环境下使用语音\",\"img\":\"https://i.stack.imgur.com/XeKat.png\"},{\"type\":1,\"text\":\"短信4\",\"img\":\"https://i.stack.imgur.com/XeKat.png\"},{\"type\":1,\"text\":\"快速删除、语音输入\",\"img\":\"data:image/png;base64,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\"},{\"type\":1,\"text\":\"短信2\",\"img\":\"https://i.stack.imgur.com/XeKat.png\"},{\"type\":1,\"text\":\"5+环境下使用语音\",\"img\":\"https://i.stack.imgur.com/XeKat.png\"},{\"type\":1,\"text\":\"短信4\",\"img\":\"https://i.stack.imgur.com/XeKat.png\"}]}";
            gridView.setViewData(JSONObject.parseObject(gridData));
            mLayoutRoot.addView(gridView);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return root;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mFragmentHomeViewModel= new ViewModelProvider(getActivity()).get(FragmentHomeViewModel.class);

        init_alters(getContext(),mLayoutRoot,mFragmentHomeViewModel);

    }

    @Override
    public void onStart() {
        super.onStart();
//        setActivityActionBarVisible(false);


        mHomeViewTask=new HomeViewTask();

        mHomeViewTask.execute();
    }


    public static final String[] EVENT_PROJECTION = new String[]{
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

    private void queryCalendars() {
        try {
            if(!checkReadCalendarPermission(9001)) {
                return;
            }
            Cursor cur = null;
            ContentResolver cr = getActivity().getContentResolver();// getContentResolver();
            Uri uri = CalendarContract.Calendars.CONTENT_URI;
            cur = cr.query(uri, EVENT_PROJECTION, null, null, null);
            while (cur.moveToNext()) {
                long calID = 0;
                String displayName = null;
                String accountName = null;
                String ownerName = null;

                // Get the field values
                calID = cur.getLong(PROJECTION_ID_INDEX);
                displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
                accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
                ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);

                // Do something with the values...
                System.out.println("calID="+calID+";displayName="+displayName+";accountName="+accountName+";ownerName="+ownerName);

            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private boolean checkReadCalendarPermission(int requestCode)
    {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CALENDAR},requestCode
            );
            return false;
        }
        return true;
    }
    private void queryCalEvents()
    {

        try {
            if(checkReadCalendarPermission(9002)) {
                Cursor cur = null;
                String[] EVENTS_PROJECTION = new String[]{
                        CalendarContract.Events.CALENDAR_ID,                           // 0
                        CalendarContract.Events.TITLE,                  //事件的名称
                        CalendarContract.Events.DESCRIPTION,         //事件的描述
                        CalendarContract.Events.ORGANIZER  ,                // 事件组织者（所有者）的电子邮件。
                        CalendarContract.Events.EVENT_LOCATION,                  //事件的发生地点
                        CalendarContract.Events.DTSTART,                  //事件开始时间，以从公元纪年开始计算的协调世界时毫秒数表示。
                        CalendarContract.Events.DTEND,                // 事件结束时间，以从公元纪年开始计算的协调世界时毫秒数表示。
                        CalendarContract.Events.EVENT_TIMEZONE,  // 事件的时区
                        CalendarContract.Events.ALL_DAY,  // 值为 1 表示此事件占用一整天（按照本地时区的定义）。值为 0 表示它是常规事件，可在一天内的任何时间开始和结束。
                        CalendarContract.Events._ID
                };
                ContentResolver cr = getActivity().getContentResolver();// getContentResolver();
                Uri uri = CalendarContract.Calendars.CONTENT_URI;
                cur = cr.query(CalendarContract.Events.CONTENT_URI, EVENTS_PROJECTION, null, null, null);
                // cur = cr.query(CalendarContract.Events.CONTENT_URI, EVENT_PROJECTION, null, null, null);

                while (cur.moveToNext()) {
                    long calID = 0;
                    String title = null;
                    String desc = null;
                    String organizer = null;
                    String location=null;
                    long dtstart=0;
                    long dtend=0;
                    String timezone=null;
                    int allDay=0;
                    String eventId=null;

                    // Get the field values
                    calID = cur.getLong(PROJECTION_ID_INDEX);
                    title = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
                    desc = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
                    organizer = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);
                    location = cur.getString(4);
                    dtstart = cur.getLong(5);
                    dtend = cur.getLong(6);
                    timezone = cur.getString(7);
                    allDay = cur.getInt(8);
                    eventId=cur.getString(9);

                    Date startDate=new Date(dtstart);
                    Date endDate=new Date(dtend);

                    // Do something with the values...
                    System.out.println("ID="+eventId+";calID=" + calID + ";title=" + title + ";desc=" + desc + ";organizer=" + organizer+";location="+location+";dtstart="+DateFormat.getDateFormat(getContext()).format(startDate)+";dtend="+DateFormat.getDateFormat(getContext()).format(endDate)+";timezone="+timezone+";allDay="+allDay);

                }
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 9001)//读日历权限
        {
            if(PackageManager.PERMISSION_GRANTED == grantResults[0])
            {
                queryCalendars();

            }else {
                Toast.makeText(getContext(),"需要读取日历权限才能继续",Toast.LENGTH_LONG).show();
            }

        }else if(requestCode== 9002)//读日历事件权限
        {
            if(PackageManager.PERMISSION_GRANTED == grantResults[0])
            {
                queryCalEvents();

            }else {
                Toast.makeText(getContext(),"需要读取日历权限才能继续",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void insertCalEvents()
    {
        try{
            Date now=new Date();

            Calendar beginTime = Calendar.getInstance();
            beginTime.setTime(now);
            Calendar endTime = Calendar.getInstance();
            endTime.setTime(now);
            endTime.add(Calendar.DAY_OF_MONTH,3);

            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                    .putExtra(CalendarContract.Events.TITLE, "我在测试日历事件添加")
                    .putExtra(CalendarContract.Events.DESCRIPTION, "我的测试数据")
                    .putExtra(CalendarContract.Events.EVENT_LOCATION, "深圳")
                    .putExtra(CalendarContract.Events.ALL_DAY, true)
                    .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                    .putExtra(Intent.EXTRA_EMAIL, "ljincheng@126.com");
            startActivity(intent);
        }catch (Exception ex)
        {

        }
    }


    private void init_alters(Context context,ViewGroup view,FragmentHomeViewModel fragmentHomeViewModel)
    {
        SectionView sectionView=new SectionView(context);
        sectionView.showDividers(LinearLayout.SHOW_DIVIDER_MIDDLE|LinearLayout.SHOW_DIVIDER_END);

        TextView seeAllBtn=new TextView(context);
        seeAllBtn.setText("SEE ALL");
        HeaderView headerView=new HeaderView(context);
        headerView.setTitle("Alerts").setThumbnail(DrawableHelper.getVectorDrawable(context,R.drawable.ic_calendar_24)).setIconType(HeaderView.ACCESSORY_TYPE_CUSTOM).addIconView(seeAllBtn);//.setThumbnail(DrawableHelper.getVectorDrawable(context,R.drawable.ic_add_circle_black_24dp));
        sectionView.setHeaderView(headerView);

        fragmentHomeViewModel.todayNotices().observe(getActivity(), new Observer<List<NoticesDetail>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(@Nullable final List<NoticesDetail> noticesDetails) {
                sectionView.removeBodyView();
                if(noticesDetails!=null) {
                        for (int i = 0, k = noticesDetails.size(); i < k; i++) {
                        NoticesDetail noticesDetail=noticesDetails.get(i);
                        CreditCardBillView creditCardBillView=new CreditCardBillView(context);
                        creditCardBillView.setBillText(noticesDetail.getTitle(),noticesDetail.getStartTime(),noticesDetail.getEndTime(),noticesDetail.getAllDay());
                        sectionView.addBodyView(creditCardBillView);
                    }
                }
            }
        });


        view.addView(sectionView,0);

    }

    private void init_gridlayout(Context context,ViewGroup view)
    {
        RecyclerView gridRecyclerView=new RecyclerView(context);
        GridLayoutManager gridManager = new GridLayoutManager(getContext(), 4);
        gridRecyclerView.setLayoutManager(gridManager);
        view.addView(gridRecyclerView,1);

    }

    public class HomeViewTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                if(checkReadCalendarPermission(9002)) {
                    Cursor cur = null;
                    String[] EVENTS_PROJECTION = new String[]{
                            CalendarContract.Events.CALENDAR_ID,                           // 0
                            CalendarContract.Events.TITLE,                  //事件的名称
                            CalendarContract.Events.DESCRIPTION,         //事件的描述
                            CalendarContract.Events.ORGANIZER  ,                // 事件组织者（所有者）的电子邮件。
                            CalendarContract.Events.EVENT_LOCATION,                  //事件的发生地点
                            CalendarContract.Events.DTSTART,                  //事件开始时间，以从公元纪年开始计算的协调世界时毫秒数表示。
                            CalendarContract.Events.DTEND,                // 事件结束时间，以从公元纪年开始计算的协调世界时毫秒数表示。
                            CalendarContract.Events.EVENT_TIMEZONE,  // 事件的时区
                            CalendarContract.Events.ALL_DAY,  // 值为 1 表示此事件占用一整天（按照本地时区的定义）。值为 0 表示它是常规事件，可在一天内的任何时间开始和结束。
                            CalendarContract.Events._ID
                    };
                    ContentResolver cr = getActivity().getContentResolver();// getContentResolver();
                    Uri uri = CalendarContract.Calendars.CONTENT_URI;
                    cur = cr.query(CalendarContract.Events.CONTENT_URI, EVENTS_PROJECTION, null, null, "DTSTART desc");
                    // cur = cr.query(CalendarContract.Events.CONTENT_URI, EVENT_PROJECTION, null, null, null);

                    Date now=new Date();
                    long nowtime=now.getTime();
                    List<NoticesDetail> noticesDetailList=new ArrayList<>();
                    while (cur.moveToNext()) {
                        long calID = 0;
                        String title = null;
                        String desc = null;
                        String organizer = null;
                        String location=null;
                        long dtstart=0;
                        long dtend=0;
                        String timezone=null;
                        int allDay=0;
                        String eventId=null;

                        // Get the field values
                        calID = cur.getLong(0);
                        title = cur.getString(1);
                        desc = cur.getString(2);
                        organizer = cur.getString(3);
                        location = cur.getString(4);
                        dtstart = cur.getLong(5);
                        dtend = cur.getLong(6);
                        timezone = cur.getString(7);
                        allDay = cur.getInt(8);
                        eventId=cur.getString(9);


                       // mFragmentHomeViewModel.deleteNoticeBySource("日历");
//                        if(nowtime<=dtend)
//                        {
                            Date startDate=new Date(dtstart);
                            Date endDate=new Date(dtend);


                            // Do something with the values...
                            System.out.println("ID="+eventId+";calID=" + calID + ";title=" + title + ";desc=" + desc + ";organizer=" + organizer+";location="+location+";dtstart="+DateFormat.getDateFormat(getContext()).format(startDate)+";dtend="+DateFormat.getDateFormat(getContext()).format(endDate)+";timezone="+timezone+";allDay="+allDay);

                            NoticesDetail noticesDetail=new NoticesDetail();
                            noticesDetail.setTitle(title);
                            noticesDetail.setStartTime(startDate);
                            noticesDetail.setEndTime(endDate);
                            noticesDetail.setAllDay(allDay);
                            noticesDetail.setDetail(desc);
                            noticesDetail.setSource("日历");
                            noticesDetail.setNoticeId(""+calID+"-"+eventId);
                        noticesDetailList.add(noticesDetail);



//                        }
                    }
                    mFragmentHomeViewModel.deleteNoticeBySource("日历");
                    mFragmentHomeViewModel.saveNotice(noticesDetailList);
                }
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
            return null;
        }
    }
}
