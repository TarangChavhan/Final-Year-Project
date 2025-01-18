package com.example.finalyearproject.Admin;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalyearproject.Admin.AdapterClass.AdapterGetAllCategoryDetailsRV;
import com.example.finalyearproject.Common.Urls;
import com.example.finalyearproject.POJOGetAllCategoryDetails;
import com.example.finalyearproject.R;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeActivity extends AppCompatActivity {
    RecyclerView rvrecyclerView;
    List<POJOGetAllCategoryDetails> pojoGetAllCategoryDetails;
    AdapterGetAllCategoryDetailsRV adapterGetAllCategoryDetailsRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_home);

        rvrecyclerView = findViewById(R.id.rvadminHomeListview);
        rvrecyclerView.setLayoutManager(new GridLayoutManager(AdminHomeActivity.this,2,GridLayoutManager.HORIZONTAL,false));



        pojoGetAllCategoryDetails = new ArrayList<>();
        adapterGetAllCategoryDetailsRV = new AdapterGetAllCategoryDetailsRV(pojoGetAllCategoryDetails,this);
        rvrecyclerView.setAdapter(adapterGetAllCategoryDetailsRV);

        getAllCategoryRV();


    }

    private void getAllCategoryRV() {
        RequestQueue requestQueue = Volley.newRequestQueue(AdminHomeActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Urls.AllCategoryDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("getAllCategory");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String strid = jsonObject1.getString("id");
                        String strCategory = jsonObject1.getString("category_name");
                        String strCategoryImage = jsonObject1.getString("category_image");

                        pojoGetAllCategoryDetails.add(new POJOGetAllCategoryDetails(strid,strCategoryImage,strCategory));
                    }
                    adapterGetAllCategoryDetailsRV.notifyDataSetChanged();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AdminHomeActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
}