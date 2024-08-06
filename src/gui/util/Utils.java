package gui.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public List<Integer> extractNumber(String s){

        Pattern pattern = Pattern.compile("([0-9a-zA-Z])+");
        Matcher matcher = pattern.matcher(s);

        List<Integer> numbers = new ArrayList<>();

        while (matcher.find()) {
            String compound = matcher.group();
            if (compound.length() == 1) {
                numbers.add(1);
            } else {
                for (char c: compound.toCharArray()){
                    if (Character.isDigit(c)) {
                        numbers.add(Character.getNumericValue(c));
                    }
                }
            }
        }
        return numbers;
    }

    public List<Character> extractLetters(String s){
        List<Character> letters = new ArrayList<>();
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            letters.add(matcher.group().charAt(0));
        }
        return letters;
    }

    public boolean isBalanced(Map<String, Double> reagents, Map<String, Double> products) {
        Map<String, Double> reagentsAtoms = countAtoms(reagents);
        Map<String, Double> productsAtoms = countAtoms(products);

        return reagentsAtoms.equals(productsAtoms);
    }

    public Map<String, Double> countAtoms(Map<String, Double> compounds) {
        Map<String, Double> atoms = new HashMap<>();
        for (Map.Entry<String, Double> entry : compounds.entrySet()) {
            String compound = entry.getKey();
            double coefficient = entry.getValue();
            Map<String, Double> compoundAtoms = parseCompound(compound);
            for (Map.Entry<String, Double> atomEntry : compoundAtoms.entrySet()) {
                String atom = atomEntry.getKey();
                double count = atomEntry.getValue() * coefficient;
                atoms.put(atom, atoms.getOrDefault(atom, 0.0) + count);
            }
        }
        return atoms;
    }

    public Map<String, Double> parseCompound(String compound) {
        Map<String, Double> atoms = new HashMap<>();
        Matcher matcher = Pattern.compile("\\(?([A-Z][a-z]?)(\\d*)\\)?(\\d*)").matcher(compound);
        while (matcher.find()) {
            String element = matcher.group(1);
            double count = 1.0;
            if (!matcher.group(2).isEmpty()) {
                count = Double.parseDouble(matcher.group(2));
            }
            double multiplier = matcher.group(3).isEmpty() ? 1.0 : Double.parseDouble(matcher.group(3));
            count *= multiplier;
            atoms.put(element, atoms.getOrDefault(element, 0.0) + count);
        }
        return atoms;
    }

    public Map<String, Double> parseCompounds(String compounds) {
        Map<String, Double> atoms = new HashMap<>();
        String[] parts = compounds.split("\\+");
        for (String part : parts) {
            Map<String, Double> compoundAtoms = parseCompound(part.trim());
            atoms.putAll(compoundAtoms);
        }
        return atoms;
    }

    public boolean validateNumberOfCompounds(String s) {
        Pattern pattern = Pattern.compile("\\+");
        Matcher matcher = pattern.matcher(s);

        int n = 0;
        while (matcher.find()) {
            n++;
        }
        return n > 2;
    }

}
