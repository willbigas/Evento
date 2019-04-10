package control;

import dao.UserDao;
import evento.Main;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.User;
import model.tablemodel.UserTableModel;
import view.user.ManageUser;

/**
 *
 * @author William
 */
public class UserControl {

    User usuario;
    List<User> listUsuarios;
    UserDao usuarioDao;
    UserTableModel usuarioTable;
    Integer linhaSelecionada = 0;

    public UserControl() {
        usuarioDao = new UserDao();
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
        TF_LOGIN = view.user.LoginUser.tfLogin.getText();
        TF_PASSWORD = view.user.LoginUser.tfSenha.getText();
    }

    private void pegaCamposFormUsuario() {
        TF_LOGIN = null;
        TF_PASSWORD = null;
        TF_LOGIN = view.user.CreateUser.tfLogin.getText();
        TF_PASSWORD = view.user.CreateUser.tfSenha.getText();
    }

    public void atualizaJtableUsuario() {
        listUsuarios = usuarioDao.listar();
        usuarioTable.clear();
        usuarioTable.addListOfObject(listUsuarios);
    }

    public void mudaModeloDaTabela() {
        ManageUser.tblUsuario.setModel(usuarioTable);
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

    public void efetuaLoginAction() {
        if (validaLogin()) {
            Main.JanelaPrincipal();
        } else {
            JOptionPane.showMessageDialog(null, "Usuario ou Senha incorretos!");
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
        return ManageUser.tblUsuario.getSelectedRow();
    }

    public User pegaUsuarioSelecionadoDaTabela() {
        if (ManageUser.tblUsuario.getSelectedRow() == - 1) {
            linhaSelecionada = -1;
            return null;
        } else {
            usuario = usuarioTable.getObject(ManageUser.tblUsuario.getSelectedRow());
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
        String pesquisa = view.user.ManageUser.tfPesquisar.getText();
        List<User> usuariosPesquisados = usuarioDao.pesquisarPorTermo(pesquisa);
        usuarioTable.clear();
        usuarioTable.addListOfObject(usuariosPesquisados);
    }

    public void deletarUsuarioAction() {
        usuario = usuarioTable.getObject(ManageUser.tblUsuario.getSelectedRow());
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
