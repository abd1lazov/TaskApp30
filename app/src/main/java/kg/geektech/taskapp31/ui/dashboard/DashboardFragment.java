package kg.geektech.taskapp31.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import kg.geektech.taskapp31.R;
import kg.geektech.taskapp31.interfaces.OnItemClickListener;
import kg.geektech.taskapp31.models.Task;
import kg.geektech.taskapp31.ui.home.TaskAdapter;

public class DashboardFragment extends Fragment {
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private Object OnItemClickListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter();
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        initList();
        getDataFromFireStore();
    }
    private void initList() {
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener();
        }



    private void getDataFromFireStore() {
        FirebaseFirestore.getInstance()
                .collection("tasks")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshots) {
                        List<kg.geektech.taskapp31.models.Task> list = snapshots.toObjects(Task.class);
                        adapter.addItems(list);
                    }
                });
        }

}