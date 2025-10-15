package br.com.dio.exception;

public class NofundsEnoughException extends RuntimeException
{
    public NofundsEnoughException(String message) {
        super (message);
    }
}
