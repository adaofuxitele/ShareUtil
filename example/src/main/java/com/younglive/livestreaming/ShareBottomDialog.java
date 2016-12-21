package com.younglive.livestreaming;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;

import me.shaohui.bottomdialog.BaseBottomDialog;
import me.shaohui.shareutil.ShareUtil;
import me.shaohui.shareutil.share.ShareListener;
import me.shaohui.shareutil.share.SharePlatform;

/**
 * Created by shaohui on 2016/12/10.
 */

public class ShareBottomDialog extends BaseBottomDialog implements View.OnClickListener {

    private ShareListener mShareListener;

    @Override
    public int getLayoutRes() {
        return R.layout.layout_bottom_share;
    }

    @Override
    public void bindView(final View v) {
        v.findViewById(R.id.share_qq).setOnClickListener(this);
        v.findViewById(R.id.share_qzone).setOnClickListener(this);
        v.findViewById(R.id.share_weibo).setOnClickListener(this);
        v.findViewById(R.id.share_wx).setOnClickListener(this);
        v.findViewById(R.id.share_wx_timeline).setOnClickListener(this);
        v.findViewById(R.id.mFaceBook).setOnClickListener(this);

        mShareListener = new ShareListener() {

            @Override
            public void shareSuccess() {
                Toast.makeText(v.getContext(), "分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void shareFailure(Exception e) {
                Log.d("shareFailure", e + "");
                Toast.makeText(v.getContext(), "分享失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void shareCancel() {
                Toast.makeText(v.getContext(), "取消分享", Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_qq:
                ShareUtil.shareImage(getContext(), SharePlatform.QQ,
                        "http://shaohui.me/images/avatar.gif", mShareListener);
                break;
            case R.id.share_qzone:
                ShareUtil.shareMedia(getContext(), SharePlatform.QZONE, "Title", "summary",
                        "http://www.google.com", "http://shaohui.me/images/avatar.gif",
                        mShareListener);
                break;
            case R.id.share_weibo:
//                ShareUtil.shareText(getContext(), SharePlatform.WEIBO,
//                        "http://shaohui.me/images/avatar.gif", mShareListener);
//                ShareUtil.shareMedia(getContext(), SharePlatform.WEIBO, "Title", "summary",
//                        "http://www.google.com", "http://shaohui.me/images/avatar.gif",
//                        mShareListener);
                Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap();
                ShareUtil.shareMedia(getContext(), SharePlatform.WEIBO, "title", "summary", "http://baidu.com", bitmap, mShareListener);
                break;
            case R.id.share_wx_timeline:
                ShareUtil.shareText(getContext(), SharePlatform.WX_TIMELINE, "测试分享文字",
                        mShareListener);
                break;
            case R.id.share_wx:
                ShareUtil.shareMedia(getContext(), SharePlatform.WX, "Title", "summary",
                        "http://www.google.com", "http://shaohui.me/images/avatar.gif",
                        mShareListener);
                break;
            case R.id.mFaceBook:
//                AccessToken.refreshCurrentAccessTokenAsync();
//                Log.i("fackBookClicked", AccessToken.getCurrentAccessToken().getPermissions().contains("publish_actions") + "");
                ShareUtil.shareMedia(getContext(), SharePlatform.FACEBOOK, "My FaceBook Share test", "It is a Good way for this",
                        "http://www.google.com", "http://shaohui.me/images/avatar.gif",
                        mShareListener);
                break;
        }
        dismiss();
    }
}
