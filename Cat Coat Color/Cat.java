import java.awt.*;
import java.util.*;

import static java.util.Map.entry;

public class Cat {

    private final static String BLACK = "Black";
    private final static String RED = "Red";
    private final static String DILUTE = "Dilute";

    private final static String[] BLACKCOMBOS = new String[]{"BB", "bB", "Bb", "bb"};
    private final static String[] DILUTECOMBOS = new String[]{"DD", "Dd", "dd"};
    private final static Map<Gender, String[]> REDCOMBOS = Map.of(
            Gender.MALE, new String[]{"o", "O"},
            Gender.FEMALE, new String[]{"oo", "OO", "Oo"}
    );

    public static HashMap<Gender, Map<ArrayList<String>, String>> colorFromGenes = new HashMap<>();

    public static final Map<String, Map<Gender, String[]>> GENESFROMCOLOR = Map.ofEntries(
            entry("Black", Map.of(
                    Gender.MALE, new String[]{"B", "D", "o"},
                    Gender.FEMALE, new String[]{"B", "D", "oo"}
            )),
            entry("Blue", Map.of(
                    Gender.MALE, new String[]{"B", "dd", "o"},
                    Gender.FEMALE, new String[]{"B", "dd", "oo"}
            )),
            entry("Chocolate", Map.of(
                    Gender.MALE, new String[]{"bb", "D", "o"},
                    Gender.FEMALE, new String[]{"bb", "D", "oo"}
            )),
            entry("Lilac", Map.of(
                    Gender.MALE, new String[]{"bb", "dd", "o"},
                    Gender.FEMALE, new String[]{"bb", "dd", "oo"}
            )),
            entry("Red", Map.of(
                    Gender.MALE, new String[]{"D", "O"},
                    Gender.FEMALE, new String[]{"D", "OO"}
            )),
            entry("Cream", Map.of(
                    Gender.MALE, new String[]{"dd", "O"},
                    Gender.FEMALE, new String[]{"dd", "OO"}
            )),
            entry("Black-Red Tortie", Map.of(
                    Gender.FEMALE, new String[]{"B", "D", "Oo"}
            )),
            entry("Blue-Cream Tortie", Map.of(
                    Gender.FEMALE, new String[]{"B", "dd", "Oo"}
            )),
            entry("Chocolate-Red Tortie", Map.of(
                    Gender.FEMALE, new String[]{"bb", "D", "Oo"}
            )),
            entry("Lilac-Cream Tortie", Map.of(
                    Gender.FEMALE, new String[]{"bb", "dd", "Oo"}
            ))
    );

    private final String color;
    private final Gender gender;
    private final String[] genes;
    private final Map<String, ArrayList<String>> genePossibilities;

    Cat(String color, Gender gender){
        this.color = color;
        this.gender = gender;
        this.genes = GENESFROMCOLOR.get(color).get(gender);
        this.genePossibilities = generateGenePossibilities(genes, gender);
        if(colorFromGenes.isEmpty()){
            genColorFromGenes();
        }
    }

    private Map<String, ArrayList<String>> generateGenePossibilities(String[] genes, Gender gender){
        ArrayList<String> black = new ArrayList<>();
        ArrayList<String> dilute = new ArrayList<>();
        ArrayList<String> red = new ArrayList<>();

        for(String gene : genes){
            if(gene.contains("d") || gene.contains("D")){
                for(String genePair : DILUTECOMBOS){
                    if(genePair.contains(gene)){
                        dilute.add(genePair);
                    }
                }
            }
            else if(gene.contains("b") || gene.contains("B")){
                for(String genePair : BLACKCOMBOS){
                    if(genePair.contains(gene)){
                        black.add(genePair);
                    }
                }
            }
            else{
                for(String genePair : REDCOMBOS.get(gender)){
                    if(genePair.contains(gene)){
                        red.add(genePair);
                    }
                }

            }
            if(black.isEmpty()){
                for(String genePair : BLACKCOMBOS){
                    black.add(genePair);
                }
            }

        }

        if(!color.equals(RED)){
            black.remove("bB");
        }

        Map<String, ArrayList<String>> geneMap = Map.of(
                BLACK, black,
                DILUTE, dilute,
                RED, red
        );

        return geneMap;
    }

    private Map<String, ArrayList<String>> splitGenePossibilities(Map<String, ArrayList<String>> genePossibilities){
        Map<String, ArrayList<String>> splitGenePossibilities = new HashMap<>();
        for(String key : genePossibilities.keySet()){
            ArrayList<String> singleGeneList = new ArrayList<>();
            for(String genePair : genePossibilities.get(key)){
                String[] splitGene = genePair.split("");
                for(String singleGene : splitGene){
                    singleGeneList.add(singleGene);
                }
            }
            splitGenePossibilities.put(key, singleGeneList);
        }
        return splitGenePossibilities;
    }

    /*
    *Structure assuming this gets called on female cat
    * */
    public HashMap<Gender, ArrayList<ArrayList<String>>> findAllCombinations(Cat mate){
        ArrayList<ArrayList<String>> maleCombinations = new ArrayList<>();
        ArrayList<ArrayList<String>> femaleCombinations = new ArrayList<>();
        HashMap<Gender, ArrayList<ArrayList<String>>> results = new HashMap<>();

        Map<String, ArrayList<String>> SplitFemaleGenePossibilities = splitGenePossibilities(genePossibilities);
        Map<String, ArrayList<String>> SplitMaleGenePossibilities = splitGenePossibilities(mate.genePossibilities);

        ArrayList<String> al1 = SplitFemaleGenePossibilities.get(BLACK);
        ArrayList<String> al2 = SplitFemaleGenePossibilities.get(DILUTE);
        ArrayList<String> al3 = SplitFemaleGenePossibilities.get(RED);

        ArrayList<String> al4 = SplitMaleGenePossibilities.get(BLACK);
        ArrayList<String> al5 = SplitMaleGenePossibilities.get(DILUTE);
        ArrayList<String> al6 = SplitMaleGenePossibilities.get(RED);

        for(int i = 0; i < al1.size(); i++){
            String tmp1 = al1.get(i);
            for(int j = 0; j < al2.size(); j++){
                String tmp2 = tmp1 + al2.get(j);
                for(int k = 0; k < al3.size(); k++){
                    String tmp3 = tmp2 + al3.get(k);
                    for(int m = 0; m < al4.size(); m++){
                        String tmp4 = tmp3 +al4.get(m);
                        for(int h = 0; h < al5.size(); h++){
                            String tmp5 = tmp4 + al5.get(h);
                            for(int g = 0; g < al6.size(); g++){
                                String tmp6 = tmp5 + al6.get(g);
                                String[] tempArr = tmp6.split("");
                                ArrayList<String> splitString = new ArrayList<>();
                                for (String geneStringChar: tempArr) {
                                    splitString.add(geneStringChar);
                                }
                                Collections.sort(splitString);
                                femaleCombinations.add(splitString);
                            }
                        }
                    }
                }
            }
        }

        for(int i = 0; i < al1.size(); i++){
            String tmp1 = al1.get(i);
            for(int j = 0; j < al2.size(); j++){
                String tmp2 = tmp1 + al2.get(j);
                for(int k = 0; k < al3.size(); k++){
                    String tmp3 = tmp2 + al3.get(k);
                    for(int m = 0; m < al4.size(); m++){
                        String tmp4 = tmp3 +al4.get(m);
                        for(int h = 0; h < al5.size(); h++){
                            String tmp5 = tmp4 + al5.get(h);
                            String[] tempArr = tmp5.split("");
                            ArrayList<String> splitString = new ArrayList<>();
                            for (String geneStringChar: tempArr) {
                                splitString.add(geneStringChar);
                            }
                            Collections.sort(splitString);
                            maleCombinations.add(splitString);
                        }
                    }
                }
            }
        }

        results.put(Gender.MALE, maleCombinations);
        results.put(Gender.FEMALE, femaleCombinations);

        return results;
    }

    private void genColorFromGenes(){
        HashMap<ArrayList<String>, String> maleColorFromGenes = new HashMap<>();
        HashMap<ArrayList<String>, String> femaleColorFromGenes = new HashMap<>();

        for(String color : GENESFROMCOLOR.keySet()){
            for(Gender gender : GENESFROMCOLOR.get(color).keySet()){
                String [] genes = GENESFROMCOLOR.get(color).get(gender);
                if(gender == Gender.MALE){
                    ArrayList<ArrayList<String>> allGeneCombos = colorGenePossibilities(genes, Gender.MALE);
                    for(int i = 0; i < allGeneCombos.size(); i++) {
                        maleColorFromGenes.put(allGeneCombos.get(i), color);
                    }
                }
                else{
                    ArrayList<ArrayList<String>> allGeneCombos = colorGenePossibilities(genes, Gender.FEMALE);
                    for(int i = 0; i < allGeneCombos.size(); i++) {
                        femaleColorFromGenes.put(allGeneCombos.get(i), color);
                    }
                }
            }
        }
        colorFromGenes.put(Gender.MALE, maleColorFromGenes);
        colorFromGenes.put(Gender.FEMALE, femaleColorFromGenes);
    }

    private ArrayList<ArrayList<String>> colorGenePossibilities(String[] genes, Gender gender){
        Map<String, ArrayList<String>> allGeneCombos = generateGenePossibilities(genes, gender);

        ArrayList<ArrayList<String>> results = new ArrayList<>();

        ArrayList<String> black = allGeneCombos.get(BLACK);
        ArrayList<String> red = allGeneCombos.get(RED);
        ArrayList<String> dilute = allGeneCombos.get(DILUTE);

        for(int i = 0; i < black.size(); i++){
            String temp1 = black.get(i);
            for(int j = 0; j < red.size(); j++){
                String temp2 = temp1 + red.get(j);
                for(int k = 0; k < dilute.size(); k++){
                    String temp3 = temp2 + dilute.get(k);
                    String[] geneCombo = temp3.split("");
                    ArrayList<String> geneHolder = new ArrayList<>();
                    for(String singleGene : geneCombo){
                        geneHolder.add(singleGene);
                    }
                    Collections.sort(geneHolder);
                    results.add(geneHolder);

                }
            }
        }
        return results;
    }
}