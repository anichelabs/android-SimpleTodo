package ca.aniche.simpletodo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ca.aniche.simpletodo.dao.TodoItemDao;
import ca.aniche.simpletodo.model.TodoItem;


public class TodoListActivity extends ActionBarActivity {

    private static final int REQUEST_CODE = 200; //Random code
    ArrayList<TodoItem> items;
    TodoItemArrayAdapter itemsAdapter;
    ListView lvItems;
    Button btAddItem;

    //TODO: Introduce Dependency Injection with Guice or somethinge lse
    TodoItemDao todoItemDao = new TodoItemDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        items = (ArrayList<TodoItem>) getItems();
        itemsAdapter = new TodoItemArrayAdapter(this, items);
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();

    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
            new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent,
                                               View view, int position, long id) {
                    TodoItem item = items.get(position);
                    items.remove(position);
                    todoItemDao.delete(item);
                    Toast.makeText(parent.getContext(), "Deleted item with ID " + id, Toast.LENGTH_SHORT).show();
                    itemsAdapter.notifyDataSetChanged();
                    return true;
                }
        });

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(TodoListActivity.this, EditItemActivity.class);
                        i.putExtra("itemText", items.get(position).getBody());
                        i.putExtra("itemPos", position);
                        startActivityForResult(i, REQUEST_CODE);
                    }
                }
        );
    }

    @Override
    //Called after Edit is done
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String newText = data.getStringExtra("itemText");
            int pos = data.getIntExtra("itemPos", -1);
            if(pos < 0){
                Toast.makeText(this, "Could not update text. Position Unknown", Toast.LENGTH_SHORT).show();
            }
            TodoItem item = items.get(pos);
            item.setBody(newText);
            item.save();
            itemsAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Updated Item with ID " + item.getId(), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_list, menu);
        return true;
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

    public void onAddItem(View v){
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String newItem = etNewItem.getText().toString();
        if (newItem == null && "".equals(newItem.trim())){
           return;
        }
        TodoItem item = new TodoItem();
        item.setBody(newItem);
        item.setPriority(itemsAdapter.getCount() + 1);
        itemsAdapter.add(item);
        Long id = saveItem(item);
        Toast.makeText(this, "Saved item with ID " + id, Toast.LENGTH_SHORT).show();
        etNewItem.setText("");
    }

    private Long saveItem(TodoItem item) {
        return todoItemDao.save(item);
    }

    public List<TodoItem> getItems() {
        return todoItemDao.findAll();
    }
}
