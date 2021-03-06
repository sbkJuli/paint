package control.forms.debug;
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.awt.Color;
import java.awt.Rectangle;

import control.ControlPaint;
import model.objects.pen.normal.Pencil;
import model.settings.Constants;
import model.settings.State;
import model.util.DPoint;
import model.util.Util;
import model.util.adt.list.List;

/**
 * Class Bug contains methods for solving bugs that occur by being able to 
 * reconstruct them and debugging each action step-by step using the 
 * IDE debugger.
 * 
 * @author Julius Huelsmann
 * @version %I%, %U%
 */
public final class Bug {
	
	/**
	 * Paint control.
	 */
	private ControlPaint cp;
	
	/**
	 * Bug constructor.
	 * @param _loadingPath the path of the bug file that is loaded.
	 */
	private Bug(final String _loadingPath) {
		
		

        //print case message
        State.getLogger().info("normal start: launch programm!\n\n");
        
        //call controller
        cp = new ControlPaint();
        
        //set the initialization process terminated
        State.increaseInitializationFinished();
		
		//load bug and display it at loaded interface.
		loadBug(_loadingPath);
	}
	
	
	/**
	 * Load Bug from hard drive.
	 * @param _loadingPath the path of the bug file that is loaded.
	 */
	private void loadBug(final String _loadingPath) {
		List<String> ls_strg = Util.loadTextFile(_loadingPath);
		
		ls_strg.toFirst();
		while (!ls_strg.isBehind() && !ls_strg.isEmpty()) {
			
			String cString = ls_strg.getItem();
			if (cString.equals("Line")) {

				cp.getPicture().changePen(new Pencil(
						Constants.PEN_ID_LINES, 1, Color.blue));
				cp.getPicture().addPaintObject(
						cp.getView().getTabs().getTab_insert());

				DPoint p = new DPoint();
				while (!ls_strg.isBehind() 
						&& !ls_strg.isEmpty()
						&& p != null) {

					ls_strg.next();

					if (ls_strg.getItem() == null
							|| ls_strg.getItem().equals("")
							|| ls_strg.getItem().startsWith("//")) {
						ls_strg.next();
						continue;
					} else {
						
						p = isPoint(ls_strg.getItem());
						if (p != null) {

							cp.getPicture().changePaintObject(
									p, cp.getView().getPage(),
									cp.getControlPic());
						} else {
							ls_strg.previous();
						}
					}
				}
				
				cp.getPicture().finish(cp.getView().getTabs().getTab_debug());
			} else if (cString.contains("Rectangle")) {



				Rectangle p = new Rectangle();
				while (!ls_strg.isBehind() 
						&& !ls_strg.isEmpty()
						&& p != null) {

					ls_strg.next();
					System.out.println("ne");

					if (ls_strg.getItem() == null
							|| ls_strg.getItem().equals("")
							|| ls_strg.getItem().startsWith("//")) {
						ls_strg.next();
						System.out.println("hier");
						continue;
					} else {
						
						p = isRect(ls_strg.getItem());
						System.out.println(p);
						if (p != null) {

							cp.mr_sel_line_destroy(p);
						}
					}
				}
				
			} else if (
					!cString.equals("")
					&& !cString.startsWith("//")) {
				
				State.getLogger().severe("error reading bug file! Line: " 
				+ cString);
				ls_strg.next();
				
			} else {
				ls_strg.next();
			}
			
		}
		
	}

	/**
	 * Check whether string is a point and if that is the case, return the 
	 * point. Otherwise return null.
	 * 
	 * @param _line
	 * 				the text which is parsed
	 * 
	 * @return the point parsed out of the text.
	 */
	private DPoint isPoint(final String _line) {
		
		DPoint r = new DPoint();
		for (int i = 0; i < _line.length(); i++) {
			
			if (_line.charAt(i) == ' ') {
				try {
					r.setX(Double.parseDouble(_line.substring(0, i)));
					r.setY(Double.parseDouble(_line.substring(i)));
					return r;
				} catch (Exception e) {
					return null;
				}
			} 
		}
		return null;
	}

	
	/**
	 * Check whether parsed line of text is a rectangle.
	 * 
	 * @param _line
	 * 				the text which is parsed
	 * 
	 * @return the point parsed out of the text.
	 */
	private Rectangle isRect(final String _line) {
		
		int i1 = -1;
		int i2 = -1;
		int i3 = -1;
		Rectangle r = new Rectangle();
		for (int i = 0; i < _line.length(); i++) {
			
			if (_line.charAt(i) == ' ') {
				if (i1 == -1) {
					i1 = i;
				} else if (i2 == -1) {
					i2 = i;
				} else if (i3 == -1) {
					i3 = i;
				} else {
					State.getLogger().warning("Trailing spaces?");
				}
			} 
		}
		
		if (i1 != -1 && i2 != -1 && i3 != -1) {

			try {
				r.x = (int) (Double.parseDouble(_line.substring(0, i1)));
				r.y = (int) (Double.parseDouble(_line.substring(i1, i2)));
				r.width = (int) (Double.parseDouble(_line.substring(i2, i3)));
				r.height = (int) (Double.parseDouble(_line.substring(i3)));
				return r;
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * Alternative main method.
	 * @param _args the main arguments
	 */
	public static void main(final String [] _args) {
		final String bugFile = "2014_12_23.bug";
		new Bug(bugFile);
	}
}
