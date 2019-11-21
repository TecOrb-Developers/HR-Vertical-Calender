package tecorb.hafijur.calendar.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import tecorb.hafijur.calendar.Model.DBModel;
import tecorb.hafijur.calendar.R;
import tecorb.hafijur.calendar.SQLDB.HRDatabase;
import tecorb.hafijur.calendar.adapter.HRCalendarListAdapter;
import tecorb.hafijur.calendar.databinding.ActivityVerticalCalendarBinding;
import tecorb.hafijur.calendar.util.HRDateUtil;

import org.joda.time.LocalDate;

public class ActivityVerticalCalendar extends AppCompatActivity {
    ActivityVerticalCalendarBinding binding;
    private String date, month;
    private HRCalendarListAdapter HRCalendarListAdapter;
    private HRDatabase HRDatabase;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vertical_calendar);
        context = this;
        HRCalendarListAdapter = new HRCalendarListAdapter(this, listener, HRDateUtil.getTodaysDate(), null);
        HRDatabase = new HRDatabase(this);
        binding.calendarList.setAdapter(HRCalendarListAdapter);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            LocalDate selectedDate = (LocalDate) view.getTag();
            date = selectedDate.toString();
            popup();
            HRCalendarListAdapter.updateSelection(selectedDate, null);
        }
    };

    private void popup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        TextView ok = view.findViewById(R.id.okTv);
        TextView cancel = view.findViewById(R.id.cancelTv);
        final EditText fare = view.findViewById(R.id.editText);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fare.getText().toString() != null && fare.getText().toString().length() > 0 && !fare.getText().toString().equals("") && !fare.getText().toString().equals("null")) {
                    DBModel model = new DBModel(fare.getText().toString(), date);
                    HRDatabase.insertTitle(model);
                    HRCalendarListAdapter.update();
                    alertDialog.dismiss();
                } else {
                    fare.setError("Enter Event");
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

}
