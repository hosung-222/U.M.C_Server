package com.example.umcmatchingcenter.domain.enums;

public enum ScheduleColor {
    WHITE("#DCDEEE"),
    RED("#FF7676"),
    ORANGE("#FFA654"),
    YELLOW("#FFE974"),
    GREEN("#70AD4E"),
    SKYBLUE("#CCE6F9"),
    BLUE("#359AE8"),
    NAVVY("#0261AA"),
    PURPLE("#747BBB");

    private final String hexColor;

    ScheduleColor(String color) {
        this.hexColor = color;
    }

    public String getColor() {
        return hexColor;
    }
}
