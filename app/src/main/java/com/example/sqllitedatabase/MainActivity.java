package com.example.sqllitedatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

        DatabaseHelper myDb;
        Button btninsertdata,btnviewdata,btnupdate,btndelete;
        EditText edtname , edtsurname, edtmarks, edtId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        btninsertdata = findViewById(R.id.btnInsert);
        btnviewdata = findViewById(R.id.btnView);
        btnupdate = findViewById(R.id.btnUpdate);
        btndelete = findViewById(R.id.btnDelete);

        edtId = findViewById(R.id.edtTextID);
        edtname = findViewById(R.id.edtTextName);
        edtsurname = findViewById(R.id.edtTextSurname);
        edtmarks = findViewById(R.id.edtTextMarks);

        btninsertdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              boolean inserted =   myDb.insertData(edtname.getText().toString(),edtsurname.getText().toString(),edtmarks.getText().toString());
            if (inserted == true){
                Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
            }
            }
        });

        btnviewdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cursor = myDb.getAllData();
                if (cursor.getCount()==0){
                   showMessage("Error","Data Not Found");
                   return;
                }
                StringBuffer buffer = new StringBuffer();
                while (cursor.moveToNext()){
                    buffer.append("ID :"+ cursor.getString(0) + "\n");
                    buffer.append("NAME :"+ cursor.getString(1) + "\n");
                    buffer.append("SURNAME :"+ cursor.getString(2) + "\n");
                    buffer.append("MARKS :"+ cursor.getString(3) + "\n");

                }

            showMessage("Data",buffer.toString());

            }
        });


        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               boolean isupdate =  myDb.updateData(edtId.getText().toString(),edtname.getText().toString(),edtsurname.getText().toString(),edtmarks.getText().toString());
                    if (isupdate == true){
                        Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Integer deletedrow =myDb.deleteData(edtId.getText().toString());
               if (deletedrow >0){
                   Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
               }
                else{
                   Toast.makeText(MainActivity.this, "Data Not Deleted", Toast.LENGTH_SHORT).show();
               }

            }
        });

    }
    public void showMessage(String title , String Message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}