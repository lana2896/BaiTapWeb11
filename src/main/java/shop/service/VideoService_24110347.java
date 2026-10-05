package shop.service;

import shop.dao.VideoDAO_24110347;
import shop.entity.Video_24110347;

import java.util.List;

public class VideoService_24110347 {
    private final VideoDAO_24110347 videoDAO = new VideoDAO_24110347();

    public static final int ADMIN_PAGE_SIZE = 6;
    public static final int HOME_PAGE_SIZE = 3;

    public Video_24110347 findById(Integer id) {
        return videoDAO.findById(id);
    }

    public PageResult_24110347<Video_24110347> listForAdmin(int page) {
        if (page < 1) page = 1;
        long total = videoDAO.countAll();
        List<Video_24110347> items = videoDAO.findPage(page, ADMIN_PAGE_SIZE);
        return new PageResult_24110347<>(items, page, ADMIN_PAGE_SIZE, total);
    }

    public PageResult_24110347<Video_24110347> listByCategory(Integer categoryId, int page) {
        if (page < 1) page = 1;
        long total = videoDAO.countByCategory(categoryId);
        List<Video_24110347> items = videoDAO.findPageByCategory(categoryId, page, HOME_PAGE_SIZE);
        return new PageResult_24110347<>(items, page, HOME_PAGE_SIZE, total);
    }

    public long countByCategory(Integer categoryId) {
        return videoDAO.countByCategory(categoryId);
    }

    public void create(Video_24110347 video) {
        videoDAO.save(video);
    }

    public void update(Video_24110347 video) {
        videoDAO.update(video);
    }

    public void delete(Integer id) {
        videoDAO.delete(id);
    }

    public void increaseView(Integer id) {
        videoDAO.incrementViews(id);
    }
}
