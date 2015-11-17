package sigas;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	
	// ALTERAR URL CASO BANCO SEJA MOVIDO
	private static final String URL = "jdbc:firebirdsql:localhost/3050:C:/juper/old_site/SIGAS.GDB";
	private static final String USER = "SYSDBA";
	private static final String PASSWD = "masterkey";
	private Connection con;	
	
	static{
		try{
			Class.forName("org.firebirdsql.jdbc.FBDriver");		
		} catch(Exception e) {
			System.out.println("Nao foi possivel carregar plugin JDBC. "+e);
		}
	}
    
	public Database () {
		this (URL, USER, PASSWD);
	}
	

	public Database (String url, String user, String passwd){ 
		try{
			con = DriverManager.getConnection(url, user, passwd);
			System.out.println("Conectado!");
		}catch (Exception e) {
		   System.out.println("nao conectado! "+e.getMessage());
		}		
	}
	
	public void close(){
		try{ 
			con.close();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	public void insert(String sql) {	
		Statement smtp; 

		try{ // Gera a Query para o banco
			smtp = con.createStatement();
			smtp.executeUpdate(sql);
			smtp.close(); 
						
		} catch (SQLException sqlex){ // Erro ao executar a Query no banco
			System.out.println("Erro inserindo dados na base "+sqlex.getMessage());
			sqlex.printStackTrace(); 
		} catch (Exception e) {
			System.out.println("Exceção geral inserindo dados na base "+e.getMessage());			
		}
		
	}	
	
	public ResultSet execQuery(String consulta) {
 
		Statement smtp; 
		ResultSet rs=null; 

		try{ // Gera a Query para o banco 
			smtp = con.createStatement(); 
			rs = smtp.executeQuery(consulta);
			
			//smtp.close(); 
		} catch (SQLException sqlex){ // Erro ao executar a Query no banco 
			sqlex.printStackTrace(); 
		} 		
		return rs;
	}
	

}
