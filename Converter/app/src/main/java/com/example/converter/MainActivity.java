package com.example.converter;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    TextView result;
    Button btnSearch;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = (Button) findViewById(R.id.button);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        result = (TextView) findViewById(R.id.editText);

        Spinner spinner = findViewById(R.id.spinner);
        Spinner spinner2 = findViewById(R.id.spinner2);

        ArrayAdapter<?> arrayAdapter = ArrayAdapter.createFromResource  // Загрузка массива в адаптер
                (this, R.array.Валюты, android.R.layout.simple_spinner_item); // для создания предсталвения каждому элементу
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // шаблон раскрывающегося списка

        spinner.setAdapter(arrayAdapter);//устанавливаем адаптер в спиннер
        spinner.setSelection(0);//установка значения элемента по-умолчанию
        spinner.setAdapter(arrayAdapter);
        spinner2.setSelection(0);

        btnSearch.setOnClickListener(new View.OnClickListener() {   //Создаем слушателя btnSearch
            @Override
            public void onClick(View v) {

                ConInt conInt = new ConInt(new WeakReference<>(MainActivity.this));

                conInt.execute(spinner.getSelectedItem().toString(), spinner2.getSelectedItem().toString());
                result.getText();
            }
        });
    }

    public void setResultsCount(String str) {
        result.setText(getString(R.string.textview).replace("TextView", str)); //
    }

}