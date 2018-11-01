package com.stage1.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stage1.Models.ResponseLogin;
import com.stage1.Network.ApiClient;
import com.stage1.Network.ApiInterface;
import com.stage1.R;
import com.stage1.Utils.PrefManager;
import com.stage1.Utils.Validators;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AppCompatActivity {
    EditText et_email, et_password;
    private ProgressDialog pDialog;

    boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.etPassword);
        pDialog = new ProgressDialog(this);
        // Set progressbar title
//        pDialog.setTitle("Android Video Streaming Tutorial");
        // Set progressbar message
//        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);

        flag = false;
      /*  et_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX() >= (et_password.getRight() - et_password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        if (flag) {
                            flag = false;
                            et_password.setTransformationMethod(new PasswordTransformationMethod());
                        }else
                        {
                            flag = true;
                            et_password.setTransformationMethod(new HideReturnsTransformationMethod());
                        }
                        return true;
                    }
                }
                return false;
            }
        });*/
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
        ((Button) findViewById(R.id.btn_signup)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AuthActivity.this, SignUpActivity.class));
            }
        });
        ((Button) findViewById(R.id.btn_login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
//                startActivity(new Intent(AuthActivity.this,MainActivity.class));
            }
        });

        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Validators.isValidEmail(s))
                    et_email.setError("Please enter email id");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0)
                    et_password.setError("Please enter password");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void signIn() {
        if (et_email.getText().toString().trim().length() == 0) {
            et_email.setError("Please enter email id");
            return;
        }
        if (et_password.getText().toString().trim().length() == 0) {
            et_password.setError("Please enter password");
            return;
        }
        if (et_email.getText().toString().trim().length() > 0 && et_password.getText().toString().trim().length() > 0) {
            loginUser(et_email.getText().toString().trim(), et_password.getText().toString().trim());
        }
    }

    private void loginUser(String email, String password) {
        String deviceID = "asdasdasd", lat = "34232", lng = "3423433";
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseLogin> responseLoginCall = apiInterface.login(email, password, deviceID, lat, lng);
        pDialog.show();
        responseLoginCall.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.body().getErrorCode() == 0) {
                    new PrefManager(AuthActivity.this).setLogin(true);
                    new PrefManager(AuthActivity.this).storeUser(response.body().getData());
                    startActivity(new Intent(AuthActivity.this, MainActivity.class));
                    AuthActivity.this.finish();
                }
                else
                {
                    Toast.makeText(AuthActivity.this, "Invalid Email Or Password", Toast.LENGTH_SHORT).show();
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }
}
