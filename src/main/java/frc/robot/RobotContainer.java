// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;

import frc.robot.subsystems.limelight;
import frc.robot.subsystems.shooter;
import frc.robot.commands.limelightDistance;

import frc.robot.commands.runOuttake;
import frc.robot.commands.runIntake;
import frc.robot.commands.clearJam;

import frc.robot.subsystems.sparkMax;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/*
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final shooter m_shooter = new shooter();
  private final sparkMax m_sparkMax = new sparkMax();
  private final limelight m_Limelight = new limelight();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final GenericHID m_driverController = new GenericHID(0); // 0 is the USB Port to be used as indicated on the Driver Station
      

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();

    m_Limelight.setDefaultCommand(new limelightDistance(m_Limelight));
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

    final JoystickButton shooter = new JoystickButton(m_driverController, XboxController.Button.kB.value);
    final JoystickButton taker = new JoystickButton(m_driverController, XboxController.Button.kA.value);
    final JoystickButton clearJam = new JoystickButton(m_driverController, XboxController.Button.kX.value);

    shooter.toggleOnTrue(new runOuttake(m_shooter));
    taker.toggleOnTrue(new runIntake(m_shooter));
    clearJam.toggleOnTrue(new clearJam(m_shooter));
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
