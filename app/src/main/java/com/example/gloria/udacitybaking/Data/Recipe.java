package com.example.gloria.udacitybaking.Data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Recipe implements Parcelable {


    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("image")
    private String image;

    @JsonProperty("servings")
    private int servings;

    @JsonProperty("ingredients")
    private List<Ingredients> ingredients = null;

    @JsonProperty("steps")
    private List<Step> steps;

    public Recipe() {
        this.image = "";
        this.servings = 0;
        this.name = "";
        this.ingredients = new ArrayList<>();
        this.id = 0;
        this.steps = new ArrayList<>();
    }

    protected Recipe(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.ingredients = new ArrayList<>();
        in.readList(this.ingredients, Ingredients.class.getClassLoader());
        this.steps = new ArrayList<>();
        in.readList(this.steps, Step.class.getClassLoader());
        this.servings = in.readInt();
        this.image = in.readString();


    }

    public static String toBase64String(Recipe recipe) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Base64.encodeToString(mapper.writeValueAsBytes(recipe), 0);
        } catch (JsonProcessingException e) {
            //
        }
        return null;

    }

    public static Recipe fromBase64(String string) {
        if (!"".equals(string)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(Base64.decode(string, 0), Recipe.class);
            } catch (IOException e) {
                //error
            }
        }
        return null;
    }



    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeList(this.ingredients);
        dest.writeList(this.steps);
        dest.writeInt(this.servings);
        dest.writeString(this.image);
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };



}