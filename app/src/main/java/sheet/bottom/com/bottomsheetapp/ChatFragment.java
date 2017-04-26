package sheet.bottom.com.bottomsheetapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sheet.bottom.com.networklib.models.chat.ChatItem;

/**
 * Created by labattula on 07/04/17.
 */

public class ChatFragment extends Fragment {

    private static ChatFragment chatFragment = null;
    private TextView mTextView;
    private ProgressBar mProgressBar;
    private RecyclerView chatRecycler;

    private List<ChatItem> chatList = null;
    private ChatAdapter mAdapter;

    public static ChatFragment newInstance() {
        if (chatFragment == null) {
            chatFragment = new ChatFragment();
        }
        return chatFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_page, container, false);

        mTextView = (TextView) view.findViewById(R.id.fragment_chat_txt);
        mTextView.setText("chat Fragment # ");
        mProgressBar = (ProgressBar) view.findViewById(R.id.charProgressBar);
        chatRecycler = (RecyclerView) view.findViewById(R.id.chatItemsListView);
        fillListView();
        setListView();
        return view;
    }

    private void fillListView() {
        chatList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            ChatItem chatItem = new ChatItem();
            chatItem.setChatText("Value added.." + i);
            chatItem.setChatTime(Calendar.getInstance().getTime() + "");
            if (i % 3 == 0) {
                chatItem.setAiReply(true);
            }
            chatList.add(chatItem);
        }
    }

    private void setListView() {
        mAdapter = new ChatAdapter(getActivity());
        mAdapter.setChatItemList(chatList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        chatRecycler.setLayoutManager(mLayoutManager);
        chatRecycler.setItemAnimator(new DefaultItemAnimator());
        chatRecycler.setAdapter(mAdapter);
        chatRecycler.scrollToPosition(mAdapter.getItemCount() - 1);
    }


}
