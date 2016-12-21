package me.shaohui.shareutil.login.instance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.facebook.CallbackManager;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.List;

import me.shaohui.shareutil.login.LoginListener;
import me.shaohui.shareutil.login.result.BaseToken;
import me.shaohui.shareutil.share.instance.FaceBookShareInstance;

/**
 * Created by pitt on 2016/12/20.
 */

public class FaceBookLoginInstance extends LoginInstance {

    private final CallbackManager mCallbackManager;

    private final List<String> mPermissions = new ArrayList<>();

    public FaceBookLoginInstance() {
        this(null, null, false);
    }

    public FaceBookLoginInstance(Activity activity, LoginListener listener, boolean fetchUserInfo) {
        super(activity, listener, fetchUserInfo);
        mCallbackManager = CallbackManager.Factory.create();
        mPermissions.add("email");
    }

    @Override
    public void doLogin(Activity activity, LoginListener listener, boolean fetchUserInfo) {
        getLoginManager().registerCallback(mCallbackManager, listener);
        getLoginManager().logInWithReadPermissions(activity, mPermissions);
    }

    @Override
    public void fetchUserInfo(BaseToken token) {

    }

    @Override
    public void handleResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
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

    @Override
    public void recycle() {

    }

    private LoginManager getLoginManager() {
        LoginManager manager = LoginManager.getInstance();
        manager.setDefaultAudience(DefaultAudience.FRIENDS);
        manager.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
        return manager;
    }

}
