package control;

import dao.UsuarioDao;
import evento.Evento;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.User;
import model.tablemodel.UserTableModel;
import view.usuario.ManageUser;

/**
 *
 * @author William
 */
public class UsuarioControl {

    User USER;
    List<User> USER_LIST;
    UsuarioDao USER_DAO;
    UserTableModel USER_TABLE;
    Integer INDEX_SELECTED = 0;

    public UsuarioControl() {
        USER_DAO = new UsuarioDao();
        USER_LIST = new ArrayList<>();
        USER_TABLE = new UserTableModel();
        getFieldsLogin();
        getUserOfDatabase();
        setModelOfTable();

    }

    private String TF_LOGIN = "";
    private String TF_PASSWORD = "";
    private String LBL_ID = "";

    private void getFieldsLogin() {
        TF_LOGIN = view.usuario.LoginUser.tfLogin.getText();
        TF_PASSWORD = view.usuario.LoginUser.tfSenha.getText();
    }

    private void getFieldsEdit() {
        TF_LOGIN = null;
        TF_PASSWORD = null;
        LBL_ID = null;
        TF_LOGIN = view.usuario.EditUser.tfLogin.getText();
        TF_PASSWORD = view.usuario.EditUser.tfSenha.getText();
        LBL_ID = view.usuario.EditUser.lblCodigoUsuario.getText();
    }

    private void getFieldsInsert() {

        TF_LOGIN = null;
        TF_PASSWORD = null;
        TF_LOGIN = view.usuario.CreateUser.tfLogin.getText();
        TF_PASSWORD = view.usuario.CreateUser.tfSenha.getText();
    }

    public void getUserOfDatabase() {
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
            Evento.JanelaPrincipal();
        } else {
            JOptionPane.showMessageDialog(null, "Usuario ou Senha incorretos!");
        }
    }

    public void InsertUserAction() {
        getFieldsInsert();
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

    public void loadFieldsEditUserAction() {
        USER = USER_TABLE.getObject(ManageUser.tblUsuario.getSelectedRow());
        System.out.println("Usuario pegado da Tabela" + USER);
        INDEX_SELECTED = getSelectedIndex();
    }

    public void changeFieldsOnEdit() {
        view.usuario.EditUser.tfLogin.setText(USER.getLogin());
        view.usuario.EditUser.tfSenha.setText(USER.getSenha());
        view.usuario.EditUser.lblCodigoUsuario.setText(String.valueOf(USER.getId()));
    }

    public void updateUserAction() {
        getFieldsEdit();
        USER = new User();
        System.out.println("id do edit :  " + LBL_ID);
        USER.setId(Integer.valueOf(LBL_ID));
        USER.setLogin(TF_LOGIN);
        USER.setSenha(TF_PASSWORD);
        boolean inserido = USER_DAO.alterar(USER);
        if (inserido) {
            USER_TABLE.updateObject(INDEX_SELECTED, USER);
            JOptionPane.showMessageDialog(null, "Alterado com Sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Não conseguir Alterar!");
        }
        USER = null;
    }

    public void deleteUserAction() {
        USER = USER_TABLE.getObject(ManageUser.tblUsuario.getSelectedRow());
        if (USER_DAO.deletar(USER)) {
            USER_TABLE.removeObject(getSelectedIndex());
            JOptionPane.showMessageDialog(null, "Deletado com Sucesso!");
        }

    }

}
