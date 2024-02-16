// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.ArmIn;
import frc.robot.commands.ArmOut;
import frc.robot.commands.ArmOutAmp;
import frc.robot.commands.Autos;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.robotArm;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */

public class RobotContainer {

  // The robot's subsystems and commands are defined here...
  private final robotArm m_robotArm = new robotArm();
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final GenericHID m_driverController = new GenericHID(0);

  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  private void configureBindings() {
   final JoystickButton intake = new JoystickButton(m_driverController, XboxController.Button.kA.value);
   intake.toggleOnTrue(new ArmIn(m_robotArm));
   final JoystickButton outtake = new JoystickButton(m_driverController, XboxController.Button.kB.value);
   outtake.toggleOnTrue(new ArmOut(m_robotArm));
   final JoystickButton stuckIntake = new JoystickButton(m_driverController, XboxController.Button.kX.value);
    stuckIntake.toggleOnTrue(new ArmIn(m_robotArm));
   final JoystickButton outtakeamp  = new JoystickButton(m_driverController, XboxController.Button.kY.value);
    outtakeamp.toggleOnTrue(new ArmOutAmp(m_robotArm));
  }

  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
  
}
