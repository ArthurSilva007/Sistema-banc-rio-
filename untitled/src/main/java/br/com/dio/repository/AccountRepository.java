package br.com.dio.repository;

import br.com.dio.exception.AccountNotFoundException;
import br.com.dio.model.AccountWallet;

import java.util.List;

import static br.com.dio.repository.CommonsRepositoy.CheckFundsForTransaction;

public class AccountRepository {

    private List<AccountWallet> accounts;

    public AccountWallet create(final List<String> pix, final long initialFunds){
        var newAccount = new AccountWallet (initialFunds, pix);
        accounts.add(newAccount);
        return newAccount;
    }

    public void deposit(final String pix,final long fundsAmount) {
        var target = findByPix(pix);
        target.addMoney(fundsAmount, "Depósito");
    }

    public long withdraw(final String pix, final long amount) {
        var source = findByPix(pix);
        CheckFundsForTransaction(source, amount);
        source.reduceMoney(amount);
        return amount;
    }

    public void transferMoney(final String sourcePix, final String targetPix, final long amount){
        var source = findByPix(sourcePix);
        CheckFundsForTransaction(source, amount);
        var target = findByPix(targetPix);
        var message = "pix enviado de " + sourcePix + "' para ' " + targetPix + "' '";
        target.addMoney(source.reduceMoney(amount), source.getService(), message);
}

    public AccountWallet findByPix(final String pix) {
        return accounts.stream()
                .filter(a -> a.getPix()
                        .contains(pix)).findFirst()
                .orElseThrow(() -> new AccountNotFoundException("Conta não encontrada " + pix + "não existe"));
    }

    public List<AccountWallet> List(){
        return this.accounts;
    }
}
