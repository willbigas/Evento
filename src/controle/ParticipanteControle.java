package controle;

import dao.CategoriaDao;
import dao.ParticipanteDao;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import modelo.Categoria;
import modelo.Participante;
import modelo.tablemodel.ParticipanteTableModel;
import util.Mensagem;
import util.Texto;
import view.participante.JanelaGerenciarParticipante;

/**
 *
 * @author William
 */
public class ParticipanteControle {

    Participante participante;
    ParticipanteDao participanteDao;
    CategoriaDao categoriaDao;
    ParticipanteTableModel participanteTable;
    List<Participante> listParticipantes;
    List<Categoria> listCategorias;
    public static final String[] nomeDasCategorias = {"Exatas", "Programação", "Letras", "Ciencias"};
    Integer linhaSelecionada = 0;

    private String campoId = "";
    private String campoNome = "";
    private String campoEmail = "";
    private String campoCpf = "";
    private String campoTelefone = "";

    public ParticipanteControle() {
        categoriaDao = new CategoriaDao();
        participanteDao = new ParticipanteDao();
        participanteTable = new ParticipanteTableModel();
        listParticipantes = new ArrayList<>();
        listCategorias = new ArrayList<>();
        atualizaTabelaParticipante();
        mudaModeloTabelaParticipante();

    }

    public void mudaModeloTabelaParticipante() {
        JanelaGerenciarParticipante.tblParticipante.setModel(participanteTable);
    }

    public void atualizaTabelaParticipante() {
        listParticipantes = participanteDao.listar();
        participanteTable.clear();
        participanteTable.addListOfObject(listParticipantes);
    }

    private void pegaCamposCriarParticipante() {
        campoNome = view.participante.JanelaGerenciarParticipante.tfNome.getText();
        campoEmail = view.participante.JanelaGerenciarParticipante.tfEmail.getText();
        campoCpf = view.participante.JanelaGerenciarParticipante.tfCpf.getText();
        campoTelefone = view.participante.JanelaGerenciarParticipante.tfTelefone.getText();
    }

    public void criarParticipanteAction() {
        pegaCamposCriarParticipante();
        criarParticipante();
        int idInserido = participanteDao.cadastrar(participante);
        criaNovoParticipante(idInserido);
    }

    private void criaNovoParticipante(int idInserido) {
        if (idInserido != 0) {
            participante.setId(idInserido);
            participanteTable.addObject(participante);
            listParticipantes.add(participante);
            Mensagem.msgInfo(Texto.SUCESS_CREATE);
        } else {
            Mensagem.msgError(Texto.ERROR_CREATE);
        }
        participante = null;
    }

    private void criarParticipante() {
        participante = new Participante();
        participante.setId(Integer.MAX_VALUE);
        participante.setNome(campoNome);
        participante.setCpf(campoCpf);
        participante.setEmail(campoEmail);
        participante.setTelefone(campoTelefone);
        varrendoCheckBoxs(); // verifica as checks selecionadas;
        participante.setCategorias(listCategorias);
        participante.setAtivo(true);
    }

    public void carregaCamposVisualizarAction() {
        participante = participanteTable.getObject(pegaLinhaSelecionadaParticipante());
        view.participante.JanelaGerenciarParticipante.tfCpf.setText(participante.getCpf());
        view.participante.JanelaGerenciarParticipante.tfEmail.setText(participante.getEmail());
        view.participante.JanelaGerenciarParticipante.tfNome.setText(participante.getNome());
        view.participante.JanelaGerenciarParticipante.tfTelefone.setText(participante.getTelefone());
        listCategorias.clear();
        listCategorias = categoriaDao.listarCatDoParticipante(participante.getId());
        System.out.println("Lista de Categorias do Participante:" + listCategorias);
        populaAsCategoriasDoParticipante();
        linhaSelecionada = pegaLinhaSelecionadaParticipante();
    }

    private void populaAsCategoriasDoParticipante() {
        JanelaGerenciarParticipante.checkExatas.setSelected(false);
        JanelaGerenciarParticipante.checkProgramacao.setSelected(false);
        JanelaGerenciarParticipante.checkLetras.setSelected(false);
        JanelaGerenciarParticipante.checkCiencias.setSelected(false);

        for (Categoria categoria : listCategorias) {
            if (categoria.getNome().equals(nomeDasCategorias[0])) {
                JanelaGerenciarParticipante.checkExatas.setSelected(true);
            }
            if (categoria.getNome().equals(nomeDasCategorias[1])) {
                JanelaGerenciarParticipante.checkProgramacao.setSelected(true);
            }
            if (categoria.getNome().equals(nomeDasCategorias[2])) {
                JanelaGerenciarParticipante.checkLetras.setSelected(true);
            }
            if (categoria.getNome().equals(nomeDasCategorias[3])) {
                JanelaGerenciarParticipante.checkCiencias.setSelected(true);
            }
        }
        listCategorias.clear();
    }

    public void deletarParticipanteAction() {
        participante = participanteTable.getObject(pegaLinhaSelecionadaParticipante());
        participante.setAtivo(false);
        if (participanteDao.alterar(participante)) {
            participanteTable.updateObject(pegaLinhaSelecionadaParticipante(), participante);
            listParticipantes.remove(pegaLinhaSelecionadaParticipante());
            atualizaTabelaParticipante();
            Mensagem.msgInfo(Texto.SUCESS_DISABLE);
        } else {
            Mensagem.msgInfo(Texto.ERROR_DISABLE);
        }
        participante = null;
//        atualizaTabelaParticipante();
    }

    public int pegaLinhaSelecionadaParticipante() {
        return JanelaGerenciarParticipante.tblParticipante.getSelectedRow();
    }

    public void varrendoCheckBoxs() {
        listCategorias.clear();
        List<JCheckBox> checks = new ArrayList<>();
        checks.add(view.participante.JanelaGerenciarParticipante.checkExatas);
        checks.add(view.participante.JanelaGerenciarParticipante.checkProgramacao);
        checks.add(view.participante.JanelaGerenciarParticipante.checkLetras);
        checks.add(view.participante.JanelaGerenciarParticipante.checkCiencias);
        for (int i = 0; i < 4; i++) {
            checks.get(i).setName(nomeDasCategorias[i]);
        }
        for (JCheckBox checkAtual : checks) {
            if (checkAtual.isSelected()) {
                for (int i = 0; i < 4; i++) {
                    if (checkAtual.getName().equals(nomeDasCategorias[i])) {
                        Categoria categoriaSelecionada = new Categoria();
                        categoriaSelecionada.setId(i + 1);
                        categoriaSelecionada.setCodigoParticipante(participante.getId());
                        categoriaSelecionada.setNome(nomeDasCategorias[i]);
                        listCategorias.add(categoriaSelecionada);
                    }
                }
            }
        }
        System.out.println(listCategorias);
    }

    public void pesquisarAction() {
        listParticipantes.clear();
        listParticipantes = participanteDao.pesquisarPorTermo(JanelaGerenciarParticipante.tfPesquisar.getText());
        participanteTable.clear();
        participanteTable.addListOfObject(listParticipantes);
    }
}
