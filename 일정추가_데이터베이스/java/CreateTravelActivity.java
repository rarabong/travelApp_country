import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class CreateTravelActivity extends AppCompatActivity {
    private EditText destinationEditText;
    private EditText startDateEditText;
    private EditText endDateEditText;
    private Calendar calendar;
    private int year, month, day;

    private Button createButton;
    private TravelDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new TravelDataSource(this);
        dataSource.open();

        destinationEditText = findViewById(R.id.cityEditText);
        startDateEditText = findViewById(R.id.startDateEditText);
        endDateEditText = findViewById(R.id.endDateEditText);
        createButton = findViewById(R.id.createButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String destination = destinationEditText.getText().toString();
                String startDate = startDateEditText.getText().toString();
                String endDate = endDateEditText.getText().toString();

                long result = dataSource.createTravelInfo(destination, startDate, endDate);

                if (result != -1) {
                    // 데이터베이스에 저장 성공
                    Toast.makeText(CreateTravelActivity.this, "여행 정보가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    // 저장 실패
                    Toast.makeText(CreateTravelActivity.this, "여행 정보 저장 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // EditText 위젯 가져오기
        startDateEditText = findViewById(R.id.startDateEditText);
        endDateEditText = findViewById(R.id.endDateEditText);

        // Calendar 초기화
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        // startDateEditText 클릭 시 DatePickerDialog 표시
        startDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(startDateEditText);
            }
        });

        // endDateEditText 클릭 시 DatePickerDialog 표시
        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(endDateEditText);
            }
        });

        
    }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }
}