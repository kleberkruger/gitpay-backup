package br.ufms.gitpay.util;

public class Validador {

    public static boolean validarNome(String nome) {
        String regex = "^[a-zA-ZÀ-ÖØ-öø-ÿ]+\\s[a-zA-ZÀ-ÖØ-öø-ÿ]+$";
        return nome.matches(regex);
    }

    public static boolean validarUsuario(String usuario) {
        String regex = "^[a-zA-ZÀ-ÖØ-öø-ÿ]+\\s[a-zA-ZÀ-ÖØ-öø-ÿ]+$";
        return usuario.matches(regex);
    }

    public static boolean validarTelefone(String telefone) {
        String regex = telefone.length() > 2 && telefone.charAt(2) == '9' ? "^\\d{11}$" : "^\\d{10}$";
        return telefone.matches(regex);
    }

    public static boolean validarEmail(String email) {
        String regex = "^[a-zA-ZÀ-ÖØ-öø-ÿ]+\\s[a-zA-ZÀ-ÖØ-öø-ÿ]+$";
        return email.matches(regex);
    }

    public static boolean validarSenha(String senha) {
        String regex = "^[a-zA-ZÀ-ÖØ-öø-ÿ]+\\s[a-zA-ZÀ-ÖØ-öø-ÿ]+$";
        return senha.matches(regex);
    }

    public static boolean validarSenhaNumerica(String senha) {
        String regex = "^[a-zA-ZÀ-ÖØ-öø-ÿ]+\\s[a-zA-ZÀ-ÖØ-öø-ÿ]+$";
        return senha.matches(regex);
    }

    public static boolean validarCPF(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            return false;
        }
        cpf = cpf.replaceAll("\\D", "");
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }
        int[] digitos = cpf.chars().map(Character::getNumericValue).toArray();

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += digitos[i] * (10 - i);
        }

        int resto = soma % 11;
        int digitoVerificador1 = (resto < 2) ? 0 : 11 - resto;

        if (digitos[9] != digitoVerificador1) {
            return false;
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += digitos[i] * (11 - i);
        }

        resto = soma % 11;
        int digitoVerificador2 = (resto < 2) ? 0 : 11 - resto;

        return digitos[10] == digitoVerificador2;
    }

    public static boolean validarCNPJ(String cnpj) {
        if (cnpj == null || !cnpj.matches("\\d{14}|\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}")) {
            return false;
        }
        cnpj = cnpj.replaceAll("\\D", "");
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }
        int[] digitos = cnpj.chars().map(Character::getNumericValue).toArray();

        int soma = 0;
        int peso = 2;
        for (int i = 11; i >= 0; i--) {
            soma += digitos[i] * peso;
            peso++;
            if (peso == 10)
                peso = 2;
        }

        int digitoVerificador1 = (soma % 11 < 2) ? 0 : 11 - (soma % 11);

        if (digitos[12] != digitoVerificador1) {
            return false;
        }

        soma = 0;
        peso = 2;
        for (int i = 12; i >= 0; i--) {
            soma += digitos[i] * peso;
            peso++;
            if (peso == 10)
                peso = 2;
        }

        int digitoVerificador2 = (soma % 11 < 2) ? 0 : 11 - (soma % 11);

        return digitos[13] == digitoVerificador2;
    }
}
