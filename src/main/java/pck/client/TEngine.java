package pck.client;

import java.util.ArrayList;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FocusPanel;

public class TEngine {

	private TNave naveTerricola;
	private ArrayList<TNave> naves;
	
	private AbsolutePanel contenedorCanvas;
	
	private String path;
	public TEngine(){
		
		
		naveTerricola=new TNave();
		naveTerricola.setColor("red");
		naves=new ArrayList<TNave>();
		contenedorCanvas=new AbsolutePanel();
	
		path="http://prueba-jquerymobile.rhcloud.com/";
		
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
		 var tmp=this;
		 
		 $doc.getElementById("msn").innerHTML="<h1>CONECTANDO...<h1>";
		 
		 $wnd.socket.on('connect', function () {
	
		
			 $wnd.socket.on('onChangeAllPositionUsers', function (data) {
			 	 var data=JSON.parse(data);
				 tmp.@pck.client.TEngine::onChangeAllPositionUsers(Lcom/google/gwt/core/client/JsArray;)(data);
			  });
			 $wnd.socket.on('onBeginPositionUser', function (data) {
			    var user=JSON.parse(data);
			    tmp.@pck.client.TEngine::onBeginPositionUser(Lpck/client/TUser;)(user);
			  });
			 $wnd.socket.on('onRemoveUser', function (data) {
			    var user=JSON.parse(data);
				 tmp.@pck.client.TEngine::onRemoveUser(Lpck/client/TUser;)(user);
			  });
		 
		    
		    $doc.getElementById("msn").innerHTML="";
		 }); 
		   
	}-*/;

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
	/*
	public void onChangeAllPositionUsers(JavaScriptObject obj){
	 JSONArray objs= new JSONArray(obj);
	 for(int i=0;i<objs.size();i++){
		 JSONObject oi=objs.get(i).isObject();
		 String bb=oi.get("x").isNumber().toString();
		 Window.alert(bb);
	 }
	}*/
	//Este metodo se ejecuta una sola vez
	public void onBeginPositionUser(TUser user){
		naveTerricola.BorrarNave();
		naveTerricola.setX(user.getX());
		naveTerricola.setY(user.getY());
		naveTerricola.setName(user.getName());
		naveTerricola.DibujarNave();
		naves.add(naveTerricola);
	}
	private native void changePositionUser(int x,int y,String name) /*-{
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
	}
}
