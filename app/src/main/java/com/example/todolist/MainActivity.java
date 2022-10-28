package com.example.todolist;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button btnSubbmit;
    EditText editText;
    ListView listView;
    ArrayList<String> itemList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
 //fdsfds
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    editText = findViewById(R.id.editText);
    btnSubbmit = findViewById(R.id.button);
    listView = findViewById(R.id.ListView);

    itemList = FileHelper.readData(this);

    arrayAdapter = new ArrayAdapter<>(this,R.layout.activity_listview,itemList);
    listView.setAdapter(arrayAdapter);

    btnSubbmit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            Log.d(TAG, "onClick: " + now);
            String itemName = editText.getText().toString().toUpperCase();
            Log.i("MyApp",itemName);
            if(itemName.length() > 0) {
                itemList.add(itemName);
                editText.setText("");
                arrayAdapter.notifyDataSetChanged();
                Snackbar.make(view, "Item is added", Snackbar.LENGTH_SHORT).show();

            }
            else {
                Toast toastAdd = Toast.makeText(getApplicationContext(), "Error: Task can't be empty", Toast.LENGTH_SHORT);
                toastAdd.setGravity(Gravity.TOP, 0, 250);
                toastAdd.show();
            }

        }
    });

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            Log.d(TAG, "onItemClick: " + now);
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Delete");
            alert.setMessage("Do you want to delete this item from the list?");
            alert.setCancelable(false);
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    Log.i(TAG, "onClick: " + now);
                    dialogInterface.cancel();

                }
            });
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    Log.i(TAG, "onClick: " + now);
                    itemList.remove(position);
                    arrayAdapter.notifyDataSetChanged();
                    Toast toastAdd = Toast.makeText(getApplicationContext(), "Item is removed", Toast.LENGTH_SHORT);
                    toastAdd.setGravity(Gravity.TOP, 0, 250);
                    toastAdd.show();
                }
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        }
    });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long arg3) {
                TextView textView = (TextView) view;
                String titleYt = "Łasuch strajkuje • Epizod • Smerfy";
                if (textView.getText().toString().equalsIgnoreCase(titleYt.toUpperCase())) {
                    displayVideo();
                }
                return false;
            }
        });

    }

    @Override
    protected void onStop() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Log.i(TAG, "onStop: " + now);
        super.onStop();
        FileHelper.writeData(itemList,getApplicationContext());
    }

    private void displayVideo() {
        String url = "https://www.youtube.com/watch?v=CFaPAK89g8k";
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(url));

        startActivity(webIntent);
    }


}