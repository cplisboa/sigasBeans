package sigas;

import java.sql.ResultSet;

public class Hidrogeo {

	 public int idPoco;
	 public String PROPRIETARIO = "";
	 public String BACIA = "";
	 public String FEICAO = "";
	 public String UNIDADE_HIDROG = "";
	 public String FORMACAO_INICIAL1 = "";
	 public String FORMACAO_FINAL1 = "";
	 public String FORMACAO_TIPO1 = "";
	 public String FORMACAO_INICIAL2 = "";
	 public String FORMACAO_FINAL2 = "";
	 public String FORMACAO_TIPO2 = "";
	 public String LITOLOGIA_INICIAL1 = "";
	 public String LITOLOGIA_FINAL1 = "";
	 public String LITOLOGIA_DESCRICAO1 = "";
	 public String LITOLOGIA_INICIAL2 = "";
	 public String LITOLOGIA_FINAL2 = "";
	 public String LITOLOGIA_DESCRICAO2 = "";
	 public String LITOLOGIA_INICIAL3 = "";
	 public String LITOLOGIA_FINAL3 = "";
	 public String LITOLOGIA_DESCRICAO3 = "";
	 public String LITOLOGIA_INICIAL4 = "";
	 public String LITOLOGIA_FINAL4 = "";
	 public String LITOLOGIA_DESCRICAO4 = "";
	 public String LITOLOGIA_INICIAL5 = "";
	 public String LITOLOGIA_FINAL5 = "";
	 public String LITOLOGIA_DESCRICAO5 = "";
	 public String LITOLOGIA_INICIAL6 = "";
	 public String LITOLOGIA_FINAL6 = "";
	 public String LITOLOGIA_DESCRICAO6 = "";
	 public String LITOLOGIA_INICIAL7 = "";
	 public String LITOLOGIA_FINAL7 = "";
	 public String LITOLOGIA_DESCRICAO7 = "";
	 public String LITOLOGIA_INICIAL8 = "";
	 public String LITOLOGIA_FINAL8 = "";
	 public String LITOLOGIA_DESCRICAO8 = "";
	 public float PROJ_NE;
	 public float PROJ_DN;
	 public float VAZAO;
	 public float CESP;
	 public int JORNADA;	 
	 public float TRANSM;
	 public String OBSERVACOES = "";
	
	 public boolean saveHidrogeo (String pocoName) {
		 boolean result = true;
		 Database db = new Database();
		 
		 String sql = "update POCO_HIDROGEO set " + 
			  "BACIA = '" + this.BACIA + "', " +
			  "FEICAO = '" + this.FEICAO + "', " +
			  "UNIDADE_HIDROG = '" + this.UNIDADE_HIDROG + "', " +
			  "FORMACAO_INICIAL1 = '" + this.FORMACAO_INICIAL1 + "', " +
			  "FORMACAO_FINAL1 = '" + this.FORMACAO_FINAL1 + "', " +
			  "FORMACAO_TIPO1 = '" + this.FORMACAO_TIPO1 + "', " +
			  "FORMACAO_INICIAL2 = '" + this.FORMACAO_INICIAL2 + "', " +
			  "FORMACAO_FINAL2 = '" + this.FORMACAO_FINAL2 + "', " +
			  "FORMACAO_TIPO2 = '" + this.FORMACAO_TIPO2 + "', " +
			  "LITOLOGIA_INICIAL1 = '" + this.LITOLOGIA_INICIAL1 + "', " +
			  "LITOLOGIA_FINAL1 = '" + this.LITOLOGIA_FINAL1 + "', " +
			  "LITOLOGIA_DESCRICAO1 = '" + this.LITOLOGIA_DESCRICAO1 + "', " +
			  "LITOLOGIA_INICIAL2 = '" + this.LITOLOGIA_INICIAL2 + "', " +
			  "LITOLOGIA_FINAL2 = '" + this.LITOLOGIA_FINAL2 + "', " +
			  "LITOLOGIA_DESCRICAO2 = '" + this.LITOLOGIA_DESCRICAO2 + "', " +
			  "LITOLOGIA_INICIAL3 = '" + this.LITOLOGIA_INICIAL3 + "', " +
			  "LITOLOGIA_FINAL3 = '" + this.LITOLOGIA_FINAL3 + "', " +
			  "LITOLOGIA_DESCRICAO3 = '" + this.LITOLOGIA_DESCRICAO3 + "', " +
			  "LITOLOGIA_INICIAL4 = '" + this.LITOLOGIA_FINAL4 + "', " +
			  "LITOLOGIA_FINAL4 = '" + this.LITOLOGIA_FINAL4 + "', " +
			  "LITOLOGIA_DESCRICAO4 = '" + this.LITOLOGIA_DESCRICAO4 + "', " +
			  "LITOLOGIA_INICIAL5 = '" + this.LITOLOGIA_FINAL5 + "', " +
			  "LITOLOGIA_FINAL5 = '" + this.LITOLOGIA_FINAL5 + "', " +
			  "LITOLOGIA_DESCRICAO5 = '" + this.LITOLOGIA_DESCRICAO5 + "', " +
			  "LITOLOGIA_INICIAL6 = '" + this.LITOLOGIA_INICIAL6 + "', " +
			  "LITOLOGIA_FINAL6 = '" + this.LITOLOGIA_FINAL6 + "', " +
			  "LITOLOGIA_DESCRICAO6 = '" + this.LITOLOGIA_DESCRICAO6 + "', " +
			  "LITOLOGIA_INICIAL7 = '" + this.LITOLOGIA_INICIAL7 + "', " +
			  "LITOLOGIA_FINAL7 = '" + this.LITOLOGIA_FINAL7 + "', " +
			  "LITOLOGIA_DESCRICAO7 = '" + this.LITOLOGIA_DESCRICAO7 + "', " +
			  "LITOLOGIA_INICIAL8 = '" + this.LITOLOGIA_INICIAL8 + "', " +
			  "LITOLOGIA_FINAL8 = '" + this.LITOLOGIA_FINAL8 + "', " +
			  "LITOLOGIA_DESCRICAO8 = '" + this.LITOLOGIA_DESCRICAO8 + "', " +
			  "PROJ_NE = " + this.PROJ_NE + ", " +
			  "PROJ_DN = " + this.PROJ_DN + ", " +
			  "VAZAO = " + this.VAZAO + ", " +
			  "CESP = " + this.CESP + ", " +
			  "JORNADA = " + this.JORNADA + ", " +
			  "TRANSM = " + this.TRANSM + ", " +
			  "OBSERVACOES = '" + this.OBSERVACOES + "' " +
			  "where POCO_NO = " + Operacionais.getPocoId(db, pocoName);
 			  System.out.println(sql);
			try {
				System.out.println("Executando atualização de Dados Hidrogeológicos");
				db.insert(sql);	
				System.out.println("Dados Hidrogeológicos atualizados");
			} catch(Exception e) {
				System.out.println("Erro atualizando operacionais "+e.getMessage());
			}
			System.out.println("close banco de dados");
			db.close();		
			return result;
		 
	 }
	 
		// Carregar dados gerais para apresentar na pagina
		public Hidrogeo fillHidrogeo(String pocoName) {
			Database db = new Database();
			int idPoco = Operacionais.getPocoId(db, pocoName);
			System.out.println("Recuperando hidrogeo do poço de id: " + idPoco);
			
			String sql = "select * from poco_hidrogeo where poco_no="+ idPoco ;
	       	ResultSet rs = db.execQuery(sql);
	       	try {
		    	if (rs.next()) {
		    		//recuperação aqui
		    		BACIA= rs.getString("BACIA");
		    		FEICAO= rs.getString("FEICAO");
		    		UNIDADE_HIDROG= rs.getString("UNIDADE_HIDROG");
		    		FORMACAO_INICIAL1= rs.getString("FORMACAO_INICIAL1");
		    		FORMACAO_FINAL1= rs.getString("FORMACAO_TIPO1");
		    		FORMACAO_TIPO1= rs.getString("FORMACAO_TIPO1");
		    		FORMACAO_INICIAL2= rs.getString("FORMACAO_INICIAL2");
		    		FORMACAO_FINAL2= rs.getString("FORMACAO_FINAL2");
		    		FORMACAO_TIPO2= rs.getString("FORMACAO_TIPO2");
		    		LITOLOGIA_INICIAL1= rs.getString("LITOLOGIA_INICIAL1");
		    		LITOLOGIA_FINAL1= rs.getString("LITOLOGIA_FINAL1");
		    		LITOLOGIA_DESCRICAO1= rs.getString("LITOLOGIA_DESCRICAO1");
		    		LITOLOGIA_INICIAL2= rs.getString("LITOLOGIA_INICIAL2");
		    		LITOLOGIA_FINAL2= rs.getString("LITOLOGIA_FINAL2");
		    		LITOLOGIA_DESCRICAO2= rs.getString("LITOLOGIA_DESCRICAO2");
		    		LITOLOGIA_INICIAL3= rs.getString("LITOLOGIA_INICIAL3");
		    		LITOLOGIA_FINAL3= rs.getString("LITOLOGIA_FINAL3");
		    		LITOLOGIA_DESCRICAO3= rs.getString("LITOLOGIA_DESCRICAO3");
		    		LITOLOGIA_INICIAL4= rs.getString("LITOLOGIA_INICIAL4");
		    		LITOLOGIA_FINAL4= rs.getString("LITOLOGIA_FINAL4");
		    		LITOLOGIA_DESCRICAO4= rs.getString("LITOLOGIA_DESCRICAO4");
		    		LITOLOGIA_INICIAL5= rs.getString("LITOLOGIA_INICIAL5");
		    		LITOLOGIA_FINAL5= rs.getString("LITOLOGIA_FINAL5");
		    		LITOLOGIA_DESCRICAO5= rs.getString("LITOLOGIA_DESCRICAO5");
		    		LITOLOGIA_INICIAL6= rs.getString("LITOLOGIA_INICIAL6");
		    		LITOLOGIA_FINAL6= rs.getString("LITOLOGIA_FINAL6");
		    		LITOLOGIA_DESCRICAO6= rs.getString("LITOLOGIA_DESCRICAO6");
		    		LITOLOGIA_INICIAL7= rs.getString("LITOLOGIA_INICIAL7");
		    		LITOLOGIA_FINAL7= rs.getString("LITOLOGIA_FINAL7");
		    		LITOLOGIA_DESCRICAO7= rs.getString("LITOLOGIA_DESCRICAO7");
		    		LITOLOGIA_INICIAL8= rs.getString("LITOLOGIA_INICIAL8");
		    		LITOLOGIA_FINAL8= rs.getString("LITOLOGIA_FINAL8");
		    		LITOLOGIA_DESCRICAO8= rs.getString("LITOLOGIA_DESCRICAO8");
		    		PROJ_NE= rs.getFloat("PROJ_NE");
		    		PROJ_DN= rs.getFloat("PROJ_DN");
		    		VAZAO= rs.getFloat("VAZAO");
		    		CESP= rs.getFloat("CESP");
		    		JORNADA= rs.getInt("JORNADA");
		    		TRANSM= rs.getFloat("TRANSM");
		    		OBSERVACOES= rs.getString("OBSERVACOES");
		    	} else {
		    		db.close();
		    		return null;
		    	}
		    	rs.close();
	       	} catch (Exception e) {
				System.out.println("Excessão recuperando Hidrogeo");
			}
	       	db.close();
	       	return this;
		}		 
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Database db = new Database();
		Hidrogeo oper = new Hidrogeo();
		oper.saveHidrogeo("9");
	}

}
