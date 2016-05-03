package sigas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

public class DataRequester {

    private Database db=null;
    private String urlDb = null;

	public DataRequester(String url) {
		urlDb = url;
	}

	public Database getDb() {
		return db;
	}
	
	/** Retorna array com todos os poços de um sistema com seus respectivos dados cadastrais e ultimas medidas */
	public StatusPoco[] pocosOnline(int idSistema){
		
		Sistema sys = new Sistema(idSistema);
		StatusPoco[] statusPoco = null;
		Database db = new Database();
		sys.fillPocos(db);
		Poco[] pocos = sys.getPocos();
		statusPoco = new StatusPoco[pocos.length];		
		
		//Percorre lista de poços do sistema para recuperar dado online
		for (int i=0; i<pocos.length; i++) { 
		    System.out.println("Poço: " + pocos[i].getName());				
		    Medida med = onlineData(pocos[i].getCode());		    
		    statusPoco[i] = new StatusPoco(pocos[i], med);
		    		    
			if (med!=null) { 
				// Verificando se o poço está em operação
				if(med.getCorrente() > 0) {
					statusPoco[i].isOperating = true;
				}
				//Recupera informação de há quanto tempo não temos medidas do poço
				java.sql.Timestamp sqlTs = med.getTs();
				java.util.Date ts = (java.util.Date) sqlTs;
				long time = System.currentTimeMillis();
				Timestamp now = new Timestamp(time);		
				long diff = now.getTime() - ts.getTime();     
				
				long difHoras = diff/1000/60/60;
				long difDias = diff/1000/60/60/24;
				
				statusPoco[i].difDias = difDias;
				if(difDias >= 1) {
					statusPoco[i].unknownState = true;
				} else if (difHoras  > 8) {
					statusPoco[i].unknownState = true;
				}
			}
		}		
		return statusPoco;
	}

	
	/**
	 * Consulta a base e retorna ultima medida inserida
	 * 
	 */
	public Medida onlineData(String code){
		Medida med = null;
		String queryNivel = "SELECT FIRST 1 nivel,data FROM SIGAS_POCOS WHERE CODIGO_STR = '" + code + "' ORDER BY DATA DESC";
		String queryGrandezas ="SELECT FIRST 1 * FROM GRANDEZAS WHERE CODE = '" + code + "' ORDER BY DATA DESC";
		Database database = new Database();
		if(db==null)
			db = new Database(urlDb,"SYSDBA","masterkey");
    	try{
    		//Recuperando ultimo dado do bd(Nivel)
        	ResultSet rs = database.execQuery(queryNivel);
        	if(rs.next()){        		
        		med = new Medida();
        		med.setTs(rs.getTimestamp("data"));
        		med.setNivel(rs.getFloat("nivel"));
        	} else {
        		System.out.println("Nenhum dado na base para o poço: "+code);
        	}      
        	
        	ResultSet rx = database.execQuery(queryGrandezas);
        	if(rx.next()){        		
        		if(med==null)
        			med = new Medida();
        		med.setTs(rx.getTimestamp("DATA"));        		
				med.setCorrente(rx.getFloat("corrente"));
				med.setVazao(rx.getFloat("vazao"));
				med.setVolume(rx.getInt("volume"));				
				med.setCesp(rx.getFloat("cesp"));
				med.setNivel(rx.getFloat("nivel"));
        	} else {
        		System.out.println("Nenhuma Grandeza para o poço: "+code);
        	}      
        	
    	} catch(Exception e) {
    		System.out.println("Erro recuperando ultimo dado inserido");
    		e.printStackTrace();
    	}    	
    	return med;
	}
	
	public List<DadosAnuais> dadosAnuais(String code, String grandeza, String agregacao) {
		db = new Database(urlDb,"SYSDBA","masterkey");
		HashMap<Integer, DadosAnuais> hash = new HashMap<Integer, DadosAnuais>();

		String consulta = "select code, CAST(EXTRACT(MONTH FROM DATA) || '.' || EXTRACT(YEAR FROM DATA) AS CHAR(10)) as DATA,"
			+ " "+agregacao+"("+grandeza+") as agg"    //e.g. max(nivel)
			+ " from grandezas group by 1,2 order by 1,2";
		
		try {				    
		    ResultSet rs=db.execQuery(consulta);
			while (rs.next()){	
				String data = rs.getString("data");
				float agg = rs.getFloat("agg");

			  	StringTokenizer strData = new StringTokenizer(data, ".");	
				int mes = Integer.parseInt(strData.nextToken());
				int ano = Integer.parseInt(strData.nextToken());
				
				//Verifica se dado já existe na hash
				DadosAnuais anuais = hash.get(ano);
				if(anuais == null){
					anuais = new DadosAnuais(ano);
				}
				anuais.dadoMensal[mes] = agg;
				hash.put(ano, anuais);					
			}
			rs.close();
			db.close();
	    } catch (Exception e) {
		    System.out.println("Erro recuperando jornada diaria. Code="+code+". "+e.getMessage());
	    }
		List<DadosAnuais> lista = (List<DadosAnuais>) hash.values();
		return lista;
	}
	
	
	public float[][] jornada(String dayStr,String code) {
		
		db = new Database(urlDb,"SYSDBA","masterkey");		
		float[][] medida = new float[13][32];
		
	  	StringTokenizer strData = new StringTokenizer(dayStr, "/");	
		int dia = Integer.parseInt(strData.nextToken());
		int mes = Integer.parseInt(strData.nextToken());  //MES E DIA NAO SAO UTILIZADOS NO MAXIMO DO MES
		int ano = Integer.parseInt(strData.nextToken());

		String consulta = "select code, CAST(EXTRACT(DAY FROM DATA) || '.' || EXTRACT(MONTH FROM DATA) || '.'"
			+ " || EXTRACT(YEAR FROM DATA) AS CHAR(10)) as DATA, "
			+ " count(1) / 4 as jornada "
			+ " from grandezas "
			+ " where code='" + code + "'"
			+ " and corrente > 1 "
			+ " and extract (year from data) = " + ano
			+ " group by 1,2";
		
		try {				    
		    ResultSet rs=db.execQuery(consulta);
		    float anterior = 0;    
			while (rs.next()){	
				String data = rs.getString("data");
				float max = rs.getFloat("jornada");

				if(max > 24) {
					//casos de erro de medida que retornam + do que 24 horas de bombeamento
					max=anterior;
				}
			  	strData = new StringTokenizer(data, ".");	
				dia = Integer.parseInt(strData.nextToken());
				mes = Integer.parseInt(strData.nextToken());
				medida[mes][dia] = max;
				anterior = max;
			}
			rs.close();
			db.close();
	    } catch (Exception e) {
		    System.out.println("Erro recuperando jornada diaria. Code="+code+". "+e.getMessage());
	    }
		return medida;
	}
	
	
	public float[][] demanda(String dayStr, String code) {
		db = new Database(urlDb,"SYSDBA","masterkey");		
		float[][] medida = new float[13][32];
		
	  	StringTokenizer strData = new StringTokenizer(dayStr, "/");	
		int dia = Integer.parseInt(strData.nextToken());
		int mes = Integer.parseInt(strData.nextToken());  //MES E DIA NAO SAO UTILIZADOS NO MAXIMO DO MES
		int ano = Integer.parseInt(strData.nextToken());

		String consulta = "select code, CAST(EXTRACT(DAY FROM DATA) || '.' || EXTRACT(MONTH FROM DATA) || '.'"
			+ " || EXTRACT(YEAR FROM DATA) AS CHAR(10)) as DATA, "
		    + " max(hidrometro) - min(hidrometro) as demanda"
		    + " from grandezas "
		    + " where code='" + code + "' and extract (year from data) = " + ano
		    + " group by 1,2";
		
		try {				    
		    ResultSet rs=db.execQuery(consulta);
		        
			while (rs.next()){	
				String data = rs.getString("data");
				float max = rs.getFloat("demanda");

			  	strData = new StringTokenizer(data, ".");	
				dia = Integer.parseInt(strData.nextToken());
				mes = Integer.parseInt(strData.nextToken());
				medida[mes][dia] = max;
			}
			rs.close();
			db.close();
	    } catch (Exception e) {
		    System.out.println("Erro recuperando demanda diaria. Code="+code+". "+e.getMessage());
	    }
		return medida;
	}

	/**
	 * Calcula o nivel maximo dia-a-dia
	 * @param code
	 * @return
	 */
	public float[][] nivelAgrupadoMes(String dayStr, String agregacao, String code) {
		db = new Database(urlDb,"SYSDBA","masterkey");		
		float[][] medida = new float[13][32];
		
	  	StringTokenizer strData = new StringTokenizer(dayStr, "/");	
		int dia = Integer.parseInt(strData.nextToken());
		int mes = Integer.parseInt(strData.nextToken());  //MES E DIA NAO SAO UTILIZADOS NO MAXIMO DO MES
		int ano = Integer.parseInt(strData.nextToken());
		
	    String consulta = "select "+agregacao+"(nivel), CAST(EXTRACT(DAY FROM DATA) || '.' || EXTRACT(MONTH FROM "
	    	+ " DATA) || '.' || EXTRACT(YEAR FROM DATA) AS CHAR(10)) as DATA, code "
	    	+ " FROM GRANDEZAS "
	    	+ " WHERE code='" + code + "'"
	    	+ " AND EXTRACT (year from data) = "+ano+" and nivel >=0"
	    	+ " GROUP BY  2,3";

		try {				    
		    ResultSet rs=db.execQuery(consulta);
		        
			while (rs.next()){	
				String data = rs.getString("data");
				float max = rs.getFloat(agregacao);

			  	strData = new StringTokenizer(data, ".");	
				dia = Integer.parseInt(strData.nextToken());
				mes = Integer.parseInt(strData.nextToken());
				medida[mes][dia] = max;
			}
			rs.close();
			db.close();
	    } catch (Exception e) {
		    System.out.println("Erro recuperando nivel "+agregacao+" mensal. Code="+code+". "+e.getMessage());
	    }
		return medida;
	}
	
	
	/** Recupera as medidas PRÓXIMAS aquele dia (-10 até +10)
	 * 
	 * @param dayStr
	 * @param code
	 * @param type Tipo do dado a ser recuperado (nivel, vazao, corrente)
	 * @return
	 */
	public Medida[][] daysData(String dayStr, String code, String type) {		
		
		if (type.equals("nivelMax")) {
			//calcNivelMax()
		}
		db = new Database(urlDb,"SYSDBA","masterkey");		
		Medida[][] med = new Medida[20][24];
		int linhas = 20;
						
		//Setar a data requisitada em um calendar
	    java.util.Calendar cal = Calendar.getInstance();			
	  	StringTokenizer strData = new StringTokenizer(dayStr, "/");	
		int dia = Integer.parseInt(strData.nextToken());
		int mes = Integer.parseInt(strData.nextToken())-1;
		int ano = Integer.parseInt(strData.nextToken());
	    cal.set(ano,mes,dia);

	    //Diminui 10 para pegar 10 dias antes e 10 dias depois
	    cal.add(Calendar.DATE, -10);	    
		
	    for(int i=0; i<linhas; i++) {
	    	//Pegando proximo dia
	    	cal.add(Calendar.DATE, 1);
		    
		    String day = "" +(cal.get(Calendar.DAY_OF_MONTH));
		    if (day.length() == 1)
		    	day = "0"+day;
		    
		    String mesPrint = "" +(cal.get(Calendar.MONTH)+1);
		    if (mesPrint.length() == 1)
		        mesPrint = "0"+mesPrint;
		    
		    String diaPrint = cal.get(Calendar.YEAR) + "/" + mesPrint + "/" + day;
		    String consulta = "select * from grandezas where code = '" + code + "' and data BETWEEN '" + diaPrint+" 00:00:00' and '" + diaPrint +" 23:59:59' order by data";		        	    		    	 						  			  
			try {				    
				//Recuperando ultimo dado do bd
			    ResultSet rs=db.execQuery(consulta);
			        
				while (rs.next()){				
					Timestamp ts = rs.getTimestamp("DATA");
					// Recupera hora do RS para usar na "linha"
					int hora = ts.getHours();
					Medida medida = new Medida();
					medida.setCorrente(rs.getFloat("corrente"));
					medida.setVazao(rs.getFloat("vazao"));
					medida.setVolume(rs.getInt("volume"));				
					medida.setCesp(rs.getFloat("cesp"));
					medida.setNivel(rs.getFloat("nivel"));										
					med[i][hora] = medida;		    
				 }
				 rs.close();				
		    } catch (Exception e) {
			    System.out.println("Erro fazendo consulta diaria e criando medida "+e.getMessage());
		    }
	    } 	
	    db.close();
	    return med;
	}
	
	private void createLines (Database db, int pocoId) throws Exception {
		String sql = "insert into operacionais (idpoco) values ("+ pocoId +")";
		String sql2 = "insert into poco_hidrogeo (poco_no) values ("+ pocoId +")";
		//String sql3 = "insert into poco_hidrogeo (idpoco) values ("+ pocoId +")";
		System.out.println(sql);
		db.insert(sql);
		db.insert(sql2);
		
	}
	
	private int getPocoId(Database db) {
		int gen = 0;
		try {
			ResultSet rs = db.execQuery("select gen_id(POCO_ID, 1) from rdb$database");
			rs.next();
			gen = Integer.parseInt(rs.getString("GEN_ID"));
			rs.close();
		} catch (Exception e) {
			System.out.println("Erro recuperando Id do poco para criar "+e.getMessage());
		}
		return gen;
	}
	
	/**
	 * Método criado para termos um cadastro simplificado de poço
	 * 
	 * @param nome Nome ou número do poço
	 * @param id_sistema Id do Sistema
	 * @param superin Superintendência
	 * @param utm_norte UTM Norte (coordenada usada como identificador do poço ao receber dados)
	 */
	public boolean createPocoSlim (String nome, int id_sistema, String superin, String utm_norte, String utm_leste) {
		boolean result = true;
		db = new Database(urlDb,"SYSDBA","masterkey");
		
		//Recupera proximo Id do generator que será usado
		int generator = getPocoId(db);
		String sql = "insert into poco_gerais (nome, id_sistema, utm_norte, utm_sul, manutencao, id_poco) values ("
				+ "'" + nome + "',"
				+ id_sistema +","
				+ "'" + utm_norte + "',"
				+ "'" + utm_leste + "',"
				+ "0,"
				+ generator + ")";
		
		System.out.println(sql);
		try {
			db.insert(sql);
			//Cria também entrada nas tabelas operacionais, hidrogeologicas, etc.
			createLines(db, generator);
		} catch(Exception e) {
			System.out.println("Erro criando Poco Slim "+e.getMessage());
			result=false;
		}
		db.close();
		return result;				
	}
	
	public int createPoco (String nome, String endereco, String localidade, String municipio, String uf, String cep, String utm_norte, String utm_sul, String latitude, String longitude, String outorga_no, String art, String tecnico, String art_no, String finalidade, String vazao_outorgada, String volume_out, String situacao, String reativado_data, String obs, int id_sistema) {
		System.out.println("Entrei em createPoco. Vou conectar....");
		db = new Database(urlDb,"SYSDBA","masterkey");		
		String query = "insert into poco_gerais values ('" 
				+ nome + "', '" 
				+ endereco + "', '" 
				+ localidade + "', '" 
				+ municipio + "', '" 
				+ uf + "', '" 
				+ cep + "', '" 
				+ utm_norte + "', '" 
				+ utm_sul + "', '" 
				+ latitude + "', '" 
				+ longitude + "', '" 
				+ outorga_no + "', '" 
				+ art + "', '" 
				+ tecnico + "', '" 
				+ art_no + "', '" 
				+ finalidade + "', '" 
				+ vazao_outorgada + "', '" 
				+ volume_out + "', '" 
				+ situacao + "', '" 
				+ reativado_data + "', '" 
				+ obs + "', "
				+ "GEN_ID(POCO_ID, 1)" + ", "
				+ id_sistema + ",0)";
		System.out.println(query);
		try {
			db.insert(query);	
		} catch(Exception e) {
			System.out.println("Erro criando Poco "+e.getMessage());
		}
		db.close();
			
		return 1;
	}

	/** Recupera lista de pocos por sistema
	 * 
	 * @param sistema
	 * @return
	 * @throws SQLException 
	 */
	/*
	public Poco[] getPocos(int id_sistema) {
		ArrayList<Poco> pocosList = new ArrayList<Poco>();
		
		System.out.println("Iniciando conexão com banco de dados");
		db = new Database(urlDb,"SYSDBA","masterkey");

		try {
			ResultSet rs = db.execQuery("select nome, utm_norte from poco_gerais where id_sistema="+id_sistema);
			while (rs.next()){				
				String nome = rs.getString("nome");
				String code = rs.getString("utm_norte");
				System.out.println("Recuperei um poco de nome: "+nome+" e código: "+code);
				pocosList.add(new Poco(nome, code));
			}
		} catch (Exception e) {
			System.out.println("Erro recuperando lista de pocos. "+e.getMessage());
		}
		db.close();
		Poco[] pocosArray = new Poco[pocosList.size()];
		pocosList.toArray(pocosArray);
		return pocosArray;
		
	}
	*/
	
	public Empresa[] getEmpresas() {
		String busca = "select * from empresa";
		ArrayList<Empresa> list = new ArrayList<Empresa>(5);
				
		ResultSet rs = null;
		
    	Database db = new Database();
    	try{
        	rs = db.execQuery(busca);
        	while (rs.next()) {
        		int id = rs.getInt("empresa_id");
        		String empresa = rs.getString("empresa");
        		String contrato = rs.getString("contrato");
        		int num_sistema = rs.getInt("num_sistema"); 
        		String modulos = rs.getString("modulos");
        		int num_pocos = rs.getInt("num_pocos");
        		int num_usuarios = rs.getInt("num_usuarios");
        		String numContrato = rs.getString("numContrato");
        		
        		//Recuperando usuario master
        		String masterUser = getMaster(id);
        		Empresa emp = new Empresa(id, empresa, contrato, num_sistema, modulos, num_pocos, num_usuarios, masterUser, numContrato);

        		list.add(emp);
        	}
        	rs.close();
    	} catch(Exception e) {
    		System.out.println("Erro recuperando lista de empresas. "+e.getMessage());
    		e.printStackTrace();
    	} finally {
    		db.close();    		
    	}
    	Empresa[] empArray = new Empresa[list.size()];
    	return list.toArray(empArray);
	}	
	
	/** Recuperando usuário master */
	private String getMaster(int id_empresa){
		String busca = "select usuario from usuario where id_empresa = " + id_empresa + " and (acesso='diretor' or acesso='Diretor')";
		String masterUser = "NÃO EXISTENTE";
		
    	Database db = new Database();
    	ResultSet rs = null;
		
    	try{
        	rs = db.execQuery(busca);
        	if (rs.next()) {
        		masterUser= rs.getString("usuario");
        	} else {
        		System.out.println("Não existe usuário diretor para a empresa de id = "+id_empresa);
        	}
    		rs.close();
    	} catch(Exception e) {
    		System.out.println("Erro recuperando usuario master da empresa de id = " + id_empresa + " > "+e.getMessage());
    	} finally {

    		db.close();    		
    	}
    	return masterUser;
	}
	
	/**
	 * Recupera lista de sistemas a partir do ID da EMPRESA
	 * @param id_empresa
	 * @return
	 */
	public Sistema[] getSistemas (int id_empresa) {
		ArrayList<Sistema> sistemasList = new ArrayList<Sistema>();
		
		System.out.println("Iniciando conexão com banco de dados");
		db = new Database(urlDb,"SYSDBA","masterkey");

		try {
			ResultSet rs = db.execQuery("select * from sistema where empresa_id='"+id_empresa+"' order by nome");
			while (rs.next()){				
				int sistema_id = rs.getInt("sistema_id");
				String nome = rs.getString("nome");
				System.out.println("Recuperei um Sistema de nome: "+nome+" e id: "+sistema_id);
				sistemasList.add(new Sistema(sistema_id, id_empresa, nome));
			}
		} catch (Exception e) {
			System.out.println("Erro recuperando lista de Sistemas. "+e.getMessage());
		}
		db.close();
		Sistema[] sistArray = new Sistema[sistemasList.size()];
		sistemasList.toArray(sistArray);
		return sistArray;		
		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DataRequester dr = new DataRequester("jdbc:firebirdsql:localhost/3050:C:/juper/old_site/SIGAS.GDB");
		
		List<DadosAnuais> lista = dr.dadosAnuais("451707E", "nivel", "max");
		for(DadosAnuais dado:lista) {
			for(int i=1; i<= 12; i++) {
				System.out.println(dado.ano + "/" + i +": "+dado.dadoMensal[i]);				
			}			
		}
	
		/*
		float medida[][] = dr.jornada("21/12/2015", "451707E");
		for(int i=0; i<=12; i++){
			for(int j=0; j<=12; j++){
				if(medida[i][j] > 0){
					System.out.println(j+"/"+i+"/2015: "+medida[i][j]);
				}
			}
		} */
		
		//DataRequester dr = new DataRequester("jdbc:firebirdsql:localhost/3050:C:/juper/old_site/SIGAS.GDB");
		//StatusPoco[] status = dr.pocosOnline(9);
		//for(int i=0; i<status.length; i++) {
		//	System.out.println("-----  Poço: "+status[i].poco.getName()+ " operacao: "+status[i].isOperating + " Frase: "+status[i].getFrase());			
		//}
		/*
		Medida med = (Medida) dr.onlineData("450814E");
		float fl = med.getVazao();
		System.out.println("Vazao eh: "+fl);
		System.out.println("Volume eh: "+med.getVolume());
		
		Sistema[] list = dr.getSistemas(11);
		for (int i=0; i<list.length; i++)
			System.out.println(list[i].getId_sistema()+" - "+list[i].getNome());

		*/
		
		/*
		Empresa[] emp = dr.getEmpresas();
		
		for(int i=0; i<emp.length; i++) {
			System.out.println("Empresa: " + emp[i].getNome() );
			System.out.println("... Contrato: " + emp[i].getContrato());
			System.out.println("... Usuario master: " + emp[i].getMasterUser());
		}*/
		
	}

}
