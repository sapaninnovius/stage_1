package com.stage1.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.stage1.Activities.MainActivity;
import com.stage1.R;
import com.stage1.Adapters.SearchAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements Animation.AnimationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView search_list;
    private RecyclerView.LayoutManager mLayoutManager;
    SearchAdapter searchAdapter;
    private LinearLayout ll_filter;
    private Animation animSideDown, animSideUp;
    private List<String> barList;

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
        // Inflate the layout for this fragment
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        search_list = view.findViewById(R.id.rec__search_list);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        search_list.setLayoutManager(mLayoutManager);
        searchAdapter = new SearchAdapter(this.barList);
        search_list.setAdapter(searchAdapter);
        search_list.addItemDecoration(itemDecorator);
        ll_filter = view.findViewById(R.id.ll_filter);
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
                if (name&&ll_filter.getVisibility()==View.INVISIBLE) {
                    ll_filter.setVisibility(View.VISIBLE);
                    ll_filter.startAnimation(animSideDown);
                } else if (!name) {
                    ll_filter.setVisibility(View.INVISIBLE);
                    ll_filter.startAnimation(animSideUp);
                }
            }

            @Override
            public void searchQuery(String searchquery) {
                searchAdapter.getFilter().filter(searchquery);
            }
        });
        return view;
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
}
