package com.arihant.edurite.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.arihant.edurite.R;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static void callPhone(Context context, String contact) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + contact));
        context.startActivity(callIntent);
    }

    public static void copyToClipboard(Context context, String contact) {
        ClipboardManager cManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData cData = ClipData.newPlainText("text", contact);
        cManager.setPrimaryClip(cData);
        Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();
    }
    public static String encodeImageBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap decodeImage(String image) {
        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public static String getCurrentTimeStamp() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());
    }

    public static String calculateDateDifference(String inputDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        try {
            Date date = sdf.parse(inputDate);
            long currentTimeMillis = System.currentTimeMillis();
            long inputTimeMillis = date.getTime();
            long diffInMillis = currentTimeMillis - inputTimeMillis;

            if (diffInMillis < 0) {
                return "Invalid date";
            }

            long seconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillis);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis);
            long hours = TimeUnit.MILLISECONDS.toHours(diffInMillis);
            long days = TimeUnit.MILLISECONDS.toDays(diffInMillis);
            long weeks = days / 7;
            long months = days / 30;

            if (seconds < 60) {
                return "Just now";
            } else if (minutes < 60) {
                return minutes == 1 ? "1 minute ago" : minutes + " mins ago";
            } else if (hours < 24) {
                return hours == 1 ? "1 hour ago" : hours + " hours ago";
            } else if (days < 7) {
                return days == 1 ? "1 day ago" : days + " days ago";
            } else if (weeks < 4) {
                return weeks == 1 ? "1 week ago" : weeks + " weeks ago";
            } else {
                return months == 1 ? "1 month ago" : months + " months ago";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Invalid Date";
    }
    public static boolean isValidEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public static void togglePassword(EditText editText, ImageView eye) {
        if (editText.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            eye.setImageResource(R.drawable.ic_eye_crossed);
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            editText.setSelection(editText.getText().length());
        } else {
            eye.setImageResource(R.drawable.ic_eye);
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            editText.setSelection(editText.getText().length());
        }
    }

}
