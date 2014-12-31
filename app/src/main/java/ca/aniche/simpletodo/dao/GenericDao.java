package ca.aniche.simpletodo.dao;

import com.activeandroid.Model;
import com.activeandroid.query.Select;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import ca.aniche.simpletodo.model.TodoItem;

public abstract class GenericDao<T extends Model> {

    public Long update(T entity){
        if(entity.getId() == null || entity.getId() <= 0){
            //TODO: throw a better exception
            throw new IllegalArgumentException("Update can only be called on entities that exist in DB");
        }
        return entity.save();
    }

    public Long save(T entity){
        return entity.save();
    }

    public void delete(T entity){
        entity.delete();
    }

    public T findById(Long id, Class<T> clazz){
        return new Select().from(clazz)
                .where("Id = ?", id)
                .executeSingle();
    }

    public List<T> findAll(Class<TodoItem> clazz, String sortField){
        return new Select().all().from(clazz).orderBy(sortField).execute();
    }
}
