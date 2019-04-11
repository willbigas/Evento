package evento;

import javax.swing.JFrame;
import model.Usuario;
import view.JanelaGerenciarTelas;
import view.categoria.JanelaGerenciarCategoria;
import view.participante.JanelaCriarParticipante;
import view.participante.JanelaVisualizarParticipante;
import view.participante.JanelaGerenciarParticipante;
import view.usuario.JanelaCriaUsuario;
import view.usuario.JanelaGerenciarUsuario;
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

    public static void JanelaPrincipal() {
        JanelaGerenciarTelas janelaGerenciarTelas = new JanelaGerenciarTelas();
        janelaGerenciarTelas.setTitle("JANELA PRINCIPAL");
        janelaGerenciarTelas.setExtendedState(JFrame.MAXIMIZED_BOTH);
        janelaGerenciarTelas.setLocationRelativeTo(null);
        janelaGerenciarTelas.setVisible(true);
    }

    // JANELAS DE USUARIOS //
    public static void JanelaGerenciarUsuarios() {
        JanelaGerenciarUsuario janelaGerenciarUsuarios = new JanelaGerenciarUsuario();
        janelaGerenciarUsuarios.setTitle("GERENCIAR USUARIOS");
        janelaGerenciarUsuarios.setLocationRelativeTo(null);
        janelaGerenciarUsuarios.setVisible(true);

    }

    public static void JanelaCadastrarUsuario(Usuario user, int indexSelecionada) {
        JanelaCriaUsuario janelaCadastrarUsuario = new JanelaCriaUsuario(user, indexSelecionada);
        janelaCadastrarUsuario.setTitle("CADASTRAR USUARIO");
        janelaCadastrarUsuario.setLocationRelativeTo(null);
        janelaCadastrarUsuario.setVisible(true);
    }

    // JANELAS DE CATEGORIAS
    public static void JanelaGerenciarCategorias() {
        JanelaGerenciarCategoria janelaGerenciarCategorias = new JanelaGerenciarCategoria();
        janelaGerenciarCategorias.setTitle("GERENCIAR CATEGORIAS");
        janelaGerenciarCategorias.setLocationRelativeTo(null);
        janelaGerenciarCategorias.setVisible(true);

    }

    // JANELAS DE PARTICIPANTES
    public static void JanelaGerenciarParticipantes() {
        JanelaGerenciarParticipante janelaGerenciarParticipantes = new JanelaGerenciarParticipante();
        janelaGerenciarParticipantes.setTitle("GERENCIAR PARTICIPANTES");
        janelaGerenciarParticipantes.setLocationRelativeTo(null);
        janelaGerenciarParticipantes.setVisible(true);

    }

    public static void JanelaEditarParticipante() {
        JanelaVisualizarParticipante janelaEditarParticipant = new JanelaVisualizarParticipante();
        janelaEditarParticipant.setTitle("EDITAR PARTICIPANTE");
        janelaEditarParticipant.setLocationRelativeTo(null);
        janelaEditarParticipant.setVisible(true);
    }

    public static void JanelaCadastrarParticipante() {
        JanelaCriarParticipante janelaCadastrarParticipante = new JanelaCriarParticipante();
        janelaCadastrarParticipante.setTitle("CADASTRAR PARTICIPANTE");
        janelaCadastrarParticipante.setLocationRelativeTo(null);
        janelaCadastrarParticipante.setVisible(true);
    }

}
