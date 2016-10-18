package co.edu.udea.compuvoli.gr03.lab4fcm.Fragments;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import co.edu.udea.compuvoli.gr03.lab4fcm.ChatActivity;
import co.edu.udea.compuvoli.gr03.lab4fcm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatList extends Fragment {
    ImageButton ibChatGrupal;
    public ChatList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View thisview= inflater.inflate(R.layout.fragment_chat_list, container, false);

        ibChatGrupal= (ImageButton)thisview.findViewById(R.id.btn_chat_grupal);
        ibChatGrupal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatIntent= new Intent(getActivity(),ChatActivity.class);
                chatIntent.putExtra("NAME","Chat Grupal");
                startActivity(chatIntent);
            }
        });
        return thisview;
    }




}
