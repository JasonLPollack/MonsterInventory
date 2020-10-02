# MonsterInventory

Built using the Jetpack single-activity architecture, makes use of the Navigation component and SafeArgs for passing data to the detail view.

For some simple repository classes, injection is done using katana.

Makes use of the kotlix serialization library for json. Uses kotlin extension methods to retrieve the json from the source URL, although in practice Retrofit would be a better solution for REST.

Also uses kotlin extension methods for image retrieval rather than an image library such as Fresco.
