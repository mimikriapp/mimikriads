package com.mimikri.ads.format;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chartboost.sdk.ads.Interstitial;
import com.chartboost.sdk.callbacks.BannerCallback;
import com.chartboost.sdk.callbacks.InterstitialCallback;
import com.chartboost.sdk.events.CacheError;
import com.chartboost.sdk.events.CacheEvent;
import com.chartboost.sdk.events.ClickError;
import com.chartboost.sdk.events.ClickEvent;
import com.chartboost.sdk.events.DismissEvent;
import com.chartboost.sdk.events.ImpressionEvent;
import com.chartboost.sdk.events.ShowError;
import com.chartboost.sdk.events.ShowEvent;
import com.facebook.ads.InterstitialAdListener;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.InterstitialListener;

import com.mimikri.ads.util.Constant;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;

import java.util.concurrent.TimeUnit;

public class InterstitialAd {

    public static class Builder {

        private static final String TAG = "AdNetwork";
        private final Activity activity;
        private Interstitial chartboostInterstitial = null;
        private com.facebook.ads.InterstitialAd fanInterstitialAd;
        private StartAppAd startAppAd;
        //        private com.unity3d.mediation.InterstitialAd unityInterstitialAd;

        private int retryAttempt;
        private int counter = 1;

        private String adStatus = "";
        private String adNetwork = "";
        private String backupAdNetwork = "";
        private String adMobInterstitialId = "";
        private String googleAdManagerInterstitialId = "";
        private String fanInterstitialId = "";
        private String unityInterstitialId = "";
        private String appLovinInterstitialId = "";
        private String appLovinInterstitialZoneId = "";
        private String mopubInterstitialId = "";
        private String ironSourceInterstitialId = "";
        private int placementStatus = 1;
        private int interval = 3;

        private boolean legacyGDPR = false;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder build() {
            loadInterstitialAd();
            return this;
        }

        public void show() {
            showInterstitialAd();
        }

        public Builder setAdStatus(String adStatus) {
            this.adStatus = adStatus;
            return this;
        }

        public Builder setAdNetwork(String adNetwork) {
            this.adNetwork = adNetwork;
            return this;
        }

        public Builder setBackupAdNetwork(String backupAdNetwork) {
            this.backupAdNetwork = backupAdNetwork;
            return this;
        }

        public Builder setAdMobInterstitialId(String adMobInterstitialId) {
            this.adMobInterstitialId = adMobInterstitialId;
            return this;
        }

        public Builder setGoogleAdManagerInterstitialId(String googleAdManagerInterstitialId) {
            this.googleAdManagerInterstitialId = googleAdManagerInterstitialId;
            return this;
        }

        public Builder setFanInterstitialId(String fanInterstitialId) {
            this.fanInterstitialId = fanInterstitialId;
            return this;
        }

        public Builder setUnityInterstitialId(String unityInterstitialId) {
            this.unityInterstitialId = unityInterstitialId;
            return this;
        }

        public Builder setAppLovinInterstitialId(String appLovinInterstitialId) {
            this.appLovinInterstitialId = appLovinInterstitialId;
            return this;
        }

        public Builder setAppLovinInterstitialZoneId(String appLovinInterstitialZoneId) {
            this.appLovinInterstitialZoneId = appLovinInterstitialZoneId;
            return this;
        }

        public Builder setMopubInterstitialId(String mopubInterstitialId) {
            this.mopubInterstitialId = mopubInterstitialId;
            return this;
        }

        public Builder setIronSourceInterstitialId(String ironSourceInterstitialId) {
            this.ironSourceInterstitialId = ironSourceInterstitialId;
            return this;
        }

        public Builder setPlacementStatus(int placementStatus) {
            this.placementStatus = placementStatus;
            return this;
        }

        public Builder setInterval(int interval) {
            this.interval = interval;
            return this;
        }

        public Builder setLegacyGDPR(boolean legacyGDPR) {
            this.legacyGDPR = legacyGDPR;
            return this;
        }

        public void loadInterstitialAd() {
            if (adStatus.equals(Constant.AD_STATUS_ON) && placementStatus != 0) {
                switch (adNetwork) {
                    case Constant.FAN:
                        fanInterstitialAd = new com.facebook.ads.InterstitialAd(activity, fanInterstitialId);
                        com.facebook.ads.InterstitialAdListener adListener = new InterstitialAdListener() {
                            @Override
                            public void onInterstitialDisplayed(com.facebook.ads.Ad ad) {

                            }

                            @Override
                            public void onInterstitialDismissed(com.facebook.ads.Ad ad) {
                                fanInterstitialAd.loadAd();
                            }

                            @Override
                            public void onError(com.facebook.ads.Ad ad, com.facebook.ads.AdError adError) {
                                loadBackupInterstitialAd();
                            }

                            @Override
                            public void onAdLoaded(com.facebook.ads.Ad ad) {
                                Log.d(TAG, "FAN Interstitial is loaded");
                            }

                            @Override
                            public void onAdClicked(com.facebook.ads.Ad ad) {

                            }

                            @Override
                            public void onLoggingImpression(com.facebook.ads.Ad ad) {

                            }
                        };

                        com.facebook.ads.InterstitialAd.InterstitialLoadAdConfig loadAdConfig = fanInterstitialAd.buildLoadAdConfig().withAdListener(adListener).build();
                        fanInterstitialAd.loadAd(loadAdConfig);
                        break;

                    case Constant.STARTAPP:
                        startAppAd = new StartAppAd(activity);
                        startAppAd.loadAd(new AdEventListener() {
                            @Override
                            public void onReceiveAd(@NonNull Ad ad) {
                                Log.d(TAG, "Startapp Interstitial Ad loaded");
                            }

                            @Override
                            public void onFailedToReceiveAd(@Nullable Ad ad) {
                                Log.d(TAG, "Failed to load Startapp Interstitial Ad");
                                loadBackupInterstitialAd();
                            }
                        });
                        break;

                    case Constant.UNITY:
//                        unityInterstitialAd = new com.unity3d.mediation.InterstitialAd(activity, unityInterstitialId);
//                        final IInterstitialAdLoadListener unityAdLoadListener = new IInterstitialAdLoadListener() {
//                            @Override
//                            public void onInterstitialLoaded(com.unity3d.mediation.InterstitialAd interstitialAd) {
//                                Log.d(TAG, "unity interstitial ad loaded");
//                            }
//
//                            @Override
//                            public void onInterstitialFailedLoad(com.unity3d.mediation.InterstitialAd interstitialAd, LoadError loadError, String s) {
//                                Log.e(TAG, "Unity Ads failed to load ad : " + unityInterstitialId + " : error : " + s);
//                                loadBackupInterstitialAd();
//                            }
//
//                        };
//                        unityInterstitialAd.load(unityAdLoadListener);
                        break;

                    case Constant.CHARTBOOST:
                        chartboostInterstitial = new Interstitial("start",new InterstitialCallback() {
                            @Override
                            public void onAdLoaded(@NonNull CacheEvent cacheEvent, @Nullable CacheError cacheError) {
                                if (cacheError != null) {
                                    //addToUILog("Interstitial cached error: " + cacheError.getCode().name());
                                   // incrementCounter(failLoadCounter);
                                    Log.d(TAG, "  chartboostInterstitial cacheError");
                                    loadBackupInterstitialAd();
                                } else {
                                    //addToUILog("Interstitial cached");
                                    Log.d(TAG, "  chartboostInterstitial on ad loaded");
                                   // if(chartboostInterstitial != null) { chartboostInterstitial.cache();}
                                   // chartboostInterstitial.cache();
                                }
                            }
                            @Override
                            public void onAdClicked(@NonNull ClickEvent clickEvent, @Nullable ClickError clickError) { }
                            @Override
                            public void onAdRequestedToShow(@NonNull ShowEvent showEvent) { }

                            @Override
                            public void onAdShown(@NonNull ShowEvent showEvent, @Nullable ShowError showError) {
                                if(showError != null) {
                                   // loadBackupInterstitialAd();
                                   // chartboostInterstitial.cache();
                                }else {
                                   // chartboostInterstitial.cache();
                                }
                            }

                            @Override
                            public void onImpressionRecorded(@NonNull ImpressionEvent impressionEvent) {}

                            @Override
                            public void onAdDismiss(@NonNull DismissEvent dismissEvent) { }

                         }, null);
                        chartboostInterstitial.cache();
                        break;

                    case Constant.IRONSOURCE:
                        IronSource.setInterstitialListener(new InterstitialListener() {
                            @Override
                            public void onInterstitialAdReady() {
                                Log.d(TAG, "onInterstitialAdReady");
                            }

                            @Override
                            public void onInterstitialAdLoadFailed(IronSourceError ironSourceError) {
                                Log.d(TAG, "onInterstitialAdLoadFailed" + " " + ironSourceError);
                                loadBackupInterstitialAd();
                            }

                            @Override
                            public void onInterstitialAdOpened() {
                                Log.d(TAG, "onInterstitialAdOpened");
                            }

                            @Override
                            public void onInterstitialAdClosed() {
                                Log.d(TAG, "onInterstitialAdClosed");
                                loadInterstitialAd();
                            }

                            @Override
                            public void onInterstitialAdShowSucceeded() {
                                Log.d(TAG, "onInterstitialAdShowSucceeded");
                            }

                            @Override
                            public void onInterstitialAdShowFailed(IronSourceError ironSourceError) {
                                Log.d(TAG, "onInterstitialAdShowFailed" + " " + ironSourceError);
                                loadBackupInterstitialAd();
                            }

                            @Override
                            public void onInterstitialAdClicked() {
                                Log.d(TAG, "onInterstitialAdClicked");
                            }
                        });
                        IronSource.loadInterstitial();
                        break;
                }
            }
        }

        public void loadBackupInterstitialAd() {
            if (adStatus.equals(Constant.AD_STATUS_ON) && placementStatus != 0) {
                switch (backupAdNetwork) {
                    case Constant.FAN:
                        fanInterstitialAd = new com.facebook.ads.InterstitialAd(activity, fanInterstitialId);
                        com.facebook.ads.InterstitialAdListener adListener = new InterstitialAdListener() {
                            @Override
                            public void onInterstitialDisplayed(com.facebook.ads.Ad ad) {

                            }

                            @Override
                            public void onInterstitialDismissed(com.facebook.ads.Ad ad) {
                                fanInterstitialAd.loadAd();
                            }

                            @Override
                            public void onError(com.facebook.ads.Ad ad, com.facebook.ads.AdError adError) {

                            }

                            @Override
                            public void onAdLoaded(com.facebook.ads.Ad ad) {
                                Log.d(TAG, "FAN Interstitial is loaded");
                            }

                            @Override
                            public void onAdClicked(com.facebook.ads.Ad ad) {

                            }

                            @Override
                            public void onLoggingImpression(com.facebook.ads.Ad ad) {

                            }
                        };

                        com.facebook.ads.InterstitialAd.InterstitialLoadAdConfig loadAdConfig = fanInterstitialAd.buildLoadAdConfig().withAdListener(adListener).build();
                        fanInterstitialAd.loadAd(loadAdConfig);
                        break;

                    case Constant.STARTAPP:
                        startAppAd = new StartAppAd(activity);
                        startAppAd.loadAd(new AdEventListener() {
                            @Override
                            public void onReceiveAd(@NonNull Ad ad) {
                                Log.d(TAG, "Startapp Interstitial Ad loaded");
                            }

                            @Override
                            public void onFailedToReceiveAd(@Nullable Ad ad) {
                                Log.d(TAG, "Failed to load Startapp Interstitial Ad");
                            }
                        });
                        Log.d(TAG, "load StartApp as backup Ad");
                        break;

                    case Constant.UNITY:
//                        unityInterstitialAd = new com.unity3d.mediation.InterstitialAd(activity, unityInterstitialId);
//                        final IInterstitialAdLoadListener unityAdLoadListener = new IInterstitialAdLoadListener() {
//                            @Override
//                            public void onInterstitialLoaded(com.unity3d.mediation.InterstitialAd interstitialAd) {
//                                Log.d(TAG, "unity interstitial ad loaded");
//                            }
//
//                            @Override
//                            public void onInterstitialFailedLoad(com.unity3d.mediation.InterstitialAd interstitialAd, LoadError loadError, String s) {
//                                Log.e(TAG, "Unity Ads failed to load ad : " + unityInterstitialId + " : error : " + s);
//                            }
//
//                        };
//                        unityInterstitialAd.load(unityAdLoadListener);
                        break;

                        case Constant.CHARTBOOST:
                        chartboostInterstitial = new Interstitial("start",new InterstitialCallback() {
                            @Override
                            public void onAdLoaded(@NonNull CacheEvent cacheEvent, @Nullable CacheError cacheError) {
                                if (cacheError != null) {
                                    //addToUILog("Interstitial cached error: " + cacheError.getCode().name());
                                    // incrementCounter(failLoadCounter);
                                    Log.d(TAG, "  chartboostInterstitial cacheError");
                                   // loadBackupInterstitialAd();
                                } else {
                                    //addToUILog("Interstitial cached");
                                    Log.d(TAG, "  chartboostInterstitial on ad loaded");
                                    // if(chartboostInterstitial != null) { chartboostInterstitial.cache();}
                                    // chartboostInterstitial.cache();
                                }
                            }
                            @Override
                            public void onAdClicked(@NonNull ClickEvent clickEvent, @Nullable ClickError clickError) { }
                            @Override
                            public void onAdRequestedToShow(@NonNull ShowEvent showEvent) { }

                            @Override
                            public void onAdShown(@NonNull ShowEvent showEvent, @Nullable ShowError showError) {
                                if(showError != null) {
                                    // loadBackupInterstitialAd();
                                    // chartboostInterstitial.cache();
                                }else {
                                    // chartboostInterstitial.cache();
                                }
                            }

                            @Override
                            public void onImpressionRecorded(@NonNull ImpressionEvent impressionEvent) {}

                            @Override
                            public void onAdDismiss(@NonNull DismissEvent dismissEvent) { }

                        }, null);
                        chartboostInterstitial.cache();
                        break;

                    case Constant.IRONSOURCE:
                        IronSource.setInterstitialListener(new InterstitialListener() {
                            @Override
                            public void onInterstitialAdReady() {
                                Log.d(TAG, "onInterstitialAdReady");
                            }

                            @Override
                            public void onInterstitialAdLoadFailed(IronSourceError ironSourceError) {
                                Log.d(TAG, "onInterstitialAdLoadFailed" + " " + ironSourceError);
                            }

                            @Override
                            public void onInterstitialAdOpened() {
                                Log.d(TAG, "onInterstitialAdOpened");
                            }

                            @Override
                            public void onInterstitialAdClosed() {
                                Log.d(TAG, "onInterstitialAdClosed");
                                loadInterstitialAd();
                            }

                            @Override
                            public void onInterstitialAdShowSucceeded() {
                                Log.d(TAG, "onInterstitialAdShowSucceeded");
                            }

                            @Override
                            public void onInterstitialAdShowFailed(IronSourceError ironSourceError) {
                                Log.d(TAG, "onInterstitialAdShowFailed" + " " + ironSourceError);
                            }

                            @Override
                            public void onInterstitialAdClicked() {
                                Log.d(TAG, "onInterstitialAdClicked");
                            }
                        });
                        IronSource.loadInterstitial();
                        break;

                    case Constant.NONE:
                        //do nothing
                        break;
                }
            }
        }

        public void showInterstitialAd() {
            if (adStatus.equals(Constant.AD_STATUS_ON) && placementStatus != 0) {
                if (counter == interval) {
                    switch (adNetwork) {
                        case Constant.FAN:
                            if (fanInterstitialAd != null && fanInterstitialAd.isAdLoaded()) {
                                fanInterstitialAd.show();
                                Log.d(TAG, "fan interstitial not null");
                            } else {
                                showBackupInterstitialAd();
                                Log.d(TAG, "fan interstitial null");
                            }
                            break;

                        case Constant.STARTAPP:
                            if (startAppAd != null) {
                                startAppAd.showAd();
                                Log.d(TAG, "startapp interstitial not null [counter] : " + counter);
                            } else {
                                showBackupInterstitialAd();
                                Log.d(TAG, "startapp interstitial null");
                            }
                            break;

                        case Constant.UNITY:
//                            final IInterstitialAdShowListener showListener = new IInterstitialAdShowListener() {
//                                @Override
//                                public void onInterstitialShowed(com.unity3d.mediation.InterstitialAd interstitialAd) {
//
//                                }
//
//                                @Override
//                                public void onInterstitialClicked(com.unity3d.mediation.InterstitialAd interstitialAd) {
//
//                                }
//
//                                @Override
//                                public void onInterstitialClosed(com.unity3d.mediation.InterstitialAd interstitialAd) {
//
//                                }
//
//                                @Override
//                                public void onInterstitialFailedShow(com.unity3d.mediation.InterstitialAd interstitialAd, ShowError showError, String s) {
//                                    Log.d(TAG, "unity ads show failure");
//                                    showBackupInterstitialAd();
//                                }
//                            };
//                            unityInterstitialAd.show(showListener);
                            break;

                        case Constant.CHARTBOOST:
                            if(chartboostInterstitial != null) {
                                chartboostInterstitial.show();
                            }
                            break;

                        case Constant.IRONSOURCE:
                            if (IronSource.isInterstitialReady()) {
                                IronSource.showInterstitial(ironSourceInterstitialId);
                            } else {
                                showBackupInterstitialAd();
                            }
                            break;
                    }
                    counter = 1;
                } else {
                    counter++;
                }
                Log.d(TAG, "Current counter : " + counter);
            }
        }

        public void showBackupInterstitialAd() {
            if (adStatus.equals(Constant.AD_STATUS_ON) && placementStatus != 0) {
                Log.d(TAG, "Show Backup Interstitial Ad [" + backupAdNetwork.toUpperCase() + "]");
                switch (backupAdNetwork) {
                    case Constant.FAN:
                        if (fanInterstitialAd != null && fanInterstitialAd.isAdLoaded()) {
                            fanInterstitialAd.show();
                        }
                        break;

                    case Constant.STARTAPP:
                        if (startAppAd != null) {
                            startAppAd.showAd();
                        }
                        break;

                    case Constant.UNITY:
//                        final IInterstitialAdShowListener showListener = new IInterstitialAdShowListener() {
//                            @Override
//                            public void onInterstitialShowed(com.unity3d.mediation.InterstitialAd interstitialAd) {
//
//                            }
//
//                            @Override
//                            public void onInterstitialClicked(com.unity3d.mediation.InterstitialAd interstitialAd) {
//
//                            }
//
//                            @Override
//                            public void onInterstitialClosed(com.unity3d.mediation.InterstitialAd interstitialAd) {
//
//                            }
//
//                            @Override
//                            public void onInterstitialFailedShow(com.unity3d.mediation.InterstitialAd interstitialAd, ShowError showError, String s) {
//                                Log.d(TAG, "unity ads show failure");
//                                showBackupInterstitialAd();
//                            }
//                        };
//                        unityInterstitialAd.show(showListener);
                        break;
                    case Constant.CHARTBOOST:
                        if(chartboostInterstitial != null) {
                            chartboostInterstitial.show();
                        }
                        break;

                    case Constant.IRONSOURCE:
                        if (IronSource.isInterstitialReady()) {
                            IronSource.showInterstitial(ironSourceInterstitialId);
                        }
                        break;

                    case Constant.NONE:
                        //do nothing
                        break;
                }
            }
        }

    }

}
