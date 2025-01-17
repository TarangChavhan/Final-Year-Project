package com.example.finalyearproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.Common.Urls;

import java.util.List;

public class AdapterCategoryWiseEvent extends BaseAdapter {
    List<POJOCategoryWiseEvent> pojoCategoryWiseEventList;
    Activity activity;

    public AdapterCategoryWiseEvent(List<POJOCategoryWiseEvent> list, Activity activity) {
        this.pojoCategoryWiseEventList = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return pojoCategoryWiseEventList.size();
    }

    @Override
    public Object getItem(int position) {

        return pojoCategoryWiseEventList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final Viewholder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(view==null)
        {
            holder = new Viewholder();
            view = inflater.inflate(R.layout.lv_categorywise_event,null);

            holder.ivCategoryWiseImage = view.findViewById(R.id.ivCategoryWiseEventImage);
            holder.tvCategorywiseCompanyName = view.findViewById(R.id.ivCategoryWiseCompanyName);
            holder.tvCategorywiseRating = view.findViewById(R.id.ivCategoryWiseEventRating);
            holder.tvCategorywiseCompanyAddress = view.findViewById(R.id.ivCategoryWiseEventAddress);
            holder.tvCategorywiseBudget = view.findViewById(R.id.ivCategoryWiseCompanyBudget);
            holder.tvCategorywiseeventOffer = view.findViewById(R.id.ivCategoryWiseEventOffer);
            holder.tvCategorywiseEventDescription = view.findViewById(R.id.ivCategoryWiseEventDesscription);
            holder.TvCategorywiseCategoryName = view.findViewById(R.id.ivCategoryWiseCategoryName);
            view.setTag(holder);
        }
        else
        {
            holder = (Viewholder) view.getTag();
        }
        final POJOCategoryWiseEvent obj = pojoCategoryWiseEventList.get(position);
        holder.tvCategorywiseCompanyName.setText(obj.companyname);
        holder.tvCategorywiseRating.setText(obj.evenrating);
        holder.tvCategorywiseCompanyAddress.setText(obj.companyaddress);
        holder.tvCategorywiseBudget.setText(obj.budget);
        holder.tvCategorywiseeventOffer.setText(obj.eventoffer);
        holder.tvCategorywiseEventDescription.setText(obj.eventdescription);
        holder.TvCategorywiseCategoryName.setText(obj.categoryname);

        Glide.with(activity)
                .load(Urls.getAllCategoryImages +obj.eventimage)
                .skipMemoryCache(true)
                .error(R.drawable.noimg)
                .into(holder.ivCategoryWiseImage);



        return view;
    }
    class Viewholder
    {
        ImageView ivCategoryWiseImage;
        TextView tvCategorywiseCompanyName,tvCategorywiseRating,tvCategorywiseCompanyAddress,tvCategorywiseBudget,tvCategorywiseeventOffer,tvCategorywiseEventDescription,TvCategorywiseCategoryName;

    }

}
