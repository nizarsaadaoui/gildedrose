package com.gildedrose;

class GildedRoseGoldenMaster {
    Item[] items;

    public GildedRoseGoldenMaster(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
    	// Gérer tous les éléments sauf "Aged Brie", "Backstage passes" et "Sulfuras"
        for (int i = 0; i < items.length; i++) {
            if (!items[i].name.equals("Aged Brie")
                    && !items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                // Diminuer la qualité de 1 pour les objets normaux
            	if (items[i].quality > 0) {
                    if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                        items[i].quality = items[i].quality - 1;
                    }
                }
            } 
            
            // Gérer "Aged Brie" et "Backstage passes"

            else {
            	//quality can't be more than 50
            	
                if (items[i].quality < 50) {
                    items[i].quality = items[i].quality + 1;
                    // Règles spéciales pour "Backstage passes"

                    if (items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {

                        if (items[i].sellIn < 11) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }
                        // Augmenter de 1 supplémentaire si sellIn < 6

                        if (items[i].sellIn < 6) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }
                    }
                }
            }

            // Diminuer sellIn pour tous les objets sauf "Sulfuras"

            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                items[i].sellIn = items[i].sellIn - 1;
            }
            // Gérer les objets après la date de péremption (sellIn < 0)

            if (items[i].sellIn < 0) {
                if (!items[i].name.equals("Aged Brie")) {
                    if (!items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        // Pour les objets standards après la date de péremption

                        if (items[i].quality > 0) {
                            // Diminuer la qualité de 1 supplémentaire, sauf pour "Sulfuras"

                            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                                items[i].quality = items[i].quality - 1;
                            }
                        }
                    } else {
                        // Pour "Backstage passes" après le concert, la qualité tombe à 0

                        items[i].quality = items[i].quality - items[i].quality;
                    }
                } else {
                	// Pour "Aged Brie" après la date de péremption
                    // Continuer à augmenter la qualité, mais pas au-delà de 50
                    if (items[i].quality < 50) {
                        items[i].quality = items[i].quality + 1;
                    }
                }
            }
        }
    }
}
