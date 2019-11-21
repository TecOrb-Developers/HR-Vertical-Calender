package tecorb.hafijur.calendar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import tecorb.hafijur.calendar.R;
import tecorb.hafijur.calendar.util.HRDateUtil;
import tecorb.hafijur.calendar.view.HRCalendarCard;

import org.joda.time.LocalDate;
import org.joda.time.Months;

import java.util.Calendar;

public class HRCalendarListAdapter extends BaseAdapter {

    private final Context context;
    private final View.OnClickListener listener;
    private LocalDate departDate;
    private LocalDate returnDate;

    public HRCalendarListAdapter(Context context, View.OnClickListener listener, LocalDate departDate, LocalDate returnDate) {
        this.context = context;
        this.listener = listener;
        this.departDate = departDate;
        this.returnDate = returnDate;
    }

    @Override
    public int getCount() {
        return Months.monthsBetween(HRDateUtil.getTodaysDate(), HRDateUtil.getMaxShowDate()).getMonths() + 1;
    }

    @Override
    public Calendar getItem(int position) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, position);
        return calendar;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        HRCalendarCard calendarView;
        if (convertView == null) {
            calendarView = (HRCalendarCard) LayoutInflater.from(context).inflate(R.layout.calendar_card_view, viewGroup, false);
            calendarView.setOnClickListener(listener);
        } else {
            calendarView = (HRCalendarCard) convertView;
        }
        calendarView.populate(getItem(position), departDate, returnDate);
        return calendarView;
    }

    public void updateSelection(LocalDate departDate, LocalDate returnDate) {
        this.departDate = departDate;
        this.returnDate = returnDate;
        this.notifyDataSetChanged();
    }

    public void update()
    {
        this.notifyDataSetChanged();
    }
}
