package com.example.kazuhiroshigenobu.googlemaptraining;

/**
 * Created by KazuhiroShigenobu on 13/2/17.
 */

public class User {

        public String email;
        public String password;
        public String userPhoto;
        public String userEmail;
        public int totalLikedCount;
        public int totalHelpedCount;
        public int totalFavoriteCount;


        public User(){
            // ...

    }
        public User(String email, String password,String userPhoto,String userEmail,int totalLikedCount,int totalHelpedCount,int totalFavoriteCount)
        {
            this.email = email;
            this.password = password;
            this.userPhoto = userPhoto;
            this.userEmail = userEmail;
            this.totalLikedCount = totalLikedCount;
            this.totalHelpedCount = totalHelpedCount;
            this.totalFavoriteCount = totalFavoriteCount;

    }
}
