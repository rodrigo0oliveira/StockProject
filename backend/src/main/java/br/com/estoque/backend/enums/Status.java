package br.com.estoque.backend.enums;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public enum Status {

    AVAILABLE("DISPONIVEL"),
    NOTAVAILABLE("NAODISPONIVEL");

    @NotNull
    private String name;

    Status(String name){
        this.name = name;
    }
}
