/*
 * This file is part of WebLookAndFeel library.
 *
 * WebLookAndFeel library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WebLookAndFeel library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WebLookAndFeel library.  If not, see <http://www.gnu.org/licenses/>.
 */

package sink.studio.bar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import sink.studio.core.Config;
import sink.studio.core.Register;
import web.laf.lite.layout.SwitchLayout;
import web.laf.lite.utils.CollectionUtils;
import web.laf.lite.utils.SwingUtils;
import web.laf.lite.utils.UIUtils;

/**
 * This component that allows switching between two states.
 *
 * @author Mikle Garin
 */

public class MSwitch extends JPanel{
	private static final long serialVersionUID = 1L;
    private List<ActionListener> actionListeners = new ArrayList<ActionListener> ( 1 );
    private boolean selected = false;
    private JPanel gripper;
    private JLabel leftComponent;
    private JLabel rightComponent;
    private Register reg;

    public MSwitch ()
    {
        super (new SwitchLayout () );
        UIUtils.setUndecorated(this, false);
        UIUtils.setRound ( this, 0 );
        UIUtils.setDrawFocus (this, true );
        setFocusable ( true );
        
        reg = new Register();
        Config.registerBoolean(reg);
        setSelected(Config.getBoolean(reg));
        
        putClientProperty ( "HANDLES_ENABLE_STATE", true );
        
        gripper = new JPanel(new BorderLayout ());
        UIUtils.setUndecorated(gripper, false);
        UIUtils.setRound(gripper, 0);
        add ( gripper, SwitchLayout.GRIPPER );

        // Left switch label
        leftComponent = new JLabel ( "ON", JLabel.CENTER );
        UIUtils.setBoldFont(leftComponent);
        UIUtils.setMargin(leftComponent,  new Insets(2, 5, 2, 5));
        UIUtils.setDrawShade (leftComponent, true );
        leftComponent.setForeground ( Color.DARK_GRAY );
        add ( leftComponent, SwitchLayout.LEFT );

        // Right switch label
        rightComponent = new JLabel ( "OFF", JLabel.CENTER );
        UIUtils.setBoldFont(rightComponent);
        UIUtils.setMargin(rightComponent,  new Insets(2, 5, 2, 5));
        UIUtils.setDrawShade (rightComponent, true );
        rightComponent.setForeground ( Color.DARK_GRAY );
        add ( rightComponent, SwitchLayout.RIGHT );

        // Switch animator

        // Selection switch listener
        MouseAdapter mouseAdapter = new MouseAdapter ()
        {
            @Override
            public void mousePressed ( MouseEvent e )
            {
                if ( SwingUtils.isLeftMouseButton ( e ) && isEnabled () )
                {
                    requestFocusInWindow ();
                    setSelected ( !isSelected () );
                    Config.putBoolean(reg, isSelected());
                }
            }
        };
        gripper.addMouseListener ( mouseAdapter );
        addMouseListener ( mouseAdapter );
    }

    /**
     * Returns actual switch layout.
     *
     * @return actual switch layout
     */
    public SwitchLayout getSwitchLayout ()
    {
        return ( SwitchLayout ) getLayout ();
    }

    /**
     * Returns switch gripper.
     *
     * @return switch gripper
     */
    public JPanel getGripper ()
    {
        return gripper;
    }

    /**
     * Returns left switch component.
     *
     * @return left switch component
     */
    public JLabel getLeftComponent ()
    {
        return leftComponent;
    }

    /**
     * Sets new left switch component.
     *
     * @param leftComponent new left switch component
     */
    public void setLeftComponent ( JLabel leftComponent )
    {
        if ( this.leftComponent != null )
        {
            remove ( this.leftComponent );
        }
        this.leftComponent = leftComponent;
        add ( leftComponent, SwitchLayout.LEFT );
        revalidate ();
    }

    /**
     * Returns right switch component.
     *
     * @return right switch component
     */
    public JLabel getRightComponent ()
    {
        return rightComponent;
    }

    /**
     * Sets new right switch component.
     *
     * @param rightComponent new right switch component
     */
    public void setRightComponent ( JLabel rightComponent )
    {
        if ( this.rightComponent != null )
        {
            remove ( this.rightComponent );
        }
        this.rightComponent = rightComponent;
        add ( rightComponent, SwitchLayout.RIGHT );
        revalidate ();
    }

    /**
     * Sets whether switch is enabled or not.
     *
     * @param enabled whether switch is enabled or not
     */
    @Override
    public void setEnabled ( boolean enabled )
    {
        super.setEnabled ( enabled );
        gripper.setEnabled ( enabled );
        leftComponent.setEnabled ( enabled );
        rightComponent.setEnabled ( enabled );
    }

    /**
     * Returns whether switch is selected or not.
     *
     * @return true if switch is selected, false otherwise
     */
    public boolean isSelected ()
    {
        return selected;
    }

    /**
     * Sets whether switch is selected or not and animates the transition if requested.
     *
     * @param selected whether switch is selected or not
     * @param animate  whether switch should animate the transition or not
     */
    public void setSelected ( boolean selected)
    {
        this.selected = selected;
        getSwitchLayout ().setGripperLocation ( selected ? 1f : 0f );
        revalidate ();
        fireActionPerformed ();
    }
    /**
     * Adds new switch action listener.
     *
     * @param actionListener switch action listener
     */
    public void addActionListener ( ActionListener actionListener )
    {
        actionListeners.add ( actionListener );
    }

    /**
     * Removes switch action listener.
     *
     * @param actionListener switch action listener
     */
    public void removeActionListener ( ActionListener actionListener )
    {
        actionListeners.remove ( actionListener );
    }

    /**
     * Fires that switch action is performed.
     */
    private void fireActionPerformed ()
    {
        ActionEvent actionEvent = new ActionEvent ( MSwitch.this, 0, "Selection changed" );
        for ( ActionListener actionListener : CollectionUtils.copy ( actionListeners ) )
        {
            actionListener.actionPerformed ( actionEvent );
        }
    }
}