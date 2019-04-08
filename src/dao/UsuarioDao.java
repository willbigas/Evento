package dao;

import interfaces.DaoI;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Usuario;

/**
 *
 * @author William
 */
public class UsuarioDao extends Dao implements DaoI<Usuario> {

    public UsuarioDao() {
        super();
    }

    @Override
    public List<Usuario> listar() {
        try {
            PreparedStatement stmt;
            stmt = conexao.prepareStatement("SELECT * FROM USUARIO");
            ResultSet result = stmt.executeQuery();
            List<Usuario> lista = new ArrayList<>();
            while (result.next()) {
                Usuario user = new Usuario();
                user.setId(result.getInt("id"));
                user.setLogin(result.getString("login"));
                user.setSenha(result.getString("senha"));
                lista.add(user);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public int cadastrar(Usuario obj) {
        try {
            PreparedStatement stmt;
            stmt = conexao.prepareStatement(
                    "INSERT INTO USUARIO(login , senha)"
                    + " VALUES(?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, obj.getLogin());
            stmt.setString(2, obj.getSenha());
            ResultSet res;
            if (stmt.executeUpdate() > 0) {
                res = stmt.getGeneratedKeys();
                res.next();
                return res.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }

    @Override
    public boolean alterar(Usuario obj) {
        try {
            PreparedStatement stmt = conexao.prepareStatement(""
                    + "UPDATE USUARIO SET LOGIN = ? , SENHA = ? WHERE  id = ?");
            stmt.setString(1, obj.getLogin());
            stmt.setString(2, obj.getSenha());
            stmt.setInt(3, obj.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deletar(Usuario obj) {
        try {
            PreparedStatement stmt = conexao.prepareStatement("DELETE FROM USUARIO WHERE ID = ?");
            stmt.setInt(1, obj.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public List<Usuario> pesquisarPorTermo(String termo) {
        try {
            PreparedStatement stmt = conexao.prepareStatement(""
                    + "SELECT ID, LOGIN, SENHA FROM USUARIO "
                    + "WHERE LOGIN LIKE ?");
            stmt.setString(1, "%" + termo + "%");
            ResultSet result = stmt.executeQuery();
            List<Usuario> lista = new ArrayList<>();
            while (result.next()) {
                Usuario user = new Usuario();
                user.setId(result.getInt("id"));
                user.setLogin(result.getString("login"));
                user.setSenha(result.getString("senha"));
                lista.add(user);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

    @Override
    public Usuario lerPorId(int id) {
        try {
            PreparedStatement stmt = conexao.prepareStatement(""
                    + "SELECT ID, LOGIN , SENHA FROM USUARIO "
                    + "WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                Usuario user = new Usuario();
                user.setId(result.getInt("id"));
                user.setLogin(result.getString("login"));
                user.setSenha(result.getString("senha"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
