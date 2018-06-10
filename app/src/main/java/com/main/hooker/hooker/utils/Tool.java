package com.main.hooker.hooker.utils;

public class Tool {
    public static String indexToType(int index) {
        switch (index) {
            case 1:
                return "Horror";
            case 2:
                return "Romance";
            case 3:
                return "Mystery";
            case 4:
                return "Comedy";
            case 5:
                return "Sci-fi";
            case 0:
            default:
                return "All";
        }
    }
}
