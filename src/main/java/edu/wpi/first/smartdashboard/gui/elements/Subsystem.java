package edu.wpi.first.smartdashboard.gui.elements;

import edu.wpi.first.smartdashboard.gui.elements.bindings.AbstractTableWidget;
import edu.wpi.first.smartdashboard.gui.elements.bindings.BooleanBindable;
import edu.wpi.first.smartdashboard.gui.elements.bindings.StringBindable;
import edu.wpi.first.smartdashboard.properties.ColorProperty;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.types.DataType;
import edu.wpi.first.smartdashboard.types.named.SubsystemType;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

/**
 * @author Joe Grinstead
 */
public class Subsystem extends AbstractTableWidget {

  public static final DataType[] TYPES = {SubsystemType.get()};

  public final ColorProperty background = new ColorProperty(this, "Background");

  private SubsystemCommandField valueField;

  public void init() {
    final JLabel nameLabel = new JLabel(getFieldName());
    valueField = new SubsystemCommandField();

    update(background, valueField.getBackground());

    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

    valueField.setEditable(false);
    valueField.setColumns(10);

    add(nameLabel);
    add(valueField);
    revalidate();
    repaint();
  }

  private class SubsystemCommandField extends BindableStringField {
    private boolean hasCommand = false;
    private String commandName = "";

    public SubsystemCommandField() {
      super(StringBindable.NULL);
      setStringBinding(".command", new StringBindable() {

        public void setBindableValue(String value) {
          commandName = value;
          if (hasCommand) {
            SubsystemCommandField.this.setBindableValue(commandName);
          } else {
            SubsystemCommandField.this.setBindableValue("---");
          }
        }
      }, "---");
      setBooleanBinding(".hasCommand", new BooleanBindable() {

        public void setBindableValue(boolean value) {
          hasCommand = value;
          if (hasCommand) {
            SubsystemCommandField.this.setBindableValue(commandName);
          } else {
            SubsystemCommandField.this.setBindableValue("---");
          }
        }
      });
    }
  }

  @Override
  public void propertyChanged(Property property) {
    if (property == background) {
      valueField.setBackground(background.getValue());
    }
  }
}
