package com.example.finalyearproject;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalyearproject.Common.Urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CategoryWiseEventActivity extends AppCompatActivity {

    SearchView searchCategorywiseEvent;
    ListView lvCategoryWiseEvent;
    TextView tvNoEventAvailable;
    String strCategoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_wise_event);

        searchCategorywiseEvent = findViewById(R.id.svCategorywiseEventSearch);
        lvCategoryWiseEvent = findViewById(R.id.lvCategorywiseEventlist);
        tvNoEventAvailable = findViewById(R.id.tvCateWiseEventNoEventAvilable);
        strCategoryName = getIntent().getStringExtra("categoryname");

        getCategoryWiseEventList();




    }

    private void getCategoryWiseEventList() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("categoryname",strCategoryName);

        client.post(Urls.getAllCategoryWiseEvents,
                params,
                new JsonHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            JSONArray jsonArray = response.getJSONArray("getCategoryWiseEvent");
                            if (jsonArray==null)
                            {
                                lvCategoryWiseEvent.setVisibility(View.GONE);
                                tvNoEventAvailable.setText(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(CategoryWiseEventActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}