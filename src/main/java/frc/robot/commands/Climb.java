package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;

public class Climb extends Command {
    private final Arm m_subsystem;
    private double speed;

  private boolean isFinished;

  public Climb(Arm subsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
    isFinished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  
    if ((m_subsystem.getReverseMagneticLimit() && Math.signum(speed) == -1) || (m_subsystem.leftMotorFx.getReverseLimit().getValueAsDouble() == 0 && Math.signum(speed) == -1)){
      isFinished = true;
    }
    else if ((m_subsystem.leftMotorFx.getForwardLimit().getValueAsDouble() == 0 && Math.signum(speed) == 1) || (m_subsystem.getForwardMagneticLimit() && Math.signum(speed) == 1) ){
      isFinished = true;
    }
    else{
      m_subsystem.setClimberSpeed();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    isFinished = true;
    m_subsystem.endRot();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
