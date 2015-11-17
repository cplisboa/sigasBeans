package sigas;

import java.sql.ResultSet;

public class Operacionais {

	public int idPoco;
	public String proprietario = "";
	public String marca = "";
	public String modelo = "";
	public String val_bomb = "";
	public String diametro = "";
	public String cabo = "";
	public String tensao = "";
	public String data_inst = "";
	public String prof_inst = "";
	public String tipo = "";
	public String ed_diametro = "";
	public String ed_data_inst = "";
	public String ed_prof = "";
	public String contatora = "";
	public String fuziveis = "";
	public String rele = "";
	public String rele_termico = "";
	public String amperimetro = "";
	public String pararaios = "";
	public String horimetro = "";
	public String temporizador = "";
	public String tipo_tubu = "";
	public String tamponado = "";
	public String diametro_tubu = "";
	public String prof_tubu = "";
	public String lacre = "";
	public String cruzeta = "";
	public String hidro = "";
	public String valvula = "";
	public String amort = "";
	public String filtro = "";
	public String clorador = "";
	public String cav_sensor = "";
	public String fluoretador = "";
	public String tipo_rede = "";
	public String ext_rede = "";
	public String diam_rede = "";
	public String volume = "";
	public String altura = "";
	public String cota = "";
	public String chave = "";
	public String sensor = "";
	public String expurgo = "";
	public String marca_hidro = "";
	public String modelo_hidro = "";
	public String saida = "";	
	
	public Operacionais() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static int getPocoId(Database db, String pocoName) {
		int id=-1;
		System.out.println("Pegando ID do poconame: "+pocoName);
		try {	
			ResultSet rs = db.execQuery("select id_poco from poco_gerais where nome = '"+ pocoName +"'");
			if(rs.next()) {
				id = rs.getInt("id_poco");
			}
		} catch (Exception e) {
			System.out.println("Erro consultando poco_id. "+pocoName+" - "+e.getMessage());
			
		}
		return id;
	}
	
	public boolean saveOperacional(String pocoName) {
		boolean result = true;
		Database db = new Database();
		
		String sql = "update operacionais set " + 				 
			"proprietario= '"+this.proprietario + "', " + 
			"marca= '"+this.marca + "', " + 
			"modelo= '"+this.modelo + "', " + 
			"val_bomb= '"+this.val_bomb + "', " + 
			"diametro= '"+this.diametro + "', " + 
			"cabo= '"+this.cabo + "', " + 
			"tensao= '"+this.tensao + "', " + 
			"data_inst= '"+this.data_inst + "', " + 
			"prof_inst= '"+this.prof_inst + "', " + 
			"tipo= '"+this.tipo + "', " + 
			"ed_diametro= '"+this.ed_diametro + "', " + 
			"ed_data_inst= '"+this.ed_data_inst + "', " + 
			"ed_prof= '"+this.ed_prof + "', " + 
			"contatora= '"+this.contatora + "', " + 
			"fuziveis= '"+this.fuziveis + "', " + 
			"rele= '"+this.rele + "', " + 
			"rele_termico= '"+this.rele_termico + "', " + 
			"amperimetro= '"+this.amperimetro + "', " + 
			"pararaios= '"+this.pararaios + "', " + 
			"horimetro= '"+this.horimetro + "', " + 
			"temporizador= '"+this.temporizador + "', " + 
			"tipo_tubu= '"+this.tipo_tubu + "', " + 
			"tamponado= '"+this.tamponado + "', " + 
			"diametro_tubu= '"+this.diametro_tubu + "', " + 
			"prof_tubu= '"+this.prof_tubu + "', " + 
			"lacre= '"+this.lacre + "', " + 
			"cruzeta= '"+this.cruzeta + "', " + 
			"hidro= '"+this.hidro + "', " + 
			"valvula= '"+this.valvula + "', " + 
			"amort= '"+this.amort + "', " + 
			"filtro= '"+this.filtro + "', " + 
			"clorador= '"+this.clorador + "', " + 
			"cav_sensor= '"+this.cav_sensor + "', " + 
			"fluoretador= '"+this.fluoretador + "', " + 
			"tipo_rede= '"+this.tipo_rede + "', " + 
			"ext_rede= '"+this.ext_rede + "', " + 
			"diam_rede= '"+this.diam_rede + "', " + 
			"volume= '"+this.volume + "', " + 
			"altura= '"+this.altura + "', " + 
			"cota= '"+this.cota + "', " + 
			"chave= '"+this.chave + "', " + 
			"sensor= '"+this.sensor + "', " + 
			"expurgo= '"+this.expurgo + "', " + 
			"marca_hidro= '"+this.marca + "', " + 
			"modelo_hidro= '"+this.modelo_hidro + "', " + 
			"saida= '"+this.saida + "' " + 	
			"where idPoco = " + getPocoId(db, pocoName);
			System.out.println(sql);
		try {
			System.out.println("Executando atualização de Dados Operacionais");
			db.insert(sql);	
			System.out.println("Dados operacionais do poço atualizado");
		} catch(Exception e) {
			System.out.println("Erro atualizando operacionais "+e.getMessage());
		}
		System.out.println("close banco de dados");
		db.close();		
		return result;
	}

	
	// Carregar dados gerais para apresentar na pagina
	public Operacionais fillOperacionais(String pocoName) {
		Database db = new Database();
		int idPoco = getPocoId(db, pocoName);
		System.out.println("Recuperando operacionais do poço de id: " + idPoco);
		
		String sql = "select * from operacionais where idPoco="+ idPoco ;
       	ResultSet rs = db.execQuery(sql);
       	try {
	    	if (rs.next()) {
	    		proprietario = rs.getString("proprietario");
	    		marca = rs.getString("marca");
	    		modelo = rs.getString("modelo");
	    		val_bomb = rs.getString("val_bomb");
	    		diametro = rs.getString("diametro");
	    		cabo = rs.getString("cabo");
	    		tensao = rs.getString("tensao");
	    		data_inst = rs.getString("data_inst");
	    		prof_inst = rs.getString("prof_inst");
	    		tipo = rs.getString("tipo");
	    		ed_diametro = rs.getString("ed_diametro");
	    		ed_data_inst = rs.getString("ed_data_inst");
	    		ed_prof = rs.getString("ed_prof");
	    		contatora = rs.getString("contatora");
	    		fuziveis = rs.getString("fuziveis");
	    		rele = rs.getString("rele");
	    		rele_termico = rs.getString("rele_termico");
	    		amperimetro = rs.getString("amperimetro");
	    		pararaios = rs.getString("pararaios");
	    		horimetro = rs.getString("horimetro");
	    		temporizador = rs.getString("temporizador");
	    		tipo_tubu = rs.getString("tipo_tubu");
	    		tamponado = rs.getString("tamponado");
	    		diametro_tubu = rs.getString("diametro_tubu");
	    		prof_tubu = rs.getString("prof_tubu");
	    		lacre = rs.getString("lacre");
	    		cruzeta = rs.getString("cruzeta");
	    		hidro = rs.getString("hidro");
	    		valvula = rs.getString("valvula");
	    		amort = rs.getString("amort");
	    		filtro = rs.getString("filtro");
	    		clorador = rs.getString("clorador");
	    		cav_sensor = rs.getString("cav_sensor");
	    		fluoretador = rs.getString("fluoretador");
	    		tipo_rede = rs.getString("tipo_rede");
	    		ext_rede = rs.getString("ext_rede");
	    		diam_rede = rs.getString("diam_rede");
	    		volume = rs.getString("volume");
	    		altura = rs.getString("altura");
	    		cota = rs.getString("cota");
	    		chave = rs.getString("chave");
	    		sensor = rs.getString("sensor");
	    		expurgo = rs.getString("expurgo");
	    		marca_hidro = rs.getString("marca_hidro");
	    		modelo_hidro = rs.getString("modelo_hidro");
	    		saida = rs.getString("saida");
	    	} else {
	    		db.close();
	    		return null;
	    	}
	    	rs.close();
       	} catch (Exception e) {
			System.out.println("Excessão recuperando Gerais");
		}
       	db.close();
       	return this;
	}	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Database db = new Database();
		Operacionais oper = new Operacionais();
		oper.saveOperacional("9");
		//System.out.println(oper.getPocoId(db, "6"));
		
		/*
		oper.saveOperacional(90);
		Operacionais teste = oper.fillOperacionais(90);
		if(teste==null) {
			System.out.println("Dados não cadastrados");
		}*/
	}
}
