package com.example.pinalurisaplikacia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterActivity : AppCompatActivity() {
    private lateinit var loginButton: Button
    private lateinit var regButton: Button
    private lateinit var regEmailEditText: EditText
    private lateinit var regPwdEditText: EditText
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        loginButton = findViewById(R.id.loginButton)
        regButton = findViewById(R.id.regButton)
        regEmailEditText = findViewById(R.id.regEmailEditText)
        regPwdEditText = findViewById(R.id.regPwdEditText)
        auth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }




        regButton.setOnClickListener {
            val email: String = regEmailEditText.text.toString()
            val password: String = regPwdEditText.text.toString()

            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "გთხოვთ შეავსოთ ყველა ველი", Toast.LENGTH_LONG).show()
            } else{
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                    this,
                    OnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser;
                            val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(email).build()

                            user?.updateProfile(profileUpdates)
                                ?.addOnCompleteListener(OnCompleteListener<Void?> { userUpdated ->
                                    if (userUpdated.isSuccessful) {
                                        Toast.makeText(this, "თქვენ წარმატებით დარეგისტრირდით", Toast.LENGTH_LONG)
                                            .show()
                                        val intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                })
                        } else {
                            Toast.makeText(this, "რეგისტრაცია წარუმატებელია", Toast.LENGTH_LONG).show()
                        }
                    })
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}