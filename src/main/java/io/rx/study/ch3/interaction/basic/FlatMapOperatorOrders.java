package io.rx.study.ch3.interaction.basic;

import com.google.common.collect.Lists;
import io.reactivex.Observable;

import java.util.List;

/**
 * Created by tdzd on 15/06/2017.
 */
public class FlatMapOperatorOrders {
    public static void main(String[] args) {
        Observable<Customer> customers = Observable
                .just(new Customer(), new Customer());

        customers.flatMap(customer -> Observable.fromArray(customer.getOrders().toArray(new Order[]{})));

        customers
                .map(Customer::getOrders)
                .flatMap(Observable::fromIterable);

        customers.flatMapIterable(Customer::getOrders);
    }

    private static class Customer {
        public List<Order> getOrders() {
            return Lists.newArrayList();
        }
    }

    private static class Order {
    }
}
