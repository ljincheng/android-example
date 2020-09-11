package cn.booktable.uikit.ui.adaptor;

import android.view.ViewGroup;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import cn.booktable.uikit.ui.widget.GalleryView;
import cn.booktable.uikit.ui.widget.GridIconView;
import cn.booktable.uikit.ui.widget.GridView;
import cn.booktable.uikit.ui.widget.GroupListView;
import cn.booktable.uikit.ui.widget.HeaderView;
import cn.booktable.uikit.ui.widget.IconView;
import cn.booktable.uikit.ui.widget.ListItemView;
import cn.booktable.uikit.ui.widget.RadiusImageView;
import cn.booktable.uikit.ui.widget.VideoPlayerView;

public class AdaptorViewManage {


    public static SimpleView createViewType(ViewGroup viewGroup,int viewType){

        switch (viewType)
        {
            case 1:{
                IconView iconView=new IconView(viewGroup.getContext());
                return iconView;
            }
            case 2:{
                GridView gridView=new GridView(viewGroup.getContext());
                return gridView;
            }
            case 3:{
                RadiusImageView imageView=new RadiusImageView(viewGroup.getContext());
                return imageView;
            }
            case 4:{
                GalleryView galleryView=new GalleryView(viewGroup.getContext());
                return galleryView;
            }
            case 5:{
                ListItemView imageView=new ListItemView(viewGroup.getContext());
                return imageView;
            }
            case 6:{
            GroupListView groupListView=new GroupListView(viewGroup.getContext());
                return groupListView;
            }
            case 7:{
                HeaderView headerView=new HeaderView(viewGroup.getContext());
                return headerView;
            }
            case 8:{
                GridIconView gridIconView=new GridIconView(viewGroup.getContext());
                return gridIconView;
            }
            case 9:{
            VideoPlayerView videoPlayerView=new VideoPlayerView(viewGroup.getContext());
            return videoPlayerView;
            }
        }
        return null;

    }
}
