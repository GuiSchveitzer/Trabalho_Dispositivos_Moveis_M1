package com.example.dynamicworklog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dynamicworklog.objects.Login;
import com.example.dynamicworklog.util.UserManager;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout timeDataBaseLayout;  // Layout para adicionar os campos dinamicamente
    private TextView filledCountText;         // Texto que mostra quantos campos foram preenchidos
    private Map<Integer, String> activityDataMap = new HashMap<>();  // Mapa para armazenar dados preenchidos
    private int lastFilledDay = -1; // Armazena o último dia do ano em que houve preenchimento

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Inicializa os componentes de UI
        TextView matriculaText = findViewById(R.id.matriculaText);
        TextView departmentText = findViewById(R.id.departmentText);
        TextView nameText = findViewById(R.id.nameText);
        TextView funcionText = findViewById(R.id.funcionText);
        timeDataBaseLayout = findViewById(R.id.timeDataBaseLayout);
        Button registerTimeButton = findViewById(R.id.registerTimeButton);
        filledCountText = findViewById(R.id.filledCountText);

        // Obtém o usuário logado
        Login user = new UserManager().getUser();

        // Preenche os dados do usuário nos campos de texto
        setText(user, matriculaText, departmentText, nameText, funcionText);

        // Ação do botão de registrar hora atual
        registerTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView timeTextView = findViewById(R.id.timeTextView);
                timeTextView.setVisibility(View.VISIBLE);
                Calendar calendar = Calendar.getInstance();
                String time = String.format("%02d:%02d:%02d",
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        calendar.get(Calendar.SECOND));
                timeTextView.setText("Hora registrada: " + time);
            }
        });
    }

    // Preenche os textos com os dados do usuário
    public void setText(Login user, TextView matriculaText, TextView departmentText, TextView nameText, TextView funcionText) {
        matriculaText.setText("Matrícula: " + user.getMatricula());
        departmentText.setText("Departamento: " + user.getDepartment().getName());
        nameText.setText("Nome Completo: " + user.getName());
        funcionText.setText("Função: " + user.getFunction().getName());
    }

    // Atualiza o contador de campos preenchidos
    private void updateFilledCount() {
        int filled = 0;
        for (String value : activityDataMap.values()) {
            if (value != null && !value.trim().isEmpty()) {
                filled++;
            }
        }
        filledCountText.setText("Atividades preenchidas: " + filled);
    }

    // Cria os campos dinamicamente conforme o horário atual
    public void createDynamicFields(Calendar calendar) {
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int startHour = 8;
        int endHour = 17; // Horário de término

        for (int hour = startHour; hour < endHour; hour++) {
            if (hour == 12) {
                continue;  // Pula o horário de almoço (12h-13h)
            }

            if (currentHour > hour) {
                // Cria um layout horizontal contendo o label e o campo de entrada
                LinearLayout llHorizontal = new LinearLayout(this);
                llHorizontal.setOrientation(LinearLayout.HORIZONTAL);

                // Label com o intervalo de tempo
                TextView label = new TextView(this);
                label.setText(String.format("%02d:00 - %02d:00", hour, hour + 1));
                llHorizontal.addView(label);

                // Campo de entrada (EditText) para digitação da atividade
                EditText et = new EditText(this);
                et.setHint("Informe sua atividade...");
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                et.setLayoutParams(lp);

                // Recupera valor preenchido anteriormente (se existir)
                String existingActivity = activityDataMap.get(hour);
                if (existingActivity != null) {
                    et.setText(existingActivity);
                }

                int finalHour = hour;
                // Salva automaticamente no mapa quando o usuário sai do campo
                et.setOnFocusChangeListener((v, hasFocus) -> {
                    if (!hasFocus) {
                        String text = et.getText().toString();
                        activityDataMap.put(finalHour, text);
                        updateFilledCount();  // Atualiza o número de campos preenchidos
                    }
                });

                // Adiciona campo ao layout
                llHorizontal.addView(et);
                timeDataBaseLayout.addView(llHorizontal);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Obtém a data atual
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_YEAR);  // Número do dia no ano (1-365/366)

        // Se for um novo dia, limpamos os dados antigos
        if (lastFilledDay != today) {
            activityDataMap.clear();     // Limpa atividades antigas
            lastFilledDay = today;       // Atualiza o último dia preenchido
        }

        // Limpa os campos de UI antes de recriar
        timeDataBaseLayout.removeAllViews();

        // Cria os campos novamente com base na hora atual
        createDynamicFields(calendar);

        // Atualiza contador de preenchidos
        updateFilledCount();
    }
}
