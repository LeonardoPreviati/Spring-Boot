package br.com.empreendedorismo.utils;

public enum CategoryEnum {
	
		CAT_ONE   ("CAT_ONE"),
		CAT_TWO   ("CAT_TWO"),
		CAT_THREE ("CAT_THREE"),
		CAT_FOUR  ("CAT_FOUR"),
		CAT_FIVE  ("CAT_FIVE"),
		CAT_SIX   ("CAT_SIX"),
		CAT_SEVEN ("CAT_SEVEN"),
		CAT_EIGHT ("CAT_EIGHT"),
		CAT_NINE  ("CAT_NINE"),
		CAT_TEN   ("CAT_TEN");
	
		private final String cat;
		
		private CategoryEnum(String cat) {
			this.cat = cat;
		}
}
