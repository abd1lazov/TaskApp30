package kg.geektech.taskapp31.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kg.geektech.taskapp31.App;
import kg.geektech.taskapp31.R;
import kg.geektech.taskapp31.interfaces.OnItemClickListener;
import kg.geektech.taskapp31.models.Task;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private Object OnItemClickListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
//        adapter = new TaskAdapter();
//        List<Task> list = App.getAppDatabase().taskDao().getAll();
//        adapter.addItems((list));
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        roomInit();
        super.onViewCreated(view, savedInstanceState);
        adapter = new TaskAdapter();
        recyclerView = view.findViewById(R.id.recyclerView);
        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTaskFragment();
            }
        });
        setResultListener();
        //initList();
    }

    private void roomInit() {
        App.getAppDatabase().taskDao().getAll().observe(getViewLifecycleOwner(), new Observer<java.util.List<Task>>() {
            @Override
            public void onChanged(java.util.List<Task> list) {
                initList();
            }
        });

    }

    private void initList() {
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu1,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort:
                sortRoom();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void sortRoom() {
        App.getAppDatabase().taskDao().SortByASc().observe(getViewLifecycleOwner(), new Observer<java.util.List<Task>>() {
            @Override
            public void onChanged(java.util.List<Task> list) {
                Toast.makeText(requireContext(), "отсортировано!", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
                initList();
            }
        });
    }
    private void setResultListener() {
        getParentFragmentManager().setFragmentResultListener(
                "rk_task",
                getViewLifecycleOwner(),
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        String text = result.getString("text");
                        Task task = new Task(text);
                        adapter.addItem(task);
                        Log.e("Home", "text:" + text);
                    }
                });
    }
    private void openTaskFragment() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.taskFragment);
    }
}