package pck.client.chat;

import java.util.ArrayList;

import pck.client.TEngine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.event.dom.client.KeyDownEvent;

public class TFormChat extends Composite {

	private static TFormChatUiBinder uiBinder = GWT
			.create(TFormChatUiBinder.class);
	
	@UiField ListBox lstUsersMessage;
	@UiField ListBox lstUsersConnect;
	@UiField Button btnEnviar;
	@UiField TextArea txtTexto;
	@UiField AbsolutePanel apContenedor;

	private TEngine engine;
	
	//private TMensajeChat mensaje;

	interface TFormChatUiBinder extends UiBinder<Widget, TFormChat> {
	}
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public TFormChat(TEngine engine) {
		initWidget(uiBinder.createAndBindUi(this));
		
		this.engine=engine;
		
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void agregarMensajeLargo(String mensaje,String color){
		lstUsersMessage.addItem(mensaje);
		
		SelectElement selectElement = SelectElement.as(lstUsersMessage.getElement());
		NodeList<OptionElement> options = selectElement.getOptions();
		options.getItem(lstUsersMessage.getItemCount()-1).getStyle().setColor(color);
		
		//Mueve el scrooll bar al final
		lstUsersMessage.setItemSelected(lstUsersMessage.getItemCount()-1, true);
		lstUsersMessage.setItemSelected(lstUsersMessage.getItemCount()-1, false);
	}
	private void moveScrollToFinal(ListBox lst){
		//Mueve el scrooll bar al final
		lst.setItemSelected(lst.getItemCount()-1, true);
		lst.setItemSelected(lst.getItemCount()-1, false);
	}
	public void moveScrollToFinal(){
		moveScrollToFinal(lstUsersConnect);
		moveScrollToFinal(lstUsersMessage);
	}
	private void formatearMensaje(String name,String  mensaje,String color)
	{
		String cad=name+":"+mensaje;
		int tam=cad.length();
		int n=28;
			for(int i=0;i<tam;i++){
				if((i*n)+n>tam){
					agregarMensajeLargo(cad.substring(i*n),color);
					
					break;
				}
				else{
					agregarMensajeLargo(cad.substring(i*n, (i*n)+n),color);
				}
			}		
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void agregarTodosMensajes(ArrayList<TMensajeChat> msn){
		for(TMensajeChat tmp:msn){
			
			formatearMensaje(tmp.getName(),tmp.getMensaje(),"black");
			
		}
		//Mueve el scrooll bar al final
		moveScrollToFinal(lstUsersMessage);
	;
	}	
	public void agregarTodosUsuarios(ArrayList<TMensajeChat> usr){
		for(TMensajeChat tmp:usr){
			lstUsersConnect.addItem(tmp.getName()+":"+tmp.getMensaje());
		}
		moveScrollToFinal(lstUsersConnect);
	}
	
	public void agregarMensaje(String usuario,String mensaje,String color){
		
		formatearMensaje(usuario,mensaje,color);
		
		SelectElement selectElement = SelectElement.as(lstUsersMessage.getElement());
		NodeList<OptionElement> options = selectElement.getOptions();
		options.getItem(lstUsersMessage.getItemCount()-1).getStyle().setColor(color);
		
		moveScrollToFinal(lstUsersMessage);
	}
	public void agregarUsuario(String usr){
		lstUsersConnect.addItem(usr);
		moveScrollToFinal(lstUsersConnect);
	}
	public void agregarUsuario(String usr,String color){
		lstUsersConnect.addItem(usr);
		
		SelectElement selectElement = SelectElement.as(lstUsersConnect.getElement());
		NodeList<OptionElement> options = selectElement.getOptions();
		options.getItem(lstUsersConnect.getItemCount()-1).getStyle().setColor(color);
		moveScrollToFinal(lstUsersConnect);
		
	}
	public void eliminarUsuario(String usr){
		for(int i=0;i<lstUsersConnect.getItemCount();i++){
			if(lstUsersConnect.getItemText(i).equals(usr)){
				lstUsersConnect.removeItem(i);
				break;
			}
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@UiHandler("btnEnviar")
	void onBtnEnviarClick(ClickEvent event) {
		/*
		 * Verificar sincronizacion
		 */
		formatearMensaje(engine.naveTerricola.getName(),txtTexto.getText(),"red");
	
	    engine.onSendMessageChatUser(engine.naveTerricola.getName(), txtTexto.getText());
	   
	    txtTexto.setText("");
	}//end function
	@UiHandler("txtTexto")
	void onTxtTextoKeyDown(KeyDownEvent event) {
		
		if(event.getNativeKeyCode()==KeyCodes.KEY_ENTER){
			btnEnviar.click();	
		}
		
		
	}
}//end class	
