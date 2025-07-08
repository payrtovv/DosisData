import javax.swing.JOptionPane;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class Recordatorio {
    public static void programarRecordatorioFijo(Medicamento medicamento, LocalTime hora) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime proxima = LocalDateTime.of(LocalDate.now(), hora);

        if (proxima.isBefore(ahora)) {
            proxima = proxima.plusDays(1);
        }

        long delay = Duration.between(ahora, proxima).toMillis();
        long periodo = TimeUnit.DAYS.toMillis(1); // diario

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mostrarNotificacion(medicamento);
            }
        }, delay, periodo);
    }

    public static void mostrarNotificacion(Medicamento medicamento) {
        String mensaje = "Es hora de tomar el medicamento:\n\n"
                + "Nombre: " + medicamento.getNombre() + "\n"
                + "Dosis: " + medicamento.getDosis();

        JOptionPane.showMessageDialog(null, mensaje, "Recordatorio", JOptionPane.INFORMATION_MESSAGE);
    }
}

