package com.stage1.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.stage1.Models.ResponseLogin;
import com.stage1.Network.ApiClient;
import com.stage1.Network.ApiInterface;
import com.stage1.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AppCompatActivity {
    EditText et_email,et_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
       /* AlertDialog.Builder builder = new AlertDialog.Builder(AuthActivity.this);
        builder.setPositiveButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setNegativeButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setView(((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.auth_signin, null));
        builder.setCancelable(true);
        final AlertDialog alertDialog = builder.create();
        (alertDialog).getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.round_cornor_background));*/
        ((Button)findViewById(R.id.btn_signup)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AuthActivity.this,SignUpActivity.class));
            }
        });
        ((Button)findViewById(R.id.btn_login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
//                startActivity(new Intent(AuthActivity.this,MainActivity.class));
            }
        });
    }

    private void signIn() {
        if (et_email.getText().toString().trim().length()==0)
        {
            et_email.setError("Please enter email id");
            return;
        }
        if(et_password.getText().toString().trim().length()==0)
        {
            et_password.setError("Please enter password");
            return;
        }
        if (et_email.getText().toString().trim().length()>0&&et_password.getText().toString().trim().length()>0)
        {
            loginUser(et_email.getText().toString().trim(),et_password.getText().toString().trim());
        }
    }

    private void loginUser(String email, String password) {
        String deviceID="asdasdasd",lat="34232",lng="3423433";
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseLogin> responseLoginCall = apiInterface.login(email,password,deviceID,lat,lng);
        responseLoginCall.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {

            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {

            }
        });
    }
}
