package com.xht.demo.handler;


import java.time.LocalDate;

public abstract class CalendarHandler {


    abstract void execute(LocalDate date, CalculationType calculationType);

    class aa {
        boolean flag;
        LocalDate date;
        CalculationType calculationType;
    }
}
