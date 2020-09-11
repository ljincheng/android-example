package cn.booktable.uikit.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Placeholder;

import com.alibaba.fastjson.JSONObject;

import cn.booktable.uikit.R;
import cn.booktable.uikit.ui.adaptor.SimpleView;
import cn.booktable.uikit.ui.attr.ViewAttrHelper;
import cn.booktable.uikit.util.ResHelper;
import cn.booktable.uikit.util.StringHelper;

public class HeaderView extends ConstraintLayout implements SimpleView,View.OnClickListener {
    /**
     * detailText 在 title 文字的下方
     */
    public final static int VERTICAL = 0;
    /**
     * detailText 在 item 的右方
     */
    public final static int HORIZONTAL = 1;


    public final static int TOP = 0;
    public final static int MIDDLE = 1;
    public final static int BOTTOM = 2;
    /**
     * TIP 在左边
     */
    public final static int TIP_POSITION_LEFT = 0;
    /**
     * TIP 在右边
     */
    public final static int TIP_POSITION_RIGHT = 1;

    /**
     * 右侧不显示任何东西
     */
    public final static int ACCESSORY_TYPE_NONE = 0;
    /**
     * 右侧显示一个箭头
     */
    public final static int ACCESSORY_TYPE_CHEVRON = 1;
    /**
     * 右侧显示一个开关
     */
    public final static int ACCESSORY_TYPE_SWITCH = 2;
    /**
     * 自定义右侧显示的 View
     */
    public final static int ACCESSORY_TYPE_CUSTOM = 3;


    private int mTipPosition = TIP_POSITION_LEFT;
    /**
     * 控制 detailText 是在 title 文字的下方还是 item 的右方
     */
    private int mOrientation = HORIZONTAL;
    private int mAccessoryType;

    protected ImageView mThumbnail;
    private ViewGroup mAccessoryView;
    protected TextView mTitle;
    protected TextView mSubtitle;
    protected CheckBox mSwitch;
    private ImageView mRedDot;
    private ImageView mNewTipView;
    private Placeholder mAfterTitleHolder;
    private Placeholder mBeforeAccessoryHolder;
    private boolean mDisableSwitchSelf = false;
    private boolean mShowArrow=true;
    private JSONObject mViewData;

    public HeaderView(Context context) {
        this(context,null);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
       // setThumbnailOrientation(MIDDLE);
    }

    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.layout_header_view, this, true);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CommonListItemView, defStyleAttr, 0);

        int orientation = array.getInt(R.styleable.CommonListItemView_uikit_orientation, VERTICAL);
        int accessoryType = array.getInt(R.styleable.CommonListItemView_uikit_accessory_type, ACCESSORY_TYPE_NONE);

        mThumbnail = findViewById(R.id.imageview_thumbnail);
        mTitle = findViewById(R.id.textview_title);
        mRedDot = findViewById(R.id.imageview_tips_dot);
        mNewTipView = findViewById(R.id.imageview_tips_new);
        mSubtitle = findViewById(R.id.textview_subtitle);
        mAfterTitleHolder = findViewById(R.id.holder_after_title);
        mBeforeAccessoryHolder = findViewById(R.id.holder_before_accessory);

        mAfterTitleHolder.setEmptyVisibility(View.GONE);
        mBeforeAccessoryHolder.setEmptyVisibility(View.GONE);
        mAccessoryView = findViewById(R.id.group_list_item_accessoryView);

        setOrientation(orientation);
        setIconType(accessoryType);
    }

    public HeaderView setThumbnail(Drawable drawable) {
        if (drawable == null) {
            mThumbnail.setVisibility(View.GONE);
        } else {
            mThumbnail.setImageDrawable(drawable);
            mThumbnail.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public HeaderView setThumbnailOrientation(int orientation) {
        LayoutParams imageLp = (LayoutParams) mThumbnail.getLayoutParams();
        if (orientation == TOP) {
            imageLp.bottomToBottom = LayoutParams.UNSET;
            imageLp.topToTop = LayoutParams.PARENT_ID;
        } else {
            imageLp.bottomToBottom = LayoutParams.PARENT_ID;
            imageLp.topToTop = LayoutParams.PARENT_ID;
        }
        return this;
    }

    public HeaderView setTitle(CharSequence text)
    {
        mTitle.setText(text);
        if(StringHelper.isEmpty(text))
        {
            mTitle.setVisibility(View.GONE);
        }else{
            mTitle.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public CharSequence getTitle()
    {
        return mTitle.getText();
    }

    public HeaderView setSubtitle(CharSequence text)
    {
        mSubtitle.setText(text);
        if(StringHelper.isEmpty(text))
        {
            mSubtitle.setVisibility(View.GONE);
        }else{
            mSubtitle.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public CharSequence getSubtitle()
    {
        return mSubtitle.getText();
    }



    public HeaderView setTipPosition( int tipPosition) {
        mTipPosition = tipPosition;
        if (mRedDot.getVisibility() == View.VISIBLE) {
            if (mTipPosition == TIP_POSITION_LEFT) {
                mAfterTitleHolder.setContentId(mRedDot.getId());
                mBeforeAccessoryHolder.setContentId(View.NO_ID);
            } else {
                mBeforeAccessoryHolder.setContentId(mRedDot.getId());
                mAfterTitleHolder.setContentId(View.NO_ID);
            }
            mNewTipView.setVisibility(View.GONE);
        } else if (mNewTipView.getVisibility() == View.VISIBLE) {
            if (mTipPosition == TIP_POSITION_LEFT) {
                mAfterTitleHolder.setContentId(mNewTipView.getId());
                mBeforeAccessoryHolder.setContentId(View.NO_ID);
            } else {
                mBeforeAccessoryHolder.setContentId(mNewTipView.getId());
                mAfterTitleHolder.setContentId(View.NO_ID);
            }
            mRedDot.setVisibility(View.GONE);
        }
        // checkDetailLeftMargin();
        return this;
    }

    public int getOrientation() {
        return mOrientation;
    }

    public HeaderView setOrientation(int orientation) {
        if (mOrientation == orientation) {
            return this;
        }
        mOrientation = orientation;

        LayoutParams titleLp = (LayoutParams) mTitle.getLayoutParams();
        LayoutParams detailLp = (LayoutParams) mSubtitle.getLayoutParams();
        if (orientation == VERTICAL) {

            titleLp.horizontalChainStyle = LayoutParams.UNSET;
            titleLp.verticalChainStyle = LayoutParams.CHAIN_PACKED;
            titleLp.bottomToBottom = LayoutParams.UNSET;
            titleLp.bottomToTop = mSubtitle.getId();

            detailLp.horizontalChainStyle = LayoutParams.UNSET;
            detailLp.verticalChainStyle = LayoutParams.CHAIN_PACKED;
            detailLp.leftToRight = LayoutParams.UNSET;
            detailLp.leftToLeft = mTitle.getId();
            detailLp.horizontalBias = 0f;
            detailLp.topToTop = LayoutParams.UNSET;
            detailLp.topToBottom = mTitle.getId();
            detailLp.leftMargin = 0;

        } else {

            titleLp.horizontalChainStyle = LayoutParams.CHAIN_SPREAD_INSIDE;
            titleLp.verticalChainStyle = LayoutParams.UNSET;
            titleLp.bottomToBottom = LayoutParams.PARENT_ID;
            titleLp.bottomToTop = LayoutParams.UNSET;

            detailLp.horizontalChainStyle = LayoutParams.CHAIN_SPREAD_INSIDE;
            detailLp.verticalChainStyle = LayoutParams.UNSET;
            detailLp.leftToRight = mTitle.getId();
            detailLp.leftToLeft = LayoutParams.UNSET;
            detailLp.horizontalBias = 0f;
            detailLp.topToTop = LayoutParams.PARENT_ID;
            detailLp.topToBottom = LayoutParams.UNSET;
            detailLp.topMargin = 0;
        }
        return this;
    }

    public HeaderView setIconType(int type) {
        mAccessoryView.removeAllViews();
        mAccessoryType = type;

        switch (type) {
            // 向右的箭头
            case ACCESSORY_TYPE_CHEVRON: {
                ImageView tempImageView = getAccessoryImageView();
                tempImageView.setImageDrawable(ResHelper.getAttrDrawable(getContext(), R.attr.common_list_item_arrow));
                mAccessoryView.addView(tempImageView);
                mAccessoryView.setVisibility(VISIBLE);
            }
            break;
            // switch开关
            case ACCESSORY_TYPE_SWITCH: {
                if (mSwitch == null) {
                    mSwitch = new AppCompatCheckBox(getContext());
                    mSwitch.setBackground(null);
                    mSwitch.setButtonDrawable(ResHelper.getAttrDrawable(getContext(), R.attr.common_list_item_switch));
                    mSwitch.setLayoutParams(getAccessoryLayoutParams());
                    if(mDisableSwitchSelf){
                        mSwitch.setClickable(false);
                        mSwitch.setEnabled(false);
                    }
                }
                mAccessoryView.addView(mSwitch);
                mAccessoryView.setVisibility(VISIBLE);
            }
            break;
            // 自定义View
            case ACCESSORY_TYPE_CUSTOM:
                mAccessoryView.setVisibility(VISIBLE);
                break;
            // 清空所有accessoryView
            case ACCESSORY_TYPE_NONE:
                mAccessoryView.setVisibility(GONE);
                break;
        }
        LayoutParams titleLp = (LayoutParams) mTitle.getLayoutParams();
        LayoutParams detailLp = (LayoutParams) mSubtitle.getLayoutParams();
        if (mAccessoryView.getVisibility() != View.GONE) {
            detailLp.goneRightMargin = detailLp.rightMargin;
            titleLp.goneRightMargin = titleLp.rightMargin;
        } else {
            detailLp.goneRightMargin = 0;
            titleLp.goneRightMargin = 0;
        }
        return this;
    }

    public HeaderView addIconView(View view)
    {
        if(mAccessoryType == ACCESSORY_TYPE_CUSTOM)
        {
            mAccessoryView.addView(view);
        }
        return this;
    }

    private ViewGroup.LayoutParams getAccessoryLayoutParams() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private ImageView getAccessoryImageView() {
        AppCompatImageView resultImageView = new AppCompatImageView(getContext());
        resultImageView.setLayoutParams(getAccessoryLayoutParams());
        resultImageView.setScaleType(ImageView.ScaleType.CENTER);
        return resultImageView;
    }

    public void addTo(ViewGroup viewGroup)
    {
        viewGroup.addView(this);
    }


    @Override
    public void onClick(View event)
    {
        if(mViewData!=null && mViewData.containsKey(WidgetContext.EVENT_KEY)) {
            WidgetContext.onClickEvent(this.getContext(),this,mViewData);
        }
    }

    @Override
    public void setViewData(JSONObject data) {
        ViewGroup.LayoutParams lp=ViewAttrHelper.newLinearLayoutParams(ViewAttrHelper.MATCH_WRAP);
        setLayoutParams(lp);
        ViewAttrHelper.encodeProperties(getContext(),this,data);
        if(data!=null)
        {
            if(data.containsKey("title"))
            {
                setTitle(data.getString("title"));
            }
            if(data.containsKey("subtitle"))
            {
                setSubtitle(data.getString("subtitle"));
            }
            if(data.containsKey("showMore") )
            {
                Boolean showMore=data.getBoolean("showMore");
                if(showMore!=null && showMore.booleanValue())
                {
                    TextView moreView=new TextView(this.getContext());
                    moreView.setLayoutParams(ViewAttrHelper.newLinearLayoutParams(ViewAttrHelper.WRAP_WRAP));
                    moreView.setText("更多");
                    moreView.setOnClickListener(this);
                    setIconType(ACCESSORY_TYPE_CUSTOM);
                    addIconView(moreView);
                }

            }
            mViewData=data;
        }

    }

    @Override
    public View getView() {
        return this;
    }
}
