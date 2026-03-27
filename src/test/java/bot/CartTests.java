package bot;

import bot.core.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CartTests extends TestBase {
    private final String listOfAllAddToCartButtons = "div>button[id*='add-to-cart']";
    private final String listOfAllRemoveFromCartButtons = "button[data-test^='remove']";
    private final String listOfItemsInCart = ".cart_item";

    private final String backpackAddSelector = "#add-to-cart-sauce-labs-backpack";
    private final String backpackRemoveSelector = "#remove-sauce-labs-backpack";
    private final String bikeAddSelector = "#add-to-cart-sauce-labs-bike-light";
    private final String bikeRemoveSelector = "#remove-sauce-labs-bike-light";

    private final String cartButton = ".shopping_cart_container";
    private final String cartBadgeProductsCount = "a.shopping_cart_link>span";

    private final String removeTextString = "Remove";

    private final int amountOfRemoveButtons = 6;

    @Test
    @DisplayName("Verifying if 'Remove' button updates")
    void adding_to_card_should_update_button_name() {
        bot.validLogin();
        bot.waitForPresenceOfElementLocated(backpackAddSelector);
        bot.click(backpackAddSelector);
        bot.waitForPresenceOfElementLocated(backpackRemoveSelector);

        Assertions.assertEquals(removeTextString,
                bot.getTextString(backpackRemoveSelector),
                "Button state does not change");
    }

    @Test
    @DisplayName("Verification if user can add all six products to the cart")
    void verify_if_user_can_add_all_six_products_to_cart() {
        bot.validLogin();
        int numberOfClicks = bot.clickAllElementsReturnNumberOfClicks(bot.getElements(listOfAllAddToCartButtons));

        Assertions.assertEquals(numberOfClicks, amountOfRemoveButtons
                , "difference between clicked buttons");

    }

    @Test
    @DisplayName("Updates cart badge to 1 after adding one product")
    void shouldUpdateCartBadgeWhenAddingOneProduct() {
        bot.validLogin();
        bot.click(backpackAddSelector);

        int expectedCartCount = 1;
        int actualCartCount = Integer.parseInt(bot.getTextString(cartBadgeProductsCount));

        Assertions.assertEquals(expectedCartCount, actualCartCount, "Badge value issue, value is not equal to " + expectedCartCount);

    }


    @Test
    @DisplayName("Updates cart badge to X after adding X products")
    void shouldUpdateCartBadgeCountWhenAddingFewProducts() {
        bot.validLogin();

        int expectedAmountOfItemsInCart = 4;
        bot.addingProvidedAmountOfItemsToCart(bot.getElements(listOfAllAddToCartButtons)
                , expectedAmountOfItemsInCart
        );

        int actualAmountOfItemsInCart = Integer.parseInt(bot.getTextString(cartBadgeProductsCount));

        Assertions.assertEquals(expectedAmountOfItemsInCart, actualAmountOfItemsInCart
                , "Values of items added to cart and badge value count does not match");


    }


    @Test
    @DisplayName("Removing added products update cart badge count")
    void shouldUpdateCartBadgeCountWhenRemovingProducts() {
        bot.validLogin();
        bot.click(backpackAddSelector);
        bot.click(bikeAddSelector);

        if (bot.getElements(listOfAllRemoveFromCartButtons).size() == 2) {
            bot.click(bikeRemoveSelector);
        }

        int expectedAmountOfItemsInCart = Integer.parseInt(bot.getTextString(cartBadgeProductsCount));
        int actualAmountOfItemsInCart = bot.getElements(listOfAllRemoveFromCartButtons).size();
        Assertions.assertEquals(expectedAmountOfItemsInCart, actualAmountOfItemsInCart
                , "Amount of items in cart does not match");


    }

    @Test
    @DisplayName("Correct amount of products in cart on Cart View")
    void shouldStoreCorrectAmountOfProductsInCartView() {
        bot.validLogin();
        int expectedItemsInCart = 4;
        bot.addingProvidedAmountOfItemsToCart(bot.getElements(listOfAllAddToCartButtons), expectedItemsInCart);
        bot.click(cartButton);
        int actualItemsInCart = bot.getElements(listOfItemsInCart).size();

        Assertions.assertEquals(expectedItemsInCart, actualItemsInCart
                , "Count of items in cart does not match"
        );

    }

}

/*
Koszyk
1.
Dodanie 1 produktu → badge koszyka = 1. - done
2.
Dodanie wielu produktów → badge koszyka rośnie zgodnie z liczbą. - done
3.
Usunięcie produktu z listy produktów → badge maleje. - done
4.
Usunięcie produktu z koszyka → badge maleje. - done
5.
Przejście do koszyka i powrót do listy produktów → stan koszyka zachowany.
6.
Dodanie + usunięcie tego samego produktu → wraca do stanu początkowego.
7.
Po odświeżeniu strony koszyk zachowuje stan (jeśli aplikacja tak działa).
 */