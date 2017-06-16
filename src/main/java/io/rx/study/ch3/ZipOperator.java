package io.rx.study.ch3;


import io.reactivex.Observable;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

/**
 * Created by tdzd on 15/06/2017.
 */
public class ZipOperator {
    public static void main(String[] args) throws InterruptedException {
        zip4_combineLatest();
    }

    private static void zip4_combineLatest() throws InterruptedException {
        Observable.combineLatest(
                Observable.interval(17, TimeUnit.MILLISECONDS).map(x -> String.format("S%d", x)),
                Observable.interval(10, TimeUnit.MILLISECONDS).map(x -> String.format("F%d", x)),
                (s, f) -> f + ":" + s
        ).forEach(System.out::println);

        TimeUnit.SECONDS.sleep(5);
    }

    private static void zip3_syncedProcess() throws InterruptedException {
        Observable<Long> red = Observable.interval(10, TimeUnit.MILLISECONDS);
        Observable<Long> green = Observable.interval(1000, TimeUnit.MILLISECONDS);

        Observable.zip(
                red.timestamp(),
                green.timestamp(),
                (r, g) -> r.time() - g.time()
        ).forEach(System.out::println);

        TimeUnit.SECONDS.sleep(5);
    }

    private static void delay2_flightExample() {
        Observable<LocalDate> nextTenDays = Observable
                .range(1, 10)
                .map(i -> LocalDate.now().plusDays(i));

        Observable<Vacation> possibleVacations = Observable
                .just(City.Warsaw, City.London, City.Paris)
                .flatMap(city -> nextTenDays.map(date -> new Vacation(city, date)))
                .flatMap(vacation ->
                        Observable.zip(
                                vacation.weather().filter(Weather::isSunny),
                                vacation.cheapFlightFrom(City.NewYork),
                                vacation.cheapHotel(),
                                (w, f, h) -> vacation));
    }

    static class Vacation {
        private final City where;
        private final LocalDate when;

        Vacation(City where, LocalDate when) {
            this.where = where;
            this.when = when;
        }

        public Observable<Weather> weather() {
            //
            return Observable.just(new Weather());
        }

        public Observable<Flight> cheapFlightFrom(City from) {
            //
            return Observable.just(new Flight());
        }

        public Observable<Hotel> cheapHotel() {
            //
            return Observable.just(new Hotel());
        }
    }

    static class Weather {
        boolean isSunny() {
            return false;
        }
    }

    static class Flight {}
    static class Hotel {}

    enum City {
        Warsaw,
        London,
        Paris,
        NewYork
    }

    private static void zip1_oneToOne() {
        Observable<Integer> oneToEight = Observable.range(1, 8);

        Observable<String> ranks = oneToEight
                .map(Object::toString);

        Observable<String> files = oneToEight
                .map(x -> 'a' + x - 1)
                .map(ascii -> (char) ascii.intValue())
                .map(ch -> Character.toString(ch));

        Observable<String> squares = files
                .flatMap(file -> ranks.map(rank -> file + rank));

        squares.subscribe(System.out::println);
    }

}
