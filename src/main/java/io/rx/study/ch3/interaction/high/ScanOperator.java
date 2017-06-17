package io.rx.study.ch3.interaction.high;

import io.reactivex.Observable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by tdzd on 17/06/2017.
 */
public class ScanOperator {
    public static void main(String[] args) {
        Observable<CashTransfer> transfers = getCashTransfers();

        // 커다란 단일 변환
        transfers.reduce(BigDecimal.ZERO, (totalSoFar, transfer) -> totalSoFar.add(transfer.getAmount()));

        // 작고 구성하기 쉬운 변환
        transfers
                .map(CashTransfer::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // reduce == scan.takeLastOne
        transfers
                .scan(BigDecimal.ZERO, (totalSoFar, transfer) -> totalSoFar.add(transfer.getAmount()))
                .takeLast(1);

    }

    private static void factorialByScanWithInitialValue() {
        Observable<BigInteger> factorials = Observable.range(2, 100)
                .doOnNext(System.out::println)
                .scan(BigInteger.ONE, (big, cur) -> big.multiply(BigInteger.valueOf(cur)));

        factorials.subscribe(System.out::println);
    }

    private static void scan_accumulation() {
        // 받은 데이터 청크 총량을 누적하면서 매 순간 새로운 청크가 쌓일 때마다 누적 총량을 보여주는 것
        Observable<Long> progress = transferFile();

        Observable<Long> totalProgress = progress.scan((total, chunk) -> total + chunk);
    }

    private static void thread_unsafe_Add() {
        // 동시성 버그가 발생할 수 있다.

        Observable<Long> progress = transferFile();

        LongAdder total = new LongAdder();
        // 연산자 내부에서 변경되는 전역 상태를 사용한다.
        progress.subscribe(total::add);
    }

    private static Observable<Long> transferFile() {
        return Observable.just(10L);
    }

    public static Observable<CashTransfer> getCashTransfers() {
        return Observable.just(new CashTransfer(), new CashTransfer());
    }
}
