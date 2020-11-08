# E-COMMERCE
## Özet
Proje kısaca anlatmak gerekirse mail ile kayıt olduktan sonra ürünlerinizi   ekleme, silme, güncelleme gibi işlemlerinizi yapabilceğiniz basit bir e ticaret çalışmasıdır. Amaç firebase yapısını kulllanmaktı.
## Yapılması planlananlar
Sıralama işlemi olarak kendi algoritmamı oluşturup eklemeyi düşünüyorum. Arayüz biraz daha işlevsel hale getirilebilir.
## Kullanılanlar
Mimari olarak Mvvm design pattern kullanarak tasarlanmış bir uygulamadır. Bunun yanında Firebase auth, storage, firestore , navigationview-fragment kullanılarak yazılmış bir uygulamadır.
## Gerekli olanlar
 //MVVM dependency
    def lifecycle_version = "2.2.0"
    // ViewModel
    implementation "androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version"
    // LiveData
    implementation "androidx.lifecycle:lifecycle-livedata:$lifecycle_version"
    // Lifecycles only (without ViewModel or LiveData)
    implementation "androidx.lifecycle:lifecycle-runtime:$lifecycle_version"
    // Annotation processor
    annotationProcessor "androidx.lifecycle:lifecycle-compiler:$lifecycle_version"
    def nav_version = "2.3.0"
    // Navigation implementation
    implementation "androidx.navigation:navigation-fragment:$nav_version"
    implementation "androidx.navigation:navigation-ui:$nav_version"
    //material design
    def version = "1.3.0-alpha01"
    implementation "com.google.android.material:material:$version"

    //circle image View
    implementation 'de.hdodenhof:circleimageview:3.1.0'
    //gilde
    implementation 'com.github.bumptech.glide:glide:4.11.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'

    //for spots dialogue
    implementation 'com.github.d-max:spots-dialog:1.1@aar'

    //crop image...
    implementation 'com.theartofdev.edmodo:android-image-cropper:2.8.+'

    //glide
    implementation 'com.github.bumptech.glide:glide:4.11.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'
    //compress image
    implementation 'id.zelory:compressor:2.1.0'
    //image cropper
   // api 'com.theartofdev.edmodo:android-image-cropper:2.8.+'
    //implementation 'com.theartofdev.edmodo:android-image-cropper:2.8.+'
    //Toast
   implementation 'com.sdsmdg.tastytoast:tastytoast:0.1.1'
