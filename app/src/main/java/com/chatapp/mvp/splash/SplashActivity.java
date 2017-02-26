package com.chatapp.mvp.splash;

import android.content.Intent;
import android.os.Bundle;

import com.chatapp.R;
import com.chatapp.chat.utils.SharedPreferencesUtil;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.mvp.home.HomeActivity;
import com.chatapp.mvp.onboarding.OnboardingActivity;
import com.chatapp.mvp.updatebasicprofile.UpdateBasicProfileActivity;
import com.chatapp.mvp.verify.VerifyActivity;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.Log;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;

public class SplashActivity extends BaseActivity implements SplashMvp.SplashView {

    SplashMvp.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new PresenterImpl(this);
        presenter.getListCommonParams();
    }

    @Override
    public void onGetListCommonParamsSuccess() {
        Intent intent = null;
        if (AccountUtils.isLoggedIn()) {
            LogInModel logInModel = AccountUtils.getLogInModel();
            if (logInModel.isConfirm()) {
                if (!SharedPreferencesUtil.hasQbUser()) {
                    createSession();
                } else {
                    startLoginService(SharedPreferencesUtil.getQbUser());
                }

                intent = new Intent(this, HomeActivity.class);

            } else if (logInModel.isVerified()) {

                intent = new Intent(this, UpdateBasicProfileActivity.class);

            } else if (logInModel.isNotVerify()) {

                intent = new Intent(this, VerifyActivity.class);

            }
        }

        if (intent == null) {
            intent = new Intent(this, OnboardingActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        finish();
    }

    @Override
    public void onGetListCommonFail() {

        Intent intent = new Intent(this, OnboardingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        finish();
    }

    private void createSession() {
        QBAuth.createSession().performAsync(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession result, Bundle params) {
                Log.d("Login success");
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d("Login Failure");
            }
        });
    }
}
