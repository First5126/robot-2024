package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter;

/** An example command that uses an example subsystem. */
public class runClearJam extends Command {
  private final shooter m_subsystem;

  private boolean isFinished;
  /**
   * Creates a new runClearJam.
   *
   * @param subsystem The subsystem used by this command.
   */
  public runClearJam(shooter subsystem) {
    m_subsystem = subsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    isFinished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_subsystem.clearJam();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystem.disableIntake();
    isFinished = true;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}