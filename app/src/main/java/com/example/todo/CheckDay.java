package com.example.todo;

public class CheckDay {

    static int checkLeap(int y)
    {
        if ((y % 4 == 0 && y % 100 != 0) || (y % 400 == 0))
            return 1;
        else
            return 0;
    }
    static String dayOfWeek(int dd,int mm,int yyyy){

        // Checking Leap year. If true then 1 else 0.
        int flag_for_leap = checkLeap(yyyy);

        /*Declaring and initialising the given informations
         * and arrays*/
        String day[] = { "Sun",    "Mon",   "Tue",
                "Wed", "Thu", "Fri",
                "Sat" };
        int m_no[] = { 0, 3, 3, 6, 1, 4, 6, 2, 5, 0, 3, 5 };

        /*Generalised check to find any Year Value*/
        int j;
        if ((yyyy / 100) % 2 == 0) {
            if ((yyyy / 100) % 4 == 0)
                j = 6;
            else
                j = 2;
        }
        else {
            if (((yyyy / 100) - 1) % 4 == 0)
                j = 4;
            else
                j = 0;
        }

        /*THE FINAL FORMULA*/
        int total = (yyyy % 100) + ((yyyy % 100) / 4) + dd
                + m_no[mm - 1] + j;
        if (flag_for_leap == 1) {
            if ((total % 7) > 0)
                return day[(total % 7) - 1];
            else
                return day[6];
        }
        else
            return day[(total % 7)];
    }

}
