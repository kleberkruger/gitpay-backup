import br.ufms.gitpay.*;

public class Main {

    public static void main(String[] args) {

        try {
            Usuario<PessoaFisica> u1 = new Usuario<>(new PessoaFisica("Kleber Kruger", "01234567890"), "123");
            ContaBancaria c1 = Banco.GitPay.criarConta(u1);
            System.out.println(c1.getNumero());

            Usuario<PessoaJuridica> u2 = new Usuario<>(new PessoaJuridica("UFMS", "01234567890001"), "123");
            ContaBancaria c2 = Banco.GitPay.criarConta(u2);
            System.out.println(c2.getNumero());

            for(int i = 0; i < 100; i++) {
                ContaBancaria c = Banco.GitPay.criarConta(u1);
                System.out.println(c.getNumero());
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
