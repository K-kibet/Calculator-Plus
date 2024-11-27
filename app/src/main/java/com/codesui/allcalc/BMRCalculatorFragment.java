package com.codesui.allcalc;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.codesui.allcalc.ads.AppOpenManager;
import com.codesui.allcalc.ads.BannerManager;
import com.codesui.allcalc.ads.InterstitialManager;


public class BMRCalculatorFragment extends Fragment {
    Button btnCalculate, btnRecalculate;
    EditText height, weight, age;
    RelativeLayout maleLayout, femaleLayout;
    double h =0, w =0, a = 0, bmr = 0;
    String user = "0";
    private AppOpenManager appOpenManager;
    InterstitialManager interstitialManager = new InterstitialManager();
    TextView bmrResults, mgender;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b_m_r_calculator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnCalculate = view.findViewById(R.id.btn_calculate);
        height = view.findViewById(R.id.heightxt);
        weight = view.findViewById(R.id.weighttxt);
        age = view.findViewById(R.id.agettxt);

        maleLayout = view.findViewById(R.id.maleLayout);
        femaleLayout = view.findViewById(R.id.femaleLayout);

        interstitialManager.loadInterstitial(getActivity());
        appOpenManager = new AppOpenManager();
        appOpenManager.loadAd(getActivity());

        maleLayout.setOnClickListener(v -> {
            maleLayout.setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.malefemalefocus));
            femaleLayout.setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.malefemalenotfocus));
            user = "Male";
        });

        femaleLayout.setOnClickListener(v -> {
            femaleLayout.setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.malefemalefocus));
            maleLayout.setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.malefemalenotfocus));
            user = "Female";
        });


        btnCalculate.setOnClickListener(v -> calculate());
        FrameLayout adViewContainer = view.findViewById(R.id.adViewContainer);
        BannerManager bannerManager = new BannerManager(requireContext(), requireActivity(), adViewContainer);
        bannerManager.loadBanner();
    }

    //calculate function
    private void calculate () {
        if(user.equals("0")) {
            Toast.makeText(requireContext(),"Please Select Your Gender",Toast.LENGTH_SHORT).show();
        } else if(height.length() < 1) {
            Toast.makeText(requireContext(),"Enter Your Height",Toast.LENGTH_SHORT).show();
        } else if(weight.length() < 1) {
            Toast.makeText(requireContext(),"Enter Your Weight",Toast.LENGTH_SHORT).show();
        } else if (age.length() < 1) {
            Toast.makeText(requireContext(),"Enter Your Age",Toast.LENGTH_SHORT).show();
        } else {
            h = Double.parseDouble(height.getText().toString());
            w = Double.parseDouble(weight.getText().toString());
            a = Double.parseDouble(age.getText().toString());

            if(user.equals("Male")){
                bmr = ((10*w) + (6.25*h) - (5*a) + 5);
            } else if(user.equals("Female")) {
                bmr = ((10*w) + (6.25*h) - (5*a) + 161);
            }

            Dialog dialog = new Dialog(requireContext());
            dialog.setContentView(R.layout.show_bmr_dialog);

            bmrResults= dialog.findViewById(R.id.bmrResults);
            btnRecalculate=dialog.findViewById(R.id.btnRecalculate);
            mgender=dialog.findViewById(R.id.genderdisplay);

            mgender.setText(user);
            bmrResults.setText(Double.toString(bmr));
            btnRecalculate.setOnClickListener(v1 -> {
                dialog.cancel();
                appOpenManager.showAdIfAvailable(requireActivity());
            });
            dialog.show();
            interstitialManager.showInterstitial(getActivity());
        }

    }
}