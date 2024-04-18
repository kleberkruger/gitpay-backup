package br.ufms.gitpay;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ContaBancaria implements InterfaceContaBancaria {

    private static int contador = 0;

    protected final NumeroBancario numero;
    protected final Usuario<? extends Pessoa> usuario;
    protected double saldo;
    protected final List<Transacao> transacoes;

    protected ContaBancaria(Usuario<? extends Pessoa> usuario) {
        this.numero = NumeroBancario.of(++contador, NumeroBancario.digito(contador));
        this.usuario = usuario;
        this.saldo = 0;
        this.transacoes = new ArrayList<>();
    }

    @Override
    public NumeroBancario getNumero() {
        return numero;
    }

    @Override
    public Usuario<? extends Pessoa> getUsuario() {
        return usuario;
    }

    @Override
    public double getSaldo() {
        return saldo;
    }

    @Override
    public List<Transacao> getTransacoes(LocalDate dataInicial, LocalDate dataFinal) {
        LocalDateTime dataHoraInicio = LocalDateTime.of(dataInicial, LocalTime.MIN);
        LocalDateTime dataHoraFinal = LocalDateTime.of(dataFinal, LocalTime.MAX);

        return transacoes.stream()
                .filter(t -> !t.getDataHora().isBefore(dataHoraInicio) && !t.getDataHora().isAfter(dataHoraFinal))
                .toList();
    }

    @Override
    public void depositar(double valor) {
        this.saldo += valor;

        this.transacoes.add(new Transacao(TipoTransacao.DEPOSITO, valor));
    }

    @Override
    public void sacar(double valor) {
        if (valor > this.saldo) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        this.saldo -= valor;

        this.transacoes.add(new Transacao(TipoTransacao.SAQUE, valor));
    }

    @Override
    public void transferir(ContaBancaria destino, double valor) {
        this.depositar(valor);
        destino.depositar(valor);

        this.transacoes.add(new Transacao(TipoTransacao.TRANSFERENCIA, valor));
    }
}
