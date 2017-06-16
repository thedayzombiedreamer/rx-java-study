package io.rx.study.ch3;

import static io.reactivex.Observable.empty;
import static io.reactivex.Observable.just;

/**
 * Created by tdzd on 14/06/2017.
 */
public class FlatMapOperator {
    public static void main(String[] args) {
        flatMap1_firstExample();
    }

    private static void flatMap1_firstExample() {
        just(5, 6, 7, 8, 9, 10)
                .doOnNext(i -> System.out.println("IN : " + i))
                .map(x -> x * 2)
                .filter(x -> x != 10)
                .subscribe(s -> System.out.println("OUT : " + s + "\n"));

        just(5, 6, 7, 8, 9, 10)
                .doOnNext(i -> System.out.println("flatMap IN : " + i))
                .flatMap(x -> just(x * 2))
                .flatMap(x -> (x != 10) ? just(x) : empty())
                .subscribe(s -> System.out.println("flatMap OUT: " + s + "\n"));
    }
}
