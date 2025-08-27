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