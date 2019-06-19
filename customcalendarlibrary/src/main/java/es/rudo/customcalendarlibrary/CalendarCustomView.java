package es.rudo.customcalendarlibrary;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class CalendarCustomView extends LinearLayout {
    private static final String TAG = CalendarCustomView.class.getSimpleName();
    private static final int MAX_CALENDAR_COLUMN = 42;
    private static final int NEXT_MONTH = 1;
    private static final int PREVIOUS_MONTH = -1;


    int amount = 0;

    private ImageView previousButton, nextButton;
    private TextView currentDate;
    private RecyclerView calendarGridView;


    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    private Calendar cal = Calendar.getInstance(Locale.UK);
    private Context context;
    private List<CalendarDay> selectedDays = new ArrayList<>();
    private List<EventObjects> objectsList = new ArrayList<>();


    /**
     * CustomListeners
     */
    CalendarInterfaces.ViewClicked_Position viewClicked_position;
    CalendarInterfaces.RangeSelected rangeSelected;
    CalendarInterfaces.DaySelected daySelected;
    private RecyclerView weeekGridVIew;
    /*
    private DatabaseQuery mQuery;
*/

    public CalendarCustomView(Context context) {
        super(context);
    }

    public CalendarCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initializeUILayout();


        setUpCalendarAdapter(0);
        setUpCalendarWeekAdapter();
        setPreviousButtonClickEvent();
        setNextButtonClickEvent();

        Log.d(TAG, "I need to call this method");
    }

    private void setUpCalendarWeekAdapter() {

    }

    public CalendarCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initializeUILayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout, this);
        previousButton = (ImageView) view.findViewById(R.id.previous_month);
        nextButton = (ImageView) view.findViewById(R.id.next_month);
        currentDate = (TextView) view.findViewById(R.id.display_current_date);
        calendarGridView = (RecyclerView) view.findViewById(R.id.calendar_grid);
        weeekGridVIew = (RecyclerView) view.findViewById(R.id.week_grid);
    }

    private void setPreviousButtonClickEvent() {
        previousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMonth(PREVIOUS_MONTH);
            }
        });
    }

    private void setNextButtonClickEvent() {
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMonth(NEXT_MONTH);
            }
        });
    }

    public void changeMonth(int i) {
        cal.add(Calendar.MONTH, i);
        setUpCalendarAdapter(0);
        viewClicked_position.viewClicked(i);
    }


    private void setUpCalendarAdapter(int amountDay) {
        List<CalendarDay> dayValueInCells = new ArrayList<>();

        Calendar mCal = (Calendar) cal.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);

        int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) + amount;
        if (firstDayOfTheMonth < 0)
            firstDayOfTheMonth = firstDayOfTheMonth + 7;

        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);

        sortSelectedDates();


        while (dayValueInCells.size() < MAX_CALENDAR_COLUMN) {
            CalendarDay calendarDay = new CalendarDay();
            calendarDay.date = mCal.getTime();
            calendarDay.setDateString(mCal.getTime().toString().trim());

            if (selectedDays.contains(calendarDay)) {
                calendarDay.setSelected(true);
            }
            dayValueInCells.add(calendarDay);
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        }


        Log.d(TAG, "Number of date " + dayValueInCells.size());
        String sDate = formatter.format(cal.getTime());
        currentDate.setText(sDate);

        DaysAdapter daysAdapter = new DaysAdapter(dayValueInCells, cal, objectsList, new DaysAdapter.DayClicked() {
            @Override
            public void clicked(int position, CalendarDay calendarDay, boolean isSingleSelection) {


                Calendar calendar = Calendar.getInstance();
                calendar.setTime(calendarDay.getDate());
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                daySelected.range(day, month, year, calendarDay, calendarDay.isSelected);


                if (isSingleSelection) {
                    selectedDays.clear();
                    selectedDays.add(calendarDay);
                } else {
                    if (selectedDays.contains(calendarDay)) {
                        selectedDays.remove(calendarDay);
                    } else {
                        selectedDays.add(calendarDay);
                    }
                    rangeSelected.range(selectedDays);
                    Log.e(TAG, "listAdded: " + selectedDays.size() + "");
                }


            }

            @Override
            public void listAdded(int position, List<CalendarDay> calendarDay) {


              /*  selectedDays.clear();
                selectedDays.addAll(calendarDay);*/
                Log.e(TAG, "listAdded: " + selectedDays.size() + "");
            }

            @Override
            public void selectedRange(List<CalendarDay> calendarDay) {

                selectedDays.clear();

                selectedDays.addAll(calendarDay);

                rangeSelected.range(calendarDay);

            }
        });
        calendarGridView.setLayoutManager(new GridLayoutManager(context, 7));
        calendarGridView.setAdapter(daysAdapter);
    }

    private void sortSelectedDates() {
        Collections.sort(selectedDays, new Comparator<CalendarDay>() {
            @Override
            public int compare(CalendarDay o1, CalendarDay o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
    }

    /**
     * Custom Listners
     */
    public void setOnChangeMonthListener(CalendarInterfaces.ViewClicked_Position onChangeMonthListener) {
        viewClicked_position = onChangeMonthListener;
    }

    public void setOnDateRangeListener(CalendarInterfaces.RangeSelected rangeSelected) {
        this.rangeSelected = rangeSelected;
    }

    public void setOnDayClickListener(CalendarInterfaces.DaySelected daySelected) {
        this.daySelected = daySelected;
    }

    public void firstDayOfWeek(int selectedDay) {

        List<String> listDays = Calendar_StartDay.getSelectedDay(context, selectedDay);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 7);
        weeekGridVIew.setLayoutManager(gridLayoutManager);
        CalendarWeekAdapter calendarWeekAdapter = new CalendarWeekAdapter(listDays, context);
        weeekGridVIew.setAdapter(calendarWeekAdapter);

        amount = Calendar_StartDay.getAmountDay(selectedDay);
        setUpCalendarAdapter(amount);

    }

    public void setEvents(List<EventObjects> objectsList) {
        this.objectsList.addAll(objectsList);
    }
}