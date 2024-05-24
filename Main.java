import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.io.*;

public class Main {
    private static final String FILE_NAME = "words.txt";
    private static ArrayList<String> words = new ArrayList<>();
    public static void main(String[] args) {
        loadWordsFromFile();
        boolean gra = true;
        Scanner m = new Scanner(System.in);
        while(gra){
            System.out.println("1.Graj 2.Dodaj Słowo 3.Regulamin 4.Koniec");
            int menu;
            menu=m.nextInt();
            m.nextLine();
            switch (menu) {
                case 1:
                    graj();
                    break;
                case 2:
                    dodawanieSlowa();
                    break;
                case 3:
                    regulamin();
                    break;
                case 4:
                    gra = false;
                    break;
            }
            
        }
        m.close();
    }
    
    private static void loadWordsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println("Nie można odczytać pliku: " + e.getMessage());
        }
    }

    private static void saveWordsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String word : words) {
                writer.write(word);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Nie można zapisać do pliku: " + e.getMessage());
        }
    }

    //GRAJ
    private static void graj() {
        System.out.println("Wybierz poziom trudności");
        System.out.println("1. Łatwy 2. Średni 3. Trudny");
        int zycia;
        Scanner wczytaj = new Scanner(System.in);
        zycia=wczytaj.nextInt();
        wczytaj.nextLine();
        
        switch (zycia) {
            case 1:
                zycia=20;
                break;
            case 2:
                zycia=15;
                break;
            case 3:
                zycia=10;
                break;
        }
        Random random = new Random();
        String wordToGuess = words.get(random.nextInt(words.size()));
        char[] guessedWord = new char[wordToGuess.length()];
        for (int i = 0; i < guessedWord.length; i++) {
            guessedWord[i] = '_';
        }
        
        System.out.println(guessedWord);

        boolean wordGuessed = true;

        while (zycia > 0 && wordGuessed) {
            System.out.print("Zgadnij literę: ");
            String input = wczytaj.nextLine().toLowerCase();

            if (input.isEmpty()) {
                System.out.println("Nie wpisano żadnej litery, spróbuj ponownie.");
                continue;
            }

            char guessedLetter = input.charAt(0);

            boolean letterFound = false;
            for (int i = 0; i < wordToGuess.length(); i++) {
                if (wordToGuess.charAt(i) == guessedLetter) {
                    guessedWord[i] = guessedLetter;
                    letterFound = true;
                }
            }

            if (!letterFound) {
                zycia--;
                System.out.println("Źle! Pozostało Ci: " + zycia+" żyć");
            }

            System.out.println(guessedWord);

            if (new String(guessedWord).equals(wordToGuess)) {
                wordGuessed = false;
                System.out.println("Gratulacje! Odgadłeś słowo: " + wordToGuess);
            }
            
            if (zycia == 0) {
                System.out.println("Przegrałeś! Słowo to: " + wordToGuess);
            }
        }

        
    }

    //Dodawanie Słów
    private static void dodawanieSlowa() {
        System.out.println("Dodaj słowo do bazy:");
        Scanner dodawanie = new Scanner(System.in);
        String noweSlowo = dodawanie.nextLine().toLowerCase();
        words.add(noweSlowo);
        saveWordsToFile();
    }
    //Regulamin
    private static void regulamin() {
        System.out.println("Regulamin gry w wisielcaL:");
        System.out.println("W grze slowa nie maja polskich znakow wiec używaj o zamiast ó itp.");
    }
}