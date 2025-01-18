package com.example.finalyearproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalyearproject.Admin.AdminHomeActivity;
import com.example.finalyearproject.Common.NetworkChangeListner;
import com.example.finalyearproject.Common.Urls;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
public class Login extends AppCompatActivity {
    EditText ETUserename, ETPassword;
    AppCompatButton BTLogin,btnGoogle;
    TextView TVNewUser,TVForgetPassword;
    CheckBox CBSHow;
    ProgressDialog progressDialog;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    NetworkChangeListner networkChangeListner = new NetworkChangeListner();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
        editor = sharedPreferences.edit();

        ETUserename = findViewById(R.id.ETloginUsername);
        ETPassword = findViewById(R.id.ETloginpassword);
        BTLogin = findViewById(R.id.ButtonLogin);
        TVNewUser = findViewById(R.id.TVNewUser);
        TVForgetPassword=findViewById(R.id.TVForgotPassword);
        CBSHow = findViewById(R.id.CBpassword);

        btnGoogle = findViewById(R.id.btnGooglelogin);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(Login.this,googleSignInOptions);

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SingIN();
            }
        });
        TVForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this, ConfirmRegisterMobileNoActivity.class);
                startActivity(intent);
            }
        });


        CBSHow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(CBSHow.isChecked())
                {
                    ETPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    ETPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        BTLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ETUserename.getText().toString().isEmpty())
                {
                    ETUserename.setError("UserName is Required");
                }
                else if(ETPassword.getText().toString().isEmpty())
                {
                    ETPassword.setError("Password is Required");
                }
                else if (ETUserename.getText().toString().length()<8)
                {
                    ETUserename.setError("At least 8 Character is required ");
                }
                else if (ETPassword.getText().toString().length()<8)
                {
                    ETPassword.setError("Password should 8 character long");
                }
                else if(!ETUserename.getText().toString().matches("^(?=.*[A-Z]).+$"))
                {
                    ETUserename.setError("one Uppercase is required");
                }
                else if(!ETUserename.getText().toString().matches("^(?=.*[a-z]).+$"))
                {
                    ETUserename.setError("one Lowercase is required");
                }
                else if(!ETUserename.getText().toString().matches("^(?=.*[0-9]).+$"))
                {
                    ETUserename.setError("one number is required");
                }
                else if(!ETUserename.getText().toString().matches("^(?=.*[@,#,$,%,!,_]).+$"))
                {
                    ETUserename.setError("one Special Symbol is required");
                }
                else
                {
                    progressDialog= new ProgressDialog(Login.this);
                    progressDialog.setTitle("Please wait...");
                    progressDialog.setMessage("Login is in processing");
                    progressDialog.show();

                    userLogin();
                }
            }
        });
        TVNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void SingIN() {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent,999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==999)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                Intent intent = new Intent(Login.this,HomeActivity.class);
                startActivity(intent);
                finish();
            } catch (ApiException e) {
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter= new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListner,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeListner);
    }

    private void userLogin() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params=new RequestParams();

        params.put("username",ETUserename.getText().toString());
        params.put("password",ETPassword.getText().toString());

        client.post(Urls.loginUserWebService,params, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progressDialog.dismiss();
                try {
                    String status = response.getString("success");
                    String struserrole = response.getString("UserRole");
                    if(status.equals("1") && struserrole.equals("user"))
                    {
                        progressDialog.dismiss();
                        Intent intent= new Intent(Login.this,HomeActivity.class);
                        editor.putString("username",ETUserename.getText().toString()).commit();
                        startActivity(intent);
                        finish();
                    } else if (status.equals("1") && struserrole.equals("admin")) {
                        progressDialog.dismiss();
                        Intent intent= new Intent(Login.this, AdminHomeActivity.class);
                        startActivity(intent);
                        finish();
                        
                    } else
                    {
                        Toast.makeText(Login.this,"Invalid Username Or password",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
                Toast.makeText(Login.this,"Server Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
}