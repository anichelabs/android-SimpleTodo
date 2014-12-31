package ca.aniche.simpletodo.dao;

import com.activeandroid.Model;

import java.util.List;

import ca.aniche.simpletodo.model.TodoItem;

public class TodoItemDao extends GenericDao<TodoItem> {

    public List<TodoItem> findAll() {
        return super.findAll(TodoItem.class, "Priority ASC");
    }
}
