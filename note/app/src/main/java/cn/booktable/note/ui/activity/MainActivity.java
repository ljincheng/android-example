package cn.booktable.note.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cn.booktable.mediaplayer.activities.OneVideoActivity;
import cn.booktable.note.ui.fragment.FragmentTest;
import cn.booktable.note.ui.viewmodel.WidgetDo;
import cn.booktable.note.ui.viewmodel.WidgetModel;
import cn.booktable.uikit.ui.activities.WidgetEvents;
import cn.booktable.uikit.ui.widget.BottomNavigationView;

import com.alibaba.fastjson.JSONObject;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import java.util.List;

import cn.booktable.note.Ipsum;
import cn.booktable.note.R;
import cn.booktable.note.myApplication;
import cn.booktable.note.ui.activity.UserActivity;
import cn.booktable.note.ui.fragment.BaseFragment;
import cn.booktable.note.ui.fragment.FragmentHome;
import cn.booktable.note.ui.fragment.FragmentNotices;
import cn.booktable.note.ui.fragment.FragmentSettings;
import cn.booktable.note.viewmodel.user.UserProfileViewModel;
import cn.booktable.uikit.ui.widget.WidgetContext;

public class MainActivity extends UserActivity implements WidgetEvents {


    private UserProfileViewModel mUserProfileViewModel;

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private LoadUserTask mLoadUserTask=null;
    protected WidgetModel widgetModel;

    @Override
    public int fragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            if (savedInstanceState != null) {
                return;
            }

            // 应用栏设置
            Toolbar myToolbar = findViewById(R.id.my_toolbar);
            setSupportActionBar(myToolbar);

            ActionBar ab = getSupportActionBar();
            ab.setTitle("这是什么？");
            ab.setSubtitle("hello world");

        setupBottomNavigationBar();

        widgetModel=new ViewModelProvider(this).get(WidgetModel.class);
        widgetModel.loadFromDatabase();

        mUserProfileViewModel = new ViewModelProvider(this).get(UserProfileViewModel.class);

    }

    /**
     * 根据应用栏的选择ID设置显示Fragment
     * @param navId
     */
    private void showSelectedFragment(int navId)
    {
        FragmentManager mManager=  getSupportFragmentManager();
        FragmentTransaction ft = mManager.beginTransaction();

        List<Fragment> items= mManager.getFragments();

        for(Fragment item:items)
        {
            if(!item.isStateSaved()) {
                ft.hide(item);
            }
        }

        switch (navId)
        {
            case R.id.nav_home:
            {
                String mTagName= FragmentHome.TAG_HOME;
                Fragment fragment=mManager.findFragmentByTag(mTagName);
                if(fragment==null) {
                    fragment = new FragmentHome();
                    ft.add(R.id.fragment_container, fragment,mTagName);
                }else{
                    ft.show(fragment);
                }
                ft.commit();
                mManager.executePendingTransactions();

            }
            break;
            case R.id.nav_notice:
            {
                String mTagName= FragmentNotices.TAG_NAME;
                Fragment fragment=mManager.findFragmentByTag(mTagName);
                if(fragment==null) {
                    fragment = new FragmentNotices();
                    ft.add(R.id.fragment_container, fragment,FragmentNotices.TAG_NAME);
                }else{
                    ft.show(fragment);
                }
                ft.commit();
                mManager.executePendingTransactions();
            }
            break;
            case R.id.nav_cloud:
            {
                String mTagName=CloudFragment.TAG_CLOUD;
                Fragment fragment=mManager.findFragmentByTag(mTagName);
                if(fragment==null) {
                    fragment = new FragmentTest();
                    ft.add(R.id.fragment_container, fragment,CloudFragment.TAG_CLOUD);
                }else{
                    ft.show(fragment);
                }
                ft.commit();
                mManager.executePendingTransactions();
                if(fragment instanceof BaseFragment)
                {
                    ((BaseFragment)fragment).onNavigationItemSelected();
                }
                widgetModel.loadFromDatabase();
            }
            break;
            case R.id.nav_settings:
            {
                String mTagName= FragmentSettings.TAG_NAME;
                Fragment fragment=mManager.findFragmentByTag(mTagName);
                if(fragment==null) {
                    fragment = FragmentSettings.newInstance();
                    ft.add(R.id.fragment_container, fragment,FragmentSettings.TAG_NAME);
                }else{
                    ft.show(fragment);
                }
                ft.commit();
                mManager.executePendingTransactions();

            }
            break;
        }
    }

    private void setupBottomNavigationBar()
    {
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                showSelectedFragment(menuItem.getItemId());
                return true;
            }


        });
        int defaultSelectedFragment=0;
        showSelectedFragment(R.id.nav_home);
        bottomNavigationView.getMenu().getItem(defaultSelectedFragment).setChecked(true);
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        bottomNavigationView.setItemHorizontalTranslationEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            mLoadUserTask = new LoadUserTask();
            mLoadUserTask.execute();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }


    public static class CloudFragment extends ListFragment {
        public static String TAG_CLOUD="NAV_CLOUD";

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            setListAdapter(new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_list_item_1, Ipsum.TITLES3));
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Log.i("FragmentList", "Item clicked: " + id);
        }
    }

    public class LoadUserTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
//            LiveData<List<User>> allUserList= mUserProfileViewModel.getAllUsers();
            String token= myApplication.getToken();
            if(token!=null && token.length()>0) {
                currentUser=mUserProfileViewModel.userRepository().findUserByToken(token);
                mUserProfileViewModel.setCurrentUser(currentUser);
                //setCurrentUser(currentUser);
               return currentUser!=null;
            }
            return false;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            mLoadUserTask=null;
            FragmentManager mManager=  getSupportFragmentManager();
            List<Fragment> items= mManager.getFragments();
            for(Fragment item:items)
            {
                if(item instanceof BaseFragment)
                {
                    BaseFragment baseFragment= (BaseFragment)((BaseFragment) item);
                    baseFragment.setCurrentUser(currentUser);
                }
            }

        }
    }


   private boolean viewClickEventIdle=true;//视图点击事件处理情况

    @Override
    public void OnClickEvent(View view, JSONObject viewData) {
        if(viewClickEventIdle) {
            try {
                viewClickEventIdle = false;
                if(viewData.containsKey(WidgetContext.EVENT_KEY)) {
                    JSONObject eventObj=viewData.getJSONObject(WidgetContext.EVENT_KEY);
                    if(eventObj.containsKey("videoPath")) {
                        String videoPath=eventObj.getString("videoPath");
                        String videoTitle=eventObj.containsKey("videoTitle")?eventObj.getString("videoTitle"):"";
                        //"http://10.50.22.58:8096/Items/140c89f34bd5eefed88ad8d87138af3c/Download?api_key=40d61b09b0574e94b2bf9aa271b1699d"
                        OneVideoActivity.intentTo(this, videoPath, videoTitle);
                    }else if(eventObj.containsKey("webPath"))
                    {
                        String webPath=eventObj.getString("webPath");
                        String webTitle=eventObj.containsKey("webTitle")?eventObj.getString("webTitle"):"";
                        ActivityWeb.intentTo(this,webPath,webTitle);
                    }
                }
                //        OneVideoActivity.intentTo(this, "http://media.booktable.cn/Items/140c89f34bd5eefed88ad8d87138af3c/Download?api_key=40d61b09b0574e94b2bf9aa271b1699d", "海洋奇缘");
            }finally {
                viewClickEventIdle=true;
            }
        }
    }
}
