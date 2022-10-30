package apputvikling.jorber.s354410_plans.utility;

import apputvikling.jorber.s354410_plans.R;

public abstract class MonthTranslator {

    public static int translateMonth(String month) {
        switch (month) {
            case "JANUARY":
                return R.string.january;
            case "FEBRUARY":
                return R.string.february;
            case "MARCH":
                return R.string.march;
            case "APRIL":
                return R.string.april;
            case "MAY":
                return R.string.may;
            case "JUNE":
                return R.string.june;
            case "JULY":
                return R.string.july;
            case "AUGUST":
                return R.string.august;
            case "SEPTEMBER":
                return R.string.september;
            case "OCTOBER":
                return R.string.october;
            case "NOVEMBER":
                return R.string.november;
            default:
                return R.string.december;
        }
    }
}
