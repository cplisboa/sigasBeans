package sigas;

import java.sql.Timestamp;	

public class Medida {
	private float nivel;	
	private float corrente;
	private float vazao;
	private float cesp;
	private int volume;
	private Timestamp ts;
	
	public Medida(){		
	}
			
	public Medida(float nivel, float corrente, float vazao, int volume, float cesp, Timestamp ts) {
		this.nivel = nivel;
		this.corrente = corrente;
		this.vazao = vazao;
		this.volume = volume;
		this.ts = ts;
		this.cesp = cesp;		
	}
	
	public float getNivel() {
		return nivel;
	}
	public void setNivel(float nivel) {
		this.nivel = nivel;
	}
	public float getCorrente() {
		return corrente;
	}
	public void setCorrente(float corrente) {
		this.corrente = corrente;
	}
	public float getVazao() {
		return vazao;
	}
	public void setVazao(float vazao) {
		this.vazao = vazao;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}

	public Timestamp getTs() {
		return ts;
	}

	public void setTs(Timestamp ts) {
		this.ts = ts;
	}

	public float getCesp() {
		return cesp;
	}

	public void setCesp(float cesp) {
		this.cesp = cesp;
	}
	
	

}

