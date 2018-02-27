package com.example.tomek.simplenotes;

/**
 * Created by Tomek on 27.02.2018.
 */

public class Pin {
    private static String pin;

    public Pin(String pin) throws SecurityException {
        if (!pin.matches("[0-9]{4}")) {
            throw new SecurityException("Invaild string, expected only numbers");
        } else
            Pin.pin = pin;
    }

    public static String getPin() {
        return pin;
    }

    public static void setPin(String pin) throws SecurityException {
        if (!pin.matches("[0-9]{4}")) {
            throw new SecurityException("Invaild string, expected only numbers");
        } else
            Pin.pin = pin;
    }

    public static boolean isPinSet() {
        if (pin.length() > 0)
            return true;
        else
            return false;
    }
}
