package com.dao.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GreenDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.nytimes.news.greendao");

        schema.enableKeepSectionsByDefault();
        schema.enableActiveEntitiesByDefault();

        addForecastTable(schema);

        new DaoGenerator().generateAll(schema, "app/src/main/java");
    }

    public static void addForecastTable(Schema schema) {
        Entity news = schema.addEntity("New");
        news.addIdProperty().autoincrement();
        news.addStringProperty("section");
        news.addStringProperty("title");
        news.addStringProperty("abstract_info");
        news.addStringProperty("url");
        news.addStringProperty("thumbnail_standard");
        news.addStringProperty("item_type");
        news.addStringProperty("source");
        news.addStringProperty("published_date");
    }

}
