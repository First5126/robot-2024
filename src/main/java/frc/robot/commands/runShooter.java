package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter;


public class runShooter extends Command{
  private final shooter m_Shooter;
  private boolean isFinished;
  private Timer timer;
  private double startTime;

  public runShooter(shooter shooter){
    m_Shooter = shooter;
    timer = new Timer();
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.start();
    startTime = timer.get();
  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    isFinished = false;
    m_Shooter.enableShooter();
    if (timer.hasElapsed(1.5)){
      isFinished = true;
      timer.stop();
      timer.reset();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Shooter.disableShooter();
    isFinished = true;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
