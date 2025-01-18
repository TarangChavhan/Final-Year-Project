package com.example.finalyearproject.Admin.AdapterClass;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.Common.Urls;
import com.example.finalyearproject.POJOGetAllCategoryDetails;
import com.example.finalyearproject.R;

import java.util.List;

public class AdapterGetAllCategoryDetailsRV extends RecyclerView.Adapter<AdapterGetAllCategoryDetailsRV.ViewHolder> {
    List<POJOGetAllCategoryDetails> pojoGetAllCategoryDetails;
    Activity activity;

    public AdapterGetAllCategoryDetailsRV(List<POJOGetAllCategoryDetails> pojoGetAllCategoryDetails, Activity activity) {
        this.pojoGetAllCategoryDetails = pojoGetAllCategoryDetails;
        this.activity = activity;
    }

    @Override
    public AdapterGetAllCategoryDetailsRV.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.lv_get_all_category,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGetAllCategoryDetailsRV.ViewHolder holder, int position) {
        POJOGetAllCategoryDetails obj= pojoGetAllCategoryDetails.get(position);
        holder.tvCategoryname.setText(obj.getCategoryName());

        Glide.with(activity).load(Urls.getAllCategoryImages+obj.getCategoryImage())
                .skipMemoryCache(false)
                .error(R.drawable.noimg)
                .into(holder.ivCategoryImage);
    }

    @Override
    public int getItemCount() {
        return pojoGetAllCategoryDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCategoryImage;
        TextView tvCategoryname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivCategoryImage = itemView.findViewById(R.id.ivgetAllCategoryImageCategory);
            tvCategoryname = itemView.findViewById(R.id.tvgetAllCategoryTvCategoryName);
        }
    }
}
