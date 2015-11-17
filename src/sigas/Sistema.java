package sigas;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;

// ***** ATENÇÃO!! ESTAMOS UTIULIZANDO COMO CODE A STRING UTM_NORTE!! CASO SEJA NECESSÁRIO TROCAR O CODE, MUDAR SQLS AQUI!!
public class Sistema {
	int id_sistema;
	int id_empresa;	
	String nome;
	Poco[] pocos;
	
	public Sistema(int id_sistema, int id_empresa, String nome) {
		super();
		this.id_sistema = id_sistema;
		this.id_empresa = id_empresa;
		this.nome = nome;
	}
	
	public Sistema(int idSistema) {
		super();
		this.id_sistema = idSistema;
	}
		
	public int getId_sistema() {
		return id_sistema;
	}
	public void setId_sistema(int id_sistema) {
		this.id_sistema = id_sistema;
	}
	public int getId_empresa() {
		return id_empresa;
	}
	public void setId_empresa(int id_empresa) {
		this.id_empresa = id_empresa;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Poco[] getPocos() {
		return this.pocos;
	}
	
	/** Retorna numero de poços já cadastrados em um sistema */
	public int getCountPocos() {
		int cont=0;
		Database db = new Database();
		try {
			System.out.println("Executando query para buscar pocos de um sistema");
			ResultSet rs = db.execQuery("select count(1) cont from poco_gerais where id_sistema="+this.getId_sistema());
			if (rs.next()){				
				cont = rs.getInt("cont");
				System.out.println("Sistema "+this.getId_sistema()+" tem " + cont + " poços");
			}
		} catch (Exception e) {
			System.out.println("Erro recuperando numero de poços do sistema "+this.getId_sistema()+". "+e.getMessage());
		}
		return cont; 
	}
	
	public void fillPocos(Database db) {
		ArrayList<Poco> pocosList = new ArrayList<Poco>(5);

		try {
			System.out.println("Executando query para buscar pocos de um sistema");
			ResultSet rs = db.execQuery("select id_poco, nome, utm_norte from poco_gerais where id_sistema="+this.getId_sistema()+" order by nome");
			while (rs.next()){				
				int poco_id = rs.getInt("id_poco");
				String name = rs.getString("nome");
				String code = rs.getString("utm_norte");
				
				System.out.println("Recuperei um Poco de nome: " + name + " e code: " + code);
				pocosList.add(new Poco(name, code, poco_id));
			}
			pocos = new Poco[pocosList.size()];
			pocosList.toArray(pocos);			
		} catch (Exception e) {
			System.out.println("Erro recuperando lista de Pocos de um sistema. "+e.getMessage());
		}
		
	}
	
	public static void main(String[] args) {
		
		Sistema sis = new Sistema(1);
		System.out.println("Temos "+sis.getCountPocos()+" poços.");

/*		Database db = new Database();		
		DataRequester dr = new DataRequester("jdbc:firebirdsql:localhost/3050:C:/juper/old_site/SIGAS.GDB");
		
		//Pegando sistemas da empresa 1
		Sistema[] sysList = dr.getSistemas(1);
		
		//Preenchendo pocos desses sistemas	
		for(int i=0; i < sysList.length; i++) {
			sysList[i].fillPocos(db);
		}
		
		//Agora percorrendo a lista de sistemas e apresentando seus poços
		for (int i=0; i < sysList.length; i++) {
			System.out.println("Pocos do Sistema: " + sysList[i].nome);
			for (int j=0; j<sysList[i].pocos.length; j++) {
				System.out.println("....  " + sysList[i].pocos[j].name + " code: " + sysList[i].pocos[j].code);
			}
		}
		db.close();
	*/	
	}

}
