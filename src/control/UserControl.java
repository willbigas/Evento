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

    User USER;
    List<User> USER_LIST;
    UserDao USER_DAO;
    UserTableModel USER_TABLE;
    Integer INDEX_SELECTED = 0;

    public UserControl() {
        USER_DAO = new UserDao();
        USER_LIST = new ArrayList<>();
        USER_TABLE = new UserTableModel();
        getFieldsLogin();
        updateJTableUser();
        setModelOfTable();

    }

    private String TF_LOGIN = "";
    private String TF_PASSWORD = "";
    private String LBL_ID = "";

    private void getFieldsLogin() {
        TF_LOGIN = view.user.LoginUser.tfLogin.getText();
        TF_PASSWORD = view.user.LoginUser.tfSenha.getText();
    }

    private void getFields() {
        TF_LOGIN = null;
        TF_PASSWORD = null;
        TF_LOGIN = view.user.CreateUser.tfLogin.getText();
        TF_PASSWORD = view.user.CreateUser.tfSenha.getText();
    }

    public void updateJTableUser() {
        USER_LIST = USER_DAO.listar();
        USER_TABLE.clear();
        USER_TABLE.addListOfObject(USER_LIST);
    }

    public void setModelOfTable() {
        ManageUser.tblUsuario.setModel(USER_TABLE);
    }

    private Boolean loginValidate() {
        getFieldsLogin();
        List<User> usuariosDoBanco = USER_DAO.listar();

        for (User usuario : usuariosDoBanco) {
            if (usuario.getLogin().equals(TF_LOGIN) && usuario.getSenha().equals(TF_PASSWORD)) {
                return true;
            }
        }
        return false;
    }

    public void loginAction() {
        if (loginValidate()) {
            Main.JanelaPrincipal();
        } else {
            JOptionPane.showMessageDialog(null, "Usuario ou Senha incorretos!");
        }
    }

    public void createUserAction() {
        getFields();
        USER = new User();
        USER.setLogin(TF_LOGIN); // mudar campos
        USER.setSenha(TF_PASSWORD); // mudar campos
        int idUsuario = USER_DAO.cadastrar(USER);
        if (idUsuario != 0) {
            USER.setId(idUsuario);
            USER_TABLE.addObject(USER);
            JOptionPane.showMessageDialog(null, "Inserido com Sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Não conseguir Inserir!");
        }
        USER = null;
    }

    public int getSelectedIndex() {
        return ManageUser.tblUsuario.getSelectedRow();
    }

    public User pegaUsuarioSelecionadoDaTabela() {
        if (ManageUser.tblUsuario.getSelectedRow() == - 1) {
            INDEX_SELECTED = -1;
            return null;
        } else {
            USER = USER_TABLE.getObject(ManageUser.tblUsuario.getSelectedRow());
            System.out.println("Usuario pegado da Tabela" + USER);
            INDEX_SELECTED = getSelectedIndex();
            return USER;

        }

    }

    public int pegaLinhaSelecionadaDaTabela() {
        return INDEX_SELECTED;
    }

    public void updateUserAction(User user, int index) {
        getFields();
        System.out.println("id do edit :  " + LBL_ID);
        boolean inserido = USER_DAO.alterar(user);
        if (inserido) {
            USER_TABLE.updateObject(INDEX_SELECTED, user);
            JOptionPane.showMessageDialog(null, "Alterado com Sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Não conseguir Alterar!");
        }
        USER = null;
    }

    public void searchUserAction() {
        String pesquisa = view.user.ManageUser.tfPesquisar.getText();
        List<User> usuariosPesquisados = USER_DAO.pesquisarPorTermo(pesquisa);
        USER_TABLE.clear();
        USER_TABLE.addListOfObject(usuariosPesquisados);
    }

    public void deleteUserAction() {
        USER = USER_TABLE.getObject(ManageUser.tblUsuario.getSelectedRow());
        if (USER_DAO.deletar(USER)) {
            USER_TABLE.removeObject(getSelectedIndex());
            JOptionPane.showMessageDialog(null, "Deletado com Sucesso!");
        }

    }

    public void gravarUsuario(User usuario, int index) {
        if (index == -1) {
            createUserAction();
        } else {
            updateUserAction(usuario, index);
        }
    }

}
