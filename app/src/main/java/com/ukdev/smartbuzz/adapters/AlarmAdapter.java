package com.ukdev.smartbuzz.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.ukdev.smartbuzz.database.AlarmRepository;
import com.ukdev.smartbuzz.extras.AlarmHandler;
import com.ukdev.smartbuzz.extras.AppConstants;
import com.ukdev.smartbuzz.extras.FrontEndTools;
import com.ukdev.smartbuzz.extras.BackEndTools;
import com.ukdev.smartbuzz.model.Alarm;
import com.ukdev.smartbuzz.R;

import java.util.Calendar;

/**
 * Alarm adapter
 * Enables the rendering of custom ListView items holding alarms
 * Created by Alan Camargo - April 2016
 */
public class AlarmAdapter extends ArrayAdapter<Alarm>
{

    private Context context;
    private int layoutResourceId;
    private Alarm[] data = null;

    public AlarmAdapter(Context context, int layoutResourceId, Alarm[] data)
    {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        AlarmHolder holder = new AlarmHolder();
        View row = convertView;
        if (row == null)
        {
            LayoutInflater inflater =
                    ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder.title = (TextView)row.findViewById(R.id.titleRow);
            holder.triggerTime = (TextView)row.findViewById(R.id.triggerTimeRow);
            holder.repetition = (TextView)row.findViewById(R.id.repetitionRow);
            holder.reminderIcon = (ImageView)row.findViewById(R.id.reminderIcon);
            holder.alarmToggleButton = (ToggleButton)row.findViewById(R.id.alarmToggleButton);
            holder.sunMoonImg = (ImageView)row.findViewById(R.id.sunMoonImg);
            row.setTag(holder);
        }
        else
            holder = (AlarmHolder)row.getTag();
        final Alarm alarm = data[position];
        holder.title.setText(alarm.getTitle());
        int hours = alarm.getTriggerTime().get(Calendar.HOUR_OF_DAY);
        int minutes = alarm.getTriggerTime().get(Calendar.MINUTE);
        String h = String.valueOf(hours);
        String min = String.valueOf(minutes);
        if (hours < 10)
            h = "0" + h;
        if (minutes < 10)
            min = "0" + min;
        String triggerTime = String.format("%1$s:%2$s", h, min);
        holder.triggerTime.setText(triggerTime);
        if (alarm.repeats())
            holder.repetition.setText(BackEndTools.convertIntArrayToString(context,
                alarm.getRepetition()));
        else
            holder.repetition.setVisibility(View.GONE);
        if (alarm.getTriggerTime().get(Calendar.HOUR_OF_DAY) >= 6 &&
                alarm.getTriggerTime().get(Calendar.HOUR_OF_DAY) < 18) // Day time
            holder.sunMoonImg.setImageResource(R.drawable.sun);
        else // Night time
            holder.sunMoonImg.setImageResource(R.drawable.moon);
        if (alarm.isReminder())
            holder.reminderIcon.setVisibility(View.VISIBLE);
        else
            holder.reminderIcon.setVisibility(View.GONE);
        if (alarm.isOn())
        {
            holder.alarmToggleButton.setChecked(true);
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.green));
        }
        else
        {
            holder.alarmToggleButton.setChecked(false);
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
        if (holder.alarmToggleButton.isChecked())
        {
            holder.alarmToggleButton.setBackgroundResource(R.drawable.alarm_toggle_button_selected);
            alarm.toggle(true);
        }
        else
        {
            holder.alarmToggleButton.setBackgroundResource(R.drawable.alarm_toggle_button);
            alarm.toggle(false);
        }
        final AlarmHolder finalHolder = holder;
        holder.alarmToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
            {
                if (isChecked)
                {
                    alarm.toggle(true);
                    AlarmHandler.scheduleAlarm(context, alarm);
                    compoundButton.setBackgroundResource(R.drawable.alarm_toggle_button_selected);
                    compoundButton.setChecked(true);
                    finalHolder.title.setTextColor(ContextCompat.getColor(context,
                            R.color.green));
                }
                else
                {
                    if (alarm.isLocked())
                    {
                        FrontEndTools.showToast(context,
                                String.format(context.getString(R.string.alarm_locked),
                                        alarm.getTitle()),
                                Toast.LENGTH_LONG);
                        if (AppConstants.OS_VERSION >= Build.VERSION_CODES.LOLLIPOP)
                            compoundButton.setChecked(true);
                        else
                            compoundButton.setVisibility(View.GONE);
                    }
                    else
                    {
                        if (AppConstants.OS_VERSION < Build.VERSION_CODES.LOLLIPOP
                            && compoundButton.getVisibility() == View.GONE)
                            compoundButton.setVisibility(View.VISIBLE);
                        alarm.toggle(false);
                        AlarmHandler.cancelAlarm(context, alarm);
                        compoundButton.setBackgroundResource(R.drawable.alarm_toggle_button);
                        compoundButton.setChecked(false);
                        finalHolder.title.setTextColor(ContextCompat.getColor(context,
                                R.color.red));
                    }
                }
                if (!alarm.isLocked())
                {
                    AlarmRepository.getInstance(context).update(context, alarm.getId(), alarm);
                    FrontEndTools.showNotification(context);
                }
            }
        });
        return row;
    }

    /**
     * Holds all fields for the custom ListView item
     */
    private static class AlarmHolder
    {
        TextView title;
        TextView triggerTime;
        TextView repetition;
        ImageView reminderIcon;
        ToggleButton alarmToggleButton;
        ImageView sunMoonImg;
    }

}
