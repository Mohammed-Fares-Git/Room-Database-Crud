package com.MohammedFares.ecomerce_project.di.module

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.MohammedFares.ecomerce_project.comon.Constantes
import com.MohammedFares.ecomerce_project.data.EcommerceDb
import com.MohammedFares.ecomerce_project.data.dao.AdminDao
import com.MohammedFares.ecomerce_project.data.dao.ClientDao
import com.MohammedFares.ecomerce_project.data.dao.ProductBrandDao
import com.MohammedFares.ecomerce_project.data.dao.ProductColorDao
import com.MohammedFares.ecomerce_project.data.dao.ProductDao
import com.MohammedFares.ecomerce_project.data.dao.ProductImagesDao
import com.MohammedFares.ecomerce_project.data.dao.ProductLikeDao
import com.MohammedFares.ecomerce_project.data.dao.ProductSizeDao
import com.MohammedFares.ecomerce_project.data.dao.ProductTypeDao
import com.MohammedFares.ecomerce_project.data.entity.Admin
import com.MohammedFares.ecomerce_project.data.entity.Client
import com.MohammedFares.ecomerce_project.data.entity.Product
import com.MohammedFares.ecomerce_project.data.entity.ProductBrand
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.data.entity.ProductLike
import com.MohammedFares.ecomerce_project.data.entity.ProductSize
import com.MohammedFares.ecomerce_project.data.entity.ProductSubImage
import com.MohammedFares.ecomerce_project.data.entity.ProductType
import com.MohammedFares.ecomerce_project.data.repository.AuthRepositoryImpl
import com.MohammedFares.ecomerce_project.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext ctx: Context): EcommerceDb {
        return Room.databaseBuilder(
            ctx.applicationContext,
            EcommerceDb::class.java,
            "ecomerce_temp_db"
        )
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // You can insert your initial values here
                    GlobalScope.launch {

                        val adminDao = provideRoom(ctx).adminDao()
                        val clienDao = provideRoom(ctx).clientDao()
                        val productDao = provideRoom(ctx).productDao()
                        val colorDao = provideRoom(ctx).productColorDao()
                        val sizeDao = provideRoom(ctx).productSizeDao()
                        val likeDao = provideRoom(ctx).productLikeDao()
                        val brandDao = provideRoom(ctx).productBrandDao()
                        val typeDao = provideRoom(ctx).productTypeDao()
                        val imageDao = provideRoom(ctx).imagetDao()




                        clienDao.insert(Client(firstname = "Mohammed", lastname = "Fares", email =  "mohammed@fares.com", password = "123", gender = "h", state = "active"))


                        adminDao.insertAdmin(Admin(firstname = "Mohammed", lastname = "Fares", email =  "mohammed@fares.com", password = "123"))
                        adminDao.insertAdmin(Admin(firstname = "Soufian", lastname = "Felat", email =  "soufian@felat.com", password = "123"))
                        adminDao.insertAdmin(Admin(firstname = "Amine", lastname = "Ouanda", email =  "amine@ouand.com", password = "123"))
                        adminDao.insertAdmin(Admin(firstname = "Younes", lastname = "Kinani", email =  "younes@kinani.com", password = "123"))

                        brandDao.insertBrand(ProductBrand(brandId = 1,brandName = "Nike", brandImage = "https://tse4.mm.bing.net/th?id=OIP.k0btW08ZcHA6fyuCw-q6vAHaDi&pid=Api&P=0&h=180"))
                        brandDao.insertBrand(ProductBrand(brandId = 2,brandName = "Adidas", brandImage = "https://tse4.mm.bing.net/th?id=OIP.HlG0ifdwU9yHd4BIKl5XzwHaFf&pid=Api&P=0&h=180"))

                        typeDao.insertType(ProductType(typeId = 1,typeName = Constantes.T_SHIRT, typeImage = "https://img-lcwaikiki.mncdn.com/Resource/Images/Banner/311023all-circle4.jpg"))
                        typeDao.insertType(ProductType(typeId = 2,typeName = "Jean", typeImage = "https://img-lcwaikiki.mncdn.com/Resource/Images/Banner/311023all-circle2.jpg"))
                        typeDao.insertType(ProductType(typeId = 3,typeName = Constantes.DRESS, typeImage = "https://img-lcwaikiki.mncdn.com/Resource/Images/Banner/311023all-circle3.jpg"))

                        productDao.insertProduct(Product(productId = 1,productName = "product1", sold = 25, livreson = true, gender = Constantes.FEMALE_KEY, productDesc = "Chevilles et bas côtelés\n" +
                                "Tailles de modèle\n" +
                                "Poitrine: 78 cm Taille: 56 cm Hanche: 91 cm Longueur: 178 cm\n" +
                                "Le mannequin porte la taille S.\n" +
                                "Contenu et caractéristiques du produit\n" +
                                "Contenu du produit:\n" +
                                "Tissu Principal: 100 % ACRYLIQUE\n" +
                                "\n" +
                                "Caractéristiques du produit:\n" +
                                "Motif: Coupe droite\n" +
                                "Coupe: Coupe confortable\n" +
                                "Épaisseur: Épais\n" +
                                "Longueur des manches: Manche longue\n" +
                                "Type de produit: Pull\n" +
                                "Le col: Bougie\n" +
                                "Tissu: Tricot\n" +
                                "Sous-marque: LCWAIKIKI Basic\n" +
                                "Genre: Femme", price = 1987.0, quantity = 1000, brandId = 1, typeId = 1, mainImage = "https://img-lcwaikiki.mncdn.com/mnresize/480/-/pim/productimages/20232/6484061/v2/l_20232-w32862z8-lcc-78-56-91-178_a.jpg"))
                        productDao.insertProduct(Product(productId = 2,productName = "product2", gender = Constantes.MALE_KEY, productDesc = "Chevilles et bas côtelés\n" +
                                "Tailles de modèle\n" +
                                "Poitrine: 78 cm Taille: 56 cm Hanche: 91 cm Longueur: 178 cm\n" +
                                "Le mannequin porte la taille S.\n" +
                                "Contenu et caractéristiques du produit\n" +
                                "Contenu du produit:\n" +
                                "Tissu Principal: 100 % ACRYLIQUE\n" +
                                "\n" +
                                "Caractéristiques du produit:\n" +
                                "Motif: Coupe droite\n" +
                                "Coupe: Coupe confortable\n" +
                                "Épaisseur: Épais\n" +
                                "Longueur des manches: Manche longue\n" +
                                "Type de produit: Pull\n" +
                                "Le col: Bougie\n" +
                                "Tissu: Tricot\n" +
                                "Sous-marque: LCWAIKIKI Basic\n" +
                                "Genre: Femme", price = 8686.0, quantity = 1000, brandId = 2, typeId = 2, mainImage = "https://img-lcwaikiki.mncdn.com/mnresize/480/-/pim/productimages/20232/6470811/v1/l_20232-w31845z8-s8f-99-75-93-185_a.jpg"))
                        productDao.insertProduct(Product(productId = 3,productName = "product3", productDesc = "Chevilles et bas côtelés\n" +
                                "Tailles de modèle\n" +
                                "Poitrine: 78 cm Taille: 56 cm Hanche: 91 cm Longueur: 178 cm\n" +
                                "Le mannequin porte la taille S.\n" +
                                "Contenu et caractéristiques du produit\n" +
                                "Contenu du produit:\n" +
                                "Tissu Principal: 100 % ACRYLIQUE\n" +
                                "\n" +
                                "Caractéristiques du produit:\n" +
                                "Motif: Coupe droite\n" +
                                "Coupe: Coupe confortable\n" +
                                "Épaisseur: Épais\n" +
                                "Longueur des manches: Manche longue\n" +
                                "Type de produit: Pull\n" +
                                "Le col: Bougie\n" +
                                "Tissu: Tricot\n" +
                                "Sous-marque: LCWAIKIKI Basic\n" +
                                "Genre: Femme", price = 665.0, quantity = 1000, brandId = 1, typeId = 3, mainImage = "https://img-lcwaikiki.mncdn.com/mnresize/480/-/pim/productimages/20232/6466928/v1/l_20232-w31507z8-l8n-103-82-86-187_a.jpg"))
                        productDao.insertProduct(Product(productId = 4,productName = "product4", productDesc = "Chevilles et bas côtelés\n" +
                                "Tailles de modèle\n" +
                                "Poitrine: 78 cm Taille: 56 cm Hanche: 91 cm Longueur: 178 cm\n" +
                                "Le mannequin porte la taille S.\n" +
                                "Contenu et caractéristiques du produit\n" +
                                "Contenu du produit:\n" +
                                "Tissu Principal: 100 % ACRYLIQUE\n" +
                                "\n" +
                                "Caractéristiques du produit:\n" +
                                "Motif: Coupe droite\n" +
                                "Coupe: Coupe confortable\n" +
                                "Épaisseur: Épais\n" +
                                "Longueur des manches: Manche longue\n" +
                                "Type de produit: Pull\n" +
                                "Le col: Bougie\n" +
                                "Tissu: Tricot\n" +
                                "Sous-marque: LCWAIKIKI Basic\n" +
                                "Genre: Femme", price = 231.0, quantity = 1000, brandId = 2, typeId = 3, mainImage = "https://img-lcwaikiki.mncdn.com/mnresize/480/-/pim/productimages/20232/6664452/v1/l_20232-w3ck07z8-fmp-75-61-88-178_a.jpg"))
                        productDao.insertProduct(Product(productId = 5,productName = "product5", productDesc = "Chevilles et bas côtelés\n" +
                                "Tailles de modèle\n" +
                                "Poitrine: 78 cm Taille: 56 cm Hanche: 91 cm Longueur: 178 cm\n" +
                                "Le mannequin porte la taille S.\n" +
                                "Contenu et caractéristiques du produit\n" +
                                "Contenu du produit:\n" +
                                "Tissu Principal: 100 % ACRYLIQUE\n" +
                                "\n" +
                                "Caractéristiques du produit:\n" +
                                "Motif: Coupe droite\n" +
                                "Coupe: Coupe confortable\n" +
                                "Épaisseur: Épais\n" +
                                "Longueur des manches: Manche longue\n" +
                                "Type de produit: Pull\n" +
                                "Le col: Bougie\n" +
                                "Tissu: Tricot\n" +
                                "Sous-marque: LCWAIKIKI Basic\n" +
                                "Genre: Femme", price = 1000.0, quantity = 1000, brandId = 1, typeId = 3, mainImage = "https://img-lcwaikiki.mncdn.com/mnresize/480/-/pim/productimages/20232/6841875/v1/l_20232-w3h441z8-dnj-80-63-90-175_a.jpg"))

                        adminDao.insertAdmin(Admin(firstname = "John", lastname = "Doe", email = "john@doe.com", password = "123"))
                        adminDao.insertAdmin(Admin(firstname = "Jane", lastname = "Doe", email = "jane@doe.com", password = "123"))
                        adminDao.insertAdmin(Admin(firstname = "Alice", lastname = "Smith", email = "alice@smith.com", password = "123"))
                        adminDao.insertAdmin(Admin(firstname = "Bob", lastname = "Johnson", email = "bob@johnson.com", password = "123"))

                        brandDao.insertBrand(ProductBrand(brandId = 3, brandName = "Puma", brandImage = "https://cdn-icons-png.flaticon.com/128/599/599597.png"))
                        brandDao.insertBrand(ProductBrand(brandId = 4, brandName = "Reebok", brandImage = "https://cdn-icons-png.flaticon.com/128/719/719067.png"))

                        typeDao.insertType(ProductType(typeId = 4, typeName = "Sweater", typeImage = "https://img-lcwaikiki.mncdn.com/Resource/Images/Banner/fr-2909-circle5.jpg"))
                        typeDao.insertType(ProductType(typeId = 5, typeName = "Shorts", typeImage = "https://cdn-icons-png.flaticon.com/128/1834/1834902.png"))
                        typeDao.insertType(ProductType(typeId = 6, typeName = "Shoes", typeImage = "https://cdn-icons-png.flaticon.com/128/1948/1948065.png"))

                        productDao.insertProduct(Product( productId = 6,productName = "Winter Jacket", productDesc = "Chevilles et bas côtelés\n" +
                                "Tailles de modèle\n" +
                                "Poitrine: 78 cm Taille: 56 cm Hanche: 91 cm Longueur: 178 cm\n" +
                                "Le mannequin porte la taille S.\n" +
                                "Contenu et caractéristiques du produit\n" +
                                "Contenu du produit:\n" +
                                "Tissu Principal: 100 % ACRYLIQUE\n" +
                                "\n" +
                                "Caractéristiques du produit:\n" +
                                "Motif: Coupe droite\n" +
                                "Coupe: Coupe confortable\n" +
                                "Épaisseur: Épais\n" +
                                "Longueur des manches: Manche longue\n" +
                                "Type de produit: Pull\n" +
                                "Le col: Bougie\n" +
                                "Tissu: Tricot\n" +
                                "Sous-marque: LCWAIKIKI Basic\n" +
                                "Genre: Femme", price = 299.99, quantity = 500, brandId = 3, typeId = 4, mainImage = "https://img-lcwaikiki.mncdn.com/mnresize/480/-/pim/productimages/20222/6150426/v1/l_20222-w2ge78z8-s23-85-105-90-174_a.jpg"))
                        productDao.insertProduct(Product(productId = 7,productName = "Running Shoes", productDesc = "Chevilles et bas côtelés\n" +
                                "Tailles de modèle\n" +
                                "Poitrine: 78 cm Taille: 56 cm Hanche: 91 cm Longueur: 178 cm\n" +
                                "Le mannequin porte la taille S.\n" +
                                "Contenu et caractéristiques du produit\n" +
                                "Contenu du produit:\n" +
                                "Tissu Principal: 100 % ACRYLIQUE\n" +
                                "\n" +
                                "Caractéristiques du produit:\n" +
                                "Motif: Coupe droite\n" +
                                "Coupe: Coupe confortable\n" +
                                "Épaisseur: Épais\n" +
                                "Longueur des manches: Manche longue\n" +
                                "Type de produit: Pull\n" +
                                "Le col: Bougie\n" +
                                "Tissu: Tricot\n" +
                                "Sous-marque: LCWAIKIKI Basic\n" +
                                "Genre: Femme", price = 129.99, quantity = 800, brandId = 4, typeId = 6, mainImage = "https://img-lcwaikiki.mncdn.com/mnresize/480/-/pim/productimages/20232/6484061/v2/l_20232-w32862z8-lcc-78-56-91-178_a.jpg"))
                        productDao.insertProduct(Product(productId = 8,productName = "Summer Dress", livreson = true, productDesc = "Chevilles et bas côtelés\n" +
                                "Tailles de modèle\n" +
                                "Poitrine: 78 cm Taille: 56 cm Hanche: 91 cm Longueur: 178 cm\n" +
                                "Le mannequin porte la taille S.\n" +
                                "Contenu et caractéristiques du produit\n" +
                                "Contenu du produit:\n" +
                                "Tissu Principal: 100 % ACRYLIQUE\n" +
                                "\n" +
                                "Caractéristiques du produit:\n" +
                                "Motif: Coupe droite\n" +
                                "Coupe: Coupe confortable\n" +
                                "Épaisseur: Épais\n" +
                                "Longueur des manches: Manche longue\n" +
                                "Type de produit: Pull\n" +
                                "Le col: Bougie\n" +
                                "Tissu: Tricot\n" +
                                "Sous-marque: LCWAIKIKI Basic\n" +
                                "Genre: Femme", price = 79.99, quantity = 700, brandId = 3, typeId = 3, mainImage = "https://img-lcwaikiki.mncdn.com/mnresize/480/-/pim/productimages/20232/6466928/v1/l_20232-w31507z8-l8n-103-82-86-187_a.jpg"))
                        productDao.insertProduct(Product(productId = 9,productName = "Athletic Shorts", livreson = true, productDesc = "Chevilles et bas côtelés\n" +
                                "Tailles de modèle\n" +
                                "Poitrine: 78 cm Taille: 56 cm Hanche: 91 cm Longueur: 178 cm\n" +
                                "Le mannequin porte la taille S.\n" +
                                "Contenu et caractéristiques du produit\n" +
                                "Contenu du produit:\n" +
                                "Tissu Principal: 100 % ACRYLIQUE\n" +
                                "\n" +
                                "Caractéristiques du produit:\n" +
                                "Motif: Coupe droite\n" +
                                "Coupe: Coupe confortable\n" +
                                "Épaisseur: Épais\n" +
                                "Longueur des manches: Manche longue\n" +
                                "Type de produit: Pull\n" +
                                "Le col: Bougie\n" +
                                "Tissu: Tricot\n" +
                                "Sous-marque: LCWAIKIKI Basic\n" +
                                "Genre: Femme", price = 49.99, quantity = 1000, brandId = 4, typeId = 5, mainImage = "https://img-lcwaikiki.mncdn.com/mnresize/480/-/pim/productimages/20232/6484061/v2/l_20232-w32862z8-lcc-78-56-91-178_a.jpg"))
                        productDao.insertProduct(Product(productId = 10,productName = "Casual T-Shirt", sold = 70, livreson = true, productDesc = "Chevilles et bas côtelés\n" +
                                "Tailles de modèle\n" +
                                "Poitrine: 78 cm Taille: 56 cm Hanche: 91 cm Longueur: 178 cm\n" +
                                "Le mannequin porte la taille S.\n" +
                                "Contenu et caractéristiques du produit\n" +
                                "Contenu du produit:\n" +
                                "Tissu Principal: 100 % ACRYLIQUE\n" +
                                "\n" +
                                "Caractéristiques du produit:\n" +
                                "Motif: Coupe droite\n" +
                                "Coupe: Coupe confortable\n" +
                                "Épaisseur: Épais\n" +
                                "Longueur des manches: Manche longue\n" +
                                "Type de produit: Pull\n" +
                                "Le col: Bougie\n" +
                                "Tissu: Tricot\n" +
                                "Sous-marque: LCWAIKIKI Basic\n" +
                                "Genre: Femme", price = 19.99, quantity = 1200, brandId = 3, typeId = 1, mainImage = "https://img-lcwaikiki.mncdn.com/mnresize/480/-/pim/productimages/20232/6470811/v1/l_20232-w31845z8-s8f-99-75-93-185_a.jpg"))


                        colorDao.insertColor(ProductColor(colorName = "white", colorHexCode = "#FFE91E63", productId = 1))
                        colorDao.insertColor(ProductColor(colorName = "black", colorHexCode = "#FF93B36F", productId = 1))
                        colorDao.insertColor(ProductColor(colorName = "yellow", colorHexCode = "#FFFFEB3B", productId = 1))
                        colorDao.insertColor(ProductColor(colorName = "yellow", colorHexCode = "#FFFFEB3B", productId = 7))
                        colorDao.insertColor(ProductColor(colorName = "green", colorHexCode = "#FF8BC34A", productId = 7))
                        colorDao.insertColor(ProductColor(colorName = "blue", colorHexCode = "#FF03A9F4", productId = 7))

                        colorDao.insertColor(ProductColor(colorName = "white", colorHexCode = "#FFFFFFFF", productId = 2))
                        colorDao.insertColor(ProductColor(colorName = "black", colorHexCode = "#FF000000", productId = 2))
                        colorDao.insertColor(ProductColor(colorName = "red", colorHexCode = "#FF0000", productId = 2))
                        colorDao.insertColor(ProductColor(colorName = "yellow", colorHexCode = "#009688", productId = 2))
                        colorDao.insertColor(ProductColor(colorName = "green", colorHexCode = "#FF8BC34A", productId = 2))
                        colorDao.insertColor(ProductColor(colorName = "blue", colorHexCode = "#FF03A9F4", productId = 2))

                        colorDao.insertColor(ProductColor(colorName = "white", colorHexCode = "#FFFFFFFF", productId = 3))
                        colorDao.insertColor(ProductColor(colorName = "black", colorHexCode = "#FF000000", productId = 3))
                        colorDao.insertColor(ProductColor(colorName = "red", colorHexCode = "#FF0000", productId = 3))
                        colorDao.insertColor(ProductColor(colorName = "yellow", colorHexCode = "#009688", productId = 3))
                        colorDao.insertColor(ProductColor(colorName = "green", colorHexCode = "#FF8BC34A", productId = 3))
                        colorDao.insertColor(ProductColor(colorName = "blue", colorHexCode = "#FF03A9F4", productId = 3))

                        colorDao.insertColor(ProductColor(colorName = "white", colorHexCode = "#FFFFFFFF", productId = 4))
                        colorDao.insertColor(ProductColor(colorName = "black", colorHexCode = "#FF000000", productId = 4))
                        colorDao.insertColor(ProductColor(colorName = "red", colorHexCode = "#FF0000", productId = 4))
                        colorDao.insertColor(ProductColor(colorName = "yellow", colorHexCode = "#009688", productId = 4))
                        colorDao.insertColor(ProductColor(colorName = "green", colorHexCode = "#FF8BC34A", productId = 4))
                        colorDao.insertColor(ProductColor(colorName = "blue", colorHexCode = "#FF03A9F4", productId = 4))

                        colorDao.insertColor(ProductColor(colorName = "white", colorHexCode = "#FFFFFFFF", productId = 5))
                        colorDao.insertColor(ProductColor(colorName = "black", colorHexCode = "#FF000000", productId = 5))
                        colorDao.insertColor(ProductColor(colorName = "red", colorHexCode = "#FF0000", productId = 5))
                        colorDao.insertColor(ProductColor(colorName = "yellow", colorHexCode = "#009688", productId = 5))
                        colorDao.insertColor(ProductColor(colorName = "green", colorHexCode = "#FF8BC34A", productId = 5))
                        colorDao.insertColor(ProductColor(colorName = "blue", colorHexCode = "#FF03A9F4", productId = 5))

                        sizeDao.insertSize(ProductSize(sizeName = "XL", productId = 1))
                        sizeDao.insertSize(ProductSize(sizeName = "XXL", productId = 1))
                        sizeDao.insertSize(ProductSize(sizeName = "L", productId = 1))
                        sizeDao.insertSize(ProductSize(sizeName = "ML", productId = 1))
                        sizeDao.insertSize(ProductSize(sizeName = "M", productId = 1))

                        sizeDao.insertSize(ProductSize(sizeName = "XL", productId = 2))
                        sizeDao.insertSize(ProductSize(sizeName = "XXL", productId = 3))
                        sizeDao.insertSize(ProductSize(sizeName = "L", productId = 3))
                        sizeDao.insertSize(ProductSize(sizeName = "ML", productId = 2))
                        sizeDao.insertSize(ProductSize(sizeName = "M", productId = 2))

                        sizeDao.insertSize(ProductSize(sizeName = "XL", productId = 4))
                        sizeDao.insertSize(ProductSize(sizeName = "XXL", productId = 4))
                        sizeDao.insertSize(ProductSize(sizeName = "L", productId = 4))
                        sizeDao.insertSize(ProductSize(sizeName = "ML", productId = 4))
                        sizeDao.insertSize(ProductSize(sizeName = "M", productId = 4))

                        sizeDao.insertSize(ProductSize(sizeName = "XL", productId = 5))
                        sizeDao.insertSize(ProductSize(sizeName = "XXL", productId = 5))
                        sizeDao.insertSize(ProductSize(sizeName = "L", productId = 6))
                        sizeDao.insertSize(ProductSize(sizeName = "ML", productId = 5))
                        sizeDao.insertSize(ProductSize(sizeName = "M", productId = 5))

                        imageDao.insertImage(ProductSubImage(productId = 1, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6484061/v2/l_20232-w32862z8-lcc-78-56-91-178_a2.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 1, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6484061/v2/l_20232-w32862z8-lcc-78-56-91-178_a3.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 1, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6484061/v1/l_20232-w32862z8-lcc_u.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 1, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6484061/v2/l_20232-w32862z8-lcc-78-56-91-178_a4.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 1, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6484061/v1/l_20232-w32862z8-fks-83-62-92-176_a1.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 1, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6484061/v1/l_20232-w32862z8-fks-83-62-92-176_a2.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 1, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6484061/v1/l_20232-w32862z8-fks-83-62-92-176_a4.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 1, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6484061/v1/l_20232-w32862z8-fks_u.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 1, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6484061/v2/l_20232-w32862z8-ldr-78-56-91-178_a2.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 1, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6484061/v2/l_20232-w32862z8-ldr_u.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 1, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6484061/v2/l_20232-w32862z8-ldr_u1.jpg"))


                        imageDao.insertImage(ProductSubImage(productId = 2, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6470811/v1/l_20232-w31845z8-s8f-99-75-93-185_a4.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 2, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6470811/v1/l_20232-w31845z8-s8f-99-75-93-185_a2.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 2, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6470811/v1/l_20232-w31845z8-s8f_u.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 2, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6470811/v2/l_20232-w31845z8-j0l-99-75-93-185_a1.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 2, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6470811/v2/l_20232-w31845z8-j0l_u.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 2, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6470811/v1/l_20232-w31845z8-s8f_u1.jpg"))


                        imageDao.insertImage(ProductSubImage(productId = 3, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6466928/v1/l_20232-w31507z8-l8n-103-82-86-187_a2.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 3, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6466928/v1/l_20232-w31507z8-l8n-103-82-86-187_a4.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 3, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6466928/v1/l_20232-w31507z8-l8n_u.jpg"))


                        imageDao.insertImage(ProductSubImage(productId = 4, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6664452/v1/l_20232-w3ck07z8-fmp-75-61-88-178_a1.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 4, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6664452/v1/l_20232-w3ck07z8-fmp-75-61-88-178_a.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 4, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6664452/v1/l_20232-w3ck07z8-fmp-75-61-88-178_a3.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 4, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6664452/v1/l_20232-w3ck07z8-fmp-75-61-88-178_a4.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 4, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6664452/v1/l_20232-w3ck07z8-fmp_u.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 4, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6664452/v1/l_20232-w3ck07z8-h0u-75-61-88-178_a.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 4, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6664452/v1/l_20232-w3ck07z8-h0u-75-61-88-178_a1.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 4, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6664452/v1/l_20232-w3ck07z8-h0u-75-61-88-178_a3.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 4, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6664452/v1/l_20232-w3ck07z8-h0u-75-61-88-178_a2.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 4, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6664452/v1/l_20232-w3ck07z8-h0u-75-61-88-178_a4.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 4, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6664452/v1/l_20232-w3ck07z8-h0u_u.jpg"))


                        imageDao.insertImage(ProductSubImage(productId = 5, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6841875/v1/l_20232-w3h441z8-dnj-80-63-90-175_a1.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 5, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6841875/v1/l_20232-w3h441z8-dnj-80-63-90-175_a2.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 5, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6841875/v1/l_20232-w3h441z8-dnj-80-63-90-175_a3.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 5, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6841875/v1/l_20232-w3h441z8-dnj-80-63-90-175_a4.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 5, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20232/6841875/v1/l_20232-w3h441z8-dnj_u.jpg"))



                        imageDao.insertImage(ProductSubImage(productId = 6, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20222/6150426/v1/l_20222-w2ge78z8-s23_u.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 6, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20222/6150426/v1/l_20222-w2ge78z8-s23-85-105-90-174_a5.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 6, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20222/6150426/v1/l_20222-w2ge78z8-s23-85-105-90-174_a3.jpg"))
                        imageDao.insertImage(ProductSubImage(productId = 6, imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/1024/-/pim/productimages/20222/6150426/v1/l_20222-w2ge78z8-s23-85-105-90-174_a3.jpg"))

                        likeDao.insertLike(ProductLike(productId = 1, clientId = 1))
                        likeDao.insertLike(ProductLike(productId = 4, clientId = 1))
                        likeDao.insertLike(ProductLike(productId = 1, clientId = 1))
                        likeDao.insertLike(ProductLike(productId = 7, clientId = 1))
                        likeDao.insertLike(ProductLike(productId = 3, clientId = 1))
                    }
                }
            })
            .build()

    }

    @Singleton
    @Provides
    fun provideAdminDao(ecommerceDb: EcommerceDb): AdminDao {
        return ecommerceDb.adminDao()
    }

    @Singleton
    @Provides
    fun provideClientDao(ecommerceDb: EcommerceDb): ClientDao {
        return ecommerceDb.clientDao()
    }

    @Singleton
    @Provides
    fun provideImageDao(ecommerceDb: EcommerceDb): ProductImagesDao {
        return ecommerceDb.imagetDao()
    }

    @Singleton
    @Provides
    fun provideProductColorDao(ecommerceDb: EcommerceDb): ProductColorDao {
        return ecommerceDb.productColorDao()
    }

    @Singleton
    @Provides
    fun provideProductTypeDao(ecommerceDb: EcommerceDb): ProductTypeDao {
        return ecommerceDb.productTypeDao()
    }

    @Singleton
    @Provides
    fun provideProductSizeDao(ecommerceDb: EcommerceDb): ProductSizeDao {
        return ecommerceDb.productSizeDao()
    }

    @Singleton
    @Provides
    fun provideProductBrandDao(ecommerceDb: EcommerceDb): ProductBrandDao {
        return ecommerceDb.productBrandDao()
    }

    @Singleton
    @Provides
    fun provideProductDao(ecommerceDb: EcommerceDb): ProductDao {
        return ecommerceDb.productDao()
    }

    @Singleton
    @Provides
    fun provideProductLikkeDao(ecommerceDb: EcommerceDb): ProductLikeDao {
        return ecommerceDb.productLikeDao()
    }

}