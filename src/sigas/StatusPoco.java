package sigas;

public class StatusPoco {

	public Poco poco;
	public Medida med;
	public boolean isOperating = false;
	public boolean hasManutencao = false;
	public boolean unknownState = false;
	long difDias = 0;

	public StatusPoco(Poco poco, Medida med) {
		super();
		this.poco = poco;
		this.med = med;
	}
	
	/** Calcula mensagem de warning que deve ser mostrada para determinado poço */
	public String getFrase() {
		String frase="";
		if(med==null) {
			frase = "NENHUMA MEDIDA ENCONTRADA PARA ESSE PO&Ccedil;O";
		} else {
			// Existem medidas

			//Verifica se temos problemas no tempo sem leitura do poço
			java.sql.Timestamp sqlTs = med.getTs();
			java.util.Date ts = (java.util.Date) sqlTs;
		
			java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());		
			long diff = now.getTime() - ts.getTime();     
    
			long difHoras = diff/1000/60/60;
			long difDias = diff/1000/60/60/24;
				
			if(poco.manutencao == 1) {
				frase = "PO&Ccedil;O EM MANUTENPO&Ccedil;&Atilde;O. DADOS APRESENTADOS APENAS PARA TESTES";
				this.hasManutencao = true;
				unknownState = true;
			} else {
				if(difDias >= 1) {
					frase = "Sem leitura a "+difDias+" dia(s)";
					unknownState = true;
				} else if (difHoras  > 8) {
					frase = "Sem leitura a "+difHoras+" horas";			
					unknownState = true;
				} else {
					if ((med.getNivel()==0) && (med.getCorrente()==0) && (med.getVazao()==0)) {
						frase = "Poço em Manutenção";
					} else if(med.getCorrente() > 0) {
						frase = "Em opera&ccedil;&atilde;o";
					} else {
						frase = "Em repouso";
					}								
				}
			}
		}
		return frase;				
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
