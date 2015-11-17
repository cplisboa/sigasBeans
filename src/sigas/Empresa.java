package sigas;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Empresa {
	
	private int id;
	private String nome;
	private String contrato;
	private int num_sistemas;
	private String modulos;
	private int num_pocos;
	private int num_usuarios;
	private String numContrato;
	private Sistema[] sistemas;	
	private String masterUser;
	private HashMap<String, String> hashModulos = new HashMap<String, String>();
	private static final String RETRIEVE_SISTEMAS = "select * from sistema where empresa_id = ";

	public Empresa(int id, String nome, String contrato, int num_sistemas,
			String modulos, int num_pocos, int num_usuarios, String masterUser, String numContrato) {
		super();
		this.id = id;
		this.nome = nome;
		this.contrato = contrato;
		this.num_sistemas = num_sistemas;
		this.modulos = modulos;
		createHashModulos(modulos);
		this.num_pocos = num_pocos;
		this.num_usuarios = num_usuarios;
		this.masterUser = masterUser;
		this.numContrato = numContrato;
	}

	/** Construtor que recebe apenas o id da empresa */
	public Empresa (int emp_id) {
		this.id = emp_id;
	}

	//Cria hash com os modulos da empresa
	private void createHashModulos(String modulos) {
		if (modulos!=null) {
			StringTokenizer str = new StringTokenizer(modulos, ":");
			
			while (str.hasMoreTokens()){			
				String unit = str.nextToken().trim();
				hashModulos.put(unit, unit);			
			}
		}
	}

	/**
	 * Retona "selected" casoo modulo esteja habilitado, para contrução de seleção multipla na tela de criação de empresas
	 * @param unit Numero do Módulo
	 * @return "selected" ou vazio
	 */
	public String isModuleEnable (String unit) {
		if(hasModule(unit))
			return "selected";
		else
			return "";		
	}
	
	/** Verifica se a empresa tem permissão no modulo unit */
	public boolean hasModule(String unit) {
		boolean result = false;
		
		if(hashModulos.get(unit)!= null) {
			result=true;
		}
		return result;		
	}
	
	/** Preenche dados da empresa a partir do  ID (poderia ser usado no construtor) */
	public void setEmpresa (int emp_id) {
		String busca = "select * from empresa where empresa_id = '" + emp_id + "'";
		ResultSet rs = null;		
    	Database db = new Database();
    	try{
        	rs = db.execQuery(busca);
        	if (rs.next()) {
        		this.id = rs.getInt("empresa_id");
        		this.nome = rs.getString("empresa");
        		this.contrato = rs.getString("contrato");
        		this.num_sistemas = rs.getInt("num_sistema"); 
        		this.modulos = rs.getString("modulos");
        		this.num_pocos = rs.getInt("num_pocos");
        		this.num_usuarios = rs.getInt("num_usuarios");
        		this.numContrato = rs.getString("numContrato");
        	} else {
        		System.out.println("Empresa de ID= " + emp_id + "desconhecida");
        	}
        	rs.close();
    	} catch(Exception e) {
    		System.out.println("Erro recuperando dados da empresa de ID = " + emp_id + ". "+e.getMessage());
    	} finally {
    		db.close();    		
    	}		
	}
	
	
	/** Recupera Sistemas a partir da empresa */
	/*
	public getSistemas() {
		ArrayList<Sistema> list = new ArrayList<Sistema>(5);
    	Database db = new Database();
    	try{
        	ResultSet rs = db.execQuery(RETRIEVE_SISTEMAS + id);
        	while (rs.next()) {
        		int id_sistema = rs.getInt("sistema_id");
        		String nome = rs.getString("nome");
        		Sistema sis = new Sistema(id_sistema, this.id, nome)
        		list.add(sis);
        	}
    	} catch(Exception e) {
    		System.out.println("Erro recuperando lista de sistemas da empresa");
    	}
		Sistema[] sistArray = new Sistema[sistemasList.size()];
		sistemasList.toArray(sistArray);
		return sistArray;		   	
		
	}
	*/
	
	public static boolean removeEmpresa(String name) {
		Database db = new Database();		
		boolean ok=true;
		String sql = "delete from empresa emp where emp.empresa='" + name + "'";
		try {
			db.insert(sql);	
		} catch(Exception e) {
			System.out.println("Erro removendo Empresa "+e.getMessage());
			ok=false;
		}
		db.close();
		return ok;
	}

	public static boolean updateEmpresa(String empresaName, String numContrato, String contrato, String sistemas, String pocos, String usuarios, String master, String senha, String[] modulos) {
		Database db = new Database();		
		boolean ok=true;
		String modulosList = "";
		// update empresa emp set contrato = '12', num_sistema=5, num_pocos=5, num_usuarios=5 where emp.empresa='Internacional';
		System.out.println("Atualizando "+empresaName+" Contrato ="+contrato+" num_sistemas: "+sistemas);
		
		//Carregando lista de modulos para separar por dois pontos.
		for (int i=0; i<modulos.length; i++) {
			modulosList += modulos[i] + ":";
		}
		
		String sql = "update empresa emp set contrato = '" + contrato
				+ "', num_sistema="+sistemas
				+", num_pocos="+pocos 
				+ ", numContrato='"+numContrato
				+"', num_usuarios="+usuarios
				+", modulos='" + modulosList
				+"' where emp.empresa='" + empresaName + "'";
		System.out.println(sql);
		try {
			db.insert(sql);	
		} catch(Exception e) {
			System.out.println("Erro atualizando Empresa "+e.getMessage());
			ok=false;
		}
		
		// Atualizando usuario master
		sql = "update usuario set senha = '"+ senha +"' where usuario = '"+master+"'";
		try {
			db.insert(sql);	
		} catch(Exception e) {
			System.out.println("Erro atualizando Usuario master "+e.getMessage());
			ok=false;
		}		
		
		db.close();
		return ok;
	}	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getContrato() {
		return contrato;
	}
	public void setContrato(String contrato) {
		this.contrato = contrato;
	}
	public int getNum_sistemas() {
		return num_sistemas;
	}
	public void setNum_sistemas(int num_sistemas) {
		this.num_sistemas = num_sistemas;
	}
	public String getModulos() {
		return modulos;
	}
	public void setModulos(String modulos) {
		this.modulos = modulos;
	}
	public int getNum_pocos() {
		return num_pocos;
	}
	
	public String getNumContrato() {
		return numContrato;
	}

	public void setNum_pocos(int num_pocos) {
		this.num_pocos = num_pocos;
	}
	public int getNum_usuarios() {
		return num_usuarios;
	}
	public void setNum_usuarios(int num_usuarios) {
		this.num_usuarios = num_usuarios;
	}
	public Sistema[] getSistemas() {
		return sistemas;
	}

	public void setSistemas(Sistema[] sistemas) {
		this.sistemas = sistemas;
	}

	public String getMasterUser() {
		return masterUser;
	}

	public void setMasterUser(String masterUser) {
		this.masterUser = masterUser;
	}
	
}
