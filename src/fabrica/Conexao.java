package fabrica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Alunos
 */
public class Conexao {
    private final static String URL = "jdbc:mysql://localhost:3306/evento";
    private final static String USER="root";
    private final static String PASS="";
    private static Connection conexao;
    
    public static Connection pegaConexao(){
        if(conexao==null){
            try {
                conexao = DriverManager.getConnection(URL, USER, PASS);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println("Conectou...");
        }
        return conexao;
    }
    
}
