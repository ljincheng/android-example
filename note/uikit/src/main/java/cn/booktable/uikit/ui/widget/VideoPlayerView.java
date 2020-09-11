package cn.booktable.uikit.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONObject;

import cn.booktable.uikit.R;
import cn.booktable.uikit.ui.adaptor.SimpleView;
import cn.booktable.uikit.ui.attr.ViewAttrHelper;
import cn.booktable.uikit.util.DisplayHelper;
import cn.studypartner.edu.mediaplayer.widget.media.AndroidMediaController;
import cn.studypartner.edu.mediaplayer.widget.media.IjkVideoView;

public class VideoPlayerView extends IjkVideoView  implements SimpleView {

    private AndroidMediaController mMediaController;

    public VideoPlayerView(Context context) {
        this(context,null,0);
    }

    public VideoPlayerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VideoPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LinearLayout.LayoutParams rootLp = ViewAttrHelper.newLinearLayoutParams(ViewAttrHelper.MATCH_WRAP);
        rootLp.height= DisplayHelper.dp2px(context,200);
        setLayoutParams(rootLp);
        initCom();
    }

    private void initCom()
    {
        mMediaController = new AndroidMediaController(getContext(), false);
        setMediaController(mMediaController);
    }

    public  void setVideo(String video)
    {
        if(video!=null && video.length()>0)
        {
            setVideoPath(video);
            setAspectRatio(1);
            start();
        }

    }

    @Override
    public void setViewData(JSONObject data) {
        ViewAttrHelper.encodeProperties(getContext(),this,data);
        if(data!=null)
        {
            String src=data.getString("videoPath");
            setVideo(src);
        }
    }

    @Override
    public View getView() {
        return this;
    }


}
