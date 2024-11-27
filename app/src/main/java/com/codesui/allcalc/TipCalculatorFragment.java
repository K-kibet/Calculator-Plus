package com.codesui.allcalc;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.codesui.allcalc.ads.BannerManager;
import com.codesui.allcalc.ads.InterstitialManager;

public class TipCalculatorFragment extends Fragment {

    EditText etAmount;
    TextView tvPercent;
    SeekBar sbPercent;
    TextView tvTip, tvTotal;
    Button btnCalculate;
    InterstitialManager interstitialManager = new InterstitialManager();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tip_calculator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etAmount = view.findViewById(R.id.et_amount);
        tvPercent = view.findViewById(R.id.tv_percent);
        sbPercent = view.findViewById(R.id.sb_percent);
        tvTip = view.findViewById(R.id.tv_tip);
        tvTotal = view.findViewById(R.id.tv_total);
        btnCalculate = view.findViewById(R.id.btn_calculate);

        interstitialManager.loadInterstitial(getActivity());

        sbPercent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int percent = progress;
                tvPercent.setText(String.valueOf(percent + "%"));
                calculate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculate();
            }
        });

        btnCalculate.setOnClickListener(v -> {
            calculate();
            interstitialManager.showInterstitial(getActivity());
        });

        FrameLayout adViewContainer = view.findViewById(R.id.adViewContainer);
        BannerManager bannerManager = new BannerManager(requireContext(), requireActivity(), adViewContainer);
        bannerManager.loadBanner();
    }

    private void calculate() {

        if(etAmount.length() == 0) {
            etAmount.requestFocus();
            etAmount.setError("Enter Amount");
        } else {
            double amount = Double.parseDouble(etAmount.getText().toString());
            int percent = sbPercent.getProgress();
            double tip = amount * percent/100.0;
            double total = amount + tip;
            tvTip .setText(String.valueOf(tip));
            tvTotal.setText(String.valueOf(total));
        }
    }
}