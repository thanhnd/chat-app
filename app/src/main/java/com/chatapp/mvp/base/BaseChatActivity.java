package com.chatapp.mvp.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.chatapp.R;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PEOPLE) {
                ArrayList<QBUser> selectedUsers = (ArrayList<QBUser>) data
                        .getSerializableExtra(com.chatapp.chat.ui.activity.SelectUsersActivity.EXTRA_QB_USERS);

//                if (isPrivateDialogExist(selectedUsers)){
//                    selectedUsers.remove(ChatHelper.getCurrentUser());
//                    QBChatDialog existingPrivateDialog = QbDialogHolder.getInstance().getPrivateDialogWithUser(selectedUsers.get(0));
//                    isProcessingResultInProgress = false;
//                    com.chatapp.chat.ui.activity.ChatActivity.startForResult(DialogsActivity.this, REQUEST_DIALOG_ID_FOR_UPDATE, existingPrivateDialog.getDialogId());
//                } else {
//                    ProgressDialogFragment.show(getSupportFragmentManager(), R.string.create_chat);
//                    createDialog(selectedUsers);
//                }
            } else if (requestCode == REQUEST_DIALOG_ID_FOR_UPDATE) {
//                if (data != null) {
//                    String dialogId = data.getStringExtra(com.chatapp.chat.ui.activity.ChatActivity.EXTRA_DIALOG_ID);
//                    loadUpdatedDialog(dialogId);
//                } else {
//                    isProcessingResultInProgress = false;
//                    updateDialogsList();
//                }
            }
        }
//        else {
//            updateDialogsAdapter();
//        }
    }

    protected void createDialog(final QBUser user) {
        ArrayList<QBUser> users = new ArrayList<>();
        users.add(user);
        users.add(ChatHelper.getCurrentUser());
        createDialog(users);
    }

    protected void createDialog(final ArrayList<QBUser> selectedUsers) {
        ChatHelper.getInstance().createDialogWithSelectedUsers(selectedUsers,
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
}
