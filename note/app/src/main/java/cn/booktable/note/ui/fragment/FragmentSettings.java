package cn.booktable.note.ui.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import cn.booktable.note.R;
import cn.booktable.note.ui.activity.LoginActivity;
import cn.booktable.note.viewmodel.user.User;
import cn.booktable.note.viewmodel.user.UserProfileViewModel;
import cn.booktable.uikit.ui.widget.GroupListView;
import cn.booktable.uikit.ui.widget.HeaderView;
import cn.booktable.uikit.ui.widget.ListItemView;
import cn.booktable.uikit.ui.widget.SectionView;
import cn.booktable.uikit.util.DisplayHelper;
import cn.booktable.uikit.util.DrawableHelper;
import cn.booktable.uikit.util.LayoutHelper;

public class FragmentSettings extends BaseFragment {

    private FragmentSettingsViewModel mViewModel;
    public static String TAG_NAME="NAV_SETTINGS";

    private TextView mAccountUserName;
    private User currentUser;
    private UserProfileViewModel mUserProfileViewModel;
    private LinearLayout mRootBodyView;


    @Override
    public String getTagName() {
        return TAG_NAME;
    }

    public static FragmentSettings newInstance() {
        return new FragmentSettings();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_settings, container, false);
        mAccountUserName=view.findViewById(R.id.account_userName);
        mRootBodyView=view.findViewById(R.id.layout_bodyView);

        initGroupListView(getContext(),view);
        initSectionView(getContext(),view);
        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUserProfileViewModel = new ViewModelProvider(getActivity()).get(UserProfileViewModel.class);

        //===========View 绑定 LiveData=============
        //设置用户名
        mUserProfileViewModel.currentUser().observe(getActivity(),(User user)->{
            if(user!=null) {
                mAccountUserName.setText(user.getUserName());
            }else{
                mAccountUserName.setText(R.string.login);
            }
        });

            mAccountUserName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //if(getCurrentUser()==null) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivityForResult(intent, 101);
                    //}
                }
            });


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

    }


    private void initGroupListView(Context context, View view)
    {
        GroupListView groupListView=new GroupListView(context);
        groupListView.showDividers(LinearLayout.SHOW_DIVIDER_MIDDLE|LinearLayout.SHOW_DIVIDER_END);

        String header="提醒设置";
        TextView headerRightTextView=new TextView(context);
        headerRightTextView.setText("显示/隐藏");

        HeaderView headerView=new HeaderView(context);
        headerView.setIconType(HeaderView.ACCESSORY_TYPE_CUSTOM).addIconView(headerRightTextView);
        headerView.setTitle(header).setSubtitle("每日提醒消息设置").addTo(groupListView);//.setThumbnail(DrawableHelper.getVectorDrawable(context,R.drawable.ic_add_circle_black_24dp));

        String[] items=new String[]{"银行账单提醒","工作安排提醒","设置每日工作时间安排;休假、请假、年假、节假日等假日设置提醒;议会、项目启动会等会议提醒;备忘提醒；","其他提醒"};
        String[] details=new String[]{"设置银行每月账单到期还款提醒","设置每日工作时间安排;休假、请假、年假、节假日等假日设置提醒;议会、项目启动会等会议提醒;备忘提醒；","这是比较长的标题",null};

        int thumbnailWidth=0;
        for(int i=0,k=items.length;i<k;i++) {

            TextView rightTextView=new TextView(context);
            rightTextView.setText(" 1.50");
            TextView rightTextView2=new TextView(context);
            rightTextView2.setText(" 20.40");

            GroupListView iconGroup=new GroupListView(context);
            iconGroup.addView(rightTextView);
            iconGroup.addView(rightTextView2);
            iconGroup.setOrientation(LinearLayout.HORIZONTAL);


            ListItemView listItemView=new ListItemView(context);
            Drawable thumbnail=DrawableHelper.getVectorDrawable(context,R.drawable.ic_nav_notice);

            iconGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    groupListView.removeView(listItemView);
                }
            });
            listItemView.setTitle(items[i])
                    .setSubtitle(details[i])
                    .setThumbnail(i==0?thumbnail:null)
                    .setIconType(ListItemView.ACCESSORY_TYPE_CUSTOM).setIconOrientation(ListItemView.MIDDLE).addIconView(iconGroup)
                    .addTo(groupListView);
            thumbnailWidth=thumbnail.getIntrinsicWidth();
        }
        if(thumbnailWidth!=0)
        {
            thumbnailWidth+=DisplayHelper.dp2px(context,16);
        }
        groupListView.setDividerPaddingLeft(thumbnailWidth);

        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin=DisplayHelper.dp2px(context,16);
        int shadowElevation=DisplayHelper.dp2px(context,4);
        lp.setMargins(margin,margin,margin,margin);
        groupListView.setLayoutParams(lp);
        groupListView.setRadius(margin);
//        groupListView.setRadiusAndShadow(margin,shadowElevation,0.45f);
        groupListView.setBorderColor(Color.GRAY).setBorderWidth(DisplayHelper.dp2px(context,2));
        mRootBodyView.addView(groupListView);

    }

    private void initSectionView(Context context, View view)
    {
        SectionView sectionView=new SectionView(context);
        sectionView.setDividerPaddingLeft(0);
        sectionView.showDividers(LinearLayout.SHOW_DIVIDER_MIDDLE|LinearLayout.SHOW_DIVIDER_END);

        String header="提醒设置";
        TextView headerRightTextView=new TextView(context);
        headerRightTextView.setText("显示/隐藏");

        HeaderView headerView=new HeaderView(context);
        headerView.setTitle(header).setSubtitle("每日提醒消息设置");//.setThumbnail(DrawableHelper.getVectorDrawable(context,R.drawable.ic_add_circle_black_24dp));
        sectionView.setHeaderView(headerView);

        String[] items=new String[]{"银行账单提醒","工作安排提醒","设置每日工作时间安排;休假、请假、年假、节假日等假日设置提醒;议会、项目启动会等会议提醒;备忘提醒；","其他提醒"};
        String[] details=new String[]{"设置银行每月账单到期还款提醒","设置每日工作时间安排;休假、请假、年假、节假日等假日设置提醒;议会、项目启动会等会议提醒;备忘提醒；","这是比较长的标题",null};

        int thumbnailWidth=0;
        for(int i=0,k=items.length;i<k;i++) {

            TextView rightTextView=new TextView(context);
            rightTextView.setText(" 1.50");
            TextView rightTextView2=new TextView(context);
            rightTextView2.setText(" 20.40");

            GroupListView iconGroup=new GroupListView(context);
            iconGroup.addView(rightTextView);
            iconGroup.addView(rightTextView2);
            iconGroup.setOrientation(LinearLayout.HORIZONTAL);


            ListItemView listItemView=new ListItemView(context);
            Drawable thumbnail=DrawableHelper.getVectorDrawable(context,R.drawable.ic_nav_notice);


            listItemView.setTitle(items[i])
                    .setSubtitle(details[i])
                    .setThumbnail(i==0?thumbnail:null)
                    .setIconType(ListItemView.ACCESSORY_TYPE_CUSTOM).setIconOrientation(ListItemView.MIDDLE).addIconView(iconGroup);
            sectionView.addBodyView(listItemView);
            thumbnailWidth=thumbnail.getIntrinsicWidth();
        }
//        if(thumbnailWidth!=0)
//        {
//            thumbnailWidth+=DisplayHelper.dp2px(context,16);
//        }
//        sectionView.setDividerPaddingLeft(thumbnailWidth);

//        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin=DisplayHelper.dp2px(context,16);
//        int shadowElevation=DisplayHelper.dp2px(context,4);
//        lp.setMargins(margin,margin,margin,margin);
//        sectionView.setLayoutParams(lp);
        sectionView.setRadius(margin);
//        groupListView.setRadiusAndShadow(margin,shadowElevation,0.45f);
//        sectionView.setBorderColor(Color.GRAY).setBorderWidth(DisplayHelper.dp2px(context,2));
        mRootBodyView.addView(sectionView);

    }
}
