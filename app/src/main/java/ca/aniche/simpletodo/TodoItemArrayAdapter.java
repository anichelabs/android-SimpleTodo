package ca.aniche.simpletodo;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import ca.aniche.simpletodo.model.TodoItem;

public class TodoItemArrayAdapter extends ArrayAdapter<TodoItem>{
   public TodoItemArrayAdapter(Context context, ArrayList<TodoItem> todoItems) {
        super(context, 0, todoItems);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todoitem, parent, false);
        }
        // Lookup view for data population
        TextView tvItemBody = (TextView) convertView.findViewById(R.id.tvItemBody);
        CheckBox cbItem = (CheckBox) convertView.findViewById(R.id.cbItem);
        final View finalConvertView = convertView;
        cbItem.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                TextView tvItemBody = (TextView) finalConvertView.findViewById(R.id.tvItemBody);
                TodoItem item = getItem(position);
                item.setCompleted(cb.isChecked());
                if(item.isCompleted()) {
                    tvItemBody.setPaintFlags(tvItemBody.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    tvItemBody.setPaintFlags(tvItemBody.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                }
                item.save();
                notifyDataSetChanged();
            }
        });

        // Get the data item for this position
        TodoItem todoItem = getItem(position);
        // Populate the data into the template view using the data object
        tvItemBody.setText(todoItem.getBody());
        if(todoItem.isCompleted()) {
            tvItemBody.setPaintFlags(tvItemBody.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            tvItemBody.setPaintFlags(tvItemBody.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        cbItem.setChecked(todoItem.isCompleted());

        // Return the completed view to render on screen
        return convertView;
    }

}
