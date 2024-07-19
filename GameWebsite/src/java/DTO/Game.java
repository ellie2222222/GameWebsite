/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Tam
 */
public class Game {
    private int gameId;
    private String title;
    private String description;
    private double price;
    private String releaseDate;
    private String platform;
    private String publisher;
    private boolean inLibrary;
    private ArrayList<String> genreList;
    private ArrayList<String> imagePathList;

    public Game(String title, String description, double price, String releaseDate, String platform, String publisher) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.releaseDate = releaseDate;
        this.platform = platform;
        this.publisher = publisher;
    }

    public Game(int gameId, String title, String description, double price, String releaseDate, String platform, String publisher) {
        this.gameId = gameId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.releaseDate = releaseDate;
        this.platform = platform;
        this.publisher = publisher;
    }

    public Game(int gameId, String title, String description, double price, String releaseDate, String platform, String publisher, ArrayList<String> genreList, ArrayList<String> imagePathList) {
        this.gameId = gameId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.releaseDate = releaseDate;
        this.platform = platform;
        this.publisher = publisher;
        this.genreList = genreList;
        this.imagePathList = imagePathList;
    }
    
     public Game(int gameId, String title, String description, double price, String releaseDate, String platform, String publisher, boolean inLibrary, ArrayList<String> genreList, ArrayList<String> imagePathList) {
        this.gameId = gameId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.releaseDate = releaseDate;
        this.platform = platform;
        this.publisher = publisher;
        this.inLibrary = inLibrary;
        this.genreList = genreList;
        this.imagePathList = imagePathList;
    }
    
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public boolean isInLibrary() {
        return inLibrary;
    }

    public void setInLibrary(boolean inLibrary) {
        this.inLibrary = inLibrary;
    }

    public ArrayList<String> getImagePathList() {
        return imagePathList;
    }

    public void setImagePathList(ArrayList<String> imagePathList) {
        this.imagePathList = imagePathList;
    }

    public ArrayList<String> getGenreList() {
        return genreList;
    }

    public void setGenreList(ArrayList<String> genreList) {
        this.genreList = genreList;
    }
    
    public String getImageHeader() {
        String imagePath = "";
        Iterator<String> it = imagePathList.iterator();
        while (it.hasNext()) {
            imagePath = it.next();
            if (imagePath.contains("header")) {
                break;
            }
        }
        return imagePath;
    }
    
    public String getImageMain() {
        String imagePath = "";
        Iterator<String> it = imagePathList.iterator();
        while (it.hasNext()) {
            imagePath = it.next();
            if (imagePath.contains("main")) {
                break;
            }
        }
        return imagePath;
    }
    
    public String getImageSlideshow() {
        String imagePath = "";
        Iterator<String> it = imagePathList.iterator();
        while (it.hasNext()) {
            imagePath = it.next();
            if (imagePath.contains("slideshow")) {
                break;
            }
        }
        return imagePath;
    }
    
    public String getShortDescription() {
        String desc = getDescription();
        desc = desc.split("\\.")[0];
        return desc;
    }
}
