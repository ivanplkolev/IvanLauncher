package com.example.ivanlauncher;

public class MainActivityObserver {

    private static MainActivity activeActivity = null;


    public static void setActiveMainActivity(MainActivity active) {
        activeActivity = active;
    }

    public static void lockScreen() {
        activeActivity.lockScreen();
    }

    public static void openCall(String number) {
        activeActivity.openCall(number);
    }

    public static void updateCall(int newState) {
        activeActivity.updateCall(newState);
    }

    public static void closeCall() {
        activeActivity.closeCall();
    }


    public static void allowRedirect() {
        activeActivity.alloeRedirect();
    }

}
