package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.limitSwitches;

public class limitSwitchTest extends Command{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final limitSwitches m_subsystem;
  private boolean isFinished;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public limitSwitchTest(limitSwitches subsystem) {
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
    isFinished = m_subsystem.getOut();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}

