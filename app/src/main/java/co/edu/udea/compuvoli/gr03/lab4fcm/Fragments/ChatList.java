package co.edu.udea.compuvoli.gr03.lab4fcm.Fragments;


import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import co.edu.udea.compuvoli.gr03.lab4fcm.ChatActivity;
import co.edu.udea.compuvoli.gr03.lab4fcm.POJO.ChatModel;
import co.edu.udea.compuvoli.gr03.lab4fcm.POJO.user;
import co.edu.udea.compuvoli.gr03.lab4fcm.R;
import co.edu.udea.compuvoli.gr03.lab4fcm.utils.Cons;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatList extends Fragment {
    ImageButton ibChatGrupal;
    FirebaseRecyclerAdapter mAdapter;
    String USER="users";
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;


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
                startActivity(chatIntent);
            }
        });
        DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirebaseUser=mFirebaseAuth.getCurrentUser();
        RecyclerView recycler = (RecyclerView) thisview.findViewById(R.id.rv_privados);
        recycler.setHasFixedSize(true);
        final LinearLayoutManager llm= new LinearLayoutManager(getContext());
        recycler.setLayoutManager(llm);


        mAdapter = new FirebaseRecyclerAdapter<user, UserViewHolder>(user.class, R.layout.user, UserViewHolder.class, mFirebaseDatabaseReference.child(USER).orderByChild("uid")) {
            int myposition;
            @Override
            public void populateViewHolder(UserViewHolder chatMessageViewHolder, final user chatMessage, int position) {


                chatMessageViewHolder.nameTv.setText(chatMessage.getUsername());
                if(chatMessage.isState()){
                    chatMessageViewHolder.statusTv.setText(R.string.conectado);
                    chatMessageViewHolder.statusTv.setTextColor(Color.parseColor("#4CAF50"));

                }else{
                    chatMessageViewHolder.statusTv.setText(R.string.desconectado);
                    chatMessageViewHolder.statusTv.setTextColor(Color.parseColor("#607D8B"));
                }
                if(chatMessage.getPhotoUrl()!=null){
                    Picasso.with(getContext()).load(chatMessage.getPhotoUrl()).into(chatMessageViewHolder.messengerImageView);

                }
                else{
                    Picasso.with(getContext()).load(R.drawable.group).into(chatMessageViewHolder.messengerImageView);
                }
                chatMessageViewHolder.ib.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent chatIntent = new Intent(getActivity(),ChatActivity.class);
                        chatIntent.putExtra(Cons.NAME,chatMessage.getUsername());
                        chatIntent.putExtra(Cons.MYUID,mFirebaseUser.getUid());
                        chatIntent.putExtra(Cons.RECIEVERUID,chatMessage.getUid());
                        startActivity(chatIntent);
                    }
                });

                if(mFirebaseUser.getUid().equals(chatMessage.getUid())){
                    myposition=position;

                }


            }




        };
        recycler.setAdapter(mAdapter);






        return thisview;
    }
    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTv;
        public CircleImageView messengerImageView;
        public ImageButton ib;
        public TextView statusTv;

        public UserViewHolder(View v) {
            super(v);
            nameTv = (TextView) itemView.findViewById(R.id.username_text);
            messengerImageView = (CircleImageView) itemView.findViewById(R.id.profile_image);
            ib=(ImageButton)itemView.findViewById(R.id.btn_chat);
            statusTv=(TextView)itemView.findViewById(R.id.status);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }



}
