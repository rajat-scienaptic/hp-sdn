package com.sdn.model.sdn;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity
@Table(name = "sdn_activity_logs")
public class SdnActivityLogs {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @NotBlank
    @Column(name = "ip")
    private String ip;
    @NotNull @NotBlank
    @Column(name = "endpoint")
    private String endpoint;
    @NotNull @NotBlank
    @Column(name = "type")
    private String type;
    @NotNull @NotBlank
    @Column(name = "status")
    private String status;
    @NotNull @NotBlank
    @Column(name = "message")
    private String message;
    @NotNull
    @Column(name = "create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;
}
