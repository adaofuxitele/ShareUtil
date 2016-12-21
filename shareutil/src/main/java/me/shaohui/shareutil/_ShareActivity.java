package me.shaohui.shareutil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import me.shaohui.shareutil.share.SharePlatform;

/**
 * Created by shaohui on 2016/11/19.
 */

public class _ShareActivity extends Activity {

    private int mType;
    private int mPlatformType;

    private boolean isNew;

    private static final String TYPE = "share_activity_type";
    private static final String PLATFORM_TYPE = "share_activity_platform_type";

    public static Intent newInstance(Context context, int type, int platformType) {
        Intent intent = new Intent(context, _ShareActivity.class);
        intent.putExtra(TYPE, type);
        intent.putExtra(PLATFORM_TYPE, platformType);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShareLog.i("ShareActivity onCreate");
        isNew = true;

        // init data
        mType = getIntent().getIntExtra(TYPE, 0);
        mPlatformType = getIntent().getIntExtra(PLATFORM_TYPE, 0);
        if (mType == ShareUtil.TYPE) {
            // 分享
            ShareUtil.action(this);
        } else if (mType == LoginUtil.TYPE) {
            // 登录
            LoginUtil.action(this);
        } else {
            // handle 微信回调
            LoginUtil.handleResult(-1, -1, getIntent());
            ShareUtil.handleResult(getIntent());
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ShareLog.i("ShareActivity onResume");
        if (isNew) {
            isNew = false;
        } else {
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ShareLog.i("ShareActivity onNewIntent");
        // 处理回调
        if (mType == LoginUtil.TYPE) {
            LoginUtil.handleResult(0, 0, intent);
        } else if (mType == ShareUtil.TYPE) {
            ShareUtil.handleResult(intent);
        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ShareLog.i("ShareActivity onActivityResult");
        Log.i("onActivityResult", "requestCode:" + requestCode + "  resultCode" + resultCode + "  data" + data);
        // 处理回调
        if (mType == LoginUtil.TYPE) {
            LoginUtil.handleResult(requestCode, resultCode, data);
        } else if (mType == ShareUtil.TYPE && mPlatformType == SharePlatform.FACEBOOK) {
            ShareUtil.handleResult(requestCode, resultCode, data);
        } else if (mType == ShareUtil.TYPE) {
            ShareUtil.handleResult(data);
        }
        finish();
    }
}
