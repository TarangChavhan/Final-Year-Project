package com.example.finalyearproject.Admin.AdapterClass;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.AdapterCategoryWiseEvent;
import com.example.finalyearproject.Admin.POJOClass.POJOViewAllCoustomerDetails;
import com.example.finalyearproject.Common.Urls;
import com.example.finalyearproject.POJOCategoryWiseEvent;
import com.example.finalyearproject.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class AdapterViewALLCustomerDetails extends BaseAdapter {
    List<POJOViewAllCoustomerDetails> pojoViewAllCoustomerDetailsList;
    Activity activity;


    public AdapterViewALLCustomerDetails(List<POJOViewAllCoustomerDetails> pojoViewAllCoustomerDetailsList, Activity activity) {
        this.pojoViewAllCoustomerDetailsList = pojoViewAllCoustomerDetailsList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return pojoViewAllCoustomerDetailsList.size();
    }

    @Override
    public Object getItem(int position) {
        return pojoViewAllCoustomerDetailsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final AdapterViewALLCustomerDetails.ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(view==null)
        {
            holder = new AdapterViewALLCustomerDetails.ViewHolder();
            view = inflater.inflate(R.layout.lv_view_all_customer_details,null);

            holder.ivprofile = view.findViewById(R.id.ALLCuStomerProfilePhoto);
            holder.tvName = view.findViewById(R.id.TVALLCoustomerProfilename);
            holder.tvUsername = view.findViewById(R.id.TVALLCoustomerProfileUserName);
            holder.tvAddress = view.findViewById(R.id.TVALLCoustomerProfileAddress);
            holder.tvEmailid = view.findViewById(R.id.TVALLCoustomerProfileEmail);
            holder.tvMobileNo = view.findViewById(R.id.TVALLCoustomerProfilePhoneNo);
            holder.acbtnDeleteUser = view.findViewById(R.id.BtnALLCusomerDetalisDetelet);
            view.setTag(holder);
        }
        else
        {
            holder = (AdapterViewALLCustomerDetails.ViewHolder) view.getTag();
        }
        final POJOViewAllCoustomerDetails obj = pojoViewAllCoustomerDetailsList.get(position);
        holder.tvName.setText(obj.getName());
        holder.tvMobileNo.setText(obj.getPhoneno());
        holder.tvEmailid.setText(obj.getEmailid());
        holder.tvAddress.setText(obj.getAddress());
        holder.tvUsername.setText(obj.getUsername());

        Glide.with(activity)
                .load(Urls.getAllCategoryImages +obj.getImage())
                .skipMemoryCache(true)
                .error(R.drawable.noimg)
                .into(holder.ivprofile);
        holder.acbtnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(activity);
                ad.setTitle("Delete User");
                ad.setMessage("Are You Sure You Want to Delete User ?");
                ad.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                ad.setNegativeButton("Delete User", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DeleteUser(obj.getUsername(),position);
                    }
                }).create().show();
            }
        });
        return view;
    }
    private void DeleteUser(String username, int position) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",username);
        client.post(Urls.deleteUserWebService,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1"))
                    {
                        pojoViewAllCoustomerDetailsList.remove(position);
                        notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(activity, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    class ViewHolder{
        AppCompatButton acbtnDeleteUser;
        ImageView ivprofile;
        TextView tvName,tvMobileNo,tvUsername,tvAddress,tvEmailid;
    }
}