package com.vraimentuneapplicationdequalite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    private EditText ET_saisieTaille;
    private EditText ET_saisiePoids;
    private Button BT_effacer;
    private Button BT_calculer;

    private boolean texte;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ET_saisiePoids = findViewById(R.id.main_poid_et);
        ET_saisieTaille = findViewById(R.id.main_taille_et);
        BT_calculer = findViewById(R.id.main_calcul_bt);
        BT_effacer = findViewById(R.id.main_cancel_bt);

        BT_calculer.setEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ET_saisieTaille.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                BT_calculer.setEnabled(!ET_saisieTaille.getText().toString().trim().isEmpty() &&
                        !ET_saisiePoids.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        ET_saisiePoids.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                BT_calculer.setEnabled(!ET_saisieTaille.getText().toString().trim().isEmpty() &&
                        !ET_saisiePoids.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        BT_effacer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetFields();
            }
        });

        BT_calculer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultActivity = new Intent(MainActivity.this, ResultActivity.class);
                double imc = getIMC(Double.parseDouble(ET_saisieTaille.getText().toString()), Double.parseDouble(ET_saisiePoids.getText().toString()));
                resultActivity.putExtra("valeurIMC", imc);
                resultActivity.putExtra("valeurIMCText", getStringIMC(imc));
                startActivity(resultActivity);
            }
        });
    }

    /**
     * Obtient l'IMC en fonctions de valeurs passées en paramètre
     * @param taille Taille donné cm
     * @param poids Poids donné kg
     * @return L'IMC
     */
    private Double getIMC(Double taille, Double poids) {
        if (taille > 3) {
            taille = taille / 100;
        }
        double imc = poids / Math.pow(taille, 2);
        BigDecimal roundImc = new BigDecimal(imc);
        roundImc = roundImc.setScale(2, BigDecimal.ROUND_UP);

        return roundImc.doubleValue();
    }

    private String getStringIMC(double imc) {
        if (imc < 18.5) {
            return getText(R.string.imc_text_maigreur).toString();
        } else if (imc < 24.9) {
            return getText(R.string.imc_text_normal).toString();
        } else if (imc < 29.9) {
            return getText(R.string.imc_text_surpoids).toString();
        } else if (imc < 34.9) {
            return getText(R.string.imc_text_ob).toString();
        } else {
            return getText(R.string.imc_text_ob2).toString();
        }
    }

    private void resetFields() {
        ET_saisieTaille.setText("");
        ET_saisiePoids.setText("");
        BT_calculer.setEnabled(false);
        ET_saisieTaille.requestFocus();
    }
}