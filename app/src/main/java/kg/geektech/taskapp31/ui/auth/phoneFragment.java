package kg.geektech.taskapp31.ui.auth;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

import kg.geektech.taskapp31.R;
public class phoneFragment extends Fragment {
    private EditText editPhone;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private ProgressBar prgBar;
    private Button btnContinue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phone, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editPhone = view.findViewById(R.id.editPhone);
        prgBar = view.findViewById(R.id.progressBar);
        btnContinue = view.findViewById(R.id.btnContinue);
        view.findViewById(R.id.btnContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (!editPhone.getText().toString().isEmpty()){
                     requestSMS();
                 }else{
                     Toast.makeText(requireContext(), "Введите номер телефона",Toast.LENGTH_SHORT).show();
                 }
                prgBar.setVisibility(View.VISIBLE);
                btnContinue.setVisibility(View.INVISIBLE);

            }
        });
        initCallbacks();
    }
    private void initCallbacks() {
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.e("Phone", "OnVerificationCompleted");
                prgBar.setVisibility(View.VISIBLE);
                btnContinue.setVisibility(View.GONE);
            }
            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("Phone", "OnVerificationFailed:" + e.getMessage());
                prgBar.setVisibility(View.GONE);
                btnContinue.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCodeSent(@NonNull String verificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                prgBar.setVisibility(View.GONE);
                btnContinue.setVisibility(View.VISIBLE);
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.codeFragment);
            }
        };
    }
    private void requestSMS() {
        String phone = editPhone.getText().toString();
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder()
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(requireActivity())
                .setCallbacks(callbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}
