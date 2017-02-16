package com.bkbklim.Helpers;

/**
 * Created by bklim on 16/01/16.
 */
public interface ThirdPartyController {
    //admob related
    public void showBannerAd();
    public void hideBannerAd();
    public boolean isAdShown();
//    public boolean isWifiConnected();

    //facebook related
    public void postFacebook(int level, String path);

}
