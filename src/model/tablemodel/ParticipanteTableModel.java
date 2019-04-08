package model.tablemodel;

import interfaces.ActionsTableModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Participante;

public class ParticipanteTableModel extends AbstractTableModel implements ActionsTableModel<Participante> {

    // Constantes representando o índice das colunas
    private static final int ID = 0;
    private static final int NOME = 1;
    private static final int CPF = 2;
    private static final int EMAIL = 3;
    private static final int TELEFONE = 4;

    // Lista de Objetos<Produtos> a serem exibidos na tela.
    private List<Participante> linhas;

    // Colunas da Tabela (View)
    private String[] colunas = {"ID", "NOME", "CPF", "EMAIL", "TELEFONE"};

    // Cria um ProdutoTableModel sem nenhuma linha
    public ParticipanteTableModel() {
        linhas = new ArrayList<>();
    }

    // Cria um ProdutoTableModel contendo a lista recebida por parâmetro 
    public ParticipanteTableModel(List<Participante> listParticipantes) {
        linhas = listParticipantes;
    }

    /**
     * Retorna quantidade de Linhas.
     *
     * @return
     */
    @Override
    public int getRowCount() {
        return linhas.size();
    }

    /**
     * Retorna a quantidade de colunas.
     *
     * @return
     */
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    /**
     * Retorna o Nome das colunas.
     *
     * @param columnIndex
     * @return
     */
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
            case CPF:
                return String.class;
            case EMAIL:
                return String.class;
            case TELEFONE:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    /**
     * Pega o Valor de uma Coluna/Linha da Tabela
     *
     * @param linha
     * @param coluna
     * @return
     */
    @Override
    public Object getValueAt(int linha, int coluna) {
        Participante participante = linhas.get(linha);
        switch (coluna) {
            case ID:
                return participante.getId();
            case NOME:
                return participante.getNome();
            case CPF:
                return participante.getCpf();
            case EMAIL:
                return participante.getEmail();
            case TELEFONE:
                return participante.getTelefone();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    /**
     * Altera Valor de uma Coluna / Linha da tabela
     *
     * @param valor
     * @param linha
     * @param coluna
     */
    @Override
    public void setValueAt(Object valor, int linha, int coluna) {
        Participante participante = linhas.get(linha);
        switch (coluna) {
            case ID:
                participante.setId(Integer.valueOf((String) valor));
                break;
            case NOME:
                participante.setNome((String) valor);
                break;
            case CPF:
                participante.setCpf((String) valor);
                break;
            case EMAIL:
                participante.setEmail((String) valor);
                break;
             case TELEFONE:
                participante.setTelefone((String) valor);
                break;
            default:
                // Não deve ocorrer, pois só existem 4 colunas
                throw new IndexOutOfBoundsException("columnIndex out of bounds");

        }
        this.fireTableCellUpdated(linha, coluna); // Atualiza Celula da tabela

    }

    /**
     * Retorna o Produto referente a linha especificada
     *
     * @param indiceLinha
     * @return
     */
    @Override
    public Participante getObject(int indiceLinha) {
        return linhas.get(indiceLinha);
    }

    /**
     * Adiciona o Produto especificado ao modelo.
     *
     * @param produto
     */
    @Override
    public void addObject(Participante produto) {
        linhas.add(produto);
        int ultimoIndice = getRowCount() - 1; // linhas -1
        fireTableRowsInserted(ultimoIndice, ultimoIndice); // atualiza insert
    }

    /**
     * Remove o produto da linha especificada.
     *
     * @param indiceLinha
     */
    @Override
    public void removeObject(int indiceLinha) {
        linhas.remove(indiceLinha);
        fireTableRowsDeleted(indiceLinha, indiceLinha); // atualiza delete
    }

    /**
     * Remove o produto da linha especificada.
     *
     * @param indiceLinha
     * @param p
     */
    @Override
    public void updateObject(int indiceLinha, Participante p) {
        linhas.set(indiceLinha, p);
        fireTableRowsUpdated(indiceLinha, indiceLinha); // atualiza delete
    }

    /**
     * Adiciona uma lista de Produtos no final da lista.
     *
     * @param participantes
     */
    @Override
    public void addListOfObject(List<Participante> participantes) {
        int indice = getRowCount();
        linhas.addAll(participantes);
        fireTableRowsInserted(indice, indice + participantes.size());
        fireTableDataChanged();
    }

    /**
     * Limpa todos os registros da tabela
     */
    @Override
    public void clear() {
        linhas.clear();
        fireTableDataChanged(); // atualiza toda tabela.
    }

}
