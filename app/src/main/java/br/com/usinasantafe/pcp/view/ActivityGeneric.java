package br.com.usinasantafe.pcp.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.util.ArrayList;

import br.com.usinasantafe.pcp.NetworkChangeListerner;
import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.model.pst.DatabaseHelper;


public class ActivityGeneric extends OrmLiteBaseActivity<DatabaseHelper> {

    public EditText editTextPadrao;

    public static boolean connectNetwork;

    NetworkChangeListerner networkChangeListerner = new NetworkChangeListerner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getHelper();
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListerner, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListerner);
        super.onStop();
    }


    private class EventoBotao implements View.OnClickListener {

        private String numBotao;
        private String localClassName;

        public EventoBotao(String numBotao, String localClassName) {
            this.numBotao = numBotao;
            this.localClassName = localClassName;
        }

        @Override
        public void onClick(View v) {

            String texto = editTextPadrao.getText().toString();
            if(localClassName.equals("view.CPFVisitTercActivity")){
                if(texto.length() < 14){
                    if(texto.length() == 3){
                        editTextPadrao.setText(editTextPadrao.getText() + "." + numBotao);
                    } else if(texto.length() == 7){
                        editTextPadrao.setText(editTextPadrao.getText() + "." + numBotao);
                    } else if(texto.length() == 11){
                        editTextPadrao.setText(editTextPadrao.getText() + "-" + numBotao);
                    } else {
                        editTextPadrao.setText(editTextPadrao.getText() + "" + numBotao);
                    }
                }
            } else {
                if (numBotao.equals(",")) {
                    if (!texto.contains(",")) {
                        editTextPadrao.setText(editTextPadrao.getText() + "" + numBotao);
                    }
                } else {
                    editTextPadrao.setText(editTextPadrao.getText() + "" + numBotao);
                }
            }

        }

    }

    @Override
    protected void onResume() {

        super.onResume();

        if (findViewById(R.id.editTextPadrao) != null) {
            editTextPadrao = findViewById(R.id.editTextPadrao);
            if ((!this.getLocalClassName().equals("view.VeiculoUsinaActivity"))
                    && (!this.getLocalClassName().equals("view.MatricColabActivity"))
                    && (!this.getLocalClassName().equals("view.NotaFiscalActivity"))
                    && (!this.getLocalClassName().equals("view.CPFVisitTercActivity"))) {
                editTextPadrao.setText("");
            }
        }

        if (findViewById(R.id.buttonNum0) != null) {
            Button buttonNum0 = findViewById(R.id.buttonNum0);
            buttonNum0. setOnClickListener(new EventoBotao("0", this.getLocalClassName()));
        }

        if (findViewById(R.id.buttonNum1) != null) {
            Button buttonNum1 = findViewById(R.id.buttonNum1);
            buttonNum1.setOnClickListener(new EventoBotao("1", this.getLocalClassName()));
        }

        if (findViewById(R.id.buttonNum2) != null) {
            Button buttonNum2 = findViewById(R.id.buttonNum2);
            buttonNum2.setOnClickListener(new EventoBotao("2", this.getLocalClassName()));
        }

        if (findViewById(R.id.buttonNum3) != null) {
            Button buttonNum3 = findViewById(R.id.buttonNum3);
            buttonNum3.setOnClickListener(new EventoBotao("3", this.getLocalClassName()));
        }

        if (findViewById(R.id.buttonNum4) != null) {
            Button buttonNum4 = findViewById(R.id.buttonNum4);
            buttonNum4.setOnClickListener(new EventoBotao("4", this.getLocalClassName()));
        }

        if (findViewById(R.id.buttonNum5) != null) {
            Button buttonNum5 = findViewById(R.id.buttonNum5);
            buttonNum5.setOnClickListener(new EventoBotao("5", this.getLocalClassName()));
        }

        if (findViewById(R.id.buttonNum6) != null) {
            Button buttonNum6 = findViewById(R.id.buttonNum6);
            buttonNum6.setOnClickListener(new EventoBotao("6", this.getLocalClassName()));
        }

        if (findViewById(R.id.buttonNum7) != null) {
            Button buttonNum7 = findViewById(R.id.buttonNum7);
            buttonNum7.setOnClickListener(new EventoBotao("7", this.getLocalClassName()));
        }

        if (findViewById(R.id.buttonNum8) != null) {
            Button buttonNum8 = findViewById(R.id.buttonNum8);
            buttonNum8.setOnClickListener(new EventoBotao("8", this.getLocalClassName()));
        }

        if (findViewById(R.id.buttonNum9) != null) {
            Button buttonNum9 = findViewById(R.id.buttonNum9);
            buttonNum9.setOnClickListener(new EventoBotao("9", this.getLocalClassName()));
        }

        if (findViewById(R.id.buttonNum00) != null) {
            Button buttonNum00 = findViewById(R.id.buttonNum00);
            buttonNum00.setOnClickListener(new EventoBotao("00", this.getLocalClassName()));
        }

        if (findViewById(R.id.buttonVirg) != null) {
            Button buttonVirg = findViewById(R.id.buttonVirg);
            buttonVirg.setOnClickListener(new EventoBotao(",", this.getLocalClassName()));
        }
    }

}
