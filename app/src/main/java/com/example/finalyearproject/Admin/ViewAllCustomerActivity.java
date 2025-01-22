package com.example.finalyearproject.Admin;

import static android.app.PendingIntent.getActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalyearproject.AdapterGetAllCategoryDetails;
import com.example.finalyearproject.Admin.AdapterClass.AdapterViewALLCustomerDetails;
import com.example.finalyearproject.Admin.POJOClass.POJOViewAllCoustomerDetails;
import com.example.finalyearproject.Common.Urls;
import com.example.finalyearproject.Login;
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

public class ViewAllCustomerActivity extends AppCompatActivity {
    ListView lvShowAllCustomer;
    TextView tvNoCategoryCustomer;
    SearchView searchCustomer;
    ProgressDialog progressDialog;
    List<POJOViewAllCoustomerDetails> pojoViewAllCoustomerDetailsListView;
    AdapterViewALLCustomerDetails adapterViewALLCustomerDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_all_customer);

        lvShowAllCustomer=findViewById(R.id.lvViewAllCustomerShowAllCustomer);
        tvNoCategoryCustomer=findViewById(R.id.tvViewAllCustomerNoCustomer);
        searchCustomer=findViewById(R.id.svViewAllCustomerSerchCustomer);

        pojoViewAllCoustomerDetailsListView = new ArrayList<>();


        progressDialog= new ProgressDialog(ViewAllCustomerActivity.this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Customer List is Loading in Process");
        progressDialog.show();

        viewAllCustomere();

    }

    private void viewAllCustomere() {
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.post(Urls.getAllCustomerrDeatailswebservice,
                params,
                new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        try {
                            progressDialog.dismiss();
                            JSONArray jsonArray = response.getJSONArray("getAllCustomerDetails");
                            if(jsonArray.isNull(0))
                            {
                                tvNoCategoryCustomer.setVisibility(View.VISIBLE);
                            }
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String strid = jsonObject.getString("id");
                                String StrCustomerImage = jsonObject.getString("image");
                                String strCustomerName = jsonObject.getString("name");
                                String strCustomerUserName = jsonObject.getString("username");
                                String StrCustomerEmailId = jsonObject.getString("emailid");
                                String StrCustomerAddress = jsonObject.getString("address");
                                String strCustomerPhoneNo = jsonObject.getString("phone_no");







                                pojoViewAllCoustomerDetailsListView.add(new POJOViewAllCoustomerDetails(strid,StrCustomerImage,strCustomerName,strCustomerUserName,StrCustomerEmailId,StrCustomerAddress,strCustomerPhoneNo));
                            }
                            adapterViewALLCustomerDetails = new AdapterViewALLCustomerDetails(pojoViewAllCoustomerDetailsListView,ViewAllCustomerActivity.this);


                            lvShowAllCustomer.setAdapter(adapterViewALLCustomerDetails);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(ViewAllCustomerActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}