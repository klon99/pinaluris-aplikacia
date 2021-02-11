package com.example.pinalurisaplikacia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var emailEditText: EditText
    private lateinit var pwdEditText: EditText
    private lateinit var auth: FirebaseAuth
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.login_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.goBackButton) {
            auth.signOut()
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
               override fun onCreate(savedInstanceState: Bundle?) {
                   super.onCreate(savedInstanceState)
                      setContentView(R.layout.activity_login)


                     emailEditText = findViewById(R.id.loginEmailEditText)
                     pwdEditText = findViewById(R.id.loginPwdEditText)
                     loginButton = findViewById(R.id.signInButton)
                      auth = FirebaseAuth.getInstance()



                     loginButton.setOnClickListener {
                            val email: String = emailEditText.text.toString()
                                         val password: String = pwdEditText.text.toString()

                                   if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                            Toast.makeText(this, "გთხოვთ შეავსოთ ყველა ველი", Toast.LENGTH_LONG).show()
                           } else{
                             auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener { task ->
                          if(task.isSuccessful) {
                        Toast.makeText(this, "თქვენ წარმატებით შეხვედით სისტემაში.", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                           }else {
                        Toast.makeText(this, "წარუმატებელი მოქმედება.", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }
}