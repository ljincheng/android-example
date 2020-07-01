package cn.booktable.note.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cn.booktable.uikit.ui.widget.BottomNavigationView;
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

public class MainActivity extends UserActivity {


    private UserProfileViewModel mUserProfileViewModel;

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private LoadUserTask mLoadUserTask=null;

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

            //设置frament容器
//        HomeFragment list = new HomeFragment();
//        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, list).commit();

        setupBottomNavigationBar();

//            HomeFragment firstFragment = new HomeFragment();
//            firstFragment.setArguments(getIntent().getExtras());
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.fragment_container, firstFragment).commit();





//        RecyclerView recyclerView = findViewById(R.id.recyclerview);
//        final UserProfileListAdapter adapter = new UserProfileListAdapter(this);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        // Get a new or existing ViewModel from the ViewModelProvider.
        mUserProfileViewModel = new ViewModelProvider(this).get(UserProfileViewModel.class);
//
//        // Add an observer on the LiveData returned by getAlphabetizedWords.
//        // The onChanged() method fires when the observed data changes and the activity is
//        // in the foreground.
//        mUserProfileViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
//            @Override
//            public void onChanged(@Nullable final List<User> users) {
//                // Update the cached copy of the words in the adapter.
//                adapter.setUser(users);
//            }
//        });
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, NewUserActivity.class);
//                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
//            }
//        });
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
                    fragment = new CloudFragment();
                    ft.add(R.id.fragment_container, fragment,CloudFragment.TAG_CLOUD);
                }else{
                    ft.show(fragment);
                }
                ft.commit();
                mManager.executePendingTransactions();
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

}