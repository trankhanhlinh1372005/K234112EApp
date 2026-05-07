package com.trankhanhlinh.k234112eapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    /*
    Declare all variables for interactive views
     */
    EditText editUsername ;
    EditText editPassword;
    TextView txtMessage;
    CheckBox chkSaveLogin;
    RadioButton radAdmin;
    RadioButton radEmployee;

    String name_share_pref="LoginInfo";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        addViews();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addViews(){
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        txtMessage = findViewById(R.id.txtMessage);
        chkSaveLogin= findViewById(R.id.chkSaveLogin);
        radAdmin=findViewById(R.id.radAdmin);
        radEmployee=findViewById(R.id.radEmployee);
    }
    public void loginSystem(View view) {
        String username= editUsername.getText().toString();
        String password= editPassword.getText().toString();
        if(username.equalsIgnoreCase("admin")
                && password.equals("123"))
        {
            boolean saved=chkSaveLogin.isChecked();
            SharedPreferences preferences=getSharedPreferences(name_share_pref,MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("Username", username);
            editor.putString("Password", password);
            editor.putBoolean("Saved",saved);
            editor.commit();

            txtMessage.setText(getString(R.string.str_login_successful));
            if(radAdmin.isChecked()){
                //dĩ nhiên ta phải kiểm tra account này có quyền admin hay ko (tính sau)
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(LoginActivity.this, EmployeeManagementActivity.class);
                startActivity(intent);
            }
        }
        else
        {
            txtMessage.setText(getString(R.string.str_login_failed));
        }
    }

    public void Exit(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences=getSharedPreferences(name_share_pref,MODE_PRIVATE);
        String username= preferences.getString("Username","");
        String password= preferences.getString("Password","");
        boolean saved= preferences.getBoolean("Saved",false);
        if(saved)
        {
            editUsername.setText(username);
            editPassword.setText(password);
        }
        chkSaveLogin.setChecked(saved);
    }
}