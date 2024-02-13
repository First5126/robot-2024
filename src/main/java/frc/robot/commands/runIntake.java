package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.shooter;

public class runIntake extends Command{
  private final shooter m_Shooter;
  private boolean isFinished;

  private boolean Slowed = false;

  private final GenericHID m_driverController = new GenericHID(OperatorConstants.kDriverControllerPort);

  public runIntake(shooter shooter){
    m_Shooter = shooter;
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    isFinished = false;
    Slowed = false;
  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!m_Shooter.SlowSensor.get()){
      Slowed = true;
    }
    if (!m_Shooter.StopSensor.get()){
      m_driverController.setRumble(RumbleType.kBothRumble, 1);
      isFinished = true;
      m_Shooter.rumbling = true;
      m_Shooter.timer.start();
    }
    else if (Slowed){
      m_Shooter.enableIntakeSlow();
    }
    else{
      m_Shooter.enableIntake();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Shooter.disableIntake();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
