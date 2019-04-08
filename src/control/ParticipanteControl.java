package control;

import dao.CategoriaDao;
import dao.ParticipanteDao;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import model.Categoria;
import model.Participante;
import model.tablemodel.CategoriaTableModel;
import view.participante.CadastrarParticipante;

/**
 *
 * @author William
 */
public class ParticipanteControl {

    Participante PARTICIPANT;
    ParticipanteDao PARTICIPANT_DAO;
    CategoriaDao CATEGORIA_DAO;
    CategoriaTableModel CATEGORY_TABLE;

    private String TF_NOME = "";
    private String TF_EMAIL = "";
    private String TF_CPF = "";
    private String TF_TELEFONE = "";

    public ParticipanteControl() {
        CATEGORIA_DAO = new CategoriaDao();
        PARTICIPANT_DAO = new ParticipanteDao();
        CATEGORY_TABLE = new CategoriaTableModel();
        setModelOfTableCategory();
    }

    public void setModelOfTableCategory() {
        CadastrarParticipante.tblCategoriaParticipante.setModel(CATEGORY_TABLE);
    }

    private void getFieldsInsert() {
        TF_NOME = null;
        TF_EMAIL = null;
        TF_CPF = null;
        TF_TELEFONE = null;
        TF_NOME = view.participante.CadastrarParticipante.tfNome.getText();
        TF_EMAIL = view.participante.CadastrarParticipante.tfEmail.getText();
        TF_CPF = view.participante.CadastrarParticipante.tfCpf.getText();
        TF_TELEFONE = view.participante.CadastrarParticipante.tfTelefone.getText();
    }

    public void loadComboCategory() {
        List<Categoria> categorias = CATEGORIA_DAO.listar();
        Object[] items = categorias.toArray();
        DefaultComboBoxModel model = new DefaultComboBoxModel(items);
        CadastrarParticipante.cbCategoria.setModel(model);
    }

    public List<Categoria> getCategorysAdded() {
        List<Categoria> categoriasDoParticipante = new ArrayList<>();
        for (int i = 0; i < CATEGORY_TABLE.getRowCount(); i++) {
            categoriasDoParticipante.add(CATEGORY_TABLE.getObject(i));
        }
        return categoriasDoParticipante;
    }

    public void insertCategoryParticipantAction() {
        Categoria catDaComboBox = (Categoria) CadastrarParticipante.cbCategoria.getSelectedItem();
        CATEGORY_TABLE.addObject(catDaComboBox);
    }
    
     public void deleteCategoryParticipantAction() {
        CATEGORY_TABLE.removeObject(CadastrarParticipante.tblCategoriaParticipante.getSelectedRow());
    }

    public void createParticipantAction() {
        getFieldsInsert();
        PARTICIPANT = new Participante();
        PARTICIPANT.setId(Integer.MAX_VALUE);
        PARTICIPANT.setNome(TF_NOME);
        PARTICIPANT.setCpf(TF_CPF);
        PARTICIPANT.setEmail(TF_EMAIL);
        PARTICIPANT.setTelefone(TF_TELEFONE);
        PARTICIPANT.setCategorias(getCategorysAdded());
        PARTICIPANT_DAO.cadastrar(PARTICIPANT);
        
        
    }
}
