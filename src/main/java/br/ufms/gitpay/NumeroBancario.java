package br.ufms.gitpay;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.IntStream;

public class NumeroBancario implements Comparable<NumeroBancario>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final int numero;
    private final int digito;

    private NumeroBancario(int numero, int digito) {
        this.numero = numero;
        this.digito = digito;
    }

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @return the digito
     */
    public int getDigito() {
        return digito;
    }

    /**
     * Retorna um número bancário a partir de uma String
     *
     * @param numeroDigito número bancário com dígito verificador
     * @return o número bancário
     */
    public static NumeroBancario of(String numeroDigito) {
        if (numeroDigito == null || !numeroDigito.matches("\\d+-\\d")) {
            throw new IllegalArgumentException("Número bancário inválido");
        }
        int[] num = Arrays.stream(numeroDigito.split("-")).mapToInt(Integer::parseInt).toArray();
        return NumeroBancario.of(num[0], num[1]);
    }

    /**
     * Retorna um número bancário a partir de uma String
     *
     * @param numero número bancário sem o dígito verificador
     * @param digito dígito verificador
     * @return o número bancário
     */
    public static NumeroBancario of(int numero, int digito) {
        if (numero < 0) {
            throw new IllegalArgumentException("O número bancário não pode ser negativo.");
        } else if (digito != digito(numero)) {
            throw new IllegalArgumentException("Dígito verificador inválido");
        }
        return new NumeroBancario(numero, digito);
    }

    /**
     * Algoritmo verificador de dígito usado em códigos bancários como mecanismo de autenticação
     * para verificar a validade e a autenticidade de um valor numérico, evitando fraudes e erros de
     * transmissão ou digitação.
     * Algoritmo: Módulo 10.
     *
     * @param numero numero a ser verificado
     * @return o dígito verificador
     */
    public static int digito(int numero) {
        return digito(String.valueOf(numero));
    }

    /**
     * Algoritmo verificador de dígito usado em códigos bancários como mecanismo de autenticação
     * para verificar a validade e a autenticidade de um valor numérico, evitando fraudes e erros de
     * transmissão ou digitação.
     * Algoritmo: Módulo 10.
     *
     * @param numero numero a ser verificado.
     * @return o dígito verificador
     */
    private static int digito(String numero) {
        int[] digitos = new StringBuilder(numero).reverse().chars().map(Character::getNumericValue).toArray();
        int soma = IntStream.range(0, digitos.length)
                .map(i -> i % 2 == 0 ? digitos[i] : digitos[i] * 2 > 9 ? digitos[i] * 2 - 9 : digitos[i] * 2)
                .sum();

        int digitoVerificador = 10 - (soma % 10);
        return digitoVerificador == 10 ? 0 : digitoVerificador;
    }

    @Override
    public int compareTo(NumeroBancario other) {
        return this.numero - other.numero;
    }

    @Override
    public String toString() {
        return String.format("%03d-%d", numero, digito);
    }
}

