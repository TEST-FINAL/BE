package com.example.seesaw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // 생성/변경 시간을 자동 업데이트
public abstract class Timestamped {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate // localdatetime 시간을 나타내주는 데이터타입
    private LocalDateTime createdAt;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @LastModifiedDate // 작성 및 수정 시간
    private LocalDateTime modifiedAt;
}
