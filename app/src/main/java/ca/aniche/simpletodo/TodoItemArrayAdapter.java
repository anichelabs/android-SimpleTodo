package ca.aniche.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ca.aniche.simpletodo.model.TodoItem;

public class TodoItemArrayAdapter extends ArrayAdapter<TodoItem>{
   public TodoItemArrayAdapter(Context context, ArrayList<TodoItem> todoItems) {
        super(context, 0, todoItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TodoItem todoItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todoitem, parent, false);
        }
        // Lookup view for data population
        TextView tvItemBody = (TextView) convertView.findViewById(R.id.tvItemBody);

        // Populate the data into the template view using the data object
        tvItemBody.setText(todoItem.getBody());
        // Return the completed view to render on screen
        return convertView;
    }

}
