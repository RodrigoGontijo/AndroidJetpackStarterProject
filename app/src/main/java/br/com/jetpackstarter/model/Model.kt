package br.com.jetpackstarter.model

import com.google.gson.annotations.SerializedName

data class DogBreed(

    @SerializedName("id")
    val breedId: String?,

    @SerializedName("name")
    val dogBreed: String?,

    @SerializedName("life_span")
    val lifeSpam: String?,

    @SerializedName("breed_group")
    val breedGroup: String?,

    @SerializedName("bred_for")
    val bredFor: String?,

    @SerializedName("temperament")
    val temperature: String?,

    @SerializedName("url")
    val imageUrl: String?
)