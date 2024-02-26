// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.ManualRotation;
import frc.robot.commands.RotateArm;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.PotTest;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;
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
  // Replace with CommandPS4Controller or CommandJoystick if needed
  public final GenericHID m_driverController = new GenericHID(OperatorConstants.kDriverControllerPort);

  public final Arm m_arm = new Arm();
  public PotTest m_potTest = new PotTest();
  /* The container for the robot. Contains subsystems, OI devices, and commands. */
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
    final JoystickButton moveArmToNinetyDegrees = new JoystickButton(m_driverController, XboxController.Button.kX.value);
    final JoystickButton resetArm = new JoystickButton(m_driverController, XboxController.Button.kY.value);

    final JoystickButton manualSpinPositive = new JoystickButton(m_driverController, XboxController.Button.kA.value);
    final JoystickButton manualSpinNegative = new JoystickButton(m_driverController, XboxController.Button.kB.value);
  
    moveArmToNinetyDegrees.toggleOnTrue(new RotateArm(m_arm, 64).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
    resetArm.toggleOnTrue(new RotateArm(m_arm, 0).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

    manualSpinPositive.whileTrue(new ManualRotation(m_arm, 0.3).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
    manualSpinNegative.whileTrue(new ManualRotation(m_arm, -0.3).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
  }
}
