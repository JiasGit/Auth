package dream.lab.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etEmail, etPassword, etName, etAge;
    private TextView btnRegister;
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.editTextEmail);
        etPassword = findViewById(R.id.editTextPassword);
        etName = findViewById(R.id.editTextName);
        etAge = findViewById(R.id.editTextAge);

        btnRegister = findViewById(R.id.BtnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String name = etName.getText().toString().trim();
                String age = etAge.getText().toString().trim();

                if(email.isEmpty()){
                    etEmail.setError("Email is required!");
                    etEmail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    etEmail.setError("Invalid Email!");
                    etEmail.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    etPassword.setError("Password is required!");
                    return;
                }
                if(name.isEmpty()){
                    etName.setError("Name is required!");
                    return;
                }
                if(age.isEmpty()){
                    etAge.setError("Age is required");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    Toast.makeText(MainActivity.this,"Started", Toast.LENGTH_LONG).show();

                                    User user = new User(email, name, age);

                                    /*
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(MainActivity.this, "Registered!", Toast.LENGTH_LONG).show();
                                                    }
                                                    else{
                                                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });

                                     */

                                    db = FirebaseDatabase.getInstance();
                                    reference = db.getReference("Users");
                                    reference.child(name).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            etEmail.setText("");
                                            etName.setText("");
                                            etAge.setText("");
                                            Toast.makeText(MainActivity.this, "Registered!", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }else{
                                    Toast.makeText(MainActivity.this, "Not successful", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });



    }


}