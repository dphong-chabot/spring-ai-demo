### 사전 요구사항

#### ollama
```shell
brew install ollama
ollama run gemma3:4b
```

### 프로젝트 구조
```
src
├── main
│   ├── kotlin
│   │   └── com
│   │       └── chabot
│   │           └── aidemo
│   │               ├── AiConfig.kt
│   │               ├── AiDemoApplication.kt
│   │               └── HelloController.kt
│   └── resources
│       └── application.properties
└── test
    └── kotlin
        └── com
            └── chabot
                └── aidemo
                    ├── AiDemoApplicationTests.kt
                    └── TempTest.kt

12 directories, 6 files
```

### 테스트 방법
- AiDemoApplication 실행 후 TempTest.kt의 테스트 실행
- AiDemoApplication 실행 후 ai-test.http의 http 요청 실행