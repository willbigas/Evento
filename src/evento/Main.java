package evento;

import javax.swing.JFrame;
import view.MainWindow;
import view.category.CreateCategory;
import view.category.EditCategory;
import view.category.ManageCategory;
import view.participant.CreateParticipant;
import view.participant.EditParticipant;
import view.participant.ManageParticipant;
import view.user.CreateUser;
import view.user.EditUser;
import view.user.ManageUser;
import view.user.LoginUser;

/**
 *
 * @author William
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JanelaLogin();
    }

    // Janela de Login
    public static void JanelaLogin() {
        LoginUser janelaLogin = new LoginUser();
        janelaLogin.setTitle("JANELA DE LOGIN");
        janelaLogin.setLocationRelativeTo(null);
        janelaLogin.setVisible(true);
    }

    public static void JanelaPrincipal() {
        MainWindow janelaGerenciarTelas = new MainWindow();
        janelaGerenciarTelas.setTitle("JANELA PRINCIPAL");
        janelaGerenciarTelas.setExtendedState(JFrame.MAXIMIZED_BOTH);
        janelaGerenciarTelas.setLocationRelativeTo(null);
        janelaGerenciarTelas.setVisible(true);
    }

    // JANELAS DE USUARIOS //
    public static void JanelaGerenciarUsuarios() {
        ManageUser janelaGerenciarUsuarios = new ManageUser();
        janelaGerenciarUsuarios.setTitle("GERENCIAR USUARIOS");
        janelaGerenciarUsuarios.setLocationRelativeTo(null);
        janelaGerenciarUsuarios.setVisible(true);

    }

    public static void JanelaEditarUsuario() {
        EditUser janelaEditarUsuario = new EditUser();
        janelaEditarUsuario.setTitle("EDITAR USUARIO");
        janelaEditarUsuario.setLocationRelativeTo(null);
        janelaEditarUsuario.setVisible(true);
    }

    public static void JanelaCadastrarUsuario() {
        CreateUser janelaCadastrarUsuario = new CreateUser();
        janelaCadastrarUsuario.setTitle("CADASTRAR USUARIO");
        janelaCadastrarUsuario.setLocationRelativeTo(null);
        janelaCadastrarUsuario.setVisible(true);
    }

    // JANELAS DE CATEGORIAS
    public static void JanelaGerenciarCategorias() {
        ManageCategory janelaGerenciarCategorias = new ManageCategory();
        janelaGerenciarCategorias.setTitle("GERENCIAR CATEGORIAS");
        janelaGerenciarCategorias.setLocationRelativeTo(null);
        janelaGerenciarCategorias.setVisible(true);

    }

    public static void JanelaEditarCategoria() {
        EditCategory janelaEditarCategoria = new EditCategory();
        janelaEditarCategoria.setTitle("EDITAR CATEGORIA");
        janelaEditarCategoria.setLocationRelativeTo(null);
        janelaEditarCategoria.setVisible(true);
    }

    public static void JanelaCadastrarCategoria() {
        CreateCategory janelaCadastrarCategoria = new CreateCategory();
        janelaCadastrarCategoria.setTitle("CADASTRAR CATEGORIA");
        janelaCadastrarCategoria.setLocationRelativeTo(null);
        janelaCadastrarCategoria.setVisible(true);
    }

    // JANELAS DE PARTICIPANTES
    public static void JanelaGerenciarParticipantes() {
        ManageParticipant janelaGerenciarParticipantes = new ManageParticipant();
        janelaGerenciarParticipantes.setTitle("GERENCIAR PARTICIPANTES");
        janelaGerenciarParticipantes.setLocationRelativeTo(null);
        janelaGerenciarParticipantes.setVisible(true);

    }
    
    public static void JanelaEditarParticipante() {
        EditParticipant janelaEditarParticipant = new EditParticipant();
        janelaEditarParticipant.setTitle("EDITAR PARTICIPANTE");
        janelaEditarParticipant.setLocationRelativeTo(null);
        janelaEditarParticipant.setVisible(true);
    }

    public static void JanelaCadastrarParticipante() {
        CreateParticipant janelaCadastrarParticipante = new CreateParticipant();
        janelaCadastrarParticipante.setTitle("CADASTRAR PARTICIPANTE");
        janelaCadastrarParticipante.setLocationRelativeTo(null);
        janelaCadastrarParticipante.setVisible(true);
    }

}