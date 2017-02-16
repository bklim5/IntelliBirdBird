package com.bkbklim.android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.bkbklim.Helpers.ThirdPartyController;
import com.bkbklim.IntelliBirdBird;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.util.Arrays;
import java.util.List;


public class AndroidLauncher extends AndroidApplication implements ThirdPartyController {
	private static final String BANNER_AD_UNIT_ID = "ca-app-pub-4810050267945909/8113506679";
	AdView bannerAd;
	CallbackManager callbackManager;
	ShareDialog shareDialog;
	RelativeLayout layout;
	View gameView;


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		gameView = initializeForView(new IntelliBirdBird(this), config);
		layout = new RelativeLayout(this);
		layout.addView(gameView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

		setupAds();
		layout.addView(bannerAd, params);


		List<String> permissionNeeds= Arrays.asList("public_profile");
		FacebookSdk.sdkInitialize(getApplicationContext());
		callbackManager = CallbackManager.Factory.create();
		shareDialog = new ShareDialog(this);
//		LoginManager.getInstance().logInWithReadPermissions(this, permissionNeeds);
//		LoginManager.getInstance().registerCallback(callbackManager,
//				new FacebookCallback<LoginResult>() {
//					@Override
//					public void onSuccess(LoginResult loginResult) {
//						// App code
//						Log.i("facebook login", "success");
//					}
//
//					@Override
//					public void onCancel() {
//						// App code
//					}
//
//					@Override
//					public void onError(FacebookException exception) {
//						// App code
//					}
//				});

		setContentView(layout);
	}


	public void setupAds() {
		bannerAd = new AdView(this);
		bannerAd.setVisibility(View.VISIBLE);
		bannerAd.setBackgroundColor(0xff000000);
		bannerAd.setAdUnitId(BANNER_AD_UNIT_ID);
		bannerAd.setAdSize(AdSize.SMART_BANNER);
		AdRequest adRequest = new AdRequest.Builder().build();
		bannerAd.loadAd(adRequest);
	}


	@Override
	public void showBannerAd() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				bannerAd.setVisibility(View.VISIBLE);
			}
		});
	}

	@Override
	public void hideBannerAd() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				bannerAd.setVisibility(View.INVISIBLE);
			}
		});
	}

	@Override
	public boolean isAdShown() {
		if (bannerAd.getVisibility() == View.VISIBLE) {
			return true;
		}

		return false;
	}

	@Override
	public void postFacebook(int level, String path) {
		final String filePath = path;
		final File file = new File(filePath);
		shareDialog.registerCallback(callbackManager, new

				FacebookCallback<Sharer.Result>() {
					@Override
					public void onSuccess(Sharer.Result result) {
						boolean deleted = file.delete();
						if (deleted) {
							Toast.makeText(AndroidLauncher.this, "You have successfully shared IntelliBirdBird in Facebook!", Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onCancel() {
						boolean deleted = file.delete();
						if (deleted) {
							Toast.makeText(AndroidLauncher.this, "Remember to share when you achieve higher level!", Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onError(FacebookException error) {
						Toast.makeText(AndroidLauncher.this, "Error sharing post.. please try again later", Toast.LENGTH_SHORT).show();
						Log.e("facebook", error.toString());
					}
				});

		if (ShareDialog.canShow(ShareLinkContent.class)) {

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

			SharePhoto photo = new SharePhoto.Builder()
					.setBitmap(Bitmap.createBitmap(bitmap))
					.setCaption("Challenge me in IntelliBirdBird? I am at Level " + level + " now!")
					.build();

			SharePhotoContent content = new SharePhotoContent.Builder()
					.addPhoto(photo)
					.build();

			shareDialog.show(content);
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}

}
