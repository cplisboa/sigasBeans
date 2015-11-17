package sigas;

import java.sql.ResultSet;

public class Gerais {

	public String nome;
	public String idSistema; 
	public String endereco; 
	public String localidade; 
	public String municipio; 
	public String uf; 
	public String cep; 
	public String utmNorte; 
	public String utmSul; 
	public String latitude; 
	public String longitude; 
	public String outorga_no; 
	public String art; 
	public String tecnico; 
	public String artNo; 
	public String finalidade; 
	public String vazaoOutorgada; 
	public String volumeOut; 
	public String situacao; 
	public String reativadoData; 
	public String obs;
		
	public Gerais() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	// Carregar dados gerais para apresentar na pagina
	public Gerais fillGerais(String nomePoco) {
		System.out.println("Recuperando dados do poço de nome: " + nomePoco);
		Database db = new Database();
		
		String sql = "select * from poco_gerais where nome='"+ nomePoco + "'";
       	ResultSet rs = db.execQuery(sql);
       	try {
	    	if (rs.next()) {
	    		localidade = rs.getString("localidade");
	    		endereco = rs.getString("endereco");
	    		municipio = rs.getString("municipio");
	    		uf=rs.getString("uf");
	    		cep=rs.getString("cep");
	    		utmNorte=rs.getString("utm_norte");
	    		utmSul=rs.getString("utm_sul");
	    		latitude=rs.getString("latitude");
	    		longitude=rs.getString("longitude");
	    		outorga_no=rs.getString("outorga_no");
	    		art=rs.getString("art");
	    		tecnico=rs.getString("tecnico");
	    		artNo=rs.getString("art_no");
	    		finalidade=rs.getString("finalidade");
	    		vazaoOutorgada=rs.getString("vazao_outorgada");
	    		volumeOut=rs.getString("volume_outorgado");
	    		situacao=rs.getString("situacao");
	    		reativadoData=rs.getString("reativado_data");
	    		obs=rs.getString("observacoes");
	    	}
	    	rs.close();
       	} catch (Exception e) {
			System.out.println("Excessão recuperando Gerais");
		}
       	db.close();
       	return this;
	}
	
	public void atualiza() {
		System.out.println("Atualização de poço....");
		Database db = new Database();
		
		String sql = "update poco_gerais set endereco = '" + endereco + "', " +
				"localidade='" + localidade +"', " +
				"municipio='" + municipio +"', " +
				"uf='" + uf +"', " +
				"cep='" + cep +"', " +
				"utm_norte='" + utmNorte +"', " +
				"utm_sul='" + utmSul +"', " +
				"latitude='" + latitude +"', " +
				"longitude='" + longitude +"', " +
				"outorga_no='" + outorga_no +"', " +
				"art='" + art +"', " +
				"tecnico='" + tecnico +"', " +
				"art_no='" + artNo +"', " +
				"finalidade='" + finalidade +"', " +
				"vazao_outorgada='" + vazaoOutorgada +"', " +
				"volume_outorgado='" + volumeOut +"', " +
				"situacao='" + situacao +"', " +
				"reativado_data='" + reativadoData +"', " +
				"observacoes='" + obs +"' " +
				"where nome='" + nome + "'";		
		System.out.println(sql);
		
		try {
			System.out.println("Executando atualização de poço");
			db.insert(sql);	
			System.out.println("poço atualizado");
		} catch(Exception e) {
			System.out.println("Erro atualizando Poco "+e.getMessage());
		}
		System.out.println("close banco de dados");
		db.close();			
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Gerais gera = new Gerais();
		gera.fillGerais("Superior");
		System.out.println(""+gera.municipio);
		System.out.println(""+gera.latitude);
		System.out.println(""+gera.cep);
		System.out.println(""+gera.endereco);
		System.out.println(""+gera.finalidade);
		

	}

}
