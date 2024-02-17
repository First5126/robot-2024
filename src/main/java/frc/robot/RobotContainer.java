package frc.robot;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.ArmRot;
import frc.robot.subsystems.RobotUpDown;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
public class RobotContainer {
  public final Arm m_arm = new Arm();
  public final GenericHID m_driverController = new GenericHID(OperatorConstants.kDriverControllerPort);
  public RobotContainer() {
    configureBindings();
  }
  private void configureBindings() {
    final JoystickButton moveArmToNinetyDegrees = new JoystickButton(m_driverController, XboxController.Button.kX.value);
    moveArmToNinetyDegrees.toggleOnTrue(new RotateArm(m_arm, 64).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
    final JoystickButton resetArm = new JoystickButton(m_driverController, XboxController.Button.kY.value);
    resetArm.toggleOnTrue(new RotateArm(m_arm, 0).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
  }
}
