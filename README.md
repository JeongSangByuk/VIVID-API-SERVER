#  🙊 VIVID
> VIVID 서비스는 영상 콘텐츠 통합 관리 & 학습 툴 웹 서비스입니다.<br>
> 소프트웨어 마에스트로 13기 연수 과정에서 개발 진행 중인 프로젝트입니다.
<br>

##  💡  Background
1. 현재의 교육 영상은 다양한 플랫폼을 통해 공유되고 있기 때문에, **영상을 관리하는 것이 매우 어렵습니다.**
2. 영상 스트리밍 플랫폼들은 단순 시청에 목적이 맞춰져 있기 때문에, **사용자가 학습에 활용하기 불편합니다.**
<br>

##  📝  Features
1. **Zoom, Webex 등의 영상 플랫폼의 영상 콘텐츠를 손 쉽게 공유하고, 통합 관리합니다.**
2. **텍스트 필기/드로윙 필기/영상위 드로윙 필기 기능을 지원합니다.**

<br>

##  📚  Skill Stack

- **Front-end** : TypeScript, React
- **Back-end** : Java, Spring boot, JPA, QueryDSL, JUnit
- **DB** : MySQL, DynamoDB, Redis
- **Infra** : AWS Services(EC2, S3, RDS, DynamoDB, Lambda, Route53, CloudFront, MediaConvert), Docker

<br>

## 💻 개발 내용/프로젝트 중점 사항
- **Spring boot와 JPA를 기반으로 REST API를 구현**했습니다. 이 때 **RESTful API Spec을 준수하여 URI와 API를 설계**했습니다.
- **Spring boot, JPA, OOP의 동작 원리와 기본 개념**의 학습을 토대로 코드를 작성했습니다. **특정 방식과 기술에 매몰되지 않고, Trade-off를 고려하여 적합한 방식을 찾아 적용**하기 위해 노력했습니다.
- 전반적인 Backend 사이드의 **웹 서비스 인프라, 클라우드 아키텍처를 설계하고 구현** 했으며, 배포 및 운영을 담당했습니다.
- **MySQL을 기반으로 RDBMS 스키마를 설계하고 운영**했으며, **JSON 타입 데이터 저장을 위한 DynamoDB와 Cache 스토리지 용도의 Redis를 활용한 비즈니스 로직을 구현**했습니다.
- **Spring Web 계층별 책임과 역할의 분리를 위한 프로젝트 구조를 설계**했습니다.
- **클린 코드, 문서화, 트러블 슈팅 과정 기록** 통해 협업에 편리한 프로젝트를 만들었습니다.

<br>

## 🎯 트러블 슈팅 & 개발 일지

[**해당 프로젝트를 개발하면서, 기술적 이슈를 해결한 과정과 학습에 필요한 내용을 정리한 포스팅을 작성**](https://velog.io/@jsb100800/series/Srping-Boot-Development)했습니다.하나의 포스팅을 작성하는 것에는 정말로 많은 시간이 들었습니다. 이미 지나간 경험을 복기해보고 기술에 대해 한 뎁스 깊게 탐구하여 글로 표현한다는 것은 꽤나 막막한 일이었기 때문입니다.

포스팅을 작성할 때 **해당 이슈가 생긴 이유**와 어떤 방법으로 어떻게 해결했는지, 그리고 이 과정에서 **내가 고민한 것은 무엇**이고, **내가 기술적으로 성장한 부분은 무엇인지를 중심으로 작성**했습니다.

- [**JPA Soft Delete 구현하기.**](https://velog.io/@jsb100800/Spring-boot-JPA-Soft-Delete-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0)
- [**JPA Delete is not Working, 영속성와 연관 관계를 고려했는가.**](https://velog.io/@jsb100800/spring-12)
- [**JPA N+1 문제 발견과 돌파.**](https://velog.io/@jsb100800/Spring-boot-JPA-N1)
- [**Spring boot 디렉터리 패키지 구조의 선택.**](https://velog.io/@jsb100800/Spring-boot-directory-package)
- [**JPA PK 매핑 전략, Auto Increment Key와 UUID 선택의 기준.**](https://velog.io/@jsb100800/Spring-boot-JPA-PK-MAPPING)
- [**DTO와 Entity 간의 변환이 일어나는 Layer에 대한 고민.**](https://velog.io/@jsb100800/Spring-boot-DTO-Entity-%EA%B0%84-%EB%B3%80%ED%99%98-%EC%96%B4%EB%8A%90-Layer%EC%97%90%EC%84%9C-%ED%95%98%EB%8A%94%EA%B2%8C-%EC%A2%8B%EC%9D%84%EA%B9%8C)
- [**Test Containers를 활용한 Redis & DynamoDB Test 환경 구축.**](https://velog.io/@jsb100800/Spring-boot-Test)
- [**Redis Pipeline을 활용한 Redis 다중 Insert 작업 구현.**](https://velog.io/@jsb100800/redis-pipelining)
- [**DynamoDB Read시 객체 매핑 이슈 해결.**](https://velog.io/@jsb100800/Spring-boot-DynamoDB-Read)
- [**상속 관계가 있는 Entity에 @Builder 적용시 이슈 해결.**](https://velog.io/@jsb100800/super-builder)
- [**DynamoDB Type Convert 이슈 트러블 슈팅.**](https://velog.io/@jsb100800/spring123)
- [**Spring Docs Swagger의 Content-Type 불일치 이슈.**](https://velog.io/@jsb100800/Spring-boot-Swagger-issue1)
- [**@Valid가 동작하지 않을 때 이슈 트러블 슈팅.**](https://velog.io/@jsb100800/Spring-boot-Valid-is-not-working-Valid%EA%B0%80-%EB%8F%99%EC%9E%91%ED%95%98%EC%A7%80-%EC%95%8A%EC%9D%84-%EB%95%8C)

<br>

##  📋  API Docs

[API Docs](https://cooing-delivery-882.notion.site/VIVID-API-DOCS-2a2e38bedafe407e98387a7421d25429)

[API Swagger](https://api.dev.edu-vivid.com/swagger-ui/index.html#/)

<br>

##  🛠️  Architecture

<br>

<details>
<summary><b>Version 2 (개발 중 개선 버전)</b></summary>

![architecture-시스템구성도 v2 drawio (1)](https://user-images.githubusercontent.com/64072741/200391094-9b8fa3d9-9d49-4e77-905b-6b4f287fe0c5.png)

- AWS ALB(Application Load Balancer)을 이용하여 부하분산을 관리했습니다.
- **Nginx 웹서버를 사용하지 않고, S3와 CDN을 활용하여 서버리스하게 정적 콘텐츠를 호스팅하는 방식으로 개선시켰습니다.**
해당 방식을 활용하면, 웹서버 인스턴스를 따로 유지보수할 필요가 없어집니다. 
또한 가격 측면에서도 훨씬 더 저렴해진다는 이점이 있습니다.
- **Redis 서버 각각의 명시적으로 나타나도록 아키텍처에 배치했으며, 이를 하나의 subnet에 배치했습니다.** 각각의 Redis 서버의 역할은 다음과 같습니다.
    - **Cache Server** : 특정 value들을 캐싱합니다.
    - **Session Server** : 2개의 인스턴스의 Session을 관리합니다.
    - **API Cache Server** : API 호출을 캐싱합니다.
- 현재 프로젝트 규모상 RDS는 하나의 인스턴스만 있으면 충분하다고 생각했기 때문에, RDS는 하나만 배치하고, 이를 미러링하는 RDS를 추가 배치했습니다.
- Lambda를 활용한 DynamoDB CRUD API가 존재합니다.

</details>

<br>

<details>

<summary><b>Version 1 (초기 설계 버전)</b></summary>

> 해당 버전은 Version 2에서 개선 및 수정됐습니다.

![architecture-시스템구성도 drawio](https://user-images.githubusercontent.com/64072741/200391087-092c9f99-a456-48d9-9d78-d41dec6ee6d7.png)

- AWS ALB(Application Load Balancer)을 이용하여 부하분산을 관리했습니다.
- Web Server로 NginX, WAS로 Spring boot Server을 이용했습니다.
각각은 EC2 하나씩에 관리되며 총 4개의 EC2 인스턴스가 AutoScaling 되도록 설계했습니다.
- DB로서 Cache 역할을 하는 Redis와 RDS를 배치를 했습니다. 각각의 Redis와 RDS는 채널링 될 수 있도록 설계했습니다. AZ1와 AZ2의 Redis는 다른 용도의 데이터를 캐싱하도록 설계했습니다.
- Lambda를 활용한 DynamoDB CRUD API가 존재합니다.
- AWS CloudFront(CDN)를 활용해 콘텐츠 전송 성능을 향상 시켰습니다.

</details>

<br>

##  📈  ERD

<br>

<details>
<summary><b>ERD</b></summary>

![ggg](https://user-images.githubusercontent.com/64072741/200392125-afe96a91-32d1-4b6d-9224-a59bc25f5cd4.png)

</details>

<br>

##  ✍️  Process Docs

<br>

<details>
<summary><b>User Login Process</b></summary>

<br>

![image](https://user-images.githubusercontent.com/64072741/200394236-cbc86a4e-79b0-4fa4-9841-c9b127fb2c57.png)

### 최초 구글 로그인 시 

- redirect url을 통해 클라이언트 사이드에서 구글 로그인을 시도합니다.
- 로그인 성공 시, 서버의 successful 핸들러가 응답을 받습니다. 이에 따라 회원가입된 유저가 아닌 경우, 회원가입을 진행합니다.
- 로그인 성공 시, refresh token을 redis 세션 서버에 저장하고 클라이언트에 access token을 url 파라미터에 실어나서 반환합니다.

<br>

### 정상 API 호출 시

- header에 access token을 정상적으로 포함하고, 만료되지 않고 유효한 access token인 경우 정상적으로 api가 동작합니다.

<br>

### Access Token 재발급

- access token이 만료됐다면, redis 세션 서버에서 refresh token을 확인합니다.
- refresh token이 존재하고 유효하다면, access token을 재발급해줍니다.

</details>

<br>

<details>
<summary><b>Video Upload Process</b></summary>

<br>

![image (1)](https://user-images.githubusercontent.com/64072741/200394244-8b58ae20-8563-4a29-a490-35fde6961fb2.png)

### Video 업로드

- Raw Video Storage에 video 파일 원본이 업로드 됩니다.
- Raw Video Storage 업로드 완료 된 후, 람다 함수를 통해 MediaConvert 트랜스 코딩 작업이 실행됩니다.
- MediaConvert 트랜스 코딩 작업이 완료된 후, Service Video Storage에 트랜스 코딩된 video 파일이 업로드 됩니다.
- Service Video Storage에 업로드 완료된 후, 람다 함수를 통해 서버에 트랜스 코딩 상태를 successful로 바꾸는 API를 호출합니다.

</details>

<br>

<details>
<summary><b>Text Memo State Get Process</b></summary>

<br>

![image (2)](https://user-images.githubusercontent.com/64072741/200394247-5f0084d4-ab7b-47bf-b8d0-ca6c3bffba6b.png)

### Text Memo State Latest Get, 캐시에 존재할 경우

- redis에서 latest를 get합니다.

<br>

### Text Memo State Latest Get, 캐시에 존재하지 않을 경우
    
- latest가 redis에 존재하지 않을 경우, DynamoDB에서 Get해옵니다.

<br>

### Text Memo State History Get

- History는 DynamoDB에서만 Get 해옵니다.


</details>

<br>

<details>
<summary><b>Webex API Process</b></summary>

<br>

![image (3)](https://user-images.githubusercontent.com/64072741/200394253-18542d8f-b107-48bb-af89-88b38d3bceab.png)

### Webex 로그인, Webex Access Token Get

- Webex 계정 연동을 위해서는 Webex Oauth 로그인이 필요합니다.
- Webex 로그인을 통해서 code를 얻습니다.
- 해당 code를 이용해서 Webex Access Token을 얻고, 이를 DB에 저장합니다.

<br>

### Webex 녹화본 리스트 Get, Access Token이 존재할 경우
    
- 로그인한 유저가 이전의 Webex 로그인을 통해 Access token을 갖고 있을 경우,
서버에서 외부 Webex api 호출을 통해 녹화본 데이터를 얻을 수 있습니다.

<br>

### Webex 녹화본 업로드

- 녹화본 리스트에서 recording id를 이용해서 다운로드 링크를 get하는 외부 Webex api를 호출합니다.
- 다운로드 링크를 통해서 VIVID 스토리지에 영상을 업로드하고, Video 객체를 생성합니다.
- 생성된 video id를 return 합니다.

</details>



