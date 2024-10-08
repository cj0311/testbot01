# 프로젝트명

## AWS EC2 자동 배포 가이드

이 가이드는 GitHub Actions를 사용하여 AWS EC2 인스턴스에 자동으로 프로젝트를 배포하는 방법을 설명합니다.

### 사전 준비

1. AWS 계정 및 EC2 인스턴스
2. GitHub 계정 및 프로젝트 저장소
3. Binance API 키 (선택사항)

### 단계별 설정 가이드

#### 1. AWS EC2 인스턴스 준비

- EC2 인스턴스를 생성하고 SSH 접속이 가능한지 확인합니다.
- 보안 그룹에서 필요한 포트(예: 8080, 22)를 열어줍니다.

#### 2. EC2 인스턴스 초기 설정

```bash
# Java 21 설치
sudo amazon-linux-extras install java-openjdk21

# 애플리케이션 디렉토리 생성
mkdir ~/app

# systemd 서비스 파일 생성
sudo nano /etc/systemd/system/springboot-app.service
```

`springboot-app.service` 파일 내용:

```ini
[Unit]
Description=Spring Boot Application
After=network.target

[Service]
User=ec2-user
WorkingDirectory=/home/ec2-user/app
ExecStart=/usr/bin/java -jar /home/ec2-user/app/app.jar
SuccessExitStatus=143
TimeoutStopSec=10
Restart=on-failure
RestartSec=5

[Install]
WantedBy=multi-user.target
```

서비스 활성화:

```bash
sudo systemctl enable springboot-app
```

#### 3. GitHub Actions 워크플로우 설정

프로젝트 루트에 `.github/workflows/deploy.yml` 파일을 생성하고 다음 내용을 추가합니다:

```yaml
name: Deploy to AWS EC2

on:
  push:
    branches: [ main ]

jobs:
  deploy:
    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v2

    - name: Set up JDK 21
      uses: actions/setup-java@v2
      with:
        java-version: '21'
        distribution: 'adopt'

    - name: Build with Gradle
      run: ./gradlew build

    - name: Deploy to EC2
      env:
        PRIVATE_KEY: ${{ secrets.EC2_SSH_PRIVATE_KEY }}
        HOST: ${{ secrets.EC2_HOST }}
        USER: ec2-user
      run: |
        echo "$PRIVATE_KEY" > private_key && chmod 600 private_key
        scp -i private_key -o StrictHostKeyChecking=no build/libs/*.jar ${USER}@${HOST}:~/app/
        ssh -i private_key -o StrictHostKeyChecking=no ${USER}@${HOST} '
          sudo systemctl stop springboot-app
          sudo mv ~/app/*.jar ~/app/app.jar
          sudo systemctl start springboot-app
        '
```

#### 4. GitHub Secrets 설정

GitHub 저장소의 Settings > Secrets에서 다음 시크릿을 추가합니다:

- `EC2_SSH_PRIVATE_KEY`: EC2 인스턴스 접속용 private key
- `EC2_HOST`: EC2 인스턴스의 공개 IP 주소 또는 도메인 이름
- `BINANCE_API_KEY`: Binance API 키 (선택사항)
- `BINANCE_SECRET`: Binance API 시크릿 (선택사항)

#### 5. 환경 변수 설정 (EC2 인스턴스)

EC2 인스턴스에서 다음 명령을 실행하여 환경 변수를 설정합니다:

```bash
sudo nano /etc/environment
```

파일에 다음 내용을 추가:

```
BINANCE_API_KEY=your_api_key_here
BINANCE_SECRET=your_secret_here
```

#### 6. 배포

main 브랜치에 코드를 푸시하면 자동으로 배포가 시작됩니다.

#### 7. 배포 확인

- GitHub Actions 탭에서 워크플로우 실행 상태를 확인합니다.
- EC2 인스턴스에 SSH로 접속하여 애플리케이션 상태를 확인합니다:

  ```bash
  sudo systemctl status springboot-app
  ```

- 애플리케이션 로그 확인:

  ```bash
  sudo journalctl -u springboot-app -f
  ```

### 문제 해결

배포 중 문제가 발생하면 다음을 확인하세요:

1. GitHub Actions 로그
2. EC2 인스턴스의 시스템 로그
3. 애플리케이션 로그

### 보안 주의사항

- 프라이빗 키와 API 시크릿을 안전하게 관리하세요.
- EC2 인스턴스의 보안 그룹 설정을 주기적으로 검토하세요.
- 민감한 정보는 항상 암호화하여 저장하세요.





23:57:02.439 [http-nio-8080-exec-7] INFO  c.t.t.controller.WebHookController - alert message : {  "password":"비밀번호", "tradingPlatform":"BINANCE", "base":"BTC", "quote":"USDT.P", "side":"entry/buy", "amount":"0.000748", "price":"56113.8", "percent":"NaN", "leverage": "1", "margin_mode": "", "order_name":"Long @56112.5" }

- git clone  "https://github.com/jangdokang/POA.git" /root/POA

00:15:12.331 [http-nio-8080-exec-4] INFO  c.t.t.example.BinanceController - tickerDetails : {    "password":"비밀번호", "exchange":"BINANCE", "base":"BTC", "quote":"USDT.P", "side":"close/sell", "price":"56195.700000000004", "amount":"0.017829", "percent":"NaN", "margin_mode":"", "order_name":"Close entry(s) order 롱" }
