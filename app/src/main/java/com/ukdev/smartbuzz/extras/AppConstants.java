package com.ukdev.smartbuzz.extras;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import com.ukdev.smartbuzz.R;
import com.ukdev.smartbuzz.database.AlarmTable;

/**
 * Constants class
 * Created by Alan Camargo - June 2016
 */
public class AppConstants
{

    // --- AUDIO FILES ---
    public static final Uri AUDIO_BIRL = Uri.parse("android.resource://com.ukdev.smartbuzz/" + R.raw.birl);

    // --- DEFAULT VALUES ---
    public static final int DEFAULT_INTENT_EXTRA = 1;

    // --- INTENT ACTIONS ---
    // When the device is booted
    static final String ACTION_BOOT_COMPLETED = Intent.ACTION_BOOT_COMPLETED;
    // Creates a new alarm
    public static final String ACTION_CREATE_ALARM = "com.ukdev.smartbuzz.ACTION_CREATE_ALARM";
    // Cancels an alarm
    static final String ACTION_CANCEL_ALARM = "com.ukdev.smartbuzz.ACTION_CANCEL_ALARM";
    // Edits an alarm
    public static final String ACTION_EDIT_ALARM = "com.ukdev.smartbuzz.ACTION_EDIT_ALARM";
    // Triggers the alarm at maximum volume, vibrating and hides the snooze button
    public static final String ACTION_MAYHEM = "com.ukdev.smartbuzz.ACTION_MAYHEM";
    // Calls Sleep Checker
    static final String ACTION_SLEEP_CHECKER = "com.ukdev.smartbuzz.ACTION_SLEEP_CHECKER";
    // Snoozes an alarm
    static final String ACTION_SNOOZE = "com.ukdev.smartbuzz.ACTION_SNOOZE";
    // Triggers an alarm
    static final String ACTION_TRIGGER_ALARM = "com.ukdev.smartbuzz.ACTION_TRIGGER_ALARM";

    // --- INTENT EXTRAS ---
    public static final String EXTRA_EDIT = "ID to edit";
    public static final String EXTRA_ID = "ID";

    // --- ORDER BY METHODS ---
    public static final String ORDER_BY_ID = AlarmTable.COLUMN_ID;
    public static final String ORDER_BY_TIME = AlarmTable.COLUMN_TRIGGER_HOURS;
    public static final String ORDER_BY_TITLE = AlarmTable.COLUMN_TITLE;

    // --- OUTPUT ---
    // Vibrates for 2s and stops for 1s
    public static final long[] VIBRATION_PATTERN = new long[] { 1000, 2000 };

    // --- PERMISSIONS ---
    public static final String PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    static final int REQUEST_CODE = 1;

    // --- SYSTEM ---
    public static final int OS_VERSION = Build.VERSION.SDK_INT;

    // --- TIME ---
    public static final long TEN_SECONDS = 10000;
    public static final int ONE_MINUTE = 60000;
    public static final long ONE_SECOND = 1000;
    static final int TWO_MINUTES = 2 * ONE_MINUTE;
    static final int THREE_MINUTES = 3 * ONE_MINUTE;
    static final long FIVE_MINUTES = 5 * ONE_MINUTE;
    static final long TEN_MINUTES = 2 * FIVE_MINUTES;
    static final long FIFTEEN_MINUTES = 3 * FIVE_MINUTES;
    public static final long ONE_DAY = 86400000;

    // --- TITLES ---
    public static final String TITLE_ACADEMIA = "academia";
    public static final String TITLE_TREINO = "treino";

}
