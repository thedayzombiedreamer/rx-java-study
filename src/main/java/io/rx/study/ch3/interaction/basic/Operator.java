package io.rx.study.ch3.interaction.basic;

import io.reactivex.Observable;

/**
 * Created by tdzd on 14/06/2017.
 */
public class Operator {

    public static void main(String[] args) {

    }

    private static void map1_withoutSubscribe() {
        Observable
                .just(8, 9, 10)
                .doOnNext(i -> System.out.println("0) just step : " + i))
                .filter(i -> i % 3 > 0)
                .doOnNext(i -> System.out.println("1) filter step : " + i))
                .map(i -> "#" + i * 10)
                .doOnNext(i -> System.out.println("2) map step : " + i))
                .filter(s -> s.length() < 4);
    }

    private static void map2_withSubscribe() {
        Observable
                .just(8, 9, 10)
                .doOnNext(i -> System.out.println("0) just step : " + i))
                .filter(i -> i % 3 > 0)
                .doOnNext(i -> System.out.println("1) filter step : " + i))
                .map(i -> "#" + i * 10)
                .doOnNext(i -> System.out.println("2) map step : " + i))
                .filter(s -> s.length() < 4)
                .subscribe(s -> System.out.println("3) final : " + s));
    }
}
