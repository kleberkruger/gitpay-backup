package br.ufms.gitpay;

import java.time.LocalDate;

public class PessoaFisica extends Pessoa {

    private final String cpf;
    private LocalDate dataNascimento;

    public PessoaFisica(String nome, String cpf) {
        super(nome);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
