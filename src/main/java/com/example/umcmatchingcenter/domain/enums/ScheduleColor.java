package com.example.umcmatchingcenter.domain.enums;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.MatchingHandler;

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

    public String getHexColor() {
        return hexColor;
    }

    public static ScheduleColor fromHexCode(String hexCode) {
        for (ScheduleColor color : values()) {
            if (color.getHexColor().equalsIgnoreCase(hexCode)) {
                return color;
            }
        }
        throw new MatchingHandler(ErrorStatus.MATCHINGSCHEDULE_COLOR_NOT_EXIST);
    }

}
