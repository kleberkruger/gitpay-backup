package br.ufms.gitpay;

import java.time.LocalDate;
import java.util.List;

public interface InterfaceContaBancaria {

    NumeroBancario getNumero();

    Usuario<? extends Pessoa> getUsuario();

    double getSaldo();

    void depositar(double valor);

    void sacar(double valor);

    void transferir(ContaBancaria destino, double valor);

    List<Transacao> getTransacoes(LocalDate dataInicial, LocalDate dataFinal);

    default List<Transacao> getTransacoes(LocalDate dataInicial) {
        return getTransacoes(dataInicial, LocalDate.now());
    }

    default List<Transacao> getTransacoes(LocalDate dataInicial, TipoTransacao tipo) {
        return getTransacoes(dataInicial, LocalDate.now(), tipo);
    }

    default List<Transacao> getTransacoes(LocalDate dataInicial, LocalDate dataFinal, TipoTransacao tipo) {
        return getTransacoes(dataInicial, dataFinal).stream().filter(t -> t.getTipo() == tipo).toList();
    }
}
