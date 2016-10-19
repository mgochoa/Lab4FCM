package co.edu.udea.compuvoli.gr03.lab4fcm.Fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CompoundButtonCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import co.edu.udea.compuvoli.gr03.lab4fcm.POJO.user;
import co.edu.udea.compuvoli.gr03.lab4fcm.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsuariosFragment extends Fragment {


    EditText newName;
    Switch swStatus;
    Button btnUpdate;
    CircleImageView ci;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;

    public UsuariosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View thisView= inflater.inflate(R.layout.fragment_usuarios, container, false);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        newName=(EditText)thisView.findViewById(R.id.new_display_name);
        swStatus=(Switch)thisView.findViewById(R.id.switch1);
        btnUpdate=(Button)thisView.findViewById(R.id.btn_update);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        ci=(CircleImageView)thisView.findViewById(R.id.profile_image_user);

        Picasso.with(getContext()).load(mFirebaseUser.getPhotoUrl()).into(ci);

        mFirebaseDatabaseReference.child("users").child(mFirebaseUser.getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        user user = dataSnapshot.getValue(user.class);

                            swStatus.setChecked(user.isState());


                        // ...
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // ...
                    }
                });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!newName.getText().toString().equals("")){
                    mFirebaseDatabaseReference.child("users").child(mFirebaseUser.getUid()).child("username").setValue(newName.getText().toString());

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(newName.getText().toString())
                            .setPhotoUri(mFirebaseUser.getPhotoUrl())
                            .build();

                    mFirebaseUser.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("UsersActivity.java", "User name updated.");
                                    }
                                }
                            });


                    newName.setText("");
                    Snackbar.make(getView(),"Actualizado correctamente",Snackbar.LENGTH_SHORT);

                }


            }
        });
         swStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                     mFirebaseDatabaseReference.child("users").child(mFirebaseUser.getUid()).child("status").setValue(isChecked);

             }
         });

        return thisView;
    }

}
