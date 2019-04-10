package control;

import dao.CategoryDao;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Category;
import model.tablemodel.CategoryTableModel;
import util.OptionPane;
import util.Text;
import view.category.ManageCategory;

/**
 *
 * @author William
 */
public class CategoryControl {

    Category CATEGORY;
    List<Category> CATEGORY_LIST;
    CategoryDao CATEGORY_DAO;
    CategoryTableModel CATEGORIA_TABLE;
    Integer INDEX_SELECTED = 0;

    public CategoryControl() {
        CATEGORY_DAO = new CategoryDao();
        CATEGORY_LIST = new ArrayList<>();
        CATEGORIA_TABLE = new CategoryTableModel();
        updateJTableCategory();
        setModelOfTable();

    }

    private String TF_NOME = "";
    private String LBL_ID = "";

    private void getFieldsEdit() {
        TF_NOME = null;
        LBL_ID = null;
        TF_NOME = view.category.EditCategory.tfNome.getText();
        LBL_ID = view.category.EditCategory.lblCodigoCategoria.getText();
    }

    private void getFieldsInsert() {
        TF_NOME = null;
        TF_NOME = view.category.CreateCategory.tfNome.getText();
    }

    public void updateJTableCategory() {
        CATEGORY_LIST = CATEGORY_DAO.listar();
        CATEGORIA_TABLE.clear();
        CATEGORIA_TABLE.addListOfObject(CATEGORY_LIST);
    }

    public void setModelOfTable() {
        ManageCategory.tblCategoria.setModel(CATEGORIA_TABLE);
    }

    public void InsertUserAction() {
        getFieldsInsert();
        CATEGORY = new Category();
        CATEGORY.setNome(TF_NOME); // mudar campos
        int idCategoria = CATEGORY_DAO.cadastrar(CATEGORY);
        if (idCategoria != 0) {
            CATEGORY.setId(idCategoria);
            CATEGORIA_TABLE.addObject(CATEGORY);
            OptionPane.msgInfo(Text.SUCESS_CREATE);
        } else {
            OptionPane.msgError(Text.ERROR_CREATE);
        }
        CATEGORY = null;
    }

    public int getSelectedIndex() {
        return ManageCategory.tblCategoria.getSelectedRow();
    }

    public void loadFieldsEditUserAction() {
        CATEGORY = CATEGORIA_TABLE.getObject(ManageCategory.tblCategoria.getSelectedRow());
        INDEX_SELECTED = getSelectedIndex();
    }

    public void changeFieldsOnEdit() {
        view.category.EditCategory.tfNome.setText(CATEGORY.getNome());
        view.category.EditCategory.lblCodigoCategoria.setText(String.valueOf(CATEGORY.getId()));
    }

    public void updateUserAction() {
        getFieldsEdit();
        CATEGORY = new Category();
        CATEGORY.setId(Integer.valueOf(LBL_ID));
        CATEGORY.setNome(TF_NOME);
        boolean inserido = CATEGORY_DAO.alterar(CATEGORY);
        if (inserido) {
            CATEGORIA_TABLE.updateObject(INDEX_SELECTED, CATEGORY);
            OptionPane.msgInfo(Text.SUCESS_EDIT);
        } else {
            OptionPane.msgError(Text.ERROR_EDIT);
        }
        CATEGORY = null;
    }

    public void deleteUserAction() {
        CATEGORY = CATEGORIA_TABLE.getObject(ManageCategory.tblCategoria.getSelectedRow());
        if (CATEGORY == null) {
            OptionPane.msgInfo(Text.NOT_SELECTED_INPUT);
            return;
        }
        int result = OptionPane.msgConfirm(Text.ACTION_IRREVERSIBLE);
        if (result == JOptionPane.YES_OPTION) {
            if (CATEGORY_DAO.deletar(CATEGORY)) {
                CATEGORIA_TABLE.removeObject(getSelectedIndex());
                OptionPane.msgInfo(Text.SUCESS_DELETE);
            } else {
                OptionPane.msgError(Text.ERROR_DELETE);
            }
        } 

    }

}
