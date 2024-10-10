// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Swerve.Telemetry;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.LLSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DigitalInput;

import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.pathplanner.lib.auto.NamedCommands;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;

import java.util.function.BooleanSupplier;

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
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Swerve.CommandSwerveDrivetrain;
import frc.robot.Swerve.TunerConstants;
import frc.robot.commands.Intake;
import frc.robot.commands.LLAmpAdjust;
import frc.robot.commands.LLAutoAim;
import frc.robot.commands.ManualPID;
import frc.robot.commands.ManualRotation;
import frc.robot.commands.RotateArm;
import frc.robot.commands.Shoot;
import frc.robot.commands.TroubleShootIntake;
import frc.robot.commands.TroubleShootReverse;

public class RobotContainer {
  
  //Data Logger
  private DataLogger rLog = new DataLogger();
  //Arm mode switch
  private static DigitalInput ArmMode = new DigitalInput(7);
  //Controllers
  public final XboxController m_driverController = new XboxController(Constants.OperatorConstants.DriverControllerPort);
  public final XboxController m_ButtonsController = new XboxController(Constants.OperatorConstants.ButtonsControllerPort);


  //Subsystems
  private final Shooter m_ShooterSubsystem = new Shooter(m_ButtonsController, m_driverController);
  public final static Arm m_arm = new Arm();
  //Swerve
  private boolean isInverted = false;
  private final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain;
  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
  .withDeadband(Constants.SwerveConstants.MaxSpeed * 0.1).withRotationalDeadband(Constants.SwerveConstants.MaxAngularRate * 0.1) // Add a 10% deadband
  .withDriveRequestType(DriveRequestType.OpenLoopVoltage);
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.RobotCentric forwardStraight = new SwerveRequest.RobotCentric().withDriveRequestType(DriveRequestType.OpenLoopVoltage);
  private final Telemetry logger = new Telemetry(Constants.SwerveConstants.MaxSpeed);
  private SlewRateLimiter xSlewRate = new SlewRateLimiter(1.5);
  private SlewRateLimiter ySlewRate = new SlewRateLimiter(1.5);
  private SlewRateLimiter rotationSlewRate = new SlewRateLimiter(1.5); 
  /* Path follower */
  
  
  //autoChooser
  private SendableChooser<Command> autoChooser = new SendableChooser<Command>();
  
  //Limelight
  public final LLSubsystem m_LlSubsystem = new LLSubsystem();
  public RobotContainer() {
    
    NamedCommands.registerCommand("Rotate Arm Subwoofer", new RotateArm(m_arm, 11)); //Old : 15
    NamedCommands.registerCommand("Shoot Subwoofer", new Shoot(m_ShooterSubsystem, m_arm, 58.4833));
    NamedCommands.registerCommand("Rotate Arm Podium", new RotateArm(m_arm, 21.5)); //Old : 26
    NamedCommands.registerCommand("Rotate Arm Home", new ManualRotation(m_arm, -0.4));
    NamedCommands.registerCommand("Shoot Podium", new Shoot(m_ShooterSubsystem, m_arm, 78));
    NamedCommands.registerCommand("Intake", new Intake(m_ShooterSubsystem, m_ButtonsController, m_driverController));
    NamedCommands.registerCommand("Rotate to Bank Pos", new RotateArm(m_arm, 52));

    autoChooser.addOption("Bank Source Shoot Move", drivetrain.getAutoPath("Bank Source Shoot Move"));
    autoChooser.addOption("Amp Side 3 Note", drivetrain.getAutoPath("Amp Side 3 Note"));
    autoChooser.addOption("Amp Side Center Line", drivetrain.getAutoPath("Amp Side Center Line"));
    autoChooser.addOption("Alliance 4 Note Auto", drivetrain.getAutoPath("Alliance Color 4 Note Auto"));
    autoChooser.addOption("Four Blue Note Auto", drivetrain.getAutoPath("Four Note Blue Auto"));
    autoChooser.addOption("Three Blue Note Auto", drivetrain.getAutoPath("Three Note Blue Auto"));
    autoChooser.addOption("Two Blue Note Auto", drivetrain.getAutoPath("Two Note Blue Auto"));
    autoChooser.addOption("One Note Pickup Blue Auto", drivetrain.getAutoPath("One Note Pickup Blue Auto"));
    autoChooser.addOption("1730 Three Blue Note Auto", drivetrain.getAutoPath("1730 Three Note Blue Auto"));
    autoChooser.addOption("Amp Shoot Wait Move", drivetrain.getAutoPath("Amp Shoot Wait Move"));
    autoChooser.addOption("Blue Source Shoot Wait Move", drivetrain.getAutoPath("Source Shoot Wait Move"));
    autoChooser.addOption("Red Source Shoot Wait Move", drivetrain.getAutoPath("Red Source Shoot Wait Move"));
    autoChooser.addOption("Amp Side 3 Note Auto", drivetrain.getAutoPath("Amp Side 3 Note"));
    autoChooser.addOption("4766 Auto", drivetrain.getAutoPath("4766"));
    autoChooser.addOption("No Auto", drivetrain.getAutoPath("Null"));

    SmartDashboard.putData(autoChooser);

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
   drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        drivetrain.applyRequest(() -> drive.withVelocityX(xSlewRate.calculate(-(Math.signum(m_driverController.getRawAxis(1)) * Math.pow(m_driverController.getRawAxis(1), 2))) * Constants.SwerveConstants.MaxSpeed) // Drive forward with
                                                                                           // negative Y (forward)
            .withVelocityY( ySlewRate.calculate(-(Math.signum(m_driverController.getRawAxis(0)) * Math.pow(m_driverController.getRawAxis(0), 2))) * Constants.SwerveConstants.MaxSpeed) // Drive left with negative X (left)
            .withRotationalRate( rotationSlewRate.calculate(-(m_driverController.getRawAxis(4) * Math.pow(m_driverController.getRawAxis(4), 2))) * Constants.SwerveConstants.MaxAngularRate) // Drive counterclockwise with negative X (left)
        ));

    //( m_driverController).button(2).whileTrue(drivetrain.applyRequest(() -> point.withModuleDirection(new Rotation2d(-(m_driverController.getHID().getRawAxis(1)), -(m_driverController.getHID().getRawAxis(0)))))); //b*/

    // reset the field-centric heading on left bumper press
    //(m_driverController).button(8).onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));
    drivetrain.registerTelemetry(logger::telemeterize);

    final BooleanSupplier POVUpDriver = () -> this.m_driverController.getPOV() == 0;
      Trigger DriveForward = new Trigger (POVUpDriver);
      DriveForward.whileTrue(drivetrain.applyRequest(() -> forwardStraight.withVelocityX(m_driverController.getRawAxis(3)).withVelocityY(0).withRotationalRate( rotationSlewRate.calculate(-(m_driverController.getRawAxis(4) * Math.pow(m_driverController.getRawAxis(4), 2))) * Constants.SwerveConstants.MaxAngularRate) ));


    final BooleanSupplier POVLeftDriver = () -> this.m_driverController.getPOV() == 90;
      Trigger DriveLeft = new Trigger (POVLeftDriver);
      DriveLeft.whileTrue(drivetrain.applyRequest(() -> forwardStraight.withVelocityX(0).withVelocityY(-m_driverController.getRawAxis(3)).withRotationalRate( rotationSlewRate.calculate(-(m_driverController.getRawAxis(4) * Math.pow(m_driverController.getRawAxis(4), 2))) * Constants.SwerveConstants.MaxAngularRate) ));
    

    final BooleanSupplier POVDownDriver = () -> this.m_driverController.getPOV() == 180;
      Trigger DriveBack = new Trigger (POVDownDriver);
      DriveBack.whileTrue(drivetrain.applyRequest(() -> forwardStraight.withVelocityX(-m_driverController.getRawAxis(3)).withVelocityY(0).withRotationalRate( rotationSlewRate.calculate(-(m_driverController.getRawAxis(4) * Math.pow(m_driverController.getRawAxis(4), 2))) * Constants.SwerveConstants.MaxAngularRate) ));


    final BooleanSupplier POVRightDriver = () -> this.m_driverController.getPOV() == 270;
      Trigger DriveRight = new Trigger (POVRightDriver);
      DriveRight.whileTrue(drivetrain.applyRequest(() -> forwardStraight.withVelocityX(0).withVelocityY(m_driverController.getRawAxis(3)).withRotationalRate( rotationSlewRate.calculate(-(m_driverController.getRawAxis(4) * Math.pow(m_driverController.getRawAxis(4), 2))) * Constants.SwerveConstants.MaxAngularRate) ));

    final JoystickButton InvertDriveButton = new JoystickButton(m_driverController, XboxController.Button.kStart.value);
      InvertDriveButton.toggleOnTrue(drivetrain.applyRequest(() -> drive.withVelocityX(xSlewRate.calculate((Math.signum(m_driverController.getRawAxis(1)) * Math.pow(m_driverController.getRawAxis(1), 2))) * Constants.SwerveConstants.MaxSpeed) // Drive forward with
                                                                                           // negative Y (forward)
            .withVelocityY( ySlewRate.calculate((Math.signum(m_driverController.getRawAxis(0)) * Math.pow(m_driverController.getRawAxis(0), 2))) * Constants.SwerveConstants.MaxSpeed) // Drive left with negative X (left)
            .withRotationalRate( rotationSlewRate.calculate(-(m_driverController.getRawAxis(4) * Math.pow(m_driverController.getRawAxis(4), 2))) * Constants.SwerveConstants.MaxAngularRate) // Drive counterclockwise with negative X (left)
        ));

    //final JoystickButton InvertDriveButton = new JoystickButton(m_driverController, XboxController.Button.kStart.value);
      //InvertDriveButton.onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));
    final JoystickButton BreakButton = new JoystickButton(m_driverController, XboxController.Button.kRightBumper.value);
      BreakButton.whileTrue(drivetrain.applyRequest(() -> brake));

    final JoystickButton ShootSubwooferButton = new JoystickButton(m_driverController, XboxController.Button.kA.value);
      ShootSubwooferButton.toggleOnTrue(new Shoot(m_ShooterSubsystem, m_arm, 58.48333).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

    final JoystickButton ShootAmpButton = new JoystickButton(m_driverController, XboxController.Button.kX.value);
      ShootAmpButton.toggleOnTrue(new Shoot(m_ShooterSubsystem, m_arm, 35).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

    final JoystickButton ShootPodiumButton = new JoystickButton(m_driverController, XboxController.Button.kB.value);
      ShootPodiumButton.toggleOnTrue(new Shoot(m_ShooterSubsystem, m_arm, 60).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

    //final JoystickButton EjectButton = new JoystickButton(m_driverController, XboxController.Button.kY.value);
      //EjectButton.toggleOnTrue(new OverrideShoot(m_ShooterSubsystem).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
    final JoystickButton ShootBlueLineButton = new JoystickButton(m_driverController, XboxController.Button.kY.value);
      ShootBlueLineButton.toggleOnTrue(new Shoot(m_ShooterSubsystem, m_arm, 54).withInterruptBehavior(InterruptionBehavior.kCancelSelf)); //dont 

    //Buttons Controller
    final JoystickButton IntakeButton = new JoystickButton(m_ButtonsController, XboxController.Button.kA.value);    
      IntakeButton.toggleOnTrue(new Intake(m_ShooterSubsystem, m_ButtonsController, m_driverController).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

    final JoystickButton Reverse = new JoystickButton(m_ButtonsController, XboxController.Button.kX.value);
      Reverse.whileTrue(new TroubleShootReverse(m_ShooterSubsystem).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
    
    /*final JoystickButton TroubleShootReverse = new JoystickButton(m_ButtonsController.getHID(), XboxController.Button.kX.value);
      Reverse.whileTrue(new Reverse(m_ShooterSubsystem).withInterruptBehavior(InterruptionBehavior.kCancelSelf));*/

    final JoystickButton manualRotationUp = new JoystickButton(m_ButtonsController, XboxController.Button.kRightBumper.value);
      manualRotationUp.whileTrue(new ManualRotation(m_arm, 0.2).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

    final JoystickButton manualRotationDown = new JoystickButton(m_ButtonsController, XboxController.Button.kLeftBumper.value);
      manualRotationDown.whileTrue(new ManualRotation(m_arm, -0.2).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
  
    final JoystickButton HomeArm = new JoystickButton(m_ButtonsController, XboxController.Button.kStart.value);
      HomeArm.toggleOnTrue(new ManualRotation(m_arm, -0.4).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

      
      final JoystickButton TroubleShootButton = new JoystickButton(m_ButtonsController, XboxController.Button.kBack.value);
      TroubleShootButton.toggleOnTrue(new TroubleShootIntake(m_ShooterSubsystem, m_ButtonsController, m_driverController).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
      
      final JoystickButton SourcePosButton = new JoystickButton(m_ButtonsController, XboxController.Button.kB.value);
      SourcePosButton.toggleOnTrue(new RotateArm(m_arm, 40.8));
      
      //final JoystickButton ClimberButton = new JoystickButton(m_ButtonsController, XboxController.Button.kLeftStick.value);
      //ClimberButton.whileTrue(new Climb(m_arm).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
      
      final JoystickButton ManualRotationPIDButton = new JoystickButton(m_ButtonsController, XboxController.Button.kRightStick.value);
      ManualRotationPIDButton.toggleOnTrue(new ManualPID(m_arm).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
    // Limelight
    final JoystickButton limelightSpeakerAdjust = new JoystickButton(m_ButtonsController, XboxController.Button.kLeftStick.value);
      limelightSpeakerAdjust.and(m_LlSubsystem::isSpeakerTag).whileTrue(new LLAutoAim(m_LlSubsystem, drivetrain, drive).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

    final JoystickButton limelightAmpAdjust = new JoystickButton(m_ButtonsController, XboxController.Button.kLeftStick.value);
      limelightAmpAdjust.and(m_LlSubsystem::isAmpTag).whileTrue(new LLAmpAdjust(m_LlSubsystem, drivetrain, drive).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

    final BooleanSupplier POVLeftButtons = () -> this.m_ButtonsController.getPOV() == 270;
      Trigger AmpPosButton = new Trigger (POVLeftButtons);
      AmpPosButton.toggleOnTrue(new RotateArm(m_arm, 55));
    
    final BooleanSupplier POVDownButtons = () -> this.m_ButtonsController.getPOV() == 180;
      Trigger SubwooferPosButton = new Trigger (POVDownButtons);
      SubwooferPosButton.toggleOnTrue(new RotateArm(m_arm, 10.5));

    final BooleanSupplier POVRightButtons = () -> this.m_ButtonsController.getPOV() == 90;
      Trigger PodiumPosButton = new Trigger (POVRightButtons);
      PodiumPosButton.toggleOnTrue(new RotateArm(m_arm, 21.5));
    
  final BooleanSupplier POVUpButtons = () -> this.m_ButtonsController.getPOV() == 0;
      Trigger BankPosButton = new Trigger (POVUpButtons);
      BankPosButton.toggleOnTrue(new RotateArm(m_arm, 49));

    
    if (Utils.isSimulation()) {
      drivetrain.seedFieldRelative(new Pose2d(new Translation2d(), Rotation2d.fromDegrees(90)));
    }
    drivetrain.registerTelemetry(logger::telemeterize);
  }
  
  public Command getAutonomousCommand() {

    return autoChooser.getSelected();
  }

  public static void ChangeArmMode(){
        //Arm mode config
    if(ArmMode.get() == true){
      m_arm.setArmBrake();
    }
    else{
      m_arm.setArmCoast();
    }
  }

  public static boolean getSwitchPosition(){
    return ArmMode.get();
  }
}
