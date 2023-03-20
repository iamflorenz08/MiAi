package com.dreambig.miai.Views.ChatFragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreambig.miai.Models.BotResponseModel;
import com.dreambig.miai.Models.ChatModel;
import com.dreambig.miai.R;
import com.dreambig.miai.Utils.ChatRole;
import com.dreambig.miai.Views.Adapters.ChatAdapter;
import com.dreambig.miai.databinding.FragmentChatBinding;

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
        mViewModel.init();
        chatAdapter = new ChatAdapter();


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.rvConversation.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.rvConversation.setAdapter(chatAdapter);
        chatAdapter.addChat(new ChatModel(
                ChatRole.BOT,
                "Hello, how may I assist you today?"
        ));

        mViewModel.getBotResponse().observe(getViewLifecycleOwner(), new Observer<BotResponseModel>() {
            @Override
            public void onChanged(BotResponseModel botResponse) {
                chatAdapter.addChat(new ChatModel(
                        ChatRole.BOT,
                        botResponse.getChoices().get(0).getMessage().getContent()
                ));

                binding.rvConversation.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
            }
        });


        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatAdapter.addChat(new ChatModel(
                        ChatRole.USER,
                        binding.etMessage.getText().toString()
                ));

                mViewModel.askBot(binding.etMessage.getText().toString());
                binding.rvConversation.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
                binding.etMessage.setText(null);
            }
        });
    }



}