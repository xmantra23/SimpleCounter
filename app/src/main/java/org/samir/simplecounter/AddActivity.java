package org.samir.simplecounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddActivity extends AppCompatActivity {
    private EditText edtCounterName;
    private Button btnAdd;
    private TextView txtWarning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtCounterName = findViewById(R.id.edtCounterName);
        btnAdd = findViewById(R.id.btnAdd);
        txtWarning = findViewById(R.id.txtWarning);

        btnAdd.setOnClickListener(v->{
            if(edtCounterName.getText().toString().isEmpty()){
                txtWarning.setVisibility(View.VISIBLE);
            }else{
                txtWarning.setVisibility(View.GONE);
                Utils.getInstance(this).addCounter(edtCounterName.getText().toString());
                Intent intent = new Intent(AddActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}