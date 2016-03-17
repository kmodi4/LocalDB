package com.fb.markzuck.localdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Userlist extends AppCompatActivity {

    ListView lv;
    DBAdapter dbAdapter;
    List<String> list;
    ArrayAdapter<String> adapter;
    Map<String,String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        lv= (ListView) findViewById(R.id.listView);
        dbAdapter = new DBAdapter(this);
        list = new ArrayList<>();
        map = new HashMap<>();
        setAdapter();
        registerForContextMenu(lv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_userlist, menu);
        return true;
    }

    public void setAdapter(){
        list = dbAdapter.getAdapterData();
        adapter = new ArrayAdapter<String>(Userlist.this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "View_info");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "Delete");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
           //int i=info.position;
        int index = (int)info.id;
        String data = list.get(index);
        /*if(item.getTitle()=="Update"){
            Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
            //list.set(index,newName);
        }*/

        if(item.getTitle()=="View_info"){

          // map = dbAdapter.getmap();
            String detail = dbAdapter.Userdata(data);
            Toast.makeText(getApplicationContext(),detail,Toast.LENGTH_LONG).show();

        }

        else if(item.getTitle()=="Delete"){
            Toast.makeText(getApplicationContext(),"Deleting "+info.position, Toast.LENGTH_LONG).show();
            dbAdapter.delData(data);
            list.remove(index);
            adapter.notifyDataSetChanged();
        }else{
            return false;
        }
        return true;
    }
}
