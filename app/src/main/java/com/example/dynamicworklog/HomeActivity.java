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
    private Map<Integer, String> activityDataMap = new HashMap<>();  // Mapa para armazenar dados preenchidos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Inicializa os componentes de UI
        TextView matriculaText = findViewById(R.id.matriculaText);
        TextView departmentText = findViewById(R.id.departmentText);
        TextView nameText = findViewById(R.id.nameText);
        TextView funcionText = findViewById(R.id.funcionText);
        timeDataBaseLayout = findViewById(R.id.timeDataBaseLayout);  // Layout onde vamos adicionar os campos dinamicamente
        Button registerTimeButton = findViewById(R.id.registerTimeButton);

        // Obtém o usuário (Login) usando a UserManager
        Login user = new UserManager().getUser();

        // Preenche as informações do usuário nos respectivos campos de texto
        setText(user, matriculaText, departmentText, nameText, funcionText);

        // Adiciona o evento de clique no botão para registrar a hora
        registerTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para registrar a hora (exemplo, você pode personalizar conforme a necessidade)
                TextView timeTextView = findViewById(R.id.timeTextView);
                Calendar calendar = Calendar.getInstance();
                String time = String.format("%02d:%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
                timeTextView.setText("Hora registrada: " + time);
            }
        });
    }

    // Método para preencher as informações do usuário nos campos de texto
    public void setText(Login user, TextView matriculaText, TextView departmentText, TextView nameText, TextView funcionText) {
        matriculaText.setText(user.getMatricula());
        departmentText.setText(user.getDepartment().getName());
        nameText.setText(user.getName());
        funcionText.setText(user.getFunction().getName());
    }

    // Método para criar os campos dinamicamente conforme a hora atual
    public void createDynamicFields(Calendar calendar) {
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        // A partir das 8h até 12h (e considerando a hora de almoço)
        int startHour = 8;
        int endHour = 17; // 17h é o horário final

        // A hora de almoço é entre 12h e 13h, então devemos ignorar esse intervalo
        for (int hour = startHour; hour < endHour; hour++) {
            if (hour == 12) {
                continue;  // Pula a hora de almoço
            }

            // Se a hora atual já passou ou se estamos no minuto após a hora, criamos o campo
            if (currentHour > hour) {
                // Cria um novo layout horizontal para a hora
                LinearLayout llHorizontal = new LinearLayout(this);
                llHorizontal.setOrientation(LinearLayout.HORIZONTAL);

                // Cria um campo de texto (Label) para a hora
                TextView label = new TextView(this);
                label.setText(String.format("%02d:00 - %02d:00", hour, hour + 1));
                llHorizontal.addView(label);

                // Cria um campo de texto (EditText) para registrar a atividade
                EditText et = new EditText(this);
                et.setHint("Informe sua atividade...");

                // Verifica se já há um valor preenchido para essa hora e restaura se necessário
                String existingActivity = activityDataMap.get(hour);
                if (existingActivity != null) {
                    et.setText(existingActivity);  // Restaura o valor preenchido anteriormente
                }

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                et.setLayoutParams(lp);
                llHorizontal.addView(et);

                // Adiciona o layout ao layout principal
                timeDataBaseLayout.addView(llHorizontal);

                // Armazena o valor preenchido no mapa quando o usuário digitar
                int finalHour = hour;
                et.setOnFocusChangeListener((v, hasFocus) -> {
                    if (!hasFocus) {
                        activityDataMap.put(finalHour, et.getText().toString());  // Armazena o texto do EditText
                    }
                });
            }
        }
    }


    // Método chamado quando a Activity é retomada
    @Override
    protected void onResume() {
        super.onResume();
        // Limpa os campos existentes antes de recriar os campos com base no horário atual
        timeDataBaseLayout.removeAllViews();

        // Obtém a hora atual
        Calendar calendar = Calendar.getInstance();

        // Chama o método para criar os campos dinamicamente de acordo com a hora atual
        createDynamicFields(calendar);
    }
}
