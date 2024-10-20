import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BestGymEver {

    private List<Member> members = new ArrayList<>(); //Plockar in informationen som finns om medlemmarna.
    private PT pt; //Skickar ut informationen om när medlemmarna tränar till PT:n

    public BestGymEver(String MemberData, String ptDataFile) {
        readMembers (MemberData);
        pt = new PT(ptDataFile);
    }

    // Läser in medlemmar från filen med try-with-resources
    private void readMembers(String MemberData) {
        try (BufferedReader reader = new BufferedReader(new FileReader(MemberData))) {
            String rad;
            while ((rad = reader.readLine()) != null) {
                String[] membersInfo = rad.split(", ");
                String personnummer = membersInfo[0];
                String name = membersInfo[1];

                String datumRad = reader.readLine();
                LocalDate senasteBetalning = LocalDate.parse(datumRad);

                Member member = new Member(personnummer, name, senasteBetalning);
                members.add(member); //Lägger till medlemmen på listan till PT:n
            }
        } catch (IOException e) {
            System.out.println("Fel vid läsning av medlemsfil: " + e.getMessage());
        }
    }

    // Söker efter en medlem baserat på namn eller personnummer och ignorera stora och små bokstäver.
    public Member findMember (String findmember) {
        for (Member member : members) {
            if (member.getName().equalsIgnoreCase(findmember) || member.getPersonnummer().equals(findmember)) {
                return member;
            }
        }
        return null;
    }

    public void startSearch() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Ange namn eller personnummer på den eventuella medlemmen eller ENTER för att avsluta: ");
            String searchMmeber = scanner.nextLine();

            if (searchMmeber.isEmpty()) {
                System.out.println("Sökning klar.");
                break;
            }

            Member medlem = findMember (searchMmeber);

            if (medlem == null) {
                System.out.println("Personen finns inte i systemet och får inte komma in och träna.");
            } else if (medlem.isCurrentMember()) {
                System.out.println(medlem.getName() + " är en nuvarande medlem och är välkommen att träna.");
                pt.registerWorkout(medlem); // Registrera träning
            } else {
                System.out.println(medlem.getName() + " är en före detta medlem och får inte komma in och träna.");
            }
        }
    }

    public static void main(String[] args) {
        BestGymEver gym = new BestGymEver("src/MemberData.txt", "PTData.txt");
        gym.startSearch();
    }
}
