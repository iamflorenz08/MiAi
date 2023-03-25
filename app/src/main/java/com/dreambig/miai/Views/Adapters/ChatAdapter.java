package com.dreambig.miai.Views.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dreambig.miai.Models.BotMessageModel;
import com.dreambig.miai.Models.ChatModel;
import com.dreambig.miai.Utils.ChatRole;
import com.dreambig.miai.databinding.BotChatLayoutBinding;
import com.dreambig.miai.databinding.ChatTypingLayoutBinding;
import com.dreambig.miai.databinding.UserChatLayoutBinding;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {
    private ArrayList<BotMessageModel> chats = new ArrayList<>();

    @Override
    public int getItemViewType(int position) {
        if(chats.get(position).getRole().equals("user")){
            return ChatRole.USER;
        }
        else if(chats.get(position).getRole().equals("assistant")){
            return ChatRole.ASSISTANT;
        }

        return ChatRole.TYPING;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ChatRole.USER){
            UserChatLayoutBinding binding = UserChatLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new UserViewHolder(binding);
        }
        else if(viewType == ChatRole.ASSISTANT){
            BotChatLayoutBinding binding = BotChatLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new BotViewHolder(binding);
        }

        ChatTypingLayoutBinding binding = ChatTypingLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TypingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BotMessageModel chat = chats.get(position);

        if(chat.getRole().equals("user")){
            UserChatLayoutBinding binding = ((UserViewHolder) holder).binding;
            binding.tvMessage.setText(chat.getContent().trim());
        }
        else if(chat.getRole().equals("assistant")){
            BotChatLayoutBinding binding = ((BotViewHolder) holder).binding;
            binding.tvMessage.setText(chat.getContent().trim());
        }

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public void setChats(ArrayList<BotMessageModel> chats) {
        this.chats.clear();
        this.chats.addAll(chats);
        notifyDataSetChanged();
    }

    public void addChats(BotMessageModel chat){
        this.chats.add(chat);
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

    private class TypingViewHolder extends RecyclerView.ViewHolder{
        private ChatTypingLayoutBinding binding;
        public TypingViewHolder(@NonNull ChatTypingLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
