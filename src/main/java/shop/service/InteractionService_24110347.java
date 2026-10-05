package shop.service;

import shop.dao.FavoriteDAO__24110347;
import shop.dao.ShareDAO_24110347;
import shop.entity.Favorite_24110347;
import shop.entity.Share_24110347;
import shop.entity.User_24110347;
import shop.entity.Video_24110347;

import java.util.Date;

public class InteractionService_24110347 {
    private final ShareDAO_24110347 shareDAO = new ShareDAO_24110347();
    private final FavoriteDAO__24110347 favoriteDAO = new FavoriteDAO__24110347();

    public long countShares(Integer videoId) {
        return shareDAO.countByVideo(videoId);
    }

    public long countLikes(Integer videoId) {
        return favoriteDAO.countByVideo(videoId);
    }

    public void share(Video_24110347 video, User_24110347 currentUser, String email) {
        Share_24110347 share = new Share_24110347();
        share.setVideo(video);
        share.setUser(currentUser);
        share.setEmails(email);
        share.setSharedDate(new Date());
        shareDAO.save(share);
    }

    /** @return false neu user nay da like video nay roi (khong luu trung) */
    public boolean like(Video_24110347 video, User_24110347 currentUser) {
        if (favoriteDAO.exists(video.getVideoId(), currentUser.getUsername())) {
            return false;
        }
        Favorite_24110347 favorite = new Favorite_24110347();
        favorite.setVideo(video);
        favorite.setUser(currentUser);
        favorite.setLikedDate(new Date());
        favoriteDAO.save(favorite);
        return true;
    }
}
