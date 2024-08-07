package com.gildedrose;

class GildedRose {
	private static final int MIN_QUALITY = 0;
	private static final int MAX_QUALITY = 50;
	private static final String SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros";
	private static final String BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT = "Backstage passes to a TAFKAL80ETC concert";
	private static final String AGED_BRIE = "Aged Brie";
	private static final String CONJURED = "Conjured Mana Cake";

	private Item[] items;

	public Item[] getItems() {
		return items;
	}

	public GildedRose(Item[] items) {
		this.items = items;
	}

	public void updateQuality() {
		for (Item item : items) {
			// never has to be sold or decreases in Quality
			if (SULFURAS_HAND_OF_RAGNAROS.equals(item.name)) {
				continue;
			}

			item.sellIn = item.sellIn - 1;

			if (BACKSTAGE_PASSES_TO_A_TAFKAL80ETC_CONCERT.equals(item.name)) {
				updateBackstage(item);
			} else if (AGED_BRIE.equals(item.name)) {
				updateAgedBrie(item);
			} else if (CONJURED.equals(item.name) && item.quality > MIN_QUALITY) {
				updateConjured(item);
			} else if (item.quality > MIN_QUALITY) {
				updateStandard(item);
			}

		}
	}

	private void updateConjured(Item item) {
		item.quality = item.quality - 2; // Decrease twice as fast

		if (item.sellIn < 0 && item.quality > MIN_QUALITY) {
			item.quality = item.quality - 2; // Decrease twice as fast after sellIn date
		}

		if (item.quality < 0) {
			item.quality = 0; // Ensure quality is not negative
		}
	}

	private void updateBackstage(Item item) {
		if (item.quality < MAX_QUALITY) {
			item.quality = item.quality + 1;

			// decrease by 2
			if (item.sellIn < 10) {
				inscreaseQuality(item);
			}
			// decrease by 3
			if (item.sellIn < 5) {
				inscreaseQuality(item);
			}
		}

		if (item.sellIn < 0) {
			item.quality = 0;

		}
	}

	private void inscreaseQuality(Item item) {
		if (item.quality < MAX_QUALITY) {
			item.quality = item.quality + 1;
		}
	}

	private void updateStandard(Item item) {
		item.quality = item.quality - 1;

		if (item.sellIn < 0 && item.quality > MIN_QUALITY) {
			item.quality = item.quality - 1;
		}

	}

	private void updateAgedBrie(Item item) {
		inscreaseQuality(item);

		if (item.sellIn < 0 && item.quality < MAX_QUALITY) {
			item.quality = item.quality + 1;
		}
	}
}
