package me.matyyy.kscore.utils;

import java.util.ArrayList;

public class FakeTeam {

    private static final String UNIQUE_ID = generateUUID();
    // This represents the number of FakeTeams that have been created.
    // It is used to generate a unique Team name.
    private static int ID = 0;
    private final ArrayList<String> members = new ArrayList<>();
    private String name;
    private String prefix = "";
    private String suffix = "";

    public FakeTeam(String prefix, String suffix, int sortPriority, boolean playerTag) {
        this.name = UNIQUE_ID + "_" + getNameFromInput(sortPriority) + ++ID + (playerTag ? "+P" : "");

		this.prefix = prefix;
		this.suffix = suffix;

	}

    public String getName() {
    	return this.name;
    }
    
    public ArrayList<String> getMembers() {
    	return this.members;
    }
    
    public String getPrefix() {
    	return this.prefix;
    }
    
    public String getSuffix() {
    	return this.suffix;
    }
    
    public void addMember(String player) {
        if (!members.contains(player)) {
            members.add(player);
        }
    }

    public boolean isSimilar(String prefix, String suffix) {
        return this.prefix.equals(prefix) && this.suffix.equals(suffix);
    }

    /**
     * This is a special method to sort nametags in
     * the tablist. It takes a priority and converts
     * it to an alphabetic representation to force a
     * specific sort.
     *
     * @param input the sort priority
     * @return the team name
     */
    private String getNameFromInput(int input) {
        if (input < 0) return "Z";
        char letter = (char) ((input / 5) + 65);
        int repeat = input % 5 + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < repeat; i++) {
            builder.append(letter);
        }
        return builder.toString();
    }
    
    public static String generateUUID() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            builder.append(chars.charAt((int) (Math.random() * chars.length())));
        }
        return builder.toString();
    }
}
