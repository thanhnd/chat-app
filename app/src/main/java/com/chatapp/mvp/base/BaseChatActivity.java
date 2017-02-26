package com.chatapp.mvp.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.chatapp.R;
import com.chatapp.chat.ui.activity.PermissionsActivity;
import com.chatapp.chat.utils.Consts;
import com.chatapp.chat.utils.PermissionsChecker;
import com.chatapp.chat.utils.SharedPreferencesUtil;
import com.chatapp.chat.utils.chat.ChatHelper;
import com.chatapp.chat.utils.qb.QbAuthUtils;
import com.chatapp.chat.utils.qb.QbSessionStateCallback;
import com.chatapp.utils.Log;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.sample.core.ui.dialog.ProgressDialogFragment;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;

public abstract class BaseChatActivity extends BaseActivity implements QbSessionStateCallback {

    public static final int REQUEST_SELECT_PEOPLE = 174;
    public static final int REQUEST_DIALOG_ID_FOR_UPDATE = 165;

    private static final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    protected boolean isAppSessionActive;
    private boolean isProcessingResultInProgress;
    protected PermissionsChecker checker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean wasAppRestored = savedInstanceState != null;
        boolean isQbSessionActive = QbAuthUtils.isSessionActive();
        final boolean needToRestoreSession = wasAppRestored || !isQbSessionActive;
        Log.d("wasAppRestored = " + wasAppRestored);
        Log.d("isQbSessionActive = " + isQbSessionActive);

        // Triggering callback via Handler#post() method
        // to let child's code in onCreate() to execute first
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

        checker = new PermissionsChecker(getApplicationContext());
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
        ProgressDialogFragment.show(getSupportFragmentManager(), R.string.dlg_restoring_chat_session);

        ChatHelper.getInstance().login(user, new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void result, Bundle bundle) {
                Log.d("Chat login onSuccess()");
                isAppSessionActive = true;
                onSessionCreated(true);

                ProgressDialogFragment.hide(getSupportFragmentManager());
            }

            @Override
            public void onError(QBResponseException e) {
                isAppSessionActive = false;
                ProgressDialogFragment.hide(getSupportFragmentManager());
                Log.w("Chat login onError(): " + e);
                onSessionCreated(false);
            }
        });
    }

    protected void createDialog(String photo, final QBUser user) {
        ArrayList<QBUser> users = new ArrayList<>();
        users.add(user);
        users.add(ChatHelper.getCurrentUser());
        createDialog(photo, users);
    }

    protected void createDialog(final String photo, final ArrayList<QBUser> selectedUsers) {
        ChatHelper.getInstance().createDialogWithSelectedUsers(photo, selectedUsers,
                new QBEntityCallback<QBChatDialog>() {
                    @Override
                    public void onSuccess(QBChatDialog dialog, Bundle args) {
                        isProcessingResultInProgress = false;
                        com.chatapp.chat.ui.activity.ChatActivity.startForResult(BaseChatActivity.this,
                                REQUEST_DIALOG_ID_FOR_UPDATE, dialog.getDialogId());
                        ProgressDialogFragment.hide(getSupportFragmentManager());
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        isProcessingResultInProgress = false;
                        ProgressDialogFragment.hide(getSupportFragmentManager());
                    }
                }
        );
    }

    protected void startPermissionsActivity(boolean checkOnlyAudio) {
        PermissionsActivity.startActivity(this, checkOnlyAudio, Consts.PERMISSIONS);
    }
}
