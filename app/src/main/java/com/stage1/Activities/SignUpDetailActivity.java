package com.stage1.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.stage1.Models.ResponseLogin;
import com.stage1.Models.ResponseRegistration;
import com.stage1.Network.ApiClient;
import com.stage1.Network.ApiInterface;
import com.stage1.R;
import com.stage1.ResponseRole;
import com.stage1.Utils.PrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpDetailActivity extends AppCompatActivity {
    private static final String TAG = "SignupDetailactivity";
    private ProgressDialog pDialog;
    private List<ResponseRole.Data> roleList;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    boolean flag;
    EditText
            et_sign_up_address,
            et_register_email,
            et_register_password,
            et_confirm_password,
            et_popup_fname,
            et_popup_lname,
            et_popup_phone,
            et_popup_post,
            et_popup_email,
            et_popup_gender,
            et_popup_password;
    Spinner sp_popup_post;
    Button btn_sign_up_submit;
    CheckBox cb__sign_up_i_agree;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_detail);
        pDialog = new ProgressDialog(this);
        // Set progressbar title
//        pDialog.setTitle("Android Video Streaming Tutorial");
        // Set progressbar message
//        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
        initLayout();
        preparerolelist();
    }

    private void preparerolelist() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseRole> getRole = apiService.getRole();
        pDialog.show();
        getRole.enqueue(new Callback<ResponseRole>() {

            @Override
            public void onResponse(Call<ResponseRole> call, Response<ResponseRole> response) {
                if (response.body().getErrorCode() == 0 && response.body().getData().size() > 0) {
                    roleList = response.body().getData();
                    new PrefManager(SignUpDetailActivity.this).createList(response.body().getData());
                } else
                    Log.e(getClass().getName(), "something went wrong==>" + response.body().getErrorCode() + "==>" + response.body().getMessage() + "==>" + response.body().getData());

                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseRole> call, Throwable t) {
                Log.e(getClass().getName(), t.getStackTrace().toString());
                pDialog.dismiss();
            }
        });
    }

    private void initLayout() {
        et_sign_up_address = findViewById(R.id.et_sign_up_address);
        et_popup_fname = findViewById(R.id.et_sign_up_fname);
        et_popup_lname = findViewById(R.id.et_sign_up_lname);
        et_popup_phone = findViewById(R.id.et_sign_up_pno);
        et_popup_post = findViewById(R.id.et_sign_up_post);
        et_popup_email = findViewById(R.id.et_sign_up_email);
        et_popup_password = findViewById(R.id.et_sign_up_password);
        sp_popup_post = findViewById(R.id.sp_sign_up_post);
        et_popup_gender = findViewById(R.id.et_sign_up_gender);
        et_popup_email.setText(getIntent().getStringExtra("email"));
        et_popup_password.setText(getIntent().getStringExtra("pwd"));
        et_popup_email.setEnabled(false);
        et_popup_password.setEnabled(false);
        btn_sign_up_submit = findViewById(R.id.btn_sign_up_submit);
        cb__sign_up_i_agree = findViewById(R.id.cb__sign_up_i_agree);
        et_sign_up_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                    .build(SignUpDetailActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });
        /*et_sign_up_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
        /*et_sign_up_address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    try {
                        Intent intent =
                                new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                        .build(SignUpDetailActivity.this);
                        startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                    } catch (GooglePlayServicesRepairableException e) {
                        // TODO: Handle the error.
                    } catch (GooglePlayServicesNotAvailableException e) {
                        // TODO: Handle the error.
                    }
                }
            }
        });*/
        btn_sign_up_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
//        et_popup_post.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)
//                {
//                    et_popup_post.setVisibility(View.GONE);
//                    sp_popup_post.setVisibility(View.VISIBLE);
//                    sp_popup_post.performClick();
//                }
//            }
//        });
        et_popup_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                roleDropdown(true);
                final List<String> roleList_ = new ArrayList<>();
//        roleList_.add("Select Role");
//                String[] roleList_;

                for (ResponseRole.Data data : roleList) {
                    roleList_.add(data.getRole());
                }
                PopupWindow popUp = popupWindowsort(roleList_, et_popup_post);
                popUp.showAsDropDown(v, 0, 0);
            }
        });
//        et_popup_post.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            private View v;
//
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//
//            }
//        });
        et_popup_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<String> genderList = new ArrayList<>();
                genderList.add("Male");
                genderList.add("Female");
                genderList.add("Other");
                PopupWindow popupWindow = popupWindowsort(genderList, et_popup_gender);
                popupWindow.showAsDropDown(v, 0, 0);
            }
        });
//        postdropdown();
    }

    private void roleDropdown(boolean hasFocus) {
        final List<String> roleList_ = new ArrayList<>();
        roleList_.add("Select Role");
//                String[] roleList_;

        for (ResponseRole.Data data : roleList) {
            roleList_.add(data.getRole());
        }
        if (hasFocus) {
            ArrayAdapter<String> postAdapter = new ArrayAdapter<String>(SignUpDetailActivity.this, android.R.layout.select_dialog_item, roleList_);
            postAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_popup_post.setAdapter(postAdapter);
            sp_popup_post.setVisibility(View.VISIBLE);
//                    et_popup_post.setVisibility(View.GONE);

                /*    new Thread(new Runnable() {
                        public void run() {
                            // DO NOT ATTEMPT TO DIRECTLY UPDATE THE UI HERE, IT WON'T WORK!
                            // YOU MUST POST THE WORK TO THE UI THREAD'S HANDLER
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    // Open the Spinner...
//                                    pw.dismiss();

//                                    popupWindowDogs.showAsDropDown(et_popup_post, -5, 0);
                                    sp_popup_post.performClick();
                                }
                            }, 5000);
                        }
                    }).start();*/
            sp_popup_post.performClick();
//            flag = false;
            sp_popup_post.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    if (flag)
//                        sp_popup_post.setVisibility(View.GONE);
                    et_popup_post.setText(roleList.get(position + 1).getRole());
                    flag = true;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
//                    sp_popup_post.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            sp_popup_post.setVisibility(View.GONE);
//                            et_popup_post.setText(roleList.get(position).getRole());
//                        }
//                    });
        }
    }

    private void validate() {
        if (cb__sign_up_i_agree.isChecked()) {
            if (et_popup_fname.getText().toString().trim().length() == 0) {
                et_popup_fname.setError("Please enter first name");
            }
            if (et_popup_lname.getText().toString().trim().length() == 0) {
                et_popup_lname.setError("Please enter last name");
            }
            if (et_popup_phone.getText().toString().trim().length() == 0) {
                et_popup_phone.setError("Please enter correct phone number");
            }
            if (et_popup_post.getText().toString().trim().length() == 0) {
                String error_message = "Please enter type from ";
                for (ResponseRole.Data row : roleList) {
                    error_message = error_message + row.getRole();
                }
                error_message = error_message + ".";
                et_popup_post.setError(error_message);
            }
            if (et_sign_up_address.getText().toString().trim().length() == 0) {
                et_sign_up_address.setError("Please enter address");
            }
            if (et_popup_gender.getText().toString().trim().length() == 0) {
                et_popup_gender.setError("Please select gender");
            }
            if (et_popup_fname.getText().toString().trim().length() > 0 &&
                    et_popup_lname.getText().toString().trim().length() > 0 &&
                    et_popup_phone.getText().toString().trim().length() > 0 &&
                    et_popup_post.getText().toString().trim().length() > 0 &&
                    et_popup_email.getText().toString().trim().length() > 0 &&
                    et_popup_password.getText().toString().trim().length() > 0 && et_sign_up_address.getText().toString().trim().length() > 0 && et_popup_gender.getText().toString().trim().length() > 0) {
                int post = 0;
                for (ResponseRole.Data row : roleList) {
                    if (row.getRole().toLowerCase().equals(et_popup_post.getText().toString().toLowerCase().trim())) {
                        post = row.getId();
                    }
                }
                registerUser(et_popup_fname.getText().toString().trim(),
                        et_popup_lname.getText().toString().trim(),
                        et_popup_phone.getText().toString().trim(),
                        post,
                        et_popup_email.getText().toString().trim(),
                        et_popup_password.getText().toString().trim(), "123",/*,"123","123","123","123","123"*/
                        et_sign_up_address.getText().toString().trim(), et_popup_gender.getText().toString().trim());
            }
        } else {
            cb__sign_up_i_agree.setError("Please accept Terms and Conditions");
        }
    }

    private void registerUser(String fname, String lname, String phone, int post, final String email, final String password, String token,/*String street,String apt,String city,String zipcode,String state,*/String address, String gender) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseRegistration> responseRegistrationCall = apiInterface.newRegister(
                fname + " " + lname,
                phone,
                post,
                email,
                password,
                token,
               /* street,
                apt,
                city,
                zipcode,
                state*/
                address,
                gender);
        pDialog.show();
        responseRegistrationCall.enqueue(new Callback<ResponseRegistration>() {
            @Override
            public void onResponse(Call<ResponseRegistration> call, Response<ResponseRegistration> response) {
                Toast.makeText(SignUpDetailActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                if (response.body().getErrorCode() == 0) {
                    loginUser(email, password);
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseRegistration> call, Throwable t) {

                Snackbar.make((RelativeLayout) findViewById(R.id.rl_sign_up_detail), "Something went wrong", Snackbar.LENGTH_LONG).setAction("Try Again", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        validate();
                    }
                }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
//                Toast.makeText(SignUpActivity.this, "" + t.getStackTrace().toString(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        });
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
                    new PrefManager(SignUpDetailActivity.this).setLogin(true);
                    new PrefManager(SignUpDetailActivity.this).storeUser(response.body().getData());
                    startActivity(new Intent(SignUpDetailActivity.this, MainActivity.class));
                    SignUpDetailActivity.this.finish();
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                et_sign_up_address.setText(place.getAddress());
                Log.i(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    /**
     * show popup window method reuturn PopupWindow
     *
     * @param roleList_
     * @param
     */
    private PopupWindow popupWindowsort(final List<String> roleList_, final EditText et_view) {

        // initialize a pop up window type
        final PopupWindow popupWindow = new PopupWindow(this);

        ArrayList<String> sortList = new ArrayList<String>();
        sortList.add("A to Z");
        sortList.add("Z to A");
        sortList.add("Low to high price");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
                roleList_);
        // the drop down list is a list view
        ListView listViewSort = new ListView(this);

        // set our adapter and pass our pop up window contents
        listViewSort.setAdapter(adapter);

        // set on item selected
        listViewSort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                et_view.setText(roleList_.get(position));
                popupWindow.dismiss();
            }
        });

        // some other visual settings for popup window
        popupWindow.setFocusable(true);

        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        // popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.white));
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // set the listview as popup content
        popupWindow.setContentView(listViewSort);

        return popupWindow;
    }
}
