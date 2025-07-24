# Robot Drive System

Este repositório contém o código-fonte para o sistema de acionamento (drive system) de um robô FRC (FIRST Robotics Competition), desenvolvido em Java utilizando o WPILib. O projeto foca no controle de motores REV Robotics (SparkMax e SparkFlex) para movimentação e viragem do robô.

## 🚀 Visão Geral

O `RobotDriveSystem` é a base para a movimentação do nosso robô, implementando um subsistema de acionamento que gerencia os motores de condução e viragem. Ele foi projetado para ser modular e configurável, permitindo fácil adaptação a diferentes configurações de robôs e estratégias de controle.

## ✨ Funcionalidades Principais

*   **Controle de Motores REV Robotics:** Integração e configuração dos controladores de motor SparkMax (para condução) e SparkFlex (para viragem).
*   **Configurações de Motor:** Definição de modos de inatividade (IdleMode.kCoast), limites de corrente inteligentes (Smart Current Limit) e rampas de aceleração (Ramp Rate) para ambos os tipos de motores.
*   **Subsistema de Acionamento:** Implementação de um `DriveSubsystem` que encapsula a lógica de controle dos motores.
*   **Controle de Movimento:** Função `drive()` para comandar o robô com base em velocidades X e Y, calculando o ângulo de viragem e a velocidade resultante.
*   **Parada de Motores:** Função `stopMotors()` para interromper a movimentação do robô.
*   **Estrutura WPILib:** Organizado de acordo com as melhores práticas do WPILib, facilitando a integração em projetos FRC.


## 📋 Funcionalidades Detalhadas

### 1. Controle de Motores
O repositório utiliza controladores de motor da REV Robotics, especificamente o **SparkMax** e o **SparkFlex**. Esses controladores fornecem controle preciso sobre a velocidade e a direção dos motores.  
O código inclui configurações para:
- **Modos de Inatividade:** Configuração do modo `IdleMode.kCoast`.
- **Limites de Corrente:** Uso de `Smart Current Limit` para proteger os motores.
- **Rampas de Aceleração:** Configuração de `Ramp Rate` para garantir um desempenho suave.

### 2. Subsistema de Acionamento
O `DriveSubsystem` é uma classe central que encapsula toda a lógica de controle dos motores. Ele permite que o robô se mova em diferentes direções com base nas entradas do usuário (como joystick ou botões).  
- A função `drive()` calcula a velocidade e a direção do robô, utilizando a cinemática para determinar como os motores devem ser acionados.

### 3. Parada de Motores
A função `stopMotors()` é implementada para garantir que os motores possam ser parados de forma segura e controlada. Isso evita danos ao hardware e garante a segurança durante a operação.

### 4. Integração com WPILib
O projeto segue as melhores práticas do **WPILib**, a biblioteca padrão para programação de robôs FRC. Isso facilita a integração com outros subsistemas e componentes do robô, como sensores e atuadores.

## 🛠️ Tecnologias Utilizadas

*   **Java:** Linguagem de programação principal.
*   **WPILib:** Biblioteca oficial para programação de robôs FRC.
*   **REV Robotics SPARK MAX:** Controlador de motor para o motor de condução.
*   **REV Robotics SPARK Flex:** Controlador de motor para o motor de viragem.
*   **Gradle:** Sistema de automação de build.

## 📂 Estrutura do Projeto

```
RobotDriveSystem/
├── .vscode/
│   ├── launch.json             # Configurações de debug do VS Code
│   └── settings.json           # Configurações do editor e Java
├── build.gradle                # Script de build do Gradle
├── gradlew                     # Script Gradle Wrapper (Linux/macOS)
├── gradlew.bat                 # Script Gradle Wrapper (Windows)
├── settings.gradle             # Configurações do projeto Gradle
├── src/
│   └── main/
│       └── java/
│           └── frc/
│               └── robot/
│                   ├── Configs.java          # Configurações dos motores
│                   ├── Constants.java        # Constantes do robô (IDs, etc.)
│                   ├── Main.java             # Ponto de entrada principal do robô
│                   ├── Robot.java            # Classe principal do robô (TimedRobot)
│                   ├── RobotContainer.java   # Mapeamento de botões e comandos
│                   └── subsystems/
│                       └── DriveSubsystem.java # Subsistema de acionamento do robô
└── README.md                   # Este arquivo
```

## 📄 Licença

Este projeto está licenciado sob a licença WPILib BSD. Consulte o arquivo `WPILib-License.md` para mais detalhes.

---