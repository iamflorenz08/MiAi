package com.dreambig.miai.Views.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dreambig.miai.Models.ChatModel;
import com.dreambig.miai.Utils.ChatRole;
import com.dreambig.miai.databinding.BotChatLayoutBinding;
import com.dreambig.miai.databinding.UserChatLayoutBinding;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {
    private ArrayList<ChatModel> chats = new ArrayList<>();

    @Override
    public int getItemViewType(int position) {
        if(chats.get(position).getType() == ChatRole.USER){
            return ChatRole.USER;
        }
        return ChatRole.BOT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ChatRole.USER){
            UserChatLayoutBinding binding = UserChatLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new UserViewHolder(binding);
        }

        BotChatLayoutBinding binding = BotChatLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BotViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatModel chat = chats.get(position);

        if(chat.getType() == ChatRole.USER){
            UserChatLayoutBinding binding = ((UserViewHolder) holder).binding;
            binding.tvMessage.setText(chat.getMessage().trim());
        }
        else{
            BotChatLayoutBinding binding = ((BotViewHolder) holder).binding;
            binding.tvMessage.setText(chat.getMessage().trim());
        }

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public void addChat(ChatModel chat){
        chats.add(chat);
        notifyDataSetChanged();
    }

    private class UserViewHolder extends RecyclerView.ViewHolder{
        private UserChatLayoutBinding binding;
        public UserViewHolder(@NonNull UserChatLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private class BotViewHolder extends RecyclerView.ViewHolder{
        private BotChatLayoutBinding binding;
        public BotViewHolder(@NonNull BotChatLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
