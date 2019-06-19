package es.rudo.customcalendarlibrary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarWeekAdapter extends RecyclerView.Adapter<CalendarWeekAdapter.ViewHolder> {

    private List<String> weekList;
    private Context context;

    public CalendarWeekAdapter(List<String> weekList, Context context) {
        this.weekList = weekList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_days, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.sun.setText(weekList.get(position));

    }

    @Override
    public int getItemCount() {
        return weekList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sun = itemView.findViewById(R.id.sun);

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
