package dao;

import interfaces.DaoI;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import model.Participant;

/**
 *
 * @author William
 */
public class ParticipantDao extends Dao implements DaoI<Participant> {

    CategoryDao categoriaDao = new CategoryDao();

    public ParticipantDao() {
        super();

    }

    @Override
    public List<Participant> listar() {
        try {
            PreparedStatement stmt;
            stmt = conexao.prepareStatement("SELECT * FROM PARTICIPANTE ORDER BY ID");
            ResultSet result = stmt.executeQuery();
            List<Participant> lista = new ArrayList<>();
            while (result.next()) {
                Participant participante = new Participant();
                participante.setId(result.getInt("id"));
                participante.setNome(result.getString("nome"));
                participante.setCpf(result.getString("cpf"));
                participante.setEmail(result.getString("email"));
                participante.setTelefone(result.getString("telefone"));
                List<Category> listCategorias = categoriaDao.listarCatDoParticipante(result.getInt("id"));
                participante.setCategorias(listCategorias);
                lista.add(participante);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public int cadastrar(Participant obj) {
        try {
            PreparedStatement stmt;
            stmt = conexao.prepareStatement(
                    "INSERT INTO PARTICIPANTE(NOME, CPF, EMAIL, TELEFONE)"
                    + " VALUES(?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getCpf());
            stmt.setString(3, obj.getEmail());
            stmt.setString(4, obj.getTelefone());
            ResultSet res;
            Integer idParticipante;
            if (stmt.executeUpdate() > 0) {
                res = stmt.getGeneratedKeys();
                res.next();
                idParticipante = res.getInt(1);
                gravarCategorias(obj, idParticipante);
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
    public boolean alterar(Participant obj) {
        try {
            PreparedStatement stmt = conexao.prepareStatement(""
                    + "UPDATE PARTICIPANTE SET NOME = ? , CPF = ? , EMAIL = ? , TELEFONE = ? WHERE  id = ?");
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getCpf());
            stmt.setString(3, obj.getEmail());
            stmt.setString(4, obj.getTelefone());
            stmt.setInt(5, obj.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deletar(Participant obj) {
        try {
            PreparedStatement stmt = conexao.prepareStatement("DELETE FROM PARTICIPANTE WHERE ID = ?");
            stmt.setInt(1, obj.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public List<Participant> pesquisarPorTermo(String termo) {
        try {
            PreparedStatement stmt = conexao.prepareStatement(""
                    + "SELECT * FROM PARTICIPANTE "
                    + "WHERE NOME LIKE ?");
            stmt.setString(1, "%" + termo + "%");
            ResultSet result = stmt.executeQuery();
            List<Participant> lista = new ArrayList<>();
            while (result.next()) {
                Participant participante = new Participant();
                participante.setId(result.getInt("id"));
                participante.setNome(result.getString("nome"));
                participante.setCpf(result.getString("cpf"));
                participante.setEmail(result.getString("email"));
                participante.setTelefone(result.getString("telefone"));
                participante.setCategorias(categoriaDao.listarCatDoParticipante(result.getInt("id")));
                lista.add(participante);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

    @Override
    public Participant lerPorId(int id) {
        try {
            PreparedStatement stmt = conexao.prepareStatement(""
                    + "SELECT * FROM participant "
                    + "WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                Participant participante = new Participant();
                participante.setId(result.getInt("id"));
                participante.setNome(result.getString("nome"));
                participante.setCpf(result.getString("cpf"));
                participante.setEmail(result.getString("email"));
                participante.setTelefone(result.getString("telefone"));
                participante.setCategorias(categoriaDao.listarCatDoParticipante(result.getInt("id")));
                return participante;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    private void gravarCategorias(Participant obj, int id) throws SQLException {
        if (obj.getCategorias() != null && !obj.getCategorias().isEmpty()) {
            for (Category categoria : obj.getCategorias()) {
                categoriaDao.cadastrarComParticipante(categoria, id);
            }
        }

    }
}
