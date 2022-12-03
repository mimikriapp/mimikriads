package com.mimikri.ads.format;

import android.app.Activity;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

//import com.applovin.sdk.AppLovinMediationProvider;
//import com.applovin.sdk.AppLovinSdk;
import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.privacy.model.CCPA;
import com.chartboost.sdk.privacy.model.GDPR;
import com.ironsource.mediationsdk.IronSource;
import com.mimikri.ads.R;
import com.mimikri.ads.helper.AudienceNetworkInitializeHelper;
import com.mimikri.ads.util.Constant;

import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

public class AdNetwork {

    public static class Initialize {

        private static final String TAG = "AdNetwork";
        Activity activity;
        private String adStatus = "";
        private String adNetwork = "";
        private String backupAdNetwork = "";
        private String adMobAppId = "";
        private String startappAppId = "0";
        private String unityGameId = "";
        private String appLovinSdkKey = "";
        private String mopubBannerId = "";
        private String ironSourceAppKey = "";
        private String chartboost_app_id = "";
        private String chartboost_signature = "";
        private boolean debug = true;

        public Initialize(Activity activity) {
            this.activity = activity;
        }

        public Initialize build() {
            initAds();
            initBackupAds();
            return this;
        }

        public Initialize setAdStatus(String adStatus) {
            this.adStatus = adStatus;
            return this;
        }

        public Initialize setAdNetwork(String adNetwork) {
            this.adNetwork = adNetwork;
            return this;
        }

        public Initialize setBackupAdNetwork(String backupAdNetwork) {
            this.backupAdNetwork = backupAdNetwork;
            return this;
        }

        public Initialize setAdMobAppId(String adMobAppId) {
            this.adMobAppId = adMobAppId;
            return this;
        }

        public Initialize setStartappAppId(String startappAppId) {
            this.startappAppId = startappAppId;
            return this;
        }

        public Initialize setUnityGameId(String unityGameId) {
            this.unityGameId = unityGameId;
            return this;
        }

        public Initialize setAppLovinSdkKey(String appLovinSdkKey) {
            this.appLovinSdkKey = appLovinSdkKey;
            return this;
        }

        public Initialize setMopubBannerId(String mopubBannerId) {
            this.mopubBannerId = mopubBannerId;
            return this;
        }

        public Initialize setIronSourceAppKey(String ironSourceAppKey) {
            this.ironSourceAppKey = ironSourceAppKey;
            return this;
        }

        public Initialize setChartboost_app_id(String chartboost_app_id) {
            this.chartboost_app_id = chartboost_app_id;
            return this;
        }
        public Initialize setChartboost_signature(String chartboost_signature) {
            this.chartboost_signature = chartboost_signature;
            return this;
        }



        public Initialize setDebug(boolean debug) {
            this.debug = debug;
            return this;
        }

        public void initAds() {
            if (adStatus.equals(Constant.AD_STATUS_ON)) {
                switch (adNetwork) {
                    case Constant.FAN:
                        AudienceNetworkInitializeHelper.initializeAd(activity, debug);
                        break;
                    case Constant.STARTAPP:
                        StartAppSDK.init(activity, startappAppId, false);
                        StartAppSDK.setTestAdsEnabled(debug);
                        StartAppAd.disableSplash();
                        StartAppSDK.setUserConsent(activity, "pas", System.currentTimeMillis(), true);
                        break;
                    case Constant.UNITY:
                        if (debug) {
                            new AlertDialog.Builder(activity)
                                    .setTitle("Unity Ads Removal")
                                    .setMessage(Html.fromHtml(activity.getString(R.string.unity_ads_announcement)))
                                    .setPositiveButton("DISMISS", null)
                                    .show();
                        }
//                        InitializationConfiguration configuration = InitializationConfiguration.builder()
//                                .setGameId(unityGameId)
//                                .setInitializationListener(new IInitializationListener() {
//                                    @Override
//                                    public void onInitializationComplete() {
//                                        Log.d(TAG, "Unity Mediation is successfully initialized. with ID : " + unityGameId);
//                                    }
//
//                                    @Override
//                                    public void onInitializationFailed(SdkInitializationError errorCode, String msg) {
//                                        Log.d(TAG, "Unity Mediation Failed to Initialize : " + msg);
//                                    }
//                                }).build();
//                        UnityMediation.initialize(configuration);
                        break;
                    case Constant.CHARTBOOST:
                        // Needs to be set before SDK init
                        Chartboost.addDataUseConsent(activity, new GDPR(GDPR.GDPR_CONSENT.BEHAVIORAL));
                        Chartboost.addDataUseConsent(activity, new CCPA(CCPA.CCPA_CONSENT.OPT_IN_SALE));
                        Chartboost.startWithAppId(activity, chartboost_app_id, chartboost_signature, startError -> {
                            if (startError == null) {
                                Log.d(TAG, "CHARTBOOST SDK is initialized");
                            } else {
                                Log.d(TAG, "CHARTBOOST SDK initialized with error: "+startError.getCode().name());
                                Log.d(TAG, "CHARTBOOST SDK "+"app_id="+chartboost_app_id+"_signature="+chartboost_signature);
                            }
                        });
                        Chartboost.getDataUseConsent(activity, GDPR.GDPR_STANDARD);
                        break;
                    case Constant.IRONSOURCE:
                        String advertisingId = IronSource.getAdvertiserId(activity);
                        IronSource.setUserId(advertisingId);
                        IronSource.init(activity, ironSourceAppKey);
                        break;
                }
                Log.d(TAG, "[" + adNetwork + "] is selected as Primary Ads");
            }
        }

        public void initBackupAds() {
            if (adStatus.equals(Constant.AD_STATUS_ON)) {
                switch (backupAdNetwork) {
                    case Constant.STARTAPP:
                        StartAppSDK.init(activity, startappAppId, false);
                        StartAppSDK.setTestAdsEnabled(debug);
                        StartAppAd.disableSplash();
                        StartAppSDK.setUserConsent(activity, "pas", System.currentTimeMillis(), true);
                        break;
                    case Constant.UNITY:
//                        InitializationConfiguration configuration = InitializationConfiguration.builder()
//                                .setGameId(unityGameId)
//                                .setInitializationListener(new IInitializationListener() {
//                                    @Override
//                                    public void onInitializationComplete() {
//                                        Log.d(TAG, "Unity Mediation is successfully initialized. with ID : " + unityGameId);
//                                    }
//
//                                    @Override
//                                    public void onInitializationFailed(SdkInitializationError errorCode, String msg) {
//                                        Log.d(TAG, "Unity Mediation Failed to Initialize : " + msg);
//                                    }
//                                }).build();
//                        UnityMediation.initialize(configuration);
                        break;

                    case Constant.CHARTBOOST:
                        // Needs to be set before SDK init
                        Chartboost.addDataUseConsent(activity, new GDPR(GDPR.GDPR_CONSENT.BEHAVIORAL));
                        Chartboost.addDataUseConsent(activity, new CCPA(CCPA.CCPA_CONSENT.OPT_IN_SALE));
                        Chartboost.startWithAppId(activity, chartboost_app_id, chartboost_signature, startError -> {
                            if (startError == null) {
                                Log.d(TAG, "CHARTBOOST SDK is initialized");

                            } else {
                                Log.d(TAG, "CHARTBOOST SDK initialized with error: "+startError.getCode().name());
                            }
                        });
                        break;
                    case Constant.IRONSOURCE:
                        String advertisingId = IronSource.getAdvertiserId(activity);
                        IronSource.setUserId(advertisingId);
                        IronSource.init(activity, ironSourceAppKey);
                        break;

                    case Constant.NONE:
                        //do nothing
                        break;
                }
                Log.d(TAG, "[" + backupAdNetwork + "] is selected as Backup Ads");
            }
        }

    }

}
