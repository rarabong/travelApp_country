import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.appcompat.widget.SwitchCompat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText amountEditText;
    private SwitchCompat switchCompat;
    private String[] currencyList = {"KRW", "USD", "EUR", "CAD"};
    private Task task; // Task 객체를 멤버 변수로 선언하여 AsyncTask의 결과를 저장

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amountEditText = findViewById(R.id.amount_Edt);
        switchCompat = findViewById(R.id.sw_onoff);

        Spinner currencySpinner = findViewById(R.id.spinner);
        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                // 선택된 화폐의 코드를 가져옴
                String selectedCurrency = currencyList[position];

                // 환율 정보 가져오기
                Task task = new Task();
                task.execute(selectedCurrency, "KRW");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencyList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(adapter);

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "알림이 설정되었습니다.", Toast.LENGTH_SHORT).show();
                    createNotification();
                } else {
                    Toast.makeText(getApplicationContext(), "알림이 해제되었습니다.", Toast.LENGTH_SHORT).show();
                    removeNotification();
                }
            }
        });

        // EditText 입력 값이 변경될 때마다 비교하도록 TextWatcher 추가
        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (task != null && task.getStatus() == AsyncTask.Status.FINISHED) {
                    double convertedAmount = task.getConvertedAmount(); // AsyncTask에서 가져온 환산된 한국 원 100원당 금액

                    String editTextValue = amountEditText.getText().toString();

                    if (!editTextValue.isEmpty()) {
                        try {
                            double enteredValue = Double.parseDouble(editTextValue);

                            // 입력된 값이 환산된 값보다 작거나 같을 때만 알림 생성
                            if (convertedAmount <= enteredValue * 100) {
                                createNotification();
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            // 숫자로 변환할 수 없는 경우에 대한 예외 처리
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private class Task extends AsyncTask<String, Void, Double> {
        private String clientKey = "fca_live_HJ3qZNnDbou0ArS6LQrEPUvNv8z0ZcfoG5XrHNcu";
        private String str, receiveMsg;
        private double currencyRate;

        @Override
        protected Double doInBackground(String... params) {
            String[] resultArr = new String[3];

            String from = params[0];
            String to = params[1];
            URL url = null;
            try {
                url = new URL("https://api.freecurrencyapi.com/v1/latest?apikey="
                        + clientKey
                        + "&currencies="
                        + from
                        + "," + to);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                    reader.close();
                } else {
                    Log.e("통신 결과", conn.getResponseCode() + "에러");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                JsonParser jsonParser = new JsonParser();
                Object obj = jsonParser.parse(receiveMsg);

                JsonObject jsonObj = (JsonObject) obj;

                JsonObject curobj = (JsonObject) jsonObj.get("data");

                String a = curobj.get(from).toString();
                String b = curobj.get(to).toString();

                double currencyRate = Double.parseDouble(b) / Double.parseDouble(a);

                double krwAmount = 100; // 한국 원 100원당 환산 금액
                double convertedAmount = currencyRate * krwAmount; // 선택한 환율에 따른 한국 원 100원당 환산 금액


            } catch (JsonParseException e) {
                e.printStackTrace();
            }
            return currencyRate;
        }

        public double getConvertedAmount() {
            return currencyRate;
        }
    }

    private void createNotification() {

        // EditText에서 값을 가져옴
        String editTextValue = amountEditText.getText().toString();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("환율 알림");
        builder.setContentText("환율이 " + editTextValue + " 원이 되었어요!");

        builder.setColor(Color.RED);
        // 사용자가 탭을 클릭하면 자동 제거
        builder.setAutoCancel(true);

        // 알림 표시
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }

        // id값은 정의해야하는 각 알림의 고유한 int값
        notificationManager.notify(1, builder.build());
    }
    private void removeNotification() {
        // Notification 제거
        NotificationManagerCompat.from(this).cancel(1);
    }

}