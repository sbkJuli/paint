package view.util;


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
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.AbstractBorder;


/**
 * Round border,.
 * @author Julius Huelsmann
 * @version %I%, %U%
 */
@SuppressWarnings("serial")
public class RoundedBorder extends AbstractBorder {
    
    /**
     * {@inheritDoc}
     */
    @Override public final void paintBorder(
            final Component _c_owner, final Graphics _g_owner, 
            final int _x_coord, final int _y_coord, 
            final int _width, final int _height) {
        
      Graphics2D g2 = (Graphics2D) _g_owner.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
              RenderingHints.VALUE_ANTIALIAS_ON);
      
      RoundRectangle2D round = new RoundRectangle2D.Float(
              _x_coord, _y_coord, _width - 1, _height - 1,
              _height - 1, _height - 1);
      Container parent = _c_owner.getParent();
      if (parent != null) {
        g2.setColor(parent.getBackground());
        Area corner = new Area(new Rectangle2D.Float(
                _x_coord, _y_coord, _width, _height));
        corner.subtract(new Area(round));
        g2.fill(corner);
      }
      g2.setColor(Color.GRAY);
      g2.draw(round);
      g2.dispose();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override public final Insets getBorderInsets(final Component _c) {
        
        return new Insets(2 * 2, 2 * 2 * 2, 2 * 2, 2 * 2 * 2);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override public final Insets getBorderInsets(final Component _c, 
            final Insets _insets) {
      _insets.left = 2 * 2 * 2;
      _insets.right = _insets.left;
      
      _insets.bottom = 2 * 2;
      _insets.top = _insets.bottom;
      return _insets;
    }
  }
