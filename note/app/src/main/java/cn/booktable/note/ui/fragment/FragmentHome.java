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


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.booktable.note.R;
import cn.booktable.note.viewmodel.notices.NoticesDetail;
import cn.booktable.note.viewmodel.notices.NoticesViewModel;
import cn.booktable.uikit.ui.widget.CreditCardBillView;
import cn.booktable.uikit.ui.widget.HeaderView;
import cn.booktable.uikit.ui.widget.ListItemView;
import cn.booktable.uikit.ui.widget.SectionView;
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
        setActivityActionBarVisible(false);


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
