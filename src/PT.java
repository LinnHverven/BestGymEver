import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class PT {
    private String PTData;

    public PT(String ptData) {
        this.PTData = ptData;
    }

    public void registerWorkout(Member member) {
        try (FileWriter writer = new FileWriter(PTData, true)) {
            writer.write(LocalDate.now() + ", " + member.getName() + ", " + member.getPersonnummer() + "\n");
            System.out.println("Träning registrerad för: " + member.getName());
        } catch (IOException e) {
            System.out.println("Fel vid registrering av träning: " + e.getMessage());
        }
    }
}
