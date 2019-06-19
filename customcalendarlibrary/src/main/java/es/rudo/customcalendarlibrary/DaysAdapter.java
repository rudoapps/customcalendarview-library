package es.rudo.customcalendarlibrary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.ViewHolder> {

    private static boolean SINGLE_SELECTION = false;
    private static boolean RANGE_SELECTION = false;


    private Calendar currentDate;
    private List<EventObjects> allEvents;
    private List<CalendarDay> monthly_Dates;
    private List<CalendarDay> auxCalendar = new ArrayList<>();
    DayClicked dayClicked;

    int firstClickPosition = -1;
    int secoundClickPosition = -1;
    int timesClicked = 0;


    public DaysAdapter(List<CalendarDay> monthlyDates, Calendar currentDate, List<EventObjects> allEvents, DayClicked dayClicked) {
        this.monthly_Dates = monthlyDates;
        this.currentDate = currentDate;
        this.allEvents = allEvents;
        this.dayClicked = dayClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_single_cell, parent, false);
        int width = (int) (parent.getMeasuredWidth() / 6.9);
        view.setLayoutParams(new RelativeLayout.LayoutParams(width, width));

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Context innerContext = holder.item.getContext();

        if (monthly_Dates.get(position).isSelected()) {
            if (!auxCalendar.contains(monthly_Dates.get(position))) {
                auxCalendar.add(monthly_Dates.get(position));
            }
        }

        displayData(holder, position, innerContext);

        holder.innerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RANGE_SELECTION) {
                    DaysAdapter.this.rangeSelectedMode(holder, position);
                } else if (SINGLE_SELECTION) {
                    DaysAdapter.this.singleSelectionMode(holder, position);
                } else {
                    DaysAdapter.this.multiSelectionMode(holder, position);
                }
            }
        });
    }

    private void rangeSelectedMode(ViewHolder holder, int position) {


        switch (timesClicked) {
            case 0:

                for (CalendarDay monthly_date : monthly_Dates) {
                    monthly_date.setSelected(false);
                }

                firstClickPosition = holder.getAdapterPosition();
                timesClicked++;
                monthly_Dates.get(position).setSelected(true);

                notifyDataSetChanged();

                break;
            case 1:
                secoundClickPosition = holder.getAdapterPosition();
                if (secoundClickPosition > firstClickPosition) {
                    List<CalendarDay> auxSelectedDays = new ArrayList<>();
                    for (int i = firstClickPosition; i < monthly_Dates.size(); i++) {
                        if (i <= secoundClickPosition) {
                            monthly_Dates.get(i).setSelected(true);
                            auxSelectedDays.add(monthly_Dates.get(i));
                        }
                    }

                    dayClicked.selectedRange(auxSelectedDays);

                    firstClickPosition = -1;
                    secoundClickPosition = -1;
                    timesClicked = 0;


                    notifyDataSetChanged();
                } else {

                    firstClickPosition = -1;
                    secoundClickPosition = -1;
                    timesClicked = 0;


                    for (CalendarDay monthly_date : monthly_Dates) {
                        monthly_date.setSelected(false);
                    }
                    firstClickPosition = holder.getAdapterPosition();
                    timesClicked++;
                    monthly_Dates.get(position).setSelected(true);


                    notifyDataSetChanged();


                }


                break;
        }
    }

    private void singleSelectionMode(ViewHolder holder, int position) {

        for (CalendarDay monthly_date : monthly_Dates) {
            monthly_date.setSelected(false);
        }
        notifyDataSetChanged();
        monthly_Dates.get(position).setSelected(true);
        notifyItemChanged(position);

        dayClicked.clicked(position, monthly_Dates.get(position), true);

    }

    private void multiSelectionMode(ViewHolder holder, int position) {

        if (monthly_Dates.get(position).isSelected) {
            monthly_Dates.get(position).setSelected(false);
            if (auxCalendar.contains(monthly_Dates.get(position)))
                auxCalendar.remove(monthly_Dates.get(position));
        } else {
            monthly_Dates.get(position).setSelected(true);
            auxCalendar.add(monthly_Dates.get(position));
        }

        dayClicked.listAdded(position, auxCalendar);
        dayClicked.clicked(position, monthly_Dates.get(position), false);


        notifyItemChanged(position);
        if (holder.getAdapterPosition() - 1 > 0)
            notifyItemChanged(position - 1);
        if (holder.getAdapterPosition() + 1 < monthly_Dates.size())
            notifyItemChanged(position + 1);
        //  notifyDataSetChanged();
    }

    private void displayData(ViewHolder holder, int position, Context innerContext) {

        Date mDate = monthly_Dates.get(position).date;
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(mDate);
        int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCal.get(Calendar.MONTH) + 1;
        int displayYear = dateCal.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);

        if (displayMonth == currentMonth && displayYear == currentYear) {
            //Display SelectedMnth
            holder.item.setVisibility(View.VISIBLE);
            showMonthDays(holder, position, innerContext);
            holder.innerItem.setVisibility(View.VISIBLE);

        } else {
            holder.item.setVisibility(View.VISIBLE);
            holder.innerItem.setVisibility(View.GONE);
            holder.item.setBackgroundColor(ContextCompat.getColor(holder.item.getContext(), R.color.red_material));
        }


        //Add day to calendar


        holder.textDate.setText(String.valueOf(dayValue));
        //Add events to the calendar
        Calendar eventCalendar = Calendar.getInstance();
        if (allEvents != null)


            for (int i = 0; i < allEvents.size(); i++) {
                eventCalendar.setTime(allEvents.get(i).getDate());
                if (dayValue == eventCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalendar.get(Calendar.MONTH) + 1
                        && displayYear == eventCalendar.get(Calendar.YEAR)) {
                    holder.imageCircle.setVisibility(View.VISIBLE);
                    monthly_Dates.get(position).setEvent(allEvents.get(i));
           //         break;
                }else
                    holder.imageCircle.setVisibility(View.GONE);
            }


    }

    private void showMonthDays(ViewHolder holder, int position, Context innerContext) {
        holder.textDate.setTextColor(ContextCompat.getColor(innerContext, R.color.customcalendarColorWhite));
        int nextPos = position + 1;
        int lastPos = position - 1;
        int maxPos = monthly_Dates.size();
        int minPos = 0;

        if (monthly_Dates.get(position).isSelected) {
            if (nextPos < maxPos && lastPos >= 0) {
                if (monthly_Dates.get(nextPos).isSelected() && monthly_Dates.get(lastPos).isSelected()) {
                    // La posicion anterior y posterior están seleccionadas
                    holder.innerItem.setBackground(ContextCompat.getDrawable(innerContext, R.drawable.selected_day_center));
                } else if (!monthly_Dates.get(nextPos).isSelected() && monthly_Dates.get(lastPos).isSelected()) {
                    // La posicion anterior está seleccionada
                    holder.innerItem.setBackground(ContextCompat.getDrawable(innerContext, R.drawable.selected_day_right));
                } else if (monthly_Dates.get(nextPos).isSelected() && !monthly_Dates.get(lastPos).isSelected()) {
                    // La posicion posterior está seleccionada
                    holder.innerItem.setBackground(ContextCompat.getDrawable(innerContext, R.drawable.selected_day_left));
                } else {
                    // No hay seleccion anterior o posetior.
                    holder.innerItem.setBackground(ContextCompat.getDrawable(innerContext, R.drawable.selected_day_circle));

                }

            } else if (position == maxPos) {
                if (monthly_Dates.get(lastPos).isSelected) {
                    // La posicion anterior está seleccionada
                    holder.innerItem.setBackground(ContextCompat.getDrawable(innerContext, R.drawable.selected_day_right));
                } else {
                    // No hay seleccion anterior
                    holder.innerItem.setBackground(ContextCompat.getDrawable(innerContext, R.drawable.selected_day_circle));

                }
            } else if (position == minPos) {
                if (monthly_Dates.get(nextPos).isSelected) {
                    // La posicion anterior está seleccionada
                    holder.innerItem.setBackground(ContextCompat.getDrawable(innerContext, R.drawable.selected_day_left));
                } else {
                    holder.innerItem.setBackground(ContextCompat.getDrawable(innerContext, R.drawable.selected_day_circle));

                }
            }

            holder.innerItem.setVisibility(View.VISIBLE);

        } else {
            holder.innerItem.setBackground(null);
            holder.item.setBackgroundColor(ContextCompat.getColor(innerContext, R.color.red_material));
            //       holder.innerItem.setBackgroundColor(ContextCompat.getColor(innerContext, R.color.colorWhite));
            holder.textDate.setTextColor(ContextCompat.getColor(innerContext, R.color.customcalendarColorWhite));

        }
        holder.item.setBackgroundColor(ContextCompat.getColor(innerContext, R.color.red_material));
        holder.textDate.setTextColor(ContextCompat.getColor(innerContext, R.color.customcalendarColorWhite));


    }

    @Override
    public int getItemCount() {
        return monthly_Dates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        int id;

        TextView textDate = itemView.findViewById(R.id.calendar_date_id);
        TextView eventId = itemView.findViewById(R.id.event_id);
        LinearLayout eventWrapper = itemView.findViewById(R.id.event_wrapper);
        RelativeLayout item = itemView.findViewById(R.id.item);
        RelativeLayout innerItem = itemView.findViewById(R.id.innerItem);
        CircleImageView imageCircle = itemView.findViewById(R.id.image_circle);


        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public interface DayClicked {

        void clicked(int position, CalendarDay calendarDay, boolean isSingleSelection);
        void listAdded(int position, List<CalendarDay> calendarDay);
        void selectedRange(List<CalendarDay> calendarDay);

    }

}
