package com.maohongyu.newsapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by isle on 2017/1/10.
 */

public class CategoryBean {

    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public static class Category implements Parcelable{

        private String name;

        private String type;

        protected Category(Parcel in) {
            name = in.readString();
            type = in.readString();
        }

        public Category(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public Category() {
        }

        public static final Creator<Category> CREATOR = new Creator<Category>() {
            @Override
            public Category createFromParcel(Parcel in) {
                return new Category(in);
            }

            @Override
            public Category[] newArray(int size) {
                return new Category[size];
            }
        };

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            String name = getName();
            if (name != null) {
                return name;
            }
            return null;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(type);
        }


        @Override
        public boolean equals(Object obj) {
            if (obj==this) {
                return true;
            }
            if (obj instanceof Category) {
                Category category = (Category) obj;
                if (category.getName().equals(this.getName())&&category.getType().equals(this.getType())) {
                    return true;
                }else {
                    return false;
                }
            }else {
                return false;
            }
        }
    }

    @Override
    public String toString() {
        return "CategoryBean{" +
                "categories=" + categories.toString() +
                '}';
    }

    public CategoryBean(List<Category> categories) {
        this.categories = categories;
    }

    public CategoryBean() {
    }
}
