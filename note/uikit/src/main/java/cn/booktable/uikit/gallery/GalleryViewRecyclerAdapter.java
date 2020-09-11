package cn.booktable.uikit.gallery;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import cn.booktable.uikit.ui.widget.RadiusImageView;


public class GalleryViewRecyclerAdapter extends RecyclerView.Adapter<GalleryViewRecyclerAdapter.NormalHolder> {

    private GalleryViewBase.OnBannerItemClickListener onBannerItemClickListener;
    private Context context;
    private List<String> urlList;

    public GalleryViewRecyclerAdapter(Context context, List<String> urlList, GalleryViewBase.OnBannerItemClickListener onBannerItemClickListener) {
        this.context = context;
        if(urlList==null)
        {
            this.urlList = new ArrayList<>();
        }else {
            this.urlList = urlList;
        }
        this.onBannerItemClickListener = onBannerItemClickListener;
    }

    public void setDataList( List<String> urls)
    {
        if(urls!=null && urls.size()>0) {
            this.urlList = urls;
        }
    }

    @Override
    public GalleryViewRecyclerAdapter.NormalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RadiusImageView imageView=new RadiusImageView(context);
        return new NormalHolder(imageView);
//        return new NormalHolder(new ImageView(context));
    }

    @Override
    public void onBindViewHolder(NormalHolder holder, final int position) {
        if (urlList == null || urlList.isEmpty())
            return;
        String url = urlList.get(position % urlList.size());
        RadiusImageView img = (RadiusImageView) holder.itemView;
//        Glide.with(context).load(url).into(img);
        img.setSrc(url);
//        img.setU
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBannerItemClickListener != null) {
                    onBannerItemClickListener.onItemClick(position % urlList.size());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    class NormalHolder extends RecyclerView.ViewHolder {
        RadiusImageView bannerItem;

        NormalHolder(View itemView) {
            super(itemView);
            bannerItem = (RadiusImageView) itemView;
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            bannerItem.setLayoutParams(params);
            bannerItem.setScaleType(ImageView.ScaleType.FIT_XY);
//            bannerItem.setRadius(20);

        }
    }

}