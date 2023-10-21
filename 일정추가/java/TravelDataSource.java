import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TravelDataSource {
    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public TravelDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // 여행 정보 저장
    public long createTravelInfo(String destination, String startDate, String endDate) {
        ContentValues values = new ContentValues();
        values.put("destination", destination);
        values.put("start_date", startDate);
        values.put("end_date", endDate);

        return database.insert("Travel", null, values);
    }
}