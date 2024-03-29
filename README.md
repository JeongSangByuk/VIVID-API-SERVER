#  🙊 VIVID
> VIVID 서비스는 영상 콘텐츠 통합 관리 & 학습 툴 웹 서비스입니다.<br>
> 소프트웨어 마에스트로 13기 연수 과정에서 개발한 프로젝트입니다.
<br>

[**해당 프로젝트는 여기에서 더 자세하게 볼 수 있어요!!**](https://www.byuk.dev/resume/project/vivid)

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
- [**Spring boot와 JPA를 기반으로 REST API를 구현**](https://www.byuk.dev/d7e72957-b774-4e18-9a80-7659bae9c657)했습니다. 이 때 RESTful API Spec을 준수하여 URI와 API를 설계했습니다.
- [**DB 설계**](https://www.byuk.dev/4e4fa633-1c93-4ffa-a428-eb015af74b65), [**웹 서비스 인프라, 클라우드 아키텍처를 설계하고 구현**](https://www.byuk.dev/f1fc0115-366e-429d-8cba-7f4bf329e77d)했으며, 배포를 담당했습니다.
- [**외부 API 연동**](https://www.byuk.dev/f4b98b6e-065d-4f21-95f7-db1e64f6b0c1), [**멀티미디어 데이터 처리**](https://www.byuk.dev/8eb1ab36-d75c-404f-ac71-167b3fc415ff), [**대용량 JSON 데이터 캐싱 및 활용**](https://www.byuk.dev/cb9fb001-81af-49c8-b517-c4f185e5a01a) 등의 복잡한 백엔드 프로세스를 설계하고 구현했습니다.
- [**문서화**](https://www.byuk.dev/3d0fc291-1d88-462b-a58a-ddc748443239), [**트러블 슈팅 과정 기록 및 공유**](https://www.byuk.dev/resume/project/vivid#afb334bd4c794f9c8773110986c695f6)를 통해 협업에 편리한 프로젝트를 만들었습니다.
- API 레벨에서의 테스트 코드를 작성하여, 코드를 검증했습니다.
- 프로젝트 종료 후, [**리팩토링**](https://www.byuk.dev/a33ecd35-0261-4907-a0ed-646630cd7530)을 진행했습니다.
    - [**메소드의 역할과 책임을 분리하고, 적절하게 캡슐화하여 직관적인 코드로 개선**](https://www.byuk.dev/a33ecd35-0261-4907-a0ed-646630cd7530)했습니다.
    - [**저수준 모듈과 고수준 모듈을 분리하고 CQS 패턴을 적용**](https://www.byuk.dev/b2aeb184-a3cb-4736-bc31-8bfbe40058a3)했습니다.
    - [**Fetch Join, Batch Size 조정**](https://www.byuk.dev/ca998347-ef7c-4e88-8e24-274b1778e85d)과 [**Bulk Update 등을 활용해 N+1 문제를 해결**](https://www.byuk.dev/b01faa4a-02a9-4d9a-a93f-3e7e4dc24c4e)했습니다.

<br>

## 🎯 트러블 슈팅 & 개발 일지

**해당 프로젝트를 개발하면서, 기술적 이슈를 해결한 과정과 학습에 필요한 내용을 정리한 포스팅을 작성**했습니다.하나의 포스팅을 작성하는 것에는 정말로 많은 시간이 들었습니다. 이미 지나간 경험을 복기해보고 기술에 대해 한 뎁스 깊게 탐구하여 글로 표현한다는 것은 꽤나 막막한 일이었기 때문입니다.

포스팅을 작성할 때 **해당 이슈가 생긴 이유**와 어떤 방법으로 어떻게 해결했는지, 그리고 이 과정에서 **내가 고민한 것은 무엇**이고, **내가 기술적으로 성장한 부분은 무엇인지를 중심으로 작성**했습니다.

- [**프로젝트 리팩토링 : API 성능 최적화, Bulk Update 적용하기.**](https://www.byuk.dev/b01faa4a-02a9-4d9a-a93f-3e7e4dc24c4e)
- [**프로젝트 리팩토링 : API 성능 최적화, N+1 문제 실제로 해결하기.**](https://www.byuk.dev/ca998347-ef7c-4e88-8e24-274b1778e85d)
- [**프로젝트 리팩토링 : To Stream From For-Loop.**](https://www.byuk.dev/55b659cd-e9ce-49cb-9fbd-ad0468f28996)
- [**프로젝트 리팩토링 : 저수준 모듈 / 고수준의 모듈 분리, CQS 패턴**](https://www.byuk.dev/b2aeb184-a3cb-4736-bc31-8bfbe40058a3)
- [**프로젝트 리팩토링 : 클래스와 메소드의 적절한 분리.**](https://www.byuk.dev/a33ecd35-0261-4907-a0ed-646630cd7530)
- [**JPA Soft Delete 구현하기.**](https://www.byuk.dev/538fd0c1-a40c-4eb6-9761-fe396b64a5c4)
- [**JPA Delete is not Working, 영속성와 연관 관계를 고려했는가.**](https://www.byuk.dev/927ff674-ce08-4ca0-bfef-c73c0cb78470)
- [**JPA N+1 문제 발견과 돌파.**](https://www.byuk.dev/8a5507af-47b9-4537-a353-30db2b25cd19)
- [**Spring boot 디렉터리 패키지 구조의 선택.**](https://www.byuk.dev/3d1869ab-d4a6-450c-84c6-33588d1e33fc)
- [**JPA PK 매핑 전략, Auto Increment Key와 UUID 선택의 기준.**](https://www.byuk.dev/8a6da7b1-f2c8-4e68-8e0c-5224b9bcffdf)
- [**DTO와 Entity 간의 변환이 일어나는 Layer에 대한 고민.**](https://www.byuk.dev/73f16574-2522-4694-a504-db011b3007cc)
- [**Test Containers를 활용한 Redis & DynamoDB Test 환경 구축.**](https://www.byuk.dev/769babf6-4e8d-4d65-a03a-ea80a1d0840a)
- [**Redis Pipeline을 활용한 Redis 다중 Insert 작업 구현.**](https://www.byuk.dev/eabb6cd5-3802-4f25-a7f7-759d68c9f457)
- [**DynamoDB Read시 객체 매핑 이슈 해결.**](https://www.byuk.dev/146d3593-b869-4ed2-8d52-f2a12a572a11)
- [**상속 관계가 있는 Entity에 @Builder 적용시 이슈 해결.**](https://www.byuk.dev/8d844962-7225-4d83-94ae-b3987ccc9f7f)
- [**DynamoDB Type Convert 이슈 트러블 슈팅.**](https://www.byuk.dev/af6bec6b-4d6a-42d5-8416-2270606861f2)
- [**Spring Docs Swagger의 Content-Type 불일치 이슈.**](https://www.byuk.dev/3519b547-697e-4efb-8b58-94752afac488)
- [**@Valid가 동작하지 않을 때 이슈 트러블 슈팅.**](https://www.byuk.dev/3228f679-3620-4358-9e2d-a4075f60fe8b)

<br>

##  📋  API Docs

[API Docs](https://www.byuk.dev/d7e72957-b774-4e18-9a80-7659bae9c657)

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



