package me.wwx.mvparms.demo.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jaeger.library.StatusBarUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.wwx.mvparms.demo.R;
import me.wwx.mvparms.demo.di.component.DaggerMainComponent;
import me.wwx.mvparms.demo.di.module.MainModule;
import me.wwx.mvparms.demo.mvp.contract.MainContract;
import me.wwx.mvparms.demo.mvp.presenter.MainPresenter;
import me.wwx.mvparms.demo.mvp.ui.adapter.MainButtonAdapter;
import me.wwx.mvparms.demo.mvp.ui.util.BottomNavigationViewHelper;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 作者：wwx on 2017/5/22 0022 14:49
 * 邮箱：wanwenxiu0709@foxmail.com
 * 描述：
 */

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.maintoolbar)
    Toolbar maintoolbar;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.viewpages)
    ViewPager viewpages;

    private MenuItem menuItem;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this)) //请将MainModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public int initView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setSupportActionBar(maintoolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, maintoolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        StatusBarUtil.setColorForDrawerLayout(this, (DrawerLayout) findViewById(R.id.drawer_layout), ContextCompat.getColor(this, R.color.toolbar_color), 0);

        navView.setNavigationItemSelectedListener(this);

        viewpages.setAdapter(new MainButtonAdapter(getSupportFragmentManager()));
        viewpages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                menuItem = navigation.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewpages.setOffscreenPageLimit(2);

        BottomNavigationViewHelper.disableShiftMode(navigation);
    }

        @Override
        public void initData () {

        }


        @Override
        public void showLoading () {

        }

        @Override
        public void hideLoading () {

        }

        @Override
        public void showMessage (@NonNull String message){
            checkNotNull(message);
            UiUtils.SnackbarText(message);
        }

        @Override
        public void launchActivity (@NonNull Intent intent){
            checkNotNull(intent);
            UiUtils.startActivity(intent);
        }

        @Override
        public void killMyself () {
            finish();
        }

        @Override
        public boolean onNavigationItemSelected (@NonNull MenuItem item){
            switch (item.getItemId()) {
                case R.id.navigation_mine:
                    viewpages.setCurrentItem(0);
                    break;
                case R.id.navigation_jilv:
                    viewpages.setCurrentItem(1);
                    break;
                case R.id.navigation_vedio:
                    viewpages.setCurrentItem(2);
                    break;
                case R.id.navigation_image:
                    viewpages.setCurrentItem(3);
                    break;
                case R.id.nav_item1:
                    break;
                case R.id.nav_item2:
                    break;
                case R.id.nav_item3:
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                    break;
            }
            return false;
        }

        @Override
        public void onRefresh () {

        }

    }
