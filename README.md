🌄 등산 도우미 사이트 산 스토리! 🌄
=
![image](https://github.com/DPODW/redmeTest/assets/110981825/c453c4f1-0b7f-4bf5-a0ee-2195516cf080)



* 현재 AWS 프리티어 종료로 인해 배포가 중단되었습니다
* 배포 중단으로 인한 GitHub Action CI/CD 또한 비활성화 되었습니다.
<br>

## 프로젝트 소개 ⚙️
* 산 스토리는 특정 산을 검색하면 해당 산에 대한 정보, 날씨, 미세먼지, 등산 팁을 제공합니다.
* 산에 대해 후기를 남길수 있으며, 추천 비추천을 통해 후기를 평가할 수 있습니다

<br>

## 개발 인원 👨‍💻
<div align="center">

개인 프로젝트
| **문태진** | 
| :------: | 
| [<img src="https://avatars.githubusercontent.com/u/110981825?s=400&u=90f6ff0a633494f94916237dc912e9eedb225e34&v=4" height=150 width=150> <br/> @TaeJin Moon](https://github.com/DPODW) |

</div>

<br>

## 1. 개발 및 배포 정보 🖥️ 
### 개발 환경
* Language : JAVA
* Front-End : HTML/CSS , ThymeLeaf , AJAX , jQuery , JavaScript , BootStrap
* Back-End : SpringBoot
* ORM : JPA
* DB : My Sql
* IDE : Intelli J

<br>

### 배포 환경
* Server : AWS EC2 (Ubuntu , 프리티어)
* CI/CD 자동화 Tool : GitHub Action
* Docker 로 Container 를 생성하여 배포합니다.
* 아래 사진은 일련의 배포 과정을 도식화한 것 입니다.
  <br>
![산스토리 배포과정](https://github.com/DPODW/Mountain_Story/assets/110981825/d6d5437b-8505-4c45-b155-005b63da65d8)


<br>

* 정리해야할 정보들은 Notion 에 기록해두었습니다.

## 2. 프로젝트 구조 📂
* 디렉토리 수준으로 구조를 설정하였습니다. (내부 class 는 표시되지 않습니다)

```
─project
    ├─config
    │  ├─auth
    │  │  └─session
    │  ├─exception
    │  ├─querydsl
    │  └─security
    ├─controller
    │  └─paging
    ├─dto
    │  ├─mountain
    │  │  ├─mountainimg
    │  │  ├─mountaininfo
    │  │  ├─mountainregion
    │  │  └─mountainWeather
    │  ├─review
    │  └─user
    ├─entity
    │  ├─region
    │  └─user
    ├─repository
    │  ├─member
    │  ├─region
    │  │  └─impl
    │  └─review
    │      └─impl
    └─service
        ├─member
        │  └─impl
        ├─mountain
        │  ├─mountaininfo
        │  │  └─impl
        │  ├─mountainsmog
        │  │  └─impl
        │  └─mountainweather
        │      └─impl
        └─review
            └─impl


├─static
│  ├─assets
│  │  ├─ajax
│  │  ├─css
│  │  │  └─images
│  │  │      └─ie
│  │  ├─js
│  │  ├─sass
│  │  │  └─libs
│  │  └─webfonts
│  └─images
│      └─weather
└─templates
    ├─error
    └─main
```
## 3. 산 스토리 ERD 💾
* member table : 회원정보가 저장되는곳 입니다.
* reveiw table : 후기정보가 저장되는곳 입니다.
* review_rating_history table : 후기를 평가한 회원의 기록이 저장되는곳 입니다.
  * (is_reviewd = true 이면 '추천' , is_reviewd = false 이면 '비추천' 이다)

* location_info table = 대한민국 지역과, 지역에 따른 좌표값이 저장되어있는 곳입니다.
  * (새롭게 데이터가 저장되는곳 아님)

 ![image](https://github.com/DPODW/redmeTest/assets/110981825/3dc8f740-6f15-4682-a3e3-2803d639d0ad)


<br>


## 4. API 출처 📄
* 산 스토리는 공공 API 를 활용하여 산 관련 데이터를 가져옵니다.
  * 대한민국 산 정보 데이터 : 산림청
    * https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15058662
 
  * 지역별 날씨 정보 데이터 : 기상청
    * https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15084084

  * 지역별 미세먼지 정보 데이터 : 한국환경공단
    * https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15073855

<br>

## 5. 페이지별 기능 설명 🖨

### [메인 페이지] 🏠
* 초기화면 입니다.
* 좌측 상단의 OAuth2 를 활용한 SNS 로그인 기능이 있습니다.
  * 로그아웃시에는 각각의 SNS 로그인 서버로 로그아웃 요청을 보내고 (네이버는 연결 끊기) ->
  * Spring Security Logout 기능으로 최종 로그아웃 합니다.
 
* 좌측 하단에는 추천을 많이 받은 산 후기 Top7 랭킹 기능이 있습니다.
* 중앙 입력 폼에 산 이름을 입력하여 원하는 산의 정보를 받아볼 수 있습니다.
![산스토리 메인화면](https://github.com/DPODW/redmeTest/assets/110981825/fff6f3d6-c0ad-4991-b53e-79b0e7faf3f9)


* 후기 기능은 BootStrap 의 Modal 기능을 활용하여 구현하였습니다.
* 추천 , 비추천 기능은 로그인된 회원만 가능하며 삭제 기능은 작성자만 가능합니다.
    * 후기 평가 (추천 , 비추천) 는 1회만 가능합니다. 
    * 해당 검증 기능들은 -> AJAX 비동기 처리로 구현하였습니다.
      
<br>


* 후기 기능은 아래와 같이 작동합니다.
  
https://github.com/DPODW/redmeTest/assets/110981825/553e28ab-4d63-4b94-8f0b-21deca3f93a3


<br>

* SNS 로그인이 완료되면, 아래와 같이 프로필이 변경됩니다.

![로그인 성공](https://github.com/DPODW/redmeTest/assets/110981825/f664b65a-4ffd-4b76-8e10-da95003b0184)


<br>
<hr>
<br>

### [회원 마이페이지 & 게시글 페이지 ] 🧑
* 로그인 완료시 접근할 수 있는 페이지 입니다.
* 마이페이지에선 가입 정보를 확인 및 탈퇴가 가능합니다.
  * 회원 탈퇴시 -> JPA 의 CASCADE 를 활용하여 작성한 후기 또한 전부 삭제됩니다.
* 아래는 마이페이지 사진입니다.

![프로필](https://github.com/DPODW/redmeTest/assets/110981825/8a47e3c4-5bd5-43ff-9a9b-90d400d8b7d0)


* 게시글 페이지에선 작성한 게시글 , 좋아요 누른 게시글 , 싫어요 누른 게시글을 확인할 수 있습니다.
* 전부 JPA 를 이용한 페이징 처리가 되어있으며, 최대 10개 까지의 게시글이 보여집니다.
* 아래의 영상은 작성한 게시글 -> 좋아요 누른 게시글 -> 싫어요 누른 게시글 순서의 동작 과정 입니다.


https://github.com/DPODW/redmeTest/assets/110981825/d1d26c06-f203-45d8-8b8d-be98da9562b1


<br>
<hr>
<br>


### [산 검색 후 페이지] ⛰️
* 특정 산을 검색하면, 로딩 화면과 함께 검색된 이름의 산을 모두 가져옵니다.
* 같은 이름의 산이 존재할 경우 -> 최대 15개 까지의 같은 이름의 산을 가져옵니다.
* 우측 사진 또한 산림청에서 제공하는 사진으로, 만약 제공되는 사진이 없을시 no image 사진으로 공통 처리하였습니다.
* 회원 권한이 없어도 검색이 가능합니다.
  
<br>

* 아래 영상은 특정 산을 검색하는 과정입니다.


https://github.com/DPODW/redmeTest/assets/110981825/1b4ea0c0-e43a-4beb-ad10-51600456ad08


<br>
<hr>
<br>



### [ 특정 산 상세 검색 페이지 ] 🌄
* [산 검색 후 페이지] 에서 '날씨 확인하기' 버튼을 눌렀을때 실행되는 페이지 입니다.
* 해당 산에 대한 날씨 , 미세먼지 , 등산 팁을 제공하며, 하단에는 후기 작성 폼과, 후기 목록이 있습니다.
* 후기는 JPA 를 활용한 페이징 처리가 되어있으며, 10개씩 보여집니다.
* 후기 작성 규칙을 AJAX 비동기 처리로 구현하였습니다.

<br>

* 아래 영상은 특정 산을 상세 검색 (날씨 확인하기) 하는 과정입니다.


https://github.com/DPODW/redmeTest/assets/110981825/0f05e32a-5440-43fc-8b1f-67e8c465bdc7

<br>

#### 날씨 데이터에 대한 추가 정보 🌦️
* 날씨 데이터 최신화는 현재 시간으로부터 3시간 마다 이루어집니다.
* 날씨 정보를 제공하는 기상청 API 는 날씨 정보를 얻고자 하는 위치의 좌표값을 요청합니다.
  * 그렇기 때문에, DB 에 좌표값과 일치하는 위치 정보 (EX 울산광역시 울주군. . . ) 를 모두 저장해두어야 합니다.
  * 산림청 API 에서 산 위치를 받고 -> 해당 위치에 따른 좌표값을 DB 에서 조회 -> 해당 좌표값으로 산 위치 날씨 조회!
* 아래는 DB에 저장되어있는 좌표 <=> 위치 데이터의 일부분 입니다.

![image](https://github.com/DPODW/redmeTest/assets/110981825/cb2cb29e-1464-45a6-92e5-36cb5da3a22e)


<br>


#### 등산 팁 기능에 대한 추가 정보 🥾
* 등산 팁 기능은 날씨에 따른 문장들을 조합해서 만들어집니다.
* Thyme Leaf 로 날씨 상태에 대한 조건을 걸고, 날씨 상태에 따른 문장들은 전부 message.properties 에 정의되어있습니다.
* 아래는 등산 팁 기능의 Thyme Leaf 코드 입니다.
  
![image](https://github.com/DPODW/redmeTest/assets/110981825/b1ab0156-05a3-4bf9-afa1-06a6e810d21e)


<br>

## 6. 산 스토리 핵심 기능 동작 과정 👾
* 아래는 산 스토리의 핵심 기능인 산 검색 ~ 날씨 , 미세먼지 등 추가 정보를 얻어오는 로직의 과정을 정리한 문서 입니다.
* [산 스토리 핵심 기능 동작 과정](https://github.com/DPODW/Mountain_Story/wiki/산-검색부터-날씨-정보-조회까지)
