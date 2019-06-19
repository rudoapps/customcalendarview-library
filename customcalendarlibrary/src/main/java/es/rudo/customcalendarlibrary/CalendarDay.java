package es.rudo.customcalendarlibrary;

import java.io.Serializable;
import java.util.Date;

public class CalendarDay implements Serializable {
    Date date;
    boolean isSelected;
    String dateString;
    EventObjects event;


    public CalendarDay() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }


    public EventObjects getEvent() {
        return event;
    }

    public void setEvent(EventObjects event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CalendarDay that = (CalendarDay) o;

        return dateString != null ? dateString.equals(that.dateString) : that.dateString == null;
    }

    @Override
    public int hashCode() {
        return dateString != null ? dateString.hashCode() : 0;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
