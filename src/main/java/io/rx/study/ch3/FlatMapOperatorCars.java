package io.rx.study.ch3;

import io.reactivex.Observable;

/**
 * Created by tdzd on 14/06/2017.
 */
public class FlatMapOperatorCars {
    public static void main(String[] args) {
        Observable<CarPhoto> cars = cars();

        Observable<Observable<LicensePlate>> plates = cars.map(FlatMapOperatorCars::recognize);
        Observable<LicensePlate> plates2 = cars.flatMap(FlatMapOperatorCars::recognize);
    }

    static Observable<CarPhoto> cars() {
        return Observable.just(new CarPhoto());
    }

    static Observable<LicensePlate> recognize(CarPhoto carPhoto) {
        return Observable.just(new LicensePlate());
    }

    public static class CarPhoto {

    }

    public static class LicensePlate {

    }
}
