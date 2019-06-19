//package es.rudo.customcalendarlibrary;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//public class CalendarActivity extends AppCompatActivity {
//
//    @BindView(R.id.calendar)
//    CalendarCustomView calendar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_calendar);
//        ButterKnife.bind(this);
//
//
//        List<EventObjects> objectsList = new ArrayList<>();
//        Calendar calendarInstance = Calendar.getInstance();
//        EventObjects eventObjects = new EventObjects("Evento del dia " + calendarInstance.get(Calendar.DAY_OF_MONTH), calendarInstance.getTime());
//        objectsList.add(eventObjects);
//
//        calendar.firstDayOfWeek(Calendar.FRIDAY);
//        calendar.setEvents(objectsList);
//
//        calendar.setOnDateRangeListener(new CalendarInterfaces.RangeSelected() {
//            @Override
//            public void range(List<CalendarDay> selectedRange) {
//                for (CalendarDay calendarDay : selectedRange) {
//
//                    Log.e("POSITIION", "viewClicked: " + calendarDay.getDate().toString());
//
//                }
//
//            }
//        });
//
//        calendar.setOnDayClickListener((day, month, year, calendarDay, isSelected) -> {
//            Log.e("DAY_SELECTED", "range: " + day + "  " + month + "  " + year + "  " + calendarDay);
//            if (calendarDay.getEvent() != null) {
//                Toast.makeText(getApplicationContext(),"EVENTO SELECCIONADO: "+ calendarDay.getEvent().getMessage(), Toast.LENGTH_LONG).show();
//            }
//
//        });
//
//
//    }
//}
