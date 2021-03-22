import java.util.*;

public class CatColourCalculator {

    public static void sortAndRemoveDuplicates(ArrayList<String> arrayList){
        Set<String> set = new LinkedHashSet<>();
        set.addAll(arrayList);
        arrayList.clear();
        arrayList.addAll(set);
        Collections.sort(arrayList);
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Cat female = new Cat(scanner.nextLine(), Gender.FEMALE);
        Cat male = new Cat(scanner.nextLine(), Gender.MALE);

        HashMap<Gender, ArrayList<ArrayList<String>>> allCombinations = female.findAllCombinations(male);
        HashMap<String, Integer> colorQuantityMap = new HashMap<>();

        float total = 0;
        ArrayList<String> keyChain = new ArrayList<>();

        HashMap<Gender, Map<ArrayList<String>, String>> colorFromGenes = Cat.colorFromGenes;

        //cross allCombinations for female/male with all legal combinations
        for(Gender gender : allCombinations.keySet()){
            ArrayList<ArrayList<String>> geneCombosForGender = allCombinations.get(gender);

            for(int i = 0; i < geneCombosForGender.size(); i++){
                Set<ArrayList<String>> legalGeneCombos = colorFromGenes.get(gender).keySet();

                if(legalGeneCombos.contains(geneCombosForGender.get(i))){
                    total += 1;
                    keyChain.add(colorFromGenes.get(gender).get(allCombinations.get(gender).get(i)));

                    if(colorQuantityMap.containsKey(colorFromGenes.get(gender).get(allCombinations.get(gender).get(i)))){
                        int value = colorQuantityMap.get(colorFromGenes.get(gender).get(allCombinations.get(gender).get(i)));
                        colorQuantityMap.replace(colorFromGenes.get(gender).get(allCombinations.get(gender).get(i)), value+1);
                    }
                    else{
                        colorQuantityMap.put(colorFromGenes.get(gender).get(allCombinations.get(gender).get(i)), 1);
                    }
                }
            }
        }
        sortAndRemoveDuplicates(keyChain);
        LinkedHashMap<String, Float> results = new LinkedHashMap<>();

        for(String key : keyChain){
            float quantity = colorQuantityMap.get(key);
            float probability = quantity/total;
            results.put(key, probability);
        }
        results.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach((entry) -> System.out.printf(entry.getKey()+" %.9f"+"\n", entry.getValue()));
    }
}
