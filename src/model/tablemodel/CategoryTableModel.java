package model.tablemodel;

import interfaces.ActionsTableModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Category;

public class CategoryTableModel extends AbstractTableModel implements ActionsTableModel<Category> {

    // Constantes representando o índice das colunas
    private static final int ID = 0;
    private static final int NOME = 1;
    private static final int PARTICIPANTE = 2;

    // Lista de Objetos<Produtos> a serem exibidos na tela.
    private List<Category> linhas;

    // Colunas da Tabela (View)
    private String[] colunas = {"ID", "NOME", "COD.Participante"};

    // Cria um ProdutoTableModel sem nenhuma linha
    public CategoryTableModel() {
        linhas = new ArrayList<>();
    }

    // Cria um ProdutoTableModel contendo a lista recebida por parâmetro 
    public CategoryTableModel(List<Category> listCategorias) {
        linhas = listCategorias;
    }

    @Override
    public int getRowCount() {
        return linhas.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case ID:
                return Integer.class;
            case NOME:
                return String.class;
            case PARTICIPANTE:
                return Integer.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        Category categoria = linhas.get(linha);
        switch (coluna) {
            case ID:
                return categoria.getId();
            case NOME:
                return categoria.getNome();
            case PARTICIPANTE:
                return categoria.getCodigoParticipante();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public void setValueAt(Object valor, int linha, int coluna) {
        Category categoria = linhas.get(linha);
        switch (coluna) {
            case ID:
                categoria.setId(Integer.valueOf((String) valor));
                break;
            case NOME:
                categoria.setNome((String) valor);
                break;
            case PARTICIPANTE:
                categoria.setCodigoParticipante(Integer.valueOf((String) valor));
                break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");

        }
        this.fireTableCellUpdated(linha, coluna); // Atualiza Celula da tabela

    }

    @Override
    public Category getObject(int indiceLinha) {
        return linhas.get(indiceLinha);
    }

    @Override
    public void addObject(Category categoria) {
        linhas.add(categoria);
        int ultimoIndice = getRowCount() - 1; // linhas -1
        fireTableRowsInserted(ultimoIndice, ultimoIndice); // atualiza insert
    }

    @Override
    public void removeObject(int indiceLinha) {
        linhas.remove(indiceLinha);
        fireTableRowsDeleted(indiceLinha, indiceLinha); // atualiza delete
    }

    @Override
    public void updateObject(int indiceLinha, Category categoria) {
        linhas.set(indiceLinha, categoria);
        fireTableRowsUpdated(indiceLinha, indiceLinha); // atualiza delete
    }

    @Override
    public void addListOfObject(List<Category> categorias) {
        int indice = getRowCount();
        linhas.addAll(categorias);
        fireTableRowsInserted(indice, indice + categorias.size());
        fireTableDataChanged();
    }

    @Override
    public void clear() {
        linhas.clear();
        fireTableDataChanged(); // atualiza toda tabela.
    }

}
