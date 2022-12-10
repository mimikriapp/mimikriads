package com.mimikri.adsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.mimikri.ads.format.AdNetwork;
import com.mimikri.ads.format.BannerAd;
import com.mimikri.ads.format.InterstitialAd;
import com.mimikri.ads.format.MediumRectangleAd;
import com.mimikri.ads.format.NativeAd;



public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    AdNetwork.Initialize adNetwork;
    BannerAd.Builder bannerAd;
    MediumRectangleAd.Builder mediumRectangleAd;
    InterstitialAd.Builder interstitialAd;
    NativeAd.Builder nativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initAds();
        loadBannerAd();
        loadInterstitialAd();
        loadNativeAd();

        findViewById(R.id.btn_interstitial).setOnClickListener(v -> {
           // startActivity(new Intent(getApplicationContext(), SecondActivity.class));
            showInterstitialAd();
            bannerAd.destroyAndDetachBanner();
        });




    }

    private void initAds() {
        adNetwork = new AdNetwork.Initialize(MainActivity.this)
                .setAdStatus(Constant.AD_STATUS)
                .setAdNetwork(Constant.AD_NETWORK)
                .setBackupAdNetwork(Constant.BACKUP_AD_NETWORK)
                .setStartappAppId(Constant.STARTAPP_APP_ID)
                .setIronSourceAppKey(Constant.IRONSOURCE_APP_KEY)
                .setDebug(BuildConfig.DEBUG)
                .build();
    }

    private void loadBannerAd() {
        bannerAd = new BannerAd.Builder(this)
                .setAdStatus(Constant.AD_STATUS)
                .setAdNetwork(Constant.AD_NETWORK)
                .setBackupAdNetwork(Constant.BACKUP_AD_NETWORK)
                .setFanBannerId(Constant.FAN_BANNER_ID)
                .setIronSourceBannerId(Constant.IRONSOURCE_BANNER_ID)
                .setDarkTheme(false)
                .build();
    }

    private void loadMediumRectangleAd() {
        mediumRectangleAd = new MediumRectangleAd.Builder(this)
                .setAdStatus(Constant.AD_STATUS)
                .setAdNetwork(Constant.AD_NETWORK)
                .setBackupAdNetwork(Constant.BACKUP_AD_NETWORK)
                .setFanBannerId(Constant.FAN_BANNER_ID)
                .setIronSourceBannerId(Constant.IRONSOURCE_BANNER_ID)
                .setDarkTheme(false)
                .build();
    }

    private void loadInterstitialAd() {
        interstitialAd = new InterstitialAd.Builder(this)
                .setAdStatus(Constant.AD_STATUS)
                .setAdNetwork(Constant.AD_NETWORK)
                .setBackupAdNetwork(Constant.BACKUP_AD_NETWORK)
                .setFanInterstitialId(Constant.FAN_INTERSTITIAL_ID)
                .setIronSourceInterstitialId(Constant.IRONSOURCE_INTERSTITIAL_ID)
                .setInterval(1)
                .build();
    }

    private void showInterstitialAd() {
        interstitialAd.show();
    }

    private void loadNativeAd() {
        nativeAd = new NativeAd.Builder(this)
                .setAdStatus(Constant.AD_STATUS)
                .setAdNetwork(Constant.AD_NETWORK)
                .setBackupAdNetwork(Constant.BACKUP_AD_NETWORK)
                .setFanNativeId(Constant.FAN_NATIVE_ID)
                .setNativeAdStyle(Constant.STYLE_DEFAULT)
                .setDarkTheme(false)
                .build();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        bannerAd.destroyAndDetachBanner();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadBannerAd();
    }

}