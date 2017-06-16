package io.rx.study.ch3.interaction.plural;

import com.google.common.collect.Lists;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

/**
 * Created by tdzd on 16/06/2017.
 */
public class AmbOperator {
    public static void main(String[] args) throws InterruptedException {
        Observable.amb(Lists.newArrayList(
                stream(100, 17, "S"),
                stream(200, 10, "F"))
        ).subscribe(System.out::println);

        TimeUnit.SECONDS.sleep(1);
    }

    static Observable<String> stream(int initialDelay, int interval, String name) {
        return Observable
                .interval(initialDelay, interval, TimeUnit.MILLISECONDS)
                .map(x -> name + x)
                .doOnSubscribe(disposable -> System.out.print("Subscribe to " + name + "\n"))
                .doOnDispose(() -> System.out.println("Unsubscribe from " + name));
    }
}
