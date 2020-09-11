package cn.booktable.note.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.regex.Pattern;

import cn.booktable.note.R;
import cn.booktable.note.ui.viewmodel.WidgetDo;
import cn.booktable.note.ui.viewmodel.WidgetModel;
import cn.booktable.uikit.ui.adaptor.SimpleRecyclerAdaptor;
import cn.booktable.uikit.ui.adaptor.SimpleViewData;
import cn.booktable.uikit.ui.attr.ViewAttrHelper;
import cn.booktable.uikit.ui.widget.GalleryView;
import cn.booktable.uikit.ui.widget.GridView;
import cn.booktable.uikit.ui.widget.RadiusImageView;
import cn.booktable.uikit.util.StringHelper;

public class FragmentTest extends BaseFragment {

    public static String TAG_HOME = "NAV_CLOUD";

    @Override
    public String getTagName() {
        return TAG_HOME;
    }

    RecyclerView mLayoutRoot;
    protected SimpleRecyclerAdaptor mAdaptor=null;
    private SimpleViewData mSimpleViewData=new SimpleViewData();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // View root= super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_test, container, false);
        if (savedInstanceState == null) {


        }
        mLayoutRoot=root.findViewById(R.id.layout_root);
        LinearLayoutManager mPagerLayoutManager = new LinearLayoutManager(getContext());
        mLayoutRoot.setLayoutManager(mPagerLayoutManager);
        mAdaptor=new SimpleRecyclerAdaptor(mSimpleViewData);
        mLayoutRoot.setAdapter(mAdaptor);
        return root;

    }

    @Override
    public void onNavigationItemSelected() {
        super.onNavigationItemSelected();
    }

    @Override
    public void onStart() {
        super.onStart();

        WidgetModel widgetModel=new ViewModelProvider(requireActivity()).get(WidgetModel.class);

        widgetModel.getWidgets().observe(getActivity(), new Observer<List<WidgetDo>>() {
            @Override
            public void onChanged(List<WidgetDo> widgetDos) {
                try{
                    //mSimpleViewData.clear();
                    boolean isNoticeFull=false;
                    if(mSimpleViewData.size()==0)
                    {
                        isNoticeFull=true;
                    }else if(widgetDos.size()!=mSimpleViewData.size())
                    {
                        isNoticeFull=true;
                        mSimpleViewData.clear();
                    }
                    for(int i=0,k=widgetDos.size();i<k;i++) {
                        WidgetDo widgetDo=widgetDos.get(i);
                        if(widgetDo.getSection()!=null && widgetDo.getSection().intValue()==1) {
                            String dataStr= widgetDo.getData();
                            if(StringHelper.isNotBlank(dataStr))
                            {
                                JSONObject jsonObject=JSONObject.parseObject(dataStr);
                                boolean isNew=mSimpleViewData.append(jsonObject,i);
                                if (isNew && !isNoticeFull) {
                                    mAdaptor.notifyItemChanged(i);
                                }
                            }
                        }
                    }
                    if(widgetDos!=null && widgetDos.size()>0 && isNoticeFull) {
                        mAdaptor.notifyDataSetChanged();
                    }

                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
    }




}
