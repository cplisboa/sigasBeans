package sigas;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

public class User {

	private String usuario;
	private int idEmpresa;
	private String nome;
	private String senha;
	private String empresa;
	private String telefone;
	private String acesso;
	
	public User(String usuario, int idEmpresa, String nome, String senha,
			String empresa, String telefone, String acesso) {
		super();
		this.usuario = usuario;
		this.idEmpresa = idEmpresa;
		this.nome = nome;
		this.senha = senha;
		this.empresa = empresa;
		this.telefone = telefone;
		this.acesso = acesso;
	}
		
	/**
	 * Método STATIC para recuperação dos usuários de uma determinada empresa
	 * @param idEmpresa
	 * @return
	 */
	public static User[] getUserList(int idEmpresa) {
		ArrayList<User> list = new ArrayList<User>();
		ResultSet rs = null;
		String sql = "select * from usuario where id_empresa="+idEmpresa;
				
        System.out.println("Iniciando conexão com banco de dados em getUserList");
    	Database db = new Database();
    	try{
        	rs=db.execQuery(sql);
        	while(rs.next()) {
        		User user = createUser(rs);        		
        		if (user!= null) {
        			list.add(user);
        		}
        	}      
        	rs.close();
    	} catch(Exception e) {
    		System.out.println("Erro recuperando lista de usuário da empresa "+idEmpresa+". "+e.getMessage());
    	}
    	db.close();
    	
		User[] userArray = new User[list.size()];
		list.toArray(userArray);
		return userArray;    	
	}
		
	/**
	 * Cria usuário a partir do resultSet
	 * @param rs
	 * @return
	 */
	private static User createUser(ResultSet rs) {
		User user = null;
	
		try {
			String nome = rs.getString("nome");
			String usuario = rs.getString("usuario");
			String senha = rs.getString("senha");
			String empresa = rs.getString("empresa");
			String telefone = rs.getString("telefone");
			String acesso = rs.getString("acesso");
			int idEmpresa = rs.getInt("id_empresa");
			
			user = new User(usuario,idEmpresa, nome, senha, empresa, telefone, acesso);			
		} catch (Exception e) {
			System.out.println("Erro lendo rs de criação de usuário");
		}		
		return user;
	}
	

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getTelefone() {
		return telefone;
	}




	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}




	public String getAcesso() {
		return acesso;
	}




	public void setAcesso(String acesso) {
		this.acesso = acesso;
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		User[] list = User.getUserList(1);
		System.out.println("Lista de usuário da empresa 1");
		for(int i=0; i<list.length; i++) {
			System.out.println(" ... "+list[i].getUsuario());
		}
	
	}
}
