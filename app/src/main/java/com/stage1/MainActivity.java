package com.stage1;

import android.annotation.SuppressLint;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private DrawerLayout mDrawerLayout;
    HomeFragment homeFragment;
   /* ImageView img_search,img_search_clear;
    EditText et_search;*/
    Toolbar toolbar;
    String title;
    private FragmentCommunicator fragmentCommunicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.my_toolbar);
        /*img_search_clear = findViewById(R.id.img_search_cancel);
        img_search = findViewById(R.id.img_search);
        et_search = findViewById(R.id.et_search);
        img_search.setOnClickListener(this);
        img_search_clear.setOnClickListener(this);*/
        createDialog();
        final BottomNavigationView bottomNavigation = findViewById(R.id.bottom_menu);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        FragmentTransaction fragmentTransaction;
                switch (menuItem.getItemId()) {
                    case R.id.mnu_contact:
                        return true;
                    case R.id.mnu_gallery:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new GalaryFragment(),"gallery");
                        fragmentTransaction.addToBackStack("gallery");
                        fragmentTransaction.commit();
                        return true;
                    case R.id.mnu_home:
                        getSupportFragmentManager().getBackStackEntryAt(0);
                        return true;
                    case R.id.mnu_message:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new InboxFragment(),"inbox");
                      fragmentTransaction.addToBackStack("inbox");
                      fragmentTransaction.commit();
                        return true;
                    default:
                        return false;
                }
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        if (menuItem.getItemId() == R.id.mnu_changePassword) {
                            showMyDialog();
                        }
                        if (menuItem.getItemId() ==R.id.mnu_logout)
                        {
                            showauthactivity();
                        }
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });
        View headerLayout = navigationView.getHeaderView(0); // 0-index header
        ImageView img_btn_edit = headerLayout.findViewById(R.id.img_btn_edit);
        img_btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
        homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, homeFragment);
        fragmentTransaction.commit();
    }

    private void showauthactivity() {
        startActivity(new Intent(this,AuthActivity.class));
        finish();
    }

    private void showMyDialog() {
        alertDialog.show();
    }

    AlertDialog.Builder builder;
    AlertDialog alertDialog;

    private void createDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setView(((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.auth_signin, null));
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.round_cornor_background));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        /*searchView.setBackgroundColor(getResources().getColor(android.R.color.white));*/
        searchView.setMaxWidth(10000);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnCloseListener(this);
        ImageView closeButton = (ImageView)searchView.findViewById(/*R.id.search_close_btn*/searchView.getContext().getResources()
                .getIdentifier("android:id/search_close_btn", null, null));
        /*closeButton.setOnClickListener(this);*/
        MenuItemCompat.setOnActionExpandListener( menu.findItem(R.id.search), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                flag=false;
                searchView.setIconified(false);
                boolean i = searchView.isIconified();
               fragmentCommunicator.passData(true);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                searchView.setIconified(true);
                fragmentCommunicator.passData(false);
                return true;
            }
        });
        return true;


    }
    public void passVal(FragmentCommunicator fragmentCommunicator) {
        this.fragmentCommunicator = fragmentCommunicator;

    }
    SearchView searchView;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.search:
                /*searchView.setOnQueryTextListener(this);
                searchView.performClick();
                searchView.requestFocus();*/
//                searchView.setOnQueryTextListener(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
    boolean flag= false;
    @Override
    public boolean onClose() {
            if (!searchView.isIconified()&&!flag)
            {
                flag=true;
//                searchView.setIconified(true);
//                onBackPressed();
//                searchView.onActionViewCollapsed();
                return true;
            }
//        searchView.onActionViewCollapsed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.onActionViewCollapsed();
        } else {
            super.onBackPressed();
        }
    }
    /* @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.img_search:
                title = toolbar.getTitle().toString();
                toolbar.setTitle("");
                et_search.setVisibility(View.VISIBLE);
                img_search_clear.setVisibility(View.VISIBLE);
                img_search.setVisibility(View.INVISIBLE);
                break;
            case R.id.img_search_cancel:
                toolbar.setTitle(title);
                et_search.setVisibility(View.INVISIBLE);
                img_search_clear.setVisibility(View.INVISIBLE);
                img_search.setVisibility(View.VISIBLE);
                break;
        }
    }*/
    public interface FragmentCommunicator {

        public void passData(boolean name);
    }
}
