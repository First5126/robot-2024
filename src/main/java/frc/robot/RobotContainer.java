// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Swerve.Telemetry;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.LLSubsystem;
import edu.wpi.first.wpilibj.XboxController;

import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.pathplanner.lib.auto.NamedCommands;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.Utils;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.FindDistance;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Swerve.CommandSwerveDrivetrain;
import frc.robot.Swerve.TunerConstants;
import frc.robot.commands.Intake;
import frc.robot.commands.ManualRotation;
import frc.robot.commands.Reverse;
import frc.robot.commands.RotateArm;
import frc.robot.commands.Shoot;

public class RobotContainer {
  
  //Controllers
  private final CommandGenericHID m_driverController = new CommandGenericHID(Constants.OperatorConstants.DriverControllerPort);
  public final CommandGenericHID m_ButtonsController = new CommandGenericHID(Constants.OperatorConstants.ButtonsControllerPort);


  //Subsystems
  //private final Shooter m_ShooterSubsystem = new Shooter(m_ButtonsController, m_driverController);
  //public final Arm m_arm = new Arm();
  public final LLSubsystem m_LlSubsystem = new LLSubsystem();
  //Swerve
  /*private final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain;
  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(Constants.SwerveConstants.MaxSpeed * 0.1).withRotationalDeadband(Constants.SwerveConstants.MaxAngularRate * 0.1) // Add a 10% deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage);
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.RobotCentric forwardStraight = new SwerveRequest.RobotCentric().withDriveRequestType(DriveRequestType.OpenLoopVoltage);
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
  private final Telemetry logger = new Telemetry(Constants.SwerveConstants.MaxSpeed);
  private SlewRateLimiter xSlewRate = new SlewRateLimiter(1.5);
  private SlewRateLimiter ySlewRate = new SlewRateLimiter(1.5);
  private SlewRateLimiter rotationSlewRate = new SlewRateLimiter(1.5); */
  /* Path follower */


  //autoChooser
  private SendableChooser<Command> autoChooser = new SendableChooser<Command>();
  
  public RobotContainer() {

    /*NamedCommands.registerCommand("Rotate Arm", new RotateArm(m_arm, 0));

    autoChooser.addOption("Test", drivetrain.getAutoPath("Test"));
    SmartDashboard.putData(autoChooser);*/

    configureBindings();
    m_LlSubsystem.setDefaultCommand(new FindDistance(m_LlSubsystem, 2));
  }

  private double deadband(double value){
    if (Math.abs(value) < 0.1){
      return 0;
    }
    else{
    return value;
    }
  }

  private void configureBindings() {
    //Swerve and driving

  /*  drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        drivetrain.applyRequest(() -> drive.withVelocityX(xSlewRate.calculate(-(Math.signum(m_driverController.getHID().getRawAxis(1)) * Math.pow(m_driverController.getHID().getRawAxis(1), 2))) * Constants.SwerveConstants.MaxSpeed) // Drive forward with
                                                                                           // negative Y (forward)
            .withVelocityY( ySlewRate.calculate(-(Math.signum(m_driverController.getHID().getRawAxis(0)) * Math.pow(m_driverController.getHID().getRawAxis(0), 2))) * Constants.SwerveConstants.MaxSpeed) // Drive left with negative X (left)
            .withRotationalRate( rotationSlewRate.calculate(-(m_driverController.getHID().getRawAxis(4) * Math.pow(m_driverController.getHID().getRawAxis(4), 2))) * Constants.SwerveConstants.MaxAngularRate) // Drive counterclockwise with negative X (left)
        ));

    (m_driverController).button(1).whileTrue(drivetrain.applyRequest(() -> brake)); //a
    ( m_driverController).button(2).whileTrue(drivetrain
        .applyRequest(() -> point.withModuleDirection(new Rotation2d(-(m_driverController.getHID().getRawAxis(1)), -(m_driverController.getHID().getRawAxis(0)))))); //b

    // reset the field-centric heading on left bumper press
    (m_driverController).button(5).onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));
    drivetrain.registerTelemetry(logger::telemeterize);

    m_driverController.pov(0).whileTrue(drivetrain.applyRequest(() -> forwardStraight.withVelocityX(0.5).withVelocityY(0)));
    m_driverController.pov(180).whileTrue(drivetrain.applyRequest(() -> forwardStraight.withVelocityX(-0.5).withVelocityY(0)));
  */


    //Buttons Controller
    /*final JoystickButton IntakeButton = new JoystickButton(m_ButtonsController.getHID(), XboxController.Button.kA.value);    
      IntakeButton.toggleOnTrue(new Intake(m_ShooterSubsystem, m_ButtonsController, m_driverController).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

    final JoystickButton ROtateArm = new JoystickButton(m_ButtonsController.getHID(), XboxController.Button.kY.value);
      ROtateArm.toggleOnTrue(new RotateArm(m_arm, -43.824219).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

    final JoystickButton SubwooferShotButton = new JoystickButton(m_ButtonsController.getHID(), XboxController.Button.kB.value);    
      SubwooferShotButton.toggleOnTrue(new Shoot(m_ShooterSubsystem, 58.48333).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

    final JoystickButton Reverse = new JoystickButton(m_ButtonsController.getHID(), XboxController.Button.kX.value);
      Reverse.whileTrue(new Reverse(m_ShooterSubsystem).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
    /*final JoystickButton moveArmToNinetyDegrees = new JoystickButton(m_ButtonsController, XboxController.Button.kX.value);
        moveArmToNinetyDegrees.toggleOnTrue(new RotateArm(m_arm, 64).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
    final JoystickButton homeArm = new JoystickButton(m_ButtonsController, XboxController.Button.kY.value);
        homeArm.toggleOnTrue(new RotateArm(m_arm, 0).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

    final JoystickButton manualRotationUp = new JoystickButton(m_ButtonsController.getHID(), XboxController.Button.kRightBumper.value);
      manualRotationUp.whileTrue(new ManualRotation(m_arm, 0.1).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

    final JoystickButton manualRotationDown = new JoystickButton(m_ButtonsController.getHID(), XboxController.Button.kLeftBumper.value);
      manualRotationDown.whileTrue(new ManualRotation(m_arm, -0.1).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

    final Trigger AmpShotButton = new Trigger (m_ButtonsController.povLeft());
      AmpShotButton.toggleOnTrue(new Shoot(m_ShooterSubsystem, 15.95));
    
    final Trigger SubwooferButton = new Trigger (m_ButtonsController.povDown());
      SubwooferButton.toggleOnTrue(new Shoot(m_ShooterSubsystem, 58.483333));

    final Trigger PodiumShotButton = new Trigger (m_ButtonsController.povRight());
      PodiumShotButton.toggleOnTrue(new Shoot(m_ShooterSubsystem, 63));*/

    /*if (Utils.isSimulation()) {
      drivetrain.seedFieldRelative(new Pose2d(new Translation2d(), Rotation2d.fromDegrees(90)));
    }
    drivetrain.registerTelemetry(logger::telemeterize);*/
  }
  
  public Command getAutonomousCommand() {

    return autoChooser.getSelected();
  }
}
