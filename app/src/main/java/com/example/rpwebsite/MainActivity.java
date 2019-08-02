package com.example.rpwebsite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TextView tvCategory, tvSubCategory;
    Spinner spnCat, spnSubCat;
    Button btnGo;
    WebView webView;
    ArrayList<String> alSubCategory;
    ArrayAdapter<String> aaSubCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCategory = findViewById(R.id.textViewCategory);
        tvSubCategory = findViewById(R.id.textViewSub);
        spnCat = findViewById(R.id.spinnerCategory);
        spnSubCat = findViewById(R.id.spinnerSubCategory);
        btnGo = findViewById(R.id.buttonGo);
        webView = findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient());
        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setAllowFileAccess(false);
        setting.setBuiltInZoomControls(true);

        alSubCategory = new ArrayList<>();
        aaSubCategory = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, alSubCategory);

        spnCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        alSubCategory.clear();
                        String[] strSubCategory = getResources().getStringArray(R.array.rp_cat);
                        alSubCategory.addAll(Arrays.asList(strSubCategory));
                        spnSubCat.setAdapter(aaSubCategory);
                        break;
                    case 1:
                        alSubCategory.clear();
                        strSubCategory = getResources().getStringArray(R.array.soi_cat);
                        alSubCategory.addAll(Arrays.asList(strSubCategory));
                        spnSubCat.setAdapter(aaSubCategory);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[][] sites = {
                        {
                            "https://www.rp.edu.sg/",
                            "https://www.rp.edu.sg/student-life"
                        },
                        {
                            "https://www.rp.edu.sg/soi/full-time-diplomas/details/r47",
                                "https://www.rp.edu.sg/soi/full-time-diplomas/details/r12"
                        }
                };

                String url = sites[spnCat.getSelectedItemPosition()][spnSubCat.getSelectedItemPosition()];
                webView.loadUrl(url);

                tvCategory.setVisibility(View.GONE);
                spnCat.setVisibility(View.GONE);
                tvSubCategory.setVisibility(View.GONE);
                spnSubCat.setVisibility(View.GONE);
                btnGo.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        tvCategory.setVisibility(View.VISIBLE);
        spnCat.setVisibility(View.VISIBLE);
        tvSubCategory.setVisibility(View.VISIBLE);
        spnSubCat.setVisibility(View.VISIBLE);
        btnGo.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();

        int posCategory = spnCat.getSelectedItemPosition();
        int posSubCategory = spnSubCat.getSelectedItemPosition();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();

        prefEdit.putInt("spnCat", posCategory);
        prefEdit.putInt("spnSubCat", posSubCategory);

        prefEdit.commit();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        int defaultCat = prefs.getInt("spnCat", 0);
        int defaultSubCat = prefs.getInt("spnSubCat", 0);

        spnCat.setSelection(defaultCat);
        spnSubCat.setSelection(defaultSubCat);
    }
}
