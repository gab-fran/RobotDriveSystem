package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Configs;
import frc.robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {
  /** Driving motors */
  private final SparkMax drivingMotor; // Motor responsável por dirigir o robô (frente esquerda)

  private final TalonFX drivingMotorKraken; // Outro motor de driving (frente esquerda, configuração Kraken)

  /** Turning motors */
  private final SparkFlex turningMotor; // Motor responsável por turning (frente esquerda)
  private final SparkFlex turningMotor2; // Motor responsável por turning (frente direita)

  /** Encoders */
  private final RelativeEncoder turningEncoder; // Encoder para o motor de turning da frente esquerda
  private final RelativeEncoder turningEncoder2; // Encoder para o motor de turning da frente direita

  /** PID controllers */
  private final SparkClosedLoopController pid; // Controlador PID para o motor de turning da frente esquerda
  private final SparkClosedLoopController pid2; // Controlador PID para o motor de turning da frente direita

  public DriveSubsystem() {
    // Instancia o motor de direção da frente esquerda utilizando o SparkMax com
    // motor brushless
    drivingMotor = new SparkMax(DriveConstants.frontLeftDriving, MotorType.kBrushless);

    // Instancia o motor de direção da frente esquerda (configuração Kraken)
    // utilizando o TalonFX
    drivingMotorKraken = new TalonFX(DriveConstants.frontLeftDrivingKraken, "rio");

    // Instancia o motor de turning da frente esquerda utilizando o SparkFlex com
    // motor brushless
    turningMotor = new SparkFlex(DriveConstants.frontLeftTurning, MotorType.kBrushless);

    // Instancia o motor de turning da frente direita utilizando o SparkFlex com
    // motor brushless
    turningMotor2 = new SparkFlex(DriveConstants.frontRightTurning, MotorType.kBrushless);

    // Obtém o encoder relativo do motor de turning da frente esquerda
    turningEncoder = turningMotor.getEncoder();

    // Obtém o encoder relativo do motor de turning da frente direita
    turningEncoder2 = turningMotor2.getEncoder();

    // Obtém o controlador PID do motor de turning da frente esquerda
    pid = turningMotor.getClosedLoopController();

    // Obtém o controlador PID do motor de turning da frente direita
    pid2 = turningMotor2.getClosedLoopController();

    // Inicializa os motores com as configurações apropriadas
    inicializeMotors();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  /**
   * Inicializa os motores e suas configurações para o subsistema de direção.
   * Este método configura os motores de direção e de turning com configurações
   * predefinidas,
   * limpa quaisquer falhas persistentes, aplica configurações múltiplas vezes se
   * necessário,
   * e reseta as posições dos motores e encoders.
   * 
   * Passos realizados:
   * 1. Configura os motores de direção e turning com parâmetros de reset seguro e
   * persistentes.
   * 2. Limpa falhas persistentes no motor de direção (Kraken).
   * 3. Aplica uma configuração padrão ao motor de direção (Kraken).
   * 4. Tenta aplicar a configuração do motor de direção (Kraken) até 5 vezes,
   * verificando o sucesso após cada tentativa.
   * 5. Registra uma mensagem de erro caso a configuração não seja aplicada com
   * sucesso.
   * 6. Reseta a posição do motor de direção (Kraken) e dos encoders de turning.
   */
  public void inicializeMotors() {
    // Configura o motor de direção (SparkMax) com parâmetros seguros e persistentes
    drivingMotor.configure(Configs.drivingConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // Configura o motor de turning da frente esquerda com parâmetros seguros e
    // persistentes
    turningMotor.configure(Configs.turningConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // Configura o motor de turning da frente direita com parâmetros seguros e
    // persistentes
    turningMotor2.configure(Configs.turningConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // Limpa falhas persistentes no motor de direção Kraken (TalonFX)
    drivingMotorKraken.clearStickyFaults();

    // Aplica uma configuração padrão ao motor de direção Kraken (TalonFX)
    drivingMotorKraken.getConfigurator().apply(new TalonFXConfiguration());

    // Tenta aplicar a configuração específica para o motor de direção Kraken até 5
    // vezes
    StatusCode status = StatusCode.StatusCodeNotInitialized;
    for (int i = 0; i < 5; ++i) {
      status = drivingMotorKraken.getConfigurator().apply(Configs.drivingConfigKraken);
      // Verifica se a configuração foi aplicada com sucesso
      if (status.isOK())
        break;
    }
    // Caso a configuração não seja aplicada com sucesso, registra uma mensagem de
    // erro
    if (!status.isOK()) {
      System.out.println("Could not apply configs, error code: " + status.toString());
    }

    // Reseta a posição do motor de direção Kraken (TalonFX)
    drivingMotorKraken.getConfigurator().setPosition(0);

    // Reseta a posição do encoder do motor de turning da frente esquerda
    turningEncoder.setPosition(0);

    // Reseta a posição do encoder do motor de turning da frente direita
    turningEncoder2.setPosition(0);
  }

  public void drive(double xSpeed, double ySpeed) {

    setTurningAngle(xSpeed, ySpeed);

    setDrivingSpeed(xSpeed, ySpeed);
    
  }

  public void setTurningAngle(double xSpeed, double ySpeed) {
    if (Math.abs(xSpeed) < 0.05 && Math.abs(ySpeed) < 0.05)
      return;

    double angleDeg = Math.toDegrees(Math.atan2(ySpeed, xSpeed));

    if (angleDeg < 0)
      angleDeg += 360;

    double setpoint = (angleDeg / 360.0) * 1;

    pid.setReference(setpoint, ControlType.kPosition);
    //pid2.setReference(setpoint, ControlType.kPosition);
  }

  public void setDrivingSpeed(double xSpeed, double ySpeed) {
    double speed = Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed);
    speed *= Math.signum(ySpeed);

    if (speed > 0.1) speed = 0.1;
    if (speed < -0.1) speed = -0.1;

    drivingMotorKraken.set(speed);
  }

}
