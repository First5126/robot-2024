package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter;


public class runShooter extends Command{
  private final shooter m_Shooter;
  private boolean isFinished;

  public runShooter(shooter shooter){
    m_Shooter = shooter;
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    isFinished = false;
    m_Shooter.enableShooter();
    isFinished = true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Shooter.disableShooter();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
