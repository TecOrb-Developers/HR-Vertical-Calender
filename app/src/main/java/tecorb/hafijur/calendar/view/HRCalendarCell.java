package tecorb.hafijur.calendar.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import tecorb.hafijur.calendar.Model.DBModel;
import tecorb.hafijur.calendar.R;
import tecorb.hafijur.calendar.SQLDB.HRDatabase;
import tecorb.hafijur.calendar.util.HRDateUtil;

import org.joda.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HRCalendarCell extends android.support.v7.widget.AppCompatTextView {
    Context context;

    public HRCalendarCell(Context context) {
        super(context);
        this.context = context;
    }

    public HRCalendarCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public HRCalendarCell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }

    public void populate(Calendar calendar, LocalDate startDate, LocalDate endDate) {
        HRDatabase HRDatabase = new HRDatabase(context);
        LocalDate today = HRDateUtil.getTodaysDate();
        LocalDate calendarDate = HRDateUtil.getConvertedLocalDate(calendar);
        setTag(calendarDate);
        setVisibility(View.VISIBLE);
        setBackground(getResources().getDrawable(R.drawable.background_drawable));

        List<DBModel> model = new ArrayList<>();
        model.addAll(HRDatabase.viewNotes(calendarDate.toString()));
        if (model.size() > 0) {
            for (int i = 0; i < model.size(); i++) {
                if (String.valueOf(calendarDate).equals(model.get(i).getDate())) {
                    setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)).concat("\n").concat(model.get(i).getTitle()).concat(" \u20B9"));
                    setTextColor(Color.RED);
                } else {
                    setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                    setTextColor(Color.BLACK);
                }
            }
        } else {
            setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        }
        setEnabled(!(calendarDate.isBefore(today) || calendarDate.isAfter(HRDateUtil.getMaxShowDate())));
        setActivated(today.equals(calendarDate));
        updateSelection(startDate, endDate);
    }

    public void updateSelection(LocalDate startDate, LocalDate endDate) {

        LocalDate calendarDate = (LocalDate) getTag();

        if (HRDateUtil.localDateEquals(calendarDate, startDate) && HRDateUtil.localDateEquals(calendarDate, endDate)) {
            updateCell(R.mipmap.calendar_selector_same_day, Typeface.BOLD, true);
        } else if (HRDateUtil.localDateEquals(calendarDate, startDate)) {
            int resId = endDate == null ?
                    R.drawable.bg_selected_date_background : R.mipmap.calendar_selector_green_grey;
            updateCell(resId, Typeface.BOLD, true);
            setTextColor(Color.WHITE);
        } else if (HRDateUtil.localDateEquals(calendarDate, endDate)) {
            int resId = startDate == null ?
                    R.mipmap.calendar_selector_blue : R.mipmap.calendar_selector_blue_grey;
            updateCell(resId, Typeface.BOLD, true);
        } else if (startDate != null &&
                endDate != null &&
                calendarDate.isAfter(new LocalDate(startDate)) &&
                calendarDate.isBefore(new LocalDate(endDate))) {
            updateCell(0, Typeface.NORMAL, false);
        } else {
            updateCell(R.drawable.background_drawable, HRDateUtil.getTodaysDate().equals(calendarDate) ? Typeface.BOLD : Typeface.NORMAL, false);
        }
    }

    private void updateCell(int resId, int textStyle, boolean selected) {
        setBackgroundResource(resId);
        setTypeface(null, textStyle);
        setSelected(selected);
    }


}
