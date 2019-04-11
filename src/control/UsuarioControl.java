package control;

import dao.UsuarioDao;
import evento.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Usuario;
import model.tablemodel.UsuarioTableModel;
import util.Mensagem;
import util.Texto;
import view.usuario.JanelaGerenciarUsuario;

/**
 *
 * @author William
 */
public class UsuarioControl {

    Usuario usuario;
    List<Usuario> listUsuarios;
    UsuarioDao usuarioDao;
    UsuarioTableModel usuarioTable;
    Integer linhaSelecionada = 0;

    public UsuarioControl() {
        usuarioDao = new UsuarioDao();
        listUsuarios = new ArrayList<>();
        usuarioTable = new UsuarioTableModel();
        pegaCamposLogin();
        atualizaJtableUsuario();
        mudaModeloDaTabela();

    }

    private String TF_LOGIN = "";
    private String TF_PASSWORD = "";
    private String LBL_ID = "";

    private void pegaCamposLogin() {
        TF_LOGIN = view.usuario.JanelaLoginUsuario.tfLogin.getText();
        TF_PASSWORD = view.usuario.JanelaLoginUsuario.tfSenha.getText();
    }

    private void pegaCamposFormUsuario() {
        TF_LOGIN = null;
        TF_PASSWORD = null;
        TF_LOGIN = view.usuario.JanelaCriaUsuario.tfLogin.getText();
        TF_PASSWORD = view.usuario.JanelaCriaUsuario.tfSenha.getText();
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
        List<Usuario> usuariosDoBanco = usuarioDao.listar();

        for (Usuario usuario : usuariosDoBanco) {
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
        usuario = new Usuario();
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

    public Usuario pegaUsuarioSelecionadoDaTabela() {
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

    public void atualizarUsuarioAction(Usuario user, int index) {
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
        String pesquisa = view.usuario.JanelaGerenciarUsuario.tfPesquisar.getText();
        List<Usuario> usuariosPesquisados = usuarioDao.pesquisarPorTermo(pesquisa);
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

    public void gravarUsuarioAction(Usuario usuario, int index) {
        if (index == -1) {
            criarUsuarioAction();
        } else {
            atualizarUsuarioAction(usuario, index);
        }
    }

}
