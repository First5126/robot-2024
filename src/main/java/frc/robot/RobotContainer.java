// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.Swerve.Telemetry;
import frc.robot.commands.ManualRotation;
import frc.robot.commands.RotateArm;
import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.Utils;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.Intake;
import frc.robot.commands.Reverse;
import frc.robot.commands.Shoot;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Swerve.CommandSwerveDrivetrain;
import frc.robot.Swerve.Telemetry;
import frc.robot.Swerve.TunerConstants;

public class RobotContainer {
  //Subsystems
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final Shooter m_ShooterSubsystem = new Shooter();
  public final Arm m_arm = new Arm();

  //Controllers
  private final CommandXboxController m_driverController = new CommandXboxController(Constants.OperatorConstants.DriverControllerPort);
  private final GenericHID m_ButtonsController = new GenericHID(Constants.OperatorConstants.ButtonsControllerPort);

  //Swerve
  private final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain;
  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(Constants.SwerveConstants.MaxSpeed * 0.1).withRotationalDeadband(Constants.SwerveConstants.MaxAngularRate * 0.1) // Add a 10% deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage);
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
  private final Telemetry logger = new Telemetry(Constants.SwerveConstants.MaxSpeed);


  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    // Swerve and driving
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));
    m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        drivetrain.applyRequest(() -> drive.withVelocityX(-(Math.signum(m_driverController.getLeftY()) * Math.pow(m_driverController.getLeftY(), 2)) * Constants.SwerveConstants.MaxSpeed) // Drive forward with
                                                                                           // negative Y (forward)
            .withVelocityY(-(Math.signum(m_driverController.getLeftX()) * Math.pow(m_driverController.getLeftX(), 2)) * Constants.SwerveConstants.MaxSpeed) // Drive left with negative X (left)
            .withRotationalRate(-m_driverController.getRightX() * Constants.SwerveConstants.MaxAngularRate) // Drive counterclockwise with negative X (left)
        ));

    m_driverController.a().whileTrue(drivetrain.applyRequest(() -> brake));
    m_driverController.b().whileTrue(drivetrain
        .applyRequest(() -> point.withModuleDirection(new Rotation2d(-m_driverController.getLeftY(), -m_driverController.getLeftX()))));

    // reset the field-centric heading on left bumper press
    m_driverController.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));

    if (Utils.isSimulation()) {
      drivetrain.seedFieldRelative(new Pose2d(new Translation2d(), Rotation2d.fromDegrees(90)));
    }
    drivetrain.registerTelemetry(logger::telemeterize);

    //Buttons Controller
    final JoystickButton Intake2Button = new JoystickButton(m_ButtonsController, XboxController.Button.kA.value);    
      Intake2Button.toggleOnTrue(new Intake(m_ShooterSubsystem).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

    final JoystickButton ShootButton = new JoystickButton(m_ButtonsController, XboxController.Button.kB.value);    
      ShootButton.toggleOnTrue(new Shoot(m_ShooterSubsystem).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

    final JoystickButton Reverse = new JoystickButton(m_ButtonsController, XboxController.Button.kX.value);
      Reverse.toggleOnTrue(new Reverse(m_ShooterSubsystem).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
    final JoystickButton moveArmToNinetyDegrees = new JoystickButton(m_ButtonsController, XboxController.Button.kX.value);
    final JoystickButton homeArm = new JoystickButton(m_ButtonsController, XboxController.Button.kY.value);

    final JoystickButton manualRotationUp = new JoystickButton(m_ButtonsController, XboxController.Button.kRightBumper.value);
    final JoystickButton manualRotationDown = new JoystickButton(m_ButtonsController, XboxController.Button.kLeftBumper.value);
  
    moveArmToNinetyDegrees.toggleOnTrue(new RotateArm(m_arm, 64).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
    homeArm.toggleOnTrue(new RotateArm(m_arm, 0).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

    manualRotationUp.whileTrue(new ManualRotation(m_arm, 0.3).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
    manualRotationDown.whileTrue(new ManualRotation(m_arm, -0.3).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
  }
  
  public Command getAutonomousCommand() {
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}

