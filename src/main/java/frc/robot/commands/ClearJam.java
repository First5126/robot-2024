package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.robotArm;

public class ClearJam extends Command {
  private final robotArm m_subsystem;
  private boolean isFinished;

  public ClearJam(robotArm subsystem) {
    m_subsystem = subsystem;
  }

  @Override
  public void initialize() {
    isFinished = false;
  }
  @Override
  public void execute() {
    m_subsystem.enableClearJam();
  }
  @Override
  public void end(boolean interrupted) {
    m_subsystem.disableClearJam();
    isFinished = true;
  }
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
