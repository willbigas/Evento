package util;

/**
 *
 * @author Will
 */
public class Texto {

    /**
     * CRUD Texto
     */
    // Sucess
    public static final String SUCESS_CREATE = "Cadastrado com Sucesso!";
    public static final String SUCESS_EDIT = "Editado com Sucesso!";
    public static final String SUCESS_DELETE = "Excluido com Sucesso!";
    public static final String SUCESS_OUT = "Saída gravada com sucesso";
    public static final String SUCESS_IN = "Entrada gravada com sucesso";

    // Errors
    public static final String ERROR_DELETE = "Erro ao excluir";
    public static final String ERROR_CREATE = "Erro ao Cadastrar!";
    public static final String ERROR_EDIT = "Erro ao Editar!";
    public static final String ERROR_IN = "Erro ao gravar Entrada";
    public static final String ERROR_OUT = "Erro ao Gravar a Saida";

    // Invalid Inputs
    public static final String ERRO_NAME_INVALID = "Nome Invalido!";
    public static final String ERROR_VALUE_INVALID = "Valor Invalido!";

    // Empty values
    public static final String EMPTY_INPUT = "Algum dos campos estão vazios";

    // Item not selected
    public static final String NOT_SELECTED_PRODUCT = "Nenhum Produto Selecionado!";
    public static final String NOT_SELECTED_INPUT = "Nenhuma Linha Selecionada!";

    // Information // question
    public static final String ACTION_IRREVERSIBLE = "Você deseja Realmente excluir? esta acao e irreversivel";
    public static final String CATEGORY_DEPENDENCY = "Existem Categorias vinculadas a esse Participante,"
            + " Por favor navegue até o contexto de Categorias e Remova as dependencias!";

    // Nothing found
    public static final String NOTHING_FOUND_VALUE = "Nenhum valor total encontrado!";

    // Others
    public static final String GREATER_TOTAL_VALUE = "O Valor Recebido é menor que "
            + "o valor Total da Entrada";
    public static final String OUTDATE_BEFORE_INDATE = "A data de Saida "
            + "Antecede a data de Entrada!";

}
