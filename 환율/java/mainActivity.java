import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;

public class mainActivity extends AppCompatActivity {

    static final int listCount = 9;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_main);

        ListView listView = findViewById(R.id.listView);

        ArrayList<country_name> countries = new ArrayList<>();
        countries.add(new country_name("미국", R.drawable.america));
        countries.add(new country_name("영국", R.drawable.america));
        countries.add(new country_name("일본", R.drawable.america));
        countries.add(new country_name("베트남", R.drawable.america));
        countries.add(new country_name("대만", R.drawable.america));
        countries.add(new country_name("중국", R.drawable.america));
        countries.add(new country_name("영인이", R.drawable.america));
        countries.add(new country_name("영인이네 나라", R.drawable.america));
        countries.add(new country_name("재준이나라", R.drawable.america));
        countries.add(new country_name("재준국", R.drawable.america));
        countries.add(new country_name("전재준국", R.drawable.america));
        countries.add(new country_name("영인재준나라", R.drawable.america));

        // 다른 국가 데이터 추가

        CustomAdapter adapter = new CustomAdapter(this, R.layout.list_item, countries);
        listView.setAdapter(adapter);

    }
    
    public void onBakButtonClick(View view) {
        onBackPressed(); // 뒤로가기 동작 실행

    }
}