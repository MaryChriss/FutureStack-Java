package br.com.fiap.sprint3.users;

public record Token(
    String token, 
    String type,
    String email
) {}
