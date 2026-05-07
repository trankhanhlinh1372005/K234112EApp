package com.trankhanhlinh.k234112eapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


public class CalculatorActivity extends AppCompatActivity {

    EditText edtFormula;
    Button btnDel,btnCalculate;
    TextView txtMC,txtMR,txtMPlus,txtMMinus,txtMS,txtM;

    View.OnClickListener m_onclick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        addViews();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvents() {
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get current data:
                String current_data=edtFormula.getText().toString();
                //remove last character:
                String new_value="";
                if(current_data.length()>1)
                {
                    new_value=current_data.substring(0,current_data.length()-1);
                }
                //set new value:
                edtFormula.setText(new_value);
            }
        });

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //step 1: get data (formular)
                String formular=edtFormula.getText().toString();
                //step 2: invoke library for formular (find internet)...
                String result="";
                //result=library_nào_đó(formular)
                try
                {
                    //step 2: replace special symbols
                    formular=formular.replace("×","*");
                    formular=formular.replace("÷","/");

                    //step 3: build expression
                    Expression expression =
                            new ExpressionBuilder(formular)
                                    .build();

                    //step 4: calculate result
                    double value = expression.evaluate();

                    //step 5: remove .0 if integer

                    if(value == (long)value)
                    {
                        result = String.valueOf((long)value);
                    }
                    else
                    {
                        result = String.valueOf(value);
                    }

                    //step 6: show result
                    edtFormula.setText(result) ;
                }
                catch (Exception ex)
                {
                    edtFormula.setText(getString(R.string.str_error));
                }
            }
        });

        m_onclick=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.equals(txtM))
                {
                    //khách hàng nhấn txtM
                }
                else if (view.equals(txtMMinus))
                {
                    //khách hàng nhấn txtMinus
                }//không dùng dấu == để so sánh vì nó không hiểu so sánh ô nhớ khi dùng ==
            }
        };
        //m_onclick là biến có khả năng sinh sự kiện (variable as listener)
        //thường dùng để sharing sự kiện (từ 2 view trở lên)
        txtM.setOnClickListener(m_onclick);
        txtMMinus.setOnClickListener(m_onclick);
        txtMR.setOnClickListener(m_onclick);
        txtMS.setOnClickListener(m_onclick);
        txtMPlus.setOnClickListener(m_onclick);
        txtMC.setOnClickListener(m_onclick);
    }

    private void addViews() {
        edtFormula=findViewById(R.id.edtFormula);
        btnDel=findViewById(R.id.btnDel);
        btnCalculate=findViewById(R.id.btnCalculate);

        txtMC=findViewById(R.id.txtMC);
        txtMR=findViewById(R.id.txtMR);
        txtMPlus=findViewById(R.id.txtMPlus);
        txtMMinus=findViewById(R.id.txtMMinus);
        txtMS=findViewById(R.id.txtMS);
        txtM=findViewById(R.id.txtM);
    }
    public void processInputData(View view) {
        Button btn_clicked= (Button) view;
        //old value:
        String old_value=edtFormula.getText().toString();
        //input value:
        String input_value=btn_clicked.getText().toString();
        //new value (lasted value):
        String new_value=old_value+input_value;
        //show new value for customer:
        edtFormula.setText(new_value);
    }
    @Override
    protected void onPause() {
        super.onPause();

        //step 1: tạo bộ nhớ tạm
        android.content.SharedPreferences preferences =
                getSharedPreferences("CALCULATOR_DATA", MODE_PRIVATE);

        //step 2: lấy editor để lưu dữ liệu
        android.content.SharedPreferences.Editor editor =
                preferences.edit();

        //step 3: lấy phép tính hiện tại
        String current_formula = edtFormula.getText().toString();

        //step 4: lưu dữ liệu
        editor.putString("FORMULA", current_formula);

        //step 5: xác nhận lưu
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //step 1: mở bộ nhớ đã lưu
        android.content.SharedPreferences preferences =
                getSharedPreferences("CALCULATOR_DATA", MODE_PRIVATE);

        //step 2: lấy dữ liệu đã lưu
        String saved_formula =
                preferences.getString("FORMULA", "");

        //step 3: phục hồi dữ liệu lên EditText
        edtFormula.setText(saved_formula);
    }
}