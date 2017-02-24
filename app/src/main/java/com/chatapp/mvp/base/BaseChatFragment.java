package com.chatapp.mvp.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.chatapp.chat.managers.DialogsManager;
import com.chatapp.chat.utils.SharedPreferencesUtil;
import com.chatapp.chat.utils.chat.ChatHelper;
import com.chatapp.chat.utils.qb.QbAuthUtils;
import com.chatapp.chat.utils.qb.QbSessionStateCallback;
import com.chatapp.utils.Log;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.model.QBUser;

/**
 * Created by thanhnguyen on 1/6/17.
 */
public abstract class BaseChatFragment extends BaseFragment  implements QbSessionStateCallback {

    private static final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    protected boolean isAppSessionActive;
    protected boolean isProcessingResultInProgress;

    protected DialogsManager dialogsManager;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialogsManager = new DialogsManager();

        boolean wasAppRestored = savedInstanceState != null;
        boolean isQbSessionActive = QbAuthUtils.isSessionActive();
        final boolean needToRestoreSession = wasAppRestored || !isQbSessionActive;

        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (needToRestoreSession) {
                    recreateChatSession();
                    isAppSessionActive = false;
                } else {
                    onSessionCreated(true);
                    isAppSessionActive = true;
                }
            }
        });


    }

    private void recreateChatSession() {
        Log.d("Need to recreate chat session");

        QBUser user = SharedPreferencesUtil.getQbUser();
        if (user == null) {
            throw new RuntimeException("User is null, can't restore session");
        }

        reloginToChat(user);
    }

    private void reloginToChat(final QBUser user) {

        ChatHelper.getInstance().login(user, new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void result, Bundle bundle) {
                Log.d("Chat login onSuccess()");
                isAppSessionActive = true;
                onSessionCreated(true);
            }

            @Override
            public void onError(QBResponseException e) {
                isAppSessionActive = false;
                Log.w("Chat login onError(): " + e);
                onSessionCreated(false);
            }
        });
    }
}
