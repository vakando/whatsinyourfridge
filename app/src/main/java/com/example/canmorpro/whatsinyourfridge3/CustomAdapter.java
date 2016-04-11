package com.example.canmorpro.whatsinyourfridge3;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.canmorpro.whatsinyourfridge3.R;
import com.example.empress.whatsinyourfridge3.DBHelper;

import java.util.ArrayList;

public class CustomAdapter extends CursorAdapter{

    public ArrayList<Boolean> itemChecked = new ArrayList<>();
    public ArrayList<String> checked = new ArrayList<>();
    public ArrayList<String> unchecked = new ArrayList<>();
    public ArrayList<String> item = new ArrayList<>();


    public CustomAdapter(Context context, Cursor cursor, int flags, ArrayList<String> ch, ArrayList<String> unch) {
        super(context,cursor,flags);

        this.checked = ch;
        this.unchecked = unch;

        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            item.add(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_I_NAME)));
            Log.d("item added",cursor.getString(cursor.getColumnIndex(DBHelper.KEY_I_NAME)));
            if(cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_I_CHECK)) == 1) {
                itemChecked.add(i, true);
            }
            else {
                itemChecked.add(i, false);
            }
            cursor.moveToNext();
        }
        /*cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            if(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_CHECKED)) == "true") {
                itemChecked.add(i, true); // initializes items value
                if (!checked.contains(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_INGREDIENT_NAME))))
                    checked.add(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_INGREDIENT_NAME)));
            } else {
                itemChecked.add(i, false);
                if (!unchecked.contains(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_INGREDIENT_NAME))))
                    unchecked.add(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_INGREDIENT_NAME)));
            }
            cursor.moveToNext();
        }*/
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // when the view will be created for first time,
        // we need to tell the adapters, how each item will look
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View retView = inflater.inflate(R.layout.line, parent, false);
        return retView;
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        // here we are setting our data
        // that means, take the data from the cursor and put it in views
        final TextView name = (TextView) view.findViewById(R.id.textView1);
        CheckBox cb = (CheckBox) view.findViewById(R.id.checkBox1);
        name.setText(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_I_NAME)));
        if(checked.contains(name.getText()))
            cb.setChecked(true);
        cb.setOnClickListener(new View.OnClickListener() {
                                  public void onClick(View v) {
                                      CheckBox cb = (CheckBox) v.findViewById(R.id.checkBox1);
                                      if (cb.isChecked()) {
                                          Log.d("cb checked","name ingredient = "+name.getText().toString());
                                          itemChecked.set(item.indexOf(name.getText().toString()), true);
                                          if (!checked.contains(name.getText().toString()))
                                              checked.add(name.getText().toString());
                                          if (unchecked.contains(name.getText().toString()))
                                              unchecked.remove(name.getText().toString());
                                      } else {
                                          Log.d("cb unchecked","name ingredient = "+name.getText());
                                          itemChecked.set(item.indexOf(name.getText().toString()), false);
                                          if (!unchecked.contains(name.getText().toString()))
                                              unchecked.add(name.getText().toString());
                                          if (checked.contains(name.getText().toString()))
                                              checked.remove(name.getText().toString());
                                      }
                                  }
                              }

        );
        cb.setChecked(itemChecked.get(item.indexOf(name.getText().toString())));
    }

    public ArrayList<String> getChecked(){
        return checked;
    }

    public ArrayList<String> getUnchecked(){
        return unchecked;
    }
}