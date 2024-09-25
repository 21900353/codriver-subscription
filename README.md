
# AI 챗봇 Codriver 구독 기능

## 기능적 요구사항

1. 회원이 유료 구독을 신청하거나 해지할 수 있다.
2. 구독 신청 후 결제 단계로 진행한다.
3. 결제를 완료하면 구독된 상태가 된다.
4. 회원이 구독 해지하면 구독 해지된 상태가 된다.
5. 회원이 구독/해지 이력을 확인할 수 있다. 
6. 구독 신청 이력의 결제 유무를 확인할 수 있다.

## 비기능적 요구사항

1. 구독 신청 및 해지는 결제나 구독 처리와 관계 없이 언제나 할 수 있어야 한다. (asynchronous/event-driven)
2. 결제 파트에서 장애가 발생하더라도 자동 복구되어 회원이 결제를 진행할 수 있다. (circuit breaker, fallback)
3. 회원은 마이페이지에서 구독 이력을 확인한다. (CQRS)

## Event storming

### 이벤트 도출

![image](https://github.com/user-attachments/assets/0bc6418d-3447-44d8-98c5-f9d5ec990e23)

필수 이벤트는 총 5개로 아래와 같다.

- SubscriptionRequested: 구독 신규 신청 이벤트
- SubscriptionCancelled: 구독 해지 신청 이벤트
- PaymentMade: 결제 완료 이벤트
- Registered: 구독 등록 완료 이벤트
- Unregistered: 구독 해지 완료 이벤트

## Bounded context

1. subscription (구독 변경 신청 → 신규신청 또는 해지)
2. payment (결제)
3. management (구독 신청 처리)
4. mypage (구독 신청 상태)

## Aggregate, command, policy, actor, read model 부착

![image (1)](https://github.com/user-attachments/assets/5864080f-1a13-4f07-bef8-d5cdec09edf1)

## 1차 수정

위 모형에서 Management의 Registered/Unregistered 이벤트 발생 시 Subscription의 subProcessed(신청 처리 여부)가 false → true로 변경해야 한다. 위 모형에서 이벤트 발생 후 Subscription에서 이벤트를 받을 수 없기 때문에 각 이벤트를 받는 policy가 필요하다.

*아래는 수정된 모형이다.*

![image (2)](https://github.com/user-attachments/assets/f1970295-9046-4d64-83c9-7199b3d8cef8)

## 최종 모형

![image (3)](https://github.com/user-attachments/assets/1f353ff4-6856-4833-9456-4883dcffeb90)

## 요구사항 검증

> *기능적 요구사항*
> 
> 1. 회원이 유료 구독을 신청하거나 해지할 수 있다.
> 2. 구독 신청 후 결제 단계로 진행한다.
> 3. 결제를 완료하면 구독된 상태가 된다.
> 4. 회원이 구독 해지하면 구독 해지된 상태가 된다.
> 5. 회원이 구독/해지 이력을 확인할 수 있다. 
> 6. 구독 신청 이력의 결제 유무를 확인할 수 있다.
> 
> *비기능적 요구사항*
> 
> 1. 구독 신청, 해지, 결제는 구독 처리와 관계 없이 언제나 할 수 있어야 한다. (asynchronous/event-driven)
> 2. 결제 파트에서 장애가 발생하더라도 자동 복구되어 회원이 결제를 진행할 수 있다. (circuit breaker, fallback)
> 3. 회원은 마이페이지에서 구독 이력을 확인한다. (CQRS)
- 회원은 subscription context에서 구독을 신청하거나 해지 신청할 수 있다. (기능적 요구사항 1)
- 구독 신청 시 payment context - pay method로 request를 보낸다. (기능적 요구사항 2)
- PaymentMade 이벤트 시 management context에서 구독 등록을 하고 Registered 이벤트를 발생시킨다. 발생 시 subscription context - registerComplete policy가 이벤트를 받아 구독 상태를 변경한다. (기능적 요구사항 3)
- 구독 해지 시 Cancelled가 발생하고 이를 management context - subscriptionCancelled policy가 받아 구독 해지 처리한다. (기능적 요구사항 4)
- mypage read model을 통해 구독/해지 이력을 확인할 수 있다. (기능적 요구사항 5)
- mypage read model을 통해 결제 유무를 확인할 수 있다. (기능적 요구사항 6)
- 구독 신청 시 pay 한 뒤 결제 완료 이벤트가 publish 되고, 구독 해지 시 해지 이벤트가 publish 되어 asynchronous/event-driven을 만족한다. (비기능적 요구사항 1)
- payment context에서 circuit breaker와 fallback을 구현한다. (비기능적 요구사항 2)
- 웹상의 마이페이지에서 mypage read model을 이용하여 구독 이력을 조회한다. (비기능적 요구사항 3)

## 클라우드 배포

## 오토스케일

```bash
kubectl autoscale deployment order --cpu-percent=50 --min=1 --max=3
```

![image (4)](https://github.com/user-attachments/assets/a69e9466-219b-434e-b883-4c99735cffba)

subscription, payment, management, mypage 에 스케일 아웃 규칙을 적용하였다. Pod는 기본 1개이고, CPU 사용량이 50%를 넘어갈 경우 Pod가 최대 3개까지 증가할 수 있다.

## Circuit breaker

결제 컨테이너의 circuit breaker는 Istio를 사용해 구현하였다. 설정은 아래와 같이 하였는데, 한 번의 5xx 오류가 발생하면 그 컨테이너는 3분 간 eject 된다.

```yaml
apiVersion: networking.istio.io/v1alpha3
kind: DestinationRule
metadata:
  name: dr-payment
spec:
  host: payment
  trafficPolicy:
    loadBalancer:
      simple: ROUND_ROBIN
      localityLbSetting:
        enabled: false
    outlierDetection:
      interval: 10s
      consecutive5xxErrors: 1
      baseEjectionTime: 3m
      maxEjectionPercent: 100
```

## 셀프힐링/무정지 배포

### Liveness, readiness probe

```yaml
readinessProbe:
  httpGet:
    path: '/actuator/health'
    port: 8080
  initialDelaySeconds: 10
  timeoutSeconds: 2
  periodSeconds: 5
  failureThreshold: 10
livenessProbe:
  httpGet:
    path: '/actuator/health'
    port: 8080
  initialDelaySeconds: 120
  timeoutSeconds: 2
  periodSeconds: 5
  failureThreshold: 5

```

위와 같이 HttpGet liveness, readiness probe를 매 5초 pod 상태를 체크하도록 설정하였다. subscription, payment, management, mypage 네 개의 deployment에 probe를 생성하였다.

### Liveness unhealthy 시 재시작 확인

### 무정지 배포 확인 (readiness)

![image (5)](https://github.com/user-attachments/assets/ccbfb3ed-4d5f-4359-bc22-990b9e75203d)

Siege로 HTTP 요청을 보내는 중 subscription을 배포 하였다. HTTP 요청을 보내는 중 배포가 있었음에도 Availability 100.00%을 유지하여 무정지 배포가 되었음을 확인하였다.

## 클라우드 스토리지

구독 정보를 클라우드 스토리지에 저장하기 위해 구독 pod에 스토리지를 mount 해야 한다. 

PVC를 아래와 같이 생성하였다.

```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: azurefile
spec:
  accessModes:
  - ReadWriteMany
  storageClassName: azurefile
  resources:
    requests:
      storage: 1Gi
```

![image (6)](https://github.com/user-attachments/assets/f675b578-8db3-4dcb-ab91-43bf8d6f84f1)

그 다음 deployment.yaml을 사용해 /mnt/data 경로에 mount 시켰다. subscription pod에서 접근 가능하다.

![image (7)](https://github.com/user-attachments/assets/5f042e6b-8c90-44e4-a314-753a82b37733)

## ConfigMap

컨테이너와 환경정보를 분리하기 위해 각 컨테이너마다 ConfigMap을 사용한다. 변경될 가능성이 있는 정보는 ConfigMap을 통해 관리한다면 용이할 것이다.

![image (8)](https://github.com/user-attachments/assets/3e5909e9-33b3-4791-950b-ac66089f1b75)

![subcription의 ConfigMap](https://github.com/user-attachments/assets/8a645bd2-b855-4bee-b88f-ddd56d1a9e3a)
subcription의 ConfigMap

## 서비스 메쉬

Istio(demo 프로필)을 설치하여 서비스 메쉬를 구성하였다. Addon으로 Loki, Grafana, Prometheus, Kiala 등을 설치하였다. 

![image (10)](https://github.com/user-attachments/assets/65440df8-d416-41b3-9d8f-034ad867fa2b)

## 통합 모니터링

### Monitoring 툴

서비스 메쉬 모니터링을 위해 Kiali, Grafana, Loki를 사용하기 위해 ‘codriver-ingress’ 라는 ingress를 설정하였다.


↓ Kiali 대쉬보드
![image (11)](https://github.com/user-attachments/assets/301c9598-1c72-47db-88c7-e4963fb751b4)

↓ Prometheus로 요청 메트릭 조회
![image (12)](https://github.com/user-attachments/assets/a3c54b8f-5145-4c16-9258-dd7bce8e1951)

↓ Siege 요청 중에 Grafana로 요청이 증가함을 확인할 수 있다
![image (13)](https://github.com/user-attachments/assets/b0bda650-fe1a-4e4c-b3c2-1968d10e3139)

### Loggregation

![image (14)](https://github.com/user-attachments/assets/625a3b41-8a2b-4186-bfb6-43064a09a575)

Loki 스택을 사용하여 로그 저장소를 관리하고, Loki로부터 Grafana를 통해 시각화 한다.

위 사진은 subscription app에서 발생한 로그이다. Siege를 이용한 GET 요청과 파티션 관련 kafka 내부로그를 확인할 수 있다.
