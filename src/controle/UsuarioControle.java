package controle;

import dao.UsuarioDao;
import evento.Principal;
import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;
import util.Mensagem;
import util.Texto;

/**
 *
 * @author William
 */
public class UsuarioControle {

    Usuario usuario;
    List<Usuario> listUsuarios;
    UsuarioDao usuarioDao;
    Integer linhaSelecionada = 0;

    public UsuarioControle() {
        usuarioDao = new UsuarioDao();
        listUsuarios = new ArrayList<>();
        pegaCamposLogin();

    }

    private String TF_LOGIN = "";
    private String TF_PASSWORD = "";

    private void pegaCamposLogin() {
        TF_LOGIN = view.usuario.JanelaLoginUsuario.tfLogin.getText();
        TF_PASSWORD = view.usuario.JanelaLoginUsuario.tfSenha.getText();
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
            Principal.JanelaGerenciarParticipantes();
            return true;
        } else {
            Mensagem.msgInfo(Texto.ERROR_LOGIN_INCORRECT);
            return false;
        }
    }

    public int pegaLinhaSelecionadaDaTabela() {
        return linhaSelecionada;
    }

}
