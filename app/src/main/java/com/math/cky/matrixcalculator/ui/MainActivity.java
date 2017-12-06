package com.math.cky.matrixcalculator.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.math.cky.matrixcalculator.R;
import com.math.cky.matrixcalculator.conf.OperationType;
import com.math.cky.matrixcalculator.fragment.BasicOperationFragment;
import com.math.cky.matrixcalculator.fragment.DeterminantFragment;
import com.math.cky.matrixcalculator.fragment.MatrixDefineFragment;
import com.math.cky.matrixcalculator.fragment.MatrixHistoryFragment;
import com.math.cky.matrixcalculator.fragment.MulFragment;
import com.math.cky.matrixcalculator.fragment.RankFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String[] typeList;
    private boolean flag=false;
    private  FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("矩阵");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        initDate();
        fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new MatrixDefineFragment(),typeList[0]);
        adapter.addFragment(new BasicOperationFragment(),typeList[1]);
        adapter.addFragment(new MulFragment(),typeList[2]);
        adapter.addFragment(new DeterminantFragment(),typeList[3]);
        adapter.addFragment(new RankFragment(),typeList[4]);
        adapter.addFragment(new MatrixHistoryFragment(),typeList[5]);
        viewPager.setAdapter(adapter);
    }

    private void initDate(){
        typeList=new String[]{"定义","基本运算","矩阵乘法","行列式","矩阵的秩","历史"};
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (!flag) {
                flag = true;
                Snackbar snackbar=Snackbar.make(fab,R.string.exit_app,Snackbar.LENGTH_SHORT);
                ((TextView)snackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(0xffffffff);
                snackbar.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        flag = false;
                    }
                }, 2000);
                return;
            }else {
                getApplication().onTerminate();
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(this,SettingsActivity.class);
            startActivity(intent);
        }else {
            Intent intent=new Intent(this,HistoryActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.num_mul) {
            Intent intent=new Intent(MainActivity.this,SingleOperationActivity.class);
            intent.putExtra(OperationType.OPERATION_TYPE,OperationType.NUM_TIME);
            startActivity(intent);
        } else if (id == R.id.add_sub) {
            Intent intent=new Intent(MainActivity.this,AddAndSubOperationActivity.class);
            intent.putExtra(OperationType.OPERATION_TYPE,OperationType.ADD_SUB);
            startActivity(intent);
        } else if (id == R.id.multiplication) {
            Intent intent=new Intent(MainActivity.this,AddAndSubOperationActivity.class);
            intent.putExtra(OperationType.OPERATION_TYPE,OperationType.TIME);
            startActivity(intent);
        }else if(id==R.id.nav_det){
            Intent intent=new Intent(MainActivity.this,SingleOperationActivity.class);
            intent.putExtra(OperationType.OPERATION_TYPE,OperationType.DEL);
            startActivity(intent);
        }else if (id == R.id.nav_manage) {
            Intent intent=new Intent(MainActivity.this,SingleOperationActivity.class);
            intent.putExtra(OperationType.OPERATION_TYPE,OperationType.EIGEN_VALUES);
            startActivity(intent);
        }else if(id==R.id.nav_rank){
            Intent intent=new Intent(MainActivity.this,SingleOperationActivity.class);
            intent.putExtra(OperationType.OPERATION_TYPE,OperationType.RANK);
            startActivity(intent);
        }else if(id==R.id.nav_inverse){
            Intent intent=new Intent(MainActivity.this,SingleOperationActivity.class);
            intent.putExtra(OperationType.OPERATION_TYPE,OperationType.INVERSE);
            startActivity(intent);
        }else if (id==R.id.nav_triangle){
            Intent intent=new Intent(MainActivity.this,SingleOperationActivity.class);
            intent.putExtra(OperationType.OPERATION_TYPE,OperationType.LUDECOMPOSITION);
            startActivity(intent);
        }else if (id==R.id.nav_qr){
            Intent intent=new Intent(MainActivity.this,SingleOperationActivity.class);
            intent.putExtra(OperationType.OPERATION_TYPE,OperationType.QRDECOMPOSITION);
            startActivity(intent);
        }else if (id==R.id.nav_s){
            Intent intent=new Intent(MainActivity.this,SingleOperationActivity.class);
            intent.putExtra(OperationType.OPERATION_TYPE,OperationType.SDECOMPOSITION);
            startActivity(intent);
        }else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
