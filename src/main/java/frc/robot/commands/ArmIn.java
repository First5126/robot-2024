package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.robotArm;

public class ArmIn extends Command {

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final robotArm m_subsystem;
  private boolean isFinished = false;
  private boolean Slowed;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */

  public ArmIn(robotArm subsystem) {
    m_subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    isFinished = false;
    Slowed = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!m_subsystem.SlowSensor.get()){
      Slowed = true;
    }
    if (!m_subsystem.StopSensor.get()){
      isFinished = true;
    }
    else if (Slowed){
      m_subsystem.enableIntakeslow();
    }
    else{
      m_subsystem.enableIntake();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystem.disableIntake();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
  
}
