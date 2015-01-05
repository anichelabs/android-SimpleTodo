package ca.aniche.simpletodo.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "TodoItems")
public class TodoItem extends Model {

    @Column(name = "Body")
    private String body;

    @Column(name = "Priority")
    private int priority;

    @Column(name = "Completed", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private int completed;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isCompleted(){
        return completed > 0 ? true: false;
    }

    public void setCompleted(boolean isCompleted){
        this.completed = isCompleted ? 1 : 0;
    }

}