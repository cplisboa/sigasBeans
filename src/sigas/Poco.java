package sigas;

public class Poco {
	String name;
	String code;
	int poco_id;
	
	/** Construtor que recebe os dados fundamentais para se ter um poço */
	public Poco(String name, String code, int poco_id) {
		this.name = name;
		this.code = code;
		this.poco_id = poco_id;
	}
	
	/** Retorna o numero de pocos cadastrados para a empresa idempresa */
	public static int numPocos(int idEmpresa) {
		int contPoco = 0;
		DataRequester dr = new DataRequester("jdbc:firebirdsql:localhost/3050:C:/juper/old_site/SIGAS.GDB");
		Sistema[] sysList = dr.getSistemas(idEmpresa);
		Database db = new Database();
		for (int i=0; i<sysList.length; i++) {
			sysList[i].fillPocos(db);		
			contPoco += sysList[i].getPocos().length;
		}
		return contPoco;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public static void main (String[] args){
		System.out.println("Numpocos empresa 1 = "+Poco.numPocos(1));
	}

}
