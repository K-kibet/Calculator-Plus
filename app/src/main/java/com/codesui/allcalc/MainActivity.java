package com.codesui.allcalc;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.codesui.allcalc.ads.AppOpenManager;
import com.codesui.allcalc.ads.RewardedInterstitialManager;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private AppOpenManager appOpenManager;
    private RewardedInterstitialManager rewardedInterstitialManager;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this);

        appOpenManager = new AppOpenManager();
        appOpenManager.loadAd(this);
        rewardedInterstitialManager = new RewardedInterstitialManager(MainActivity.this, this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.basic_calculator);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (bundle == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_container, new BasicCalculatorFragment()).commit();
            navigationView.setCheckedItem(R.id.basic_calculator);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        rewardedInterstitialManager.loadAd();
        switch (item.getItemId()) {
            case R.id.basic_calculator:
                fragment = new BasicCalculatorFragment();
                toolbar.setTitle(R.string.basic_calculator);
                rewardedInterstitialManager.showAdNow();
                break;
            case R.id.bmi_calculator:
                fragment = new BMICalculatorFragment();
                toolbar.setTitle(R.string.bmi_calculator);
                rewardedInterstitialManager.showAdNow();
                break;
            case R.id.bmr_calculator:
                fragment = new BMRCalculatorFragment();
                toolbar.setTitle(R.string.bmr_calculator);
                rewardedInterstitialManager.showAdNow();
                break;
            case R.id.discount_calculator:
                fragment = new DiscountCalculatorFragment();
                toolbar.setTitle(R.string.discount_calculator);
                rewardedInterstitialManager.showAdNow();
                break;
            case R.id.tip_calculator:
                fragment = new TipCalculatorFragment();
                toolbar.setTitle(R.string.tip_calculator);
                rewardedInterstitialManager.showAdNow();
                break;
            case R.id.nav_share:
                drawer.closeDrawer(GravityCompat.START);
                share();
                return false;
            case R.id.nav_rate:
                drawer.closeDrawer(GravityCompat.START);
                rate();
                return false;
        }
        assert fragment != null;
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_container, fragment).commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        appOpenManager.showAdIfAvailable(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        appOpenManager.showAdIfAvailable(this);
    }

    private void share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Football Livestream");
        String shareMessage = "\nLet me recommend you this application\n\n";
        shareMessage = shareMessage + "https://www.amazon.com/dp/" + getPackageName() + "\n\n";
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    public void rate() {
        try {
            Intent rateIntent = rateIntentForUrl("amzn://apps/android?p=");
            startActivity(rateIntent);
        } catch (ActivityNotFoundException e) {
            Intent rateIntent = rateIntentForUrl("https://www.amazon.com/dp/");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        intent.addFlags(flags);
        return intent;
    }


    /*
    private void share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Football Livestream");
        String shareMessage = "\nLet me recommend you this application\n\n";
        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n\n";
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    public void rate() {
        try {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        } catch (ActivityNotFoundException e) {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        intent.addFlags(flags);
        return intent;
    }*/
}