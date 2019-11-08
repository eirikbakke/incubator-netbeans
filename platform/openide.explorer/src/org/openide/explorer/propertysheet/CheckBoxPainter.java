/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.openide.explorer.propertysheet;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JCheckBox;

// Exact duplicate of the class org.netbeans.swing.outline.CheckBoxPainter.
final class CheckBoxPainter {
    private final JCheckBox checkBox;
    private boolean enabled;
    private Boolean selected;

    /**
     * @param checkBox may have its state changed upon paint
     */
    public CheckBoxPainter(JCheckBox checkBox) {
        if (checkBox == null)
            throw new NullPointerException();
        this.checkBox = checkBox;
    }

    /**
     * @param selected may be null to indicate an indeterminate state
     */
    public void setState(boolean enabled, Boolean selected) {
        this.enabled = enabled;
        this.selected = selected;
    }

    public void setSize(int width, int height) {
        checkBox.setSize(width, height);
        checkBox.doLayout();
    }

    public Dimension getSize() {
        return checkBox.getSize();
    }

    private final Composite composite =
        AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);

    public void paint(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        checkBox.setBorderPainted(false);
        // I don't know if this has any effect on the common LAFs, but the Javadoc suggests using it.
        checkBox.setBorderPaintedFlat(true);
        checkBox.setOpaque(false);
        /* AquaButtonLabeledUI ignores setOpaque(false) when the parent is a CellRendererPane. Make
        the background color translucent to avoid having to find the right background color. */
        checkBox.setBackground(new Color(0, 0, 0, 0));
        checkBox.setSelected(Boolean.TRUE.equals(selected));
        checkBox.setEnabled(enabled);
        this.checkBox.paint(g);
        if (selected == null) {
            checkBox.setSelected(true);
            Composite oldComposite = g.getComposite();
            g.setComposite(composite);
            this.checkBox.paint(g);
            g.setComposite(oldComposite);
        }
    }
}
