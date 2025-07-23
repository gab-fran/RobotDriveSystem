package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs;

public class DriveSubsystem extends SubsystemBase {
  public final SparkMax drivingMotor;
  public final SparkFlex turningMotor;

  private final RelativeEncoder turningEncoder;

  private final SparkClosedLoopController pid;

  public DriveSubsystem() {
    drivingMotor = new SparkMax(22, MotorType.kBrushless);
    turningMotor = new SparkFlex(18, MotorType.kBrushless);

    turningEncoder = turningMotor.getEncoder();

    pid = turningMotor.getClosedLoopController();

    drivingMotor.configure(Configs.drivingConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    turningMotor.configure(Configs.turningConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void drive(double xSpeed, double ySpeed) {

    if (Math.abs(xSpeed) < 0.05 && Math.abs(ySpeed) < 0.05)
      return;

    double angleDeg = Math.toDegrees(Math.atan2(ySpeed, xSpeed));

    if (angleDeg < 0)
      angleDeg += 360;

    //double setpoint = (angleDeg / 360.0) * 7168.0;
    double speed = Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed);

    turningMotor.set(speed);

    //pid.setReference(setpoint, ControlType.kPosition);
  }
  public void stopMotors(){
    drivingMotor.set(0);
    turningMotor.set(0);
  }
}
