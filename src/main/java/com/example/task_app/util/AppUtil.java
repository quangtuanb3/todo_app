
package com.example.task_app.util;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class AppUtil {
    public static final ModelMapper mapper;

    static {
        mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Converter<String, LocalDate> toStringDate = new AbstractConverter<>() {
            @Override
            protected LocalDate convert(String source) {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(source, format);
            }
        };
        Converter<String, LocalTime> toStringTime = new AbstractConverter<>() {
            @Override
            protected LocalTime convert(String source) {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                return LocalTime.parse(source, format);
            }
        };
        Converter<String, LocalDateTime> toStringDateTime = new AbstractConverter<>() {
            @Override
            protected LocalDateTime convert(String source) {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                return LocalDateTime.parse(source, format);
            }
        };
        Converter<LocalTime, LocalDateTime> timeToDateTime = new AbstractConverter<>() {

            @Override
            protected LocalDateTime convert(LocalTime localTime) {
                return LocalDate.now().atTime(localTime);
            }

        };

        Converter<LocalDateTime, LocalTime> toTimeDateTime = new AbstractConverter<>() {
            @Override
            protected LocalTime convert(LocalDateTime source) {
                return source.toLocalTime();
            }
        };
        mapper.createTypeMap(String.class, LocalDate.class);
        mapper.addConverter(toStringDate);
        mapper.addConverter(toStringDateTime);
        mapper.addConverter(toTimeDateTime);
        mapper.addConverter(toStringTime);
        mapper.addConverter(timeToDateTime);
    }
    public static LocalDate[] getWeekBoundaries(LocalDate date) {
        LocalDate[] boundaries = new LocalDate[2];
        LocalDate firstDayOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate lastDayOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
        boundaries[0] = firstDayOfWeek;
        boundaries[1] = lastDayOfWeek;
        return boundaries;
    }
    public static int getCurrentWeekNumber() {
        LocalDate currentDate = LocalDate.now();

        Locale locale = Locale.getDefault();
        WeekFields weekFields = WeekFields.of(locale);

        return currentDate.get(weekFields.weekOfWeekBasedYear());
    }
    public static LocalDate[] getStartAndEndDatesFromWeek(String weekYear) {
        LocalDate[] result = new LocalDate[2];
        int year = Integer.parseInt(weekYear.substring(0, 4));
        int week = Integer.parseInt(weekYear.substring(6));

        LocalDate startOfWeek = LocalDate.of(year, 1, 1)
                .with(TemporalAdjusters.firstDayOfYear())
                .plusWeeks(week)
                .with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));

        LocalDate endOfWeek = startOfWeek.plusDays(6);

        result[0] = startOfWeek;
        result[1] = endOfWeek;

        return result;
    }

}

