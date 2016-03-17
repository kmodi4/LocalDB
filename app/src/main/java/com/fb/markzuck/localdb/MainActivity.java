package com.fb.markzuck.localdb;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username,pass;
    Button bt;
    String name,pw;
    DBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);
        bt = (Button) findViewById(R.id.button);

        dbAdapter = new DBAdapter(this);
           bt.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   addUser();
               }
           });

    }

    public void addUser(){
        name = username.getText().toString();
        pw = pass.getText().toString();
        if(name.equals("") || pw.equals("")){
            Toast.makeText(getApplicationContext(),"Empty Field",Toast.LENGTH_SHORT).show();
        }else {
            long id = dbAdapter.InsertData(name, pw);
            if (id > 0) {
                Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void viewAll(View view){
        String msg = dbAdapter.getData();
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    public void userDetail(View view){
        name = username.getText().toString();
        if(name.equals("")) {
            Toast.makeText(getApplicationContext(), "Empty FIeld", Toast.LENGTH_SHORT).show();
        }
        else {
            String msg = dbAdapter.Userdata(name);
            if (msg.equals("")){
                Toast.makeText(getApplicationContext(),"Entry Not Found", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void updateDetail(View view){
        name = username.getText().toString();
        if(name.equals("")) {
            Toast.makeText(getApplicationContext(), "Empty FIeld", Toast.LENGTH_SHORT).show();
        }
        else {
            try {


                String sub1 = name.substring(0, name.indexOf(" "));
                String sub2 = name.substring(name.indexOf(" ") + 1);

                if (sub1.equals("") || sub2.equals(""))
                    Toast.makeText(getApplicationContext(), "Format:old new", Toast.LENGTH_SHORT).show();
                else {
                    int msg = dbAdapter.updateData(sub1, sub2);
                    if (msg == 0) {
                        Toast.makeText(getApplicationContext(), "Entry Not Found", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "updated at " + msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }catch (IndexOutOfBoundsException e){
                Toast.makeText(getApplicationContext(), "Format:old new", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void Del(View view){
        name = username.getText().toString();
        if(name.equals("")) {
            Toast.makeText(getApplicationContext(), "Empty FIeld", Toast.LENGTH_SHORT).show();
        }
        else {
            int msg = dbAdapter.delData(name);
            if (msg==0){
                Toast.makeText(getApplicationContext(),"Entry Not Found", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "deleted at "+msg, Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.show_list:
                Intent i = new Intent(MainActivity.this,Userlist.class);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
