package com.chatapp.chat.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.chatapp.R;
import com.chatapp.chat.managers.DialogsManager;
import com.chatapp.chat.ui.adapter.DialogsAdapter;
import com.chatapp.chat.utils.chat.ChatHelper;
import com.chatapp.chat.utils.qb.QbChatDialogMessageListenerImp;
import com.chatapp.chat.utils.qb.QbDialogHolder;
import com.chatapp.mvp.base.BaseChatFragment;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBIncomingMessagesManager;
import com.quickblox.chat.QBSystemMessagesManager;
import com.quickblox.chat.exception.QBChatException;
import com.quickblox.chat.listeners.QBChatDialogMessageListener;
import com.quickblox.chat.listeners.QBSystemMessageListener;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.sample.core.gcm.GooglePlayServicesHelper;
import com.quickblox.sample.core.utils.constant.GcmConsts;

import java.util.ArrayList;

import butterknife.ButterKnife;

import static com.chatapp.mvp.base.BaseChatActivity.REQUEST_DIALOG_ID_FOR_UPDATE;


public class DialogsFragment extends BaseChatFragment implements DialogsManager.ManagingDialogsCallbacks {
    private static final String TAG = DialogsFragment.class.getSimpleName();


    private ProgressBar progressBar;
    private FloatingActionButton fab;
    private ActionMode currentActionMode;
    private SwipyRefreshLayout setOnRefreshListener;
    private QBRequestGetBuilder requestBuilder;
    private int skipRecords = 0;

    private BroadcastReceiver pushBroadcastReceiver;
    private GooglePlayServicesHelper googlePlayServicesHelper;
    private DialogsAdapter dialogsAdapter;
    private QBChatDialogMessageListener allDialogsMessagesListener;
    private SystemMessagesListener systemMessagesListener;
    private QBSystemMessagesManager systemMessagesManager;
    private QBIncomingMessagesManager incomingMessagesManager;


    public static void start(Context context) {
        Intent intent = new Intent(context, DialogsFragment.class);
        context.startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialogs, container, false);

        ButterKnife.bind(this, view);

        initUi(view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pushBroadcastReceiver = new PushBroadcastReceiver();
        if (isAppSessionActive) {
            allDialogsMessagesListener = new AllDialogsMessageListener();
            systemMessagesListener = new SystemMessagesListener();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(pushBroadcastReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(pushBroadcastReceiver,
                new IntentFilter(GcmConsts.ACTION_NEW_GCM_EVENT));

        updateDialogsList();
    }

    private void updateDialogsList() {
        if (isAppSessionActive) {
            requestBuilder.setSkip(skipRecords = 0);

            loadDialogsFromQb(true, true);
        }
    }

    private void initUi(View view) {
        ListView dialogsListView = (ListView) view.findViewById(R.id.list_dialogs_chats);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_dialogs);
        setOnRefreshListener = (SwipyRefreshLayout) view.findViewById(R.id.swipy_refresh_layout);

        dialogsAdapter = new DialogsAdapter(getContext(), new ArrayList<>(QbDialogHolder.getInstance().getDialogs().values()));

        dialogsListView.setAdapter(dialogsAdapter);

        dialogsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                QBChatDialog selectedDialog = (QBChatDialog) parent.getItemAtPosition(position);
                if (currentActionMode == null) {
                    com.chatapp.chat.ui.activity.ChatActivity.startForResult(getActivity(), REQUEST_DIALOG_ID_FOR_UPDATE, selectedDialog.getDialogId());
                } else {
                    dialogsAdapter.toggleSelection(selectedDialog);
                }
            }
        });
        dialogsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                QBChatDialog selectedDialog = (QBChatDialog) parent.getItemAtPosition(position);
                dialogsAdapter.selectItem(selectedDialog);
                return true;
            }
        });
        requestBuilder = new QBRequestGetBuilder();

        setOnRefreshListener.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                requestBuilder.setSkip(skipRecords += ChatHelper.DIALOG_ITEMS_PER_PAGE);
                loadDialogsFromQb(true, false);
            }
        });
    }

    private void loadDialogsFromQb(final boolean silentUpdate, final boolean clearDialogHolder) {
        isProcessingResultInProgress = true;
        if (!silentUpdate) {
            progressBar.setVisibility(View.VISIBLE);
        }

        ChatHelper.getInstance().getDialogs(requestBuilder, new QBEntityCallback<ArrayList<QBChatDialog>>() {
            @Override
            public void onSuccess(ArrayList<QBChatDialog> dialogs, Bundle bundle) {
                isProcessingResultInProgress = false;
                progressBar.setVisibility(View.GONE);
                setOnRefreshListener.setRefreshing(false);

                if (clearDialogHolder) {
                    QbDialogHolder.getInstance().clear();
                }
                QbDialogHolder.getInstance().addDialogs(dialogs);
                updateDialogsAdapter();
            }

            @Override
            public void onError(QBResponseException e) {
                isProcessingResultInProgress = false;
                progressBar.setVisibility(View.GONE);
                setOnRefreshListener.setRefreshing(false);
            }
        });
    }

    private void updateDialogsAdapter() {
        dialogsAdapter.updateList(new ArrayList<>(QbDialogHolder.getInstance().getDialogs().values()));
    }

    @Override
    public void onDialogCreated(QBChatDialog chatDialog) {
        updateDialogsAdapter();
    }

    @Override
    public void onDialogUpdated(String chatDialog) {
        updateDialogsAdapter();
    }

    @Override
    public void onNewDialogLoaded(QBChatDialog chatDialog) {
        updateDialogsAdapter();
    }

    @Override
    public void onSessionCreated(boolean success) {
        if (success) {
            registerQbChatListeners();
            if (QbDialogHolder.getInstance().getDialogs().size() > 0) {
                loadDialogsFromQb(true, true);
            } else {
                loadDialogsFromQb(false, true);
            }
        }
    }

    private void registerQbChatListeners() {
        incomingMessagesManager = QBChatService.getInstance().getIncomingMessagesManager();
        systemMessagesManager = QBChatService.getInstance().getSystemMessagesManager();

        if (incomingMessagesManager != null) {
            incomingMessagesManager.addDialogMessageListener(allDialogsMessagesListener != null
                    ? allDialogsMessagesListener : new AllDialogsMessageListener());
        }

        if (systemMessagesManager != null) {
            systemMessagesManager.addSystemMessageListener(systemMessagesListener != null
                    ? systemMessagesListener : new SystemMessagesListener());
        }

        dialogsManager.addManagingDialogsCallbackListener(this);
    }

    private class SystemMessagesListener implements QBSystemMessageListener {
        @Override
        public void processMessage(final QBChatMessage qbChatMessage) {
            dialogsManager.onSystemMessageReceived(qbChatMessage);
        }

        @Override
        public void processError(QBChatException e, QBChatMessage qbChatMessage) {

        }
    }

    private class AllDialogsMessageListener extends QbChatDialogMessageListenerImp {
        @Override
        public void processMessage(final String dialogId, final QBChatMessage qbChatMessage, Integer senderId) {
            if (!senderId.equals(ChatHelper.getCurrentUser().getId())) {
                dialogsManager.onGlobalMessageReceived(dialogId, qbChatMessage);
            }
        }
    }

    private class PushBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra(GcmConsts.EXTRA_GCM_MESSAGE);
            Log.v(TAG, "Received broadcast " + intent.getAction() + " with data: " + message);
            requestBuilder.setSkip(skipRecords = 0);
            loadDialogsFromQb(true, true);
        }
    }

}
