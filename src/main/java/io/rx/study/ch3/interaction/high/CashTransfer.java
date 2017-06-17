package io.rx.study.ch3.interaction.high;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by tdzd on 17/06/2017.
 */
@NoArgsConstructor
@Setter
@Getter
public class CashTransfer {
    private BigDecimal amount;
}
