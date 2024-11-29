package es.tfg.codeguard.model.dto;

public record UserPrivilegesDTO(String username, boolean tester, boolean creator) {
}
