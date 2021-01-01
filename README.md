# spring-kafka-enum

## kafka server 설치
간단히 테스트로 사용할 kafka를 설치합니다.
```
brew install zookeeper
brew install kafka

brew services start zookeeper
brew services start kafka
```
기본 설정으로는 host와 port는 localhost:9092 입니다.

## build.gradle - spring-boot-starter-json 추가
spring boot 기본 starter에는 jackson이 없습니다. dependencies에 spring-boot-starter-json를 추가합니다. 참고로 spring-boot-starter-web은 기본적으로 가지고 있습니다.
```
implementation 'org.springframework.boot:spring-boot-starter-json'
```

편의상 lombok을 사용합니다. dependencies에 추가합니다.
```
compileOnly 'org.projectlombok:lombok'
annotationProcessor 'org.projectlombok:lombok'
```

## JacksonConfig - enable READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE
READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE 설정은 기본적으로 비활성화 되어 있습니다. 이 기능을 켜줍니다.

application.yaml 설정을 다음과 같이 추가합니다.
```
spring:
  jackson:
    deserialization:
      READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE: true
```

Java Config - @Configuration으로도 적용할 수 있습니다. 다만 이 방법을 사용하면 application.yaml 설정과는 별개의 설정이 되니 주의합니다.
```
@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);
    }
}
```


## kafka listener container factory - message converter에 StringJsonMessageConverter(objectMapper) 
```
@Bean
KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setMessageConverter(new StringJsonMessageConverter(objectMapper));
    factory.setConsumerFactory(consumerFactory());
    return factory;
}
```
message converter 세팅시 StringJsonMessageConverter를 넣어주는데 objectMapper를 넣어줍니다. objectMapper를 넣어주지 않으면 READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE 설정이 안먹습니다.

## @JsonEnumDefaultValue
enum을 선언할 때 알 수 없는 값이 들어 올 때 사용할 값을 지정하려면 @JsonEnumDefaultValue 애노테이션을 사용하면 됩니다.
```
public enum TestType {
    ALPHA,
    BETA,
    @JsonEnumDefaultValue UNKNOWN;
}
```