package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.NoteStuckOutake;

/** An example command that uses an example subsystem. */
public class StuckOutake extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final NoteStuckOutake m_subsystem;
  private boolean isFinished = false;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public StuckOutake(NoteStuckOutake subsystem) {
    m_subsystem = subsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    isFinished = false;
    m_subsystem.enableOutakeRev();
    isFinished = true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystem.disableOutakeRev();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(isFinished)
        return true;
    return false;
  }
}