import java.util.Scanner;

class DelayPrinter {
    public static void slowPrint(String text) {
        if (text == null) {
            System.out.println();
            return;
        }
        try {
            for (char c : text.toCharArray()) {
                System.out.print(c);
                Thread.sleep(12);
            }
            System.out.println();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(text);
        } catch (Exception e) {
            System.out.println(text);
        }
    }
}

// ===================== LOADING BAR =====================
class LoadingBar {
    public static void show() {
        DelayPrinter.slowPrint("\nLoading story...\n");

        int total = 20;
        for (int i = 0; i <= total; i++) {
            int percent = (i * 100) / total;
            StringBuilder bar = new StringBuilder("[");
            for (int j = 0; j < total; j++) {
                if (j < i)
                    bar.append("=");
                else
                    bar.append(" ");
            }
            bar.append("] ").append(percent).append("%");
            System.out.print("\r" + bar);
            try {
                Thread.sleep(120);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("\n");
    }
}

// ===================== MAIN MENU =====================
public class Interactive_Story_App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (true) {
            printMainMenu();

            try {
                System.out.print("Choose an option: ");
                String line = scanner.nextLine();
                try {
                    choice = Integer.parseInt(line);
                } catch (NumberFormatException nfe) {
                    DelayPrinter.slowPrint("Invalid input. Please type a number.\n");
                    continue;
                }

                switch (choice) {
                    case 1:
                        startGame(scanner);
                        break;

                    case 2:
                        showInstructions();
                        break;

                    case 3:
                        DelayPrinter.slowPrint("Exiting game. Goodbye!");
                        scanner.close();
                        return;

                    default:
                        DelayPrinter.slowPrint("Invalid choice. Try again.\n");
                }
            } catch (Exception e) {
                DelayPrinter.slowPrint("Unexpected error: " + e.getMessage());
            }
        }
    }

    private static void printMainMenu() {
        DelayPrinter.slowPrint("======================================");
        DelayPrinter.slowPrint("      INTERACTIVE STORY GAME");
        DelayPrinter.slowPrint("======================================");
        DelayPrinter.slowPrint("1. STORY ONE");
        DelayPrinter.slowPrint("2. Instructions");
        DelayPrinter.slowPrint("3. Exit");
    }

    private static void startGame(Scanner scanner) {
        LoadingBar.show();

        InteractiveStoryGame.GameState state = new InteractiveStoryGame.GameState(scanner);

        InteractiveStoryGame.GameEngine engine = new InteractiveStoryGame.GameEngine(state);
        engine.start();

        DelayPrinter.slowPrint("\n(Story finished -- returning to main menu)\n");
    }

    private static void showInstructions() {
        DelayPrinter.slowPrint("\n=========== INSTRUCTIONS ===========");
        DelayPrinter.slowPrint("• This is a branching text adventure.");
        DelayPrinter.slowPrint("• Read the story and choose an option when prompted.");
        DelayPrinter.slowPrint("• Your choices affect the direction and ending of the game.");
        DelayPrinter.slowPrint("• Type the number of the option and press ENTER.");
        DelayPrinter.slowPrint("• At any choice prompt, type 0 to return to the main menu.\n");
        DelayPrinter.slowPrint("Enjoy the story!\n");
    }
}

// ===================== HELPERS & EXCEPTIONS =====================

class ReturnToMenuException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ReturnToMenuException() {
        super("Player requested return to main menu.");
    }
}

// ===================== PATH CLASSES =====================

abstract class Path {
    protected String playerName;
    protected Scanner input;

    public Path(String playerName, Scanner input) {
        this.playerName = playerName;
        this.input = input;
    }

    public abstract void playPath();

    protected int getInput(int maxOption) {
        int choice = -1;
        while (true) {
            try {
                System.out.print("Enter choice (0-" + maxOption + "): ");
                String line = input.nextLine().trim();
                int parsed;
                try {
                    parsed = Integer.parseInt(line);
                } catch (NumberFormatException nfe) {
                    DelayPrinter.slowPrint("Invalid input. Please enter a number between 0 and " + maxOption);
                    continue;
                }
                if (parsed < 0 || parsed > maxOption) {
                    DelayPrinter.slowPrint("Invalid input. Please enter a number between 0 and " + maxOption);
                    continue;
                }
                if (parsed == 0) {

                    throw new ReturnToMenuException();
                }
                choice = parsed;
                break;
            } catch (ReturnToMenuException rtm) {

                throw rtm;
            } catch (Exception e) {
                DelayPrinter.slowPrint("Invalid input. Please enter a number between 0 and " + maxOption);
            }
        }
        return choice;
    }

    protected void printMiniEvents(String[] miniEvents) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        for (String event : miniEvents) {
            DelayPrinter.slowPrint(event);
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    protected void printSeparator(String title) {
        DelayPrinter.slowPrint("\n-----------------------------------");
        DelayPrinter.slowPrint("  " + title);
        DelayPrinter.slowPrint("-----------------------------------\n");
    }
}

// -------------------- PATH 1 --------------------
class Path1 extends Path {
    private String[] miniEvents = {
            "A strong wind blows through the tall trees, making the branches move and the leaves rustle softly.",
            "Shadows move quickly between the old trees, almost like they are alive, making you feel watched.",
            "Small golden lights flicker in the mist, floating for a moment before disappearing, as if inviting you to follow them.",
            "The smell of sampaguita flowers mixes with the damp earth and moss, sweet but a little sad.",
            "Far away, you hear a soft, sad sob through the trees, making your heart worry for your sister."
    };

    public Path1(String playerName, Scanner input) {
        super(playerName, input);
    }

    @Override
    public void playPath() {
        printSeparator("--- The Forest Beckons ---");
        DelayPrinter.slowPrint("\n--- The Forest Beckons ---");
        DelayPrinter.slowPrint(
                "Makiling's forest is shrouded in darkness. Mist curls around your feet and drifts over the twisted roots. Each step echoes through the quiet woods. People speak of the guardian spirit protecting the mountain, yet your heart pushes you onward. You cannot turn back — your sister is missing, and every heartbeat calls for her.   \n");

        printMiniEvents(miniEvents);

        DelayPrinter.slowPrint(
                "\nAs you move cautiously, a shiver runs down your spine. The trees seem to lean closer, watching you. You pause and listen. Far off in the forest, you hear a faint whisper of your sister’s name. You must choose: call out to her, or follow the small, flickering lights in the mist.\n");

        DelayPrinter.slowPrint("What should you do?");
        DelayPrinter.slowPrint("1. Call out your sister's name.");
        DelayPrinter.slowPrint("2. Stay silent and follow the light ahead.");

        int choice = getInput(2);

        if (choice == 1) {
            callOutSister();
        } else {
            followLight();
        }
    }

    private void callOutSister() {
        DelayPrinter.slowPrint(
                "\nYou drop to your knees on the cold ground. “Please, my sister is lost. I just want to find her,” you plead. The spirit watches quietly. After a moment, she gives a faint, sad smile. “Your heart is pure. Few mortals come here without greed,” she whispers. Her hand rests lightly on your forehead. Warmth spreads through you, and your vision fills with images of your sister, safe by a sparkling stream, surrounded by glowing vines and gentle, protective light.\n");

        DelayPrinter.slowPrint(
                "You kneel on the cold ground. “Please, my sister is lost. I only want to find her,” you plead. The spirit watches silently. After a moment, she smiles faintly, a sad, distant smile. “Your heart is pure. Few mortals come here without greed,” she whispers. Her hand touches your forehead gently. Warmth spreads through you, and your vision fills with images of your sister, safe by a sparkling stream, surrounded by glowing vines and soft, protective light.\n");

        DelayPrinter.slowPrint("Your heart races, but you have choices to make:");
        DelayPrinter.slowPrint("1. Kneel and humbly ask for her help.");
        DelayPrinter.slowPrint("2. Demand to know where your sister is, with urgency and fear in your voice.");

        int choice = getInput(2);

        InteractiveStoryGame.GameState state = InteractiveStoryGame.GameStateHolder.getState();
        if (state != null) {
            if (choice == 1) {
                state.addRespect(2);
            } else {
                state.addFear(1);
                state.addBravery(1);
            }
        }

        if (choice == 1) {
            DelayPrinter.slowPrint(
                    "\nYou fall to your knees, the cold earth beneath you. “Please, my sister is lost. I only wish to find her,” you plead. The spirit watches in silence. Moments pass, then she smiles faintly, a sad and distant smile. “Your heart is pure. Few mortals come here without greed,” she whispers. Her hand rests lightly on your forehead. Warmth spreads through you, and your vision fills with vivid images of your sister, safe beside a sparkling stream, surrounded by glowing vines and gentle, protective light.\n");

            DelayPrinter.slowPrint(
                    "The spirit’s voice echoes in your mind: “Go. Follow the path I have shown. But never return at night again.” She fades into the mist, leaving only the gentle scent of sampaguita.\n");
        } else {
            DelayPrinter.slowPrint(
                    "\n'“Where is she?” you shout, desperation overtaking caution. “I know you took her!” The guardian’s eyes darken, and a chilling wind sweeps through the clearing. The ground shakes beneath you. Roots burst from the soil, coiling tightly around your legs. Panic grips you, but after a tense struggle, they loosen, letting you stumble free, soaked in sweat and fear. Even as the forest grows silent again, your determination hardens — you must keep going.\n");
        }

        Path2 nextPath = new Path2(playerName, input);
        nextPath.playPath();
    }

    private void followLight() {
        DelayPrinter.slowPrint(
                "\nYou stay silent, carefully moving toward the flickering lights in the mist. Each step is deliberate, the soft earth yielding beneath your feet. The glow leads you to a stone altar hidden under ancient trees. Moss covers its surface, and fruits, flowers, and pearls lie scattered around, left by travelers over many years. A faint voice whispers, “Leave an offering, and your wish will be heard.'\n");

        DelayPrinter.slowPrint("What will you do?");
        DelayPrinter.slowPrint("1. Leave your necklace as an offering.");
        DelayPrinter.slowPrint("2. Ignore it and continue forward.");

        int choice = getInput(2);

        InteractiveStoryGame.GameState state = InteractiveStoryGame.GameStateHolder.getState();
        if (state != null) {
            if (choice == 1) {
                state.addRespect(1);
            } else {
                state.addFear(1);
            }
        }

        if (choice == 1) {
            DelayPrinter.slowPrint(
                    "\nYou kneel and place your treasured necklace — a gift from your mother — on the altar. The forest seems to breathe. The mist thickens for a moment, then clears, revealing a narrow hidden path. Hope rises within you as you step forward, feeling as if unseen eyes guide and protect you.\n");
        } else {
            DelayPrinter.slowPrint(
                    "\nYou pass the altar, torn between hesitation and determination. The mist thickens, and shadows flicker just out of sight. Still, a faint trail appears, calling you forward. The forest tests you, but love and the strong desire to find your sister push you onward.\n");
        }

        Path2 nextPath = new Path2(playerName, input);
        nextPath.playPath();
    }
}

// -------------------- PATH 2 --------------------
class Path2 extends Path {
    private String[] miniEvents = {
            "A sudden flutter of wings passes overhead, leaving a faint trail of golden feathers in the air.",
            "The mist thickens, curling around the trees and narrow path, as if trying to hide your way.",
            "You feel a gentle, almost unnoticeable touch on your shoulder, though no one is there.",
            "A distant stream gurgles, its sound blending with whispers of your sister’s name, guiding you.",
            "Faint glimmers of light move among the branches, hinting at hidden paths and secret clearings."
    };

    public Path2(String playerName, Scanner input) {
        super(playerName, input);
    }

    @Override
    public void playPath() {
        printSeparator("--- The Hidden Trail ---");
        DelayPrinter.slowPrint("\n--- The Hidden Trail ---");
        DelayPrinter.slowPrint(
                "Following the trail from Path 1, the forest becomes thicker, with older, twisted trees.");
        DelayPrinter.slowPrint(
                "With each step, you move deeper into the unknown. Mist wraps around your legs, and the air is filled with the scent of damp earth and flowers.\n");

        printMiniEvents(miniEvents);

        DelayPrinter.slowPrint(
                "\nAhead, a faint glow flickers among the trees, shimmering like a will-o'-the-wisp. It seems to call you forward, but caution is needed.\n");

        DelayPrinter.slowPrint("How do you approach the glowing light?");
        DelayPrinter.slowPrint("1. Approach cautiously, studying every shadow and sound.");
        DelayPrinter.slowPrint("2. Move boldly, trusting your instincts.");
        DelayPrinter.slowPrint("3. Observe and wait, letting the patterns of light reveal the safest path.");

        int choice = getInput(3);

        InteractiveStoryGame.GameState state = InteractiveStoryGame.GameStateHolder.getState();
        if (state != null) {
            if (choice == 1)
                state.addRespect(1);
            if (choice == 2)
                state.addBravery(2);
            if (choice == 3)
                state.addRespect(1);
        }

        switch (choice) {
            case 1:
                DelayPrinter.slowPrint(
                        "\nCarefully, you move forward, hearing the leaves whisper and twigs crunch beneath your feet. The forest seems to pause, as if testing your determination.");
                break;
            case 2:
                DelayPrinter.slowPrint(
                        "\nYou step forward with courage, feeling the rush of adventure. The wind brushes by, carrying the faint scent of wildflowers and mystery.");
                break;
            case 3:
                DelayPrinter.slowPrint(
                        "\nYou stop and watch the play of light and shadow. Gradually, the flickering glow reveals a faint trail, showing a subtle path ahead.");
                break;
        }

        DelayPrinter.slowPrint(
                "\nThe path leads to a stone altar covered in moss, partially hidden under a dense canopy of trees.");
        DelayPrinter.slowPrint(
                "Ancient symbols are etched into the stone, glowing softly. Fruits, flowers, and delicate pearls lie on its surface, left by travelers over many centuries.\n");

        DelayPrinter.slowPrint("What will you do?");
        DelayPrinter.slowPrint("1. Leave a token of respect as an offering.");
        DelayPrinter.slowPrint("2. Ignore the altar and continue forward on the path.");

        int choice2 = getInput(2);

        if (state != null) {
            if (choice2 == 1)
                state.addRespect(2);
            else
                state.addFear(1);
        }

        if (choice2 == 1)
            DelayPrinter.slowPrint(
                    "\nYou kneel and place a small family ring on the altar. The air shimmers, and a gentle warmth spreads through the forest, as if unseen eyes recognize your respect and bravery.\n");
        else
            DelayPrinter.slowPrint(
                    "\nYou leave the altar behind and move onward. The forest becomes quiet, almost thoughtful. Shadows shift slightly, but the hidden path stays clear enough to follow.\n");

        Path3 nextPath = new Path3(playerName, input);
        nextPath.playPath();
    }
}

// -------------------- PATH 3 --------------------
class Path3 extends Path {
    private String[] miniEvents = {
            "Thick mist swirls around your legs, forming ghostly shapes that appear in the corners of your eyes.",
            "Golden specks of light drift slowly through the air, reflecting off the twisted tree trunks.",
            "Soft, eerie laughter echoes in the distance, reminding you of the many travelers who have passed through these woods.",
            "The uneven forest floor shifts under your feet, testing your balance and determination.",
            "Faint whispers of your sister’s name drift through the mist, tugging at your heart."
    };

    public Path3(String playerName, Scanner input) {
        super(playerName, input);
    }

    @Override
    public void playPath() {
        printSeparator("--- The Veiled Summit ---");
        DelayPrinter.slowPrint("\n--- The Veiled Summit ---");
        DelayPrinter.slowPrint(
                "You enter the heart of Makiling’s forest. The air is cool and heavy, charged with energy. Shadows flicker along your path, and distant lights glow softly, almost as if inviting you onward.\n");

        printMiniEvents(miniEvents);

        DelayPrinter.slowPrint(
                "\nAhead, the forest reveals a glowing chamber, where crystals in the walls scatter light in dancing patterns. The guardian spirit of Makiling appears, radiant and otherworldly. Her golden eyes look into yours, judging the purity of your heart.\n");

        DelayPrinter.slowPrint(
                "She speaks, her voice blending with the forest: \"To find what you seek, you must pass three trials: Perception, Courage, and Heart.");

        DelayPrinter.slowPrint("\nChoose how to approach the trials:");
        DelayPrinter.slowPrint("1. Move cautiously, observing every detail.");
        DelayPrinter.slowPrint("2. Move boldly, trusting instinct and courage.");
        DelayPrinter.slowPrint("3. Observe and analyze before acting, using wisdom to guide you.");

        int approachChoice = getInput(3);

        InteractiveStoryGame.GameState state = InteractiveStoryGame.GameStateHolder.getState();
        if (state != null) {
            if (approachChoice == 1)
                state.addRespect(1);
            if (approachChoice == 2)
                state.addBravery(2);
            if (approachChoice == 3)
                state.addRespect(1);
        }

        handleMiniEvents(approachChoice);

        DelayPrinter.slowPrint("\nTrial of Perception: Crystal Reflections");
        DelayPrinter.slowPrint(
                "The chamber changes, showing illusions of your fears and doubts. Crystal walls reflect pieces of past mistakes and worries.");
        DelayPrinter.slowPrint("1. Confront the illusions directly.");
        DelayPrinter.slowPrint("2. Study patterns carefully to avoid danger.");
        DelayPrinter.slowPrint("3. Seek guidance from the guardian spirit’s whispers.");

        int perceptionChoice = getInput(3);

        if (state != null) {
            if (perceptionChoice == 1)
                state.addBravery(1);
            if (perceptionChoice == 2)
                state.addRespect(1);
            if (perceptionChoice == 3)
                state.addRespect(1);
        }

        DelayPrinter.slowPrint("\nTrial of Courage: The Shifting Floor");
        DelayPrinter.slowPrint(
                "The ground shifts beneath you, challenging your balance and courage. Shadows twist toward you, yet a faint path stays in view.");
        DelayPrinter.slowPrint("1. Leap boldly across the shifting stones.");
        DelayPrinter.slowPrint("2. Step cautiously, one foot at a time.");
        DelayPrinter.slowPrint("3. Observe patterns before moving, waiting for the safest moment.");

        int courageChoice = getInput(3);

        if (state != null) {
            if (courageChoice == 1)
                state.addBravery(2);
            if (courageChoice == 2)
                state.addRespect(1);
            if (courageChoice == 3)
                state.addRespect(1);
        }

        DelayPrinter.slowPrint("\nTrial of Heart: The Final Veil");
        DelayPrinter.slowPrint(
                "A misty wall with faint lights appears ahead. You sense your sister beyond it, but the way is hidden and dangerous.");
        DelayPrinter.slowPrint("1. Step directly into the mist, trusting your love and instincts.");
        DelayPrinter.slowPrint("2. Call out to your sister gently, letting your voice guide you.");
        DelayPrinter.slowPrint("3. Move slowly, letting intuition guide each step carefully.");

        int heartChoice = getInput(3);

        if (state != null) {
            if (heartChoice == 1)
                state.addBravery(1);
            if (heartChoice == 2)
                state.addRespect(2);
            if (heartChoice == 3)
                state.addRespect(1);
        }

        String endingMessage = determineEnding(approachChoice, perceptionChoice, courageChoice, heartChoice);

        DelayPrinter.slowPrint("\n--- FINAL MOMENT ---");
        DelayPrinter.slowPrint(
                "The mist parts before your eyes. There, illuminated by soft golden light, stands your sister. Relief, joy, and exhaustion flood through you.");
        DelayPrinter.slowPrint(endingMessage);
        DelayPrinter.slowPrint(
                "\nThe forest whispers its approval. The guardian spirit smiles once, then vanishes into the night, leaving you with your sister and memories of the enchanted Makiling.");
        DelayPrinter.slowPrint("\nCongratulations! You have completed the Makiling Adventure.\n");
    }

    private void handleMiniEvents(int approach) {
        DelayPrinter.slowPrint("\n--- Observing the Forest ---");
        for (String event : miniEvents) {
            if (approach == 1)
                DelayPrinter.slowPrint("Careful observation: " + event);
            else if (approach == 2)
                DelayPrinter.slowPrint("Bold step: " + event);
            else
                DelayPrinter.slowPrint("Insightful awareness: " + event);
            try {
                Thread.sleep(450);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private String determineEnding(int approach, int perception, int courage, int heart) {
        int score = approach + perception + courage + heart;

        InteractiveStoryGame.GameState state = InteractiveStoryGame.GameStateHolder.getState();
        if (state != null) {
            score += state.getBravery();
            score += state.getRespect();
            score -= state.getFear();
        }

        if (score <= 6)
            return "By staying patient and watching carefully, you overcome each challenge. Your sister is safe, and the forest gives you its gentle blessing.";
        else if (score <= 9)
            return "Your bravery and instincts help you face the challenges. You safely find your sister, and the forest honors your courage.";
        else
            return "Using your intuition, courage, and a kind heart, you face the challenges and succeed. You happily reunite with your sister, and the guardian spirit gives you Makiling’s greatest blessing.";
    }
}

// ===================== INTERACTIVE STORY GAME (ENGINE & STATE)
// =====================
class InteractiveStoryGame {

    public static class GameState {
        private Scanner input;
        private int respect = 0;
        private int fear = 0;
        private int bravery = 0;

        public GameState(Scanner input) {
            this.input = input;
            GameStateHolder.setState(this);
        }

        public Scanner getInput() {
            return input;
        }

        public void addRespect(int v) {
            respect += v;
        }

        public void addFear(int v) {
            fear += v;
        }

        public void addBravery(int v) {
            bravery += v;
        }

        public int getRespect() {
            return respect;
        }

        public int getFear() {
            return fear;
        }

        public int getBravery() {
            return bravery;
        }
    }

    public static class GameStateHolder {
        private static GameState theState = null;

        public static void setState(GameState state) {
            theState = state;
        }

        public static GameState getState() {
            return theState;
        }
    }

    public static class GameEngine {
        private GameState state;

        public GameEngine(GameState state) {
            this.state = state;
        }

        public void start() {
            Scanner input = state.getInput();
            try {
                Thread.sleep(900);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.print("\nEnter your name, traveler:\n> ");
            String playerName = input.nextLine();

            DelayPrinter.slowPrint("\nWelcome, " + playerName + ", to the Makiling Adventure Game!");
            DelayPrinter
                    .slowPrint("Your journey begins now, through the mysterious and enchanted forest of Makiling...\n");

            try {
                Path1 path1 = new Path1(playerName, input);
                path1.playPath();
            } catch (ReturnToMenuException rtm) {
                DelayPrinter.slowPrint("\nReturning to main menu...\n");
                return;
            } catch (Exception e) {
                DelayPrinter.slowPrint("An unexpected error occurred during the story: " + e.getMessage());
                e.printStackTrace();
                return;
            } finally {
                GameStateHolder.setState(null);
            }
        }
    }
}
