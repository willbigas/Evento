package control;

import dao.CategoriaDao;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import model.Categoria;
import view.participante.CadastrarParticipante;

/**
 *
 * @author William
 */
public class ParticipanteControl {

    CategoriaDao CATEGORIA_DAO;

    public ParticipanteControl() {
        CATEGORIA_DAO = new CategoriaDao();
    }

    public void loadComboCategory() {
        List<Categoria> categorias = CATEGORIA_DAO.listar();
        Object[] items = categorias.toArray();
        DefaultComboBoxModel model = new DefaultComboBoxModel(items);
        CadastrarParticipante.cbCategoria.setModel(model);
    }
}
