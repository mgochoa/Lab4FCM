package co.edu.udea.compuvoli.gr03.lab4fcm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.edu.udea.compuvoli.gr03.lab4fcm.POJO.user;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 20;
    Button loginBtn;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String TAG = "LoginActivity.java";
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn=(Button)findViewById(R.id.login_btn);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mFirebaseAuth = FirebaseAuth.getInstance();
        if (mFirebaseAuth.getCurrentUser() != null) {

            mFirebaseUser = mFirebaseAuth.getCurrentUser();
            writeUser(mFirebaseUser.getUid(),mFirebaseUser.getDisplayName(),mFirebaseUser.getEmail(),
                    String.valueOf(mFirebaseUser.getPhotoUrl()),true);
            Intent ingresoIntent = new Intent(LoginActivity.this, MenuActivity.class);
            this.startActivity(ingresoIntent);
        }else{
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setProviders(
                                    //AuthUI.EMAIL_PROVIDER,
                                    AuthUI.GOOGLE_PROVIDER)
                            .build(),
                    RC_SIGN_IN);

        }
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setProviders(
                                        //AuthUI.EMAIL_PROVIDER,
                                        AuthUI.GOOGLE_PROVIDER)
                                .build(),
                        RC_SIGN_IN);
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }




    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // user is signed in!
                mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser.getUid()!=null){
                writeUser(mFirebaseUser.getUid(),mFirebaseUser.getDisplayName(),mFirebaseUser.getEmail(),
                        String.valueOf(mFirebaseUser.getPhotoUrl()),true);
                }
                startActivity(new Intent(this, MenuActivity.class));

            } else {
                Toast.makeText(LoginActivity.this, "Try again later",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void writeUser(String uid, String username, String email,String photoUrl,boolean state) {
        user user = new user(uid,username,email,photoUrl,state);

        mFirebaseDatabaseReference.child("users").child(uid).setValue(user);
    }


}
