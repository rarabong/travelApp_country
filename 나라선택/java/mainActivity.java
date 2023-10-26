import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class mainActivity extends AppCompatActivity {

    ListView listView;
    CustomAdapter adapter;
    ArrayList<country_name> countries;

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
        countries.add(new country_name("나라1", R.drawable.america));
        countries.add(new country_name("나라2", R.drawable.america));
        countries.add(new country_name("나라3", R.drawable.america));
        countries.add(new country_name("나라4", R.drawable.america));
        countries.add(new country_name("나라5", R.drawable.america));
        countries.add(new country_name("나라6", R.drawable.america));

        // 다른 국가 데이터 추가

        CustomAdapter adapter = new CustomAdapter(this, R.layout.list_item, countries);
        listView.setAdapter(adapter);

        // SearchView 설정
        SearchView searchView = findViewById(R.id.serch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 사용자가 쿼리를 제출할 때 동작을 수행할 수 있습니다.
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 사용자가 입력할 때 ListView 데이터를 필터링합니다.
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    public void onBakButtonClick(View view) {
        onBackPressed(); // 뒤로 가기
    }
}