package kg.geektech.taskapp31.ui.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.telecom.CallRedirectionService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import kg.geektech.taskapp31.R;

public class CodeFragment extends Fragment {
    private Button btnVerify;
    private TextView btnRed;
    private EditText txtEdit;
    private ProgressBar progressBar1;
    private String VerificationId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_code, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtEdit = view.findViewById(R.id.editTxt);
        progressBar1 = view.findViewById(R.id.progressBar1);
        view.findViewById(R.id.btnVerify)
        .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtEdit.getText().toString().isEmpty()) {
                    Toast.makeText(requireContext(),"Напишите отправленный вам код",Toast.LENGTH_SHORT).show();
                }
                String code = txtEdit.getText().toString();
                if (VerificationId !=null){
//                    progressBar1.setVisibility(View.VISIBLE);
//                    btnVerify.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(VerificationId,code);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
//                       progressBar1.setVisibility(View.GONE);
//                       btnVerify.setVisibility(View.VISIBLE);
                       if (task.isSuccessful()){
                           Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                           NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
                           navController.navigate(R.id.navigation_home);
//                           Intent intent = new Intent(requireContext(),CodeFragment.class);
//                           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                           startActivity(intent);
                       }else{
                           Toast.makeText(requireContext(), "Введенный проверочный код недействителен", Toast.LENGTH_SHORT).show();

                       }
                        }
                    });
                }
            }
        });
    }
}