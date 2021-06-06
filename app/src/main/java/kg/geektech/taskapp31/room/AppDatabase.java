package kg.geektech.taskapp31.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import kg.geektech.taskapp31.models.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();

}
