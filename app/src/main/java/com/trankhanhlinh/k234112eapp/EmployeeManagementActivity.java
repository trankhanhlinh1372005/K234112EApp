package com.trankhanhlinh.k234112eapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class EmployeeManagementActivity extends AppCompatActivity {
    Button btnExits;
    ListView lvEmployee;
    ArrayList<String>ListEmployee;
    ArrayAdapter<String> adapterEmployee;
    EditText edtId,edtName,edtPhone;
    int selectedItemIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_management);
        addViews();
        addEvents();

        loadData();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void loadData(){
        ListEmployee.add("e1-Tèo-0934787975");
        ListEmployee.add("e2-Tý-0934787975");
        ListEmployee.add("e3-Tiến-0934787975");
        ListEmployee.add("e4-Tý-0934787975");
        ListEmployee.add("e5-Tiến-0934787975");
        //nói adapter cập nhật giao diện:
        adapterEmployee.notifyDataSetChanged();
    }

    private void addEvents() {
        btnExits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processExit();
            }
        });
        lvEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                displayEmployeeInfor(i);
            }
        });

        }

    private void   displayEmployeeInfor(int i) {
        selectedItemIndex = i;
        String data=ListEmployee.get(i);
        String[]items=data.split("-");
        //hiển thị items[0]-->id, items[1]->name , items[2]->phone
        edtId.setText(items[0]);
        edtName.setText(items[1]);
        edtPhone.setText(items[2]);
    }


    private void processExit() {
        Dialog custom=new Dialog(this);
        custom.setContentView(R.layout.custome_dialog);
        ImageView imgSave=custom.findViewById(R.id.imgYes);
        ImageView imgCancel=custom.findViewById(R.id.imgCancel);
        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                custom.dismiss();
            }
        });
        custom.show();
    }

    @SuppressLint("WrongViewCast")
    private void addViews() {
        btnExits=findViewById(R.id.btnExits);
        lvEmployee=findViewById(R.id.lvEmployee);
        ListEmployee=new ArrayList<>();
        adapterEmployee=new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                ListEmployee);
        lvEmployee.setAdapter(adapterEmployee);

        edtId=findViewById(R.id.edtId);
        edtName=findViewById(R.id.edtName);
        edtPhone=findViewById(R.id.edtPhone);
    }
    public void saveEmployee(View view){

    }
    public void removeEmployee(View view){

    }


}
