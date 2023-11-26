import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends extends AppCompatActivity {
    private EditText destinationEditText;
    private EditText startDateEditText;
    private EditText endDateEditText;
    private Calendar calendar;
    private int year, month, day;

    private Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // EditText와 Button 초기화
        destinationEditText = findViewById(R.id.cityEditText);
        startDateEditText = findViewById(R.id.startDateEditText);
        endDateEditText = findViewById(R.id.endDateEditText);
        createButton = findViewById(R.id.createButton);

        // 'Create' 버튼 클릭 시 이벤트 처리
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 여행지, 시작일, 종료일 텍스트 가져오기
                String destination = destinationEditText.getText().toString();
                String startDate = startDateEditText.getText().toString();
                String endDate = endDateEditText.getText().toString();
            }
        });

        // 시작일과 종료일을 위한 EditText 초기화
        startDateEditText = findViewById(R.id.startDateEditText);
        endDateEditText = findViewById(R.id.endDateEditText);

        // Calendar 초기화
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        // 시작일 EditText 클릭 시 DatePickerDialog 표시
        startDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(startDateEditText);
            }
        });

        // 종료일 EditText 클릭 시 DatePickerDialog 표시
        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(endDateEditText);
            }
        });
    }

    // DatePickerDialog를 표시하는 메서드
    private void showDatePickerDialog(final EditText editText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                // 선택한 날짜를 EditText에 설정
                String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay; // 월은 0부터 시작하므로 +1
                editText.setText(selectedDate);
            }
        }, year, month, day);

        datePickerDialog.show();
    }
}