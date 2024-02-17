// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.Utils;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Swerve.CommandSwerveDrivetrain;
import frc.robot.Swerve.Telemetry;
import frc.robot.Swerve.TunerConstants;

public class RobotContainer {
  //Subsystems
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  //Controllers
  private final CommandXboxController m_driverController = new CommandXboxController(Constants.OperatorConstants.DriverControllerPort);
  private final GenericHID m_ButtonsController = new GenericHID(Constants.OperatorConstants.ButtonsControllerPort);

  //Swerve
  private final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain;
  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(Constants.SwerveConstants.MaxSpeed * 0.1).withRotationalDeadband(Constants.SwerveConstants.MaxAngularRate * 0.1) // Add a 10% deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage);
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.RobotCentric forwardStraight = new SwerveRequest.RobotCentric().withDriveRequestType(DriveRequestType.OpenLoopVoltage);
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
  private final Telemetry logger = new Telemetry(Constants.SwerveConstants.MaxSpeed);

  /* Path follower */
  private Command runAuto = drivetrain.getAutoPath("Tests");

  //autoChooser
  private final SendableChooser<Command> autoChooser;
  
  public RobotContainer() {
    autoChooser = AutoBuilder.buildAutoChooser();
    autoChooser.addOption("example path", PathPlannerPath.);
    SmartDashboard.putData("Auto Chooser", autoChooser);
    //NamedCommands.registerCommand("exampleCommand", exampleSubsystem.exampleCommand());
    configureBindings();
  }

  private void configureBindings() {
    //Swerve and driving
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));
    m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        drivetrain.applyRequest(() -> drive.withVelocityX(-m_driverController.getLeftY() * Constants.SwerveConstants.MaxSpeed) // Drive forward with
                                                                                           // negative Y (forward)
            .withVelocityY(-m_driverController.getLeftX() * Constants.SwerveConstants.MaxSpeed) // Drive left with negative X (left)
            .withRotationalRate(-m_driverController.getRightX() * Constants.SwerveConstants.MaxAngularRate) // Drive counterclockwise with negative X (left)
        ));

    m_driverController.a().whileTrue(drivetrain.applyRequest(() -> brake));
    m_driverController.b().whileTrue(drivetrain
        .applyRequest(() -> point.withModuleDirection(new Rotation2d(-m_driverController.getLeftY(), -m_driverController.getLeftX()))));

    // reset the field-centric heading on left bumper press
    m_driverController.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));
    drivetrain.registerTelemetry(logger::telemeterize);

    m_driverController.pov(0).whileTrue(drivetrain.applyRequest(() -> forwardStraight.withVelocityX(0.5).withVelocityY(0)));
    m_driverController.pov(180).whileTrue(drivetrain.applyRequest(() -> forwardStraight.withVelocityX(-0.5).withVelocityY(0)));

     /* Bindings for drivetrain characterization */
    /* These bindings require multiple buttons pushed to swap between quastatic and dynamic */
    /* Back/Start select dynamic/quasistatic, Y/X select forward/reverse direction */
    m_driverController.back().and(m_driverController.y()).whileTrue(drivetrain.sysIdDynamic(SysIdRoutine.Direction.kForward));
    m_driverController.back().and(m_driverController.x()).whileTrue(drivetrain.sysIdDynamic(SysIdRoutine.Direction.kReverse));
    m_driverController.start().and(m_driverController.y()).whileTrue(drivetrain.sysIdQuasistatic(SysIdRoutine.Direction.kForward));
    m_driverController.start().and(m_driverController.x()).whileTrue(drivetrain.sysIdQuasistatic(SysIdRoutine.Direction.kReverse));
    
    if (Utils.isSimulation()) {
      drivetrain.seedFieldRelative(new Pose2d(new Translation2d(), Rotation2d.fromDegrees(90)));
    }
    drivetrain.registerTelemetry(logger::telemeterize);
  }
  
  public Command getAutonomousCommand() {

    return autoChooser.getSelected();
  }
}
