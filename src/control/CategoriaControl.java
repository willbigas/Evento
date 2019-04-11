package control;

import dao.CategoriaDao;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Categoria;
import model.tablemodel.CategoriaTableModel;
import util.Mensagem;
import util.Texto;
import view.categoria.JanelaGerenciarCategoria;

/**
 *
 * @author William
 */
public class CategoriaControl {

    Categoria categoria;
    List<Categoria> listCategorias;
    CategoriaDao categoriaDao;
    CategoriaTableModel categoriaTable;
    Integer linhaSelecionada = 0;

    public CategoriaControl() {
        categoriaDao = new CategoriaDao();
        listCategorias = new ArrayList<>();
        categoriaTable = new CategoriaTableModel();
        atualizarJTableCategoria();
        mudaModeloDaTable();

    }

    public void atualizarJTableCategoria() {
        listCategorias = categoriaDao.listar();
        categoriaTable.clear();
        categoriaTable.addListOfObject(listCategorias);
    }

    public void mudaModeloDaTable() {
        JanelaGerenciarCategoria.tblCategoria.setModel(categoriaTable);
    }

    public int pegaIndexSelecionada() {
        return JanelaGerenciarCategoria.tblCategoria.getSelectedRow();
    }

    public void pesquisarCategoriaAction() {
        String pesquisa = view.categoria.JanelaGerenciarCategoria.tfPesquisar.getText();
        List<Categoria> categoriasPesquisadas = categoriaDao.pesquisarPorTermo(pesquisa);
        categoriaTable.clear();
        categoriaTable.addListOfObject(categoriasPesquisadas);
    }

    public void deletarCategoriaAction() {
        if (pegaIndexSelecionada() == -1) {
            Mensagem.msgInfo(Texto.NOT_SELECTED_INPUT);
            return;
        }
        categoria = categoriaTable.getObject(pegaIndexSelecionada());
        if (categoria == null) {
            Mensagem.msgInfo(Texto.NOT_SELECTED_INPUT);
            return;
        }
        int result = Mensagem.msgConfirm(Texto.ACTION_IRREVERSIBLE);
        if (result == JOptionPane.YES_OPTION) {
            if (categoriaDao.deletar(categoria)) {
                categoriaTable.removeObject(pegaIndexSelecionada());
                Mensagem.msgInfo(Texto.SUCESS_DELETE);
            } else {
                Mensagem.msgError(Texto.ERROR_DELETE);
            }
        }

    }

}
