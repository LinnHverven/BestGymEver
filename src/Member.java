import java.time.LocalDate;

public class Member {
    private String name;
    private String personnummer;
    private LocalDate latestPayment;


    public Member(String personnummer, String name, LocalDate latestPayment) {
        this.name = name;
        this.personnummer = personnummer;
        this.latestPayment = latestPayment;
    }

    public boolean isCurrentMember() {
        return latestPayment.isAfter(LocalDate.now().minusYears(1));
    }

    public String getName() {
        return name;
    }

    public String getPersonnummer() {
        return personnummer;
    }
}