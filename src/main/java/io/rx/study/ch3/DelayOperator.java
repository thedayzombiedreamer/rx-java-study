package io.rx.study.ch3;

import io.reactivex.Observable;

import java.time.DayOfWeek;
import java.util.concurrent.TimeUnit;

/**
 * Created by tdzd on 15/06/2017.
 */
public class DelayOperator {
    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(15);
    }

    private static void delay5_concatMapWithIntendedOrder() {
        Observable
                .just(DayOfWeek.SUNDAY, DayOfWeek.MONDAY)
                .concatMap(DelayOperator::loadRecordsFor)
                .subscribe(System.out::println);
    }

    private static void delay4_flatMapWithFlippedOrder() {
        Observable
                .just(DayOfWeek.SUNDAY, DayOfWeek.MONDAY)
                .flatMap(DelayOperator::loadRecordsFor)
                .subscribe(System.out::println);
    }

    private static Observable<String> loadRecordsFor(DayOfWeek dow) {
        switch (dow) {
            case SUNDAY:
                return Observable
                        .interval(90, TimeUnit.MILLISECONDS)
                        .take(5)
                        .map(i -> "SUN-" + i);
            case MONDAY:
                return Observable
                        .interval(65, TimeUnit.MILLISECONDS)
                        .take(5)
                        .map(i -> "MON-" + i);
        }

        return Observable.just("");
    }

    private static void delay3_flippedOrder() {
        Observable
                .just(10L, 1L)
                .flatMap(x -> Observable.just(x).delay(x, TimeUnit.SECONDS))
                .subscribe(System.out::println);
    }

    private static void delay2_withFlatMap() {
        Observable
                .just("Loream", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit")
                .flatMap(word -> Observable.timer(word.length(), TimeUnit.SECONDS).map(x -> word))
                .subscribe(System.out::println);
    }

    private static void delay1_withoutFlatMap() throws InterruptedException {
        Observable
                .just("Loream", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit")
                .delay(word -> Observable.timer(word.length(), TimeUnit.SECONDS))
                .subscribe(System.out::println);
    }
}
