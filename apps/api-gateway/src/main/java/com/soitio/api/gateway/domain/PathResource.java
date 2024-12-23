package com.soitio.api.gateway.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "path_resource", indexes = {
        @Index(name = "path", columnList = "path", unique = true)
})
public class PathResource {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    private String path;
    private String hostname;
    private int port;

}
