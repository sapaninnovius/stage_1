package com.stage1;

import android.content.Context;
import android.net.Uri;
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
import android.widget.Toast;


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
    private Animation animSideDown,animSideUp;

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
        // Inflate the layout for this fragment
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        search_list = view.findViewById(R.id.rec__search_list);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        search_list.setLayoutManager(mLayoutManager);
        searchAdapter = new SearchAdapter();
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
                if (name) {
                    ll_filter.setVisibility(View.VISIBLE);
                    ll_filter.startAnimation(animSideDown);
                } else if (!name) {
                    ll_filter.setVisibility(View.INVISIBLE);
                    ll_filter.startAnimation(animSideUp);
                }
            }
        });
        return view;
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
