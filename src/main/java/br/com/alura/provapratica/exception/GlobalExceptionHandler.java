package br.com.alura.provapratica.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Erro 404: Quando não encontramos um ID no banco
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Void> tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    // 2. Erro 400 de Validação: Quando o @Valid aponta campos incorretos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErroValidacao>> tratarErroValidacao(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors().stream()
                .map(DadosErroValidacao::new)
                .toList();
        return ResponseEntity.badRequest().body(erros);
    }

    // 3. Erro 400 de Regra de Negócio: Quando disparamaos RuntimeException manualmente
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> tratarErroRegraDeNegocio(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    // DTO interno para estruturar a resposta de erro de campo
    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}