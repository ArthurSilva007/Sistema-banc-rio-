package br.com.dio.repository;

import br.com.dio.exception.NofundsEnoughException;
import br.com.dio.model.AccountWallet;
import br.com.dio.model.Money;
import br.com.dio.model.MoneyAudit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static br.com.dio.model.BankService.ACCOUNT;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonsRepositoy {

    public static void CheckFundsForTransaction(final AccountWallet source, final long amount) {
        if (source.getFunds() < amount){
            throw new NofundsEnoughException("Saldo insuficiente");
        }
    }

    public static List<Money> generateMoney(final UUID transactionid, final long funds, final String description ) {
        var history = new MoneyAudit(transactionid, ACCOUNT, description, OffsetDateTime.now());
        return Stream.generate(() -> new Money(history)).limit(funds).toList();
    }


}
