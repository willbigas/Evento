package dao;

import interfaces.DaoI;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Categoria;

/**
 *
 * @author William
 */
public class CategoriaDao extends Dao implements DaoI<Categoria> {

    public CategoriaDao() {
        //Contrutor da super classe Dao. Faz a conexão.
        super();
    }

    @Override
    public List<Categoria> listar() {
        try {
            PreparedStatement stmt;
            stmt = conexao.prepareStatement("SELECT * FROM categoria");
            ResultSet result = stmt.executeQuery();
            List<Categoria> lista = new ArrayList<>();
            while (result.next()) {
                Categoria c = new Categoria();
                c.setId(result.getInt("id"));
                c.setNome(result.getString("nome"));
                lista.add(c);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Método para cadastrar um produto no banco de dados
     * <br>Retorna id do produto cadastrado
     * <br><b>Retorna 0 (zero) se houver erro</b>
     *
     * @param obj
     * @return int
     */
    @Override
    public int cadastrar(Categoria obj) {
        try {
            PreparedStatement stmt;
            stmt = conexao.prepareStatement(
                    "INSERT INTO categoria(nome)"
                    + " VALUES(?)", PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, obj.getNome());
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
    public boolean alterar(Categoria obj) {
        try {
            PreparedStatement stmt = conexao.prepareStatement(""
                    + "UPDATE categoria SET nome = ? WHERE  id = ?");
            stmt.setString(1, obj.getNome());
            stmt.setInt(2, obj.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deletar(Categoria obj) {
        try {
            PreparedStatement stmt = conexao.prepareStatement("DELETE from categoria WHERE id = ?");
            stmt.setInt(1, obj.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public List<Categoria> pesquisarPorTermo(String termo) {
        try {
            PreparedStatement stmt = conexao.prepareStatement(""
                    + "SELECT id, nome FROM categoria "
                    + "WHERE nome LIKE ?");
            stmt.setString(1, "%" + termo + "%");
            ResultSet result = stmt.executeQuery();
            List<Categoria> lista = new ArrayList<>();
            while (result.next()) {
                Categoria c = new Categoria();
                c.setId(result.getInt("id"));
                c.setNome(result.getString("nome"));
                lista.add(c);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

    @Override
    public Categoria lerPorId(int id) {
        try {
            PreparedStatement stmt = conexao.prepareStatement(""
                    + "SELECT id, nome FROM categoria "
                    + "WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                Categoria c = new Categoria();
                c.setId(result.getInt("id"));
                c.setNome(result.getString("nome"));
                return c;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public Categoria pesquisarPorParticipante(int id) {
        try {
            PreparedStatement stmt = conexao.prepareStatement(""
                    + "SELECT id, nome , fk_participante FROM categoria "
                    + "WHERE fk_participante = ?");
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                Categoria c = new Categoria();
                c.setId(result.getInt("id"));
                c.setNome(result.getString("nome"));
                return c;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public List<Categoria> listarCatDoParticipante(int id) {
        try {
            PreparedStatement stmt;
            stmt = conexao.prepareStatement("SELECT * FROM categoria where fk_participante = ?");
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            List<Categoria> lista = new ArrayList<>();
            while (result.next()) {
                Categoria c = new Categoria();
                c.setId(result.getInt("id"));
                c.setNome(result.getString("nome"));
                lista.add(c);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public int cadastrarComParticipante(Categoria obj, int id) {
        try {
            PreparedStatement stmt;
            stmt = conexao.prepareStatement(
                    "INSERT INTO categoria(nome , fk_participante)"
                    + " VALUES(?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, obj.getNome());
            stmt.setInt(2, id);
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

    public boolean excluirCategoriaParticipante(Integer id) throws Exception {
        try {
            PreparedStatement stmt = conexao.prepareStatement(
                    "DELETE FROM CATEGORIA WHERE fk_participante = ?");
            stmt.setInt(1, id);
            int executeUpdate = stmt.executeUpdate();
            return executeUpdate != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
