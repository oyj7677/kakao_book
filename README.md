# 📚 Kakao Book Search App

Android 애플리케이션으로 Kakao API를 활용한 도서 검색 및 북마크 기능을 제공합니다.

## 📱 주요 기능

- 📖 **도서 검색**: Kakao API를 통한 실시간 도서 검색
- 🔖 **북마크**: 관심 도서 저장 및 관리
- 📄 **페이징**: 효율적인 대용량 데이터 처리
- 🔍 **정렬**: 정확도, 최신순 등 다양한 정렬 기준

## 🛠 기술 스택

### **Architecture & Pattern**
- **Clean Architecture**: Domain, Data, Presentation 레이어 분리
- **MVVM Pattern**: ViewModel과 StateFlow를 활용한 상태 관리
- **Repository Pattern**: 데이터 소스 추상화

### **UI Framework**
- **Jetpack Compose**: 선언적 UI 프레임워크
- **Material Design 3**: Google의 최신 디자인 시스템
- **Navigation Compose**: 화면 간 네비게이션 관리

### **Dependencies**
```kotlin
// Core
- Kotlin 2.2.10
- Android SDK 36
- Compose BOM 2025.08.01

// DI & Architecture
- Dagger Hilt 2.57.1
- Lifecycle ViewModel Compose 2.9.3
- StateFlow & Flow

// Network & Data
- Retrofit 3.0.0
- Kotlinx Serialization 1.9.0
- Room Database 2.7.2
- Paging 3 Compose 3.4.0-alpha04

// Image Loading
- Coil 2.7.0
```

## 🏗 프로젝트 구조

```
kakao_book/
├── app/                           # Presentation Layer
│   └── src/main/java/com/oyj/kakaobook/
│       ├── ui/
│       │   ├── search/           # 검색 화면
│       │   ├── bookmark/         # 북마크 화면  
│       │   ├── detail/          # 상세 화면
│       │   ├── component/       # 공통 UI 컴포넌트
│       │   └── theme/           # 테마 설정
│       ├── navigation/          # 네비게이션 관리
│       ├── data/               # Presentation 데이터 클래스
│       ├── mapper/             # 데이터 변환
│       └── util/               # 유틸리티
├── domain/                       # Domain Layer
│   └── src/main/java/com/oyj/domain/
│       ├── entity/             # 도메인 엔티티
│       ├── repository/         # Repository 인터페이스
│       └── usecase/            # 비즈니스 로직
├── data/                        # Data Layer
│   └── src/main/kotlin/com/oyj/data/
│       ├── network/            # API 통신
│       ├── database/           # Room 데이터베이스
│       ├── source/             # 데이터 소스 (Remote/Local)
│       ├── dto/                # 네트워크 응답 모델
│       ├── mapper/             # 데이터 변환
│       └── impl/               # Repository 구현체
└── di/                          # Dependency Injection
```

## 🔧 빌드 방법

### 1. 환경 설정

**필수 요구사항:**
- Android Studio Koala | 2024.1.1 이상
- JDK 11 이상
- Android SDK API Level 24-36
- Kotlin 2.2.10

### 2. API 키 설정

프로젝트 루트에 `local.properties` 파일을 생성하고 Kakao API 키를 추가하세요:

```properties
KAKAO_API_KEY="KakaoAK your_api_key_here"
```

> Kakao API 키는 [Kakao Developers](https://developers.kakao.com/)에서 발급받을 수 있습니다.

### 3. 개발 환경 실행

Android Studio에서:
1. 프로젝트 열기
2. Gradle Sync 실행
3. 에뮬레이터 또는 실제 기기에서 실행

## 🎯 주요 구현 포인트

### 1. **Clean Architecture 적용**
- **Domain Layer**: 비즈니스 로직과 엔티티 정의
- **Data Layer**: 외부 데이터 소스 관리 (API, DB)
- **Presentation Layer**: UI 관련 로직과 상태 관리

### 2. **Paging 3 Integration**
```kotlin
// 효율적인 대용량 데이터 로딩
@GET("v3/search/book")
suspend fun searchBooks(
    @Query("query") query: String,
    @Query("page") page: Int,
    @Query("size") size: Int = 20
): BookDto
```

### 3. **상태 관리 최적화**
```kotlin
// StateFlow와 combine을 활용한 반응형 상태 관리
val searchUiState: StateFlow<SearchUiState> = combine(
    _query,
    _bookmarkedIsbnSet,
    _sortCriteria
) { query, bookmarks, criteria ->
    SearchUiState(query, bookmarks, criteria)
}.stateIn(viewModelScope, SharingStarted.Lazily, SearchUiState())
```

### 4. **Room Database 활용**
```kotlin
// 북마크 데이터 로컬 저장
@Entity(tableName = "bookmark")
data class BookmarkEntity(
    @PrimaryKey val isbn: String,
    val title: String,
    val author: String,
    // ... 기타 필드
)
```

### 5. **Type-Safe Navigation**
```kotlin
// Compose Navigation with Kotlin Serialization
@Serializable
sealed class Screen {
    @Serializable
    data object Search : Screen()
    
    @Serializable
    data object Bookmark : Screen()
    
    @Serializable
    data class BookDetail(val book: Book) : Screen()
}
```

## 📦 모듈별 의존성

### App Module
- Domain, DI 모듈 의존
- UI, Navigation, Presentation 로직 담당

### Data Module  
- Domain 모듈 의존
- Repository 구현, API 통신, 로컬 DB 관리

### Domain Module
- 순수 Kotlin 모듈 (Android 의존성 없음)
- 비즈니스 로직, 엔티티, Repository 인터페이스

### DI Module
- Dagger Hilt 설정
- 의존성 주입 구성

---

**개발자**: 오영재
**개발 기간**: 2025.09.08 - 2025.09.14
**문의**: oyj7677@gmail.com
