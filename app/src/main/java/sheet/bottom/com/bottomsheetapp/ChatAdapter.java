package sheet.bottom.com.bottomsheetapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sheet.bottom.com.networklib.models.chat.ChatItem;

/**
 * Created by labattula on 23/09/16.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChatItem> chatItemList = null;
    private Context mContext;

    public ChatAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType) {
            case 0:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.chat_item_layout, parent, false);
                return new ChatItemViewHolderSelf(itemView);
            case 1:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.chat_item_layout_ai, parent, false);
                return new ChatItemViewHolderAI(itemView);
        }
        return null;
    }

    public void setChatItemList(List<ChatItem> stackItems) {
        this.chatItemList = stackItems;
    }

    @Override
    public int getItemViewType(int position) {
        ChatItem item = chatItemList.get(position);
        if (item.isAiReply()) {
            return 1;
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatItem chatItem = chatItemList.get(position);
        if (getItemViewType(position) == 1) {
            ((ChatItemViewHolderAI) holder).title.setText("AI.." + chatItem.getChatText());
        } else {
            ((ChatItemViewHolderSelf) holder).title.setText(chatItem.getChatText());
        }
    }

    @Override
    public int getItemCount() {
        return chatItemList.size();
    }

    public class ChatItemViewHolderSelf extends RecyclerView.ViewHolder {

        TextView title;

        public ChatItemViewHolderSelf(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.chat_title_self);
        }
    }


    public class ChatItemViewHolderAI extends RecyclerView.ViewHolder {

        TextView title;

        public ChatItemViewHolderAI(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.chat_title_ai);
        }
    }
}
