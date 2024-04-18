package br.ufms.gitpay;

import java.util.ArrayList;
import java.util.List;

public class Banco {

    public static final Banco GitPay = new Banco(666, "GitPay");

    private final int codigo;
    private final String nome;
    private final List<ContaBancaria> contas;

    public Banco(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
        this.contas = new ArrayList<>();
    }

    public ContaBancaria criarConta(Usuario<? extends Pessoa> usuario) {
        if (getConta(usuario.getLogin()) != null) {
            throw new IllegalArgumentException("Usuário já cadastrado");
        }
        ContaBancaria conta = new ContaBancaria(usuario);
        contas.add(conta);

        return conta;
    }

    public void excluirConta(ContaBancaria conta) {
        if (!contas.contains(conta)) {
            throw new IllegalArgumentException("Conta inexistente");
        }
        contas.remove(conta);
    }

    public ContaBancaria getConta(NumeroBancario numero) {
        for (ContaBancaria conta : contas) {
            if (conta.getNumero().equals(numero)) {
                return conta;
            }
        }
        return null;
    }

    public ContaBancaria getConta(String cpfCnpj) {
        for (ContaBancaria conta : contas) {
            if (conta.getUsuario().getLogin().equals(cpfCnpj)) {
                return conta;
            }
        }
        return null;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }
}
