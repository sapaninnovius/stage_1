package com.stage1.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.stage1.Adapters.PlaceAutocompleteAdapter;
import com.stage1.Models.ResponseLogin;
import com.stage1.Models.ResponseRegistration;
import com.stage1.Network.ApiClient;
import com.stage1.Network.ApiInterface;
import com.stage1.R;
import com.stage1.ResponseRole;
import com.stage1.Utils.PrefManager;
import com.stage1.Utils.Validators;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    View popup;
    GoogleApiClient mGoogleApiClient;
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
            et_popup_password,et_popup_gender;
    Spinner sp_popup_post;
    List<ResponseRole.Data> roleList;
    String[] popUpContents;
    PopupWindow popupWindowDogs;
    private Button btn_sign_up_submit;
    private CheckBox cb__sign_up_i_agree;
    PlaceAutocompleteAdapter placeAdapter;
    private ProgressDialog pDialog;
    private boolean flag_pwd_confirm,flag_pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        et_register_email = findViewById(R.id.et_register_email);
        et_register_password = findViewById(R.id.et_register_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);
        flag_pwd=false;
        flag_pwd_confirm=false;
//        et_register_password.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                final int DRAWABLE_LEFT = 0;
//                final int DRAWABLE_TOP = 1;
//                final int DRAWABLE_RIGHT = 2;
//                final int DRAWABLE_BOTTOM = 3;
//                if (event.getAction()==MotionEvent.ACTION_UP){
//                    if(event.getRawX() >= (et_register_password.getRight() - et_register_password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                        // your action here
//                        if (flag_pwd) {
//                            flag_pwd = false;
//                            et_register_password.setTransformationMethod(new PasswordTransformationMethod());
//                        }else
//                        {
//                            flag_pwd = true;
//                            et_register_password.setTransformationMethod(new HideReturnsTransformationMethod());
//                        }
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });
//        et_confirm_password.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                final int DRAWABLE_LEFT = 0;
//                final int DRAWABLE_TOP = 1;
//                final int DRAWABLE_RIGHT = 2;
//                final int DRAWABLE_BOTTOM = 3;
//                if (event.getAction()==MotionEvent.ACTION_UP){
//                    if(event.getRawX() >= (et_confirm_password.getRight() - et_confirm_password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                        // your action here
//                        if (flag_pwd_confirm) {
//                            flag_pwd_confirm = false;
//                            et_confirm_password.setTransformationMethod(new PasswordTransformationMethod());
//                        }else
//                        {
//                            flag_pwd_confirm = true;
//                            et_confirm_password.setTransformationMethod(new HideReturnsTransformationMethod());
//                        }
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });
        pDialog = new ProgressDialog(this);
        // Set progressbar title
//        pDialog.setTitle("Android Video Streaming Tutorial");
        // Set progressbar message
//        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);

        et_register_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Validators.isValidEmail(s))
                    et_register_email.setError("Please enter email id");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_register_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0)
                    et_register_password.setError("Please enter password");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_confirm_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!et_confirm_password.getText().toString().trim().equals(et_register_password.getText().toString().trim()))
                    et_confirm_password.setError("Must match with previous entry");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ((Button) findViewById(R.id.btn_signup_)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* startActivityForResult(
                        new Intent(SignUpActivity.this, SignUpDetailActivity.class)
                                .putExtra("email", et_register_email.getText().toString().trim())
                                .putExtra("pwd", et_register_password.getText().toString().trim()
                                ), 1000);*/
                if (et_register_email.getText().toString().trim().length() > 6 && et_register_password.getText().toString().trim().length() > 6 && et_confirm_password.getText().toString().trim().length() > 6) {
//                    popup = popUpLayout(v, R.layout.activity_sign_up_detail, (ViewGroup) findViewById(R.id.rl_sign_up_detail));
//                    initLayout();
                    startActivityForResult(
                            new Intent(SignUpActivity.this, SignUpDetailActivity.class)
                                    .putExtra("email", et_register_email.getText().toString().trim())
                                    .putExtra("pwd", et_register_password.getText().toString().trim()
                                    ), 1000);
                } else {
                    if (et_register_email.getText().toString().trim().length() <7) {
                        et_register_email.setError("Please enter email id");
                    }
                    if (et_register_password.getText().toString().trim().length() < 7) {
                        et_register_password.setError("Please enter password");
                    }
                    if (et_confirm_password.getText().toString().trim().length() <7) {
                        et_confirm_password.setError("Must match with previous entry");
                    }
                }
            }
        });
        ((TextView) findViewById(R.id.txt_signin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).build();
//        placeAdapter = new PlaceAutocompleteAdapter(this, R.layout.layout_view_placesearch,
//                mGoogleApiClient, null, null);

    }



    private void initLayout() {
        et_popup_gender= popup.findViewById(R.id.et_gender);
        et_sign_up_address = popup.findViewById(R.id.et_sign_up_address);
        et_popup_fname = popup.findViewById(R.id.et_sign_up_fname);
        et_popup_lname = popup.findViewById(R.id.et_sign_up_lname);
        et_popup_phone = popup.findViewById(R.id.et_sign_up_pno);
        et_popup_post = popup.findViewById(R.id.et_sign_up_post);
        et_popup_email = popup.findViewById(R.id.et_sign_up_email);
        et_popup_password = popup.findViewById(R.id.et_sign_up_password);
        sp_popup_post = popup.findViewById(R.id.sp_sign_up_post);
        et_popup_email.setText(et_register_email.getText().toString().trim());
        et_popup_password.setText(et_register_password.getText().toString().trim());
        et_popup_email.setEnabled(false);
        et_popup_password.setEnabled(false);
        btn_sign_up_submit = popup.findViewById(R.id.btn_sign_up_submit);
        cb__sign_up_i_agree = popup.findViewById(R.id.cb__sign_up_i_agree);
        et_sign_up_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
     /*   et_popup_post.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            private View v;

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final List<String> roleList_ = new ArrayList<>();
//                String[] roleList_;

                for (ResponseRole.Data data: roleList)
                {
                    roleList_.add(data.getRole());
                }
                if (hasFocus)
                {
                    ArrayAdapter<String> postAdapter = new ArrayAdapter<String>(SignUpActivity.this,android.R.layout.select_dialog_item,roleList_);
                    postAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_popup_post.setAdapter(postAdapter);
                    sp_popup_post.setVisibility(View.VISIBLE);
//                    et_popup_post.setVisibility(View.GONE);

                    new Thread(new Runnable() {
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
                    }).start();
//                    sp_popup_post.performClick();
//                    sp_popup_post.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            sp_popup_post.setVisibility(View.GONE);
//                            et_popup_post.setText(roleList.get(position).getRole());
//                        }
//                    });
                }
            }
        });*/
//        postdropdown();
    }

    private void validate() {
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
        if (et_popup_fname.getText().toString().trim().length() > 0 &&
                et_popup_lname.getText().toString().trim().length() > 0 &&
                et_popup_phone.getText().toString().trim().length() > 0 &&
                et_popup_post.getText().toString().trim().length() > 0 &&
                et_popup_email.getText().toString().trim().length() > 0 &&
                et_popup_password.getText().toString().trim().length() > 0 && et_sign_up_address.getText().toString().trim().length() > 0) {
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
                    et_popup_password.getText().toString().trim(), "123"/*,"123","123","123","123","123"*/,
                    et_sign_up_address.getText().toString().trim(), et_popup_gender.getText().toString().trim());
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
                address,gender);
        pDialog.show();
        responseRegistrationCall.enqueue(new Callback<ResponseRegistration>() {
            @Override
            public void onResponse(Call<ResponseRegistration> call, Response<ResponseRegistration> response) {
                Toast.makeText(SignUpActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                if (response.body().getErrorCode() == 0) {
                    loginUser(email, password);
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseRegistration> call, Throwable t) {
                Snackbar.make(popup, "Something went wrong", Snackbar.LENGTH_LONG).setAction("Try Again", new View.OnClickListener() {
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
                    new PrefManager(SignUpActivity.this).setLogin(true);
                    new PrefManager(SignUpActivity.this).storeUser(response.body().getData());
                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                    SignUpActivity.this.finish();
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    /* private void postdropdown() {
         // initialize pop up window items list

         // add items on the array dynamically
         // format is DogName::DogID
         List<String> dogsList = new ArrayList<String>();
         dogsList.add("Akita Inu::1");
         dogsList.add("Alaskan Klee Kai::2");
         dogsList.add("Papillon::3");
         dogsList.add("Tibetan Spaniel::4");

         // convert to simple array
         popUpContents = new String[dogsList.size()];
         dogsList.toArray(popUpContents);


         // initialize pop up window
         popupWindowDogs = popupWindowDogs();


         // button on click listener

         View.OnClickListener handler = new View.OnClickListener() {
             public void onClick(View v) {

                 switch (v.getId()) {

                     case R.id.et_sign_up_post:
                         // show the list view as dropdown
                         popupWindowDogs.showAsDropDown(v, -5, 0);
                         break;
                 }
             }
         };

 //        et_popup_post.setOnClickListener(handler);
     }

     public PopupWindow popupWindowDogs() {

         // initialize a pop up window type
         PopupWindow popupWindow = new PopupWindow(this);

         // the drop down list is a list view
         ListView listViewDogs = new ListView(this);

         // set our adapter and pass our pop up window contents
         listViewDogs.setAdapter(dogsAdapter(popUpContents));

         // set the item click listener
         listViewDogs.setOnItemClickListener(new DogsDropdownOnItemClickListener());

         // some other visual settings
         popupWindow.setFocusable(true);
         popupWindow.setWidth(250);
         popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

         // set the list view as pop up window content
         popupWindow.setContentView(listViewDogs);

         return popupWindow;
     }


 *//*     * adapter where the list values will be set*//*

    private ArrayAdapter<String> dogsAdapter(String dogsArray[]) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dogsArray) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                // setting the ID and text for every items in the list
                String item = getItem(position);
                String[] itemArr = item.split("::");
                String text = itemArr[0];
                String id = itemArr[1];

                // visual settings for the list item
                TextView listItem = new TextView(SignUpActivity.this);

                listItem.setText(text);
                listItem.setTag(id);
                listItem.setTextSize(22);
                listItem.setPadding(10, 10, 10, 10);
                listItem.setTextColor(Color.WHITE);

                return listItem;
            }
        };

        return adapter;
    }
    public class DogsDropdownOnItemClickListener implements AdapterView.OnItemClickListener {

        String TAG = "DogsDropdownOnItemClickListener.java";

        @Override
        public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {

            // get the context and main activity to access variables
            Context mContext = v.getContext();
            SignUpActivity mainActivity = ((SignUpActivity) mContext);

            // add some animation when a list item was clicked
            Animation fadeInAnimation = AnimationUtils.loadAnimation(v.getContext(), android.R.anim.fade_in);
            fadeInAnimation.setDuration(10);
            v.startAnimation(fadeInAnimation);

            // dismiss the pop up
            mainActivity.popupWindowDogs.dismiss();

            // get the text and set it as the button text
            String selectedItemText = ((TextView) v).getText().toString();
//            mainActivity.buttonShowDropDown.setText(selectedItemText);

            // get the id
            String selectedItemTag = ((TextView) v).getTag().toString();
            Toast.makeText(mContext, "Dog ID is: " + selectedItemTag, Toast.LENGTH_SHORT).show();

        }

    }*/
    PopupWindow pw;

    @SuppressLint("NewApi")
    View popUpLayout(View source_view, int screen_source, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Inflate the view from a predefined XML layout
        View layout = inflater.inflate(screen_source, parent);
        // create a 300px width and 470px height PopupWindow
        Drawable dim = new ColorDrawable(Color.BLACK);
        dim.setBounds(0, 0, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dim.setAlpha((int) (255 * 1));
        layout.getOverlay().add(dim);

        pw = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        // display the popup in the center
        pw.showAtLocation(source_view, Gravity.CENTER, -115, 50);
        return layout;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
