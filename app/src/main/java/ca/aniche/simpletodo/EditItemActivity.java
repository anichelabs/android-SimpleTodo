package ca.aniche.simpletodo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class EditItemActivity extends ActionBarActivity {

    EditText etEditItem;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        etEditItem = (EditText) findViewById(R.id.etEditItem);
        String itemText = getIntent().getStringExtra("itemText");
        etEditItem.setText(itemText);
        etEditItem.setSelection(itemText.length());
        position = getIntent().getIntExtra("itemPos", -1);
        if(position < 0) {
            Toast.makeText(this, "ERROR: Unknown position of item", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
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

    public void saveEditedItem(View view){
       Intent data = new Intent();
       data.putExtra("itemText", etEditItem.getText().toString());
       data.putExtra("itemPos", position);
       setResult(RESULT_OK, data);
       finish();
    }


}
