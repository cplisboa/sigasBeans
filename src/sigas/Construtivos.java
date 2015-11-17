package sigas;

import java.sql.ResultSet;

public class Construtivos {

	public int idPoco;

	public String construtor = "";
	public String data_constr = "";
	public String artno = "";
	public String resp = "";
	public String licensa = "";
	public String data_licensa = "";
	public String inicio = "";
	public String profund = "";
	public String diametro = "";
	public int[][] perf = new int [3][2];
	public int[][] entrada = new int [3][2];
	public int[][] nivel = new int [3][2];
	public String[] rev_tipo = new String [3];
	public String[] rev_diam = new String [3];
	public int[][] rev_prof = new int [3][2];

	public String[] filtro_tipo = new String [3];
	public String[] filtro_diam = new String [3];
	public String[][] filtro_prof = new String [3][2];
	
	public String seccao = "";
	public String abertura = "";
	public String observacoes = "";
	
	 public boolean saveConstru (String pocoName) {
		 boolean result = true;
		 Database db = new Database();
		 
		 String sql = "update CONSTRUTIVOS set " + 
			  "CONSTRUTOR = '" + this.construtor + "', " +
			  "DATA_CONSTR = '" + this.data_constr + "', " +
			  "ARTNO = '" + this.artno + "', " +
			  "RESP = '" + this.resp + "', " +
			  "LICENSA = '" + this.licensa + "', " +
			  "DATA_LICENSA = '" + this.data_licensa + "', " +
			  "INICIO = '" + this.inicio + "', " +
			  "PROFUND = '" + this.profund + "', " +
			  "DIAMETRO = '" + this.diametro + "', " +
			  "perf11 = " + this.perf[0][0] + ", " +
			  "perf12 = " + this.perf[0][1] + ", " +
			  "perf21 = " + this.perf[1][0] + ", " +
			  "perf22 = " + this.perf[1][1] + ", " +
			  "perf31 = " + this.perf[2][0] + ", " +
			  "perf32 = " + this.perf[2][1] + ", " +
			  
			  "entrada11 = " + this.entrada[0][0] + ", " +
			  "entrada12 = " + this.entrada[0][1] + ", " +
			  "entrada21 = " + this.entrada[1][0] + ", " +
			  "entrada22 = " + this.entrada[1][1] + ", " +
			  "entrada31 = " + this.entrada[2][0] + ", " +
			  "entrada32 = " + this.entrada[2][1] + ", " +
			  
			  "nivel11 = " + this.nivel[0][0] + ", " +
			  "nivel12 = " + this.nivel[0][1] + ", " +
			  "nivel21 = " + this.nivel[1][0] + ", " +
			  "nivel22 = " + this.nivel[1][1] + ", " +
			  "nivel31 = " + this.nivel[2][0] + ", " +
			  "nivel32 = " + this.nivel[2][1] + ", " +
			  
			  "rev_tipo1 = '" + this.rev_tipo[0] + "', " +
			  "rev_tipo2 = '" + this.rev_tipo[1] + "', " +
			  "rev_tipo3 = '" + this.rev_tipo[2] + "', " +
			  
			  "rev_diam1 = '" + this.rev_diam[0] + "', " +
			  "rev_diam2 = '" + this.rev_diam[1] + "', " +
			  "rev_diam3 = '" + this.rev_diam[2] + "', " +
			  
			  "rev_prof11 = " + this.rev_prof[0][0] + ", " +
			  "rev_prof12 = " + this.rev_prof[0][1] + ", " +
			  "rev_prof21 = " + this.rev_prof[1][0] + ", " +
			  "rev_prof22 = " + this.rev_prof[1][1] + ", " +
			  "rev_prof31 = " + this.rev_prof[2][0] + ", " +
			  "rev_prof32 = " + this.rev_prof[2][1] +
			  
			  " where POCO_NO = " + Operacionais.getPocoId(db, pocoName);
 			  System.out.println(sql);
			try {
				System.out.println("Executando atualização de Dados Construtivos");
				db.insert(sql);	
				System.out.println("Dados Construtivos atualizados");
			} catch(Exception e) {
				System.out.println("Erro atualizando construtivos "+e.getMessage());
			}
			db.close();		
			return result;		 
	 }	

	// Carregar dados gerais para apresentar na pagina
	public Construtivos fillConstru(String pocoName) {
		Database db = new Database();
		int idPoco = Operacionais.getPocoId(db, pocoName);
		System.out.println("Recuperando construtivos do poço de id: " + idPoco);
		
		String sql = "select * from construtivos where poco_no="+ idPoco ;
       	ResultSet rs = db.execQuery(sql);
       	try {
	    	if (rs.next()) {
	    		//recuperação aqui
	    		construtor = rs.getString("construtor");
	    		data_constr = rs.getString("data_constr");
	    		artno = rs.getString("artno");
	    		resp = rs.getString("resp");
	    		licensa = rs.getString("FORMACAO_TIPO1");
	    		data_licensa = rs.getString("FORMACAO_TIPO1");
	    		inicio = rs.getString("FORMACAO_INICIAL2");
	    		profund = rs.getString("FORMACAO_FINAL2");
	    		diametro = rs.getString("FORMACAO_TIPO2");
	    		perf[0][0] = rs.getInt("perf11");
	    		perf[0][1] = rs.getInt("perf12");
	    		perf[1][0] = rs.getInt("perf21");
	    		perf[1][1] = rs.getInt("perf22");
	    		perf[2][0] = rs.getInt("perf31");
	    		perf[2][1] = rs.getInt("perf32");
	    		entrada[0][0] = rs.getInt("entrada11");
	    		entrada[0][1] = rs.getInt("entrada12");
	    		entrada[1][0] = rs.getInt("entrada21");
	    		entrada[1][1] = rs.getInt("entrada22");
	    		entrada[2][0] = rs.getInt("entrada31");
	    		entrada[2][1] = rs.getInt("entrada32");
	    		nivel[0][0] = rs.getInt("nivel11");
	    		nivel[0][1] = rs.getInt("nivel12");
	    		nivel[1][0] = rs.getInt("nivel21");
	    		nivel[1][1] = rs.getInt("nivel22");
	    		nivel[2][0] = rs.getInt("nivel31");
	    		nivel[2][1] = rs.getInt("nivel32");
	    	
	    		rev_tipo[0] = rs.getString("rev_tipo1");
	    		rev_tipo[1] = rs.getString("rev_tipo2");
	    		rev_tipo[2] = rs.getString("rev_tipo3");
	    		
	    		rev_diam[0] = rs.getString("rev_diam1");
	    		rev_diam[1] = rs.getString("rev_diam2");
	    		rev_diam[2] = rs.getString("rev_diam3");
	    		
	    		rev_prof[0][0] = rs.getInt("rev_prof11");
	    		rev_prof[0][1] = rs.getInt("rev_prof12");
	    		rev_prof[1][0] = rs.getInt("rev_prof21");
	    		rev_prof[1][1] = rs.getInt("rev_prof22");
	    		rev_prof[2][0] = rs.getInt("rev_prof31");
	    		rev_prof[2][1] = rs.getInt("rev_prof32");
	    		
	    		
	    		
	    		System.out.println(construtor);
	    		System.out.println(data_constr);
	    		System.out.println(artno);
	    		System.out.println(resp);
	    		System.out.println(licensa);
	    		System.out.println(data_licensa);
	    		System.out.println(inicio);
	    		System.out.println(profund);
	    		System.out.println(diametro);
	    		System.out.println(perf[0][0]);
	    		System.out.println(perf[0][1]);
	    		System.out.println(perf[1][0]);
	    		System.out.println(perf[1][1]);
	    		System.out.println(perf[2][0]);
	    		System.out.println(perf[2][1]);
	    		System.out.println(entrada[0][0]);
	    		System.out.println(entrada[0][1]);
	    		System.out.println(entrada[1][0]);
	    		System.out.println(entrada[1][1]);
	    		System.out.println(entrada[2][0]);
	    		System.out.println(entrada[2][1]);
	    		System.out.println(nivel[0][0]);
	    		System.out.println(nivel[0][1]);
	    		System.out.println(nivel[1][0]);
	    		System.out.println(nivel[1][1]);
	    		System.out.println(nivel[2][0]);
	    		System.out.println(nivel[2][1]);
	    	
	    		System.out.println(rev_tipo[0]);
	    		System.out.println(rev_tipo[1]);
	    		System.out.println(rev_tipo[2]);
	    		
	    		System.out.println(rev_diam[0]);
	    		System.out.println(rev_diam[1]);
	    		System.out.println(rev_diam[2]);
	    		
	    		System.out.println(rev_prof[0][0]);
	    		System.out.println(rev_prof[0][1]);
	    		System.out.println(rev_prof[1][0]);
	    		System.out.println(rev_prof[1][1]);
	    		System.out.println(rev_prof[2][0]);
	    		System.out.println(rev_prof[2][1]);	    		
	    		
	    		
	    		
	    	} else {
	    		System.out.println("Não existem dados para o poço "+idPoco);
	    		db.close();
	    	}
	    	rs.close();
       	} catch (Exception e) {
			System.out.println("Excessão recuperando Construtivos");
		}
       	db.close();	
       	return this;
	}		 	 
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
