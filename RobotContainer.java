// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;

import frc.robot.commands.ArmIn;
import frc.robot.commands.ArmOut;
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
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final GenericHID m_driverController = new GenericHID(0);
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */

  private void configureBindings() {
    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
   final JoystickButton intake = new JoystickButton(m_driverController, XboxController.Button.kA.value);
   intake.toggleOnTrue(new ArmIn(m_robotArm));
   final JoystickButton outtake = new JoystickButton(m_driverController, XboxController.Button.kB.value);
   outtake.toggleOnTrue(new ArmOut(m_robotArm));
   final JoystickButton stuckIntake = new JoystickButton(m_driverController, XboxController.Button.kX.value);
    stuckIntake.toggleOnTrue(new ArmIn(m_robotArm));
   final JoystickButton outtakeamp  = new JoystickButton(m_driverController, XboxController.Button.kY.value);
    outtakeamp.toggleOnTrue(new ArmOutAmp(m_robotArm));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   
  */

  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
  
}