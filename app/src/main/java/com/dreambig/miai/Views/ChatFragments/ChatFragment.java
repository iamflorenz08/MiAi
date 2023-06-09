package com.dreambig.miai.Views.ChatFragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreambig.miai.Models.BotMessageModel;
import com.dreambig.miai.Models.BotResponseModel;
import com.dreambig.miai.Models.ChatModel;
import com.dreambig.miai.R;
import com.dreambig.miai.Utils.ChatRole;
import com.dreambig.miai.Views.Adapters.ChatAdapter;
import com.dreambig.miai.databinding.FragmentChatBinding;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChatFragment extends Fragment {

    private ChatViewModel mViewModel;
    private FragmentChatBinding binding;
    private ChatAdapter chatAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);

        chatAdapter = new ChatAdapter();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.init("Hello! How may I help you?");

        binding.rvConversation.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.rvConversation.setAdapter(chatAdapter);

        mViewModel.getBotResponse().observe(getViewLifecycleOwner(), new Observer<ArrayList<BotMessageModel>>() {
            @Override
            public void onChanged(ArrayList<BotMessageModel> botMessageModels) {
                chatAdapter.setChats(botMessageModels);
                binding.rvConversation.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
            }
        });

        mViewModel.getIsTyping().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isTyping) {
                if (isTyping){
                    chatAdapter.addChats(
                            new BotMessageModel("typing", null)
                    );
                    binding.rvConversation.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
                }
            }
        });

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.etMessage.getText().toString().isEmpty()){
                    mViewModel.askBot(binding.etMessage.getText().toString());
                    binding.etMessage.setText(null);
                    binding.etMessage.clearFocus();
                }
            }
        });

    }



}