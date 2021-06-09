package kg.geektech.taskapp31.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import org.w3c.dom.Text;

import java.util.List;

import kg.geektech.taskapp31.models.Task;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAll();

    @Query("SELECT * FROM task ORDER BY title ASC")
    LiveData<List<Task>>SortByASc();

    @Insert
    void insert(Task task);

    @Delete
    void remove (Task task);
}

