package pck.client.chat;

public class TMensajeChat {

	private String name;
	private String mensaje;
	
	public TMensajeChat(String name){
	
		this.name=name;
	}
	public TMensajeChat(String name,String mensaje){
		
		this.name=name;
		this.mensaje=mensaje;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
