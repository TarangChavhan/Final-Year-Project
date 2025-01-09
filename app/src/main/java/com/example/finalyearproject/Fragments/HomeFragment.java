package com.example.finalyearproject.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalyearproject.AdapterGetAllCategoryDetails;
import com.example.finalyearproject.Common.Urls;
import com.example.finalyearproject.POJOGetAllCategoryDetails;
import com.example.finalyearproject.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class HomeFragment extends Fragment {
    ListView lvShowAllCategory;
    TextView tvNoCategoryAvilable;
    List<POJOGetAllCategoryDetails> pojoGetAllCategoryDetails;
    AdapterGetAllCategoryDetails adapterGetAllCategoryDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        pojoGetAllCategoryDetails = new ArrayList<>();
        lvShowAllCategory=view.findViewById(R.id.lvFragmentHomeListview);
        tvNoCategoryAvilable=view.findViewById(R.id.tvFragmentHomeNoCategoryAvliable);

        getAllCategory();

        return view;
    }
    private void getAllCategory() {
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.post(Urls.AllCategoryDetails,
                params,
                new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        try {
                            JSONArray jsonArray = response.getJSONArray("getAllCategory");
                            if(jsonArray.isNull(0))
                            {
                                tvNoCategoryAvilable.setVisibility(View.VISIBLE);
                            }
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String strid = jsonObject.getString("id");
                                String strCategoryImage = jsonObject.getString("category_image");
                                String strCategoryName = jsonObject.getString("category_name");
                                pojoGetAllCategoryDetails.add(new POJOGetAllCategoryDetails(strid,strCategoryImage,strCategoryName));
                            }
                            adapterGetAllCategoryDetails = new AdapterGetAllCategoryDetails(pojoGetAllCategoryDetails,getActivity());


                            lvShowAllCategory.setAdapter(adapterGetAllCategoryDetails);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }
                );
    }
}