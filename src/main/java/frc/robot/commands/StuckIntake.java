package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.NoteStuckIntake;
/** An example command that uses an example subsystem. */
public class StuckIntake extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final NoteStuckIntake m_subsystem;
  private boolean isFinished = false;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem
   */
  public StuckIntake(NoteStuckIntake subsystem) {
    m_subsystem = subsystem;
  }

  @Override
  public void initialize() {
    isFinished = false;
  }

  @Override
  public void execute() {
    isFinished = false;
    m_subsystem.enableIntakeRev();
    isFinished = true;
  }

  @Override
  public void end(boolean interrupted) {
    m_subsystem.disableIntakeRev();
  }

  @Override
  public boolean isFinished() {
    if(isFinished)
        return true;
    return false;
  }
}