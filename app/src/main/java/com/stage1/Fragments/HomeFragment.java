package com.stage1.Fragments;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.stage1.Activities.MainActivity;
import com.stage1.Models.ResponseSearch;
import com.stage1.Network.ApiClient;
import com.stage1.Network.ApiInterface;
import com.stage1.R;
import com.stage1.Adapters.SearchAdapter;
import com.stage1.Utils.PrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements Animation.AnimationListener, CompoundButton.OnCheckedChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    String searchquery;
    private String mParam2;
    private RecyclerView search_list;
    private RecyclerView.LayoutManager mLayoutManager;
    SearchAdapter searchAdapter;
    private LinearLayout ll_filter;
    private Animation animSideDown, animSideUp;
    private List<String> barList;
    private List<ResponseSearch.Datum> searchData;
    private HashMap<String, Boolean> filter;
    private HashMap<String, String> role;
    CheckBox bartender, waitress, bar, dancer;
    private ProgressDialog pDialog;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {

        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.barList = createDumyData();
        pDialog = new ProgressDialog(getActivity());
        // Set progressbar title
//        pDialog.setTitle("Android Video Streaming Tutorial");
        // Set progressbar message
//        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Inflate the layout for this fragment
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        search_list = view.findViewById(R.id.rec__search_list);
        final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0,R.dimen._40sdp,0,0);
        final FrameLayout.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0,0,0,0);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        search_list.setLayoutManager(mLayoutManager);
        searchquery = "";
        search_list.addItemDecoration(itemDecorator);
        ll_filter = view.findViewById(R.id.ll_filter);
        loadData();
        bartender = ll_filter.findViewById(R.id.cbox_bartender);
        waitress = ll_filter.findViewById(R.id.cbox_waitress);
        dancer = ll_filter.findViewById(R.id.cbox_dancer);
        bar = ll_filter.findViewById(R.id.cbox_bar);
        bartender.setOnCheckedChangeListener(this);
        waitress.setOnCheckedChangeListener(this);
        dancer.setOnCheckedChangeListener(this);
        bar.setOnCheckedChangeListener(this);
// load the animation
        animSideDown = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.slide_down);
        animSideUp = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.slide_up);

        // set animation listener
        animSideDown.setAnimationListener(this);
        animSideUp.setAnimationListener(this);
        ((MainActivity) getActivity()).passVal(new MainActivity.FragmentCommunicator() {


            @Override
            public void passData(boolean name) {
                if (name && ll_filter.getVisibility() == View.INVISIBLE) {
                    search_list.setLayoutParams(layoutParams);
                    ll_filter.setVisibility(View.VISIBLE);
                    ll_filter.startAnimation(animSideDown);
                } else if (!name) {
                    search_list.setLayoutParams(layoutParams1);
                    ll_filter.setVisibility(View.INVISIBLE);
                    ll_filter.startAnimation(animSideUp);
                }
            }

            @Override
            public void searchQuery(String searchquery1, List<ResponseSearch.Datum> searchData1) {
//                searchData=searchData1;
                searchquery = searchquery1;
                searchAdapter.getFilter().filter(searchquery1);
            }

            @Override
            public void loadData(List<ResponseSearch.Datum> searchData1) {
                createFilter();
                if (searchData1 == null)
                    searchData1 = new ArrayList<>();
                searchAdapter = new SearchAdapter(searchData1, filter, role);
                searchData = searchData1;
                search_list.setAdapter(searchAdapter);
                searchAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }
    public void loadData(List<ResponseSearch.Datum> searchData1) {
        createFilter();
        if (searchData1 == null)
            searchData1 = new ArrayList<>();
        searchAdapter = new SearchAdapter(searchData1, filter, role);
        searchData = searchData1;
        search_list.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();
    }
    private void createFilter() {
        SharedPreferences role_pref = new PrefManager(getActivity()).getRole_pref();
        Map<String, ?> allEntries = role_pref.getAll();
        filter = new HashMap<>();
        role = new HashMap<>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getValue().toString().toLowerCase().equals("user"))
                filter.put(entry.getValue().toString(), false);
            else
                filter.put(entry.getValue().toString(), true);

            role.put(entry.getKey(), entry.getValue().toString());
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }
    }

    private List<String> createDumyData() {
        List<String> barList = new ArrayList<>();
        barList.add("BABY DOLLS DALLAS");
        barList.add("BABY DOLLS FORT WORTH");
        barList.add("BTS CABARET");
        barList.add("BUCKS CABARET DALLAS");
        barList.add("BUCKS CABARET FORTH WORTH");
        barList.add("BUCKS WILD DALLAS");
        barList.add("BUCKS WILD FORT WORTH");
        barList.add("CABARET EAST FORT WORTH");
        barList.add("CHICAS BONITAS");
        barList.add("CHICAS LOCAS");
        barList.add("CORSETS CABARET");
        barList.add("DALLAS CABARET NORTH");
        barList.add("DALLAS CABARET SOUTH");
        barList.add("DG'S GENTLEMENS CLUB");
        barList.add("FLASH ");
        barList.add("FOXYS CABARET");
        barList.add("KINGS CABARET");
        barList.add("LA BARES");
        barList.add("LIPSTICK MENS CLUB");
        barList.add("LUCKYS CABARET");
        barList.add("ONYXS CLUB");
        barList.add("PANDORAS MENS CLUB");
        barList.add("PINUPS CABARET");
        barList.add("PT'S MENS CLUB");
        barList.add("RICK'S CABARET DFW");
        barList.add("RICK'S CABARET FORT WORTH");
        barList.add("ROXY'S SHOWGIRLS");
        barList.add("SILVER CITY CABARET");
        barList.add("SPEARMINT RHINO");
        barList.add("STARZ CABARET");
        barList.add("STILETTO'S CABARET");
        barList.add("TEMPTATIONS");
        barList.add("THE CLUBHOUSE");
        barList.add("THE LODGE");
        barList.add("THE MEN'S CLUB");
        barList.add("TIGER'S CABARET");
        barList.add("V LIVE");
        barList.add("XTC CABARET");
        barList.add("ZONA ROSA");
        return barList;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cbox_bartender:
                if (isChecked)
                    filter.put("Bartender", true);
                else
                    filter.put("Bartender", false);
                break;
            case R.id.cbox_waitress:
                if (isChecked)
                    filter.put("Waitress", true);
                else
                    filter.put("Waitress", false);
                break;
            case R.id.cbox_dancer:
                if (isChecked)
                    filter.put("Stripper", true);
                else
                    filter.put("Stripper", false);
                break;
            case R.id.cbox_bar:
                if (isChecked)
                    filter.put("Bar", true);
                else
                    filter.put("Bar", false);
                break;
        }
        searchAdapter.getFilter().filter(searchquery);
    }
    private void loadData() {


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseSearch> responseSearchCall = apiInterface.getallUser();
        pDialog.show();
        responseSearchCall.enqueue(new Callback<ResponseSearch>() {
            @Override
            public void onResponse(Call<ResponseSearch> call, Response<ResponseSearch> response) {

                if (response.body().getErrorCode() == 0) {
                    searchData = response.body().getData();
                    loadData(searchData);
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseSearch> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }
}
