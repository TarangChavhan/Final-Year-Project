package com.example.finalyearproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterGetAllCategoryDetails extends BaseAdapter {

    List<POJOGetAllCategoryDetails> pojoGetAllCategoryDetailsList;
    Activity activity;

    public AdapterGetAllCategoryDetails(List<POJOGetAllCategoryDetails> list, Activity activity) {
        this.pojoGetAllCategoryDetailsList = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return pojoGetAllCategoryDetailsList.size();
    }

    @Override
    public Object getItem(int position) {

        return pojoGetAllCategoryDetailsList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final ViewHoplder hoplder;
        LayoutInflater inflater =(LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(view==null)
        {
            hoplder = new ViewHoplder();
            view =inflater.inflate(R.layout.lv_get_all_category,null);
            hoplder.ivCategoryImage = view.findViewById(R.id.ivgetAllCategoryImageCategory);
            hoplder.tvcategoryName = view.findViewById(R.id.tvgetAllCategoryTvCategoryName);

            view.setTag(hoplder);
        }
        else {
            hoplder = (ViewHoplder) view.getTag();

        }
        final POJOGetAllCategoryDetails obj = pojoGetAllCategoryDetailsList.get(position);
        hoplder.tvcategoryName.setText(obj.getCategoryName());


        Glide.with(activity)
                .load("http://192.168.10.13:80/mobileapppAPI/images/"+obj.categoryImage)
                .skipMemoryCache(true)
                .error(R.drawable.noimg)
                .into(hoplder.ivCategoryImage);

        return view;
    }
    class ViewHoplder
    {
        ImageView ivCategoryImage;
        TextView tvcategoryName;
    }
}
