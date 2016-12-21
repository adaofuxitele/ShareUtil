package me.shaohui.shareutil.share.instance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.facebook.CallbackManager;
import com.facebook.share.internal.ShareFeedContent;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.widget.ShareDialog;

import java.util.List;

import me.shaohui.shareutil.share.ShareImageObject;
import me.shaohui.shareutil.share.ShareListener;

/**
 * Created by pitt on 2016/12/15.
 */

public class FaceBookShareInstance implements ShareInstance {
    private CallbackManager mCallManager;

    public FaceBookShareInstance() {
        mCallManager = CallbackManager.Factory.create();
    }

    //TODO add Share Text
    @Override
    public void shareText(int platform, String text, Activity activity, ShareListener listener) {
    }

    @Override
    public void shareMedia(int platform, String title, String targetUrl, String summary, ShareImageObject shareImageObject, Activity activity, ShareListener listener) {
        final ShareLinkContent faceContent = new ShareLinkContent.Builder()
                .setContentTitle(title)
                .setContentDescription(summary)
                .setContentUrl(Uri.parse(targetUrl))
                .setImageUrl(Uri.parse(shareImageObject.getPathOrUrl()))
                .build();
        final ShareDialog dialog = new ShareDialog(activity);
        if (dialog.canShow(faceContent, ShareDialog.Mode.NATIVE)) {
            dialog.registerCallback(mCallManager, listener);
            dialog.show(faceContent, ShareDialog.Mode.NATIVE);
        }
    }

    //TODO add Share Image
    @Override
    public void shareImage(int platform, ShareImageObject shareImageObject, Activity activity, ShareListener listener) {
        final SharePhoto.Builder photoBuilder = new SharePhoto.Builder();
        if (!TextUtils.isEmpty(shareImageObject.getPathOrUrl())) {
            photoBuilder.setImageUrl(Uri.parse(shareImageObject.getPathOrUrl()));
        }
        if (shareImageObject.getBitmap() != null) {
            photoBuilder.setBitmap(shareImageObject.getBitmap());
        } else if (shareImageObject.getPathOrUrl() != null) {
            photoBuilder.setImageUrl(Uri.parse(shareImageObject.getPathOrUrl()));
        }
        final SharePhoto photo = photoBuilder.build();
        final ShareContent content = new ShareMediaContent.Builder().addMedium(photo).build();
        final ShareDialog dialog = new ShareDialog(activity);
        if (dialog.canShow(content, ShareDialog.Mode.NATIVE)) {
            dialog.show(content, ShareDialog.Mode.NATIVE);
        }
    }

    @Override
    public void handleResult(Intent data) {

    }

    @Override
    public boolean isInstall(Context context) {
        PackageManager pm = context.getPackageManager();
        if (pm == null) {
            return false;
        }

        List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
        for (PackageInfo info : packageInfos) {
            if (TextUtils.equals(info.packageName.toLowerCase(), "com.facebook.katana")) {
                return true;
            }
        }
        return false;
    }

    public void handleResult(final int requestCode,
                             final int resultCode, final Intent data) {
        mCallManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void recycle() {

    }
}
