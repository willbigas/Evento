package evento;

import view.participante.JanelaGerenciarParticipante;
import view.usuario.JanelaLoginUsuario;

/**
 *
 * @author William
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JanelaLogin();
    }

    // Janela de Login
    public static void JanelaLogin() {
        JanelaLoginUsuario janelaLogin = new JanelaLoginUsuario();
        janelaLogin.setTitle("JANELA DE LOGIN");
        janelaLogin.setLocationRelativeTo(null);
        janelaLogin.setVisible(true);
    }

    // JANELAS DE PARTICIPANTES
    public static void JanelaGerenciarParticipantes() {
        JanelaGerenciarParticipante janelaGerenciarParticipantes = new JanelaGerenciarParticipante();
        janelaGerenciarParticipantes.setTitle("GERENCIAR PARTICIPANTES");
        janelaGerenciarParticipantes.setLocationRelativeTo(null);
        janelaGerenciarParticipantes.setVisible(true);

    }
}
