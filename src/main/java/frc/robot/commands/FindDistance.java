package frc.robot.commands;

import frc.robot.subsystems.LLSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class FindDistance extends Command {
  private final LLSubsystem m_LLSubsystem;
  private final int IntakeLL;
  private double distance;

  private boolean isFinished;
  public FindDistance(LLSubsystem LL_subsystem, int Front_LL) {
    m_LLSubsystem = LL_subsystem;
    IntakeLL = Front_LL;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    isFinished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    distance = m_LLSubsystem.getDistance(IntakeLL);
    isFinished = true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    isFinished = true;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
