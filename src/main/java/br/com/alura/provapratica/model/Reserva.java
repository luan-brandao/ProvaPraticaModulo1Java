package br.com.alura.provapratica.model;

import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;

@Entity
public class Reserva {
    @Id @GeneratedValue (strategy = GenerationType.AUTO)
    private Long reservaId;
    @ManyToOne (fetch = FetchType.LAZY)
    private Usuario usuario ;
    @ManyToOne  (fetch = FetchType.LAZY)
    private Sala sala;
    @Column(name = "Status_da_reserva")@Enumerated(EnumType.STRING)
    private StatusReserva status;
    @Column(name = "horarioInicio" , nullable = false )
    private LocalDateTime horarioInicio;
    @Column(name = "horarioFim" , nullable = false )
    private LocalDateTime horarioFinal;


    public Reserva(Usuario usuario, Sala sala, StatusReserva status, LocalDateTime horarioInicio, LocalDateTime horarioFinal , Long reservaId) {

        if (sala.isStatusSala() && horarioInicio.isBefore(horarioFinal)){
            this.usuario = usuario;
            this.sala = sala;
            this.status = status;
            this.horarioInicio = horarioInicio;
            this.horarioFinal = horarioFinal;
            this.reservaId = reservaId;
        }
        else {
            throw new RuntimeException("Verifique o status da sala ou o horario de reserva");
        }
    }
    public Reserva(){

    }


    public boolean conflitoReserva(Reserva outraReserva) {
        // 1. Regra de Estados e Cancelamento:
        // Se a minha reserva ou a outra estiverem canceladas, não há conflito.
        if (this.status == StatusReserva.CANCELADA || outraReserva.getStatus() == StatusReserva.CANCELADA) {
            return false;
        }

        // 2. Cenários seguros (intervalo semiaberto)
        boolean outraTerminaAntesOuJunto = outraReserva.getHorarioFinal().isBefore(this.horarioInicio)
                || outraReserva.getHorarioFinal().isEqual(this.horarioInicio);

        boolean minhaTerminaAntesOuJunto = this.horarioFinal.isBefore(outraReserva.getHorarioInicio())
                || this.horarioFinal.isEqual(outraReserva.getHorarioInicio());

        // 3. Verificação final
        if (outraTerminaAntesOuJunto || minhaTerminaAntesOuJunto) {
            return false; // Caminho livre, não tem conflito!
        } else {
            return true;  // Os horários se sobrepõem, tem conflito!
        }
    }

    public Long getReservaId() {
        return reservaId;
    }

    public void setReservaId(Long reservaId) {
        this.reservaId = reservaId;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setHorarioFinal(LocalDateTime horarioFinal) {
        this.horarioFinal = horarioFinal;
    }

    public void setHorarioInicio(LocalDateTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public void setStatus(StatusReserva status) {
        this.status = status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Sala getSala() {
        return sala;
    }

    public StatusReserva getStatus() {
        return status;
    }

    public LocalDateTime getHorarioInicio() {
        return horarioInicio;
    }

    public LocalDateTime getHorarioFinal() {
        return horarioFinal;
    }


}
