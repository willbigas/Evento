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

    public void updateJTableCategory() {
        CATEGORY_LIST = CATEGORY_DAO.listar();
        CATEGORIA_TABLE.clear();
        CATEGORIA_TABLE.addListOfObject(CATEGORY_LIST);
    }

    public void setModelOfTable() {
        ManageCategory.tblCategoria.setModel(CATEGORIA_TABLE);
    }

    public int getSelectedIndex() {
        return ManageCategory.tblCategoria.getSelectedRow();
    }

    public void searchCategoryAction() {
        String pesquisa = view.category.ManageCategory.tfPesquisar.getText();
        List<Category> categoriasPesquisadas = CATEGORY_DAO.pesquisarPorTermo(pesquisa);
        CATEGORIA_TABLE.clear();
        CATEGORIA_TABLE.addListOfObject(categoriasPesquisadas);
    }

    public void deleteCategoryAction() {
        if (getSelectedIndex() == -1) {
            OptionPane.msgInfo(Text.NOT_SELECTED_INPUT);
            return;
        }
        CATEGORY = CATEGORIA_TABLE.getObject(getSelectedIndex());
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
