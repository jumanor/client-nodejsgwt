package pck.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.FocusPanel;

public class TCapturarMouse {

	FocusPanel focus;
	TNave naveTerricola;
	int x=0;
	int y=0;
    int varMouseImageX;
    int varMouseImageY;
    
	boolean presiono=false;
	boolean moviento=false;
	
	TEngine engine;
	
	public TCapturarMouse(TEngine _engine,FocusPanel _focus,TNave _nave) {
		this.focus=_focus;
	    this.naveTerricola=_nave;
	    this.engine=_engine;
		focus.addMouseDownHandler(new MouseDownHandler() {
			
			@Override
			public void onMouseDown(MouseDownEvent event) {
				// TODO Auto-generated method stub
				varMouseImageX=event.getX()-naveTerricola.getX();
				varMouseImageY=event.getY()-naveTerricola.getY();
						
			 	x=event.getX();
			 	y=event.getY();
			 	presiono=true;
			}
		});
		focus.addMouseMoveHandler(new MouseMoveHandler() {
			
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				// TODO Auto-generated method stub
				if(presiono==true){
					
					int xf=event.getX();
					int yf=event.getY();
					
					int tmpX=naveTerricola.getX();
					int tmpY=naveTerricola.getY();
					
					naveTerricola.BorrarNave();
					naveTerricola.setX(xf+(xf-x));
					naveTerricola.setY(yf+(yf-y));
					naveTerricola.DibujarNave();
				
					moviento=true;
					
					int varX=naveTerricola.getX()-tmpX;
					int varY=naveTerricola.getY()-tmpY;
					
					//OJO: cambiar changePositionUser a broadcast para optimizar!!!!!!
					//Para que no sufra al pobre node.js :)
					if(Math.abs(varX)>2 || Math.abs(varY)>2)
					{
						engine.changePositionUserMouse(naveTerricola.getX(),naveTerricola.getY(),naveTerricola.getName());
						GWT.log("enviado :"+Math.abs(varX)+","+Math.abs(varY));
					}
					else
						GWT.log("no enviado");
				}
			}
		});
		focus.addMouseUpHandler(new MouseUpHandler() {
			
			@Override
			public void onMouseUp(MouseUpEvent event) {
				// TODO Auto-generated method stub
				if(moviento==true){
					moviento=false;
					presiono=false;
					return;
				}
				
				int xf=event.getX();
				int yf=event.getY();
					
				int tmpX=naveTerricola.getX();
				int tmpY=naveTerricola.getY();
			
				naveTerricola.BorrarNave();
				naveTerricola.setX(xf);
				naveTerricola.setY(yf);
				naveTerricola.DibujarNave();
				presiono=false;
			
				int varX=naveTerricola.getX()-tmpX;
				int varY=naveTerricola.getY()-tmpY;
				
				//Para que no sufra al pobre node.js :)
				if(Math.abs(varX)>2 || Math.abs(varY)>2)
					engine.changePositionUserMouse(naveTerricola.getX(),naveTerricola.getY(),naveTerricola.getName());
			}  
			
		});
		
	}
}
