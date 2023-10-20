import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class country extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_main);

    }

    public void onBackButtonClick(View view) {
        onBackPressed(); // 뒤로가기 동작 실행

    }
}