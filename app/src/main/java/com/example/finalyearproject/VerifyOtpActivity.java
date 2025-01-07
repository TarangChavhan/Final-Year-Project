package com.example.finalyearproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalyearproject.Common.Urls;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class VerifyOtpActivity extends AppCompatActivity {

    EditText Etcode1,Etcode2,Etcode3,Etcode4,Etcode5,Etcode6;
    TextView TVnumber,TVresend;
    ProgressDialog progressDialog;
    AppCompatButton verify;
    private String strVerificationCode, strName, strMobileNo,strEmailId,strUsername,strPassWord,strCity,strDistrict,strstate,strAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify_otp);

        Etcode1 = findViewById(R.id.verifyotpno1);
        Etcode2 = findViewById(R.id.verifyotpno2);
        Etcode3 = findViewById(R.id.verifyotpno3);
        Etcode4 = findViewById(R.id.verifyotpno4);
        Etcode5 = findViewById(R.id.verifyotpno5);
        Etcode6 = findViewById(R.id.verifyotpno6);
        verify = findViewById(R.id.verifyotpbtn);


        TVnumber = findViewById(R.id.verifyotpPhoneNumber);
        TVresend = findViewById(R.id.verifyotpResendotp);

        strVerificationCode =getIntent().getStringExtra("verificationCode");
        strName = getIntent().getStringExtra("Name");
        strCity = getIntent().getStringExtra("city");
        strDistrict = getIntent().getStringExtra("distric");
        strstate = getIntent().getStringExtra("state");
        strMobileNo = getIntent().getStringExtra("phone_no");
        strAddress = getIntent().getStringExtra("address");
        strEmailId = getIntent().getStringExtra("emailid");
        strPassWord = getIntent().getStringExtra("password");
        strUsername = getIntent().getStringExtra("username");
        Toast.makeText(this, strName, Toast.LENGTH_SHORT).show();


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(Etcode1.getText().toString().trim().isEmpty() || Etcode2.getText().toString().trim().isEmpty() ||
            Etcode3.getText().toString().trim().isEmpty() || Etcode4.getText().toString().trim().isEmpty() ||
            Etcode5.getText().toString().trim().isEmpty()  || Etcode6.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(VerifyOtpActivity.this,"Please Enter Valid Otp",Toast.LENGTH_SHORT).show();
                }
                String otpcode= Etcode1.getText().toString()+Etcode2.getText().toString()+Etcode3.getText().toString()+
                        Etcode4.getText().toString()+Etcode5.getText().toString()+Etcode6.getText().toString();
                if(strVerificationCode!=null)
                {
                    progressDialog = new ProgressDialog(VerifyOtpActivity.this);
                    progressDialog.setTitle("Verifying OTP");
                    progressDialog.setMessage("Please wait");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                   PhoneAuthCredential phoneAuthCredential=PhoneAuthProvider.getCredential(
                      strVerificationCode,otpcode);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                progressDialog.dismiss();
                                userRegisterDetails();
                            }
                        }
                    });
                }
            }
        });

        TVresend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + strMobileNo,
                        60, TimeUnit.SECONDS,
                        VerifyOtpActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Toast.makeText(VerifyOtpActivity.this,"Verifycation Completed",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(VerifyOtpActivity.this,"Verifycation Failed",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String VerificationCode, @NonNull
                            PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                               strVerificationCode = VerificationCode;
                            }
                        }
                );
            }
        });

        setupInputOtp();




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void userRegisterDetails() {
        AsyncHttpClient client = new AsyncHttpClient();  //client and server Communication
        RequestParams requestParams = new RequestParams();  //put data

        requestParams.put("name",strName);
        requestParams.put("username",strUsername);
        requestParams.put("emailid",strEmailId);
        requestParams.put("password",strPassWord);
        requestParams.put("address",strAddress);
        requestParams.put("phone_no",strMobileNo);
        requestParams.put("state",strstate);
        requestParams.put("distric",strDistrict);
        requestParams.put("city",strCity);

        client.post(Urls.RegisterUserWebService,requestParams,
                new JsonHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        progressDialog.dismiss();

                        try {
                            //below line was wrong, it shoud "String status = response.getString(("sucess")); "
                            String status = response.getString("success");
                            if (status.equals("1"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(VerifyOtpActivity.this,"Registration is Done Sucessfully",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(VerifyOtpActivity.this,Login.class);
                                startActivity(intent);
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(VerifyOtpActivity.this,"Data Already Present",Toast.LENGTH_LONG).show();
                            }



                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        progressDialog.dismiss();
                        Toast.makeText(VerifyOtpActivity.this,"Server Error",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private void setupInputOtp() {
        Etcode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    Etcode2.requestFocus();
                }

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Etcode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    Etcode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Etcode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    Etcode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Etcode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    Etcode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Etcode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    Etcode6.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}