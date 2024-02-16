package frc.robot.commands;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
public class ExampleCommand extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final ExampleSubsystem m_ExampleSubsystem;
  
  public ExampleCommand(ExampleSubsystem subsystem) {
    m_ExampleSubsystem = subsystem;
  }

  @Override
  public void initialize() {

  }
  @Override
  public void execute() {

  }
  @Override
  public void end(boolean interrupted) {
  }
  @Override
  public boolean isFinished() {
    return false;
  }
}
