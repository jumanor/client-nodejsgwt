package pck.client;

import java.util.ArrayList;

import pck.client.chat.TFormChat;
import pck.client.chat.TMessage;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FocusPanel;

public class TEngine {

	public TNave naveTerricola;
	private ArrayList<TNave> naves;
	
	private AbsolutePanel contenedorCanvas;
	
	private String path;

	private TFormChat chat;
	
	public TEngine(){
		
		naveTerricola=new TNave();
		naveTerricola.setColor("red");
		naves=new ArrayList<TNave>();
		contenedorCanvas=new AbsolutePanel();
	
		path="http://prueba-jquerymobile.rhcloud.com/";
		
		//path="http://127.0.0.1:9091/";
		
		GWT.log("RUTA NODE: "+ path);
		
		ScriptInjector.fromUrl(path+"socket.io/socket.io.js").setCallback(new Callback<Void, Exception>() {
			
			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				loadNodeJS(path);
			}
			@Override
			public void onFailure(Exception reason) {
				// TODO Auto-generated method stub
				Window.alert("Servicio Node.js en: \n "+path+"\n CAIDO !!!!");
			}
		}).inject(); 
		
	}
	
	private native void loadNodeJS(String path) /*-{
		 //Utilzamos <websocket>,si la conexion falla entonces utiliza <xhr-polling>
		 //la conexion  <xhr-polling> no controla el evento "disconnet",la conexion es mantenida unos 30 segundos
		 //aparentemente es un error de OPENSHIFT!!!
		 
		 $wnd.socket = io.connect("ws://prueba-jquerymobile.rhcloud.com:8000");
		 //$wnd.socket = io.connect("ws://127.0.0.1:9091");
		 
		 var tmp=this;
		 
		 $doc.getElementById("msn").innerHTML="<h1>CONECTANDO...<h1>";
		 
		 $wnd.socket.on('connect', function () {
		
			 $wnd.socket.on('onChangeAllPositionUsers', function (data) {
			 	 var data=JSON.parse(data);
				 tmp.@pck.client.TEngine::onChangeAllPositionUsers(Lcom/google/gwt/core/client/JsArray;)(data);
				 
			  });
			 $wnd.socket.on('onBeginPositionUser', function (data,fn) {
			 	
			    var data=JSON.parse(data);
			    tmp.@pck.client.TEngine::onBeginPositionUser(Lpck/client/TUser;)(data);
			    fn();//callback
			  });
			 $wnd.socket.on('onRemoveUser', function (data) {
			    var user=JSON.parse(data);
				 tmp.@pck.client.TEngine::onRemoveUser(Lpck/client/TUser;)(user);
			  });
		 
		    ////////////////////////////////////////////CHAT//////////////////////////////////////////
		     
		  	$wnd.socket.on('onCatchMesageChatUsers', function (data) {
			     var user=JSON.parse(data);
				 tmp.@pck.client.TEngine::onCatchMesageChatUsers(Lpck/client/chat/TMessage;)(user);
			  });
		 	$wnd.socket.on('onBeginChatSelf', function (data) {
			     var obj=JSON.parse(data);
				 tmp.@pck.client.TEngine::onBeginChatSelf(Lpck/client/chat/TMessage;Lcom/google/gwt/core/client/JsArray;Lcom/google/gwt/core/client/JsArray;)(obj.user,obj.users,obj.buffer);                        
			  });
			$wnd.socket.on('onBeginChatOther', function (data) {
			     var obj=JSON.parse(data);
				 tmp.@pck.client.TEngine::onBeginChatOther(Lpck/client/chat/TMessage;)(obj);                        
			  });
			  $wnd.socket.on('onRemoveChatUser', function (data) {
			    var data=JSON.parse(data);
				 tmp.@pck.client.TEngine::onRemoveChatUser(Lpck/client/chat/TMessage;)(data)
			  });
		    $doc.getElementById("msn").innerHTML="";
		 }); 
		   
	}-*/;
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////BEGIN CHAT//////////////////////////////////////////////////////////////////////
	/**
	 * Es usuario se desconecto
	 */
	private void onRemoveChatUser(TMessage message){
	
		chat.eliminarUsuario(message.getName());
		chat.agregarMensaje(message.getName(), message.getMessage(),"black");
	}
	/**MENSAJE BROADCAST
	 * Condiciones iniciales del CHAT para OTROS
	 * @param mensaje
	 */
	private void onBeginChatOther(TMessage message){
		chat.agregarUsuario(message.getName());
		chat.agregarMensaje(message.getName(),message.getMessage(),"black");	
	}
	/**
	 * Condiciones iniciales del CHAT para INICIADOR
	 * @param mensaje
	 */
	private void onBeginChatSelf(TMessage usuario,JsArray<TMessage> usuarios,JsArray<TMessage> buffer){
		//agregamos mensajes a la lista de Mensajes
		for(int i=0;i<buffer.length();i++){
			chat.agregarMensaje(buffer.get(i).getName(), buffer.get(i).getMessage(),"black");
	    }
		//agregamos usuarios a la lista de Conectados
		for(int i=0;i<usuarios.length();i++){
			if(usuarios.get(i).getName().equals(usuario.getName()))continue;
			chat.agregarUsuario(usuarios.get(i).getName());
		}
		//el ultimo en ser agregado a la lista de Conectado
		chat.agregarUsuario(usuario.getName(),"red");
	}
	/**
	 * Mensaje enviado del Cliente al Servidor
	 * @param mensaje
	 */
	public native void onSendMessageChatUser(String name,String message) /*-{
	   var msn={};
	   msn.message=message;
	   msn.name=name;
	   $wnd.socket.emit("onSendMessageChatUser",JSON.stringify(msn));
	}-*/;
	/**
	 * MENSAJE BROADCAST
	 * Menjases enviados del Servidor al Cliente broascast
	 * @param mensajes
	 */
	private void onCatchMesageChatUsers(TMessage mensajes){
	    
		chat.agregarMensaje(mensajes.getName(), mensajes.getMessage(),"black");
	}
	//////////////////////////////////////////////////////END CHAT///////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void onRemoveUser(TUser user){
		
		for(int i=1;i<naves.size();i++){	
			
			if(naves.get(i).getName().equals(user.getName())){
				
				naves.get(i).BorrarNave();
				
				contenedorCanvas.remove(naves.get(i).getCanvas());
				naves.remove(i);
				
			}
		}
	}
	public void onChangeAllPositionUsers(JsArray<TUser> users){
		
		if(naves.size() < users.length()){
			GWT.log("menor");
			GWT.log("size users: "+users.length());
			
			for(int i=0;i<users.length();i++){
			
				int temp=0;
				GWT.log("size naves: "+naves.size());
				for(int j=0;j<naves.size();j++){
					
					if(!naves.get(j).getName().equals(users.get(i).getName())){	
						GWT.log("index != naves"+i+"comparacion naves:"+ naves.get(j).getName());
						GWT.log("index != users"+j+"comparacion users:"+ users.get(i).getName());
						
						temp++;
					}
					else{
						GWT.log("index = naves"+i+"comparacion naves:"+ naves.get(j).getName());
						GWT.log("index = users"+j+"comparacion users:"+ users.get(i).getName());
					}
				}
				GWT.log("temp:"+temp);
				if(temp==naves.size()){
					
					TUser tmp=users.get(i);
					TNave nave=new TNave();
					nave.setX(tmp.getX());
					nave.setY(tmp.getY());
					nave.setName(tmp.getName());
					naves.add(nave);
					contenedorCanvas.insert(nave.getCanvas(), 0, 0, contenedorCanvas.getWidgetCount()-1);
					
					nave.DibujarNave();
					
					
					GWT.log("crear:"+tmp.getName());
				}

			}//end for
			
		}//end if
		else if(naves.size() == users.length()){
			
			for(int i=1;i<naves.size();i++){	
			
						for(int j=0;j<users.length();j++){
							if(naves.get(i).getName().equals(users.get(j).getName())){
															
									naves.get(i).BorrarNave();
									naves.get(i).setX(users.get(j).getX());
									naves.get(i).setY(users.get(j).getY());
									naves.get(i).DibujarNave();
								
							}
						}					
						
			}
			//naveTerricola == users.get(0)
			//naves.get(0).getCanvas().setFocus(true);
		}//end if
		
		
	}
	//Este metodo se ejecuta una sola vez
	private void onBeginPositionUser(TUser user){
		naveTerricola.BorrarNave();
		naveTerricola.setX(user.getX());
		naveTerricola.setY(user.getY());
		naveTerricola.setName(user.getName());
		naveTerricola.DibujarNave();
		naves.add(naveTerricola);
	}
	public native void changePositionUser(int x,int y,String name) /*-{
	   var user={};
	   user.x=x;
	   user.y=y;
	   user.name=name;
	   $wnd.socket.emit("onChangePositionUser",JSON.stringify(user));
	   
	}-*/;
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void ejecutar(){
		FocusPanel teclas=new  FocusPanel();
		
		
		teclas.setSize("600px", "200px");
		
		contenedorCanvas.setSize("600px","200px");
		
	    AbsolutePanel draw=new AbsolutePanel();
	    
	    draw.getElement().getStyle().setBorderWidth(2, Unit.PX);
	    draw.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
	    draw.getElement().getStyle().setColor("black");
	    
	    draw.setSize("600px", "200px");
	    
	    
	    draw.add(contenedorCanvas, 0, 0);
	    draw.add(teclas, 0, 0);
	    
	    RootPanel.get("canvas").add(draw);
		
	    teclas.setFocus(true);
		contenedorCanvas.add(naveTerricola.getCanvas(), 0, 0);
		
		//naveTerricola.DibujarNave();
	
		teclas.addKeyDownHandler(new KeyDownHandler() {
			
			@Override
			public void onKeyDown(KeyDownEvent event) {
				// TODO Auto-generated method stub
				
				if(event.getNativeKeyCode()==KeyCodes.KEY_RIGHT){
						naveTerricola.DibujarNaveDerecha();
						changePositionUser(naveTerricola.getX(),naveTerricola.getY(),naveTerricola.getName());
				}//end if
				if(event.getNativeKeyCode()==KeyCodes.KEY_LEFT){
					
					  naveTerricola.DibujarNaveIzquierda();
					  changePositionUser(naveTerricola.getX(),naveTerricola.getY(),naveTerricola.getName());
				}//end if
				if(event.getNativeKeyCode()==KeyCodes.KEY_UP){
					naveTerricola.DibujarNaveArriba();
					changePositionUser(naveTerricola.getX(),naveTerricola.getY(),naveTerricola.getName());
			}//end if
			if(event.getNativeKeyCode()==KeyCodes.KEY_DOWN){
				
				  naveTerricola.DibujarNaveAbajo();
				  changePositionUser(naveTerricola.getX(),naveTerricola.getY(),naveTerricola.getName());
			}//end if
			}
		});

		RootPanel.get("notas").add(new HTML("<b>IMPORTANTE:</b> (Utilizar Navegador <b>CHROME</b> - jumanor@gmail.com) <br>1) Hacer Click sobre el Marciano <br> 2) Mueva el Marciano con las teclas"));
		
		////////////////////////////////////////////////CHAT/////////////////////////////////////////
		final DialogBox dlg=new DialogBox();
		dlg.setAutoHideEnabled(true);
		dlg.setAnimationEnabled(true);
		dlg.setModal(true);
		chat=new TFormChat(this);	
		dlg.setWidget(chat);
		
		Button btn=new Button("CHATEAR");
		btn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				dlg.center();
			}
		});
		RootPanel.get("chat").add(btn);
		
	}
}
