import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<country_name> {
    private Context context;
    private int resource;
    private ArrayList<country_name> countries;

    // 생성자: 커스텀 어댑터를 초기화
    public CustomAdapter(Context context, int resource, ArrayList<country_name> countries) {
        super(context, resource, countries);
        this.context = context;
        this.resource = resource;
        this.countries = countries;
    }

    // 각 항목의 뷰를 생성하고 설정
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            // 레이아웃 인플레이션
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);

            holder = new ViewHolder();
            holder.flag = convertView.findViewById(R.id.flag); // 이미지뷰
            holder.name = convertView.findViewById(R.id.name); // 텍스트뷰

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        country_name country = countries.get(position);
        // 이미지뷰와 텍스트뷰에 데이터 설정
        holder.flag.setImageResource(country.getFlagResource());
        holder.name.setText(country.getName());

        return convertView;
    }

    // 뷰 홀더 패턴을 사용하여 뷰 홀더 클래스 정의
    static class ViewHolder {
        ImageView flag;
        TextView name;
    }
}