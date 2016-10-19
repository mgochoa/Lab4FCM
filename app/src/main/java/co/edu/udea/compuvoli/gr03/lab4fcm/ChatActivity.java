package co.edu.udea.compuvoli.gr03.lab4fcm;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import co.edu.udea.compuvoli.gr03.lab4fcm.POJO.ChatModel;
import co.edu.udea.compuvoli.gr03.lab4fcm.utils.Cons;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    private String CHAT_GLOBAL="ChatGlobal";
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private  RecyclerView recycler;
    EditText mMessageEditText;
    ImageButton mSendButton;
    String mUsername,mPhotoUrl;
    ScrollView scroll;

    FirebaseRecyclerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.setTitle(extras.getString(Cons.NAME,"Chat"));
            //The key argument here must match that used in the other activity
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser != null) {
            mUsername = mFirebaseUser.getDisplayName();
            mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
        } else {
          mUsername="ANONIMO";
            mPhotoUrl=null;
        }

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        recycler = (RecyclerView) findViewById(R.id.rv_chat);
        recycler.setHasFixedSize(true);
        final LinearLayoutManager llm= new LinearLayoutManager(this);
        llm.setStackFromEnd(true);


        recycler.setLayoutManager(llm);
        


        mAdapter = new FirebaseRecyclerAdapter<ChatModel, MessageViewHolder>(ChatModel.class, R.layout.item, MessageViewHolder.class, mFirebaseDatabaseReference.child(CHAT_GLOBAL)) {

            @Override
            public void populateViewHolder(MessageViewHolder chatMessageViewHolder, ChatModel chatMessage, int position) {
                chatMessageViewHolder.nameTv.setText(chatMessage.getName());
                chatMessageViewHolder.messageTv.setText(chatMessage.getText());
                if(chatMessage.getPhotoUrl()!=null){
                    Picasso.with(getBaseContext()).load(chatMessage.getPhotoUrl()).into(chatMessageViewHolder.messengerImageView);

                }
                else{
                    Picasso.with(getBaseContext()).load(R.drawable.group).into(chatMessageViewHolder.messengerImageView);
                }

            }
        };
        recycler.setAdapter(mAdapter);
        llm.scrollToPosition(mAdapter.getItemCount()-1);
        llm.scrollToPositionWithOffset(mAdapter.getItemCount()-1,0);
        recycler.scrollToPosition(mAdapter.getItemCount()-1);
        mMessageEditText=(EditText)findViewById(R.id.send_box);
        mSendButton = (ImageButton) findViewById(R.id.send_button);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mMessageEditText.getText().toString().equals("")){
                ChatModel friendlyMessage = new ChatModel(mMessageEditText.getText().toString(), mUsername,mPhotoUrl);
                mFirebaseDatabaseReference.child(CHAT_GLOBAL).push().setValue(friendlyMessage);
                mMessageEditText.setText("");
                }else{
                    Snackbar.make(view, "Total"+String.valueOf(mAdapter.getItemCount()), Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }
        });
    }


    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTv;
        public TextView messageTv;
        public CircleImageView messengerImageView;

        public MessageViewHolder(View v) {
            super(v);
            nameTv = (TextView) itemView.findViewById(R.id.name);
            messageTv = (TextView) itemView.findViewById(R.id.message);
            messengerImageView = (CircleImageView) itemView.findViewById(R.id.photo);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }
}
