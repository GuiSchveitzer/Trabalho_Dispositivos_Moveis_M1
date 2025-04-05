package com.example.dynamicworklog;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dynamicworklog.objects.Department;
import com.example.dynamicworklog.objects.Function;
import com.example.dynamicworklog.objects.Login;
import com.example.dynamicworklog.util.UserManager;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText nameRegister = (EditText) findViewById(R.id.nameRegister);
        Spinner departmentSpinner = (Spinner) findViewById(R.id.departmentSpinner);
        Spinner functionSpinner = (Spinner) findViewById(R.id.functionSpinner);
        EditText passwordRegister = (EditText) findViewById(R.id.passwordRegister);
        TextView matriculaText = (TextView) findViewById(R.id.matriculaText);

        Button registerButton = (Button) findViewById(R.id.registerButton);
        Button backHome = (Button) findViewById(R.id.backHome);


        String[] departments = {"Recursos Humanos", "Financeiro", "Desenvedor", "Marketing"};
        // Funções/Cargos por Departamento
        String[] functions = {
                // Recursos Humanos
                        "Assistente de RH",
                        "Coordenador de Recursos Humanos",
                        "Gerente de Recursos Humanos",
                        "Analista de Recrutamento e Seleção",
                        "Especialista em Benefícios",

                // Financeiro
                        "Contador(a)",
                        "Analista Financeiro",
                        "Gerente de Finanças",
                        "Auditor(a)",
                        "Analista de Crédito",

                // TI (Tecnologia da Informação)
                        "Desenvolvedor(a) de Software",
                        "Engenheiro(a) de Software",
                        "Analista de Sistemas",
                        "Suporte Técnico",
                        "Gerente de TI",

                // Marketing
                        "Coordenador(a) de Marketing Digital",
                        "Analista de Mídias Sociais",
                        "Especialista em SEO",
                        "Gerente de Marketing",
                        "Analista de PPC",

                // Vendas
                        "Vendedor(a)",
                        "Consultor(a) de Vendas",
                        "Gerente de Vendas",
                        "Representante Comercial",
                        "Analista de CRM",

                // Saúde
                        "Médico(a)",
                        "Enfermeiro(a)",
                        "Fisioterapeuta",
                        "Psicólogo(a)",
                        "Farmacêutico(a)",

                // Jurídico
                        "Advogado(a)",
                        "Consultor(a) Jurídico(a)",
                        "Assessor(a) Jurídico(a)",
                        "Paralegal",
                        "Gerente Jurídico",

                // Educação
                        "Professor(a)",
                        "Coordenador(a) Pedagógico(a)",
                        "Orientador(a) Educacional",
                        "Supervisor(a) Escolar",
                        "Tutor(a)",

                // Logística
                        "Coordenador(a) de Logística",
                        "Supervisor(a) de Produção",
                        "Analista de Supply Chain",
                        "Operador(a) de Empilhadeira",
                        "Gerente de Logística",

        };

        //add Itens
        //https://stackoverflow.com/questions/5241660/how-to-add-items-to-a-spinner-in-android
        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departments);
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departmentSpinner.setAdapter(departmentAdapter);

        ArrayAdapter<String> functionsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, functions);
        functionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        functionSpinner.setAdapter(functionsAdapter);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManager users = new UserManager();
                users.registrationValidation(new Login("", nameRegister.getText().toString(), passwordRegister.getText().toString(), new Department(departmentSpinner.getSelectedItem().toString()), new Function(functionSpinner.getSelectedItem().toString())));
                //mainActivity();
                matriculaText.setText(users.matriculaLastUser());
                matriculaText.setVisibility(VISIBLE);
                registerButton.setVisibility(INVISIBLE);
            }
        });

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity();
            }
        });


    }

    public void mainActivity(){
        Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(myIntent);
    }



}