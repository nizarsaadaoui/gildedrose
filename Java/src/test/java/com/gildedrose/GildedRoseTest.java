package com.gildedrose;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class GildedRoseTest {

	static Item[] deepCopy(Item[] originalArray) {
		Item[] copiedArray = new Item[originalArray.length];
		for (int i = 0; i < originalArray.length; i++) {
			Item originalItem = originalArray[i];
			copiedArray[i] = new Item(originalItem.name, originalItem.sellIn, originalItem.quality);
		}
		return copiedArray;
	}

	@Test
	void testConjuredItemDegradesTwiceAsFast() {
		Item[] items = new Item[] { new Item("Conjured Mana Cake", 5, 10) };
		GildedRose app = new GildedRose(items);
		app.updateQuality();
		assertThat(items[0].quality).isEqualTo(8);
		assertThat(items[0].sellIn).isEqualTo(4);
	}

	@Test
	void testConjuredItemDegradesTwiceAsFastAfterSellInDate() {
		Item[] items = new Item[] { new Item("Conjured Mana Cake", 0, 10) };
		GildedRose app = new GildedRose(items);
		app.updateQuality();
		assertThat(items[0].quality).isEqualTo(6);
		assertThat(items[0].sellIn).isEqualTo(-1);
	}

	@Test
	void testConjuredItemQualityNeverNegative() {
		Item[] items = new Item[] { new Item("Conjured Mana Cake", 5, 1) };
		GildedRose app = new GildedRose(items);
		app.updateQuality();
		assertThat(items[0].quality).isZero();
	}

	@Test
	void shouldUpdate100days() {
		Item[] items = new Item[] {

				new Item("+5 Dexterity Vest", 10, 20), //
				new Item("Aged Brie", 2, 0), //
				new Item("Elixir of the Mongoose", 5, 7), //
				new Item("Sulfuras, Hand of Ragnaros", 0, 80), //
				new Item("Sulfuras, Hand of Ragnaros", -1, 80),
				new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
				new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
				new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49) };
		Item[] items2 = deepCopy(items);
		GildedRoseGoldenMaster gildedRoseGoldenMaster = new GildedRoseGoldenMaster(items2);

		GildedRose gildedRose = new GildedRose(items);

		for (int i = 0; i < 100; i++) {
			gildedRose.updateQuality();
			gildedRoseGoldenMaster.updateQuality();

			List<Item> actualItems = Arrays.asList(gildedRose.getItems());
			List<Item> expectedItems = Arrays.asList(gildedRoseGoldenMaster.items);

			assertThat(actualItems).extracting(item -> item.sellIn)
					.containsExactly(expectedItems.stream().map(item -> item.sellIn).toArray(Integer[]::new));

			assertThat(actualItems).extracting(item -> item.quality)
					.containsExactly(expectedItems.stream().map(item -> item.quality).toArray(Integer[]::new));
		}
	}

}
