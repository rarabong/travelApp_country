import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<country_name> implements Filterable {
    private Context context;
    private int resource;
    private List<country_name> originalCountries; // 원본 데이터
    private List<country_name> filteredCountries; // 필터링된 데이터
    private Filter countryFilter;

    public CustomAdapter(Context context, int resource, ArrayList<country_name> countries) {
        super(context, resource, countries);
        this.context = context;
        this.resource = resource;
        this.originalCountries = new ArrayList<>(countries);
        this.filteredCountries = new ArrayList<>(countries);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            // 리스트 뷰 아이템을 인플레이트하고 뷰 홀더를 설정
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);

            holder = new ViewHolder();
            holder.flag = convertView.findViewById(R.id.flag);
            holder.name = convertView.findViewById(R.id.name);

            convertView.setTag(holder);
        } else {
            // 이미 인플레이트된 아이템의 뷰 홀더를 재사용
            holder = (ViewHolder) convertView.getTag();
        }

        // 현재 위치의 나라 정보를 가져와서 뷰에 설정
        country_name country = getItem(position);
        holder.flag.setImageResource(country.getFlagResource());
        holder.name.setText(country.getName());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        // 필터를 반환하며, 필터가 없는 경우 새로 생성
        if (countryFilter == null) {
            countryFilter = new CountryFilter();
        }
        return countryFilter;
    }

    private class CountryFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                // 검색어가 없는 경우, 원본 데이터를 그대로 사용
                results.values = new ArrayList<>(originalCountries);
                results.count = originalCountries.size();
            } else {
                String query = constraint.toString().toLowerCase();
                List<country_name> filtered = new ArrayList<>();

                for (country_name country : originalCountries) {
                    // 검색어가 포함된 나라만 필터링
                    if (country.getName().toLowerCase().contains(query)) {
                        filtered.add(country);
                    }
                }

                results.values = filtered;
                results.count = filtered.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // 필터링된 결과를 리스트뷰에 반영
            filteredCountries = (List<country_name>) results.values;
            clear();
            addAll(filteredCountries);
            notifyDataSetChanged();
        }
    }

    static class ViewHolder {
        ImageView flag;
        TextView name;
    }
}