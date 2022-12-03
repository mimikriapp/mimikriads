package com.mimikri.ads.format;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdSize;

import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.mimikri.ads.util.Constant;
import com.mimikri.ads.R;

import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.ads.banner.BannerListener;

public class MediumRectangleAd {

    public static class Builder {

        private static final String TAG = "AdNetwork";
        private final Activity activity;

        private com.facebook.ads.AdView fanAdView;

        FrameLayout ironSourceBannerView;
        private IronSourceBannerLayout ironSourceBannerLayout;

        private String adStatus = "";
        private String adNetwork = "";
        private String backupAdNetwork = "";
        private String adMobBannerId = "";
        private String googleAdManagerBannerId = "";
        private String fanBannerId = "";
        private String unityBannerId = "";
        private String appLovinBannerId = "";
        private String appLovinBannerZoneId = "";
        private String mopubBannerId = "";
        private String ironSourceBannerId = "";
        private int placementStatus = 1;
        private boolean darkTheme = false;
        private boolean legacyGDPR = false;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder build() {
            loadBannerAd();
            return this;
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

        public Builder setAdMobBannerId(String adMobBannerId) {
            this.adMobBannerId = adMobBannerId;
            return this;
        }

        public Builder setGoogleAdManagerBannerId(String googleAdManagerBannerId) {
            this.googleAdManagerBannerId = googleAdManagerBannerId;
            return this;
        }

        public Builder setFanBannerId(String fanBannerId) {
            this.fanBannerId = fanBannerId;
            return this;
        }

        public Builder setUnityBannerId(String unityBannerId) {
            this.unityBannerId = unityBannerId;
            return this;
        }

        public Builder setAppLovinBannerId(String appLovinBannerId) {
            this.appLovinBannerId = appLovinBannerId;
            return this;
        }

        public Builder setAppLovinBannerZoneId(String appLovinBannerZoneId) {
            this.appLovinBannerZoneId = appLovinBannerZoneId;
            return this;
        }

        public Builder setMopubBannerId(String mopubBannerId) {
            this.mopubBannerId = mopubBannerId;
            return this;
        }

        public Builder setIronSourceBannerId(String ironSourceBannerId) {
            this.ironSourceBannerId = ironSourceBannerId;
            return this;
        }

        public Builder setPlacementStatus(int placementStatus) {
            this.placementStatus = placementStatus;
            return this;
        }

        public Builder setDarkTheme(boolean darkTheme) {
            this.darkTheme = darkTheme;
            return this;
        }

        public Builder setLegacyGDPR(boolean legacyGDPR) {
            this.legacyGDPR = legacyGDPR;
            return this;
        }

        public void loadBannerAd() {
            if (adStatus.equals(Constant.AD_STATUS_ON) && placementStatus != 0) {
                switch (adNetwork) {
                    case Constant.FAN:
                        fanAdView = new com.facebook.ads.AdView(activity, fanBannerId, AdSize.RECTANGLE_HEIGHT_250);
                        RelativeLayout fanAdViewContainer = activity.findViewById(R.id.fan_banner_view_container);
                        fanAdViewContainer.addView(fanAdView);
                        com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
                            @Override
                            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                                fanAdViewContainer.setVisibility(View.GONE);
                                loadBackupBannerAd();
                                Log.d(TAG, "Error load FAN : " + adError.getErrorMessage());
                            }

                            @Override
                            public void onAdLoaded(Ad ad) {
                                fanAdViewContainer.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAdClicked(Ad ad) {

                            }

                            @Override
                            public void onLoggingImpression(Ad ad) {

                            }
                        };
                        com.facebook.ads.AdView.AdViewLoadConfig loadAdConfig = fanAdView.buildLoadAdConfig().withAdListener(adListener).build();
                        fanAdView.loadAd(loadAdConfig);
                        break;

                    case Constant.STARTAPP:
                        RelativeLayout startAppAdView = activity.findViewById(R.id.startapp_banner_view_container);
                        Banner banner = new Banner(activity, new BannerListener() {
                            @Override
                            public void onReceiveAd(View banner) {
                                startAppAdView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onFailedToReceiveAd(View banner) {
                                startAppAdView.setVisibility(View.GONE);
                                loadBackupBannerAd();
                                Log.d(TAG, adNetwork + " failed load startapp banner ad : ");
                            }

                            @Override
                            public void onImpression(View view) {

                            }

                            @Override
                            public void onClick(View banner) {
                            }
                        });
                        startAppAdView.addView(banner);
                        break;

                    case Constant.UNITY:
//                        RelativeLayout unityAdView = activity.findViewById(R.id.unity_banner_view_container);
//                        BannerView bottomBanner = new BannerView(activity, unityBannerId, new UnityBannerSize(UNITY_ADS_BANNER_WIDTH_MEDIUM, UNITY_ADS_BANNER_HEIGHT_MEDIUM));
//                        bottomBanner.setListener(new BannerView.IListener() {
//                            @Override
//                            public void onBannerLoaded(BannerView bannerView) {
//                                unityAdView.setVisibility(View.VISIBLE);
//                                Log.d("Unity_banner", "ready");
//                            }
//
//                            @Override
//                            public void onBannerClick(BannerView bannerView) {
//
//                            }
//
//                            @Override
//                            public void onBannerFailedToLoad(BannerView bannerView, BannerErrorInfo bannerErrorInfo) {
//                                Log.d("SupportTest", "Banner Error" + bannerErrorInfo);
//                                unityAdView.setVisibility(View.GONE);
//                                loadBackupBannerAd();
//                            }
//
//                            @Override
//                            public void onBannerLeftApplication(BannerView bannerView) {
//
//                            }
//                        });
//                        unityAdView.addView(bottomBanner);
//                        bottomBanner.load();
//                        Log.d(TAG, adNetwork + " Banner Ad unit Id : " + unityBannerId);
                        break;

                    case Constant.IRONSOURCE:
                        ironSourceBannerView = activity.findViewById(R.id.ironsource_banner_view_container);
                        ISBannerSize size = ISBannerSize.RECTANGLE;
                        ironSourceBannerLayout = IronSource.createBanner(activity, size);
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                        ironSourceBannerView.addView(ironSourceBannerLayout, 0, layoutParams);
                        if (ironSourceBannerLayout != null) {
                            ironSourceBannerLayout.setBannerListener(new com.ironsource.mediationsdk.sdk.BannerListener() {
                                @Override
                                public void onBannerAdLoaded() {
                                    Log.d(TAG, "onBannerAdLoaded");
                                    ironSourceBannerView.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onBannerAdLoadFailed(IronSourceError error) {
                                    Log.d(TAG, "onBannerAdLoadFailed" + " " + error);
                                    loadBackupBannerAd();
                                }

                                @Override
                                public void onBannerAdClicked() {
                                    Log.d(TAG, "onBannerAdClicked");
                                }

                                @Override
                                public void onBannerAdScreenPresented() {
                                    Log.d(TAG, "onBannerAdScreenPresented");
                                }

                                @Override
                                public void onBannerAdScreenDismissed() {
                                    Log.d(TAG, "onBannerAdScreenDismissed");
                                }

                                @Override
                                public void onBannerAdLeftApplication() {
                                    Log.d(TAG, "onBannerAdLeftApplication");
                                }
                            });
                            IronSource.loadBanner(ironSourceBannerLayout, ironSourceBannerId);
                        } else {
                            Log.d(TAG, "IronSource.createBanner returned null");
                        }
                        break;

                    case Constant.NONE:
                        //do nothing
                        break;
                }
                Log.d(TAG, "Banner Ad is enabled");
            } else {
                Log.d(TAG, "Banner Ad is disabled");
            }
        }

        public void loadBackupBannerAd() {
            if (adStatus.equals(Constant.AD_STATUS_ON) && placementStatus != 0) {
                switch (backupAdNetwork) {
                    case Constant.FAN:
                        fanAdView = new com.facebook.ads.AdView(activity, fanBannerId, AdSize.RECTANGLE_HEIGHT_250);
                        RelativeLayout fanAdViewContainer = activity.findViewById(R.id.fan_banner_view_container);
                        fanAdViewContainer.addView(fanAdView);
                        com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
                            @Override
                            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                                fanAdViewContainer.setVisibility(View.GONE);
                                Log.d(TAG, "Error load FAN : " + adError.getErrorMessage());
                            }

                            @Override
                            public void onAdLoaded(Ad ad) {
                                fanAdViewContainer.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAdClicked(Ad ad) {

                            }

                            @Override
                            public void onLoggingImpression(Ad ad) {

                            }
                        };
                        com.facebook.ads.AdView.AdViewLoadConfig loadAdConfig = fanAdView.buildLoadAdConfig().withAdListener(adListener).build();
                        fanAdView.loadAd(loadAdConfig);
                        break;

                    case Constant.STARTAPP:
                        RelativeLayout startAppAdView = activity.findViewById(R.id.startapp_banner_view_container);
                        Banner banner = new Banner(activity, new BannerListener() {
                            @Override
                            public void onReceiveAd(View banner) {
                                startAppAdView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onFailedToReceiveAd(View banner) {
                                startAppAdView.setVisibility(View.GONE);
                                Log.d(TAG, adNetwork + " failed load startapp banner ad : ");
                            }

                            @Override
                            public void onImpression(View view) {

                            }

                            @Override
                            public void onClick(View banner) {
                            }
                        });
                        startAppAdView.addView(banner);
                        break;

                    case Constant.UNITY:
//                        RelativeLayout unityAdView = activity.findViewById(R.id.unity_banner_view_container);
//                        BannerView bottomBanner = new BannerView(activity, unityBannerId, new UnityBannerSize(UNITY_ADS_BANNER_WIDTH_MEDIUM, UNITY_ADS_BANNER_HEIGHT_MEDIUM));
//                        bottomBanner.setListener(new BannerView.IListener() {
//                            @Override
//                            public void onBannerLoaded(BannerView bannerView) {
//                                unityAdView.setVisibility(View.VISIBLE);
//                                Log.d("Unity_banner", "ready");
//                            }
//
//                            @Override
//                            public void onBannerClick(BannerView bannerView) {
//
//                            }
//
//                            @Override
//                            public void onBannerFailedToLoad(BannerView bannerView, BannerErrorInfo bannerErrorInfo) {
//                                Log.d("SupportTest", "Banner Error" + bannerErrorInfo);
//                                unityAdView.setVisibility(View.GONE);
//                            }
//
//                            @Override
//                            public void onBannerLeftApplication(BannerView bannerView) {
//
//                            }
//                        });
//                        unityAdView.addView(bottomBanner);
//                        bottomBanner.load();
//                        Log.d(TAG, adNetwork + " Banner Ad unit Id : " + unityBannerId);
                        break;

                        case Constant.IRONSOURCE:
                        ironSourceBannerView = activity.findViewById(R.id.ironsource_banner_view_container);
                        ISBannerSize size = ISBannerSize.RECTANGLE;
                        ironSourceBannerLayout = IronSource.createBanner(activity, size);
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                        ironSourceBannerView.addView(ironSourceBannerLayout, 0, layoutParams);
                        if (ironSourceBannerLayout != null) {
                            ironSourceBannerLayout.setBannerListener(new com.ironsource.mediationsdk.sdk.BannerListener() {
                                @Override
                                public void onBannerAdLoaded() {
                                    Log.d(TAG, "onBannerAdLoaded");
                                    ironSourceBannerView.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onBannerAdLoadFailed(IronSourceError error) {
                                    Log.d(TAG, "onBannerAdLoadFailed" + " " + error);
                                }

                                @Override
                                public void onBannerAdClicked() {
                                    Log.d(TAG, "onBannerAdClicked");
                                }

                                @Override
                                public void onBannerAdScreenPresented() {
                                    Log.d(TAG, "onBannerAdScreenPresented");
                                }

                                @Override
                                public void onBannerAdScreenDismissed() {
                                    Log.d(TAG, "onBannerAdScreenDismissed");
                                }

                                @Override
                                public void onBannerAdLeftApplication() {
                                    Log.d(TAG, "onBannerAdLeftApplication");
                                }
                            });
                            IronSource.loadBanner(ironSourceBannerLayout, ironSourceBannerId);
                        } else {
                            Log.d(TAG, "IronSource.createBanner returned null");
                        }
                        break;
                }
                Log.d(TAG, "Banner Ad is enabled");
            } else {
                Log.d(TAG, "Banner Ad is disabled");
            }
        }

        public void destroyAndDetachBanner() {
            if (adStatus.equals(Constant.AD_STATUS_ON) && placementStatus != 0) {
                if (adNetwork.equals(Constant.IRONSOURCE) || backupAdNetwork.equals(Constant.IRONSOURCE)) {
                    if (ironSourceBannerView != null) {
                        Log.d(TAG, "ironSource banner is not null, ready to destroy");
                        IronSource.destroyBanner(ironSourceBannerLayout);
                        ironSourceBannerView.removeView(ironSourceBannerLayout);
                    } else {
                        Log.d(TAG, "ironSource banner is null");
                    }
                }
            }
        }

    }

}
