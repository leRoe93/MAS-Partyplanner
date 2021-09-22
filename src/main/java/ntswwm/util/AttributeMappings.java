package ntswwm.util;

public class AttributeMappings {

    public static String[] ANSWER_ATTRIBUTES_DRINKS_AGENT = { "beerAmount", "wineAmount", "spiritsAmount",
            "softsAmount", "drinksBudget_Specific" };

    public static String[] ANSWER_ATTRIBUTES_FOOD_AGENT = { "meatAmount", "garnishAmount", "snacksAmount",
            "foodBudget_Specific" };

    public static String[] ANSWER_ATTRIBUTES_BUDGET = { "totalBudget", "foodBudget", "drinksBudget", "locationBudget" };

    public static String[] getAnswerAttributesForAgent(String agentType) {
        switch (agentType) {

        case "BudgetAgent":
            return ANSWER_ATTRIBUTES_BUDGET;
        case "FoodAgent":
            return ANSWER_ATTRIBUTES_FOOD_AGENT;
        case "DrinksAgent":
            return ANSWER_ATTRIBUTES_DRINKS_AGENT;
        default:
            throw new IllegalArgumentException("Attribute mappings for non-existent agent was queried!");
        }
    }
}
