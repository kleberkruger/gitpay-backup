package br.ufms.gitpay;

import java.time.LocalDateTime;

public class Transacao {

    private static long contador = 0;

    private final long id;
    private final TipoTransacao tipo;
    private final double valor;
    private final LocalDateTime dataHora;

    public Transacao(TipoTransacao tipo, double valor) {
        this.id = ++contador;
        this.tipo = tipo;
        this.valor = valor;
        this.dataHora = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public double getValor() {
        return valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}
