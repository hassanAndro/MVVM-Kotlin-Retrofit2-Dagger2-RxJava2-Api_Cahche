# Android App using the Pixbay API with Architecture components, Retrofit 2, RxJava 2 and Dagger 2

 - MVVM
 - Databinding
 - RxJava 2
 - Dagger 2
 - Retrofit 2
 - AndroidX
 - LiveData
 - Call Cache

 Search images via api for images from pixbay.com

 Flow : 
 SplashScreen -> MainActivity (Search and item clickable) -> onitemClick AlertDialog -> Show -> DetailsScreen (Large Image, Name, tags, likes, favorites, Comments)
                                                          -> Search (Load new items according to search in recyclerview)

