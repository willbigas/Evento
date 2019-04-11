package control;

import dao.UsuarioDao;
import evento.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.User;
import model.tablemodel.UserTableModel;
import util.Mensagem;
import util.Texto;
import view.user.JanelaGerenciarUsuario;

/**
 *
 * @author William
 */
public class UserControl {

    User usuario;
    List<User> listUsuarios;
    UsuarioDao usuarioDao;
    UserTableModel usuarioTable;
    Integer linhaSelecionada = 0;

    public UserControl() {
        usuarioDao = new UsuarioDao();
        listUsuarios = new ArrayList<>();
        usuarioTable = new UserTableModel();
        pegaCamposLogin();
        atualizaJtableUsuario();
        mudaModeloDaTabela();

    }

    private String TF_LOGIN = "";
    private String TF_PASSWORD = "";
    private String LBL_ID = "";

    private void pegaCamposLogin() {
        TF_LOGIN = view.user.JanelaLoginUsuario.tfLogin.getText();
        TF_PASSWORD = view.user.JanelaLoginUsuario.tfSenha.getText();
    }

    private void pegaCamposFormUsuario() {
        TF_LOGIN = null;
        TF_PASSWORD = null;
        TF_LOGIN = view.user.JanelaCriaUsuario.tfLogin.getText();
        TF_PASSWORD = view.user.JanelaCriaUsuario.tfSenha.getText();
    }

    public void atualizaJtableUsuario() {
        listUsuarios = usuarioDao.listar();
        usuarioTable.clear();
        usuarioTable.addListOfObject(listUsuarios);
    }

    public void mudaModeloDaTabela() {
        JanelaGerenciarUsuario.tblUsuario.setModel(usuarioTable);
    }

    private Boolean validaLogin() {
        pegaCamposLogin();
        List<User> usuariosDoBanco = usuarioDao.listar();

        for (User usuario : usuariosDoBanco) {
            if (usuario.getLogin().equals(TF_LOGIN) && usuario.getSenha().equals(TF_PASSWORD)) {
                return true;
            }
        }
        return false;
    }

    public boolean efetuaLoginAction() {
        if (validaLogin()) {
            Principal.JanelaPrincipal();
            return true;
        } else {
            Mensagem.msgInfo(Texto.ERROR_LOGIN_INCORRECT);
            return false;
        }
    }

    public void criarUsuarioAction() {
        pegaCamposFormUsuario();
        usuario = new User();
        usuario.setLogin(TF_LOGIN); // mudar campos
        usuario.setSenha(TF_PASSWORD); // mudar campos
        int idUsuario = usuarioDao.cadastrar(usuario);
        if (idUsuario != 0) {
            usuario.setId(idUsuario);
            usuarioTable.addObject(usuario);
            JOptionPane.showMessageDialog(null, "Inserido com Sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Não conseguir Inserir!");
        }
        usuario = null;
    }

    public int pegaLinhaSelecionada() {
        return JanelaGerenciarUsuario.tblUsuario.getSelectedRow();
    }

    public User pegaUsuarioSelecionadoDaTabela() {
        if (JanelaGerenciarUsuario.tblUsuario.getSelectedRow() == - 1) {
            linhaSelecionada = -1;
            return null;
        } else {
            usuario = usuarioTable.getObject(JanelaGerenciarUsuario.tblUsuario.getSelectedRow());
            System.out.println("Usuario pegado da Tabela" + usuario);
            linhaSelecionada = pegaLinhaSelecionada();
            return usuario;

        }

    }

    public int pegaLinhaSelecionadaDaTabela() {
        return linhaSelecionada;
    }

    public void atualizarUsuarioAction(User user, int index) {
        pegaCamposFormUsuario();
        System.out.println("id do edit :  " + LBL_ID);
        boolean inserido = usuarioDao.alterar(user);
        if (inserido) {
            usuarioTable.updateObject(linhaSelecionada, user);
            JOptionPane.showMessageDialog(null, "Alterado com Sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Não conseguir Alterar!");
        }
        usuario = null;
    }

    public void procurarUsuarioAction() {
        String pesquisa = view.user.JanelaGerenciarUsuario.tfPesquisar.getText();
        List<User> usuariosPesquisados = usuarioDao.pesquisarPorTermo(pesquisa);
        usuarioTable.clear();
        usuarioTable.addListOfObject(usuariosPesquisados);
    }

    public void deletarUsuarioAction() {
        usuario = usuarioTable.getObject(JanelaGerenciarUsuario.tblUsuario.getSelectedRow());
        if (usuarioDao.deletar(usuario)) {
            usuarioTable.removeObject(pegaLinhaSelecionada());
            JOptionPane.showMessageDialog(null, "Deletado com Sucesso!");
        }

    }
    public void gravarUsuarioAction(User usuario, int index) {
        if (index == -1) {
            criarUsuarioAction();
        } else {
            atualizarUsuarioAction(usuario, index);
        }
    }

}
