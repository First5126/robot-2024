package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter;

public class runIntake extends Command{
    private final shooter m_shooter;
    private boolean isFinished;
    private boolean isSlowed;
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public runIntake(shooter subsystem) {
    m_shooter = subsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    isFinished = false;
    isSlowed = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!m_shooter.slowSensor.get()) {
      m_shooter.slowIntake();
      isSlowed = true;
    }
    if (!m_shooter.stopSensor.get()) {
      m_shooter.stopIntake();
      isFinished = true;
    }
    else if (isSlowed) {
      m_shooter.slowIntake();
    }
    else {
      m_shooter.runIntake();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.stopIntake();
    isFinished = true;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}