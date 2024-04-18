package br.ufms.gitpay;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

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
        int i = 2;
        int sum = 0;
        for (char c : numero.toCharArray()) {
            int res = Integer.parseInt(String.valueOf(c)) * i;
            sum += res > 9 ? (res - 9) : res;
            i = i == 2 ? 1 : 2;
        }
        return 10 - (sum % 10);
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

