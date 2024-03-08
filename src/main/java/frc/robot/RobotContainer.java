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
import com.pathplanner.lib.path.PathPlannerPath;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.Utils;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.commands.Intake;
import frc.robot.commands.Reverse;
import frc.robot.commands.Shoot;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Swerve.CommandSwerveDrivetrain;
import frc.robot.Swerve.Telemetry;
import frc.robot.Swerve.TunerConstants;
import java.util.function.DoubleSupplier;

public class RobotContainer {
  
  //Controllers
  private final CommandGenericHID m_driverController = new CommandGenericHID(Constants.OperatorConstants.DriverControllerPort);
  public final GenericHID m_ButtonsController = new GenericHID(Constants.OperatorConstants.ButtonsControllerPort);


  //Subsystems
  private final Shooter m_ShooterSubsystem = new Shooter(m_ButtonsController, m_driverController);
  public final Arm m_arm = new Arm();

  //Swerve
  private final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain;
  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(Constants.SwerveConstants.MaxSpeed * 0.1).withRotationalDeadband(Constants.SwerveConstants.MaxAngularRate * 0.1) // Add a 10% deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage);
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.RobotCentric forwardStraight = new SwerveRequest.RobotCentric().withDriveRequestType(DriveRequestType.OpenLoopVoltage);
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
  private final Telemetry logger = new Telemetry(Constants.SwerveConstants.MaxSpeed);
  private SlewRateLimiter xSlewRate = new SlewRateLimiter(1.5);
  private SlewRateLimiter ySlewRate = new SlewRateLimiter(1.5);
  private SlewRateLimiter rotationSlewRate = new SlewRateLimiter(1.5);
  /* Path follower */
 private Command runAuto = drivetrain.getAutoPath("test");

  //autoChooser
  private final SendableChooser<Command> autoChooser = new SendableChooser<Command>();
  
  public RobotContainer() {
    autoChooser.addOption("Test PathPlanner", runAuto);
    SmartDashboard.putData(autoChooser);
    configureBindings();
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
    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        drivetrain.applyRequest(() -> drive.withVelocityX(xSlewRate.calculate(-(Math.signum(m_driverController.getHID().getRawAxis(1)) * Math.pow(m_driverController.getHID().getRawAxis(1), 2))) * Constants.SwerveConstants.MaxSpeed) // Drive forward with
                                                                                           // negative Y (forward)
            .withVelocityY( ySlewRate.calculate(-(Math.signum(m_driverController.getHID().getRawAxis(0)) * Math.pow(m_driverController.getHID().getRawAxis(0), 2))) * Constants.SwerveConstants.MaxSpeed) // Drive left with negative X (left)
            .withRotationalRate( rotationSlewRate.calculate(-(m_driverController.getHID().getRawAxis(4) * Math.pow(m_driverController.getHID().getRawAxis(4), 2))) * Constants.SwerveConstants.MaxAngularRate) // Drive counterclockwise with negative X (left)
        ));

    (m_driverController).button(1).whileTrue(drivetrain.applyRequest(() -> brake));
    ( m_driverController).button(2).whileTrue(drivetrain
        .applyRequest(() -> point.withModuleDirection(new Rotation2d(-(m_driverController.getHID().getRawAxis(1)), -(m_driverController.getHID().getRawAxis(0))))));

    // reset the field-centric heading on left bumper press
    (m_driverController).button(5).onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));
    drivetrain.registerTelemetry(logger::telemeterize);

    m_driverController.pov(0).whileTrue(drivetrain.applyRequest(() -> forwardStraight.withVelocityX(0.5).withVelocityY(0)));
    m_driverController.pov(180).whileTrue(drivetrain.applyRequest(() -> forwardStraight.withVelocityX(-0.5).withVelocityY(0)));

     /* Bindings for drivetrain characterization */
    /* These bindings require multiple buttons pushed to swap between quastatic and dynamic */
    /* Back/Start select dynamic/quasistatic, Y/X select forward/reverse direction */
    /*m_driverController.back().and(m_driverController.y()).whileTrue(drivetrain.sysIdDynamic(SysIdRoutine.Direction.kForward));
    m_driverController.back().and(m_driverController.x()).whileTrue(drivetrain.sysIdDynamic(SysIdRoutine.Direction.kReverse));
    m_driverController.start().and(m_driverController.y()).whileTrue(drivetrain.sysIdQuasistatic(SysIdRoutine.Direction.kForward));
    m_driverController.start().and(m_driverController.x()).whileTrue(drivetrain.sysIdQuasistatic(SysIdRoutine.Direction.kReverse));
    */
    //Buttons Controller
    final JoystickButton IntakeButton = new JoystickButton(m_ButtonsController, XboxController.Button.kA.value);    
      IntakeButton.toggleOnTrue(new Intake(m_ShooterSubsystem, m_ButtonsController, m_driverController).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

    final JoystickButton ShootButton = new JoystickButton(m_ButtonsController, XboxController.Button.kB.value);    
      ShootButton.toggleOnTrue(new Shoot(m_ShooterSubsystem).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

    final JoystickButton Reverse = new JoystickButton(m_ButtonsController, XboxController.Button.kX.value);
      Reverse.whileTrue(new Reverse(m_ShooterSubsystem).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
    /*final JoystickButton moveArmToNinetyDegrees = new JoystickButton(m_ButtonsController, XboxController.Button.kX.value);
    final JoystickButton homeArm = new JoystickButton(m_ButtonsController, XboxController.Button.kY.value);
*/
    final JoystickButton manualRotationUp = new JoystickButton(m_ButtonsController, XboxController.Button.kRightBumper.value);
    final JoystickButton manualRotationDown = new JoystickButton(m_ButtonsController, XboxController.Button.kLeftBumper.value);
  /* 
    moveArmToNinetyDegrees.toggleOnTrue(new RotateArm(m_arm, 64).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
    homeArm.toggleOnTrue(new RotateArm(m_arm, 0).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
*/
    manualRotationDown.whileTrue(new ManualRotation(m_arm, 0.1).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
    manualRotationUp.whileTrue(new ManualRotation(m_arm, -0.1).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

    if (Utils.isSimulation()) {
      drivetrain.seedFieldRelative(new Pose2d(new Translation2d(), Rotation2d.fromDegrees(90)));
    }
    drivetrain.registerTelemetry(logger::telemeterize);
  }
  
  public Command getAutonomousCommand() {

    return autoChooser.getSelected();
  }
}
