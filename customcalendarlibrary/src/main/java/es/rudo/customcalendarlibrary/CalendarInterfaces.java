package es.rudo.customcalendarlibrary;

import java.util.List;

public class CalendarInterfaces {
    public interface ViewClicked_Position {
        void viewClicked(int position);
    }

    public interface RangeSelected {
        void range(List<CalendarDay> selectedRange);
    }

    public interface DaySelected {
        void range(int day, int month, int year, CalendarDay calendarDay, boolean selected);
    }


}
