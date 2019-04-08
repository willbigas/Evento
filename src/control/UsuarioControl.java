package control;

import dao.UsuarioDao;
import evento.Evento;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Usuario;
import model.tablemodel.UsuarioTableModel;
import view.usuario.GerenciarUsuario;

/**
 *
 * @author William
 */
public class UsuarioControl {

    Usuario USER;
    List<Usuario> USER_LIST;
    UsuarioDao USER_DAO;
    UsuarioTableModel USER_TABLE;
    Integer INDEX_SELECTED = 0;

    public UsuarioControl() {
        USER_DAO = new UsuarioDao();
        USER_LIST = new ArrayList<>();
        USER_TABLE = new UsuarioTableModel();
        getFieldsLogin();
        getUserOfDatabase();
        setModelOfTable();

    }

    private String TF_LOGIN = "";
    private String TF_PASSWORD = "";
    private String LBL_ID = "";

    private void getFieldsLogin() {
        TF_LOGIN = view.usuario.LoginUsuario.tfLogin.getText();
        TF_PASSWORD = view.usuario.LoginUsuario.tfSenha.getText();
    }

    private void getFieldsEdit() {
        TF_LOGIN = null;
        TF_PASSWORD = null;
        LBL_ID = null;
        TF_LOGIN = view.usuario.EditarUsuario.tfLogin.getText();
        TF_PASSWORD = view.usuario.EditarUsuario.tfSenha.getText();
        LBL_ID = view.usuario.EditarUsuario.lblCodigoUsuario.getText();
    }

    private void getFieldsInsert() {

        TF_LOGIN = null;
        TF_PASSWORD = null;
        TF_LOGIN = view.usuario.CadastrarUsuario.tfLogin.getText();
        TF_PASSWORD = view.usuario.CadastrarUsuario.tfSenha.getText();
    }

    public void getUserOfDatabase() {
        USER_LIST = USER_DAO.listar();
        USER_TABLE.clear();
        USER_TABLE.addListOfObject(USER_LIST);
    }

    public void setModelOfTable() {
        GerenciarUsuario.tblUsuario.setModel(USER_TABLE);
    }

    private Boolean loginValidate() {
        getFieldsLogin();
        List<Usuario> usuariosDoBanco = USER_DAO.listar();

        for (Usuario usuario : usuariosDoBanco) {
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
        USER = new Usuario();
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
        return GerenciarUsuario.tblUsuario.getSelectedRow();
    }

    public void loadFieldsEditUserAction() {
        USER = USER_TABLE.getObject(GerenciarUsuario.tblUsuario.getSelectedRow());
        System.out.println("Usuario pegado da Tabela" + USER);
        INDEX_SELECTED = getSelectedIndex();
    }

    public void changeFieldsOnEdit() {
        view.usuario.EditarUsuario.tfLogin.setText(USER.getLogin());
        view.usuario.EditarUsuario.tfSenha.setText(USER.getSenha());
        view.usuario.EditarUsuario.lblCodigoUsuario.setText(String.valueOf(USER.getId()));
    }

    public void updateUserAction() {
        getFieldsEdit();
        USER = new Usuario();
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
        USER = USER_TABLE.getObject(GerenciarUsuario.tblUsuario.getSelectedRow());
        if (USER_DAO.deletar(USER)) {
            USER_TABLE.removeObject(getSelectedIndex());
            JOptionPane.showMessageDialog(null, "Deletado com Sucesso!");
        }

    }

}
