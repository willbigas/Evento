package evento;

import view.GerenciarTelas;
import view.categoria.CadastrarCategoria;
import view.categoria.EditarCategoria;
import view.categoria.GerenciarCategoria;
import view.participante.CadastrarParticipante;
import view.participante.GerenciarParticipante;
import view.usuario.CadastrarUsuario;
import view.usuario.EditarUsuario;
import view.usuario.GerenciarUsuario;
import view.usuario.LoginUsuario;

/**
 *
 * @author William
 */
public class Evento {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JanelaLogin();
    }

    // Janela de Login
    public static void JanelaLogin() {
        LoginUsuario janelaLogin = new LoginUsuario();
        janelaLogin.setTitle("JANELA DE LOGIN");
        janelaLogin.setLocationRelativeTo(null);
        janelaLogin.setVisible(true);
    }

    public static void JanelaPrincipal() {
        GerenciarTelas janelaGerenciarTelas = new GerenciarTelas();
        janelaGerenciarTelas.setTitle("JANELA PRINCIPAL");
        janelaGerenciarTelas.setLocationRelativeTo(null);
        janelaGerenciarTelas.setVisible(true);
    }

    // JANELAS DE USUARIOS //
    public static void JanelaGerenciarUsuarios() {
        GerenciarUsuario janelaGerenciarUsuarios = new GerenciarUsuario();
        janelaGerenciarUsuarios.setTitle("GERENCIAR USUARIOS");
        janelaGerenciarUsuarios.setLocationRelativeTo(null);
        janelaGerenciarUsuarios.setVisible(true);

    }

    public static void JanelaEditarUsuario() {
        EditarUsuario janelaEditarUsuario = new EditarUsuario();
        janelaEditarUsuario.setTitle("EDITAR USUARIO");
        janelaEditarUsuario.setLocationRelativeTo(null);
        janelaEditarUsuario.setVisible(true);
    }

    public static void JanelaCadastrarUsuario() {
        CadastrarUsuario janelaCadastrarUsuario = new CadastrarUsuario();
        janelaCadastrarUsuario.setTitle("CADASTRAR USUARIO");
        janelaCadastrarUsuario.setLocationRelativeTo(null);
        janelaCadastrarUsuario.setVisible(true);
    }

    // JANELAS DE CATEGORIAS
    public static void JanelaGerenciarCategorias() {
        GerenciarCategoria janelaGerenciarCategorias = new GerenciarCategoria();
        janelaGerenciarCategorias.setTitle("GERENCIAR CATEGORIAS");
        janelaGerenciarCategorias.setLocationRelativeTo(null);
        janelaGerenciarCategorias.setVisible(true);

    }

    public static void JanelaEditarCategoria() {
        EditarCategoria janelaEditarCategoria = new EditarCategoria();
        janelaEditarCategoria.setTitle("EDITAR USUARIO");
        janelaEditarCategoria.setLocationRelativeTo(null);
        janelaEditarCategoria.setVisible(true);
    }

    public static void JanelaCadastrarCategoria() {
        CadastrarCategoria janelaCadastrarCategoria = new CadastrarCategoria();
        janelaCadastrarCategoria.setTitle("CADASTRAR USUARIO");
        janelaCadastrarCategoria.setLocationRelativeTo(null);
        janelaCadastrarCategoria.setVisible(true);
    }

    // JANELAS DE PARTICIPANTES
    public static void JanelaGerenciarParticipantes() {
        GerenciarParticipante janelaGerenciarParticipantes = new GerenciarParticipante();
        janelaGerenciarParticipantes.setTitle("GERENCIAR PARTICIPANTES");
        janelaGerenciarParticipantes.setLocationRelativeTo(null);
        janelaGerenciarParticipantes.setVisible(true);

    }

    public static void JanelaCadastrarParticipante() {
        CadastrarParticipante janelaCadastrarParticipante = new CadastrarParticipante();
        janelaCadastrarParticipante.setTitle("CADASTRAR PARTICIPANTE");
        janelaCadastrarParticipante.setLocationRelativeTo(null);
        janelaCadastrarParticipante.setVisible(true);
    }

}
