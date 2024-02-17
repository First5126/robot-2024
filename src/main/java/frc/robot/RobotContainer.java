package frc.robot;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.robotArm;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ArmIn;
 
public class RobotContainer {
  private final robotArm m_robotArm = new robotArm();
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final GenericHID m_driverController = new GenericHID(0);
  
  public RobotContainer() {
    configureBindings();
  }
  private void configureBindings() {
    intake.toggleOnTrue(new ArmIn(m_robotArm));
    final JoystickButton outtake = new JoystickButton(m_driverController, XboxController.Button.kY.value);
  }
}
