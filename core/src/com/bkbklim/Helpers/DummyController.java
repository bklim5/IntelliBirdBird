package com.bkbklim.Helpers;

/**
 * Created by bklim on 12/12/15.
 */
public class DummyController implements ThirdPartyController {
    @Override
    public void showBannerAd() {

    }

    @Override
    public void hideBannerAd() {

    }



    @Override
    public boolean isAdShown() {
        return true;
    }

    @Override
    public void postFacebook(int level, String path) {

    }


}
