package dao;

import interfaces.DaoI;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

/**
 *
 * @author William
 */
public class UsuarioDao extends Dao implements DaoI<User> {

    public UsuarioDao() {
        super();
    }

    @Override
    public List<User> listar() {
        try {
            PreparedStatement stmt;
            stmt = conexao.prepareStatement("SELECT * FROM USUARIO");
            ResultSet result = stmt.executeQuery();
            List<User> lista = new ArrayList<>();
            while (result.next()) {
                User user = new User();
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
    public int cadastrar(User obj) {
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
    public boolean alterar(User obj) {
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
    public boolean deletar(User obj) {
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
    public List<User> pesquisarPorTermo(String termo) {
        try {
            PreparedStatement stmt = conexao.prepareStatement(""
                    + "SELECT ID, LOGIN, SENHA FROM USUARIO "
                    + "WHERE LOGIN LIKE ?");
            stmt.setString(1, "%" + termo + "%");
            ResultSet result = stmt.executeQuery();
            List<User> lista = new ArrayList<>();
            while (result.next()) {
                User user = new User();
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
    public User lerPorId(int id) {
        try {
            PreparedStatement stmt = conexao.prepareStatement(""
                    + "SELECT ID, LOGIN , SENHA FROM USUARIO "
                    + "WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                User user = new User();
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
