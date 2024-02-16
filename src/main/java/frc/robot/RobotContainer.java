package frc.robot;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.ArmIn;
import frc.robot.commands.ArmOut;
import frc.robot.commands.ArmOutAmp;
import frc.robot.commands.Autos;
import frc.robot.commands.ClearJam;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.robotArm;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.RobotUpDown;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;

public class RobotContainer {
  private final robotArm m_robotArm = new robotArm();
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final GenericHID m_driverController = new GenericHID(0);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
   final JoystickButton intake = new JoystickButton(m_driverController, XboxController.Button.kA.value);
   intake.toggleOnTrue(new ArmIn(m_robotArm));
   final JoystickButton outtake = new JoystickButton(m_driverController, XboxController.Button.kB.value);
   outtake.toggleOnTrue(new ArmOut(m_robotArm));
   final JoystickButton ClearJam = new JoystickButton(m_driverController, XboxController.Button.kX.value);
    ClearJam.toggleOnTrue(new ClearJam(m_robotArm));
    /* final JoystickButton _____ = new JoystickButton(m_driverController, XboxController.Button.kX.value);
     * _____.toggleOnTrue(new _____(m_arm, 64).withInterruptBehavior(InterruptionBehavior.kCancelSelf));*/
   final JoystickButton outtakeamp  = new JoystickButton(m_driverController, XboxController.Button.kY.value);
    outtakeamp.toggleOnTrue(new ArmOutAmp(m_robotArm));
    /* final JoystickButton _____ = new JoystickButton(m_driverController, XboxController.Button.kY.value);
     * _____.toggleOnTrue(new _____(m_arm, 0).withInterruptBehavior(InterruptionBehavior.kCancelSelf));*/
  }

  public Command getAutonomousCommand() {
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}